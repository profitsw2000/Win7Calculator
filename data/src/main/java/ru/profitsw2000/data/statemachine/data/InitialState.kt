package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.model.generalCalculatorDataEntity
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class InitialState(
    val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(generalCalculatorDataEntity.memoryNumber, "+")
            is CalculatorAction.Digit -> digitClicked(generalCalculatorDataEntity.memoryNumber, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(generalCalculatorDataEntity.memoryNumber, "/")
            CalculatorAction.Multiply -> primitiveMathOperation(generalCalculatorDataEntity.memoryNumber, "*")
            CalculatorAction.Percentage -> percentageOperation(generalCalculatorDataEntity.memoryNumber)
            CalculatorAction.Recipoc -> recipocOperation(generalCalculatorDataEntity.memoryNumber)
            CalculatorAction.SquareRoot -> squareRootOperation(generalCalculatorDataEntity.memoryNumber)
            CalculatorAction.Subtract -> primitiveMathOperation(generalCalculatorDataEntity.memoryNumber, "-")
            CalculatorAction.ClearMemory -> clearMemory()
            CalculatorAction.Clear -> clearAll(generalCalculatorDataEntity)
            else -> this
        }
    }

    private fun primitiveMathOperation(memoryNumber: Double?, operationSign: String): GeneralCalculatorState {
        return SecondOperandInputState(GeneralCalculatorDataEntity("0","0 $operationSign", 0.0, OperationType.NO_OPERATION, memoryNumber, 0))
    }

    private fun digitClicked(memoryNumber: Double?, digit: String): GeneralCalculatorState {
        return FirstOperandInputState(generalCalculatorDataEntity(digit,"", memoryNumber, 0))
    }

    private fun percentageOperation(memoryNumber: Double?): GeneralCalculatorState {
        return OperationResultState(generalCalculatorDataEntity("0", "0", memoryNumber, 0))
    }

    private fun squareRootOperation(memoryNumber: Double?): GeneralCalculatorState {
        return OperationResultState(generalCalculatorDataEntity("0", "sqrt(0)", memoryNumber, 0))
    }

    private fun recipocOperation(memoryNumber: Double?): GeneralCalculatorState {
        return ErrorState(generalCalculatorDataEntity("Деление на ноль невозможно", "reciproc(0)", memoryNumber, 0))
    }

    private fun clearMemory(): GeneralCalculatorState {
        return InitialState(GeneralCalculatorDataEntity())
    }

    private fun clearAll(generalCalculatorDataEntity: generalCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(generalCalculatorDataEntity(memoryNumber = generalCalculatorDataEntity.memoryNumber))
    }
}