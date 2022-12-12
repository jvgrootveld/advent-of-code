package shared

private val MATCH_NUMBERS = Regex("[0-9]+")

fun String.extractInts(): List<Int> {
    return MATCH_NUMBERS.findAll(this).map {
        it.value.toInt()
    }.toList()
}

fun String.extractLongs(): List<Long> {
    return MATCH_NUMBERS.findAll(this).map {
        it.value.toLong()
    }.toList()
}