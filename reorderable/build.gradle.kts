import org.jetbrains.compose.ComposeBuildConfig.composeVersion
import java.util.Properties;

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("signing")
}

group = "com.qawaz"
version = "0.9.6"

kotlin {
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

val propertiesFile = project.rootProject.file("github.properties")
val isGithubPropAvailable = propertiesFile.exists()

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

            if(isGithubPropAvailable) {
                val githubProperties = Properties().apply {
                    propertiesFile.reader().use { load(it) }
                }

                maven {
                    name = "GithubPackages"
                    url = uri("https://maven.pkg.github.com/Qawaz/ComposeReorderable")
                    try {
                        credentials {
                            username = (githubProperties["gpr.usr"] ?: System.getenv("GPR_USER")).toString()
                            password = (githubProperties["gpr.key"] ?: System.getenv("GPR_API_KEY")).toString()
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
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

val checkGithubTask = tasks.register("checkGithubProperties") {
    doLast {
        if (!isGithubPropAvailable) {
            error("Github properties file is not available. Throwing error.")
        }
    }
}

tasks.withType(PublishToMavenRepository::class.java).configureEach {
    dependsOn(checkGithubTask)
}
