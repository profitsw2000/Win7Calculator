package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.constants.GENERAL_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

interface CalculatorState {

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
    fun doubleToCalculatorString(number: Double): String {

        val decimalFormat = DecimalFormat("###.##################")//("###.################")
        val numberOfWholeInts = decimalFormat.format(number).split(',').elementAt(0).length
        val newScale = GENERAL_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER - numberOfWholeInts
        val decimalNumber = BigDecimal(number).setScale(newScale, RoundingMode.HALF_UP)

        return if (!isOutOfMaxDigitNumber(number)) decimalFormat.format(decimalNumber).replace('.', ',')
        else number.toString().replace('.', ',').replace("E", "e+")
    }

    /**
     * Converts double number to string with require format - traditional or with scientific notation.
     * Required format defined by second parameter.
     * @param number - double number to convert
     * @param isScientificNotation - boolean variable, that defines required format. If it false
     * then required format is traditional, otherwise it is scientific notation.
     */
    fun doubleToCalculatorString(number: Double, isScientificNotation: Boolean): String {
        return if (!isScientificNotation) {
            if (isOutOfMaxDigitNumber(number)) number.toString().replace('.', ',')
            else doubleToCalculatorString(number)
        } else {
            number.toString().replace('.', ',')
        }
    }

    /**
     * Defines if double number is in certain range, so contain certain amount of digit.
     * @param number - double number to define it in range
     * @return - true if is out of range and false if otherwise
     */
    fun isOutOfMaxDigitNumber(number: Double): Boolean {

        return !((number < 1.0E16 && number > 1.0E-16) || (number > -1.0E16 && number < -1.0E-16))

    }
}