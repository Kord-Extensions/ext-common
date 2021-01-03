package com.kotlindiscord.kordex.ext.common.extensions

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.checks.inGuild
import com.kotlindiscord.kord.extensions.checks.or
import com.kotlindiscord.kord.extensions.extensions.KoinExtension
import com.kotlindiscord.kordex.ext.common.configuration.emoji.EmojiConfig
import com.kotlindiscord.kordex.ext.common.emoji.NamedEmoji
import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.GuildEmoji
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.guild.EmojisUpdateEvent
import kotlinx.coroutines.flow.toList
import org.koin.core.component.inject

/**
 * Emoji extension, in charge of keeping track of custom emoji so you can easily retrieve them later.
 */
class EmojiExtension(bot: ExtensibleBot) : KoinExtension(bot) {
    companion object {
        private val emojis: MutableMap<String, GuildEmoji> = mutableMapOf()

        /**
         * Get an emoji mention by string name, using the default parameter if it can't be found instead.
         *
         * @param name Emoji to retrieve.
         * @param default String value to use if the emoji can't be found, defaulting to `:name:`.
         */
        fun getEmoji(name: String, default: String = ":$name:"): String =
            emojis[name]?.mention ?: default

        /**
         * Get an emoji mention by [NamedEmoji] value, using the default property if the emoji can't be found instead.
         *
         * @param emoji Emoji to retrieve.
         */
        fun getEmoji(emoji: NamedEmoji): String =
            emojis[emoji.name]?.mention ?: emoji.default
    }

    override val name: String = "emoji"

    /** Emoji config, retrieved via Koin. **/
    val config: EmojiConfig by inject()

    override suspend fun setup() {
        event<ReadyEvent> {
            action { populateEmojis() }
        }

        event<EmojisUpdateEvent> {
            check(
                or(
                    *config.getGuilds()
                        .mapNotNull { bot.kord.getGuild(it) }
                        .map { inGuild(it) }
                        .toTypedArray()
                )
            )

            action { populateEmojis(event.guildId) }
        }
    }

    private suspend fun populateEmojis(forGuildId: Snowflake? = null) {
        val emojiGuilds = config.getGuilds().mapNotNull { bot.kord.getGuild(it) }

        val guildEmojis = emojiGuilds.map { guild ->
            guild.id to guild.emojis.toList()
        }.toMap()

        if (forGuildId != null) {
            emojis.entries.removeAll { (_, emoji) -> emoji.guildId != forGuildId }
        } else {
            emojis.clear()
        }

        emojiGuilds.map { it.id }.forEach { guildId ->
            if (forGuildId == null || forGuildId == guildId) {
                guildEmojis[guildId]!!.forEach {
                    if (it.name != null && !emojis.containsKey(it.name)) {
                        emojis[it.name!!] = it
                    }
                }
            }
        }
    }
}