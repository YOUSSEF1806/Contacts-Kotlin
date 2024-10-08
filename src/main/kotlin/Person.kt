import com.squareup.moshi.Moshi
import kotlinx.datetime.Clock

data class Person(
    private var name: String,
    private var surname: String = "",
    private var birthday: String = "",
    private var gender: String = ""
) : Contact() {

    override fun detailString(): String {
        val birthdayString = birthday.ifBlank { "[no data]" }
        val genderString = gender.ifEmpty { "[no data]" }
        val phoneString = if (hasNumber()) phone else "[no number]"
        val infoString = "Name: $name \nSurname: $surname " +
                "\nBirth date: $birthdayString \nGender: $genderString" +
                "\nNumber: $phoneString " +
                "\nTime created: $timeCreated." +
                "\nTime last edit: $lastEditTime"
        return infoString
    }

    override fun listProperties(): List<String> =
        listOf("name", "surname", "birth", "gender", "number")

    override fun setProperty(propertyName: String, value: String) {
        return when (propertyName) {
            "name" -> this.name = value
            "surname" -> this.surname = value
            "birth" -> this.birthday = validateBirthday(value)
            "gender" -> this.gender = validateGender(value)
            "number" -> this.phone = value
            else -> {}
        }
    }

    override fun getProperty(propertyName: String): String {
        return when (propertyName) {
            "name" -> name
            "surname" -> surname
            "birth" -> birthday
            "gender" -> gender
            "number" -> phone
            else -> ""
        }
    }

    override fun toString(): String = "$name $surname"

    override fun toJsonFormat(moshi: Moshi): String {
        val adapter = moshi.adapter(Person::class.java)
        return adapter.toJson(this)
    }

    companion object {
        fun new(): Person {
            val name = getUserResponse("Enter the name of the person: ")
            val surname = getUserResponse("Enter the surname of the person: ")
            val birthday = validateBirthday(getUserResponse("Enter the birth date: "))
            val gender = validateGender(getUserResponse("Enter the gender (F, M): "))
            val phoneNumber = getUserResponse("Enter the number: ")
            val p = Person(name, surname, birthday, gender)
                .also{
                    it.phone = phoneNumber
                    it.timeCreated = Clock.System.now().toString()
                    it.lastEditTime = Clock.System.now().toString()
                }
            return p
        }

        fun fromJsonFormat(moshi: Moshi, jsonString: String): Person? {
            val adapter = moshi.adapter(Person::class.java)
            return adapter.fromJson(jsonString)
        }

        private fun validateBirthday(birthday: String): String {
            val dateRegex = Regex("""\d{4}(-|/)\d{2}\1\d{2}""")
            if (birthday.matches(dateRegex)) {
                if(birthday.contains("/"))
                    return birthday.replace("/", "-")
                return birthday
            } else {
                println("Bad birth date!")
                return ""
            }
        }

        private fun validateGender(gender: String): String {
            if (gender.uppercase() == "F" || gender.uppercase() == "M") {
                return gender.uppercase()
            } else {
                println("Bad gender!")
                return ""
            }
        }
    }
}
