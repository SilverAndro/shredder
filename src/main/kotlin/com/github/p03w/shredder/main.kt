package com.github.p03w.shredder

import com.github.p03w.shredder.common.EntryType
import com.github.p03w.shredder.extractor.Extractor
import com.github.p03w.shredder.matcher.Matcher
import com.github.p03w.shredder.tiny.Builder
import com.github.p03w.shredder.tiny.Mapper
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlin.system.measureTimeMillis

@OptIn(ObsoleteCoroutinesApi::class)
suspend fun main() {
    // User input would be here, for source and target version names
    val v1 = "1.16"
    val v2 = "1.16.1"
    println("========< Shredding >========")
    val time = measureTimeMillis {
        val extractor = Extractor()
        val source = extractor.extractClasses("jars/$v1-merged.jar", EntryType.ORIGINAL)
        println("${source.size} classes in $v1")
        val target = extractor.extractClasses("jars/$v2-merged.jar", EntryType.NEW)
        println("${target.size} classes in $v2")
        println()

        val mapper = Mapper()
        val remappedSource = mapper.map(source, extractor.extractTiny("jars/$v1.tiny"))

        val matcher = Matcher()
        val matches = matcher.match(remappedSource, target)

        // here there would be the ui stuff where user manually maps any unsure matches

        val builder = Builder()
        val tinyData = builder.makeTiny(matches)
    }
    println("=============================")
    println("Finished in ${time * .001} seconds")
}
