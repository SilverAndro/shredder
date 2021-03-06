package com.github.p03w.shredder.shredder

import com.github.p03w.shredder.common.EntryType
import com.github.p03w.shredder.extraction.ClassFileEntry
import com.github.p03w.shredder.extraction.classFilesFromJar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.security.MessageDigest
import kotlin.math.abs

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
    println("${origClasses.size} classes in $origJar")
    val newClasses = deferredNew.await()
    println("${newClasses.size} classes in $newJar")
    println()

    val identicalClasses = mutableMapOf<String, String>()
    val changedClasses = mutableMapOf<String, String>()

    println("Shredding identical classes...")
    origClasses.forEach { orig ->
        for (new in newClasses) {
            if (compareBytes(orig.data, new.data)) {
                identicalClasses[orig.name] = new.name
                break
            }
        }
    }
    println("Found ${identicalClasses.size} identical classes\n")

    val deletedClasses = mutableListOf<String>()
    val unsureClasses = mutableMapOf<ClassFileEntry, ClassFileEntry>()

    println("Shredding changed classes...")
    val badMatcher = GlobalScope.launch {
        origClasses.forEach { orig ->
            if (orig.name !in identicalClasses.keys) {
                launch {
                    var closest: ClassFileEntry? = null
                    var closestScore = Int.MAX_VALUE

                    for (new in newClasses) {
                        var currentScore = 0
                        for (i in 8 until Integer.min(orig.data.size, new.data.size)) { // skip bytes 0-7, jar data
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
                    if (closest!!.name in identicalClasses.values) {
                        deletedClasses.add(orig.name)
                    } else {
                        changedClasses[orig.name] = closest.name
                    }
                }
            }
        }
    }
    badMatcher.join()
    println("Found ${changedClasses.size} changed classes")
    println("Found ${deletedClasses.size} deleted classes")

    val addedClasses = newClasses.filter {
        it.name !in identicalClasses.values && it.name !in changedClasses.values
    }
    println("Found ${addedClasses.size} new classes")
}

private fun compareBytes(a: ByteArray, b: ByteArray) =
    a.size == b.size &&
    MessageDigest.getInstance("MD5").digest(a).let { md5 ->
        val c: Long = md5[0] * 256L * md5[1] + 256 * 256 * md5[2] + 256 * 256 * 256 * md5[3]
        val d: Long = md5[4] * 256L * md5[5] + 256 * 256 * md5[6] + 256 * 256 * 256 * md5[7]
        c xor d
    } == MessageDigest.getInstance("MD5").digest(b).let { md5 ->
        val c: Long = md5[0] * 256L * md5[1] + 256 * 256 * md5[2] + 256 * 256 * 256 * md5[3]
        val d: Long = md5[4] * 256L * md5[5] + 256 * 256 * md5[6] + 256 * 256 * 256 * md5[7]
        c xor d
    }
