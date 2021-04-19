package com.github.p03w.shredder.common.data

/**
 * A data structure that represents a class
 */
abstract class Class {
    /**
     * The name of the class
     */
    protected abstract val name: String?

    /**
     * The methods of the class
     */
    abstract val methods: List<Method>

    /**
     * The fields of the class
     */
    abstract val fields: List<Field>
}
