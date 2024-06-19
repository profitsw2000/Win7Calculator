package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class PrimitiveMathOperationState (
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        when(action) {
            CalculatorAction.Add -> TODO()
            CalculatorAction.AddToMemory -> TODO()
            CalculatorAction.Backspace -> TODO()
            CalculatorAction.Clear -> TODO()
            CalculatorAction.ClearEntered -> TODO()
            CalculatorAction.ClearMemory -> TODO()
            is CalculatorAction.Digit -> TODO()
            CalculatorAction.Divide -> TODO()
            CalculatorAction.Equal -> TODO()
            CalculatorAction.Multiply -> TODO()
            CalculatorAction.Percentage -> TODO()
            CalculatorAction.PlusMinus -> TODO()
            CalculatorAction.ReadMemory -> TODO()
            CalculatorAction.Recipoc -> TODO()
            CalculatorAction.SaveToMemory -> TODO()
            CalculatorAction.SquareRoot -> TODO()
            CalculatorAction.Subtract -> TODO()
            CalculatorAction.SubtractFromMemory -> TODO()
        }
    }

    /**
     * Clear internal memory of calculator
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Same with cleared memory of calculator data
     */
    private fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return PrimitiveMathOperationState(generalCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste to mainString field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandMemoryReadState with same calculator data but number from memory field converted to string and copied to mainString - if memoryNumber field contains any number,
     * if not - set main string field to "0"
     */
    private fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) {
            this
        }
        else {
            SecondOperandMemoryReadState(generalCalculatorDataEntity.copy(mainString = generalCalculatorDataEntity.memoryNumber.toString()))
        }
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    private fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return PrimitiveMathOperationState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.mainString.toDouble()))
    }

    /**
     * Add number to number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Same state with same calculator data but with other memoryNumber. mainString field converted to number and added to memoryNumber field.
     */
    private fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = generalCalculatorDataEntity.mainString.toDouble()
        return if (generalCalculatorDataEntity.memoryNumber != null)
            PrimitiveMathOperationState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.plus(operandNumber)))
        else PrimitiveMathOperationState(generalCalculatorDataEntity.copy(memoryNumber = operandNumber))
    }

    /**
     * Subtract number from number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Same state with same calculator data but with other memoryNumber. mainString field converted to number and memoryNumber field subtracted by that number.
     */
    private fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val operandNumber: Double = generalCalculatorDataEntity.mainString.toDouble()
        return if (generalCalculatorDataEntity.memoryNumber != null)
            PrimitiveMathOperationState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.minus(operandNumber)))
        else PrimitiveMathOperationState(generalCalculatorDataEntity.copy(memoryNumber = -(operandNumber)))
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
     * Changes sign of the entered number. It's contained in mainString field of calculator data.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandMemoryReadState with new mainString field of calculator data
     */
    private fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val negatedNumber = (0 - calculatorStringToDouble(generalCalculatorDataEntity.mainString))
        return SecondOperandMemoryReadState(generalCalculatorDataEntity.copy(
            mainString = doubleToCalculatorString(negatedNumber)
        ))
    }

    /**
     * Calculates input number in main string percent of the number in operand of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return SecondOperandMemoryReadState with main string got from calculation of number, is result of number that equals percentage of [operand]
     * percent from number equals [operand]
     */
    private fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val percentageResult = (generalCalculatorDataEntity.operand/100)*generalCalculatorDataEntity.operand

        return SecondOperandMemoryReadState(
            generalCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(percentageResult)
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