import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven {
            name = "KotDis"
            url = uri("https://maven.kotlindiscord.com/repository/maven-public/")
        }
    }
}

// Load properties

val moduleGroup: String by project
val moduleVersion: String by project

val detektVersion: String by project
val kordExVersion: String by project
val kotlinVersion: String by project

plugins {
    `maven-publish`

    id("io.gitlab.arturbosch.detekt")
    id("de.undercouch.download")

    kotlin("jvm")
}

repositories {
    maven {
        name = "KotDis"
        url = uri("https://maven.kotlindiscord.com/repository/maven-public/")
    }

    // You can add more repositories below this line if needed.
}

dependencies {
    // Linting
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")

    // Configuration
    implementation("com.uchuhimo:konf:0.23.0")
    implementation("com.uchuhimo:konf-toml:0.23.0")

    // KordEx
    implementation("com.kotlindiscord.kord.extensions:kord-extensions:$kordExVersion")

    // Logging
    implementation("io.github.microutils:kotlin-logging:2.0.3")

    // Kotlin libs
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
}

/**
 * You probably don't want to touch anything below this line. It contains mostly boilerplate, and expands variables
 * from the gradle.properties file.
 */

group = "com.kotlindiscord.kordex.$moduleGroup"
version = moduleVersion

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"

    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val sourceJar = task("sourceJar", Jar::class) {
    dependsOn(tasks["classes"])
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val downloadDetektConfig = task("downloadDetektConfig", Download::class) {
    src("https://github.com/Kord-Extensions/kord-extensions/raw/root/kord-extensions/detekt.yml")
    dest(projectDir)
    onlyIfModified(true)
}

val printVersion = task("printVersion") {
    logger.quiet(version.toString())
}

downloadDetektConfig.download()

detekt {
    buildUponDefaultConfig = true
    config = rootProject.files("detekt.yml")
}

tasks.build {
    this.finalizedBy(sourceJar)
}

publishing {
    repositories {
        maven {
            name = "KotDis"

            url = if (version.toString().contains("SNAPSHOT")) {
                uri("https://maven.kotlindiscord.com/repository/maven-snapshots/")
            } else {
                uri("https://maven.kotlindiscord.com/repository/maven-releases/")
            }

            credentials {
                username = project.findProperty("kotdis.user") as String?
                    ?: System.getenv("KOTLIN_DISCORD_USER")

                password = project.findProperty("kotdis.password") as String?
                    ?: System.getenv("KOTLIN_DISCORD_PASSWORD")
            }

            version = version
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components.getByName("java"))

            artifact(sourceJar)
        }
    }
}
