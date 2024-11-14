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
import ru.profitsw2000.utils.calcSinh
import ru.profitsw2000.utils.commaTruncate
import ru.profitsw2000.utils.factorial
import ru.profitsw2000.utils.powerTo
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.asinh
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.sin
import kotlin.math.sqrt
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