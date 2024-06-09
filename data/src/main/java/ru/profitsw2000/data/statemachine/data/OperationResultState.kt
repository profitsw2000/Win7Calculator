package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.model.GeneralCalculatorDataModel
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class OperationResultState(
    val generalCalculatorDataModel: GeneralCalculatorDataModel
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> InitialState(generalCalculatorDataModel)
            CalculatorAction.AddToMemory -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Backspace -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Clear -> InitialState(generalCalculatorDataModel)
            CalculatorAction.ClearEntered -> InitialState(generalCalculatorDataModel)
            CalculatorAction.ClearMemory -> InitialState(generalCalculatorDataModel)
            is CalculatorAction.Digit -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Divide -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Equal -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Multiply -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Percentage -> InitialState(generalCalculatorDataModel)
            CalculatorAction.PlusMinus -> InitialState(generalCalculatorDataModel)
            CalculatorAction.ReadMemory -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Recipoc -> InitialState(generalCalculatorDataModel)
            CalculatorAction.SaveToMemory -> InitialState(generalCalculatorDataModel)
            CalculatorAction.SquareRoot -> InitialState(generalCalculatorDataModel)
            CalculatorAction.Subtract -> InitialState(generalCalculatorDataModel)
            CalculatorAction.SubtractFromMemory -> InitialState(generalCalculatorDataModel)
        }
    }
}