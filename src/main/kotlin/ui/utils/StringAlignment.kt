package org.qudus.squad.ui.utils

object StringAlignment {
   fun String.center(width: Int): String {
        if (this.length >= width) return this
        val left = (width - this.length) / 2
        val right = width - this.length - left
        return " ".repeat(left) + this + " ".repeat(right)
    }
}