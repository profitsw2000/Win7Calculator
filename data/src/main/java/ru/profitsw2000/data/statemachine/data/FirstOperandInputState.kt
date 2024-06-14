package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class FirstOperandInputState(
    val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action){
            CalculatorAction.Add -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.AddToMemory -> addNumberToMemory(generalCalculatorDataEntity)
            CalculatorAction.Backspace -> deleteLastDigit(generalCalculatorDataEntity)
            CalculatorAction.Clear -> clearAllData(generalCalculatorDataEntity)
            CalculatorAction.ClearEntered -> clearAllData(generalCalculatorDataEntity)
            CalculatorAction.ClearMemory -> clearMemory(generalCalculatorDataEntity)
            is CalculatorAction.Digit -> appendDigit(generalCalculatorDataEntity, action.digit)
            CalculatorAction.Divide -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Equal -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Multiply -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Percentage -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.PlusMinus -> negateOperand(generalCalculatorDataEntity)
            CalculatorAction.ReadMemory -> readMemory(generalCalculatorDataEntity)
            CalculatorAction.Recipoc -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.SaveToMemory -> saveToMemory(generalCalculatorDataEntity)
            CalculatorAction.SquareRoot -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.Subtract -> InitialState(generalCalculatorDataEntity)
            CalculatorAction.SubtractFromMemory -> subtractNumberFromMemory(generalCalculatorDataEntity)
        }
    }

    /**
     * Function to clear calculator memory
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but memoryNumber field set to null if memoryNumber field contains any number,
     * or FirstOperandInputState with same calculator data if memoryNumber field is null
     */
    private fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber != null) InitialState(generalCalculatorDataEntity.copy(memoryNumber = null))
        else FirstOperandInputState(generalCalculatorDataEntity)
    }

    /**
     * Reads memory from memoryNumber field of calculator data if it is not null, convert it to string and paste to mainString field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but number from memory field converted to string and copied to mainString - if memoryNumber field contains any number,
     * Create new scratch file from selection
     */
    private fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.memoryNumber == null) FirstOperandInputState(generalCalculatorDataEntity)
        else {
            InitialState(generalCalculatorDataEntity.copy(mainString = generalCalculatorDataEntity.memoryNumber.toString()))
        }
    }

    /**
     * Add number to number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but with other memoryNumber. mainString field converted to number and added to memoryNumber field.
     */
    private fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val firstOperandNumber: Double = generalCalculatorDataEntity.mainString.toDouble()
        return if (generalCalculatorDataEntity.memoryNumber != null)
            InitialState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.plus(firstOperandNumber)))
        else InitialState(generalCalculatorDataEntity.copy(memoryNumber = firstOperandNumber))
    }

    /**
     * Subtract number from number in memory of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but with other memoryNumber. mainString field converted to number and memoryNumber field subtracted by that number.
     */
    private fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val firstOperandNumber: Double = generalCalculatorDataEntity.mainString.toDouble()
        return if (generalCalculatorDataEntity.memoryNumber != null)
            InitialState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.memoryNumber.minus(firstOperandNumber)))
        else InitialState(generalCalculatorDataEntity.copy(memoryNumber = -(firstOperandNumber)))
    }

    /**
     * Save number from main string to memoryNumber field
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    private fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(generalCalculatorDataEntity.copy(memoryNumber = generalCalculatorDataEntity.mainString.toDouble()))
    }

    /**
     * Appended digit in string format to mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @param digitToAppend - contains string with digit that has to be added to end of mainString of calculator data
     * @return FirstOperandInputState with new mainString field of calculator data
     */
    private fun appendDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digitToAppend: String): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.mainString.length > 16) {
            FirstOperandInputState(generalCalculatorDataEntity)
        } else if(generalCalculatorDataEntity.mainString.length < 2) {
            FirstOperandInputState(generalCalculatorDataEntity.copy(mainString = digitToAppend))
        } else {
            FirstOperandInputState(generalCalculatorDataEntity.copy(mainString = generalCalculatorDataEntity.mainString.plus(digitToAppend)))
        }
    }

    /**
     * Clear all entered data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return InitialState with initial calculator data except memoryNumber field. It contains old value.
     */
    private fun clearAllData(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(GeneralCalculatorDataEntity(memoryNumber = generalCalculatorDataEntity.memoryNumber))
    }

    /**
     * Function removes last character(digit) in mainString field of calculator data
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandInputState with new mainString field of calculator data if string contains
     * more than 1 character, otherwise InitialState with mainString field turns to "0"
     */
    private fun deleteLastDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return if (generalCalculatorDataEntity.mainString.length > 1) {
            FirstOperandInputState(generalCalculatorDataEntity.copy(mainString = generalCalculatorDataEntity.mainString.dropLast(1)))
        } else {
            InitialState(generalCalculatorDataEntity.copy(mainString = "0"))
        }
    }

    /**
     * Changes sign of the entered number. It's contained in mainString field of calculator data.
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return FirstOperandInputState with new mainString field of calculator data
     */
    private fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        val negatedNumber = (0 - calculatorStringToDouble(generalCalculatorDataEntity.mainString))
        return FirstOperandInputState(generalCalculatorDataEntity.copy(
            mainString = doubleToCalculatorString(negatedNumber)
        ))
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