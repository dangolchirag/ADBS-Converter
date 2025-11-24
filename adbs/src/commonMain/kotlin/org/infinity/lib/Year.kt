package org.infinity.lib

import kotlin.jvm.JvmInline

@JvmInline
value class Year(
    val yearIndex: Int
) {
    companion object {
        fun ofValue(year: Int) = Year(BSPointer.getYearIndex(year))
        fun ofIndex(yearIndex: Int) = Year(yearIndex)
    }
}
