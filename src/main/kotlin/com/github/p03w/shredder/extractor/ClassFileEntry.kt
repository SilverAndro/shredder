package com.github.p03w.shredder.extractor

import com.github.p03w.shredder.common.EntryType

/**
 * A data class that represents the data from a JarEntry
 *
 * @param name the name of the entry
 * @param type the [EntryType] the entry is from
 * @param data the bytes that make up the entry
 */
data class ClassFileEntry(val name: String, val type: EntryType, val data: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClassFileEntry

        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "ClassFileEntry[name=$name, type=$type]"
    }
}
