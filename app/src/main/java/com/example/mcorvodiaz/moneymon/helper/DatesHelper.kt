package com.example.mcorvodiaz.moneymon.helper

import java.text.SimpleDateFormat
import java.util.*

class DatesHelper {
    companion object {
        fun parseToLong(stringDate: String): Long {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val date = formatter.parse(stringDate)
            val formatter2 = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
            val numDate = formatter2.format(date)
            return numDate.toLong()
        }

        fun parseToString(longDate: Long): String {
            val formatter2 = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
            val numDate = formatter2.parse(longDate.toString())
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            return formatter.format(numDate)
        }

        fun today(): Long {
            val date = Date()
            val formatter2 = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
            val numDate = formatter2.format(date)
            return numDate.toLong()
        }
    }
}