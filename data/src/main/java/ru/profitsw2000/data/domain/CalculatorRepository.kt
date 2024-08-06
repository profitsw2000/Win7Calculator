package ru.profitsw2000.data.domain

import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState

interface CalculatorRepository {

    fun operationClicked(buttonCode: Int)

    fun addDigit(digit: String)
}