package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorBaseState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import kotlin.math.sqrt

class FirstOperandInputState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorBaseState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action){
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.PLUS, "+")
            CalculatorAction.AddToMemory -> addNumberToMemory(generalCalculatorDataEntity)
            CalculatorAction.Backspace -> clearDigit(generalCalculatorDataEntity)
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
        }
    }

    /**
     * Function to clear calculator memory
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandReadState with same calculator data but memoryNumber field set to null
     */
    override fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return FirstOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste to mainString field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandReadState with same calculator data but number from memory field converted to string and copied to mainString - if memoryNumber field contains any number,
     * if not - set main string field to "0"
     */
    override fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            FirstOperandReadState(generalCalculatorDataEntity.copy(mainString = "0"))
        }
        else {
            FirstOperandReadState(generalCalculatorDataEntity.copy(mainString = doubleToCalculatorString(generalCalculatorDataEntity.memoryNumber)))
        }
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandReadState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    override fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return FirstOperandReadState(
            generalCalculatorDataEntity.copy(
                memoryNumber = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Add number to number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandReadState with same calculator data but with other memoryNumber. mainString field converted to number and added to memoryNumber field.
     */
    override fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val firstOperandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            FirstOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.plus(firstOperandNumber)))
        else FirstOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = firstOperandNumber))
    }

    /**
     * Subtract number from number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandReadState with same calculator data but with other memoryNumber. mainString field converted to number and memoryNumber field subtracted by that number.
     */
    override fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        val firstOperandNumber: Double = calculatorStringToDouble(generalCalculatorDataEntity.mainString)

        return if (generalCalculatorDataEntity.memoryNumber != null)
            FirstOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.minus(firstOperandNumber)))
        else FirstOperandReadState(generalCalculatorDataEntity.copy(memoryNumber = -(firstOperandNumber)))
    }

    /**
     * Function removes last character(digit) in mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandInputState with new mainString field of calculator data if string contains
     * more than 1 character, otherwise InitialState with mainString field turns to "0"
     */
    private fun clearDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
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
     * Clear all entered data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with initial calculator data except memoryNumber field. It contains old value.
     */
    override fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(GeneralCalculatorDataEntity(memoryNumber = generalCalculatorDataEntity.memoryNumber))
    }

    /**
     * Changes sign of the entered number. It's contained in mainString field of calculator data.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandInputState with new mainString field of calculator data
     */
    override fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

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
    override fun calculateSquareRoot(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {

        return try {
            val sqrtDouble = sqrt(calculatorStringToDouble(generalCalculatorDataEntity.mainString))
            val sqrtString = doubleToCalculatorString(sqrtDouble)

            FirstOperandReadState(
                generalCalculatorDataEntity.copy(
                    mainString = sqrtString,
                    historyString = if (generalCalculatorDataEntity.historyString == "") "sqrt(${generalCalculatorDataEntity.mainString})"
                    else "sqrt(${generalCalculatorDataEntity.historyString})"
                )
            )
        } catch (numberFormatException: NumberFormatException) {
            ErrorState(
                generalCalculatorDataEntity.copy(
                    historyString = "sqrt(${generalCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ErrorState(
                generalCalculatorDataEntity.copy(
                    historyString = "sqrt(${generalCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Appended digit in string format to mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @param digitToAppend - contains string with digit that has to be added to end of mainString of calculator data
     * @return FirstOperandInputState with new mainString field of calculator data
     */
    override fun inputDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digitToAppend: String): GeneralCalculatorState {

        val mainString = generalCalculatorDataEntity.mainString

        return when {
            mainString.contains(",") && digitToAppend == "," -> this
            mainString.length >= MAIN_STRING_MAX_DIGIT_NUMBER && !(mainString.contains(",")) -> this
            mainString.length >= (MAIN_STRING_MAX_DIGIT_NUMBER + 1) -> this
            mainString == "0" && mainString.length < 2 -> SecondOperandInputState(
                generalCalculatorDataEntity.copy(
                    mainString = digitToAppend
                )
            )
            else -> FirstOperandInputState(
                generalCalculatorDataEntity.copy(
                    mainString = "$mainString${digitToAppend}"
                )
            )
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
    override fun primitiveMathOperation(
        generalCalculatorDataEntity: GeneralCalculatorDataEntity,
        operationType: OperationType,
        operationString: String
    ): GeneralCalculatorState {

        val historyString = "${generalCalculatorDataEntity.mainString}$HISTORY_STRING_SPACE_LETTER$operationString"

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
     * @return FirstOperandReadState with "0" in main and history string of calculator data
     */
    override fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return FirstOperandReadState(generalCalculatorDataEntity.copy(
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
    override fun reciprocOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return try {
            FirstOperandReadState(generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(1/(calculatorStringToDouble(generalCalculatorDataEntity.mainString))),
                historyString = "reciproc(${generalCalculatorDataEntity.mainString})"
            ))
        } catch (numberFormatException: NumberFormatException) {
            ErrorState(generalCalculatorDataEntity.copy(
                historyString = "reciproc(${generalCalculatorDataEntity.mainString})",
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            ))
        } catch (exception: Exception) {
            ErrorState(generalCalculatorDataEntity.copy(
                historyString = "reciproc(${generalCalculatorDataEntity.mainString})",
                errorCode = UNKNOWN_ERROR_CODE
            ))
        }
    }

    /**
     * Changes state of calculator.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandReadState with same calculator data.
     */
    override fun calculateResult(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return FirstOperandReadState(
            generalCalculatorDataEntity.copy()
        )
    }
}