package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity

interface ScientificCalculatorState : CalculatorState {

    val scientificCalculatorDataEntity: ScientificCalculatorDataEntity

    fun clearAll(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

}