package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.constants.MAIN_STRING_MAX_DIGIT_NUMBER
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
        val numberOfWholeInts = number.toString().split('.').elementAt(0).length
        val newScale = MAIN_STRING_MAX_DIGIT_NUMBER - numberOfWholeInts
        val decimalNumber = BigDecimal(number).setScale(newScale, RoundingMode.HALF_EVEN)

        val decimalFormat = DecimalFormat("###.################")//("###.################")
        return decimalFormat.format(decimalNumber).replace('.', ',')
    }
}