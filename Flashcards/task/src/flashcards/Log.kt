package flashcards

import java.io.File

object Log {
    private val logs = mutableListOf<String>()
    private fun add(string: String) {
        logs.add(string)
    }

    /**
     * Export log to file
     *
     */
    fun export() {
        this.print("File name:")
        val fileName = this.read()
        File(fileName).printWriter().use { out ->
            logs.forEach {
                out.println(it)
            }
        }
        this.print("The log has been saved.")
    }

    /**
     * Print to console and log
     *
     * @param string
     */
    fun print(string: String) {
        add(string)
        println(string)
    }

    /**
     * read from console and add to log
     *
     * @return
     */
    fun read(): String {
        val input = readLine()!!
        add(input)
        return input
    }
}