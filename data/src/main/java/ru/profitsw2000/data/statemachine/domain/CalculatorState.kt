package ru.profitsw2000.data.statemachine.domain

interface CalculatorState {
    fun consumeAction(): CalculatorState
}