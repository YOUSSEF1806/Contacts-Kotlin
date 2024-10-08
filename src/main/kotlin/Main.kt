

fun main(args: Array<String>) {
    if (args.size == 1) {
        AppContact.setFileName(args[0])
    }
    AppContact.launch()
}

