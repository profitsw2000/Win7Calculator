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

class GeneralCalculatorSecondOperandReadState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorBaseState {

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.PLUS, "+")
            CalculatorAction.AddToMemory -> addNumberToMemory(generalCalculatorDataEntity)
            CalculatorAction.Backspace -> this
            CalculatorAction.Clear -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearEntered -> clearMainString(generalCalculatorDataEntity)
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
     * @return Same state with cleared memory of calculator data
     */
    override fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return GeneralCalculatorSecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste
     * to mainString field. Additionaly, remove from historyString field last entered action while on GeneralCalculatorSecondOperandReadState
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorSecondOperandReadState with same calculator data but number from memory field converted to string and copied to mainString - if memoryNumber field contains any number,
     * if not - set main string field to "0". Also history string field updated by removing last action entrance while on GeneralCalculatorSecondOperandReadState
     */
    override fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            GeneralCalculatorSecondOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = "0",
                    historyString = generalCalculatorDataEntity.historyString.replaceAfterLast(
                        HISTORY_STRING_SPACE_LETTER, ""
                    )
                )
            )
        }
        else {
            GeneralCalculatorSecondOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(generalCalculatorDataEntity.memoryNumber),
                    historyString = generalCalculatorDataEntity.historyString.replaceAfterLast(
                        HISTORY_STRING_SPACE_LETTER, ""
                    )
                )
            )
        }
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorSecondOperandReadState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    override fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return GeneralCalculatorSecondOperandReadState(
            generalCalculatorDataEntity.copy(
                memoryNumber = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Add number to number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorSecondOperandReadState with same calculator data but with other memoryNumber. mainString field converted to number and added to memoryNumber field.
     */
    override fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            GeneralCalculatorSecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.plus(operandNumber)))
        else GeneralCalculatorSecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = operandNumber))
    }

    /**
     * Subtract number from number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorSecondOperandReadState with same calculator data but with other memoryNumber. mainString field converted to number and memoryNumber field subtracted by that number.
     */
    override fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            GeneralCalculatorSecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.minus(operandNumber)))
        else GeneralCalculatorSecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = -(operandNumber)))
    }

    /**
     * Clears mainString field of calculator data and set it to "0", also remove from
     * historyString field last entered action while on GeneralCalculatorSecondOperandReadState
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorSecondOperandInputState where mainString field calculator data contain "0"
     */
    private fun clearMainString(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val historyString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) generalCalculatorDataEntity.historyString
        else generalCalculatorDataEntity.historyString.replaceAfterLast(HISTORY_STRING_SPACE_LETTER, "")

        return GeneralCalculatorSecondOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = "0",
                historyString = historyString
            )
        )
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
     * Changes sign of the entered number. It's contained in mainString field of calculator data. History string
     * field of calculator data appended with action name string
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorSecondOperandReadState with new mainString and history fields of calculator data
     */
    override fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val negatedNumber = (0 - calculatorStringToDouble(generalCalculatorDataEntity.mainString))
        val baseString = generalCalculatorDataEntity.historyString.replaceAfterLast(
            HISTORY_STRING_SPACE_LETTER, ""
        )
        val lastActionString = "negate(${generalCalculatorDataEntity.historyString.takeLastWhile { it != ' ' }})"
        val appendedHistoryString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}negate(${generalCalculatorDataEntity.mainString})"
        } else {
            "$baseString$lastActionString"
        }

        return GeneralCalculatorSecondOperandReadState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(negatedNumber),
                historyString = appendedHistoryString
            )
        )
    }

    /**
     * Calculates square root of input number and add action to history of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorInitialState with result of square root calculation in main string field and
     * action added to history string of calculator data
     */
    override fun calculateSquareRoot(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val baseString = generalCalculatorDataEntity.historyString.replaceAfterLast(
            HISTORY_STRING_SPACE_LETTER, ""
        )
        val lastActionString = "sqrt(${generalCalculatorDataEntity.historyString.takeLastWhile { it != ' ' }})"
        val appendedHistoryString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}" +
                    HISTORY_STRING_SPACE_LETTER +
                    "sqrt(${generalCalculatorDataEntity.mainString})"
        } else {
            "$baseString$lastActionString"
        }

        return try {
            val sqrtString = doubleToCalculatorString(sqrt(calculatorStringToDouble(generalCalculatorDataEntity.mainString)))

            GeneralCalculatorSecondOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = sqrtString,
                    historyString = appendedHistoryString
                )
            )
        } catch (numberFormatException: NumberFormatException) {
            GeneralCalculatorErrorState(
                generalCalculatorDataEntity.copy(
                    historyString = appendedHistoryString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            GeneralCalculatorErrorState(
                generalCalculatorDataEntity.copy(
                    historyString = appendedHistoryString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }

/*        val sqrtString = doubleToCalculatorString(sqrt(calculatorStringToDouble(generalCalculatorDataEntity.mainString)))
        val baseString = generalCalculatorDataEntity.historyString.replaceAfterLast(
            HISTORY_STRING_SPACE_LETTER, ""
        )
        val lastActionString = "sqrt(${generalCalculatorDataEntity.historyString.takeLastWhile { it != ' ' }})"
        val appendedHistoryString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}sqrt(${generalCalculatorDataEntity.mainString})"
        } else {
            "$baseString$lastActionString"
        }

        return GeneralCalculatorSecondOperandReadState(
            generalCalculatorDataEntity.copy(
                mainString = sqrtString,
                historyString = appendedHistoryString
            )
        )*/
    }

    /**
     * Writes digit in string format to mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @param digitToInput - contains string with digit that has to be added to mainString of calculator data
     * @return GeneralCalculatorSecondOperandInputState with new mainString and historyString fields of calculator data
     */
    override fun inputDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digitToInput: String): GeneralCalculatorState {

        return if (digitToInput == ",") GeneralCalculatorSecondOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = "0,",
                historyString = generalCalculatorDataEntity.historyString.replaceAfterLast(
                    HISTORY_STRING_SPACE_LETTER, "").dropLast(1)
            )
        )
        else GeneralCalculatorSecondOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = digitToInput,
                historyString = generalCalculatorDataEntity.historyString.replaceAfterLast(
                    HISTORY_STRING_SPACE_LETTER, "").dropLast(1)
            )
        )
    }

    /**
     * Make calculation and update calculator data - in mainString field result of operation,
     * historyString field appended with performed action and operationType updated depending
     * on clicked math operation sign.
     * @param1 generalCalculatorDataEntity - contains current calculator data,
     * @param2 operationType - type of next primitive math operation
     * @param3 operationString - operation sign, need to be added in history string
     * @return GeneralCalculatorPrimitiveMathOperationState with new operationType field and updated historyString,
     * mainString and operand fields of calculator data.
     */
    override fun primitiveMathOperation(
        generalCalculatorDataEntity: GeneralCalculatorDataEntity,
        operationType: OperationType,
        operationString: String
    ): GeneralCalculatorState {

        val historyString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}" +
                    "$HISTORY_STRING_SPACE_LETTER" +
                    "${generalCalculatorDataEntity.mainString} " +
                    "$operationString"
        } else {
            "${generalCalculatorDataEntity.historyString}" +
                    "$HISTORY_STRING_SPACE_LETTER" +
                    "$operationString"
        }
        val firstOperand = generalCalculatorDataEntity.operand
        val secondOperand = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
        val operationResult = when(generalCalculatorDataEntity.operationType) {
            OperationType.PLUS -> firstOperand + secondOperand
            OperationType.MINUS -> firstOperand - secondOperand
            OperationType.MULTIPLY -> firstOperand * secondOperand
            OperationType.DIVIDE -> firstOperand / secondOperand
            OperationType.NO_OPERATION -> 0.0
        }

        return GeneralCalculatorPrimitiveMathOperationState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(operationResult),
                historyString = historyString,
                operand = operationResult,
                operationType = operationType
            )
        )
    }

    /**
     * Calculates input number in main string percent of the number in operand of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorSecondOperandReadState with main string got from calculation of number, is result of number that equals percentage of [operand]
     * percent from number equals [operand]. String of result number added to history string field of calculator data.
     */
    override fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val percentageResult = (generalCalculatorDataEntity.operand/100)*calculatorStringToDouble(generalCalculatorDataEntity.mainString)
        val appendedHistoryString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}${doubleToCalculatorString(percentageResult)}"
        } else {
            "${generalCalculatorDataEntity.historyString.replaceAfterLast(
                HISTORY_STRING_SPACE_LETTER, "")}" +
                    "${doubleToCalculatorString(percentageResult)}"
        }

        return GeneralCalculatorSecondOperandReadState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(percentageResult),
                historyString = appendedHistoryString
            )
        )
    }

    /**
     * Calculates inversely proportioned number to that contained in main string field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorSecondOperandReadState if calculation completed successfully with result written in main string field and
     * action added to history string. If math operation throws exception, then GeneralCalculatorErrorState returned by function with
     * error code written to corresponded field and action added to history string of calculator data.
     */
    override fun reciprocOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val baseString = generalCalculatorDataEntity.historyString.replaceAfterLast(
            HISTORY_STRING_SPACE_LETTER, ""
        )
        val lastActionString = "reciproc(${generalCalculatorDataEntity.historyString.takeLastWhile { it != ' ' }})"
        val appendedHistoryString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}reciproc(${generalCalculatorDataEntity.mainString})"
        } else {
            "$baseString$lastActionString"
        }

        return try {
            GeneralCalculatorSecondOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(1/(calculatorStringToDouble(generalCalculatorDataEntity.mainString))),
                    historyString = appendedHistoryString
                )
            )
        } catch (numberFormatException: NumberFormatException) {
            GeneralCalculatorErrorState(generalCalculatorDataEntity.copy(
                historyString = "${generalCalculatorDataEntity.historyString}" +
                        "reciproc(${generalCalculatorDataEntity.mainString})",
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            ))
        } catch (exception: Exception) {
            GeneralCalculatorErrorState(generalCalculatorDataEntity.copy(
                historyString = "${generalCalculatorDataEntity.historyString}" +
                        "reciproc(${generalCalculatorDataEntity.mainString})",
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
                historyString = "",
                operand = secondOperand
            )
        )
    }

    /**
     * Define, whether the last character of the string is math operation sign or not
     * @param historyString - string to define last char
     * @return true if last char is math operation sign, false if not
     */
    private fun isLastOperationChar(historyString: String): Boolean {
        return (historyString.last() in listOf('+', '-', '*', '/'))
    }
}