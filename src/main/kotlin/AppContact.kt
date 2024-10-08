import PhoneBook.listContacts
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readLines
import kotlin.io.path.writeLines


object AppContact {
    private var fileName = "" // data/phonebook.txt
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun launch() {
        loadFromFile()
        Menu.menuLoop()
        saveToFile()
    }

    private fun saveToFile() {
        if (fileName != "" && listContacts.isNotEmpty()) {
            Path(fileName)
                .writeLines(
                    listContacts.map {
                        val prefix = if (it is Organization) "organization" else "person"
                        "$prefix: ${it.toJsonFormat(moshi)}"
                    }
                )
        }
    }

    private fun loadFromFile() {
        val pathFile = Path(fileName)
        if (fileName != "" && pathFile.exists()) {
            println("open $fileName")
            listContacts = pathFile.readLines().map { line ->
                val type = line.substringBefore(": {")
                if (type == "person")
                    Person.fromJsonFormat(moshi, line.substringAfter("$type: ")) !!
                else
                    Organization.fromJsonFormat(moshi, line.substringAfter("$type: ")) !!
            }
        }
    }

    fun setFileName(fileName: String) {
        if (fileName.trim() == "") {
            this.fileName = ""
        } else {
            this.fileName = fileName
        }
    }
}
