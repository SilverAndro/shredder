package com.github.p03w.shredder.matcher

import com.github.p03w.shredder.extractor.ClassFileEntry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.MessageDigest
import kotlin.math.abs

class Matcher {
    suspend fun match(origClasses: List<ClassFileEntry>, newClasses: List<ClassFileEntry>): Map<ClassFileEntry, ClassFileEntry> {
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
        val unsureClasses = mutableListOf<ClassFileEntry>()

        println("Shredding changed classes...")
        val badMatcher = GlobalScope.launch {
            origClasses.forEach { orig ->
                if (orig.name !in identicalClasses.keys) {
                    launch {
                        var closest: ClassFileEntry? = null
                        var closestScore = Int.MAX_VALUE

                        for (new in newClasses) {
                            // Quit early if known
                            if (new.name in identicalClasses.values) continue

                            val distance = levenshteinOfByteArrays(orig.data, new.data)
                            if (distance < closestScore) {
                                closest = new
                                closestScore = distance
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

        // TODO: do matcher return stuff
        return mapOf()
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
}
