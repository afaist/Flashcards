import kotlin.math.cos
import kotlin.math.sin

fun main() = readLine()!!.toDouble().run { println(sin(this) - cos(this)) }