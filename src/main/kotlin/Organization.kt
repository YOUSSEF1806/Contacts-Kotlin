package com.youyou

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class Organization(
    name: String,
    private var address: String
) : Contact(name) {

    override fun editContact(): Boolean {
        val userResponse = getUserResponse("Select a field (address, number): ")
        var wasUpdated = true
        when (userResponse) {
            "address" -> address = getUserResponse("enter surname: ")
            "number" -> setPhone(getUserResponse("enter number: "))
            else -> wasUpdated = false
        }
        if (wasUpdated) {
            updated()
            println("The record updated!")
        }
        return wasUpdated
    }

    override fun printInfo() {
        println("Organization name: ${getName()}")
        println("Address: $address")
        val phoneString = if (hasNumber()) getPhone() else "[no number]"
        println("Number: $phoneString")
        println("Time created: ${getTimeCreated().asTZDefaultDateTime()}")
        println("Time last edit: ${getLastEditTime().asTZDefaultDateTime()}")
    }

    override fun toString(): String = getName()

    companion object {
        fun new(): Organization {
            val name = getUserResponse("Enter the organization name: ")
            val address = getUserResponse("Enter the address: ")
            val org = Organization(name, address)
            org.setPhone(getUserResponse("Enter the number: "))
            return org
        }
    }
}

internal fun Instant.asTZDefaultDateTime(): LocalDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
