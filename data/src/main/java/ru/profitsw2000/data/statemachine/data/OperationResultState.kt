package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import kotlin.math.sqrt

class OperationResultState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.AddToMemory -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Backspace -> this
            CalculatorAction.Clear -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.ClearEntered -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.ClearMemory -> InitialState(generalCalculatorDataEntity)
            is CalculatorAction.Digit -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Divide -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Equal -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Multiply -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Percentage -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.PlusMinus -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.ReadMemory -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Recipoc -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.SaveToMemory -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.SquareRoot -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Subtract -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.SubtractFromMemory -> InitialState(generalCalculatorDataEntity)
        }
    }

    /**
     * Clear internal memory of calculator
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return OperationResultState with cleared memory of calculator data
     */
    private fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return OperationResultState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste
     * to mainString field.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with from memory field converted to string and
     * copied to mainString - if memoryNumber field contains any number,
     * if not - set main string field to "0".
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
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return OperationResultState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    private fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return OperationResultState(
            generalCalculatorDataEntity.copy(
                memoryNumber = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Add number to number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return OperationResultState with same calculator data but with other memoryNumber. mainString field converted to number and added to memoryNumber field.
     */
    private fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            OperationResultState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.plus(operandNumber)))
        else OperationResultState(generalCalculatorDataEntity.copy(memoryNumber = operandNumber))
    }

    /**
     * Subtract number from number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return OperationResultState with same calculator data but with other memoryNumber. mainString field converted to number and memoryNumber field subtracted by that number.
     */
    private fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            OperationResultState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.minus(operandNumber)))
        else OperationResultState(generalCalculatorDataEntity.copy(memoryNumber = -(operandNumber)))
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
     * Changes sign of the result number. It's contained in mainString field of calculator data. History string
     * field of calculator data appended with action name string.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with new mainString and history fields of calculator data
     */
    private fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val negatedNumber = (0 - calculatorStringToDouble(generalCalculatorDataEntity.mainString))

        return InitialState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(negatedNumber),
                historyString = "negate(${generalCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Calculates square root of input number and add action to history of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with result of square root calculation in main string field and
     * action added to history string of calculator data.
     * Or ErrorState if mainString contain negative number.
     * */
    private fun calculateSquareRoot(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        return if (calculatorStringToDouble(generalCalculatorDataEntity.mainString) < 0.0) {
            ErrorState(
                GeneralCalculatorDataEntity(
                    historyString = "sqrt(${generalCalculatorDataEntity.mainString})",
                    memoryNumber = generalCalculatorDataEntity.memoryNumber,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } else {
            InitialState(
                generalCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(sqrt(calculatorStringToDouble(generalCalculatorDataEntity.mainString))),
                    historyString = "sqrt(${generalCalculatorDataEntity.mainString})"
                )
            )
        }
    }

    /**
     * Writes digit in string format to mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @param digitToInput - contains string with digit that has to be added to mainString of calculator data
     * @return FirstOperandInputState with new mainString and historyString fields of calculator data
     */
    private fun inputDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digitToInput: String): GeneralCalculatorState {

        return if (digitToInput == ",") FirstOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = "0,"
            )
        )
        else FirstOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = digitToInput
            )
        )
    }

    /**
     * Calculates input number in main string percent of the same number
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with main string got from calculation of number, as result of number that equals percentage of [mainString]
     * percent from number equals [mainString]. String of result number added to history string field of calculator data.
     */
    private fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val percentageResult = (calculatorStringToDouble(generalCalculatorDataEntity.mainString)/100)*calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return InitialState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(percentageResult),
                historyString = "${doubleToCalculatorString(percentageResult)}"
            )
        )
    }

    /**
     * Calculates inversely proportioned number to that contained in main string field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState if calculation completed successfully with result written in main string field and
     * action written to history string. If math operation throws exception, then ErrorState returned by function with
     * error code written to corresponded field and action written to history string of calculator data
     */
    private fun reciprocOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return try {
            InitialState(generalCalculatorDataEntity.copy(
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
     * Calculates result of math operation and save it in calculator data.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return OperationResultState where mainString field contains result of math operation between number in
     * operand field and mainString fields. Type of math operation is in operationType field of calculator
     * data.
     */
    private fun calculateResult(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val firstOperand = generalCalculatorDataEntity.operand
        val secondOperand = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
        val operationResult = when(generalCalculatorDataEntity.operationType) {
            OperationType.PLUS -> firstOperand + secondOperand
            OperationType.MINUS -> firstOperand - secondOperand
            OperationType.MULTIPLY -> firstOperand * secondOperand
            OperationType.DIVIDE -> firstOperand / secondOperand
            OperationType.NO_OPERATION -> 0.0
        }

        return OperationResultState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(operationResult),
                historyString = ""
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