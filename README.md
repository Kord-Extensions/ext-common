# Extensions: Common Code

[![Discord: Click here](https://img.shields.io/static/v1?label=Discord&message=Click%20here&color=7289DA&style=for-the-badge&logo=discord)](https://discord.gg/gjXqqCS) [![Release](https://img.shields.io/nexus/r/com.kotlindiscord.kordex.ext.common/ext-common?nexusVersion=3&logo=gradle&color=blue&label=Release&server=https%3A%2F%2Fmaven.kotlindiscord.com&style=for-the-badge)](https://maven.kotlindiscord.com/#browse/browse:maven-releases:ext.common%2Fext-common) [![Snapshot](https://img.shields.io/nexus/s/com.kotlindiscord.kordex.ext.common/ext-common?logo=gradle&color=orange&label=Snapshot&server=https%3A%2F%2Fmaven.kotlindiscord.com&style=for-the-badge)](https://maven.kotlindiscord.com/#browse/browse:maven-snapshots:ext.common%2Fext-common)

This module contains common code shared by the other extensions modules, as well as some common utility extensions.

# Provided Extensions

* `EmojiExtension` via `bot.extEmoji()`, which exists to keep track of the custom emoji that other extensions may need.
  Be sure to add this extension to your bot if you have any other extensions that need it!

# Getting Started

* **Maven repo:** `https://maven.kotlindiscord.com/repository/maven-public/`
* **Maven coordinates:** `com.kotlindiscord.kordex.ext.common:ext-common:VERSION`

This module doesn't contain much that a user may need to interact with directly, but if you're using another module
that makes use of one of the extensions in this one, you may need to configure them as explained below.

None of the bundled extensions add any commands or user-facing components.

# Configuration: Emoji Extension

* **Env var prefix:** `KORDX_EMOJI`
* **System property prefix:** `kordx.emoji`

This extension makes use of the Konf library for configuration. Included in the JAR is a default configuration file,
`kordex/emoji/default.toml`. You may configure the extension in one of the following ways:

* **TOML file as a resource:** `kordex/emoji/config.toml`
* **TOML file on the filesystem:** `config/ext/emoji.toml`
* **Environment variables,** prefixed with `KORDX_EMOJI_`, upper-casing keys and replacing `.` with `_` in key names
* **System properties,** prefixed with `kord.emoji.`

For an example, feel free to [read the included default.toml](src/main/resources/kordex/emoji/default.toml). The
following configuration keys are available:

* `emoji.guilds`: List of guild IDs to index custom emoji from, if required - omit this or set it to an empty list and all guilds will be indexes, in the order the bot joined them in.
* `emoji.overrides`: Mapping of emoji names to guild IDs, if you need emoji with a specific name to come from a specific guild while ignoring the sort list of the indexed guilds.
