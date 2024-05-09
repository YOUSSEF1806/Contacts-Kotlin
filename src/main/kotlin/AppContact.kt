package com.youyou

object AppContact {
    private var contact = Contact()

    fun launch() {
        contact = Contact.fromUserInput()
        println("A record created!")
        println("A Phone Book with a single record created!")
    }
}