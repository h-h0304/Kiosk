package com.example.kiosk
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Delay {
    fun execute(block: () -> Unit) {
        runBlocking {
            launch {
                delay(3000) // 3초 뒤
                block() // 다른 작업 수행
            }
        }
    }
}