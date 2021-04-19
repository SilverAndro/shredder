package com.github.p03w.shredder.tiny.data

/**
 * Provides an official name to match to the implementing object's given name
 */
interface ITiny {
    /**
     * The name according to the .class file in the shredded jar
     */
    val official: String
}
