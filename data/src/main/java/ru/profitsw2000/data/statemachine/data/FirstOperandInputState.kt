package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class FirstOperandInputState(
    val mainString: String,
    val memoryWritten: Boolean
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action){
            CalculatorAction.Add -> InitialState(false)
            CalculatorAction.AddToMemory -> InitialState(false)
            CalculatorAction.Backspace -> InitialState(false)
            CalculatorAction.Clear -> InitialState(memoryWritten)
            CalculatorAction.ClearEntered -> InitialState(false)
            CalculatorAction.ClearMemory -> InitialState(false)
            is CalculatorAction.Digit -> InitialState(false)
            CalculatorAction.Divide -> InitialState(false)
            CalculatorAction.Equal -> InitialState(false)
            CalculatorAction.Multiply -> InitialState(false)
            CalculatorAction.Percentage -> InitialState(false)
            CalculatorAction.PlusMinus -> InitialState(false)
            CalculatorAction.ReadMemory -> InitialState(false)
            CalculatorAction.Recipoc -> InitialState(false)
            CalculatorAction.SaveToMemory -> InitialState(false)
            CalculatorAction.SquareRoot -> InitialState(false)
            CalculatorAction.Subtract -> InitialState(false)
            CalculatorAction.SubtractFromMemory -> InitialState(false)
        }
    }
}