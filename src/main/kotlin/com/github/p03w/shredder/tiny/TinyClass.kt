package com.github.p03w.shredder.tiny

data class TinyClass(
    val officialName: String,

    val tinyName: String,

    val methods: List<TinyMethod>,

    val fields: List<TinyField>,
)
