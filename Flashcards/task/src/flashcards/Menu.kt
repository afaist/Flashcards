package flashcards

const val MENU_ADD = "add"
const val MENU_REMOVE = "remove"
const val MENU_IMPORT = "import"
const val MENU_EXPORT = "export"
const val MENU_ASK = "ask"
const val MENU_LOG = "log"
const val MENU_HARDEST = "hardest card"
const val MENU_RESET = "reset stats"
const val MENU_EXIT = "exit"
val pointsMenu = arrayOf(MENU_ADD, MENU_REMOVE, MENU_IMPORT, MENU_EXPORT, MENU_ASK,
    MENU_LOG, MENU_HARDEST, MENU_RESET, MENU_EXIT)

enum class Menu {
    ADD, REMOVE, IMPORT, EXPORT, ASK, LOG, HARDEST, RESET, EXIT;

    fun toRaw() = enumToRaw[this]

    companion object {
        private val rawToEnum = mapOf(
            MENU_ADD to ADD,
            MENU_REMOVE to REMOVE,
            MENU_IMPORT to IMPORT,
            MENU_EXPORT to EXPORT,
            MENU_ASK to ASK,
            MENU_LOG to LOG,
            MENU_HARDEST to HARDEST,
            MENU_RESET to RESET,
            MENU_EXIT to EXIT
        )
        val enumToRaw = rawToEnum.entries.associate { (k, v) -> v to k }
        fun ofRaw(raw: String): Menu? {
            return if (raw in pointsMenu)
                rawToEnum[raw]!!
            else {
                Log.print("Wrong input!")
                null
            }
        }
    }

}