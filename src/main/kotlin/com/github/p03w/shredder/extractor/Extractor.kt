package com.github.p03w.shredder.extractor

import com.github.p03w.shredder.common.EntryType
import com.github.p03w.shredder.tiny.data.TinyClass
import java.util.jar.JarFile

class Extractor {
    fun extractClasses(pathToJar: String, type: EntryType): List<ClassFileEntry> {
        val jarFile = JarFile(pathToJar)
        val entries = jarFile.entries().toList().filter { it.name.endsWith(".class") }

        val out: MutableList<ClassFileEntry> = mutableListOf()

        for (entry in entries) {
            val raw = jarFile.getInputStream(entry).readBytes()
            out.add(ClassFileEntry(entry.name, type, raw))
        }

        return out
    }

    fun extractTiny(pathToTiny: String): List<TinyClass> {
        // TODO: Implement tiny extraction
        return listOf()
    }
}
