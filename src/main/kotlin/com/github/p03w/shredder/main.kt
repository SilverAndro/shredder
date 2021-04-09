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
import kotlin.system.measureTimeMillis

@OptIn(ObsoleteCoroutinesApi::class)
suspend fun main() {
    val time = measureTimeMillis {
        shred()
    }
    println("Finished in ${time * .001} seconds")
}

suspend fun shred() {
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
                var closestScore = Int.MAX_VALUE

                for (new in newClasses) {
                    if (orig.hash == new.hash) {
                        closest = new
                        closestScore = 0
                        break
                    }

                    var currentScore = 0
                    for (i in 0 until min(orig.data.size, new.data.size)) {
                        if (orig.data[i] != new.data[i]) {
                            currentScore++
                        }
                    }

                    currentScore += abs(orig.data.size - new.data.size)

                    if (currentScore < closestScore) {
                        closest = new
                        closestScore = currentScore
                    }
                }

                println("$closestScore | $orig -> $closest")
            }
        }
    }
    badMatcher.join()
}
