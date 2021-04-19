package com.github.p03w.shredder.common.data

/**
 * A data structure representing a method in a class
 */
abstract class Method {
    /**
     * The name of the method
     */
    protected abstract val name: String?

    /**
     * An ordered list of the types of each parameter of the method
     */
    abstract val parameters: List<String>

    /**
     * The return type of the method
     */
    abstract val returnType: String
}
