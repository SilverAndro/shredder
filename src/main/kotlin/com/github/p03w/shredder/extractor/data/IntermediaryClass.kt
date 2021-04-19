package com.github.p03w.shredder.extractor.data

import com.github.p03w.shredder.common.data.Class
import com.github.p03w.shredder.common.data.Field

/**
 * A data structure representing a class in the intermediate stage between an official and tiny class.
 */
data class IntermediaryClass(
    override val methods: List<IntermediaryMethod>,

    override val fields: List<IntermediaryField>,

    override val intermediaryName: String?,

    override val officialName: String,

    override val definition: ByteArray,
) : Class(), IIntermediate {
    /**
     * There is no single name for an intermediary object, therefore the name is always null
     */
    override val name: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IntermediaryClass) return false

        if (methods != other.methods) return false
        if (fields != other.fields) return false
        if (!definition.contentEquals(other.definition)) return false
        if (intermediaryName != other.intermediaryName) return false
        if (officialName != other.officialName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = methods.hashCode()
        result = 31 * result + fields.hashCode()
        result = 31 * result + definition.contentHashCode()
        result = 31 * result + intermediaryName.hashCode()
        result = 31 * result + officialName.hashCode()
        return result
    }
}
