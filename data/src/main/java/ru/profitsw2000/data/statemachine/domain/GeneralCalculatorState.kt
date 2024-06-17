package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity

interface GeneralCalculatorState : CalculatorState {
    val generalCalculatorDataEntity: GeneralCalculatorDataEntity
}