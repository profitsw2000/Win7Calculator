package ru.profitsw2000.data.domain

import kotlinx.coroutines.flow.StateFlow
import ru.profitsw2000.data.model.GeneralCalculatorDataModel
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

interface GeneralCalculatorRepository : CommonCalculatorRepository {

    val generalCalculatorDataSource: StateFlow<GeneralCalculatorDataModel>

    fun renderGeneralCalculatorState(newState: GeneralCalculatorState)
}