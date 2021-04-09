package com.github.p03w.shredder

import com.github.p03w.shredder.common.EntryType
import com.github.p03w.shredder.extraction.classFilesFromJar

fun main() {
    val orig = "jars/1.16.jar"
    val new = "jars/1.16.1.jar"

    println(classFilesFromJar(orig, EntryType.ORIGINAL))
    println(classFilesFromJar(new, EntryType.NEW))
}
