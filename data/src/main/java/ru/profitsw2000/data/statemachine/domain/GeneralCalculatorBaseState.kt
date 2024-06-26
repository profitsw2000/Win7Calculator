package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType

interface GeneralCalculatorBaseState : GeneralCalculatorState {

    fun clearMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

    fun readMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

    fun saveToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

    fun addNumberToMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

    fun subtractNumberFromMemory(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

    fun negateOperand(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

    fun calculateSquareRoot(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

    fun inputDigit(generalCalculatorDataEntity: GeneralCalculatorDataEntity, digitToAppend: String): GeneralCalculatorState

    fun primitiveMathOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity,
                               operationType: OperationType,
                               operationString: String): GeneralCalculatorState

    fun percentageOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

    fun reciprocOperation(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

    fun calculateResult(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

}