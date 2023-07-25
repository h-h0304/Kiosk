package com.example.kiosk

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val menuManager = MenuManager()
    val MenuHandler = MenuHandler(menuManager)

    MenuHandler.handleMenu()
}
