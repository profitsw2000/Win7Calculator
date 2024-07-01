package ru.profitsw2000.data.domain

interface CalculatorRepository {

    fun operationClicked(buttonCode: Int)

    fun addDigit(digit: String)
}