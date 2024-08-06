package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType

interface ScientificCalculatorBaseState : GeneralCalculatorBaseState {

    fun openBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun closeBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun calculateNaturalLogarithm(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun calculateExponent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun integerOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun fractionOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun hyperbolicSinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

    fun hyperbolicArcSinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

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

    fun hyperbolicCosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

    fun hyperbolicArcCosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

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

    fun tangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun arcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun cubeNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun cubeRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun fixedToExponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun exponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun logarithmBaseTen(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun tenPowerX(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState
}