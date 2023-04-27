import org.jetbrains.compose.ComposeBuildConfig.composeVersion
import java.util.Properties;

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("maven-publish")
    id("signing")
}

group = "com.qawaz"
version = "0.9.8"

kotlin {
    android {
        publishLibraryVariants("release")
    }
    jvm()
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.foundation)
                implementation("org.jetbrains.compose.ui:ui-util:${composeVersion}")
            }
        }
    }
}

//val javadocJar = tasks.register("javadocJar", Jar::class.java) {
//    archiveClassifier.set("javadoc")
//}

publishing {
    publications {
        repositories {
            maven {
                name="oss"
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                credentials {
                    username = extra.properties.getOrDefault("ossrh.Username", "") as String
                    password = extra.properties.getOrDefault("ossrh.Password", "") as String
                }
            }

            maven("https://maven.pkg.github.com/Qawaz/ComposeReorderable") {
                name = "GithubPackages"
                try {
                    credentials {
                        username = (System.getenv("GPR_USER")).toString()
                        password = (System.getenv("GPR_API_KEY")).toString()
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }
    publications {
        withType<MavenPublication> {
//            artifact(javadocJar)
            pom {
                name.set("ComposeReorderable")
                description.set("Reorderable Compose LazyList")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                url.set("https://github.com/aclassen/ComposeReorderable")
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/aclassen/ComposeReorderable/issues")
                }
                scm {
                    connection.set("https://github.com/aclassen/ComposeReorderable.git")
                    url.set("https://github.com/aclassen/ComposeReorderable")
                }
                developers {
                    developer {
                        name.set("Andre Claßen")
                        email.set("andreclassen1337@gmail.com")
                    }
                }
            }
        }
    }
}

//signing {
//    sign(publishing.publications)
//}

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }
}