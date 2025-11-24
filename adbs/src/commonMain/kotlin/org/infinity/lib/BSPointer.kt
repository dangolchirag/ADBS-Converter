package org.infinity.lib

import kotlin.math.floor
import kotlin.math.sign


class BSPointer(
    year: Year,
    val month: Int,
    val day: Int
) {

    val yearIndex = year.yearIndex

    val year: Int
        get() = getYear(yearIndex = yearIndex)

    init {
        check(month in 1..12) {
            "Month, $month should be between 1 and 12."
        }
        check(this.year in BS_DATES.first()[0]..BS_DATES.last()[0]) {
            "Invalid Year."
        }
    }


    private tailrec fun find(remainingDays: Int, monthOffset: Int): BSPointer {
        val (yearIndex, monthIndex) = addMonthToPointer(monthOffset)
        val numOfDaysInMonth = getNumOfDaysInMonth(Year.ofIndex(yearIndex), monthIndex)
        return if (remainingDays in (-numOfDaysInMonth + 1)..numOfDaysInMonth) {
            BSPointer(
                year = Year.ofIndex(yearIndex),
                month = monthIndex,
                day = if (remainingDays <= 0) {
                    numOfDaysInMonth + remainingDays
                } else {
                    remainingDays
                }
            )
        } else {
            val sign = remainingDays.sign
            find(
                remainingDays - (numOfDaysInMonth * sign),
                monthOffset + sign
            )
        }
    }


    operator fun plus(value: Int): BSPointer {
        val remainingDays = day + value
        val monthOffset = if (remainingDays <= 0) {
            -1
        } else {
            0
        }
        return find(remainingDays, monthOffset)
    }


    private fun addMonthToPointer(monthToAdd: Int): Pair<Int, Int> {
        val offset = (month - 1) + monthToAdd
        return Pair(
            first = yearIndex + floor(offset / 12f).toInt(),
            second = ((offset % 12) + 12) % 12 + 1
        )
    }

    fun getPointedValue(monthToAdd: Int): Int {
        val (yearIndex, monthIndex) = addMonthToPointer(monthToAdd)
        return getNumOfDaysInMonth(Year.ofIndex(yearIndex), monthIndex)
    }

    operator fun minus(value: Int): BSPointer {
        return plus(-value)
    }


    operator fun minus(other: BSPointer): Int {
        val shouldBePositive = this > other
        val (low, high) = if (shouldBePositive) other to this else this to other

        var monthOffset = 0
        var days = 0

        while (low.addMonthToPointer(monthOffset) != high.addMonthToPointer(0)) {
            days += low.getPointedValue(monthOffset)
            monthOffset += 1
        }

        return (days - low.day + high.day) * if (shouldBePositive) 1 else -1

    }

    operator fun compareTo(other: BSPointer): Int {
        return if (this.year == other.year) {
            if (this.month == other.month) {
                this.day.compareTo(other.day)
            } else {
                this.month.compareTo(other.month)
            }
        } else {
            this.year.compareTo(other.year)
        }
    }

    override fun toString(): String {
        return "Year = $year,  Month = $month, Day = $day"
    }

    companion object {
        fun getYear(yearIndex: Int) = BS_DATES[yearIndex][0]

        fun getNumOfDaysInMonth(year: Year, month: Int) =
            BS_DATES[year.yearIndex][month]

        fun getNumberOfAvailableMonths() = BS_DATES.size * 12

        fun getYearIndex(year: Int) = BS_DATES.indexOfFirst { it[0] == year }

        fun getYearAndMonth(position: Int): Pair<Int, Int> {
            val yearIndex = position / 12
            val month = (position % 12) + 1
            return yearIndex to month
        }

        fun getPageIndex(year: Year, month: Int) = year.yearIndex * 12 + month - 1

        fun getFirstDay() = Triple(
            BS_DATES.first()[0],
            1,
            1
        )

        fun getLastDay() = Triple(
            BS_DATES.last()[0],
            12,
            BS_DATES.last().last()
        )

        fun getYearRange(): IntRange {
            return IntRange(BSPointer.getFirstDay().first, BSPointer.getLastDay().first)
        }


    }


}
