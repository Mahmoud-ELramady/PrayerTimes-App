package com.elramady.prayertimes.common

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.elramady.prayertimes.domain.models.Timings
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {


    fun getYearFromDate(date: Date): String {
        val yearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
        return yearFormat.format(date)
    }

    fun getMonthFromDate(date: Date): String {
        val monthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
        return monthFormat.format(date)
    }

    fun getDayFromDate(date: Date): String {
        val dayFormat = SimpleDateFormat("dd", Locale.ENGLISH)
        return dayFormat.format(date)
    }


    fun getFormattedDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        return dateFormat.format(date)
    }

     fun getCurrentDate():Date{
        val currentDate = Date()
         return currentDate
    }

    fun getCurrentMonthNumber(): Int {
        val calendar = Calendar.getInstance() // Get an instance of the Calendar
        return calendar.get(Calendar.MONTH) + 1 // Get the month (0-11) and add 1 to make it (1-12)
    }

    fun convertTo12HourFormat(time: String): String {
        // Regular expression to match the input format "HH:mm (timezone)"
        val regex = Regex("""^(\d{1,2}):(\d{2}) \((\w+)\)$""")
        val matchResult = regex.find(time)

        return if (matchResult != null) {
            val (hourString, minuteString, timezone) = matchResult.destructured

            // Convert the extracted hour and minute to integers
            val hour = hourString.toInt()
            val minute = minuteString.toInt()

            // Validate the hour and minute ranges
            if (hour in 0..23 && minute in 0..59) {
                // Convert to 12-hour format
                val amPm = if (hour < 12) "AM" else "PM"
                val hour12 = if (hour % 12 == 0) 12 else hour % 12

                // Format the result
                String.format("%02d:%02d %s", hour12, minute, amPm)
            } else {
                "Invalid time format"
            }
        } else {
            "Invalid time format"
        }
    }



    fun getNextPrayer(prayerTimes: Map<String, String?>): Pair<String, Long> {
        // Get the current time
        val currentTime = Calendar.getInstance()

        // Time format without the (BST)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        var nextPrayerName: String? = null
        var nextPrayerTime: Calendar? = null
        var minTimeDiff: Long = Long.MAX_VALUE

        // Iterate through the prayer times
        for ((prayerName, prayerTimeStr) in prayerTimes) {
            val timeStr = prayerTimeStr?.substringBefore(" ") // Remove the (BST) part
            val prayerTime = Calendar.getInstance()

            // Parse the prayer time
            prayerTime.time = timeFormat.parse(timeStr)

            // Set the time of the prayer in the current day
            prayerTime.set(Calendar.YEAR, currentTime.get(Calendar.YEAR))
            prayerTime.set(Calendar.MONTH, currentTime.get(Calendar.MONTH))
            prayerTime.set(Calendar.DAY_OF_MONTH, currentTime.get(Calendar.DAY_OF_MONTH))

            // If the prayer time has already passed today, move it to the next day
            if (prayerTime.before(currentTime)) {
                prayerTime.add(Calendar.DAY_OF_MONTH, 1)
            }

            // Calculate the time difference between the current time and the prayer time
            val timeDiff = prayerTime.timeInMillis - currentTime.timeInMillis

            // Find the minimum time difference
            if (timeDiff < minTimeDiff) {
                minTimeDiff = timeDiff
                nextPrayerName = prayerName
                nextPrayerTime = prayerTime
            }
        }

        // Return the next prayer name and the time left (in milliseconds)
        return Pair(nextPrayerName ?: "No Prayer", minTimeDiff)
    }

    // Utility function to convert milliseconds to a readable format
    @SuppressLint("DefaultLocale")
    fun formatTimeLeft(timeInMillis: Long): String {
        val minutes = (timeInMillis / (1000 * 60)) % 60
        val hours = (timeInMillis / (1000 * 60 * 60)) % 24
        return String.format("%02d hours %02d minutes", hours, minutes)
    }



}