package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class ErrorState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> this
            CalculatorAction.AddToMemory -> this
            CalculatorAction.Backspace -> this
            CalculatorAction.Clear -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearEntered -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearMemory -> this
            is CalculatorAction.Digit -> this
            CalculatorAction.Divide -> this
            CalculatorAction.Equal -> this
            CalculatorAction.Multiply -> this
            CalculatorAction.Percentage -> this
            CalculatorAction.PlusMinus -> this
            CalculatorAction.ReadMemory -> this
            CalculatorAction.Recipoc -> this
            CalculatorAction.SaveToMemory -> this
            CalculatorAction.SquareRoot -> this
            CalculatorAction.Subtract -> this
            CalculatorAction.SubtractFromMemory -> this
        }
    }

    /**
     * Clears all data, contained in fields mainString, historyString and operand fields of calculator data.
     * OperationType set to NO_OPERATION state
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with some fields of calculator data set to default state
     */
    private fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(
            GeneralCalculatorDataEntity(
                memoryNumber = generalCalculatorDataEntity.memoryNumber
            )
        )
    }
}