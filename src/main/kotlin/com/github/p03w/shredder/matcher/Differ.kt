package com.github.p03w.shredder.matcher

/**
 * Computes the Levenshtein Distance of 2 byte arrays
 *
 * Note that ideally you should check for identical data *before* passing it here
 *
 * Also doesn't check for every case (i.e empty data)
 */
fun levenshteinOfByteArrays(dataA: ByteArray, dataB: ByteArray): Int {
    val aSize = dataA.size + 1
    val bSize = dataB.size + 1

    var cost = IntArray(aSize) { it }
    var newCost = IntArray(aSize)

    for (i in 1 until bSize) {
        newCost[0] = i

        for (j in 1 until aSize) {
            val match = if(dataA[j - 1] == dataB[i - 1]) 0 else 1

            val costReplace = cost[j - 1] + match
            val costInsert = cost[j] + 1
            val costDelete = newCost[j - 1] + 1

            newCost[j] = minOf(costInsert, costDelete, costReplace)
        }

        val swap = cost
        cost = newCost
        newCost = swap
    }

    return cost[aSize - 1]
}
