package com.github.p03w.shredder.tiny.data

/**
 * Keeps track of how many classes, methods, and fields have been identified.
 */
data class TinyData(
    /**
     * Current number of classes. Next class identified must be named class_[classCount]+1
     */
    val classCount: Int,

    /**
     * Current number of methods. Next class identified must be named method_[methodCount]+1
     */
    val methodCount: Int,

    /**
     * Current number of fields. Next class identified must be named field_[fieldCount]+1
     */
    val fieldCount: Int,
)
