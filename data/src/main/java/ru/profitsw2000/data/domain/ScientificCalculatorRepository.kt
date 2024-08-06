package ru.profitsw2000.data.domain

import kotlinx.coroutines.flow.StateFlow
import ru.profitsw2000.data.model.GeneralCalculatorDataModel

interface ScientificCalculatorRepository : CalculatorRepository {

    val scientificCalculatorDataSource: StateFlow<GeneralCalculatorDataModel>
    fun angleUnitSelected(angleUnitCode: Int)
    val isFunctionsInverted: Boolean
    val angleUnitCode: Int

    fun changeAngleUnits(angleUnitCode: Int)

    fun invertFunctions(isInverted: Boolean)

}