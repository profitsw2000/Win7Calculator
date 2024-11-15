package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.constants.DEGREES_ANGLE_CODE
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.GENERAL_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.GRADS_ANGLE_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.OVERFLOW_ERROR_CODE
import ru.profitsw2000.data.constants.RADIANS_ANGLE_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorErrorState
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorFirstOperandInputState
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorFirstOperandReadState
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorSecondOperandInputState
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorBaseState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorInputState
import ru.profitsw2000.utils.calcCosh
import ru.profitsw2000.utils.calcSinh
import ru.profitsw2000.utils.commaTruncate
import ru.profitsw2000.utils.factorial
import ru.profitsw2000.utils.powerTo
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.acosh
import kotlin.math.asin
import kotlin.math.asinh
import kotlin.math.atan
import kotlin.math.atanh
import kotlin.math.cbrt
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.math.tanh
import kotlin.math.truncate

class ScientificCalculatorFirstOperandInputState(
    override val scientificCalculatorDataEntity: ScientificCalculatorDataEntity
) : ScientificCalculatorInputState {

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
     * Deletes last character in mainString field.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun clearDigit(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val truncatedString = scientificCalculatorDataEntity.mainString.dropLast(1)
        val mainString = if(truncatedString.length == 0 ||
            truncatedString == "-0" ||
            truncatedString == "-") "0"
        else truncatedString

        return ScientificCalculatorFirstOperandInputState(
            scientificCalculatorDataEntity.copy(
                mainString = mainString
            )
        )
    }

    /**
     * Clears mainString field of calculator data and sets it to "0" value.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun clearEntered(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandInputState(
            scientificCalculatorDataEntity.copy(
                mainString = "0"
            )
        )
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
                    historyString = if (scientificCalculatorDataEntity.historyString == "") "sqrt(${scientificCalculatorDataEntity.mainString.commaTruncate()})"
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

    /**
     * Append digit in second parameter of fun to mainString of calculator data if it
     * satisfy to a certain condition.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun inputDigit(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        digitToAppend: String
    ): CalculatorState {
        val mainString = scientificCalculatorDataEntity.mainString

        return when {
            mainString.contains(",") && digitToAppend == "," -> this
            mainString.length >= GENERAL_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER && !(mainString.contains(",")) -> this
            mainString.length >= (GENERAL_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER + 1) -> this
            mainString == "0" && mainString.length < 2 -> ScientificCalculatorFirstOperandInputState(
                scientificCalculatorDataEntity.copy(
                    mainString = digitToAppend
                )
            )
            else -> ScientificCalculatorFirstOperandInputState(
                scientificCalculatorDataEntity.copy(
                    mainString = "$mainString${digitToAppend}"
                )
            )
        }
    }

    /**
     * Changes current state and writes to historyString, scientificOperationType and firstOperand fields
     * of calculator data appropriate info.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @param scientificOperationType - contains operation type that needs to execute
     * @param operationString - string with corresponding sign, depended on operation type
     * @return ScientificCalculatorMathOperationState with updated calculator data
     */
    override fun primitiveMathOperation(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        scientificOperationType: ScientificOperationType,
        operationString: String
    ): CalculatorState {
        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                historyString = "${scientificCalculatorDataEntity.mainString.commaTruncate()}" +
                        "$HISTORY_STRING_SPACE_LETTER$operationString",
                scientificOperationType = scientificOperationType,
                operand = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Calculates result of one divided on number entered in mainString field and
     * writes it back to mainString. Designator of committed operation appended to history string.
     * Changes current state depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data if
     * divider is not equal to zero
     * ScientificCalculatorErrorState with updated calculator data if divider equal 0
     */
    override fun reciprocOperation(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(1/(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))),
                historyString = "reciproc(${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            ))
        } catch (numberFormatException: NumberFormatException) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                historyString = "reciproc(${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            ))
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                historyString = "reciproc(${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                errorCode = UNKNOWN_ERROR_CODE
            ))
        }
    }

    /**
     * Do nothing
     */
    override fun calculateResult(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return this
    }

    /**
     * Changes current state to ScientificCalculatorFirstOperandReadState while current
     * state recorded to prevState field of new state. Calculator data of new state remains
     * the same, except historyString field to which character '(' is appended.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun openBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.commaTruncate(),
                historyString = "${scientificCalculatorDataEntity.historyString}(",
                prevState = this
            )
        )
    }

    /**
     * If bracket was not opened before, then do nothing. Otherwise changes state, depending on state
     * that was before opening bracket(contains in prevState field of calculator data). Operation
     * recorded in historyString. prevState field of calculator data of state preceded to opening
     * bracket recorded to calculator data of newly created state.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return this - if bracket was not opened,
     * otherwise - ScientificCalculatorFirstOperandReadState or
     * ScientificCalculatorSecondOperandReadState
     * (depending on state preceded bracket opening)with updated calculator data
     */
    override fun closeBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return if (scientificCalculatorDataEntity.prevState == null) this
        else {
            val returnData = scientificCalculatorDataEntity.prevState.scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.commaTruncate(),
                memoryNumber = scientificCalculatorDataEntity.memoryNumber,
                historyString = "${scientificCalculatorDataEntity.historyString.commaTruncate()})"
            )
            when(scientificCalculatorDataEntity.prevState){
                is ScientificCalculatorMathOperationState -> ScientificCalculatorSecondOperandReadState(returnData)
                is ScientificCalculatorSecondOperandInputState -> ScientificCalculatorSecondOperandReadState(returnData)
                is ScientificCalculatorSecondOperandReadState -> ScientificCalculatorSecondOperandReadState(returnData)
                is ScientificCalculatorSecondOperandPowerNumberInputState -> ScientificCalculatorSecondOperandReadState(returnData)
                else -> ScientificCalculatorFirstOperandReadState(returnData)
            }
        }
    }

    /**
     * Calculates natural logarithm of number, entered to mainString and changed current state to
     * ScientificCalculatorFirstOperandInputState if calculation is successful or ScientificCalculatorErrorState
     * if is not.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState if error occurred.
     */
    override fun calculateNaturalLogarithm(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(ln(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))),
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
        } catch (numberFormatException: NumberFormatException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates exponent raised to the power of entered number.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and calculation result in mainString field
     * if calculation completed successfully
     * ScientificCalculatorErrorState if calculation completed with error
     */
    override fun calculateExponent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(exp(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))),
                    historyString = "${scientificCalculatorDataEntity.historyString}powe(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
        } catch (numberFormatException: NumberFormatException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}powe(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}powe(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Rounds number, entered to the mainString of calculator data. Operation recorded to historyString
     * of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun integerOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    truncate(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}Int(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Discards whole part of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun fractionOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    calculatorStringToDouble(scientificCalculatorDataEntity.mainString) % 1),
                historyString = "${scientificCalculatorDataEntity.historyString}frac(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Calculates hyperbolic sinus of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState with corresponding error code.
     */
    override fun hyperbolicSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        calculatorStringToDouble(scientificCalculatorDataEntity.mainString).calcSinh()
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}sinh(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sinh(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sinh(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates hyperbolic arcsinus of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data.
     */
    override fun hyperbolicArcSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    asinh(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}asinh(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Calculate sinus of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Changes calculator state.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data.
     */
    override fun sinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        val result = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> sin(radiansFromDegrees(calculatorStringToDouble(scientificCalculatorDataEntity.mainString)))
            RADIANS_ANGLE_CODE -> sin(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
            GRADS_ANGLE_CODE -> sin(radiansFromGrads(calculatorStringToDouble(scientificCalculatorDataEntity.mainString)))
            else -> sin(radiansFromDegrees(calculatorStringToDouble(scientificCalculatorDataEntity.mainString)))
        }
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "sind"
            RADIANS_ANGLE_CODE -> "sinr"
            GRADS_ANGLE_CODE -> "sing"
            else -> "sind"
        }

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(result),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Calculate arcsinus of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Changes calculator state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun arcSinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        val enteredNumber = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
        val result = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> degreesFromRadians(asin(enteredNumber))
            RADIANS_ANGLE_CODE -> asin(enteredNumber)
            GRADS_ANGLE_CODE -> gradsFromRadians(asin(enteredNumber))
            else -> degreesFromRadians(asin(enteredNumber))
        }
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "asind"
            RADIANS_ANGLE_CODE -> "asinr"
            GRADS_ANGLE_CODE -> "asing"
            else -> "asind"
        }

        return if (abs(enteredNumber) > 1) ScientificCalculatorErrorState(
            scientificCalculatorDataEntity.copy(
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                errorCode = INVALID_INPUT_ERROR_CODE
            )
        ) else ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(result),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Calculates number, entered to mainString of calculator data, to the power of 2. Operation
     * recorded to historyString of calculator data. Changes calculator state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun squareNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        calculatorStringToDouble(scientificCalculatorDataEntity.mainString).powerTo(2.0)
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}sqr(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sqr(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sqr(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates factorial of number, entered to mainString of calculator data. Operation
     * recorded to historyString of calculator data. Changes calculator state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun factorial(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        calculatorStringToDouble(scientificCalculatorDataEntity.mainString).factorial()
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}fact(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}fact(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}fact(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Converts number, entered to mainString field of calculator data, from degrees unit
     * with decimal fractional part to degrees unit with fractional part presented in minutes.
     * Operation recorded to historyString of calculator data. Changes calculator state
     * to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun decimalToMinutes(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val fraction = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)%1
        val integer = truncate(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
        val convertedFraction = (fraction*60)/100
        val convertedValue = integer + convertedFraction

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(convertedValue),
                historyString = "${scientificCalculatorDataEntity.historyString}dms(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Converts number, entered to mainString field of calculator data, from degrees
     * with fractional part presented in minutes to degrees with decimal fractional part.
     * Operation recorded to historyString of calculator data. Changes calculator state
     * to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun minutesToDecimal(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val fraction = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)%1
        val integer = truncate(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
        val convertedFraction = (fraction*100)/60
        val convertedValue = integer + convertedFraction

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(convertedValue),
                historyString = "${scientificCalculatorDataEntity.historyString}deg(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Calculates hyperbolic cosine of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState with corresponding error code.
     */
    override fun hyperbolicCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        calculatorStringToDouble(scientificCalculatorDataEntity.mainString).calcCosh()
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}cosh(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}cosh(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}cosh(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates hyperbolic arccosine of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState with corresponding error code if error occurred.
     */
    override fun hyperbolicArcCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val number = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
        return if (number < 1)
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        else ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    acosh(number)
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Calculate cosine of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Changes calculator state.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data.
     */
    override fun cosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        val result = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> cos(radiansFromDegrees(calculatorStringToDouble(scientificCalculatorDataEntity.mainString)))
            RADIANS_ANGLE_CODE -> cos(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
            GRADS_ANGLE_CODE -> cos(radiansFromGrads(calculatorStringToDouble(scientificCalculatorDataEntity.mainString)))
            else -> cos(radiansFromDegrees(calculatorStringToDouble(scientificCalculatorDataEntity.mainString)))
        }
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "cosd"
            RADIANS_ANGLE_CODE -> "cosr"
            GRADS_ANGLE_CODE -> "cosg"
            else -> "cosd"
        }

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(result),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Calculate arccosine of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Changes calculator state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun arcCosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        val enteredNumber = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
        val result = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> degreesFromRadians(acos(enteredNumber))
            RADIANS_ANGLE_CODE -> acos(enteredNumber)
            GRADS_ANGLE_CODE -> gradsFromRadians(acos(enteredNumber))
            else -> degreesFromRadians(acos(enteredNumber))
        }
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "acosd"
            RADIANS_ANGLE_CODE -> "acosr"
            GRADS_ANGLE_CODE -> "acosg"
            else -> "acosd"
        }

        return if (abs(enteredNumber) > 1) ScientificCalculatorErrorState(
            scientificCalculatorDataEntity.copy(
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                errorCode = INVALID_INPUT_ERROR_CODE
            )
        ) else ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(result),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Changes current state to ScientificCalculatorMathOperationState,
     * input number and operation sign writes to history string of calculator data,
     * same as operation type.
     * @param1 scientificCalculatorDataEntity - contains current calculator data,
     * @param2 scientificOperationType - type of math operation
     * @param3 operationString - operation sign, need to be added in history string
     * @return GeneralCalculatorPrimitiveMathOperationState with changed historyString and operationType fields of calculator data
     */
    override fun mathOperation(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        scientificOperationType: ScientificOperationType,
        operationString: String
    ): CalculatorState {
        val historyString = "${scientificCalculatorDataEntity.historyString}" +
                "${scientificCalculatorDataEntity.mainString.commaTruncate()}" +
                "$HISTORY_STRING_SPACE_LETTER" +
                "$operationString"

        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                historyString = historyString,
                scientificOperationType = scientificOperationType,
                operand = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Placed PI number to mainString field of calculator data.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun piNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(PI)
            )
        )
    }

    /**
     * Placed double PI number to mainString field of calculator data.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun doublePiNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(2*PI)
            )
        )
    }

    /**
     * Calculates hyperbolic tangent of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully.
     */
    override fun hyperbolicTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    tanh(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}tanh(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Calculates hyperbolic arctangent of entered number to the mainString of calculator data. Operation recorded to
     * historyString of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState, depending on entered to mainString number.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data if modulus of a number
     * of entered to mainString number is less than 1
     * ScientificCalculatorErrorState with corresponding error code otherwise.
     */
    override fun hyperbolicArcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return if (abs(calculatorStringToDouble(scientificCalculatorDataEntity.mainString)) < 1)
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        atanh(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
        else ScientificCalculatorErrorState(
            ScientificCalculatorDataEntity(
                historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            )
        )
    }

    /**
     * Calculate tangent of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Changes calculator state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState, depending on entered number.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if
     * entered number is not multiple to PI/2 or to 3*PI/2
     * ScientificCalculatorErrorState with corresponding error code in calculator data if otherwise.
     */
    override fun tangent(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        val angleInRadians = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> radiansFromDegrees(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
            RADIANS_ANGLE_CODE -> calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
            GRADS_ANGLE_CODE -> radiansFromGrads(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
            else -> radiansFromDegrees(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
        }
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "tand"
            RADIANS_ANGLE_CODE -> "tanr"
            GRADS_ANGLE_CODE -> "tang"
            else -> "tand"
        }

        return if (((angleInRadians/PI)*2.0)%2.0 != 0.0 && ((angleInRadians/PI)*2.0)%1.0 == 0.0)
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}" +
                            "$operationString(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        else ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(tan(angleInRadians)),
                historyString = "${scientificCalculatorDataEntity.historyString}" +
                        "$operationString(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Calculates arctangent of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Changes calculator state to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun arcTangent(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        val enteredNumber = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
        val result = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> degreesFromRadians(atan(enteredNumber))
            RADIANS_ANGLE_CODE -> atan(enteredNumber)
            GRADS_ANGLE_CODE -> gradsFromRadians(atan(enteredNumber))
            else -> degreesFromRadians(atan(enteredNumber))
        }
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "atand"
            RADIANS_ANGLE_CODE -> "atanr"
            GRADS_ANGLE_CODE -> "atang"
            else -> "atand"
        }

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(result),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Calculates number, entered to mainString of calculator data, to the power of 3. Operation
     * recorded to historyString of calculator data. Changes calculator state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun cubeNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        calculatorStringToDouble(scientificCalculatorDataEntity.mainString).powerTo(3.0)),
                    historyString = "${scientificCalculatorDataEntity.historyString}cube(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}cube(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}cube(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates cube root of entered to mainString number and write result number back to mainString.
     * Completed operation writes to historyString, current state changed.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun cubeRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    cbrt(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}cuberoot(" +
                        "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
            )
        )
    }

    /**
     * Changes the number display format, entered to mainString field of calculator data,
     * from conventional to scientific notation and backward. State of calculator changed to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun fixedToExponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val isScientificNotation = !(scientificCalculatorDataEntity.isScientificNotation)

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    calculatorStringToDouble(scientificCalculatorDataEntity.mainString),
                    isScientificNotation
                ),
                isScientificNotation = isScientificNotation
            )
        )
    }

    /**
     * Changes current state to ScientificCalculatorFirstOperandPowerNumberInputState, in which number
     * in mainString field is presented in scientific notation format and exponent number entering
     * took place. Mantissa of this number is that in the mainString field of calculator data of
     * current state.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandPowerNumberInputState with updated calculator data
     */
    override fun exponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val mainString = if (scientificCalculatorDataEntity.mainString.contains(','))
            "${scientificCalculatorDataEntity.mainString}e+0"
        else "${scientificCalculatorDataEntity.mainString},e+0"

        return ScientificCalculatorFirstOperandPowerNumberInputState(
            scientificCalculatorDataEntity.copy(mainString = mainString)
        )
    }

    /**
     * Calculates logarithm base 10 of the number, entered to mainString field of calculator data.
     * Operation recorded to historyString field of calculator data. Changes current state to
     * ScientificCalculatorFirstOperandReadState or ScientificCalculatorErrorState, depending on
     * number in mainString field.
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if
     * number in mainString is more than zero
     * ScientificCalculatorErrorState with appropriate code in errorCode field if otherwise
     *
     */
    override fun logarithmBaseTen(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return if (calculatorStringToDouble(scientificCalculatorDataEntity.mainString) <= 0)
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}log(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            ) else
            ScientificCalculatorFirstOperandReadState(
                ScientificCalculatorDataEntity(
                    mainString = doubleToCalculatorString(
                        log10(calculatorStringToDouble(
                                scientificCalculatorDataEntity.mainString
                            )
                        )
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}log(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
    }

    /**
     * Calculates 10 to the power of number, entered to mainString field of calculator data. Operation
     * recorded to historyString field of calculator data. Changes calculator state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState with corresponding error code in calculator data if error occurred.
     */
    override fun tenPowerX(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        10.0.powerTo(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}10^(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}10^(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}10^(" +
                            "${scientificCalculatorDataEntity.mainString.commaTruncate()})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Clears all fields of calculator data (except memoryNumber) and reset current state to
     * ScientificCalculatorInitialState.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorInitialState with default calculator data except memoryNumber field that
     * saved old value.
     */
    override fun clearAll(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity(
                memoryNumber = scientificCalculatorDataEntity.memoryNumber
            )
        )
    }
}