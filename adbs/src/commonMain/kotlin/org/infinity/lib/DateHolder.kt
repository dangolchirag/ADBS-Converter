package org.infinity.lib
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