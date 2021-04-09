package com.github.p03w.shredder

import com.github.p03w.shredder.shredder.shred
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlin.system.measureTimeMillis

@OptIn(ObsoleteCoroutinesApi::class)
suspend fun main() {
    println("========< Shredding >========")
    val time = measureTimeMillis {
        shred()
    }
    println("Finished in ${time * .001} seconds")
}
