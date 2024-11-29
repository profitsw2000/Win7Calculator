package ru.profitsw2000.data.statemachine.domain

import ch.obermuhlner.math.big.BigDecimalMath
import ru.profitsw2000.data.constants.GENERAL_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.utils.commaTruncate
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

interface CalculatorState {

    val scale: Int

    fun consumeAction(action: CalculatorAction): CalculatorState

    /**
     * Converts string to double
     * @param calculatorString - string to convert
     * @return converted number
     */
    fun calculatorStringToDouble(calculatorString: String): Double {
        return try {
            calculatorString.replace(",", ".").toDouble()
        } catch (numberFormatException: NumberFormatException) {
            0.0
        }
    }

    /**
     * Converts double number to string for calculator display
     * @param number - double type number to convert to string
     * @return string, formatted specifically for calculator display
     */
    fun  doubleToCalculatorString(number: Double): String {

        val decimalFormat = DecimalFormat("###.################")
        val numberOfWholeInts = decimalFormat.format(number).split(',').elementAt(0).length
        val newScale = GENERAL_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER - numberOfWholeInts
        val decimalNumber = BigDecimal(number).setScale(newScale, RoundingMode.HALF_UP)

        return if (isOutOfMaxDigitNumber(number)) getScientificFormattedString(number)
        else decimalFormat.format(decimalNumber).replace('.', ',')
    }

    /**
     * Converts double number to string with require format - traditional or with scientific notation.
     * Required format defined by second parameter.
     * @param number - double number to convert
     * @param isScientificNotation - boolean variable, that defines required format. If it false
     * then required format is traditional, otherwise it is scientific notation.
     */
    fun doubleToCalculatorString(number: Double, isScientificNotation: Boolean): String {
        return if (isScientificNotation) {
            getScientificFormattedString(number)
        } else {
            doubleToCalculatorString(number)
        }
    }

    /**
     * Defines if double number is in certain range, so contain certain amount of digit.
     * @param number - double number to define it in range
     * @return - true if is out of range and false if otherwise
     */
    fun isOutOfMaxDigitNumber(number: Double): Boolean {
        return !((number < 1.0E16 && number > 1.0E-16) || (number > -1.0E16 && number < -1.0E-16) || number == 0.0)
    }

    /**
     * Converts number of double type to string, formatted specifically to calculator display where
     * exponent part with small "e" and with "-"or "+" sign.
     * @param number - number to convert
     * @return String with formatted number
     */
    fun getScientificFormattedString(number: Double): String {
        val  numberString = DecimalFormat("0.0##############E0", DecimalFormatSymbols(Locale.ENGLISH)).format(number)

        return if (numberString.contains("E-")) numberString.replace('.', ',').replace("E-", "e-").replace("0e", "e")
        else numberString.replace('.', ',').replace("E", "e+").replace("0e", "e")
    }

    /**
     * Format conventional calculator string according to parameter value.
     * @param isScientificNotation - boolean parameter to decide whether to format string to scientific form or not
     * @return formatted to scientific form string if param is true, otherwise return same string, but
     * with truncated comma at the end of it if has.
     */
    fun String.calcFormat(isScientificNotation: Boolean): String {
        return if (isScientificNotation) {
            getScientificFormattedString(calculatorStringToDouble(this))
        } else {
            this.commaTruncate()
        }
    }

    fun String.add(augend: String): String {
        val mathContext = MathContext(scale)
        val addendBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val augendBigDecimal = BigDecimalMath.toBigDecimal(augend.toStandardFormat())
        val result = addendBigDecimal.add(augendBigDecimal, mathContext).stripTrailingZeros()

        checkForOverflow(result)

        return result.toString().toCalculatorFormat()
    }

    fun String.subtract(subtrahend: String): String {
        val mathContext = MathContext(scale)
        val minuendBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val subtrahendBigDecimal = BigDecimalMath.toBigDecimal(subtrahend.toStandardFormat())
        val result = minuendBigDecimal.subtract(subtrahendBigDecimal, mathContext).stripTrailingZeros()

        checkForOverflow(result)

        return result.toString().toCalculatorFormat()
    }

    fun String.multiply(multiplicand: String): String {
        val mathContext = MathContext(scale)
        val multiplierBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val multiplicandBigDecimal = BigDecimalMath.toBigDecimal(multiplicand.toStandardFormat())
        val result = multiplierBigDecimal.multiply(multiplicandBigDecimal, mathContext).stripTrailingZeros()

        checkForOverflow(result)

        return result.toString().toCalculatorFormat()
    }

    fun String.divide(divisor: String): String {
        val mathContext = MathContext(scale)
        val dividendBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val divisorBigDecimal = BigDecimalMath.toBigDecimal(divisor.toStandardFormat())
        val result = dividendBigDecimal.divide(divisorBigDecimal, mathContext).stripTrailingZeros()

        checkForOverflow(result)

        return result.toString().toCalculatorFormat()
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

    fun String.sqrt(): String {
        val mathContext = MathContext(scale)
        val numberBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())

        return BigDecimalMath.sqrt(numberBigDecimal, mathContext).stripTrailingZeros().toString().toCalculatorFormat()
    }

    private fun checkForOverflow(result: BigDecimal) {
        val maxValueString = "1E+10000"
        val minValueString = "-1E+10000"
        val minFractionValueString = "1E-9999"
        val maxFractionValueString = "-1E-9999"

        val maxValueBigDecimal = BigDecimalMath.toBigDecimal(maxValueString)
        val minValueBigDecimal = BigDecimalMath.toBigDecimal(minValueString)
        val minFractionValueBigDecimal = BigDecimalMath.toBigDecimal(minFractionValueString)
        val maxFractionValueBigDecimal = BigDecimalMath.toBigDecimal(maxFractionValueString)

        if ((result.compareTo(maxValueBigDecimal) != -1) ||
            (result.compareTo(minValueBigDecimal) != 1) ||
            ((result.compareTo(minFractionValueBigDecimal) == -1) && (result.compareTo(maxFractionValueBigDecimal) == 1)))
            throw ArithmeticException("Overflow of calculated number.")
    }
}