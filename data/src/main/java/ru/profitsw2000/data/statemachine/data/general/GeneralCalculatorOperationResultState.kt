package ru.profitsw2000.data.statemachine.data.general

import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorBaseState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import kotlin.math.sqrt

class GeneralCalculatorOperationResultState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorBaseState {

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.PLUS, "+")
            CalculatorAction.AddToMemory -> addNumberToMemory(generalCalculatorDataEntity)
            CalculatorAction.Backspace -> this
            CalculatorAction.Clear -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearEntered -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearMemory -> clearMemory(generalCalculatorDataEntity)
            is CalculatorAction.Digit -> inputDigit(generalCalculatorDataEntity, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.DIVIDE, "/")
            CalculatorAction.Equal -> calculateResult(generalCalculatorDataEntity)
            CalculatorAction.Multiply -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.MULTIPLY, "*")
            CalculatorAction.Percentage -> percentageOperation(generalCalculatorDataEntity)
            CalculatorAction.PlusMinus -> negateOperand(generalCalculatorDataEntity)
            CalculatorAction.ReadMemory -> readMemory(generalCalculatorDataEntity)
            CalculatorAction.Recipoc -> reciprocOperation(generalCalculatorDataEntity)
            CalculatorAction.SaveToMemory -> saveToMemory(generalCalculatorDataEntity)
            CalculatorAction.SquareRoot -> calculateSquareRoot(generalCalculatorDataEntity)
            CalculatorAction.Subtract -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.MINUS, "-")
            CalculatorAction.SubtractFromMemory -> subtractNumberFromMemory(generalCalculatorDataEntity)
            else -> this
        }
    }

    /**
     * Clear internal memory of calculator
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorOperationResultState with cleared memory of calculator data
     */
    override fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return GeneralCalculatorOperationResultState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste
     * to mainString field.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorInitialState with from memory field converted to string and
     * copied to mainString - if memoryNumber field contains any number,
     * if not - set main string field to "0".
     */
    override fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            GeneralCalculatorInitialState(generalCalculatorDataEntity.copy(mainString = "0"))
        }
        else {
            GeneralCalculatorInitialState(generalCalculatorDataEntity.copy(mainString = doubleToCalculatorString(generalCalculatorDataEntity.memoryNumber)))
        }
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorOperationResultState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    override fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return GeneralCalculatorOperationResultState(
            generalCalculatorDataEntity.copy(
                memoryNumber = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Add number to number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorOperationResultState with same calculator data but with other memoryNumber. mainString field converted to number and added to memoryNumber field.
     */
    override fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            GeneralCalculatorOperationResultState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.plus(operandNumber)))
        else GeneralCalculatorOperationResultState(generalCalculatorDataEntity.copy(memoryNumber = operandNumber))
    }

    /**
     * Subtract number from number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorOperationResultState with same calculator data but with other memoryNumber. mainString field converted to number and memoryNumber field subtracted by that number.
     */
    override fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            GeneralCalculatorOperationResultState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.minus(operandNumber)))
        else GeneralCalculatorOperationResultState(generalCalculatorDataEntity.copy(memoryNumber = -(operandNumber)))
    }

    /**
     * Clears all data, contained in fields mainString, historyString and operand fields of calculator data.
     * OperationType set to NO_OPERATION state
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with some fields of calculator data set to default state
     */
    override fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return GeneralCalculatorInitialState(generalCalculatorDataEntity.copy(
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
     * @return GeneralCalculatorInitialState with new mainString and history fields of calculator data
     */
    override fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val negatedNumber = (0 - calculatorStringToDouble(generalCalculatorDataEntity.mainString))

        return GeneralCalculatorInitialState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(negatedNumber),
                historyString = "negate(${generalCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Calculates square root of input number and add action to history of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorInitialState with result of square root calculation in main string field and
     * action added to history string of calculator data.
     * Or GeneralCalculatorErrorState if mainString contain negative number.
     * */
    override fun calculateSquareRoot(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        return if (calculatorStringToDouble(generalCalculatorDataEntity.mainString) < 0.0) {
            GeneralCalculatorErrorState(
                GeneralCalculatorDataEntity(
                    historyString = "sqrt(${generalCalculatorDataEntity.mainString})",
                    memoryNumber = generalCalculatorDataEntity.memoryNumber,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } else {
            GeneralCalculatorInitialState(
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
     * @return GeneralCalculatorFirstOperandInputState with new mainString and historyString fields of calculator data
     */
    override fun inputDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digitToInput: String): GeneralCalculatorState {

        return if (digitToInput == ",") GeneralCalculatorFirstOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = "0,"
            )
        )
        else GeneralCalculatorFirstOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = digitToInput
            )
        )
    }

    /**
     * Change type operation field of calculator data. Number from mainString field recorded to
     * operand field.
     * @param1 generalCalculatorDataEntity - contains current calculator data,
     * @param2 operationType - type of next primitive math operation
     * @param3 operationString - operation sign, need to be added in history string
     * @return GeneralCalculatorPrimitiveMathOperationState with new operationType field and operand fields of calculator data.
     */
    override fun primitiveMathOperation(
        generalCalculatorDataEntity: GeneralCalculatorDataEntity,
        operationType: OperationType,
        operationString: String
    ): GeneralCalculatorState {

        val historyString = "${generalCalculatorDataEntity.mainString}" +
                "$HISTORY_STRING_SPACE_LETTER" +
                "$operationString"

        return GeneralCalculatorPrimitiveMathOperationState(
            generalCalculatorDataEntity.copy(
                historyString = historyString,
                operand = calculatorStringToDouble(generalCalculatorDataEntity.mainString),
                operationType = operationType
            )
        )
    }

    /**
     * Calculates input number in main string percent of the same number
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorInitialState with main string got from calculation of number, as result of number that equals percentage of [mainString]
     * percent from number equals [mainString]. String of result number added to history string field of calculator data.
     */
    override fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val percentageResult = (calculatorStringToDouble(generalCalculatorDataEntity.mainString)/100)*calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return GeneralCalculatorInitialState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(percentageResult),
                historyString = "${doubleToCalculatorString(percentageResult)}"
            )
        )
    }

    /**
     * Calculates inversely proportioned number to that contained in main string field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorInitialState if calculation completed successfully with result written in main string field and
     * action written to history string. If math operation throws exception, then GeneralCalculatorErrorState returned by function with
     * error code written to corresponded field and action written to history string of calculator data
     */
    override fun reciprocOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return try {
            GeneralCalculatorInitialState(generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(1/(calculatorStringToDouble(generalCalculatorDataEntity.mainString))),
                historyString = "reciproc(${generalCalculatorDataEntity.mainString})"
            ))
        } catch (numberFormatException: NumberFormatException) {
            GeneralCalculatorErrorState(generalCalculatorDataEntity.copy(
                historyString = "1/${generalCalculatorDataEntity.mainString}",
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            ))
        } catch (exception: Exception) {
            GeneralCalculatorErrorState(generalCalculatorDataEntity.copy(
                historyString = "1/${generalCalculatorDataEntity.mainString}",
                errorCode = UNKNOWN_ERROR_CODE
            ))
        }
    }

    /**
     * Calculates result of math operation and save it in calculator data.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorOperationResultState where mainString field contains result of math operation between number in
     * operand field and mainString fields. Type of math operation is in operationType field of calculator
     * data.
     */
    override fun calculateResult(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val firstOperand = generalCalculatorDataEntity.operand
        val secondOperand = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
        val operationResult = when(generalCalculatorDataEntity.operationType) {
            OperationType.PLUS -> firstOperand + secondOperand
            OperationType.MINUS -> firstOperand - secondOperand
            OperationType.MULTIPLY -> firstOperand * secondOperand
            OperationType.DIVIDE -> firstOperand / secondOperand
            OperationType.NO_OPERATION -> 0.0
        }

        return GeneralCalculatorOperationResultState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(operationResult),
                historyString = ""
            )
        )
    }
}