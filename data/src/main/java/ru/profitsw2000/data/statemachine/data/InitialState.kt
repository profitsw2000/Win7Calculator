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
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataModel.memoryNumber, "+")
            is CalculatorAction.Digit -> digitClicked(generalCalculatorDataModel.memoryNumber, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(generalCalculatorDataModel.memoryNumber, "/")
            CalculatorAction.Multiply -> primitiveMathOperation(generalCalculatorDataModel.memoryNumber, "*")
            CalculatorAction.Percentage -> percentageOperation(generalCalculatorDataModel.memoryNumber)
            CalculatorAction.Recipoc -> recipocOperation(generalCalculatorDataModel.memoryNumber)
            CalculatorAction.SquareRoot -> squareRootOperation(generalCalculatorDataModel.memoryNumber)
            CalculatorAction.Subtract -> primitiveMathOperation(generalCalculatorDataModel.memoryNumber, "-")
            CalculatorAction.ClearMemory -> clearMemory()
            else -> this
        }
    }

    private fun primitiveMathOperation(memoryNumber: Double?, operationSign: String): GeneralCalculatorState {
        return SecondOperandInputState(GeneralCalculatorDataModel("0","0 $operationSign", memoryNumber, 0))
    }

    private fun digitClicked(memoryNumber: Double?, digit: String): GeneralCalculatorState {
        return FirstOperandInputState(GeneralCalculatorDataModel(digit,"", memoryNumber, 0))
    }

    private fun percentageOperation(memoryNumber: Double?): GeneralCalculatorState {
        return OperationResultState(GeneralCalculatorDataModel("0", "0", memoryNumber, 0))
    }

    private fun squareRootOperation(memoryNumber: Double?): GeneralCalculatorState {
        return OperationResultState(GeneralCalculatorDataModel("0", "sqrt(0)", memoryNumber, 0))
    }

    private fun recipocOperation(memoryNumber: Double?): GeneralCalculatorState {
        return ErrorState(GeneralCalculatorDataModel("Деление на ноль невозможно", "reciproc(0)", memoryNumber, 0))
    }

    private fun clearMemory(): GeneralCalculatorState {
        return InitialState(GeneralCalculatorDataModel())
    }
}