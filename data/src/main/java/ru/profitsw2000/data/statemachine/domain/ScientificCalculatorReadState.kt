package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity

interface ScientificCalculatorReadState: ScientificCalculatorBaseState {

    fun clearEntered(scientificCalculatorDataEntity: ScientificCalculatorDataEntity) : CalculatorState

}