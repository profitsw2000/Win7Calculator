package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import java.text.DecimalFormat

interface GeneralCalculatorState : CalculatorState {

    val generalCalculatorDataEntity: GeneralCalculatorDataEntity

    fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

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
        val decimalFormat = DecimalFormat("###.################")//("###.################")
        return decimalFormat.format(number).replace('.', ',')
    }
}