pluginManagement {
    val detektVersion: String by settings
    val downloadVersion: String by settings
    val kotlinVersion: String by settings

    plugins {
        id("de.undercouch.download") version downloadVersion
        id("io.gitlab.arturbosch.detekt") version detektVersion

        kotlin("jvm") version kotlinVersion
    }
}

val moduleName: String by settings

rootProject.name = moduleName
