package ru.profitsw2000.data.model

data class CalculatorDisplayModel(
    val mainString: String,
    val historyString: String,
    val memoryWritten: Boolean,
    val errorCode: Int
)
