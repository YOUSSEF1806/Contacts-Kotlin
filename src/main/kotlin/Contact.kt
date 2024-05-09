package com.youyou

data class Contact(
    val name: String = "",
    val surname: String = "",
    val phone: String = "00-00-00-00-00"
) {
    companion object {
        fun fromUserInput() : Contact {
            println("Enter the name of the person:")
            val name = readln()
            println("Enter the surname of the person:")
            val surname = readln()
            println("Enter the number:")
            val phone = readln()
            return Contact(name, surname, phone)
        }
    }
}