package ru.profitsw2000.data.domain

import kotlinx.coroutines.flow.StateFlow
import ru.profitsw2000.data.model.GeneralCalculatorDataModel

interface GeneralCalculatorRepository : CommonCalculatorRepository {

    val generalCalculatorDataSource: StateFlow<GeneralCalculatorDataModel>

}