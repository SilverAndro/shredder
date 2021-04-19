package com.github.p03w.shredder.tiny.data

import com.github.p03w.shredder.common.data.Class

/**
 * A class with intermediary names, as well as its official name
 */
data class TinyClass(
    public override val name: String,

    override val methods: List<TinyMethod>,

    override val fields: List<TinyField>,

    override val official: String,
) : Class(), ITiny
