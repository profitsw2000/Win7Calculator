package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class InitialState(
    private val memoryWritten: Boolean
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(memoryWritten, "+")
            is CalculatorAction.Digit -> digitClicked(memoryWritten, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(memoryWritten, "/")
            CalculatorAction.Multiply -> primitiveMathOperation(memoryWritten, "*")
            CalculatorAction.Percentage -> percentageOperation(memoryWritten)
            CalculatorAction.Recipoc -> recipocOperation(memoryWritten)
            CalculatorAction.SquareRoot -> squareRootOperation(memoryWritten)
            CalculatorAction.Subtract -> primitiveMathOperation(memoryWritten, "-")
            else -> this
        }
    }

    private fun primitiveMathOperation(memoryWritten: Boolean, operationSign: String): GeneralCalculatorState {
        return SecondOperandInputState("0","0 $operationSign", memoryWritten)
    }

    private fun digitClicked(memoryWritten: Boolean, digit: String): GeneralCalculatorState {
        return FirstOperandInputState(digit, memoryWritten)
    }

    private fun percentageOperation(memoryWritten: Boolean): GeneralCalculatorState {
        return OperationResultState("0", "0", memoryWritten)
    }

    private fun squareRootOperation(memoryWritten: Boolean): GeneralCalculatorState {
        return OperationResultState("0", "sqrt(0)", memoryWritten)
    }

    private fun recipocOperation(memoryWritten: Boolean): GeneralCalculatorState {
        return ErrorState("Деление на ноль невозможно", "recipoc(0)", memoryWritten)
    }
}