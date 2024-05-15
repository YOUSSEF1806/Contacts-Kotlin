package com.youyou

internal fun getUserResponse(prompt: String) = print(prompt).let { readln().trim() }

object AppContact {
    private var listContacts = listOf<Contact>()

    fun mainMenu() {
        val possibleActions = MenuOptions.values().map { it.string }
        do {
            val userChoice = getUserResponse("\nEnter action (${possibleActions.joinToString()}):")
            when (userChoice) {
                MenuOptions.ADD.string -> addAction()
                MenuOptions.REMOVE.string -> editOrRemoveAction(MenuOptions.REMOVE)
                MenuOptions.EDIT.string -> editOrRemoveAction(MenuOptions.EDIT)
                MenuOptions.COUNT.string -> countAction()
                MenuOptions.LIST.string -> infoAction()
                MenuOptions.EXIT.string -> return
                else -> println("Invalid Action!")
            }
        } while (userChoice != MenuOptions.EXIT.string)
    }

    private fun addAction() {
        listContacts = listContacts.toMutableList().let {
            val newContact = Contact.new()
            if(newContact != null) {
                it.add(newContact)
                println("The record added.")
            } else {
                println("Invalid input")
            }
            it.toList()
        }
    }

    private fun editOrRemoveAction(action: MenuOptions) {
        if (listContacts.isNotEmpty()) {
            listAction()
            try {
                val selectedIndex = getUserResponse("Select a record: ").toInt()
                if (selectedIndex in (1..listContacts.size)) {
                    if (action == MenuOptions.EDIT)
                        editAction(selectedIndex - 1)
                    else if (action == MenuOptions.REMOVE)
                        removeAction(selectedIndex - 1)
                }
            } catch (e: NumberFormatException) {
                println("Is Not a number!")
            }
        } else println("No records to ${action.string}!")
    }

    private fun editAction(index: Int) {
        listContacts[index].editContact()
    }

    private fun removeAction(index: Int) {
        listContacts = listContacts.toMutableList().let {
            it.removeAt(index)
            println("The record removed!")
            it.toList()
        }
    }

    private fun countAction() {
        println("The Phone Book has ${listContacts.size} records.")
    }

    private fun infoAction() {
        listAction()
        try {
            val selectedIndex = getUserResponse("Enter index to show info: ").toInt()
            if (selectedIndex in (1..listContacts.size)) {
                listContacts[selectedIndex - 1].printInfo()
            }
        } catch (e: NumberFormatException) {
            println("Is Not a number!")
        }
    }

    private fun listAction() {
        listContacts.forEachIndexed { index, contact ->
            println("${index + 1}. $contact")
        }
    }
}

enum class MenuOptions(val string: String) {
    ADD("add"),
    REMOVE("remove"),
    EDIT("edit"),
    COUNT("count"),
    LIST("info"),
    EXIT("exit")
}