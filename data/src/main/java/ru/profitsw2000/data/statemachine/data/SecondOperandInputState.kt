package ru.profitsw2000.data.statemachine.data

import android.util.Log
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

class SecondOperandInputState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorBaseState {

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.PLUS, "+")
            CalculatorAction.AddToMemory -> addNumberToMemory(generalCalculatorDataEntity)
            CalculatorAction.Backspace -> clearDigit(generalCalculatorDataEntity)
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
     * @return SecondOperandReadState with cleared memory of calculator data
     */
    override fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return SecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste
     * to mainString field.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandReadState with same calculator data but number from memory field converted to string and
     * copied to mainString - if memoryNumber field contains any number,
     * if not - set main string field to "0".
     */
    override fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            SecondOperandReadState(generalCalculatorDataEntity.copy(mainString = "0"))
        }
        else {
            SecondOperandReadState(generalCalculatorDataEntity.copy(mainString = doubleToCalculatorString(generalCalculatorDataEntity.memoryNumber)))
        }
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandReadState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    override fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
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
    override fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
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
    override fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            SecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.minus(operandNumber)))
        else SecondOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = -(operandNumber)))
    }

    /**
     * Function removes last character(digit) in mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandInputState with new mainString field of calculator data if string contains
     * more than 1 character, otherwise SecondOperandInputState with mainString field turns to "0"
     */
    private fun clearDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.mainString.length > 2) {
            SecondOperandInputState(generalCalculatorDataEntity.copy(mainString = generalCalculatorDataEntity.mainString.dropLast(1)))
        } else if (generalCalculatorDataEntity.mainString.length == 2
            && generalCalculatorDataEntity.mainString.first() != '-') {
            SecondOperandInputState(generalCalculatorDataEntity.copy(mainString = generalCalculatorDataEntity.mainString.dropLast(1)))
        } else {
            SecondOperandInputState(generalCalculatorDataEntity.copy(mainString = "0"))
        }
    }

    /**
     * Clears mainString field of calculator data and set it to "0"
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandInputState where mainString field calculator data contain "0"
     */
    private fun clearMainString(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return SecondOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = "0"
            )
        )
    }

    /**
     * Clear all entered data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with initial calculator data except memoryNumber field. It contains old value.
     */
    override fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(GeneralCalculatorDataEntity(memoryNumber = generalCalculatorDataEntity.memoryNumber))
    }

    /**
     * Changes sign of the entered number.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandReadState with new mainString
     */
    override fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val negatedNumber = (0 - calculatorStringToDouble(generalCalculatorDataEntity.mainString))

        return SecondOperandInputState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(negatedNumber)
            )
        )
    }

    /**
     * Calculates square root of input number and add action to history of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandReadState with result of square root calculation in main string field and
     * action added to history string of calculator data     */
    override fun calculateSquareRoot(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        return if (calculatorStringToDouble(generalCalculatorDataEntity.mainString) < 0.0) {
            ErrorState(
                GeneralCalculatorDataEntity(
                    historyString = "${generalCalculatorDataEntity.historyString}" +
                            "${HISTORY_STRING_SPACE_LETTER}" +
                            "sqrt(${generalCalculatorDataEntity.mainString})",
                    memoryNumber = generalCalculatorDataEntity.memoryNumber,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } else {
            SecondOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(sqrt(calculatorStringToDouble(generalCalculatorDataEntity.mainString))),
                    historyString = "${generalCalculatorDataEntity.historyString}" +
                            "${HISTORY_STRING_SPACE_LETTER}" +
                            "sqrt(${generalCalculatorDataEntity.mainString})"
                )
            )
        }
    }

    /**
     * Writes digit in string format to mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @param digitToAppend - contains string with digit that has to be added to mainString of calculator data
     * @return SecondOperandInputState with new mainString and historyString fields of calculator data
     */
    override fun inputDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digitToAppend: String): GeneralCalculatorState {

        return when {
            generalCalculatorDataEntity.mainString == "0" && digitToAppend == "0" -> this
            generalCalculatorDataEntity.mainString.contains(",") && digitToAppend == "," -> this
            generalCalculatorDataEntity.mainString.length >= 16 -> this
            generalCalculatorDataEntity.mainString == "0" && generalCalculatorDataEntity.mainString.length < 2 -> SecondOperandInputState(
                generalCalculatorDataEntity.copy(
                    mainString = digitToAppend
                )
            )
            else -> SecondOperandInputState(
                generalCalculatorDataEntity.copy(
                    mainString = "${generalCalculatorDataEntity.mainString}${digitToAppend}"
                )
            )
        }
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
    override fun primitiveMathOperation(
        generalCalculatorDataEntity: GeneralCalculatorDataEntity,
        operationType: OperationType,
        operationString: String
    ): GeneralCalculatorState {

        val historyString = "${generalCalculatorDataEntity.historyString}" +
                "$HISTORY_STRING_SPACE_LETTER" +
                "${generalCalculatorDataEntity.mainString}" +
                "$HISTORY_STRING_SPACE_LETTER" +
                "$operationString"
        val firstOperand = generalCalculatorDataEntity.operand
        val secondOperand = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
        val operationResult = when(generalCalculatorDataEntity.operationType) {
            OperationType.PLUS -> firstOperand + secondOperand
            OperationType.MINUS -> firstOperand - secondOperand
            OperationType.MULTIPLY -> firstOperand * secondOperand
            OperationType.DIVIDE -> firstOperand / secondOperand
            OperationType.NO_OPERATION -> 0.0
        }
        Log.d("VVV", "primitiveMathOperation: $operationResult")

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
     * @return SecondOperandReadState with main string got from calculation of number, as result of number that equals percentage of [operand]
     * percent from number equals [operand]. String of result number added to history string field of calculator data.
     */
    override fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val percentageResult = (generalCalculatorDataEntity.operand/100)*calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return SecondOperandReadState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(percentageResult),
                historyString = " ${generalCalculatorDataEntity.historyString}" +
                        "$HISTORY_STRING_SPACE_LETTER" +
                        "${doubleToCalculatorString(percentageResult)}"
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
    override fun reciprocOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        return try {
            SecondOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(1/(calculatorStringToDouble(generalCalculatorDataEntity.mainString))),
                    historyString = "${generalCalculatorDataEntity.historyString}" +
                            "${HISTORY_STRING_SPACE_LETTER}" +
                            "reciproc(${generalCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ErrorState(generalCalculatorDataEntity.copy(
                historyString = "${generalCalculatorDataEntity.historyString}" +
                        "${HISTORY_STRING_SPACE_LETTER}" +
                        "reciproc(${generalCalculatorDataEntity.mainString})",
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            ))
        } catch (exception: Exception) {
            ErrorState(generalCalculatorDataEntity.copy(
                historyString = "${generalCalculatorDataEntity.historyString}" +
                        "${HISTORY_STRING_SPACE_LETTER}" +
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

        return OperationResultState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(operationResult),
                historyString = "",
                operand = secondOperand
            )
        )
    }
}