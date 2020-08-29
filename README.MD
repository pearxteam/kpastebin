# KPastebin [![Maven Central](https://img.shields.io/maven-central/v/net.pearx.kpastebin/kpastebin.svg?label=version&logo=kotlin&logoColor=white)](https://search.maven.org/search?q=g:%22net.pearx.kpastebin%22%20AND%20a:%22kpastebin%22) [![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![License](https://img.shields.io/github/license/pearxteam/kpastebin.svg)](https://raw.githubusercontent.com/pearxteam/kpastebin/master/LICENSE.TXT)
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

2. Add [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) and any [Ktor client engine](https://ktor.io/clients/http-client/engines.html) to your project's dependencies.

3. Use the library and have fun! See [net.pearx.kpastebin.PastebinClient](src/commonMain/kotlin/net/pearx/kpastebin/PastebinClient.kt) class KDoc for more info.

# Examples
```kotlin
runBlocking {
    // see KDoc for more info
    val client = PastebinClient("your dev key") // create a PastebinClient instance. You can get your dev key here: https://pastebin.com/doc_api#1
    client.getPaste("0b42rwhf") // getting a paste
    
    client.login("username", "password") // logging in
    client.getUserDetails().accountType // getting current user account type
}
```