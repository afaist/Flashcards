package flashcards

import java.io.File

const val FILE_SIGNATURE = "### CARDS ###"
const val SEPARATOR = "||"

class FlashCards {
    private val cards = mutableListOf<Card>()

    private fun inTerms(new: String): Boolean = cards.any { new == it.term }
    private fun inDefinitions(new: String) = cards.any { new == it.definition }

    /**
     * adds a card in interaction with the user
     *
     */
    fun addCard() {
        Log.print("The card:")
        val term = Log.read()
        if (inTerms(term)) {
            Log.print("The card \"$term\" already exists.")
            return
        }
        Log.print("The definition of the card:")
        val definition = Log.read()
        if (inDefinitions(definition)) {
            Log.print("The definition \"$definition\" already exists.")
            return
        }
        cards.add(Card(term, definition))
        Log.print("The pair (\"$term\":\"$definition\") has been added.")
    }

    /**
     * remove card if it exists in interaction with user
     *
     */
    fun remove() {
        Log.print("Which card?")
        val term = Log.read()
        val found = cards.indexOfFirst { it.term == term }
        if (found < 0) {
            Log.print("Can't remove \"$term\": there is no such card.")
        } else {
            cards.removeAt(found)
            Log.print("The card has been removed.")
        }
    }

    /**
     * Play with user to game with cards
     *
     */
    fun ask() {
        Log.print("How many times to ask?")
        val n = Log.read().toInt()
        for (i in 1..n) {
            val card = getRandomCard()
            card.shows++
            Log.print("Print the definition of \"${card.term}\":")
            val answer = Log.read()
            Log.print(
                if (card.definition == answer) {
                    "Correct!"
                } else {
                    card.errors++
                    val found = cards.indexOfFirst { it.definition == answer }
                    if (found < 0) {
                        "Wrong. The right answer is \"${card.definition}\"."
                    } else {
                        "Wrong. The right answer is \"${card.definition}\", " +
                                "but your definition is correct for \"${cards[found].term}\"."
                    }
                })
        }
    }

    /**
     * Get random card
     *
     * @return card
     */
    private fun getRandomCard(): Card {
        val n = (0..cards.lastIndex).random()
        return cards[n]
    }

    /**
     * Export cards to file
     *
     */
    fun export(_fileName: String = "") {
        var fileName = _fileName
        if (fileName.isEmpty()) {
            Log.print("File name:")
            fileName = Log.read()
        }
        try {
            File(fileName).printWriter().use { out ->
                out.println(FILE_SIGNATURE)
                var n = 0
                cards.forEach {
                    n++
                    out.println(it)
                }
                Log.print("$n cards have been saved.")
            }
        } catch (e: Exception) {
            Log.print(e.message ?: "")
        }
    }

    /**
     * import cards from file
     *
     */
    fun import(_fileName: String = "") {
        var fileName = _fileName
        if (fileName.isEmpty()) {
            Log.print("File name:")
            fileName = Log.read()
        }
        val file = File(fileName)
        if (!file.exists()) {
            Log.print("File not found.")
            return
        }
        val lines = file.readLines()
        if (lines[0] == FILE_SIGNATURE) {
            for (i in 1..lines.lastIndex) {
                val (term, definition, shows, errors) = lines[i].split(SEPARATOR)
                val found = cards.indexOfFirst { it.term == term }
                if (found >= 0) {
                    cards[found].update(definition, shows.toInt(), errors.toInt())
                } else {
                    cards.add(Card(term, definition, shows.toInt(), errors.toInt()))
                }
            }
            Log.print("${lines.lastIndex} cards have been loaded.")
        } else {
            Log.print("Bad cards file format!!")
        }
    }

    /**
     * Get the hardest card
     *
     */
    fun getHardestCard() {
        if (cards.isEmpty()) {
            Log.print("There are no cards with errors.")
            return
        }
        val max = cards.maxOf { it.errors }
        if (max == 0) {
            Log.print("There are no cards with errors.")
            return
        }
        val selCards = cards.filter { it.errors == max }
        Log.print(
            if (selCards.isEmpty()) {
                "There are no cards with errors."
            } else if (selCards.size == 1) {
                "The hardest card is \"${selCards[0].term}\". " +
                        "You have ${selCards[0].errors} errors answering it."
            } else {
                val str = StringBuilder("The hardest cards are ")
                var n = 0
                selCards.forEach { card ->
                    if (n > 0) {
                        str.append(", ")
                    }
                    n += card.errors
                    str.append("\"${card.term}\"_${card.errors}")
                }
                str.append(". You have $n errors answering them.")
                str.toString()
            }
        )
    }

    /**
     * Reset statistics
     *
     */
    fun resetStatistics() {
        for (card in cards) {
            card.resetStatistics()
        }
        Log.print("Card statistics have been reset.")
    }
}