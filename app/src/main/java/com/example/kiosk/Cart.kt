package com.example.kiosk

class Cart {
    val items = mutableListOf<MenuItem>()

    fun addItem(item: MenuItem) {
        items.add(item)
    }
}