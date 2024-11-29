package ru.profitsw2000.data.utils

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.MathContext

fun String.add(augend: String, scale: Int): String {
    val mathContext = MathContext(scale)
    val addendBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
    val augendBigDecimal = BigDecimalMath.toBigDecimal(augend.toStandardFormat())

    return addendBigDecimal.add(augendBigDecimal, mathContext).stripTrailingZeros().toString().toCalculatorFormat()
}

fun String.subtract(subtrahend: String, scale: Int): String {
    val mathContext = MathContext(scale)
    val minuendBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
    val subtrahendBigDecimal = BigDecimalMath.toBigDecimal(subtrahend.toStandardFormat())

    return minuendBigDecimal.subtract(subtrahendBigDecimal, mathContext).stripTrailingZeros().toString().toCalculatorFormat()
}

fun String.multiply(multiplicand: String, scale: Int): String {
    val mathContext = MathContext(scale)
    val multiplierBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
    val multiplicandBigDecimal = BigDecimalMath.toBigDecimal(multiplicand.toStandardFormat())

    return multiplierBigDecimal.multiply(multiplicandBigDecimal, mathContext).stripTrailingZeros().toString().toCalculatorFormat()
}

fun String.divide(divisor: String, scale: Int): String {
    val mathContext = MathContext(scale)
    val dividendBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
    val divisorBigDecimal = BigDecimalMath.toBigDecimal(divisor.toStandardFormat())

    return dividendBigDecimal.divide(divisorBigDecimal, mathContext).stripTrailingZeros().toString().toCalculatorFormat()
}

fun String.negate(): String = BigDecimalMath.toBigDecimal(this.toStandardFormat()).negate().stripTrailingZeros().toString().toCalculatorFormat()

fun String.toCalculatorFormat(): String {
    return when {
        this.contains("E") && this.contains(".") -> this.replace(".", ",").replace("E", "e")
        this.contains("E") -> this.replace("E", ",e")
        else -> this.replace(".",",")
    }
}

fun String.toStandardFormat(): String {
    return when {
        this.contains(",e") -> this.replace(",e", "E")
        else -> this.replace(",", ".").replace("e", "E")
    }
}

