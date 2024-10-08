package ru.profitsw2000.utils

import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sinh

private const val sterling_koef_1 = 1.0/12.0
private const val sterling_koef_2 = 1.0/288.0
private const val sterling_koef_3 = 139.0/51840.0
private const val sterling_koef_4 = 571.0/2488320.0
private const val sterling_koef_5 = 163879.0/209018880.0
private const val sterling_koef_6 = 5246819.0/75246796800.0

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

fun Double.factorial(): Double {
    val result = if (this % 1 == 0.0) factorialRecursive(this)
    else gamma(this)

    checkForOverflow(result)

    return result
}

private fun checkForOverflow(double: Double) {
    if (double == Double.POSITIVE_INFINITY ||
        double == Double.NEGATIVE_INFINITY ||
        double == Double.NaN)
        throw ArithmeticException("Overflow of calculated number.")
}

private fun factorialRecursive(x: Double): Double {
    return if (x <= 1) 1.0
    else x* factorialRecursive(x - 1)
}

private fun gamma(x: Double): Double {
    val n = x + 1
    return ((2*PI*n).pow(1/2) * n.pow(n-1) * exp(-n))/
            (1 - sterling_koef_1/n +
                    sterling_koef_2/n.pow(2) +
                    sterling_koef_3/n.pow(3) +
                    sterling_koef_4/n.pow(4) +
                    sterling_koef_5/n.pow(5) +
                    sterling_koef_6/n.pow(6))
}