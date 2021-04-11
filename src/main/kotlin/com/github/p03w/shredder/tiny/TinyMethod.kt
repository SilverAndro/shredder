package com.github.p03w.shredder.tiny

import com.github.p03w.shredder.common.data.Method

data class TinyMethod(
    override val name: String,

    override val type: String,

    val official: String,
) : Method()
