package com.youyou

import kotlinx.datetime.*

open class Contact(
    private var name: String
) {
    private var phone: String = ""
    private val timeCreated: Instant = Clock.System.now()
    private var lastEditTime: Instant = Clock.System.now()

    private fun checkValidPhone(phone: String): Boolean {
        val regexPhoneFormat1 = Regex("""^\+?([0-9a-zA-Z]+)?\s?-?([0-9a-zA-Z]{2,}\s?-?)*$""")
        val regexPhoneFormat2 = Regex("""^\+?(\([0-9a-zA-Z]+\))\s?-?([0-9a-zA-Z]{2,}\s?-?)*$""")
        val regexPhoneFormat3 = Regex("""^\+?([0-9a-zA-Z]+)\s?-?(\([0-9a-zA-Z]{2,}\))\s?-?([0-9a-zA-Z]{2,}\s?-?)*$""")
        val parenthesesCheck = Regex("""\(.+\).*\(.+\)""")
        return phone.matches(regexPhoneFormat1)
            .or(phone.matches(regexPhoneFormat2))
            .or(phone.matches(regexPhoneFormat3))
            .and(!phone.matches(parenthesesCheck))
    }

    open fun editContact(): Boolean {
        when (this) {
            is Organization -> return this.editContact()
            is Person -> return this.editContact()
        }
        return false
    }

    open fun printInfo() {
        when (this) {
            is Organization -> this.printInfo()
            is Person -> this.printInfo()
        }
    }

    fun updated() {
        lastEditTime = Clock.System.now()
    }

    fun hasNumber(): Boolean = this.phone != ""

    fun setPhone(phone: String) {
        this.phone = if (checkValidPhone(phone))
            phone
        else
            ""
    }

    fun getPhone(): String = phone

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String = name

    fun getTimeCreated(): Instant = timeCreated
    fun getLastEditTime(): Instant = lastEditTime

    companion object {
        fun new(): Contact? {
            val typeEntry = getUserResponse("Enter the type (person, organization): ")
            return when (typeEntry) {
                "person" -> Person.new()
                "organization" -> Organization.new()
                else -> null
            }
        }
    }
}
