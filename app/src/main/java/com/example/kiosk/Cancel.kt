package com.example.kiosk

class Cancel(private val cart: Cart) {

    fun cancelAll() {
        cart.items.clear()
        println("진행중인 주문이 모두 취소되었습니다.")
    }

    fun cancelByIndex(index: Int) {
        if (index in cart.items.indices) {
            val removedItem = cart.items.removeAt(index)
            println("${removedItem.name}이(가) 주문에서 취소되었습니다.")
        } else {
            println("잘못된 번호를 입력하셨습니다.")
        }
    }
}