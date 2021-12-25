import kotlin.math.pow
import kotlin.math.roundToInt

const val THIRD = 1 / 3.0
fun main() = println(readLine()!!.toDouble().pow(THIRD).roundToInt())