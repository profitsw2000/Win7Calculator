package ru.profitsw2000.data.model

data class GeneralCalculatorDataModel(
    val mainString: String = "0",
    val historyString: String = "",
    val memoryNumber: Double? = null,
    val errorCode: Int = 0
)
