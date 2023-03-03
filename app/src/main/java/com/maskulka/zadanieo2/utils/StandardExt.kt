
package com.maskulka.zadanieo2.utils

/**
 * Calls the specified function [block] and returns its result if `this` is `null`.
 *
 * @param R The type of the receiver.
 * @param block The function to execute if `this` is `null`.
 * @return `this` if it is not `null`, or the [block] result if `this` is `null`.
 */
inline fun <R> R?.orElse(block: () -> R) = this ?: run(block)

/**
 * Checks if [p1] and [p2] are not `null` and calls the specified function [block] if this is the case.
 *
 * @param T1 The type of the first parameter.
 * @param T2 The type of the second parameter.
 * @param R The return type of [block].
 * @param p1 The first parameter to check.
 * @param p2 The second parameter to check.
 * @param block The function to execute if [p1] and [p2] are not `null`.
 * @return The [block] result if [p1] and [p2] are not `null`, or `null` if [p1] or [p2] is `null`.
 */
inline fun <T1 : Any, T2 : Any, R : Any> ifNotNull(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

/**
 * Checks if [p1], [p2] and [p3] are not `null` and calls the specified function [block] if this is the case.
 *
 * @param T1 The type of the first parameter.
 * @param T2 The type of the second parameter.
 * @param T3 The type of the third parameter.
 * @param R The return type of [block].
 * @param p1 The first parameter to check.
 * @param p2 The second parameter to check.
 * @param p2 The third parameter to check.
 * @param block The function to execute if [p1], [p2] and [p3] are not `null`.
 * @return The [block] result if [p1], [p2] and [p3] are not `null`, or `null` if [p1], [p2] or [p3] is `null`.
 */
inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> ifNotNull(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

/**
 * Casts the receiver to the specified [T] type.
 *
 * @param T The type to cast the receiver to.
 * @receiver The receiver cast as an instance of [T] or `null` if the cast failed.
 */
inline fun <reified T> Any.castOrNull() = this as? T

/**
 * Calls the specified function [block] and returns its result if invocation was successful, or `null` if a [Throwable] was caught while executing the [block] function.
 *
 * @param R The return type of [block].
 * @param block The function to execute.
 * @return The [block] return value, or `null` if a [Throwable] was thrown while executing [block].
 */
inline fun <R> tryOrNull(block: () -> R): R? {
    return try {
        block()
    } catch (_: Throwable) {
        null
    }
}
