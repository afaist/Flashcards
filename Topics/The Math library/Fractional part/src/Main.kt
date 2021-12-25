const val BASE = 10
fun main() = readLine()!!.toDouble().run { println(((this - this.toInt()) * BASE).toInt()) }