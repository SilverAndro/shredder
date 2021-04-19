package com.github.p03w.shredder.tiny.data

import com.github.p03w.shredder.common.data.Method

/**
 * A method with intermediary names, as well as its official name
 *
 * Note that only this method's name will be in intermediary, and its [parameters] and [return type][returnType] keep
 * their official names
 */
data class TinyMethod(
    public override val name: String,

    override val parameters: List<String>,

    override val returnType: String,

    override val official: String,
) : Method(), ITiny
