# KPastebin [![Maven Central](https://img.shields.io/maven-central/v/net.pearx.kpastebin/kpastebin.svg?label=version&logo=kotlin&logoColor=white)](https://search.maven.org/search?q=g:%22net.pearx.kpastebin%22%20AND%20a:%22kpastebin%22) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![License](https://img.shiel**ds.io/github/license/pearxteam/kpastebin.svg)](https://raw.githubusercontent.com/pearxteam/kpastebin/master/LICENSE.TXT)
[![Discord](https://img.shields.io/discord/136085738151346176.svg?logo=discord&logoColor=white)](https://discord.gg/q9cX9QE)

![Test & Deploy | develop](https://github.com/pearxteam/kpastebin/workflows/Test%20&%20Deploy%20%7C%20develop/badge.svg?branch=develop)
![Test & Deploy | master](https://github.com/pearxteam/kpastebin/workflows/Test%20&%20Deploy%20%7C%20master/badge.svg?branch=master)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=pearxteam_kpastebin&metric=coverage)](https://sonarcloud.io/dashboard?id=pearxteam_kpastebin)


Multiplatform Kotlin library to interact with the pastebin.com API.

# Setup
1. Add the lib to your project's dependencies:

* Groovy (replace `$kpastebin_version` with the version you want):
```groovy
 // universal dependency for Gradle 5.3 and above
 // in case of multiplatform project, just specify the dependency for commonMain/commonTest source set
 implementation "net.pearx.kpastebin:kpastebin:$kpastebin_version" 
```
```kotlin
 // for Gradle versions below 5.3:
 implementation "net.pearx.kpastebin:kpastebin-metadata:$kpastebin_version" // for Common
 // or
 implementation "net.pearx.kpastebin:kpastebin-jvm:$kpastebin_version" // for JVM
 // or
 implementation "net.pearx.kpastebin:kpastebin-js:$kpastebin_version" // for JS
 // or
 implementation "net.pearx.kpastebin:kpastebin-android:$kpastebin_version" // for Android
 // or
 implementation "net.pearx.kpastebin:kpastebin-PLATFORM_YOU_WANT:$kpastebin_version" // for Native
```

* Kotlin (replace `$kpastebinVersion` with the version you want):
```kotlin
 // universal dependency for Gradle 5.3 and above
 // in case of multiplatform project, just specify the dependency for commonMain/commonTest source set
 implementation("net.pearx.kpastebin:kpastebin:$kpastebinVersion") 
```
```kotlin
 // for Gradle versions below 5.3:
 implementation("net.pearx.kpastebin:kpastebin-metadata:$kpastebinVersion") // for Common
 // or
 implementation("net.pearx.kpastebin:kpastebin-jvm:$kpastebinVersion") // for JVM
 // or
 implementation("net.pearx.kpastebin:kpastebin-js:$kpastebinVersion") // for JS
 // or
 implementation("net.pearx.kpastebin:kpastebin-android:$kpastebinVersion") // for Android
 // or
 implementation("net.pearx.kpastebin:kpastebin-PLATFORM_YOU_WANT:$kpastebinVersion") // for Native
```

2. Use the library and have fun!