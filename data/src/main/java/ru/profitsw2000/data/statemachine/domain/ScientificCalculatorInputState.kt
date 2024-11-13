package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity

interface ScientificCalculatorInputState : ScientificCalculatorBaseState {

    fun clearDigit(scientificCalculatorDataEntity: ScientificCalculatorDataEntity) : CalculatorState

    fun clearEntered(scientificCalculatorDataEntity: ScientificCalculatorDataEntity) : CalculatorState

}