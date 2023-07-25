package com.example.kiosk

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Calendar
import java.text.SimpleDateFormat

class MenuHandler(private val menuManager: MenuManager) {
    private val cart = Cart()
    private val order = Order(cart)
    private val currentBalance = 20.0 // 잔액을 설정
    private val cancel = Cancel(cart) // Cancel 객체 생성
    private val delay = Delay()
    private val banking = Banking()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @RequiresApi(Build.VERSION_CODES.O)
    fun handleMenu() {
        var menuChoice: Int
        var menuChoiceCategory: String
        val mainMenu = MainMenu()

        outer@do {
            mainMenu.display()
            val input = readLine()?.toIntOrNull()
            menuChoice = input ?: -1
            inner@when (menuChoice) {
                1, 2, 3, 4 -> {
                    menuChoiceCategory = when (menuChoice) {
                        1 -> "COFFEE & ESPRESSO"
                        2 -> "FRAPPUCCINO & BLENDED"
                        3 -> "TEAVANA & COFFEE"
                        4 -> "COLD BREW & CHOCOLATE"
                        else -> ""
                    }
                    menuManager.displayMenu(menuChoiceCategory)
                    val beverageChoice = readLine()?.toIntOrNull()
                    if (beverageChoice != null) {
                        when (beverageChoice) {
                            0 -> println("뒤로 가기")
                            in 1..menuManager.getCategorySize(menuChoiceCategory) -> {
                                val menuItem = menuManager.findByIdAndCategory(beverageChoice, menuChoiceCategory)
                                menuItem?.let {
                                    println("${it.name}     | ${it.price} | ${it.description}")
                                    println("위 메뉴를 장바구니에 추가하시겠습니까?")
                                    println("1. 확인        2. 취소")
                                    val cartChoice = readLine()?.toIntOrNull()
                                    if (cartChoice == 1) {
                                        cart.addItem(it)
                                        println("${it.name} 가 장바구니에 추가되었습니다.")
                                    }
                                } ?: println("잘못된 번호를 입력하셨습니다.")
                            }
                            else -> println("잘못된 번호를 입력하셨습니다.")
                        }
                    } else {
                        println("잘못된 입력입니다.")
                    }
                }
                5 -> {
                    order.printOrder()
                    println("1. 주문     2. 메뉴판")
                    println("아래와 같이 주문 하시겠습니까? (현재 주문 대기 수: ${order.getNumberOfWaitingOrders()})")
                    val userChoice = readLine()?.toIntOrNull()
                    if (userChoice == 1){
                        if (banking.canProcess()) {
                            order.processOrder(currentBalance)
                            delay.execute { println("결제가 완료되었습니다. (${dateFormat.format(Calendar.getInstance().time)})") }
                        } else {
                            println("현재 시각은 ${dateFormat.format(Calendar.getInstance().time)}입니다.")
                            println("은행 점검 시간은 오후 11시 10분 ~ 오후 11시 20분이므로 결제할 수 없습니다.")
                        }
                    }else if(userChoice == 2){
                        continue@outer
                    }else{
                        println("잘못된 입력입니다.")
                        continue@outer
                    }
                }
                6 -> {
                    println("진행 중인 주문을 취소하시겠습니까?")
                    println("1. 전체 취소        2. 품목별 취소     3. 취소")
                    val cancelChoice = readLine()?.toIntOrNull()

                    when (cancelChoice) {
                        1 -> {
                            cancel.cancelAll()
                            order.onOrderCancelled()  // 전체 주문 취소 시 호출
                        }
                        2 -> {
                            cart.items.forEachIndexed { index, item ->
                                println("${index + 1}. ${item.name} | ${item.price} | ${item.description}")
                            }
                            println("취소할 항목의 번호를 입력하세요 (1 이상 ${cart.items.size} 이하)")
                            val cancelIndex = readLine()?.toIntOrNull()
                            cancelIndex?.let {
                                cancel.cancelByIndex(it - 1)
                                order.onOrderCancelled()  // 품목별 주문 취소 시 호출
                            }
                        }
                        else -> {
                            println("취소 여부를 취소하셨습니다.")
                        }
                    }
                }
                0 -> println("프로그램을 종료합니다.")
                else -> println("잘못된 번호를 입력했어요. 다시 입력해주세요.")
            }
        } while (menuChoice != 0)
    }
}
