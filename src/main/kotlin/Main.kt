import dev.flexicon.functionalketchup.Option
import dev.flexicon.functionalketchup.flatMap
import dev.flexicon.functionalketchup.fold
import dev.flexicon.functionalketchup.get
import dev.flexicon.functionalketchup.some

fun main() {
    println("Hello ƛ🍅")

    val result = getUserName()
        .flatMap(::getLogsForUser)
        .flatMap(::firstAndLast)

    println("Result: ${result.get()}")

    result.fold({ println("Your logs are: $it") }, { println("Failed to retrieve logs") })
    val next = result.fold({ it }, { emptyList() })
}

fun getUserName(): Option<String> = some("Steve")

fun getLogsForUser(username: String): Option<List<String>> = when (username) {
    "John" -> some(listOf("hello", "world", "foo"))
    else -> Option.None
}

fun <T> firstAndLast(list: List<T>): Option<List<T>> = when {
    list.size < 2 -> Option.None
    else -> some(listOf(list.first(), list.last()))
}
