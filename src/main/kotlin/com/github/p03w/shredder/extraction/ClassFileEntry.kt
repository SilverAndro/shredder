package com.github.p03w.shredder.extraction

import com.github.p03w.shredder.common.EntryType

data class ClassFileEntry(val name: String, val type: EntryType, val data: ByteArray, val hash: Int = data.hashCode()) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClassFileEntry

        if (type != other.type) return false
        if (hash != other.hash) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + hash
        return result
    }

    override fun toString(): String {
        return "ClassFileEntry[name=$name, type=$type, hash=$hash]"
    }
}
