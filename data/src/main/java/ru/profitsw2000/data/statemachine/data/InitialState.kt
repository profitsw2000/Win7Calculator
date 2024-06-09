package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.model.GeneralCalculatorDataModel
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class InitialState(
    val generalCalculatorDataModel: GeneralCalculatorDataModel
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataModel.memoryWritten, "+")
            is CalculatorAction.Digit -> digitClicked(generalCalculatorDataModel.memoryWritten, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(generalCalculatorDataModel.memoryWritten, "/")
            CalculatorAction.Multiply -> primitiveMathOperation(generalCalculatorDataModel.memoryWritten, "*")
            CalculatorAction.Percentage -> percentageOperation(generalCalculatorDataModel.memoryWritten)
            CalculatorAction.Recipoc -> recipocOperation(generalCalculatorDataModel.memoryWritten)
            CalculatorAction.SquareRoot -> squareRootOperation(generalCalculatorDataModel.memoryWritten)
            CalculatorAction.Subtract -> primitiveMathOperation(generalCalculatorDataModel.memoryWritten, "-")
            CalculatorAction.ClearMemory -> clearMemory()
            else -> this
        }
    }

    private fun primitiveMathOperation(memoryWritten: Boolean, operationSign: String): GeneralCalculatorState {
        return SecondOperandInputState(GeneralCalculatorDataModel("0","0 $operationSign", memoryWritten, 0))
    }

    private fun digitClicked(memoryWritten: Boolean, digit: String): GeneralCalculatorState {
        return FirstOperandInputState(GeneralCalculatorDataModel(digit,"", memoryWritten, 0))
    }

    private fun percentageOperation(memoryWritten: Boolean): GeneralCalculatorState {
        return OperationResultState(GeneralCalculatorDataModel("0", "0", memoryWritten, 0))
    }

    private fun squareRootOperation(memoryWritten: Boolean): GeneralCalculatorState {
        return OperationResultState(GeneralCalculatorDataModel("0", "sqrt(0)", memoryWritten, 0))
    }

    private fun recipocOperation(memoryWritten: Boolean): GeneralCalculatorState {
        return ErrorState(GeneralCalculatorDataModel("Деление на ноль невозможно", "recipoc(0)", memoryWritten, 0))
    }

    private fun clearMemory(): GeneralCalculatorState {
        return InitialState(GeneralCalculatorDataModel("0", "0", false, 0))
    }
}