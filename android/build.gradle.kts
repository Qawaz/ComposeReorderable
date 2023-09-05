plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":reorderable"))
    implementation(compose.runtime)
    implementation(compose.material)
//    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.7.1")
    implementation("io.coil-kt:coil-compose:2.3.0")
}

android {
    namespace = "org.burnoutcrew.android"
    sourceSets {
        map { it.java.srcDir("src/${it.name}/kotlin") }
    }
    val minSdkVersion: Int by rootProject.extra
    val targetSdkVersion: Int by rootProject.extra
    val compileSdkVersion: Int by rootProject.extra
    compileSdk = compileSdkVersion
    defaultConfig {
        minSdk =  minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}