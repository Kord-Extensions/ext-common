package com.kotlindiscord.kordex.ext.common

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kordex.ext.common.configuration.emoji.EmojiConfig
import com.kotlindiscord.kordex.ext.common.configuration.emoji.TomlEmojiConfig
import com.kotlindiscord.kordex.ext.common.extensions.EmojiExtension
import org.koin.dsl.bind
import org.koin.dsl.module

/** Set up the emoji extension and add it to the bot. **/
fun ExtensibleBot.extEmoji() {
    val config = koin.getOrNull<EmojiConfig>()

    if (config == null) {
        extEmojiConfig(TomlEmojiConfig())
    }

    this.addExtension(EmojiExtension::class)
}

/**
 * Define the emoji extension configuration using a custom implementation.
 *
 * Be sure to call this before [extEmoji], or an exception will be thrown!
 */
@Throws(IllegalStateException::class)
fun ExtensibleBot.extEmojiConfig(config: EmojiConfig) {
    val configObj = koin.getOrNull<EmojiConfig>()

    if (configObj != null) {
        error(
            "Emoji extension is already configured - make sure you call extEmojiConfig before extMappings!"
        )
    }

    val configModule = module {
        single { config } bind EmojiConfig::class
    }

    koin.loadModules(listOf(configModule))
}
