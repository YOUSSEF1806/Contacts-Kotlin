import com.squareup.moshi.Moshi
import kotlinx.datetime.*

abstract class Contact {
    protected var phone: String = ""
        set(value) {
            field = if (isValidPhone(value))
                value
            else
                ""
        }
    protected var timeCreated: String = ""
    protected var lastEditTime: String = ""

    fun updated() {
        lastEditTime = Clock.System.now().toString()
    }

    fun hasNumber(): Boolean = this.phone != ""

    abstract fun detailString(): String
    abstract fun listProperties(): List<String>
    abstract fun getProperty(propertyName: String): String
    abstract fun setProperty(propertyName: String, value: String)
    abstract fun toJsonFormat(moshi: Moshi): String

    companion object {
        fun isValidPhone(phone: String): Boolean {
            val regexPhoneFormat1 = Regex("""^\+?([0-9a-zA-Z]+)?\s?-?([0-9a-zA-Z]{2,}\s?-?)*$""")
            val regexPhoneFormat2 = Regex("""^\+?(\([0-9a-zA-Z]+\))\s?-?([0-9a-zA-Z]{2,}\s?-?)*$""")
            val regexPhoneFormat3 = Regex("""^\+?([0-9a-zA-Z]+)\s?-?(\([0-9a-zA-Z]{2,}\))\s?-?([0-9a-zA-Z]{2,}\s?-?)*$""")
            val parenthesesCheck = Regex("""\(.+\).*\(.+\)""")
            return phone.matches(regexPhoneFormat1)
                .or(phone.matches(regexPhoneFormat2))
                .or(phone.matches(regexPhoneFormat3))
                .and(!phone.matches(parenthesesCheck))
        }
    }
}
