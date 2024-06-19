package ru.profitsw2000.data.statemachine.data

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

class SecondOperandMemoryReadState(
    override val generalCalculatorDataEntity: GeneralCalculatorDataEntity
) : GeneralCalculatorState {
    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {

            else -> InitialState(generalCalculatorDataEntity)
        }
    }
}