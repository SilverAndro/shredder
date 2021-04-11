package com.github.p03w.shredder.tiny

import com.github.p03w.shredder.common.data.Field

data class TinyField(
    override val name: String,

    override val type: String,

    val official: String,
) : Field()
