package ru.profitsw2000.data.model

import ru.profitsw2000.data.constants.NO_ERROR_CODE

data class GeneralCalculatorDataModel(
    val mainString: String = "0",
    val historyString: String = "",
    val memorySign: String = "",
    val errorCode: Int = NO_ERROR_CODE
)
