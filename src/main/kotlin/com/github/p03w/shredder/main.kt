package com.github.p03w.shredder

import com.github.p03w.shredder.common.EntryType
import com.github.p03w.shredder.extraction.ClassFileEntry
import com.github.p03w.shredder.extraction.classFilesFromJar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Integer.min
import kotlin.math.abs

@OptIn(ObsoleteCoroutinesApi::class)
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

    val badMatcher = GlobalScope.launch {
        origClasses.forEach { orig ->
            launch {
                var closest: ClassFileEntry? = null
                var minScore = Int.MAX_VALUE
                newClasses.forEach { new ->
                    var score = 0
                    for (i in 0 until min(orig.data.size, new.data.size)) {
                        if (orig.data[i] != new.data[i]) {
                            score++
                        }
                    }

                    score += abs(orig.data.size - new.data.size)

                    if (score < minScore) {
                        closest = new
                        minScore = score
                    }
                }
                println("$minScore | $orig -> $closest")
            }
        }
    }
    badMatcher.join()
}
