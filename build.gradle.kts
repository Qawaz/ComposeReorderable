plugins {
    `maven-publish`
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.multiplatform") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("org.jetbrains.compose") apply false
}

ext {
    extra["compileSdkVersion"] = 33
    extra["minSdkVersion"] = 21
    extra["targetSdkVersion"] = 33
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}