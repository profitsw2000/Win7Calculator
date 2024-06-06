package ru.profitsw2000.data.domain

interface CommonCalculatorRepository: CalculatorRepository {

    fun divide()

    fun multiply()

    fun subtract()

    fun add()

    fun equal()

    fun clearEntered()
}