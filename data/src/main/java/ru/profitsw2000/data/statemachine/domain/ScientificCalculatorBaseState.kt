package ru.profitsw2000.data.statemachine.domain

import ch.obermuhlner.math.big.BigDecimalMath
import ru.profitsw2000.data.constants.ARC_COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.ARC_SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.ARC_TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.DEGREES_ANGLE_CODE
import ru.profitsw2000.data.constants.DEGREES_TO_RADIANS_FUNCTION_CODE
import ru.profitsw2000.data.constants.DEG_FUNCTION_CODE
import ru.profitsw2000.data.constants.DMS_FUNCTION_CODE
import ru.profitsw2000.data.constants.EXPONENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.FRACTIONAL_PART_FUNCTION_CODE
import ru.profitsw2000.data.constants.GRADS_ANGLE_CODE
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
import ru.profitsw2000.data.constants.SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.utils.dropCalculationError
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.PI
import kotlin.math.abs

const val GRADS_TO_DEGREES_COEF = "1.11111111111111111111111111111111"
const val ZERO_STRING_NUMBER = "0"
const val ONE_STRING_NUMBER = "1"
const val NINE_STRING_NUMBER = "9"
const val TEN_STRING_NUMBER = "10"
const val PI_DEGREES = "180"
const val PI_2_DEGREES = "90"
const val PI_GRADS = "200"
const val PI_2_GRADS = "100"


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
        val number = BigDecimalMath.toBigDecimal(this.toStandardFormat()).stripTrailingZeros()

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
        return this.getNumberPart(functionCode).toResultString().toCalculatorFormat()
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
            this.getNumberPart(functionCode).toScientificNotationString().toCalculatorFormat()
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
        val mathContext = MathContext(scale + 2)
        val number = BigDecimalMath.toBigDecimal(this.toStandardFormat())

        val result = when (functionCode) {
            NATURAL_LOGARITHM_FUNCTION_CODE -> BigDecimalMath.log(number, mathContext)
            EXPONENT_FUNCTION_CODE -> BigDecimalMath.exp(number, mathContext)
            HYPERBOLIC_SINUS_FUNCTION_CODE -> BigDecimalMath.sinh(number, mathContext)
            HYPERBOLIC_ARC_SINUS_FUNCTION_CODE -> BigDecimalMath.asinh(number, mathContext)
            HYPERBOLIC_COSINE_FUNCTION_CODE -> BigDecimalMath.cosh(number, mathContext)
            HYPERBOLIC_ARC_COSINE_FUNCTION_CODE -> BigDecimalMath.acosh(number, mathContext)
            HYPERBOLIC_TANGENT_FUNCTION_CODE -> BigDecimalMath.tanh(number, mathContext)
            HYPERBOLIC_ARC_TANGENT_FUNCTION_CODE -> BigDecimalMath.atanh(number, mathContext)
            LOGARITHM_BASE_10_FUNCTION_CODE -> BigDecimalMath.log10(number, mathContext)
            else -> number
        }

        checkForOverflow(result)

        return result
    }

    /**
     * Calculates the result of the function of the number contained in the @this string
     * with high precision(scale + 2).
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in String type with high precision
     */
    fun String.highPrecisionMathFunction(functionCode: Int): String {
        return this.calculateMathFunction(functionCode)
            .toResultString()
            .toCalculatorFormat()
    }

    /**
     * Calculates the result of the function of the number contained in the @this string.
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in String type
     */
    fun String.mathFunction(functionCode: Int): String {
        val mathContext = MathContext(scale)
        return this.calculateMathFunction(functionCode)
            .round(mathContext)
            .stripTrailingZeros()
            .toResultString()
            .toCalculatorFormat()
    }

    /**
     * Сalculates the result of the function of the number contained in the @this string.
     * @param functionCode - contain code of function, that need to be done
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function parameter
     */
    fun String.mathFunction(functionCode: Int, isScientificNotation: Boolean): String {
        val mathContext = MathContext(scale)
        return if (isScientificNotation)
            this.calculateMathFunction(functionCode)
                .round(mathContext)
                .stripTrailingZeros()
                .toScientificNotationString()
                .toCalculatorFormat()
        else
            this.mathFunction(functionCode)
    }

    /**
     * Сalculates the result of the trigonometric function
     * of the number contained in the @this string,
     * Function selected depending on the value of functionCode parameter.
     * @param functionCode - contain code of function, that need to be done
     * @param angleUnitCode - defines what type of angle unit contains in @this number -
     * degrees, radians or grads
     * @return result of calculation in BigDecimal type
     */
    fun String.calculateTrigonometricFunction(functionCode: Int, angleUnitCode: Int): BigDecimal {
        val mathContext = MathContext(scale + 2)
        val number = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val angle = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> convertAngle(
                number,
                DEGREES_TO_RADIANS_FUNCTION_CODE
            )
            GRADS_ANGLE_CODE -> convertAngle(
                number,
                GRADS_TO_RADIANS_FUNCTION_CODE
            )
            else -> number
        }
        val result = when(functionCode) {
            SINUS_FUNCTION_CODE -> BigDecimalMath.sin(angle, mathContext)
            COSINE_FUNCTION_CODE -> BigDecimalMath.cos(angle, mathContext)
            TANGENT_FUNCTION_CODE -> BigDecimalMath.tan(angle, mathContext)
            else -> number
        }

        return checkAngle(result)
    }

    /**
     * Calculates the result of the trigonometric function of the number contained in the @this string.
     * @param functionCode - contain code of function, that need to be done
     * @param angleUnitCode - defines what type of angle unit contains in @this number -
     * degrees, radians or grads
     * @return result of calculation in String type
     */
    fun String.trigonometricFunction(functionCode: Int, angleUnitCode: Int): String {
        val mathContext = MathContext(scale)
        return this.calculateTrigonometricFunction(functionCode, angleUnitCode)
            .round(mathContext)
            .stripTrailingZeros()
            .toResultString()
            .toCalculatorFormat()
    }

    /**
     * Сalculates the result of the trigonometric function of the number contained in the @this string.
     * @param functionCode - contain code of function, that need to be done
     * @param angleUnitCode - defines what type of angle unit contains in @this number -
     * degrees, radians or grads
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function parameter
     */
    fun String.trigonometricFunction(functionCode: Int,
                                     angleUnitCode: Int,
                                     isScientificNotation: Boolean): String {
        val mathContext = MathContext(scale)
        return if (isScientificNotation)
            this.calculateTrigonometricFunction(functionCode, angleUnitCode)
                .round(mathContext)
                .stripTrailingZeros()
                .toScientificNotationString()
                .toCalculatorFormat()
        else
            this.trigonometricFunction(functionCode, angleUnitCode)
    }

    /**
     * Сalculates the result of the inverse trigonometric function
     * of the number contained in the @this string,
     * Function selected depending on the value of functionCode parameter.
     * @param functionCode - contain code of function, that need to be done
     * @param angleUnitCode - defines what type of angle unit contains in @this number -
     * degrees, radians or grads
     * @return result of calculation in BigDecimal type
     */
    fun String.calculateInverseTrigonometricFunction(functionCode: Int, angleUnitCode: Int): BigDecimal {
        val mathContext = MathContext(scale + 2)
        val number = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val result = when(functionCode) {
            ARC_SINUS_FUNCTION_CODE -> BigDecimalMath.asin(number, mathContext)
            ARC_COSINE_FUNCTION_CODE -> BigDecimalMath.acos(number, mathContext)
            ARC_TANGENT_FUNCTION_CODE -> BigDecimalMath.atan(number, mathContext)
            else -> number
        }
        val angle = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> convertAngle(
                result,
                RADIANS_TO_DEGREES_FUNCTION_CODE
            )
            GRADS_ANGLE_CODE -> convertAngle(
                result,
                RADIANS_TO_GRADS_FUNCTION_CODE
            )
            else -> result
        }

        return angle
    }

    /**
     * Calculates the result of the inverse trigonometric function
     * of the number contained in the @this string.
     * @param functionCode - contain code of function, that need to be done
     * @param angleUnitCode - defines what type of angle unit contains in @this number -
     * degrees, radians or grads
     * @return result of calculation in String type
     */
    fun String.inverseTrigonometricFunction(functionCode: Int, angleUnitCode: Int): String {
        val mathContext = MathContext(scale)
        return this.calculateInverseTrigonometricFunction(functionCode, angleUnitCode)
            .round(mathContext)
            .stripTrailingZeros()
            .toResultString()
            .toCalculatorFormat()
    }

    /**
     * Сalculates the result of the inverse trigonometric function
     * of the number contained in the @this string.
     * @param functionCode - contain code of function, that need to be done
     * @param angleUnitCode - defines what type of angle unit contains in @this number -
     * degrees, radians or grads
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function parameter
     */
    fun String.inverseTrigonometricFunction(functionCode: Int,
                                     angleUnitCode: Int,
                                     isScientificNotation: Boolean): String {
        val mathContext = MathContext(scale)
        return if (isScientificNotation)
            this.calculateInverseTrigonometricFunction(functionCode, angleUnitCode)
                .round(mathContext)
                .stripTrailingZeros()
                .toScientificNotationString()
                .toCalculatorFormat()
        else
            this.trigonometricFunction(functionCode, angleUnitCode)
    }

    /** Converts number in @this to radians/degrees/grads
     * from radians/degrees/grads (depending on functionCode param).
     * @param angle - number to convert
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in BigDecimal type
     */
    fun convertAngle(angle: BigDecimal, functionCode: Int): BigDecimal {
        val mathContext = MathContext(scale + 2)
        val nineNumber = BigDecimalMath.toBigDecimal(NINE_STRING_NUMBER)
        val tenNumber = BigDecimalMath.toBigDecimal(TEN_STRING_NUMBER)

        val result = when(functionCode) {
            DEGREES_TO_RADIANS_FUNCTION_CODE -> BigDecimalMath.toRadians(angle, mathContext)
            RADIANS_TO_DEGREES_FUNCTION_CODE -> BigDecimalMath.toDegrees(angle, mathContext)
            GRADS_TO_RADIANS_FUNCTION_CODE -> BigDecimalMath.toRadians(
                angle.multiply(nineNumber).divide(tenNumber,mathContext),
                mathContext
            )
            RADIANS_TO_GRADS_FUNCTION_CODE -> BigDecimalMath.toDegrees(angle, mathContext)
                .multiply(tenNumber).divide(nineNumber, mathContext)
            else -> angle
        }
        checkForOverflow(result)

        return result.stripTrailingZeros()
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

        return result.stripTrailingZeros()
    }

    /** Calculates power of number contained in @this (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @param exponent - exponent or root number
     * @return result of calculation in String type
     */
    fun String.powerOfNumber(exponent: String): String {
        return this.calculatePowerOfNumber(exponent, POWER_OF_FUNCTION_CODE).toResultString().toCalculatorFormat()
    }

    /** Calculates power of number contained in @this (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @param exponent - exponent or root number
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function parameter
     */
    fun String.powerOfNumber(exponent: String, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculatePowerOfNumber(exponent, POWER_OF_FUNCTION_CODE).toScientificNotationString().toCalculatorFormat()
        else
            this.powerOfNumber(exponent)
    }

    /** Calculates power or root of number contained in @this (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @param exponent - exponent or root number
     * @return result of calculation in String type
     */
    fun String.rootOfNumber(exponent: String): String {
        return this.calculatePowerOfNumber(exponent, ROOT_OF_FUNCTION_CODE).toResultString().toCalculatorFormat()
    }

    /** Calculates power or root of number contained in @this (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @param exponent - exponent or root number
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function parameter
     */
    fun String.rootOfNumber(exponent: String, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculatePowerOfNumber(exponent, ROOT_OF_FUNCTION_CODE).toScientificNotationString().toCalculatorFormat()
        else
            this.rootOfNumber(exponent)
    }

    /** Converts number in @this to radians/degrees/grads from radians/degrees/grads (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in BigDecimal type
     */
    fun String.convertAngleUnits(functionCode: Int): BigDecimal {
        val mathContext = MathContext(scale + 2)
        val number = BigDecimalMath.toBigDecimal(this.toStandardFormat())
        val nineNumber = BigDecimalMath.toBigDecimal(NINE_STRING_NUMBER)
        val tenNumber = BigDecimalMath.toBigDecimal(TEN_STRING_NUMBER)

        val result = when(functionCode) {
            DEGREES_TO_RADIANS_FUNCTION_CODE -> BigDecimalMath.toRadians(number, mathContext)
            RADIANS_TO_DEGREES_FUNCTION_CODE -> BigDecimalMath.toDegrees(number, mathContext)
            GRADS_TO_RADIANS_FUNCTION_CODE -> BigDecimalMath.toRadians(
                number.multiply(nineNumber).divide(tenNumber,mathContext),
                mathContext
            )
            RADIANS_TO_GRADS_FUNCTION_CODE -> BigDecimalMath.toDegrees(number, mathContext)
                .multiply(tenNumber).divide(nineNumber, mathContext)
            else -> number
        }
        checkForOverflow(result)

        return result.stripTrailingZeros()
    }

    /** Converts number in @this to radians/degrees/grads from radians/degrees/grads (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in String type
     */
    fun String.convert(functionCode: Int): String {
        val mathContext = MathContext(scale)
        return this
            .convertAngleUnits(functionCode)
            .round(mathContext)
            .toResultString()
            .toCalculatorFormat()
    }

    /** Converts number in @this to radians/degrees/grads from radians/degrees/grads (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in String type
     */
    fun String.highPrecisionConvert(functionCode: Int): String {
        return this
            .convertAngleUnits(functionCode)
            .toResultString()
            .toCalculatorFormat()
    }

    /** Converts number in @this to radians/degrees/grads from radians/degrees/grads (depending on functionCode param).
     * @param functionCode - contain code of function, that need to be done
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function isScientificNotation parameter
     */
    fun String.convert(functionCode: Int, isScientificNotation: Boolean): String {
        val mathContext = MathContext(scale)
        return if (isScientificNotation)
            this.convertAngleUnits(functionCode)
                .round(mathContext)
                .toResultString()
                .toCalculatorFormat()
        else
            this.convert(functionCode)
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
                    (fraction.toStandardFormat().multiply("60")).divide("100")
                ).toStandardFormat(),
                mathContext
            )
            DEG_FUNCTION_CODE -> BigDecimalMath.toBigDecimal(
                integral.add(
                    (fraction.multiply("100")).divide("60")
                ).toStandardFormat(),
                mathContext
            )
            else -> BigDecimalMath.toBigDecimal(this.toStandardFormat())
        }

        checkForOverflow(result)

        return result.stripTrailingZeros()
    }

    /** Converts number in @this with fractional part in minutes to
     * number with decimal fractional part or otherwise(depending on functionCode param)
     * @param functionCode - contain code of function, that need to be done
     * @return result of calculation in String type
     */
    fun String.decimalMinutes(functionCode: Int): String {
        return this.calculateDegreesFractionPart(functionCode).toResultString().toCalculatorFormat()
    }


    /** Converts number in @this with fractional part in minutes to
     * number with decimal fractional part or otherwise(depending on functionCode param)
     * @param functionCode - contain code of function, that need to be done
     * @param isScientificNotation - define result string format
     * @return result of calculation in String type with engineering
     * or plain format depending on function parameter     */
    fun String.decimalMinutes(functionCode: Int, isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculateDegreesFractionPart(functionCode).toScientificNotationString().toCalculatorFormat()
        else
            this.decimalMinutes(functionCode)
    }

    fun piNumber(): String {
        val mathContext = MathContext(scale)
        return BigDecimalMath.pi(mathContext).toResultString().toCalculatorFormat()
    }

    fun piNumber(isScientificNotation: Boolean): String {
        val mathContext = MathContext(scale)
        return if (isScientificNotation)
            BigDecimalMath.pi(mathContext).toScientificNotationString().toCalculatorFormat()
        else
            piNumber()
    }

    fun doublePiNumber(): String {
        val mathContext = MathContext(scale)
        return BigDecimalMath.pi(mathContext).toResultString().toCalculatorFormat().multiply("2")
    }

    fun doublePiNumber(isScientificNotation: Boolean): String {
        val mathContext = MathContext(scale)
        return if (isScientificNotation)
            BigDecimalMath.pi(mathContext).toScientificNotationString().toCalculatorFormat().multiply("2")
        else
            piNumber()
    }

    /**Calculates factorial of @this number and return result in BigDecimal type.
     * @return  result of calculation in String type
     */
    fun String.calculateFactorial(): BigDecimal {
        val mathContext = MathContext(scale)
        val number = BigDecimalMath.toBigDecimal(this.toStandardFormat())

        val result = BigDecimalMath.factorial(number, mathContext)

        checkForOverflow(result)
        return result.stripTrailingZeros()
    }

    /**Calculates factorial of @this number and return result in String type.
     * @return  result of calculation in String type
     */
    fun String.factorial(): String {
        return this.calculateFactorial().toResultString().toCalculatorFormat()
    }

    /**Calculates factorial of @this number and return result in String type
     * @param isScientificNotation - define result string format
     * @return  result of calculation in String type with engineering
     * or plain format depending on function parameter
     */
    fun String.factorial(isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            this.calculateFactorial().toScientificNotationString().toCalculatorFormat()
        else
            this.factorial()
    }

    /**
     * Format number in the String to conventional or scientific(engineering) form
     * according to function parameter.
     * @param isScientificNotation if true then format string with number to engineering format (1,234e+2),
     * if it is false, then format to conventional form (123,4).
     * @return formatted String
     */
    fun String.formatStringNumber(isScientificNotation: Boolean): String {
        return if (isScientificNotation)
            BigDecimalMath.toBigDecimal(this.toStandardFormat()).toScientificNotationString().toCalculatorFormat()
        else
            BigDecimalMath.toBigDecimal(this.toStandardFormat()).toResultString().toCalculatorFormat()
    }

    private fun checkAngle(angle: BigDecimal): BigDecimal {
        val maxValueString = "1E+31"
        val minValueString = "1.75E-32"
        val maxValueBigDecimal = BigDecimalMath.toBigDecimal(maxValueString)
        val minValueBigDecimal = BigDecimalMath.toBigDecimal(minValueString)
        val zeroString = "0"
        val zeroBigDecimal = BigDecimalMath.toBigDecimal(zeroString)

        return when{
            angle.abs().compareTo(minValueBigDecimal) == -1 -> zeroBigDecimal
            angle.abs().compareTo(maxValueBigDecimal) == 1 -> throw ArithmeticException("Overflow number exception")
            else -> angle
        }
    }
}