package flashcards

data class Card(
    val term: String,
    var definition: String,
    var shows: Int = 0,
    var errors: Int = 0,
) {
    /**
     * Update card
     *
     * @param _definition
     * @param _shows
     * @param _errors
     */
    fun update(_definition: String, _shows: Int = 0, _errors: Int = 0) {
        definition = _definition
        shows = _shows
        errors = _errors
    }

    /**
     * Reset statistics
     *
     */
    fun resetStatistics() {
        this.errors = 0
        this.shows = 0
    }

    override fun toString(): String {
        return "$term$SEPARATOR$definition$SEPARATOR$shows$SEPARATOR$errors"
    }
}