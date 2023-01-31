pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String).apply(false)
        kotlin("android").version(extra["kotlin.version"] as String).apply(false)
        kotlin("multiplatform").version(extra["kotlin.version"] as String).apply(false)
        id("com.android.application").version(extra["agp.version"] as String).apply(false)
        id("com.android.library").version(extra["agp.version"] as String).apply(false)
        id("org.jetbrains.compose").version(extra["compose.version"] as String).apply(false)
        id("org.jetbrains.dokka").version(extra["kotlin.version"] as String).apply(false)
    }
}
rootProject.name = "ComposeReorderList"


include(":android")
include(":desktop")
include(":reorderable")
include(":web")

