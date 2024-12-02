package ru.profitsw2000.data.statemachine.domain

import ch.obermuhlner.math.big.BigDecimalMath
import ru.profitsw2000.data.constants.ARC_COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.ARC_SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.ARC_TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.DEGREES_TO_RADIANS_FUNCTION_CODE
import ru.profitsw2000.data.constants.DEG_FUNCTION_CODE
import ru.profitsw2000.data.constants.DMS_FUNCTION_CODE
import ru.profitsw2000.data.constants.EXPONENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.FRACTIONAL_PART_FUNCTION_CODE
import ru.profitsw2000.data.constants.GRADS_TO_RADIANS_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_ARC_COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_ARC_SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_ARC_TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.INTEGRAL_PART_FUNCTION_CODE
import ru.profitsw2000.data.constants.LOGARITHM_BASE_10_FUNCTION_CODE
import ru.profitsw2000.data.constants.NATURAL_LOGARITHM_FUNCTION_CODE
import ru.profitsw2000.data.constants.POWER_OF_FUNCTION_CODE
import ru.profitsw2000.data.constants.RADIANS_TO_DEGREES_FUNCTION_CODE
import ru.profitsw2000.data.constants.RADIANS_TO_GRADS_FUNCTION_CODE
import ru.profitsw2000.data.constants.ROOT_OF_FUNCTION_CODE
import ru.profitsw2000.data.constants.SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.utils.dropCalculationError
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.PI

const val GRADS_TO_DEGREES_COEF = "1.11111111111111111111111111111111"

interface ScientificCalculatorBaseState : ScientificCalculatorState {

    fun clearMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun readMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun saveToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun addNumberToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun subtractNumberFromMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun negateOperand(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun calculateSquareRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun inputDigit(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        digitToAppend: String
    ): CalculatorState

    fun primitiveMathOperation(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
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

    fun decimalToMinutes(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun minutesToDecimal(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

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

    fun mathOperation(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        scientificOperationType: ScientificOperationType,
        operationString: String
    ): CalculatorState

    fun piNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun doublePiNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun hyperbolicTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun hyperbolicArcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun tangent(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

    fun arcTangent(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState

    fun cubeNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun cubeRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun fixedToExponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun exponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun logarithmBaseTen(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun tenPowerX(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState

    fun radiansFromDegrees(angleInDegrees: Double): Double {
        return (PI * angleInDegrees) / 180.0
    }

    fun radiansFromGrads(angleInGrads: Double): Double {
        return (PI * angleInGrads) / 200.0
    }

    fun degreesFromRadians(angleInRadians: Double): Double {
        return ((angleInRadians * 180.0) / PI).dropCalculationError()
    }

    fun gradsFromRadians(angleInRadians: Double): Double {
        return ((angleInRadians * 200.0) / PI).dropCalculationError()
    }

    /**
     * Truncate integral or fractional part of @this (depending on parameter value)
     * and return result in BigDecimal type.
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in BigDecimal type
     */
    fun String.getNumberPart(functionCode: Int): BigDecimal {
        val number = BigDecimalMath.toBigDecimal(this.toStandardFormat())

        return when(functionCode) {
            INTEGRAL_PART_FUNCTION_CODE -> BigDecimalMath.integralPart(number)
            FRACTIONAL_PART_FUNCTION_CODE -> BigDecimalMath.fractionalPart(number)
            else -> number
        }
    }

    /**
     * Truncate integral or fractional part of @this (depending on parameter value)
     * and return result in String type.
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in String type
     */
    fun String.numberPart(functionCode: Int): String {
        return this.getNumberPart(functionCode).toString().toCalculatorFormat()
    }

    /**
     * Truncate integral or fractional part of @this (depending on parameter value)
     * and return result in String type
     * with engineering or plain format depending on function parameter
     * @param functionCode - contain code of function, that need to be done
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type
     */
    fun String.numberPart(functionCode: Int, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.getNumberPart(functionCode).toEngineeringString().toCalculatorFormat()
        else
            this.numberPart(functionCode)
    }

    /**
     * Сalculates the result of the function of the number contained in the @this string,
     * Function selected depending on the value of functionCode parameter.
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in BigDecimal type
     */
    fun String.calculateMathFunction(functionCode: Int): BigDecimal {
        val mathContext = MathContext(scale)
        val number = BigDecimalMath.toBigDecimal(this.toStandardFormat())

        val result = when (functionCode) {
            NATURAL_LOGARITHM_FUNCTION_CODE -> BigDecimalMath.log(number, mathContext)
            EXPONENT_FUNCTION_CODE -> BigDecimalMath.exp(number, mathContext)
            HYPERBOLIC_SINUS_FUNCTION_CODE -> BigDecimalMath.sinh(number, mathContext)
            HYPERBOLIC_ARC_SINUS_FUNCTION_CODE -> BigDecimalMath.asinh(number, mathContext)
            SINUS_FUNCTION_CODE -> BigDecimalMath.sin(number, mathContext)
            ARC_SINUS_FUNCTION_CODE -> BigDecimalMath.asin(number, mathContext)
            HYPERBOLIC_COSINE_FUNCTION_CODE -> BigDecimalMath.cosh(number, mathContext)
            HYPERBOLIC_ARC_COSINE_FUNCTION_CODE -> BigDecimalMath.acosh(number, mathContext)
            COSINE_FUNCTION_CODE -> BigDecimalMath.cos(number, mathContext)
            ARC_COSINE_FUNCTION_CODE -> BigDecimalMath.acos(number, mathContext)
            HYPERBOLIC_TANGENT_FUNCTION_CODE -> BigDecimalMath.tanh(number, mathContext)
            HYPERBOLIC_ARC_TANGENT_FUNCTION_CODE -> BigDecimalMath.atanh(number, mathContext)
            TANGENT_FUNCTION_CODE -> BigDecimalMath.tan(number, mathContext)
            ARC_TANGENT_FUNCTION_CODE -> BigDecimalMath.atan(number, mathContext)
            LOGARITHM_BASE_10_FUNCTION_CODE -> BigDecimalMath.log10(number, mathContext)
            else -> number
        }

        checkForOverflow(result)

        return result
    }

    /**
     * Calculates the result of the function of the number contained in the @this string.
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in String type
     */
    fun String.mathFunction(functionCode: Int): String {
        return this.calculateMathFunction(functionCode).toString().toCalculatorFormat()
    }

    /**
     * Сalculates the result of the function of the number contained in the @this string.
     * @param functionCode - contain code of function, that need to be done
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function parameter
     */
    fun String.mathFunction(functionCode: Int, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculateMathFunction(functionCode).toEngineeringString().toCalculatorFormat()
        else
            this.mathFunction(functionCode)
    }

    /** Calculates power or root of number contained in @this (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @param exponent - exponent or root number
     * @return result of calculation in BigDecimal type
     */
    fun String.calculatePowerOfNumber(exponent: String, functionCode: Int): BigDecimal {
        val mathContext = MathContext(scale)
        val base = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val exponent = BigDecimalMath.toBigDecimal(exponent.toStandardFormat())

        val result = when(functionCode) {
            POWER_OF_FUNCTION_CODE -> BigDecimalMath.pow(base, exponent, mathContext)
            ROOT_OF_FUNCTION_CODE -> BigDecimalMath.root(base, exponent, mathContext)
            else -> base
        }

        checkForOverflow(result)

        return result
    }

    /** Calculates power or root of number contained in @this (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @param exponent - exponent or root number
     * @return result of calculation in String type
     */
    fun String.powerOfNumber(exponent: String, functionCode: Int): String {
        return this.calculatePowerOfNumber(exponent, functionCode).toString().toCalculatorFormat()
    }

    /** Calculates power or root of number contained in @this (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @param exponent - exponent or root number
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function parameter
     */
    fun String.powerOfNumber(exponent: String, functionCode: Int, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculatePowerOfNumber(exponent, functionCode).toEngineeringString().toCalculatorFormat()
        else
            this.powerOfNumber(exponent, functionCode)
    }

    /** Converts number in @this to radians/degrees/grads from radians/degrees/grads (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in BigDecimal type
     */
    fun String.convertAngleUnits(functionCode: Int): BigDecimal {
        val mathContext = MathContext(scale)
        val number = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val result = when(functionCode) {
            DEGREES_TO_RADIANS_FUNCTION_CODE -> BigDecimalMath.toRadians(number, mathContext)
            RADIANS_TO_DEGREES_FUNCTION_CODE -> BigDecimalMath.toDegrees(number, mathContext)
            GRADS_TO_RADIANS_FUNCTION_CODE -> BigDecimalMath.toRadians(
                number.divide(BigDecimalMath.toBigDecimal(GRADS_TO_DEGREES_COEF, mathContext)),
                mathContext
            )
            RADIANS_TO_GRADS_FUNCTION_CODE -> BigDecimalMath.toDegrees(number, mathContext).multiply(
                BigDecimalMath.toBigDecimal(GRADS_TO_DEGREES_COEF, mathContext),
                mathContext
            )
            else -> number
        }

        checkForOverflow(result)

        return result
    }

    /** Converts number in @this to radians/degrees/grads from radians/degrees/grads (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in String type
     */
    fun String.convert(functionCode: Int): String {
        return this.convertAngleUnits(functionCode).toString().toCalculatorFormat()
    }

    /** Converts number in @this to radians/degrees/grads from radians/degrees/grads (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function isScientificNotation parameter
     */
    fun String.convert(functionCode: Int, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.convertAngleUnits(functionCode).toEngineeringString().toCalculatorFormat()
        else
            this.convertAngleUnits(functionCode).toString().toCalculatorFormat()
    }

    /** Converts number in @this with fractional part in minutes to
     * number with decimal fractional part or otherwise(depending on functionCode param)
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in BigDecimal type
     */
    fun String.calculateDegreesFractionPart(functionCode: Int): BigDecimal {
        val mathContext = MathContext(scale)
        val fraction = this.numberPart(FRACTIONAL_PART_FUNCTION_CODE)
        val integral = this.numberPart(INTEGRAL_PART_FUNCTION_CODE)
        val result = when(functionCode) {
            DMS_FUNCTION_CODE -> BigDecimalMath.toBigDecimal(
                integral.add(
                    (fraction.multiply("60")).divide("100")
                ),
                mathContext
            )
            DEG_FUNCTION_CODE -> BigDecimalMath.toBigDecimal(
                integral.add(
                    (fraction.multiply("60")).divide("100")
                ),
                mathContext
            )
            else -> BigDecimalMath.toBigDecimal(this)
        }

        checkForOverflow(result)

        return result
    }

    /** Converts number in @this with fractional part in minutes to
     * number with decimal fractional part or otherwise(depending on functionCode param)
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in String type
     */
    fun String.decimalMinutes(functionCode: Int): String {
        return this.calculateDegreesFractionPart(functionCode).toString().toCalculatorFormat()
    }


    /** Converts number in @this with fractional part in minutes to
     * number with decimal fractional part or otherwise(depending on functionCode param)
     * @param functionCode - contain code of function, that need to be done
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function parameter     */
    fun String.decimalMinutes(functionCode: Int, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculateDegreesFractionPart(functionCode).toEngineeringString().toCalculatorFormat()
        else
            this.decimalMinutes(functionCode)
    }

    fun piNumber(): String {
        val mathContext = MathContext(scale)
        return BigDecimalMath.pi(mathContext).toString().toCalculatorFormat()
    }

    fun piNumber(isScientificNotation: Boolean): String {
        val mathContext = MathContext(scale)
        return if (isScientificNotation)
            BigDecimalMath.pi(mathContext).toEngineeringString().toCalculatorFormat()
        else
            piNumber()
    }

    fun doublePiNumber(): String {
        val mathContext = MathContext(scale)
        return BigDecimalMath.pi(mathContext).toString().toCalculatorFormat().multiply("2")
    }

    fun doublePiNumber(isScientificNotation: Boolean): String {
        val mathContext = MathContext(scale)
        return if (isScientificNotation)
            BigDecimalMath.pi(mathContext).toEngineeringString().toCalculatorFormat().multiply("2")
        else
            piNumber()
    }
}