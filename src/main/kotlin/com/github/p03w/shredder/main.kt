package com.github.p03w.shredder

import com.github.p03w.shredder.common.EntryType
import com.github.p03w.shredder.extraction.classFilesFromJar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

suspend fun main() {
    val origJar = "jars/1.16.jar"
    val newJar = "jars/1.16.1.jar"

    val deferredOrig = GlobalScope.async {
        classFilesFromJar(origJar, EntryType.ORIGINAL)
    }
    val deferredNew = GlobalScope.async {
        classFilesFromJar(newJar, EntryType.NEW)
    }

    val origClasses = deferredOrig.await()
    val newClasses = deferredNew.await()
}
