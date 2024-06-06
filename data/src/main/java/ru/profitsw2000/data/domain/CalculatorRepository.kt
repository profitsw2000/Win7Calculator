package ru.profitsw2000.data.domain

interface CalculatorRepository {

    fun memoryClear()

    fun memoryRead()

    fun memorySet()

    fun memoryPlus()

    fun memoryMinus()

    fun backspace()

    fun clearAll()

    fun plusMinus()

    fun addDigit(digit: String)
}