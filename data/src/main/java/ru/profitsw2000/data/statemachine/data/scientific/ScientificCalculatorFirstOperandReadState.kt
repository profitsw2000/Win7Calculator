package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorBaseState

class ScientificCalculatorFirstOperandReadState(
    override val scientificCalculatorDataEntity: ScientificCalculatorDataEntity
) : ScientificCalculatorBaseState{

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        TODO("Not yet implemented")
    }

    /**
     * Copied function parameter, sets field memoryNumber to null, create instance of ScientificCalculatorFirstOperandReadState
     * with newly created calculator data as constructor and return it.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun clearMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                memoryNumber = null
            )
        )
    }

    /**
     * Copied parameter of function, which is a calculator data, reads memory field value and converts it
     * to string value, which is recorded to mainString field of calculator data. historyString field cleared.
     * Then created instance of ScientificCalculatorFirstOperandReadState with newly created calculator data as constructor and
     * return it.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun readMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = if (scientificCalculatorDataEntity.memoryNumber == null) "0".calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                )
                else scientificCalculatorDataEntity.memoryNumber.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = scientificCalculatorDataEntity.historyString.replaceAfterLast("(", "")
            )
        )
    }

    /**
     * Saved number from mainString of calculator data to memoryNumber field of calculator data and
     * changes current state of calculator to ScientificCalculatorFirstOperandReadState. Number in
     * mainString formatted according to isScientificNotation field value.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun saveToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = if (scientificCalculatorDataEntity.mainString == "0") null
                else scientificCalculatorDataEntity.mainString,
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                )
            )
        )
    }

    override fun addNumberToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun subtractNumberFromMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun clearAll(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override val scale: Int
        get() = TODO("Not yet implemented")

    override fun negateOperand(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun calculateSquareRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun inputDigit(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        digitToAppend: String
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun primitiveMathOperation(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        scientificOperationType: ScientificOperationType,
        operationString: String
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun reciprocOperation(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun calculateResult(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun openBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun closeBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun calculateNaturalLogarithm(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun calculateExponent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun integerOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun fractionOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicArcSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun sinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun arcSinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun squareNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun factorial(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun decimalToMinutes(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun minutesToDecimal(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicArcCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun cosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun arcCosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun mathOperation(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        scientificOperationType: ScientificOperationType,
        operationString: String
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun piNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun doublePiNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicArcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun tangent(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun arcTangent(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun cubeNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun cubeRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun fixedToExponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun exponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun logarithmBaseTen(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun tenPowerX(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }
}