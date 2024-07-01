package ru.profitsw2000.data.statemachine.data

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

class InitialState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorBaseState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.PLUS, "+")
            CalculatorAction.AddToMemory -> addNumberToMemory(generalCalculatorDataEntity)
            CalculatorAction.Clear -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearEntered -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearMemory -> clearMemory(generalCalculatorDataEntity)
            is CalculatorAction.Digit -> inputDigit(generalCalculatorDataEntity, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.DIVIDE, "/")
            CalculatorAction.Multiply -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.MULTIPLY, "*")
            CalculatorAction.Percentage -> percentageOperation(generalCalculatorDataEntity)
            CalculatorAction.PlusMinus -> negateOperand(generalCalculatorDataEntity)
            CalculatorAction.ReadMemory -> readMemory(generalCalculatorDataEntity)
            CalculatorAction.Recipoc -> reciprocOperation(generalCalculatorDataEntity)
            CalculatorAction.SquareRoot -> calculateSquareRoot(generalCalculatorDataEntity)
            CalculatorAction.Subtract -> primitiveMathOperation(generalCalculatorDataEntity, OperationType.MINUS, "-")
            CalculatorAction.SubtractFromMemory -> subtractNumberFromMemory(generalCalculatorDataEntity)
            CalculatorAction.SaveToMemory -> saveToMemory(generalCalculatorDataEntity)
            else -> this
        }
    }

    /**
     * Clear internal memory of calculator
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with cleared memory of calculator data
     */
    override fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads internal memory and copy it to mainString of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandReadState with new main string field of calculator data if memoryNumber is not null
     * or same state if it is.
     */
    override fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            this
        } else {
            FirstOperandReadState(
                generalCalculatorDataEntity.copy(mainString = doubleToCalculatorString(generalCalculatorDataEntity.memoryNumber))
            )
        }
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    override fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(
            generalCalculatorDataEntity.copy(
                memoryNumber = if (generalCalculatorDataEntity.mainString == "0") null
                else calculatorStringToDouble(generalCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Add number, that contain main string of calculators display, to memory number
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with memory number field incremented on number, that contained in main string field
     */
    override fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            if (generalCalculatorDataEntity.mainString == "0") this
            else InitialState(
                generalCalculatorDataEntity.copy(
                    memoryNumber = calculatorStringToDouble(generalCalculatorDataEntity.mainString)
                )
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
    override fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            if (generalCalculatorDataEntity.mainString == "0") this
            else InitialState(
                generalCalculatorDataEntity.copy(memoryNumber = 0 - calculatorStringToDouble(generalCalculatorDataEntity.mainString))
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
    override fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(GeneralCalculatorDataEntity(memoryNumber = generalCalculatorDataEntity.memoryNumber))
    }

    /**
     * Changes inputs number sign to opposite and writes action to history.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with changed main string and writes action to history string of calculator data
     */
    override fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return this
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
     * Changed current state if clicked button is not 0. Clicked digit stored in main string field of calculator data.
     * @param1 generalCalculatorDataEntity - contains current calculator data
     * @param2 digit - contain string with number of clicked button
     * @return FirstOperandInputState if clicked digit is not 0 with digit stored in main string field, otherwise returns same state with same calculator data.
     */
    override fun inputDigit(
        generalCalculatorDataEntity: GeneralCalculatorDataEntity,
        digitToAppend: String
    ): GeneralCalculatorState {
        return if (digitToAppend  == "0") this
        else if(digitToAppend == ",") FirstOperandInputState(generalCalculatorDataEntity.copy(mainString = "0,"))
        else FirstOperandInputState(generalCalculatorDataEntity.copy(mainString = digitToAppend))
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
     * @return OperationResultState with "0" in main and history string of calculator data
     */
    override fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return OperationResultState(
            generalCalculatorDataEntity.copy(
                mainString = "0",
                historyString = "0"
            )
        )
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
            OperationResultState(generalCalculatorDataEntity.copy(
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

    override fun calculateResult(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return this
    }
}