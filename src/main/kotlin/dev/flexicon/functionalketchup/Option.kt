package dev.flexicon.functionalketchup

// Consider the Option type, which wraps a value.
// As an Option, a value is only ever in one of two states:
//      either it is something or it is nothing.
sealed interface Option<out T> {
    data class Some<T>(val value: T) : Option<T>

    object None : Option<Nothing>
}

// Provide a way to easily wrap a value as Option.
fun <T> some(value: T): Option<T> = Option.Some(value)

// Provide a way to run any transformer function for Options which have Some value,
// and simply return None for others.
fun <T, R> Option<T>.flatMap(f: (T) -> Option<R>): Option<R> = fold({ f(it) }, { Option.None })

// Provide a simple way to get at the optional value inside an Option.
fun <T> Option<T>.get(): T? = fold(onSome = { it }, onNone = { null })

fun <T> Option<T>.getOrThrow(message: () -> String = { "Option is None" }): T =
    fold({ it }, { throw IllegalStateException(message()) })

// Provide a simple match against the state of the Option.
fun <T, R> Option<T>.fold(onSome: (T) -> R, onNone: () -> R): R = when (this) {
    is Option.None -> onNone()
    is Option.Some -> onSome(value)
}
