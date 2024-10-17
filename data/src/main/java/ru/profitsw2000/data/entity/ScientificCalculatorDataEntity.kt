package ru.profitsw2000.data.entity

import ru.profitsw2000.data.constants.NO_ERROR_CODE
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorBaseState

data class ScientificCalculatorDataEntity(
    val mainString: String = "0",
    val historyString: String = "",
    val operand: Double = 0.0,
    val scientificOperationType: ScientificOperationType = ScientificOperationType.NO_OPERATION,
    val memoryNumber: Double? = null,
    val errorCode: Int = NO_ERROR_CODE,
    val prevState: ScientificCalculatorBaseState? = null,
    val isScientificNotation: Boolean = false
)

enum class ScientificOperationType {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    MODULUS,
    POWER_OF,
    ROOT_OF,
    NO_OPERATION
}
