# @PROJECT_NAME_BEAUTIFUL@ [![Maven Central](https://img.shields.io/maven-central/v/net.pearx.@PROJECT_NAME@/@PROJECT_NAME@.svg?label=version&logo=kotlin&logoColor=white)](https://search.maven.org/search?q=g:%22net.pearx.@PROJECT_NAME@%22%20AND%20a:%22@PROJECT_NAME@%22) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![License](https://img.shields.io/github/license/pearxteam/@PROJECT_NAME@.svg)](https://raw.githubusercontent.com/pearxteam/@PROJECT_NAME@/master/LICENSE.TXT)
[![Discord](https://img.shields.io/discord/136085738151346176.svg?logo=discord&logoColor=white)](https://discord.gg/q9cX9QE)
![Test & Deploy | develop](https://github.com/pearxteam/@PROJECT_NAME@/workflows/Test%20&%20Deploy%20%7C%20develop/badge.svg?branch=develop)
![Test & Deploy | master](https://github.com/pearxteam/@PROJECT_NAME@/workflows/Test%20&%20Deploy%20%7C%20master/badge.svg?branch=master)

@PROJECT_DESCRIPTION@

# Setup
1. Add the lib to your project's dependencies:

* Groovy (replace `$@PROJECT_NAME@_version` with the version you want):
```groovy
 // universal dependency for Gradle 5.3 and above
 // in case of multiplatform project, just specify the dependency for commonMain/commonTest source set
 implementation "net.pearx.@PROJECT_NAME@:@PROJECT_NAME@:$@PROJECT_NAME@_version" 
```
```kotlin
 // for Gradle versions below 5.3:
 implementation "net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-metadata:$@PROJECT_NAME@_version" // for Common
 // or
 implementation "net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-jvm:$@PROJECT_NAME@_version" // for JVM
 // or
 implementation "net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-js:$@PROJECT_NAME@_version" // for JS
 // or
 implementation "net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-android:$@PROJECT_NAME@_version" // for Android
 // or
 implementation "net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-PLATFORM_YOU_WANT:$@PROJECT_NAME@_version" // for Native
```

* Kotlin (replace `$@PROJECT_NAME@Version` with the version you want):
```kotlin
 // universal dependency for Gradle 5.3 and above
 // in case of multiplatform project, just specify the dependency for commonMain/commonTest source set
 implementation("net.pearx.@PROJECT_NAME@:@PROJECT_NAME@:$@PROJECT_NAME@Version") 
```
```kotlin
 // for Gradle versions below 5.3:
 implementation("net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-metadata:$@PROJECT_NAME@Version") // for Common
 // or
 implementation("net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-jvm:$@PROJECT_NAME@Version") // for JVM
 // or
 implementation("net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-js:$@PROJECT_NAME@Version") // for JS
 // or
 implementation("net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-android:$@PROJECT_NAME@Version") // for Android
 // or
 implementation("net.pearx.@PROJECT_NAME@:@PROJECT_NAME@-PLATFORM_YOU_WANT:$@PROJECT_NAME@Version") // for Native
```

2. Use the library and have fun!