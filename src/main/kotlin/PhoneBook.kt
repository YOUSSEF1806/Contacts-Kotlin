
object PhoneBook {
    internal var listContacts = listOf<Contact>()

    fun addContact(typeContact: String): Boolean {
        val newContact = when (typeContact.lowercase()) {
            "person" -> Person.new()
            "organization" -> Organization.new()
            else -> null
        }
        if (newContact != null) {
            listContacts = listContacts.toMutableList()
                .apply { this.add(newContact) }
                .toList()
            return true
        } else return false
    }

    fun editContact(selectedIndex: Int): Boolean {
        val listProps = listContacts[selectedIndex].listProperties()
        val propertyName = getUserResponse("Select a field (${listProps.joinToString()}): ")

        when (propertyName) {
            in listProps -> editPropertyPrompt(propertyName).also {
                listContacts = listContacts.toMutableList().apply {
                    this[selectedIndex].setProperty(propertyName, it)
                    this[selectedIndex].updated()
                }.toList()
                println("The record updated!")
                return true
            }

            else -> return false
        }
    }

    private fun editPropertyPrompt(propertyName: String): String {
        return when (propertyName) {
            "name" -> getUserResponse("enter name: ")
            "surname" -> getUserResponse("enter surname: ")
            "birth" -> getUserResponse("Enter the birth date: ")
            "gender" -> getUserResponse("Enter the gender (M, F): ")
            "number" -> getUserResponse("enter number: ")
            "address" -> getUserResponse("enter the address: ")
            else -> ""
        }
    }

    fun deleteContact(selectedIndex: Int) {
        listContacts = listContacts.toMutableList()
            .apply { this.removeAt(selectedIndex) }
            .toList()
    }

    fun listContactString(contacts: List<Contact>, header: Boolean = false): String {
        var printString = ""
        if (header)
            printString += "Found ${contacts.size} results:\n"
        contacts.forEachIndexed { index, contact ->
            printString += "${index + 1}. $contact\n"
        }
        return printString
    }

    fun searchContacts(query: String = ""): List<Contact> {
        return if (query == "")
            listContacts
        else
            listContacts.filter { contact ->
                val queryRegex = Regex(query, RegexOption.IGNORE_CASE)
                contact.listProperties()
                    .joinToString(" ") { contact.getProperty(it) }
                    .contains(queryRegex)
            }
    }

    fun getCount(): Int = listContacts.size

}