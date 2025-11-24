package org.infinity.lib

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.number
import kotlinx.datetime.plus

private const val TAG = "DateConverter"

object DateConverter {
    private const val YEAR_AD = 2000
    private const val MONTH_AD = 1
    private const val DAY_AD = 1


    private const val YEAR_BS = 2056
    private const val MONTH_BS = 9
    private const val DAY_BS = 17

    fun adToBs(year: Int, month: Int, day: Int): DateHolder {
        val startingDate = LocalDate(YEAR_AD, MONTH_AD, DAY_AD)

        val endingDate = LocalDate(year, month, day)

        val days = startingDate.daysUntil(endingDate)

        var bsPointer = BSPointer(
            year = Year.ofValue(YEAR_BS),
            month = MONTH_BS,
            day = DAY_BS
        )


        bsPointer += days


        return DateHolder(
            year = bsPointer.year,
            month = bsPointer.month,
            day = bsPointer.day
        )

    }

    fun bsToAd(year: Int, month: Int, day: Int): DateHolder {

        val startingDay = BSPointer(
            year = Year.ofValue(YEAR_BS),
            month = MONTH_BS,
            day = DAY_BS
        )
        val endingDay = BSPointer(
            year = Year.ofValue(year), month = month, day = day
        )

        val days = endingDay - startingDay

        val startingDate = LocalDate(YEAR_AD, MONTH_AD, DAY_AD)
        val requiredData = startingDate.plus(DatePeriod(days = days))


        return DateHolder(
            year = requiredData.year,
            month = requiredData.month.number,
            day = requiredData.day
        )

    }
}

data class DateHolder(
    val year: Int,
    val month: Int,
    val day: Int
) {
    operator fun compareTo(other: DateHolder): Int {
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
}
