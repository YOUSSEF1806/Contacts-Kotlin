package com.youyou

import kotlinx.datetime.LocalDate

class Person(
    name: String,
    private var surname: String
) : Contact(name) {
    private var birthday: LocalDate? = null
    private var gender: Gender? = null

    override fun editContact(): Boolean {
        val userResponse = getUserResponse("Select a field (name, surname, birth, gender, number): ")
        var wasUpdated = true
        when (userResponse) {
            "name" -> setName(getUserResponse("enter name: "))
            "surname" -> surname = getUserResponse("enter surname: ")
            "birth" ->  {
                val valid = setBirthday(getUserResponse("Enter the birth date: "))
                if(!valid) println("Bad birth date!")
            }
            "gender" -> {
                val valid = setGender(getUserResponse("Enter the gender (M, F): "))
                if (!valid) println("Bad gender!")
            }
            "number" -> setPhone(getUserResponse("enter number: "))
            else -> wasUpdated = false
        }
        if (wasUpdated) {
            updated()
            println("The record updated!")
        }
        return wasUpdated
    }

    fun setBirthday(birthday: String): Boolean {
        try {
            this.birthday = LocalDate.parse(birthday)
            return true
        } catch (e: Exception) {
            this.birthday = null
            return false
        }
    }

    fun setGender(gender: String): Boolean {
        when (gender) {
            "F" -> {
                this.gender = Gender.FEMALE
                return true
            }
            "M" -> {
                this.gender = Gender.MALE
                return true
            }
            else -> {
                this.gender = null
                return false
            }
        }
    }

    override fun printInfo() {
        val birthdayString = birthday ?: "[no data]"
        val genderString = gender?.string ?: "[no data]"
        println("Name: ${getName()}")
        println("Surname: $surname")
        println("Birth date: $birthdayString")
        println("Gender: $genderString")
        val phoneString = if (hasNumber()) getPhone() else "[no number]"
        println("Number: $phoneString")
        println("Time created: ${getTimeCreated().asTZDefaultDateTime()}")
        println("Time last edit: ${getLastEditTime().asTZDefaultDateTime()}")
    }

    override fun toString(): String = "${getName()} $surname"

    companion object {
        fun new(): Person {
            val name = getUserResponse("Enter the name of the person: ")
            val surname = getUserResponse("Enter the surname of the person: ")
            val p = Person(name, surname)
            val birthday = getUserResponse("Enter the birth date: ")
            if (p.setBirthday(birthday))
                println("Bad birth date!")
            val gender = getUserResponse("Enter the gender (M, F): ")
            if (p.setGender(gender))
                println("Bad gender!")
            p.setPhone(getUserResponse("Enter the number: "))
            return p
        }
    }
}

enum class Gender(val string: String) {
    FEMALE("F"),
    MALE("M")
}