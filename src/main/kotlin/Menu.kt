
object Menu {
    private var selectedRecord: Int = -1
    private val menuMap = mapOf("menu" to { mainMenu() }, "record" to { recordMenu() })
    private var currentMenu = "menu"
    private var exitFlag = false

    fun menuLoop() {
        while (!exitFlag) {
            menuMap[currentMenu]?.invoke()
        }
    }

    private fun mainMenu() {
        val response = getUserResponse("\n[menu] Enter action (add, list, search, count, exit): ")
        when (response.lowercase()) {
            "add" -> addAction()
            "list" -> listAction()
            "search" -> searchAction()
            "count" -> countAction()
            "exit" -> exitAction()
        }
    }

    private fun addAction() {
        val typeEntry = getUserResponse("Enter the type (person, organization): ")
        if (PhoneBook.addContact(typeEntry))
            println("The record added.")
        else
            println("Invalid input")
    }

    private fun listAction() {
        val listElem = PhoneBook.searchContacts()
        println(PhoneBook.listContactString(listElem))
        do {
            val userChoice = getUserResponse("\n[list] Enter action ([number], back): ")
            when (userChoice) {
                "back" -> mainMenu()
                else -> selectItemAndGoToRecord(userChoice, listElem)
            }
        } while (userChoice != "back" && selectedRecord == -1)
    }

    private fun searchAction() {
        val queryString = getUserResponse("Enter search query: ")
        val listElem = PhoneBook.searchContacts(queryString)
        println(PhoneBook.listContactString(listElem, true))
        do {
            val userChoice = getUserResponse("\n[search] Enter action ([number], back, again): ")
            when (userChoice) {
                "back" -> currentMenu = "menu"
                "again" -> searchAction()
                else -> selectItemAndGoToRecord(userChoice, listElem)
            }
        } while (userChoice != "back" && userChoice != "again" && selectedRecord == -1)
    }

    private fun selectItemAndGoToRecord(userChoice: String, listElem: List<Contact>) {
        selectedRecord = selectRecordIndex(userChoice, listElem)
        if (selectedRecord != -1) {
            println(PhoneBook.searchContacts()[selectedRecord].detailString())
            currentMenu = "record"
        }
    }

    private fun selectRecordIndex(userChoice: String, listElem: List<Contact>): Int {
        try {
            val selectedIndex = userChoice.toInt()
            if (selectedIndex in (1..listElem.size)) {
                return selectedIndex - 1
            } else {
                println("Invalid record number")
            }
        } catch (e: NumberFormatException) {
            println("Is Not a number!")
        }
        return -1
    }

    private fun countAction() {
        println("The Phone Book has ${PhoneBook.getCount()} records.")
    }

    private fun recordMenu() {
        val response = getUserResponse("\n[record] Enter action (edit, delete, menu): ")
        when (response) {
            "edit" -> editAction()
            "delete" -> deleteAction()
            "menu" -> currentMenu = "menu"
        }
    }

    private fun editAction() {
        PhoneBook.editContact(selectedRecord)
    }

    private fun deleteAction() {
        PhoneBook.deleteContact(selectedRecord)
    }

    private fun exitAction() {
        exitFlag = true
    }

}

internal fun getUserResponse(prompt: String) = print(prompt).let { readln().trim() }
