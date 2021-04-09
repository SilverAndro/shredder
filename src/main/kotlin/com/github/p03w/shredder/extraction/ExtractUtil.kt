package com.github.p03w.shredder.extraction

import com.github.p03w.shredder.common.EntryType
import java.net.URL
import java.net.URLClassLoader
import java.security.MessageDigest
import java.util.jar.JarFile

fun classFilesFromJar(pathToJar: String, type: EntryType): List<ClassFileEntry> {
    val jarFile = JarFile(pathToJar)
    val entries = jarFile.entries().toList().filter { it.name.endsWith(".class") }

    val out: MutableList<ClassFileEntry> = mutableListOf()

    for (entry in entries) {
        val raw = jarFile.getInputStream(entry).readBytes()
        val digested = MessageDigest.getInstance("MD5").digest(raw)
        /* val hash = StringBuilder(digested.size * 2).let { sb ->
            digested.forEach { sb.append(String.format("%02X", it)) }
            sb.toString()
        } */
        val hashL = digested.let { md5 ->
            val a: Long = md5[0] * 256L * md5[1] + 256 * 256 * md5[2] + 256 * 256 * 256 * md5[3]
            val b: Long = md5[4] * 256L * md5[5] + 256 * 256 * md5[6] + 256 * 256 * 256 * md5[7]
            a xor b
        }
        out.add(ClassFileEntry(entry.name, type, raw, hashL))
    }

    return out
}
