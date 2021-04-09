package com.github.p03w.shredder

import com.github.p03w.shredder.common.EntryType
import com.github.p03w.shredder.extraction.classFilesFromJar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

suspend fun main() {
    val orig = "jars/1.16.jar"
    val new = "jars/1.16.1.jar"

    val oldExtract = GlobalScope.async {
        classFilesFromJar(orig, EntryType.ORIGINAL)
    }
    val newExtract = GlobalScope.async {
        classFilesFromJar(new, EntryType.NEW)
    }

    println(oldExtract.await())
    println(newExtract.await())
}
