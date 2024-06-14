package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class ErrorState(
    val generalCalculatorDataEntity: GeneralCalculatorDataEntity
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
}