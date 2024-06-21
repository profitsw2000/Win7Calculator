package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import kotlin.math.sqrt

class FirstOperandInputState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action){
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.PLUS, "+")
            CalculatorAction.AddToMemory -> addNumberToMemory(generalCalculatorDataEntity)
            CalculatorAction.Backspace -> deleteLastDigit(generalCalculatorDataEntity)
            CalculatorAction.Clear -> clearAllData(generalCalculatorDataEntity)
            CalculatorAction.ClearEntered -> clearAllData(generalCalculatorDataEntity)
            CalculatorAction.ClearMemory -> clearMemory(generalCalculatorDataEntity)
            is CalculatorAction.Digit -> appendDigit(generalCalculatorDataEntity, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.DIVIDE, "/")
            CalculatorAction.Equal -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Multiply -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.MULTIPLY, "*")
            CalculatorAction.Percentage -> percentageOperation(generalCalculatorDataEntity)
            CalculatorAction.PlusMinus -> negateOperand(generalCalculatorDataEntity)
            CalculatorAction.ReadMemory -> readMemory(generalCalculatorDataEntity)
            CalculatorAction.Recipoc -> reciprocOperation(generalCalculatorDataEntity)
            CalculatorAction.SaveToMemory -> saveToMemory(generalCalculatorDataEntity)
            CalculatorAction.SquareRoot -> calculateSquareRoot(generalCalculatorDataEntity)
            CalculatorAction.Subtract -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.MINUS, "-")
            CalculatorAction.SubtractFromMemory -> subtractNumberFromMemory(generalCalculatorDataEntity)
        }
    }

    /**
     * Function to clear calculator memory
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but memoryNumber field set to null
     */
    private fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste to mainString field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but number from memory field converted to string and copied to mainString - if memoryNumber field contains any number,
     * if not - set main string field to "0"
     */
    private fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            InitialState(generalCalculatorDataEntity.copy(mainString = "0"))
        }
        else {
            InitialState(generalCalculatorDataEntity.copy(mainString = generalCalculatorDataEntity.memoryNumber.toString()))
        }
    }

    /**
     * Add number to number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but with other memoryNumber. mainString field converted to number and added to memoryNumber field.
     */
    private fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val firstOperandNumber: Double = generalCalculatorDataEntity.mainString.toDouble()
        return if (generalCalculatorDataEntity.memoryNumber != null)
            InitialState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.plus(firstOperandNumber)))
        else InitialState(generalCalculatorDataEntity.copy(memoryNumber = firstOperandNumber))
    }

    /**
     * Subtract number from number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but with other memoryNumber. mainString field converted to number and memoryNumber field subtracted by that number.
     */
    private fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val firstOperandNumber: Double = generalCalculatorDataEntity.mainString.toDouble()
        return if (generalCalculatorDataEntity.memoryNumber != null)
            InitialState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.minus(firstOperandNumber)))
        else InitialState(generalCalculatorDataEntity.copy(memoryNumber = -(firstOperandNumber)))
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    private fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.mainString.toDouble()))
    }

    /**
     * Appended digit in string format to mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @param digitToAppend - contains string with digit that has to be added to end of mainString of calculator data
     * @return FirstOperandInputState with new mainString field of calculator data
     */
    private fun appendDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digitToAppend: String): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.mainString == "0" && digitToAppend == "0") this
        else if (generalCalculatorDataEntity.mainString.contains(",") && digitToAppend == ",") this
        else if (generalCalculatorDataEntity.mainString.length >= 16) this
        else FirstOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = "${generalCalculatorDataEntity.mainString}${digitToAppend}"
            )
        )
    }

    /**
     * Clear all entered data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with initial calculator data except memoryNumber field. It contains old value.
     */
    private fun clearAllData(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(GeneralCalculatorDataEntity(memoryNumber = generalCalculatorDataEntity.memoryNumber))
    }

    /**
     * Function removes last character(digit) in mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandInputState with new mainString field of calculator data if string contains
     * more than 1 character, otherwise InitialState with mainString field turns to "0"
     */
    private fun deleteLastDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.mainString.length > 1) {
            FirstOperandInputState(generalCalculatorDataEntity.copy(mainString = generalCalculatorDataEntity.mainString.dropLast(1)))
        } else if (generalCalculatorDataEntity.mainString.length == 2
            && generalCalculatorDataEntity.mainString.first() != '-') {
            FirstOperandInputState(generalCalculatorDataEntity.copy(mainString = generalCalculatorDataEntity.mainString.dropLast(1)))
        } else {
            InitialState(generalCalculatorDataEntity.copy(mainString = "0"))
        }
    }

    /**
     * Changes sign of the entered number. It's contained in mainString field of calculator data.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandInputState with new mainString field of calculator data
     */
    private fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val negatedNumber = (0 - calculatorStringToDouble(generalCalculatorDataEntity.mainString))
        return FirstOperandInputState(generalCalculatorDataEntity.copy(
            mainString = doubleToCalculatorString(negatedNumber)
        ))
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
}