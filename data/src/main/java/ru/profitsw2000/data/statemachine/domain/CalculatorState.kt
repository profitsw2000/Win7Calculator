package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.statemachine.action.CalculatorAction

interface CalculatorState {
    fun consumeAction(action: CalculatorAction): CalculatorState
}