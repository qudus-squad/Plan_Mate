package org.qudus.squad.logic.utils

import kotlin.random.Random

class GenerateUUID {
    private fun randomHex(length: Int): String =
        List(length) { Random.nextInt(0, 16).toString(16) }.joinToString("")

    fun generate(): String =
        "${randomHex(8)}-${randomHex(4)}-${randomHex(4)}-${randomHex(4)}-${randomHex(12)}"
}