package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.model.GeneralCalculatorDataModel
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class FirstOperandInputState(
    val generalCalculatorDataModel: GeneralCalculatorDataModel
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action){
            CalculatorAction.Add -> InitialState(generalCalculatorDataModel)
            CalculatorAction.AddToMemory -> addNumberToMemory(generalCalculatorDataModel)
            CalculatorAction.Backspace -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Clear -> clearAllData(generalCalculatorDataModel)
            CalculatorAction.ClearEntered -> InitialState(generalCalculatorDataModel)
            CalculatorAction.ClearMemory -> clearMemory(generalCalculatorDataModel)
            is CalculatorAction.Digit -> appendDigit(generalCalculatorDataModel, action.digit)
            CalculatorAction.Divide -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Equal -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Multiply -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Percentage -> InitialState(generalCalculatorDataModel)
            CalculatorAction.PlusMinus -> InitialState(generalCalculatorDataModel)
            CalculatorAction.ReadMemory -> readMemory(generalCalculatorDataModel)
            CalculatorAction.Recipoc -> InitialState(generalCalculatorDataModel)
            CalculatorAction.SaveToMemory -> saveToMemory(generalCalculatorDataModel)
            CalculatorAction.SquareRoot -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Subtract -> InitialState(generalCalculatorDataModel)
            CalculatorAction.SubtractFromMemory -> subtractNumberFromMemory(generalCalculatorDataModel)
        }
    }

    /**
     * Function to clear calculator memory
     * @param generalCalculatorDataModel - contains current calculator data
     * @return InitialState with same calculator data but memoryNumber field set to null if memoryNumber field contains any number,
     * or FirstOperandInputState with same calculator data if memoryNumber field is null
     */
    private fun clearMemory(generalCalculatorDataModel: GeneralCalculatorDataModel): GeneralCalculatorState {
        return if (generalCalculatorDataModel.memoryNumber != null) InitialState(generalCalculatorDataModel.copy(memoryNumber = null))
        else FirstOperandInputState(generalCalculatorDataModel)
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste to mainString field
     * @param generalCalculatorDataModel - contains current calculator data
     * @return InitialState with same calculator data but number from memory field converted to string and copied to mainString - if memoryNumber field contains any number,
     * Create new scratch file from selection
     */
    private fun readMemory(generalCalculatorDataModel: GeneralCalculatorDataModel): GeneralCalculatorState {
        return if (generalCalculatorDataModel.memoryNumber == null) FirstOperandInputState(generalCalculatorDataModel)
        else {
            InitialState(generalCalculatorDataModel.copy(mainString = generalCalculatorDataModel.memoryNumber.toString()))
        }
    }

    /**
     * Add number to number in memory of calculator data
     * @param generalCalculatorDataModel - contains current calculator data
     * @return InitialState with same calculator data but with other memoryNumber. mainString field converted to number and added to memoryNumber field.
     */
    private fun addNumberToMemory(generalCalculatorDataModel: GeneralCalculatorDataModel): GeneralCalculatorState {
        val firstOperandNumber: Double = generalCalculatorDataModel.mainString.toDouble()
        return if (generalCalculatorDataModel.memoryNumber != null)
            InitialState(generalCalculatorDataModel.copy(memoryNumber = generalCalculatorDataModel.memoryNumber.plus(firstOperandNumber)))
        else InitialState(generalCalculatorDataModel.copy(memoryNumber = firstOperandNumber))
    }

    /**
     * Subtract number from number in memory of calculator data
     * @param generalCalculatorDataModel - contains current calculator data
     * @return InitialState with same calculator data but with other memoryNumber. mainString field converted to number and memoryNumber field subtracted by that number.
     */
    private fun subtractNumberFromMemory(generalCalculatorDataModel: GeneralCalculatorDataModel): GeneralCalculatorState {
        val firstOperandNumber: Double = generalCalculatorDataModel.mainString.toDouble()
        return if (generalCalculatorDataModel.memoryNumber != null)
            InitialState(generalCalculatorDataModel.copy(memoryNumber = generalCalculatorDataModel.memoryNumber.minus(firstOperandNumber)))
        else InitialState(generalCalculatorDataModel.copy(memoryNumber = -(firstOperandNumber)))
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataModel - contains current calculator data
     * @return InitialState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    private fun saveToMemory(generalCalculatorDataModel: GeneralCalculatorDataModel): GeneralCalculatorState {
        return InitialState(generalCalculatorDataModel.copy(memoryNumber = generalCalculatorDataModel.mainString.toDouble()))
    }

    /**
     * Appended digit in string format to mainString field of calculator data
     * @param generalCalculatorDataModel - contains current calculator data
     * @param digitToAppend - contains string with digit that has to be added to end of mainString of calculator data
     * @return FirstOperandInputState with new mainString field of calculator data
     */
    private fun appendDigit(generalCalculatorDataModel: GeneralCalculatorDataModel, digitToAppend: String): GeneralCalculatorState {
        return if (generalCalculatorDataModel.mainString.length > 16) {
            FirstOperandInputState(generalCalculatorDataModel)
        } else {
            FirstOperandInputState(generalCalculatorDataModel.copy(mainString = generalCalculatorDataModel.mainString.plus(digitToAppend)))
        }
    }

    /**
     * Clear all entered data
     * @param generalCalculatorDataModel - contains current calculator data
     * @return InitialState with initial calculator data except memoryNumber field. It contains old value.
     */
    private fun clearAllData(generalCalculatorDataModel: GeneralCalculatorDataModel): GeneralCalculatorState {
        return InitialState(GeneralCalculatorDataModel(memoryNumber = generalCalculatorDataModel.memoryNumber))
    }
}