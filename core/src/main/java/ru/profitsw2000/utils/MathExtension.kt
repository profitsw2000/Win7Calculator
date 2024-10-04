package ru.profitsw2000.utils

import kotlin.math.sinh

fun Double.calcSinh(): Double {
    val result = sinh(this)

    if (result == Double.POSITIVE_INFINITY ||
        result == Double.NEGATIVE_INFINITY ||
        result == Double.NaN)
        throw ArithmeticException("Overflow of result number.")

    return result
}