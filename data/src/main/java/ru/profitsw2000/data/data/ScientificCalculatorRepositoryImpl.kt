package ru.profitsw2000.data.data

import kotlinx.coroutines.flow.StateFlow
import ru.profitsw2000.data.domain.ScientificCalculatorRepository
import ru.profitsw2000.data.mappers.CalculatorMapper
import ru.profitsw2000.data.model.GeneralCalculatorDataModel

class ScientificCalculatorRepositoryImpl(
    private val calculatorMapper: CalculatorMapper
) : ScientificCalculatorRepository {

    override val scientificCalculatorDataSource: StateFlow<GeneralCalculatorDataModel>
        get() = TODO("Not yet implemented")

    override val isFunctionsInverted: Boolean
        get() = TODO("Not yet implemented")
    override val angleUnitCode: Int
        get() = TODO("Not yet implemented")

    override fun angleUnitSelected(angleUnitCode: Int) {
        TODO("Not yet implemented")
    }

    override fun changeAngleUnits(angleUnitCode: Int) {
        TODO("Not yet implemented")
    }

    override fun invertFunctions(isInverted: Boolean) {
        TODO("Not yet implemented")
    }

    override fun operationClicked(buttonCode: Int) {
        TODO("Not yet implemented")
    }

    override fun addDigit(digit: String) {
        TODO("Not yet implemented")
    }
}