package com.github.p03w.shredder.tiny

import com.github.p03w.shredder.common.data.Class

data class TinyClass(
    override val name: String,

    override val methods: List<TinyMethod>,

    override val fields: List<TinyField>,

    val official: String,
) : Class()
