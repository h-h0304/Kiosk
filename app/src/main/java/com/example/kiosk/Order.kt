package com.example.kiosk

class Order(private val cart: Cart) {
    private var orderCount = 0

    fun calculateTotalPrice(): Double {
        var totalPrice = 0.0
        cart.items.forEach { item ->
            totalPrice += item.price
        }
        return totalPrice
    }
    fun printOrder() {
        println("[ Orders ]")
        cart.items.forEach { item ->
            println("${item.name}     | ${String.format("%.1f", item.price)} | ${item.description}")
        }
        println("[ Total ]")
        println("W ${String.format("%.1f", calculateTotalPrice())}")
    }
    fun hasSufficientBalance(currentBalance: Double): Boolean {
        return currentBalance >= calculateTotalPrice()
    }
    fun processOrder(currentBalance: Double) {
        if (hasSufficientBalance(currentBalance)) {
            printOrder()
            println("주문이 완료되었습니다. 잔돈은 ${String.format("%.1f",(currentBalance - calculateTotalPrice()))} W 입니다.")
            orderCount++
            cart.items.clear()
        } else {
            val amount = calculateTotalPrice() - currentBalance
            println("현재 잔액은 ${String.format("%.1f", currentBalance)} W으로, ${String.format("%.1f", amount)} W이 부족해서 주문할 수 없습니다.")
        }
    }
    fun onOrderCancelled() {
        if (orderCount > 0) {
            orderCount--
        }
    }
    fun getNumberOfWaitingOrders(): Int {
        return orderCount
    }
}