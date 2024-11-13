package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorBaseState
import kotlin.math.sqrt

class ScientificCalculatorFirstOperandInputState(
    override val scientificCalculatorDataEntity: ScientificCalculatorDataEntity
) : ScientificCalculatorBaseState {

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        when(action) {
            CalculatorAction.Add -> TODO()
            CalculatorAction.AddToMemory -> TODO()
            is CalculatorAction.ArcCosine -> TODO()
            is CalculatorAction.ArcSinus -> TODO()
            is CalculatorAction.ArcTangent -> TODO()
            CalculatorAction.Backspace -> TODO()
            CalculatorAction.Clear -> TODO()
            CalculatorAction.ClearEntered -> TODO()
            CalculatorAction.ClearMemory -> TODO()
            is CalculatorAction.Cosine -> TODO()
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
            is CalculatorAction.Sinus -> TODO()
            CalculatorAction.SquareRoot -> TODO()
            CalculatorAction.SquaredX -> TODO()
            CalculatorAction.Subtract -> TODO()
            CalculatorAction.SubtractFromMemory -> TODO()
            is CalculatorAction.Tangent -> TODO()
            CalculatorAction.TenToPowerOfX -> TODO()
            CalculatorAction.ThirdRootOfX -> TODO()
            CalculatorAction.XPowerThree -> TODO()
            CalculatorAction.XPowerY -> TODO()
            CalculatorAction.YRootOfX -> TODO()
        }
    }

    /**
     * Copied function parameter, sets field memoryNumber to null, create instance of ScientificCalculatorFirstOperandInputState
     * with newly created calculator data as constructor and return it.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun clearMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandInputState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = null
            )
        )
    }

    /**
     * Copied parameter of function, which is a calculator data, reads memory field value and converts it
     * to string value, which is recorded to mainString field of calculator data. Then created instance of
     * ScientificCalculatorFirstOperandReadState with newly created calculator data as constructor and
     * return it.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun readMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = if (scientificCalculatorDataEntity.memoryNumber == null) "0"
                else doubleToCalculatorString(scientificCalculatorDataEntity.memoryNumber)
            )
        )
    }

    /**
     * Copied function parameter, which is a calculator data, converts mainString field to Double type and
     * write it to memoryNumber field if it is not zero. Otherwise writes to mainString field null. Then created instance of
     * ScientificCalculatorFirstOperandReadState with newly created calculator data as constructor and
     * return it.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun saveToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = if (calculatorStringToDouble(scientificCalculatorDataEntity.mainString) == 0.0) null
                else calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Copied function parameter, which is a calculator data, converts string from mainString field to Double
     * type and add it to number in memoryNumber field. Then created instance of
     * ScientificCalculatorFirstOperandReadState with newly created calculator data as constructor and
     * return it.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun addNumberToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val addedNumber = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = if (scientificCalculatorDataEntity.memoryNumber == null) addedNumber
                else calculatorStringToDouble(scientificCalculatorDataEntity.mainString) + addedNumber
            )
        )
    }

    /**
     * Subtracts entered to mainString number from memory.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun subtractNumberFromMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val subtractedNumber = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = if (scientificCalculatorDataEntity.memoryNumber == null) subtractedNumber
                else calculatorStringToDouble(scientificCalculatorDataEntity.mainString) + subtractedNumber
            )
        )
    }

    /**
     * Changes sign of entered in mainString to opposite without changing current state.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun negateOperand(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandInputState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    0 - calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
                )
            )
        )
    }

    /**
     * Calculate square root of entered to mainString number and write result number back to mainString.
     * Completed operation writes to historyString, current state changed.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if
     * number in mainString is equal or more than zero
     * ScientificCalculatorErrorState with appropriate code in errorCode field
     */
    override fun calculateSquareRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            val sqrtDouble = sqrt(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
            val sqrtString = doubleToCalculatorString(sqrtDouble)

            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = sqrtString,
                    historyString = if (scientificCalculatorDataEntity.historyString == "") "sqrt(${scientificCalculatorDataEntity.mainString})"
                    else "sqrt(${scientificCalculatorDataEntity.historyString})"
                )
            )
        } catch (numberFormatException: NumberFormatException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "sqrt(${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "sqrt(${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
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

    override fun clearAll(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }
}