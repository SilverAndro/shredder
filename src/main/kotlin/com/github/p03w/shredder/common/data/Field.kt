package com.github.p03w.shredder.common.data

/**
 * A data structure that represents a field in a class
 */
abstract class Field {
    /**
     * The name of the field
     */
    protected abstract val name: String?

    /**
     * The type of the field
     */
    abstract val type: String
}
