package com.kotlindiscord.kordex.ext.common

import com.kotlindiscord.kord.extensions.builders.ExtensibleBotBuilder
import com.kotlindiscord.kordex.ext.common.builders.ExtCommonBuilder
import com.kotlindiscord.kordex.ext.common.extensions.EmojiExtension

/** Configure the common module and add its extensions to the bot. **/
fun ExtensibleBotBuilder.ExtensionsBuilder.extCommon(builder: ExtCommonBuilder.() -> Unit) {
    val obj = ExtCommonBuilder()

    builder(obj)
    EmojiExtension.configure(obj)

    add(::EmojiExtension)
}
