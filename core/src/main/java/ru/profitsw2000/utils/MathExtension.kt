package ru.profitsw2000.utils

import kotlin.math.pow
import kotlin.math.sinh

fun Double.calcSinh(): Double {
    val result = sinh(this)

    checkForOverflow(result)

    return result
}

fun Double.powerTo(x: Double): Double {
    val result = this.pow(x)

    checkForOverflow(result)

    return result
}

private fun checkForOverflow(double: Double) {
    if (double == Double.POSITIVE_INFINITY ||
        double == Double.NEGATIVE_INFINITY ||
        double == Double.NaN)
        throw ArithmeticException("Overflow of calculated number.")
}