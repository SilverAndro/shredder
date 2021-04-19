package com.github.p03w.shredder.extractor.data

/**
 * Provides an intermediary name and an official name. Also provides a ByteArray definition of the object pulled from
 * the jar.
 */
interface IIntermediate {
    /**
     * The immutable and unique name assigned to the object based on matching
     */
    val intermediaryName: String?

    /**
     * The name according to the .class file in the shredded jar
     */
    val officialName: String

    /**
     * The ByteArray that represents this object in the shredded jar
     */
    val definition: ByteArray
}
