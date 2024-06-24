package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class FirstOperandReadState(
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
}