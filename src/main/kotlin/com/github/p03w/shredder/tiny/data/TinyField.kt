package com.github.p03w.shredder.tiny.data

import com.github.p03w.shredder.common.data.Field

/**
 * A field with intermediary names, as well as its official name
 *
 * Note that only this field's name will be in intermediary, and its [type] will keep its official name
 */
data class TinyField(
    public override val name: String,

    override val type: String,

    override val official: String,
) : Field(), ITiny
