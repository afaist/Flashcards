package flashcards

const val CHOICE = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):"
const val BYE = "Bye bye!"
fun main(args: Array<String>) {
    val cards = FlashCards()
    var importFile = ""
    var exportFile = ""
    try {
        val parameters = getParameters(args)
        if (parameters.isNotEmpty()) {
            importFile = parameters[Parameters.IMPORT.name] ?: ""
            exportFile = parameters[Parameters.EXPORT.name] ?: ""
        }
    } catch (e: Exception) {
        if (e.message != null) {
            Log.print(e.message!!)
        }
        return
    }
    if (importFile.isNotEmpty()) {
        cards.import(importFile)
    }
    while (true) {
        Log.print(CHOICE)
        val choice = Log.read()
        val menu = Menu.ofRaw(choice.lowercase()) ?: continue
        when (menu) {
            Menu.ADD -> cards.addCard()
            Menu.REMOVE -> cards.remove()
            Menu.IMPORT -> cards.import()
            Menu.EXPORT -> cards.export()
            Menu.ASK -> cards.ask()
            Menu.EXIT -> {
                Log.print(BYE)
                break
            }
            Menu.LOG -> Log.export()
            Menu.HARDEST -> cards.getHardestCard()
            Menu.RESET -> cards.resetStatistics()
        }
        println()
    }
    if (exportFile.isNotEmpty()) {
        cards.export(exportFile)
    }
}

/**
 * Parsing the command line
 *
 * @param args
 * @return map of parameters
 */
fun getParameters(args: Array<String>): Map<String, String> {
    val parameters = mutableMapOf<String, String>()
    var typeParameter = Parameters.NONE
    for (arg in args) {
        if (arg.startsWith('-')) {
            if (typeParameter != Parameters.NONE) {
                getThrow(typeParameter)
            }
            if (arg in paramList) {
                typeParameter = Parameters.ofRaw(arg)
            } else {
                println("\"$arg\" is not a valid parameter. It will be skipped.")
            }

        } else {
            parameters[typeParameter.name] = arg
            typeParameter = Parameters.NONE
        }
    }
    if (typeParameter != Parameters.NONE) {
        getThrow(typeParameter)
    }
    return parameters.toMap()
}

/**
 * Throws an exception depending on the type of parameter
 *
 * @param typeParameter
 */
private fun getThrow(typeParameter: Parameters) {
    when (typeParameter) {
        Parameters.IMPORT -> {
            throw ExceptionsNotImportFile()
        }
        Parameters.EXPORT -> {
            throw ExceptionsNotExportFile()
        }
        else -> {}
    }
}
