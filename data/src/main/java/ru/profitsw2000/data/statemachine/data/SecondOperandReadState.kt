package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import kotlin.math.sqrt

class SecondOperandReadState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {

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
        }
    }

    /**
     * Clear internal memory of calculator
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Same state with cleared memory of calculator data
     */
    private fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return SecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste
     * to mainString field. Additionaly, remove from historyString field last entered action while on SecondOperandReadState
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandReadState with same calculator data but number from memory field converted to string and copied to mainString - if memoryNumber field contains any number,
     * if not - set main string field to "0". Also history string field updated by removing last action entrance while on SecondOperandReadState
     */
    private fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            SecondOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = "0",
                    historyString = generalCalculatorDataEntity.historyString.replaceAfterLast(" ", "")
                )
            )
        }
        else {
            SecondOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = generalCalculatorDataEntity.memoryNumber.toString(),
                    historyString = generalCalculatorDataEntity.historyString.replaceAfterLast(" ", "")
                )
            )
        }
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandReadState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    private fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return SecondOperandReadState(
            generalCalculatorDataEntity.copy(
                memoryNumber = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Add number to number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandReadState with same calculator data but with other memoryNumber. mainString field converted to number and added to memoryNumber field.
     */
    private fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            SecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.plus(operandNumber)))
        else SecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = operandNumber))
    }

    /**
     * Subtract number from number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandReadState with same calculator data but with other memoryNumber. mainString field converted to number and memoryNumber field subtracted by that number.
     */
    private fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            SecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.minus(operandNumber)))
        else SecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = -(operandNumber)))
    }

    /**
     * Clears mainString field of calculator data and set it to "0", also remove from
     * historyString field last entered action while on SecondOperandReadState
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandInputState where mainString field calculator data contain "0"
     */
    private fun clearMainString(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val historyString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) generalCalculatorDataEntity.historyString
        else generalCalculatorDataEntity.historyString.replaceAfterLast(" ", "")

        return SecondOperandInputState(
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
    private fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(generalCalculatorDataEntity.copy(
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
     * @return SecondOperandReadState with new mainString and history fields of calculator data
     */
    private fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val negatedNumber = (0 - calculatorStringToDouble(generalCalculatorDataEntity.mainString))
        val baseString = generalCalculatorDataEntity.historyString.replaceAfterLast(" ", "")
        val lastActionString = "negate(${generalCalculatorDataEntity.historyString.takeLastWhile { it != ' ' }})"
        val appendedHistoryString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}negate(${generalCalculatorDataEntity.mainString})"
        } else {
            "$baseString$lastActionString"
        }

        return SecondOperandReadState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(negatedNumber),
                historyString = appendedHistoryString
            )
        )
    }

    /**
     * Calculates square root of input number and add action to history of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with result of square root calculation in main string field and
     * action added to history string of calculator data
     */
    private fun calculateSquareRoot(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val sqrtString = doubleToCalculatorString(sqrt(calculatorStringToDouble(generalCalculatorDataEntity.mainString)))
        val baseString = generalCalculatorDataEntity.historyString.replaceAfterLast(" ", "")
        val lastActionString = "sqrt(${generalCalculatorDataEntity.historyString.takeLastWhile { it != ' ' }})"
        val appendedHistoryString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}sqrt(${generalCalculatorDataEntity.mainString})"
        } else {
            "$baseString$lastActionString"
        }

        return SecondOperandReadState(
            generalCalculatorDataEntity.copy(
                mainString = sqrtString,
                historyString = appendedHistoryString
            )
        )
    }

    /**
     * Writes digit in string format to mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @param digitToInput - contains string with digit that has to be added to mainString of calculator data
     * @return SecondOperandInputState with new mainString and historyString fields of calculator data
     */
    private fun inputDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digitToInput: String): GeneralCalculatorState {

        return if (digitToInput == ",") SecondOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = "0,",
                historyString = generalCalculatorDataEntity.historyString.replaceAfterLast(" ", "").dropLast(1)
            )
        )
        else SecondOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = digitToInput,
                historyString = generalCalculatorDataEntity.historyString.replaceAfterLast(" ", "").dropLast(1)
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
     * @return PrimitiveMathOperationState with new operationType field and updated historyString,
     * mainString and operand fields of calculator data.
     */
    private fun primitiveMathOperation(
        generalCalculatorDataEntity: GeneralCalculatorDataEntity,
        operationType: OperationType,
        operationString: String
    ): GeneralCalculatorState {

        val historyString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString} " +
                    "${generalCalculatorDataEntity.mainString} " +
                    "$operationString"
        } else {
            "${generalCalculatorDataEntity.historyString} " +
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

        return PrimitiveMathOperationState(
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
     * @return SecondOperandReadState with main string got from calculation of number, is result of number that equals percentage of [operand]
     * percent from number equals [operand]. String of result number added to history string field of calculator data.
     */
    private fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val percentageResult = (generalCalculatorDataEntity.operand/100)*calculatorStringToDouble(generalCalculatorDataEntity.mainString)
        val appendedHistoryString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}${doubleToCalculatorString(percentageResult)}"
        } else {
            "${generalCalculatorDataEntity.historyString.replaceAfterLast(" ", "")}${doubleToCalculatorString(percentageResult)}"
        }

        return SecondOperandReadState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(percentageResult),
                historyString = appendedHistoryString
            )
        )
    }

    /**
     * Calculates inversely proportioned number to that contained in main string field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandReadState if calculation completed successfully with result written in main string field and
     * action added to history string. If math operation throws exception, then ErrorState returned by function with
     * error code written to corresponded field and action added to history string of calculator data.
     */
    private fun reciprocOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val baseString = generalCalculatorDataEntity.historyString.replaceAfterLast(" ", "")
        val lastActionString = "reciproc(${generalCalculatorDataEntity.historyString.takeLastWhile { it != ' ' }})"
        val appendedHistoryString = if (isLastOperationChar(generalCalculatorDataEntity.historyString)) {
            "${generalCalculatorDataEntity.historyString}reciproc(${generalCalculatorDataEntity.mainString})"
        } else {
            "$baseString$lastActionString"
        }

        return try {
            SecondOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(1/(calculatorStringToDouble(generalCalculatorDataEntity.mainString))),
                    historyString = appendedHistoryString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ErrorState(generalCalculatorDataEntity.copy(
                historyString = "${generalCalculatorDataEntity.historyString}" +
                        "reciproc(${generalCalculatorDataEntity.mainString})",
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            ))
        } catch (exception: Exception) {
            ErrorState(generalCalculatorDataEntity.copy(
                historyString = "${generalCalculatorDataEntity.historyString}" +
                        "reciproc(${generalCalculatorDataEntity.mainString})",
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