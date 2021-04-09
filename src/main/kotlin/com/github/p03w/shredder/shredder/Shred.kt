package com.github.p03w.shredder.shredder

import com.github.p03w.shredder.extraction.ClassFileEntry

data class Shred(
    val orig: ClassFileEntry,

    val new: ClassFileEntry,

    val score: Int
)
