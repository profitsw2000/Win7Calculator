package ru.profitsw2000.data.entity

import ru.profitsw2000.data.constants.NO_ERROR_CODE

data class ScientificCalculatorDataEntity(
    val mainString: String = "0",
    val historyString: String = "",
    val operand: Double = 0.0,
    val scientificOperationType: ScientificOperationType = ScientificOperationType.NO_OPERATION,
    val memoryNumber: Double? = null,
    val errorCode: Int = NO_ERROR_CODE
)

enum class ScientificOperationType {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    MODULUS,
    NO_OPERATION
}
