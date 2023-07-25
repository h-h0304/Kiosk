package com.example.kiosk
import java.util.Calendar

class Banking {
    fun canProcess(): Boolean {
        val currentTime = Calendar.getInstance()

        val bankCheckStartTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 10)
        }

        val bankCheckEndTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 20)
        }

        return !currentTime.after(bankCheckStartTime) || currentTime.before(bankCheckEndTime)
    }
}