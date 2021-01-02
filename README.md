# Template: Module

This repository contains a template for KordEx module projects. When creating a new KordEx module project, follow
the directions:

1. Fill out `gradle.properties` as required for this module.
2. Create the `src/` directory and its subdirectories, and at least one file.
3. Fill out the rest of the README, and remove this initial section, replacing the following as required:
    * `{PACKAGE FROM GRADLE PROPS}`
    * `{NAME FROM GRADLE PROPS}`
    * `moduleName` (capitalised various ways)
4. Double-check that the workflows are set up the way you need them to be.

```markdown
# Module Name

[![Discord: Click here](https://img.shields.io/static/v1?label=Discord&message=Click%20here&color=7289DA&style=for-the-badge&logo=discord)](https://discord.gg/gjXqqCS) [![Release](https://img.shields.io/nexus/r/com.kotlindiscord.kordex.{PACKAGE FROM GRADLE PROPS}/{NAME FROM GRADLE PROPS}?nexusVersion=3&logo=gradle&color=blue&label=Release&server=https%3A%2F%2Fmaven.kotlindiscord.com&style=for-the-badge)](https://maven.kotlindiscord.com/#browse/browse:maven-releases:{PACKAGE FROM GRADLE PROPS}%2F{NAME FROM GRADLE PROPS}) [![Snapshot](https://img.shields.io/nexus/s/com.kotlindiscord.kordex.{PACKAGE FROM GRADLE PROPS}/{NAME FROM GRADLE PROPS}?logo=gradle&color=orange&label=Snapshot&server=https%3A%2F%2Fmaven.kotlindiscord.com&style=for-the-badge)](https://maven.kotlindiscord.com/#browse/browse:maven-snapshots:{PACKAGE FROM GRADLE PROPS}%2F{NAME FROM GRADLE PROPS})

Module description. This module contains/does...

# Getting Started

* **Stable repo:** `https://maven.kotlindiscord.com/repository/maven-snapshots/`
* **Snapshot repo:** `https://maven.kotlindiscord.com/repository/maven-releases/`
* **Maven coordinates:** `com.kotlindiscord.kordex.{PACKAGE FROM GRADLE PROPS}:{NAME FROM GRADLE PROPS}:VERSION`

A basic set of instructions for getting started with the module, including code examples.

# Usage

Explain how this module is used, from a user-facing perspective (eg, Discord commands) if applicable. Be sure to list
everything.

# Configuration

If your module isn't configurable, you can remove this section. Otherwise, explain how your module can be configured
(assuming the default supplied method of configuration). Most modules will be using Konf by default, so follow this
template:

* **Env var prefix:** `KORDX_MODULENAME`
* **System property prefix:** `kordx.moduleName`

This extension makes use of the Konf library for configuration. Included in the JAR is a default configuration file,
`kordex/moduleName/default.toml`. You may configure the extension in one of the following ways:

* **TOML file as a resource:** `kordex/moduleName/config.toml`
* **TOML file on the filesystem:** `config/ext/moduleName.toml`
* **Environment variables,** prefixed with `KORDX_MODULENAME_`, upper-casing keys and replacing `.` with `_` in key names
* **System properties,** prefixed with `kord.moduleName.`

For an example, feel free to [read the included default.toml](src/main/resources/kordex/moduleName/default.toml). The
following configuration keys are available:

* `category.key`: Description of exactly how to set this and what it does

**Please note:** Any relevant extra info the user will want to know about.

# Customisation

Any customisation options that are provided for developers should be documented here, each within its own section. As
an example, a custom configuration provider section is given below.

## Replacing the Config Adapter

If you need some other form of configuration - for example, from a database - you can implement the
`ModuleNameConfigAdapater` interface in your own classes and pass an instance to `ExtensibleBot.extModuleNameConfig()`
before you start the bot to use it. While going into detail on each function is a little out of scope for this
document, you can find more information in the following places:

* [ModuleNameConfigAdapter interface](src/main/kotlin/com/kotlindiscord/kordex/ext/moduleName/configuration/ModuleNameConfigAdapter.kt)
* [TomlMappingsConfig class](src/main/kotlin/com/kotlindiscord/kordex/ext/moduleName/configuration/TomlModuleNameConfig.kt)

   val bot = ExtensibleBot(
     token = System.getenv("TOKEN"),
     prefix = "!"
   )
   
   suspend fun main() {
     bot.extModuleNameConfig(CustomMappingsConfig())
   
     bot.start()
   }

# Licensing & Attribution

Any extra licensing and attribution considerations should be detailed here.
```
