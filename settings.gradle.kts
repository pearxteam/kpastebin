rootProject.name = "@PROJECT_NAME@"

pluginManagement {
    val multigradleVersion: String by settings
    val kotlinVersion: String by settings
    val githubReleaseVersion: String by settings

    plugins {
        id("org.jetbrains.kotlin.multiplatform") version kotlinVersion
        id("net.pearx.multigradle.simple.project") version multigradleVersion
        id("net.pearx.multigradle.simple.settings") version multigradleVersion
        id("com.github.breadmoirai.github-release") version githubReleaseVersion
    }

    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("net.pearx.multigradle.simple.settings")
}