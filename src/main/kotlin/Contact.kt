package com.youyou

data class Contact(
    private var name: String,
    private var surname: String,
    private var phone: String = ""
) {
    fun setPhone(phone: String) {
        if (checkValidPhone(phone))
            this.phone = phone
        else
            this.phone = ""
    }

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

    private fun hasNumber(): Boolean = this.phone != ""

    fun editContact() {
        val userResponse = getUserResponse("Select a field (name, surname, number): ")
        when (userResponse) {
            "name" -> name = getUserResponse("enter name: ")
            "surname" -> surname = getUserResponse("enter surname: ")
            "number" -> setPhone(getUserResponse("enter number: "))
        }
        println("The record updated!")
    }

    override fun toString(): String {
        val phonePrint = if (hasNumber()) phone else "[no number]"
        return "$name $surname, $phonePrint"
    }

    companion object {
        fun fromUserInput() : Contact {
            val name = getUserResponse("Enter the name of the person: ")
            val surname = getUserResponse("Enter the surname of the person: ")
            return Contact(name, surname).also {
                it.setPhone(getUserResponse("Enter the number: "))
            }
        }
    }
}