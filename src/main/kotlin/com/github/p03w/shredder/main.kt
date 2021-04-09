package com.github.p03w.shredder

import com.github.p03w.shredder.common.EntryType
import com.github.p03w.shredder.extraction.classFilesFromJar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main() {
    val orig = "jars/1.16.jar"
    val new = "jars/1.16.1.jar"

    val oldExtract = GlobalScope.launch {
        classFilesFromJar(orig, EntryType.ORIGINAL)
        println("A")
    }
    val newExtract = GlobalScope.launch {
        classFilesFromJar(new, EntryType.NEW)
        println("B")
    }

    oldExtract.join()
    newExtract.join()
}
