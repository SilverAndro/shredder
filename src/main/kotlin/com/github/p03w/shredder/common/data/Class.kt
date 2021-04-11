package com.github.p03w.shredder.common.data

abstract class Class {
    abstract val name: String

    abstract val methods: List<Method>

    abstract val fields: List<Field>
}
