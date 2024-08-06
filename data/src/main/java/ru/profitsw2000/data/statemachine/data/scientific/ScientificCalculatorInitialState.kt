package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorBaseState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorState

class ScientificCalculatorInitialState(
    override val scientificCalculatorDataEntity: ScientificCalculatorDataEntity
) : ScientificCalculatorBaseState {

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> TODO()
            CalculatorAction.AddToMemory -> TODO()
            CalculatorAction.ArcCosine -> TODO()
            CalculatorAction.ArcSinus -> TODO()
            CalculatorAction.ArcTangent -> TODO()
            CalculatorAction.Backspace -> TODO()
            CalculatorAction.Clear -> clearAll(scientificCalculatorDataEntity)
            CalculatorAction.ClearEntered -> TODO()
            CalculatorAction.ClearMemory -> TODO()
            CalculatorAction.Cosine -> TODO()
            CalculatorAction.DecimalDegrees -> TODO()
            is CalculatorAction.Digit -> TODO()
            CalculatorAction.Divide -> TODO()
            CalculatorAction.Dms -> TODO()
            CalculatorAction.DoublePi -> TODO()
            CalculatorAction.Equal -> TODO()
            CalculatorAction.ExponentOfX -> TODO()
            CalculatorAction.ExponentialForm -> TODO()
            CalculatorAction.Factorial -> TODO()
            CalculatorAction.FixedToExponent -> TODO()
            CalculatorAction.Fraction -> TODO()
            CalculatorAction.HyperbolicArcCosine -> TODO()
            CalculatorAction.HyperbolicArcSinus -> TODO()
            CalculatorAction.HyperbolicArcTangent -> TODO()
            CalculatorAction.HyperbolicCosine -> TODO()
            CalculatorAction.HyperbolicSinus -> TODO()
            CalculatorAction.HyperbolicTangent -> TODO()
            CalculatorAction.Integer -> TODO()
            CalculatorAction.Inverse -> TODO()
            CalculatorAction.LeftBracket -> TODO()
            CalculatorAction.Logarithm -> TODO()
            CalculatorAction.Modulus -> TODO()
            CalculatorAction.Multiply -> TODO()
            CalculatorAction.NaturalLogarithm -> TODO()
            CalculatorAction.Percentage -> TODO()
            CalculatorAction.Pi -> TODO()
            CalculatorAction.PlusMinus -> TODO()
            CalculatorAction.ReadMemory -> TODO()
            CalculatorAction.Recipoc -> TODO()
            CalculatorAction.RightBracket -> TODO()
            CalculatorAction.SaveToMemory -> TODO()
            CalculatorAction.Sinus -> TODO()
            CalculatorAction.SquareRoot -> TODO()
            CalculatorAction.SquaredX -> TODO()
            CalculatorAction.Subtract -> TODO()
            CalculatorAction.SubtractFromMemory -> TODO()
            CalculatorAction.Tangent -> TODO()
            CalculatorAction.TenToPowerOfX -> TODO()
            CalculatorAction.ThirdRootOfX -> TODO()
            CalculatorAction.XPowerThree -> TODO()
            CalculatorAction.XPowerY -> TODO()
            CalculatorAction.YRootOfX -> TODO()
        }
    }

    /**
     * Clear internal memory of calculator
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return GeneralCalculatorInitialState with cleared memory of calculator data
     */
    override fun clearMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorInitialState(scientificCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Clears all data, contained in fields mainString, historyString and operand fields of calculator data.
     * OperationType set to NO_OPERATION state
     * @param generalCalculatorDataEntity - contains current calculator data
     * @return Initial state with some fields of calculator data set to default state
     */
    override fun clearAll(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorInitialState(ScientificCalculatorDataEntity(memoryNumber = scientificCalculatorDataEntity.memoryNumber))
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

    override fun hyperbolicSinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicArcSinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
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

    override fun decimalToDegrees(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicCosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicArcCosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
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

    override fun tangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun arcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
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

    override fun readMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun saveToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun addNumberToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun subtractNumberFromMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

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
}