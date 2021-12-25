package flashcards

const val PARAM_IMPORT = "-import"
const val PARAM_EXPORT = "-export"
val paramList = listOf(PARAM_IMPORT, PARAM_EXPORT)

enum class Parameters {
    IMPORT, EXPORT, NONE;

    fun toRaw() = enumToRaw[this]


    companion object {
        private val rawToEnum = mapOf(
            PARAM_IMPORT to IMPORT,
            PARAM_EXPORT to EXPORT,
            "" to NONE
        )
        val enumToRaw = rawToEnum.entries.associate { (k, v) -> v to k }
        fun ofRaw(raw: String): Parameters = rawToEnum[raw]!!


    }
}