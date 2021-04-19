package com.github.p03w.shredder.extractor.data

import com.github.p03w.shredder.common.data.Method

/**
 * A data structure representing a method in the intermediate stage between an official and tiny method.
 */
data class IntermediaryMethod(
    override val parameters: List<String>,

    override val returnType: String,

    override val intermediaryName: String?,

    override val officialName: String,

    override val definition: ByteArray,
) : Method(), IIntermediate {
    /**
     * There is no single name for an intermediary object, therefore the name is always null
     */
    override val name: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IntermediaryMethod) return false

        if (parameters != other.parameters) return false
        if (returnType != other.returnType) return false
        if (intermediaryName != other.intermediaryName) return false
        if (officialName != other.officialName) return false
        if (!definition.contentEquals(other.definition)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = parameters.hashCode()
        result = 31 * result + returnType.hashCode()
        result = 31 * result + (intermediaryName?.hashCode() ?: 0)
        result = 31 * result + officialName.hashCode()
        result = 31 * result + definition.contentHashCode()
        return result
    }
}
