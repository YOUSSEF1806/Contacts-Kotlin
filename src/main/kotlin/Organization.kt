import com.squareup.moshi.Moshi
import kotlinx.datetime.Clock

data class Organization(
    private var name: String,
    private var address: String = ""
) : Contact() {

    override fun detailString(): String {
        val phoneString = if (hasNumber()) phone else "[no number]"
        val infoString = "Organization name: $name" +
                "\nAddress: $address" +
                "\nNumber: $phoneString" +
                "\nTime created: $timeCreated" +
                "\nTime last edit: $lastEditTime"
        return infoString
    }

    override fun setProperty(propertyName: String, value: String) {
        return when (propertyName) {
            "name" -> this.name = value
            "address" -> this.address = value
            "number" -> this.phone = value
            else -> {}
        }
    }

    override fun getProperty(propertyName: String): String {
        return when (propertyName) {
            "name" -> name
            "address" -> address
            "number" -> phone
            else -> ""
        }
    }

    override fun listProperties(): List<String> {
        return listOf("name", "address", "number")
    }

    override fun toJsonFormat(moshi: Moshi): String {
        val adapter = moshi.adapter(Organization::class.java)
        return adapter.toJson(this)
    }

    override fun toString(): String = name

    companion object {

        fun fromJsonFormat(moshi: Moshi, jsonString: String): Organization? {
            val adapter = moshi.adapter(Organization::class.java)
            return adapter.fromJson(jsonString)
        }

        fun new(): Organization {
            val name = getUserResponse("Enter the organization name: ")
            val address = getUserResponse("Enter the address: ")
            val org = Organization(name, address)

            org.phone = getUserResponse("Enter the number: ")
            org.timeCreated = Clock.System.now().toString()
            org.lastEditTime = Clock.System.now().toString()

            return org
        }
    }
}

