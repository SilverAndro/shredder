package com.github.p03w.shredder.extractor.data

import com.github.p03w.shredder.common.data.Field

/**
 * A data structure representing a field in the intermediate stage between an official and tiny field.
 */
data class IntermediaryField(
    override val type: String,

    override val intermediaryName: String?,

    override val officialName: String,

    override val definition: ByteArray,
) : Field(), IIntermediate {
    /**
     * There is no single name for an intermediary object, therefore the name is always null
     */
    override val name: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IntermediaryField) return false

        if (type != other.type) return false
        if (intermediaryName != other.intermediaryName) return false
        if (officialName != other.officialName) return false
        if (!definition.contentEquals(other.definition)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + (intermediaryName?.hashCode() ?: 0)
        result = 31 * result + officialName.hashCode()
        result = 31 * result + definition.contentHashCode()
        return result
    }
}
