package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class OperationResultState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.AddToMemory -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Backspace -> InitialState(generalCalculatorDataEntity)
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