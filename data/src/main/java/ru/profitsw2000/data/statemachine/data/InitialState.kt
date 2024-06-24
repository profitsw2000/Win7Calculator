package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import kotlin.math.sqrt

class InitialState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.PLUS, "+")
            CalculatorAction.AddToMemory -> addMemory(generalCalculatorDataEntity)
            CalculatorAction.Clear -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearEntered -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearMemory -> clearMemory(generalCalculatorDataEntity)
            is CalculatorAction.Digit -> digitClicked(generalCalculatorDataEntity, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.DIVIDE, "/")
            CalculatorAction.Multiply -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.MULTIPLY, "*")
            CalculatorAction.Percentage -> percentageOperation(generalCalculatorDataEntity)
            CalculatorAction.PlusMinus -> negateInput(generalCalculatorDataEntity)
            CalculatorAction.ReadMemory -> readMemory(generalCalculatorDataEntity)
            CalculatorAction.Recipoc -> reciprocOperation(generalCalculatorDataEntity)
            CalculatorAction.SquareRoot -> calculateSquareRoot(generalCalculatorDataEntity)
            CalculatorAction.Subtract -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.MINUS, "-")
            CalculatorAction.SubtractFromMemory -> subtractMemory(generalCalculatorDataEntity)
            else -> this
        }
    }

    /**
     * Clear internal memory of calculator
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with cleared memory of calculator data
     */
    private fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads internal memory and copy it to mainString of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with new main string field of calculator data
     */
    private fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            InitialState(generalCalculatorDataEntity.copy())
        } else {
            InitialState(
                generalCalculatorDataEntity.copy(mainString = doubleToCalculatorString(generalCalculatorDataEntity.memoryNumber))
            )
        }
    }

    /**
     * Add number, that contain main string of calculators display, to memory number
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with memory number field incremented on number, that contained in main string field
     */
    private fun addMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            InitialState(
                generalCalculatorDataEntity.copy(memoryNumber = calculatorStringToDouble(generalCalculatorDataEntity.mainString))
            )
        } else {
            InitialState(
                generalCalculatorDataEntity.copy(memoryNumber = (generalCalculatorDataEntity.memoryNumber + calculatorStringToDouble(generalCalculatorDataEntity.mainString)))
            )
        }
    }

    /**
     * Subtract number, that contain main string of calculators display, from memory number
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with memory number field decremented on number, that contained in main string field
     */
    private fun subtractMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            InitialState(
                generalCalculatorDataEntity.copy(memoryNumber = calculatorStringToDouble(generalCalculatorDataEntity.mainString))
            )
        } else {
            InitialState(
                generalCalculatorDataEntity.copy(memoryNumber = (generalCalculatorDataEntity.memoryNumber - calculatorStringToDouble(generalCalculatorDataEntity.mainString)))
            )
        }
    }

    /**
     * Clears all data, contained in fields mainString, historyString and operand fields of calculator data.
     * OperationType set to NO_OPERATION state
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with some fields of calculator data set to default state
     */
    private fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(generalCalculatorDataEntity.copy(
            mainString = "0",
            historyString = "",
            operand = 0.0,
            operationType = OperationType.NO_OPERATION
        ))
    }

    /**
     * Changes inputs number sign to opposite and writes action to history.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with changed main string and writes action to history string of calculator data
     */
    private fun negateInput(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.mainString == "0") {
            InitialState(generalCalculatorDataEntity.copy())
        } else {
            InitialState(generalCalculatorDataEntity.copy(
                mainString = negateNumberString(generalCalculatorDataEntity.mainString),
                historyString = negateHistoryString(generalCalculatorDataEntity.mainString, generalCalculatorDataEntity.historyString)
            ))
        }
    }

    /**
     * Calculates square root of input number and writes action to history of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return OperationResultState with result of square root calculation in main string field and
     * action written to history string of calculator data
     */
    private fun calculateSquareRoot(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val sqrtDouble = sqrt(calculatorStringToDouble(generalCalculatorDataEntity.mainString))
        val sqrtString = doubleToCalculatorString(sqrtDouble)

        return OperationResultState(
            generalCalculatorDataEntity.copy(
                mainString = sqrtString,
                historyString = if (generalCalculatorDataEntity.historyString == "") "sqrt(${generalCalculatorDataEntity.mainString})"
                else "sqrt(${generalCalculatorDataEntity.historyString})"
            )
        )
    }

    /**
     * Changed current state if clicked button is not 0. Clicked digit stored in main string field of calculator data.
     * @param1 generalCalculatorDataEntity - contains current calculator data
     * @param2 digit - contain string with number of clicked button
     * @return FirstOperandInputState if clicked digit is not 0 with digit stored in main string field, otherwise returns same state with same calculator data.
     */
    private fun digitClicked(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digit: String): GeneralCalculatorState {
        return if (digit  == "0") this
        else FirstOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = digit
            )
        )
    }

    /**
     * Changes current state to PrimitiveMathOperationState, input number and operation sign writes to history string of calculator data,
     * same as operation type.
     * @param1 generalCalculatorDataEntity - contains current calculator data,
     * @param2 operationType - type of primitive math operation
     * @param3 operationString - operation sign, need to be added in history string
     * @return PrimitiveMathOperationState with changed historyString and operationType fields of calculator data
     */
    private fun primitiveMathOperation(
        generalCalculatorDataEntity: GeneralCalculatorDataEntity,
        operationType: OperationType,
        operationString: String
    ): GeneralCalculatorState {
        val historyString = "${generalCalculatorDataEntity.historyString} ${generalCalculatorDataEntity.mainString} $operationString"
        return PrimitiveMathOperationState(
            generalCalculatorDataEntity.copy(
                historyString = historyString,
                operationType = operationType,
                operand = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Calculates input number in main string percent of the number in operand of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return OperationResultState with "0" in main and history string of calculator data
     */
    private fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return OperationResultState(generalCalculatorDataEntity.copy(
            mainString = "0",
            historyString = "0"))
    }

    /**
     * Calculates inversely proportioned number to that contained in main string field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return OperationResultState if calculation completed successfully with result written in main string field and
     * action written in history string. If math operation throws exception, then ErrorState returned by function with
     * error code written to corresponded field and action written to history string of calculator data
     */
    private fun reciprocOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return try {
            OperationResultState(generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(1/(calculatorStringToDouble(generalCalculatorDataEntity.mainString))),
                historyString = "reciproc(${generalCalculatorDataEntity.mainString})"
            ))
        } catch (arithmeticException: ArithmeticException) {
            ErrorState(generalCalculatorDataEntity.copy(
                historyString = "1/${generalCalculatorDataEntity.mainString}",
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            ))
        } catch (exception: Exception) {
            ErrorState(generalCalculatorDataEntity.copy(
                historyString = "1/${generalCalculatorDataEntity.mainString}",
                errorCode = UNKNOWN_ERROR_CODE
            ))
        }
    }

    /**
     * Converts string to double
     * @param calculatorString - string to convert
     * @return converted number
     */
    private fun calculatorStringToDouble(calculatorString: String): Double {
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
    private fun doubleToCalculatorString(number: Double): String {
        return if(number.rem(1).equals(0.0)) {
            String.format("%,0f", number)
        } else {
            number.toString()
        }
    }

    private fun negateNumberString(stringNumber: String): String {
        return stringNumber.apply {
            doubleToCalculatorString(0 - calculatorStringToDouble(this))
        }
    }

    private fun negateHistoryString(stringNumber: String, historyString: String): String {
        return historyString.apply {
            if (historyString == "") "negate($stringNumber)"
            else "negate($historyString)"
        }
    }
}