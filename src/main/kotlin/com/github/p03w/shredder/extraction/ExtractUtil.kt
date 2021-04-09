package com.github.p03w.shredder.extraction

import com.github.p03w.shredder.common.EntryType
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile

fun classFilesFromJar(pathToJar: String, type: EntryType): List<ClassFileEntry> {
    val jarFile = JarFile(pathToJar)
    val entries = jarFile.entries().toList().filter { it.name.endsWith(".class") }

    val out: MutableList<ClassFileEntry> = mutableListOf()

    for (entry in entries) {
        val raw = jarFile.getInputStream(entry).readBytes()
        val hash = raw.hashCode()
        out.add(ClassFileEntry(entry.name, type, raw, hash))
    }

    return out
}
