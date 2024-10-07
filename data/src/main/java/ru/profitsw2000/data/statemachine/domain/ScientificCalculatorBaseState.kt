package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType

interface ScientificCalculatorBaseState : ScientificCalculatorState {

    fun clearMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun readMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun saveToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun addNumberToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun subtractNumberFromMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun negateOperand(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun calculateSquareRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun inputDigit(scientificCalculatorDataEntity: ScientificCalculatorDataEntity, digitToAppend: String): CalculatorState

    fun primitiveMathOperation(scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
                               scientificOperationType: ScientificOperationType,
                               operationString: String
    ): CalculatorState

    fun reciprocOperation(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun calculateResult(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun openBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun closeBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun calculateNaturalLogarithm(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun calculateExponent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun integerOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun fractionOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun hyperbolicSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun hyperbolicArcSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun sinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

    fun arcSinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

    fun squareNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun factorial(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun decimalToDegrees(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun hyperbolicCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun hyperbolicArcCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun cosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

    fun arcCosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

    fun mathOperation(scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
                      scientificOperationType: ScientificOperationType,
                      operationString: String
    ): CalculatorState

    fun piNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun doublePiNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun hyperbolicTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun hyperbolicArcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun tangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
                angleUnitCode: Int
    ): CalculatorState

    fun arcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
                   angleUnitCode: Int
    ): CalculatorState

    fun cubeNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun cubeRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun fixedToExponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun exponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun logarithmBaseTen(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun tenPowerX(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState
}