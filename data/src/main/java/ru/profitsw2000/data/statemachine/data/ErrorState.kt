package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class ErrorState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Clear -> clearAll(generalCalculatorDataEntity)
            CalculatorAction.ClearEntered -> clearAll(generalCalculatorDataEntity)
            else -> this
        }
    }

    /**
     * Clears all data, contained in fields mainString, historyString and operand fields of calculator data.
     * OperationType set to NO_OPERATION state
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with some fields of calculator data set to default state
     */
    override fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState {
        return InitialState(
            GeneralCalculatorDataEntity(
                memoryNumber = generalCalculatorDataEntity.memoryNumber
            )
        )
    }
}