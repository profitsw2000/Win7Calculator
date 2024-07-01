package ru.profitsw2000.data.entity

import ru.profitsw2000.data.constants.NO_ERROR_CODE

data class GeneralCalculatorDataEntity(
    val mainString: String = "0",
    val historyString: String = "",
    val operand: Double = 0.0,
    val operationType: OperationType = OperationType.NO_OPERATION,
    val memoryNumber: Double? = null,
    val errorCode: Int = NO_ERROR_CODE
)

enum class OperationType {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    NO_OPERATION
}