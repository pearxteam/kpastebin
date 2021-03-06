import com.github.breadmoirai.githubreleaseplugin.GithubReleaseExtension
import net.pearx.multigradle.util.MultiGradleExtension
import net.pearx.multigradle.util.kotlinMpp
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val projectChangelog: String by project
val projectDescription: String by project

val ktorVersion: String by project
val coroutinesVersion: String by project

val pearxRepoUsername: String? by project
val pearxRepoPassword: String? by project
val sonatypeOssUsername: String? by project
val sonatypeOssPassword: String? by project
val githubAccessToken: String? by project
val sonarcloudToken: String? by project
val devBuildNumber: String? by project

plugins {
    id("net.pearx.multigradle.simple.project")
    kotlin("multiplatform") apply (false)
    id("com.github.breadmoirai.github-release")
    id("org.sonarqube")
    `maven-publish`
    signing
}

group = "net.pearx.kpastebin"
description = projectDescription

configure<MultiGradleExtension> {
    if (devBuildNumber != null) {
        projectVersion = "$projectVersion-dev-$devBuildNumber"
    }
}

kotlinMpp {
    explicitApi()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }
    }
}

configure<PublishingExtension> {
    publications.withType<MavenPublication> {
        pom {
            name.set(artifactId)
            description.set(projectDescription)
            url.set("https://github.com/pearxteam/kpastebin")
            licenses {
                license {
                    name.set("Mozilla Public License, Version 2.0")
                    url.set("https://mozilla.org/MPL/2.0/")
                    distribution.set("repo")
                }
            }
            organization {
                name.set("PearX Team")
                url.set("https://pearx.net/")
            }
            developers {
                developer {
                    id.set("mrapplexz")
                    name.set("mrapplexz")
                    email.set("me@pearx.net")
                    url.set("https://pearx.net/members/mrapplexz")
                    organization.set("PearX Team")
                    organizationUrl.set("https://pearx.net/")
                    roles.set(listOf("developer"))
                    timezone.set("Asia/Yekaterinburg")
                }
            }
            scm {
                url.set("https://github.com/pearxteam/kpastebin")
                connection.set("scm:git:git://github.com/pearxteam/kpastebin")
                developerConnection.set("scm:git:git://github.com/pearxteam/kpastebin")
            }
            issueManagement {
                system.set("GitHub")
                url.set("https://github.com/pearxteam/kpastebin/issues")
            }
            ciManagement {
                system.set("GitHub")
                url.set("https://github.com/pearxteam/kpastebin/actions")
            }
        }
    }
    repositories {
        maven {
            credentials {
                username = pearxRepoUsername
                password = pearxRepoPassword
            }
            name = "pearx-repo-develop"
            url = uri("https://repo.pearx.net/maven2/develop/")
        }
        maven {
            credentials {
                username = pearxRepoUsername
                password = pearxRepoPassword
            }
            name = "pearx-repo-release"
            url = uri("https://repo.pearx.net/maven2/release/")
        }
        maven {
            credentials {
                username = sonatypeOssUsername
                password = sonatypeOssPassword
            }
            name = "sonatype-oss-release"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalUnsignedTypes"
    }

    val publishDevelop by registering {
        group = "publishing"
        dependsOn(check)
        dependsOn(withType<PublishToMavenRepository>().matching { it.repository.name.endsWith("-develop") })
    }
    val publishRelease by registering {
        group = "publishing"
        dependsOn(check)
        dependsOn(withType<PublishToMavenRepository>().matching { it.repository.name.endsWith("-release") })
    }
    val release by registering {
        group = "publishing"
        dependsOn(publishRelease)
        dependsOn(named("githubRelease"))
    }
}

configure<SigningExtension> {
    sign(publishing.publications)
}

configure<GithubReleaseExtension> {
    setToken(githubAccessToken)
    setOwner("pearxteam")
    setRepo(project.name)
    setTargetCommitish("master")
    setBody(projectChangelog)
    //setReleaseAssets((publishing.publications["maven"] as MavenPublication).artifacts.map { it.file })
}

fun findSourceDirectories(endsWith: String): FileCollection {
    return files(kotlin.sourceSets.filter { it.name.endsWith(endsWith) }.map { it.kotlin.sourceDirectories }).filter { it.isDirectory }
}

if (sonarcloudToken != null) {
    tasks.check {
        finalizedBy("sonarqube")
    }
    sonarqube {
        properties {
            property("sonar.host.url", "https://sonarcloud.io")
            property("sonar.login", sonarcloudToken!!)
            property("sonar.projectKey", "pearxteam_${project.name}")
            property("sonar.organization", "pearxteam")
            property("sonar.sourceEncoding", "UTF-8")
            property("sonar.sources", findSourceDirectories("Main").joinToString())
            property("sonar.tests", findSourceDirectories("Test").joinToString())
            property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/jacocoJvmTestReport/jacocoJvmTestReport.xml")
            property("sonar.junit.reportPaths", "$buildDir/test-results/jvmTest/*.xml")
        }
    }
}