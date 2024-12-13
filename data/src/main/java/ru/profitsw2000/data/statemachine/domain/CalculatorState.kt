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
        val number = BigDecimalMath.toBigDecimal(this)
        return if (isScientificNotation) {
            number.toScientificNotationString().toCalculatorFormat()
        } else {
            this.commaTruncate()
        }
    }

    /**
     * Calculates sum of two numbers represented in String type and return result in BigDecimal type.
     * @param augend - second member of expression
     * @return result of calculation in BigDecimal format
     */
    private fun String.calculateAdd(augend: String): BigDecimal {
        val mathContext = MathContext(scale)
        val addendBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val augendBigDecimal = BigDecimalMath.toBigDecimal(augend.toStandardFormat())
        val result = addendBigDecimal.add(augendBigDecimal, mathContext).stripTrailingZeros()

        checkForOverflow(result)

        return result
    }

    /**
     * Calculates sum of two numbers represented in String type and return result in String type.
     * @param augend - second member of expression
     * @return result of calculation in String format
     */
    fun String.add(augend: String): String {
        return this.calculateAdd(augend).toString().toCalculatorFormat()
    }

    /**
     * Calculates sum of two numbers represented in String type and return result in String type
     * in plain or engineering format depending on second parameter of function.
     * @param augend - second member of expression
     * @param isScientificNotation - define format of returned String
     * @return result of calculation in String format
     */
    fun String.add(augend: String, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculateAdd(augend).toEngineeringString().toCalculatorFormat()
        else
            this.add(augend)
    }

    /**
     * Subtract subtrahend from @this and return result in BigDecimal type.
     * @param subtrahend - number to subtract from @this
     * @return result of calculation in BigDecimal format
     */
    private fun String.calculateSubtract(subtrahend: String): BigDecimal {
        val mathContext = MathContext(scale)
        val minuendBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val subtrahendBigDecimal = BigDecimalMath.toBigDecimal(subtrahend.toStandardFormat())
        val result = minuendBigDecimal.subtract(subtrahendBigDecimal, mathContext).stripTrailingZeros()

        checkForOverflow(result)

        return result
    }

    /**
     * Subtract subtrahend from @this and return result in String type.
     * @param subtrahend - number to subtract from @this
     * @return result of calculation in String format
     */
    fun String.subtract(subtrahend: String): String {
        return this.calculateSubtract(subtrahend).toString().toCalculatorFormat()
    }

    /**
     * Subtract subtrahend from @this and return result in String type
     * in plain or engineering format depending on second parameter of function.
     * @param subtrahend - number to subtract from @this
     * @param isScientificNotation - define format of returned String
     * @return result of calculation in String type
     */
    fun String.subtract(subtrahend: String, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculateSubtract(subtrahend).toEngineeringString().toCalculatorFormat()
        else
            this.add(subtrahend)
    }

    /** Calculates multiplication of @this and multiplicand and return result in BigDecimal type.
     * @param multiplicand
     * @return result of calculation in BigDecimal type
     */
    private fun String.calculateMultiply(multiplicand: String): BigDecimal {
        val mathContext = MathContext(scale)
        val multiplierBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val multiplicandBigDecimal = BigDecimalMath.toBigDecimal(multiplicand.toStandardFormat())
        val result = multiplierBigDecimal.multiply(multiplicandBigDecimal, mathContext).stripTrailingZeros()

        checkForOverflow(result)

        return result
    }

    /** Calculates multiplication of @this and multiplicand and return result in String type.
     * @param multiplicand
     * @return result of calculation in String type
     */
    fun String.multiply(multiplicand: String): String {
        return this.calculateMultiply(multiplicand).toString().toCalculatorFormat()
    }

    /** Calculates multiplication of @this and multiplicand and return result in String type
     * in plain or engineering format depending on second parameter of function.
     * @param multiplicand
     * @return result of calculation in String type
     */
    fun String.multiply(multiplicand: String, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculateMultiply(multiplicand).toEngineeringString().toCalculatorFormat()
        else
            this.multiply(multiplicand)
    }

    /** Divide @this to divisor and return result in BigDecimal type.
     * @param divisor
     * @return result of calculation in BigDecimal type
     */
    private fun String.calculateDivide(divisor: String): BigDecimal {
        val mathContext = MathContext(scale)
        val dividendBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val divisorBigDecimal = BigDecimalMath.toBigDecimal(divisor.toStandardFormat())
        val result = dividendBigDecimal.divide(divisorBigDecimal, mathContext).stripTrailingZeros()

        checkForOverflow(result)

        return result
    }

    /** Divide @this to divisor and return result in String type.
     * @param divisor
     * @return result of calculation in String type
     */
    fun String.divide(divisor: String): String {
        return this.calculateDivide(divisor).toString().toCalculatorFormat()
    }

    /** Divide @this to divisor and return result in String type
     * in plain or engineering format depending on second parameter of function.
     * @param divisor
     * @return result of calculation in String type with appropriate format
     */
    fun String.divide(divisor: String, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculateDivide(divisor).toEngineeringString().toCalculatorFormat()
        else
            this.divide(divisor)
    }

    /** Change sign of number in @this
     * @return string that contains number with changed sign
     */
    fun String.negate(): String = BigDecimalMath.toBigDecimal(this.toStandardFormat()).negate().stripTrailingZeros().toString().toCalculatorFormat()

    /** Calculates square root of @this and return result in BigDecimal type.
     * @return result of calculation in BigDecimal type
     */
    private fun String.calculateSQRT(): BigDecimal {
        val mathContext = MathContext(scale)
        val numberBigDecimal = BigDecimalMath.toBigDecimal(this.toStandardFormat())

        return BigDecimalMath.sqrt(numberBigDecimal, mathContext).stripTrailingZeros()
    }

    /** Calculates square root of @this and return result in String type.
     * @return result of calculation in String type
     */
    fun String.sqrt(): String {
        return this.calculateSQRT().toString().toCalculatorFormat()
    }

    /** Calculates square root of @this and return result in String type
     * in plain or engineering format depending on second parameter of function.
     * @return result of calculation in String type with appropriate format
     */
    fun String.sqrt(isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculateSQRT().toEngineeringString().toCalculatorFormat()
        else
            this.sqrt()
    }

    /** Check if BigDecimal number is in certain range and throw exception if not.
     * @param number being checked
      */
    fun checkForOverflow(result: BigDecimal) {
        val maxValueString = "1E+10000"
        val minValueString = "-1E+10000"
        val minFractionValueString = "1E-9999"
        val maxFractionValueString = "-1E-9999"
        val zeroString = "0"

        val maxValueBigDecimal = BigDecimalMath.toBigDecimal(maxValueString)
        val minValueBigDecimal = BigDecimalMath.toBigDecimal(minValueString)
        val minFractionValueBigDecimal = BigDecimalMath.toBigDecimal(minFractionValueString)
        val maxFractionValueBigDecimal = BigDecimalMath.toBigDecimal(maxFractionValueString)
        val zeroBigDecimal = BigDecimalMath.toBigDecimal(zeroString)

        if ((result.compareTo(maxValueBigDecimal) != -1) ||
            (result.compareTo(minValueBigDecimal) != 1) ||
            ((result.compareTo(minFractionValueBigDecimal) == -1) &&
                    (result.compareTo(maxFractionValueBigDecimal) == 1) &&
                    (result.compareTo(zeroBigDecimal) != 0)))
            throw ArithmeticException("Overflow of calculated number.")
    }

    /**
     * Changes @this string, contained number, to appropriate format to display it on calculator.
     * Format of @this:         212.02      2E+12   3.34E-13
     * Format of calculator:    212,02      2,e+12  3,34e-13
     * @return calculator string
     */
    fun String.toCalculatorFormat(): String {
        return when {
            this.contains("E") && this.contains(".") -> this.replace(".", ",").replace("E", "e")
            this.contains("E") -> this.replace("E", ",e")
            else -> this.replace(".",",")
        }
    }

    /**
     * Changes @this string, contained number in format to display it on calculator
     * to format, appropriate to convert it to BigDecimal type.
     * Format of calculator:        212.02      2E+12   3.34E-13
     * Format of @this:             212,02      2,e+12  3,34e-13
     * @return calculator string
     */
    fun String.toStandardFormat(): String {
        return when {
            this.contains(",e") -> this.replace(",e", "E")
            else -> this.commaTruncate().replace(",", ".").replace("e", "E")
        }
    }

    /**
     * Deletes last character if it is comma.
     */
    fun String.commaTruncate(): String {
        return if (this.last() == ',') this.dropLast(1)
        else this
    }

    /**
     * Returns string representation of @this BigDecimal in scientific or
     * plain format depending on @this value - is it in certain range or
     * out of range.
     * @return number in string representation in scientific or plain format
     */
    fun BigDecimal.toResultString(): String {
        val maxValueString = "1E+${SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER}"
        val minValueString = "-1E+${SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER}"
        val minFractionValueString = "1E-${SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER}"
        val maxFractionValueString = "-1E-${SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER}"
        val zeroString = "0"

        val maxValueBigDecimal = BigDecimalMath.toBigDecimal(maxValueString)
        val minValueBigDecimal = BigDecimalMath.toBigDecimal(minValueString)
        val minFractionValueBigDecimal = BigDecimalMath.toBigDecimal(minFractionValueString)
        val maxFractionValueBigDecimal = BigDecimalMath.toBigDecimal(maxFractionValueString)
        val zeroBigDecimal = BigDecimalMath.toBigDecimal(zeroString)

        return if ((this.compareTo(maxValueBigDecimal) != -1) ||
            (this.compareTo(minValueBigDecimal) != 1) ||
            ((this.compareTo(minFractionValueBigDecimal) == -1) &&
                    (this.compareTo(maxFractionValueBigDecimal) == 1) &&
                    (this.compareTo(zeroBigDecimal) != 0)))
            this.toString()
        else
            this.toPlainString()
    }

    /**
     * Returns string representation of @this BigDecimal in scientific format.
     * @return string representation of number in scientific format.
     */
    fun BigDecimal.toScientificNotationString(): String {
        val mantissa = BigDecimalMath.mantissa(this)
        val exponent = BigDecimalMath.exponent(this)

        return if (exponent.compareTo(0) != -1)
            "${mantissa}E+$exponent"
        else
            "${mantissa}E$exponent"
    }
}