package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.constants.ARC_COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.ARC_SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.ARC_TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.DEGREES_ANGLE_CODE
import ru.profitsw2000.data.constants.DEG_FUNCTION_CODE
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.DMS_FUNCTION_CODE
import ru.profitsw2000.data.constants.EXPONENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.FRACTIONAL_PART_FUNCTION_CODE
import ru.profitsw2000.data.constants.GRADS_ANGLE_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.HYPERBOLIC_ARC_COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_ARC_SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_ARC_TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.HYPERBOLIC_TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.INTEGRAL_PART_FUNCTION_CODE
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.LOGARITHM_BASE_10_FUNCTION_CODE
import ru.profitsw2000.data.constants.NATURAL_LOGARITHM_FUNCTION_CODE
import ru.profitsw2000.data.constants.OVERFLOW_ERROR_CODE
import ru.profitsw2000.data.constants.RADIANS_ANGLE_CODE
import ru.profitsw2000.data.constants.SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorBaseState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorInputState

class ScientificCalculatorFirstOperandPowerNumberInputState(
    override val scientificCalculatorDataEntity: ScientificCalculatorDataEntity
) : ScientificCalculatorInputState {

    override val scale: Int
        get() = SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        TODO("Not yet implemented")
    }

    /**
     * Copied function parameter, sets field memoryNumber to null, create instance of ScientificCalculatorFirstOperandInputState
     * with newly created calculator data as constructor and return it.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
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
     * to string value, which is recorded to mainString field of calculator data. Then created instance of
     * ScientificCalculatorFirstOperandReadState with newly created calculator data as constructor and
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
                )
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

    /**
     * Add to calculator memory (memoryNumber field of calculator data) number, placed in mainString field.
     * Number in mainString formatted according to isScientificNotation field value.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun addNumberToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val addedNumber = scientificCalculatorDataEntity.mainString
        val calculatorData = if (addedNumber == "0") {
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                )
            )
        } else {
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                memoryNumber = if (scientificCalculatorDataEntity.memoryNumber == null) addedNumber
                else scientificCalculatorDataEntity.memoryNumber.add(addedNumber)
            )
        }

        return ScientificCalculatorFirstOperandReadState(calculatorData)
    }

    /**
     * Subtract from calculator memory (memoryNumber field of calculator data) number, placed in mainString field.
     * Number in mainString formatted according to isScientificNotation field value.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun subtractNumberFromMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val subtractedNumber = scientificCalculatorDataEntity.mainString
        val calculatorData = if (subtractedNumber == "0") {
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                )
            )
        } else {
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                memoryNumber = if (scientificCalculatorDataEntity.memoryNumber == null) "0".subtract(
                    scientificCalculatorDataEntity.mainString
                )
                else scientificCalculatorDataEntity.memoryNumber.subtract(
                    scientificCalculatorDataEntity.mainString
                )
            )
        }

        return ScientificCalculatorFirstOperandReadState(calculatorData)
    }

    /**
     * Changes sign of exponent of entered number in mainString field of
     * calculator data
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandPowerNumberInputState with updated calculator data
     */
    override fun negateOperand(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandPowerNumberInputState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.negateExponent()
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
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.sqrt(scientificCalculatorDataEntity.isScientificNotation),
                    historyString = scientificCalculatorDataEntity.historyString +
                            "sqrt(${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = scientificCalculatorDataEntity.historyString +
                            "sqrt(${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = scientificCalculatorDataEntity.historyString +
                            "sqrt(${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Append digit, that is in second parameter of fun to exponent of mainString of calculator data if it
     * satisfy to a certain condition.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandPowerNumberInputState with updated calculator data
     */
    override fun inputDigit(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        digitToAppend: String
    ): CalculatorState {
        val lastThreeDigits = scientificCalculatorDataEntity.mainString.takeLast(3)
        return when {
            scientificCalculatorDataEntity.mainString.length > 4 || digitToAppend == "," -> this
            lastThreeDigits == "e+0" || lastThreeDigits == "e-0" -> ScientificCalculatorSecondOperandPowerNumberInputState(
                scientificCalculatorDataEntity.copy(
                    mainString = "${scientificCalculatorDataEntity.mainString.dropLast(1)}$digitToAppend"
                )
            )
            else -> ScientificCalculatorFirstOperandPowerNumberInputState(
                scientificCalculatorDataEntity.copy(
                    mainString = "${scientificCalculatorDataEntity.mainString}$digitToAppend"
                )
            )
        }
    }

    /**
     * Deletes last character in exponent number of mainString field. If last
     * character of exponent is zero, then changes state to ScientificCalculatorFirstOperandInputState
     * with mantissa in mainString field of calculator data.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandPowerNumberInputState or
     * ScientificCalculatorFirstOperandInputState with updated calculator data if exponent equal 0.
     */
    override fun clearDigit(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val lastThreeChars = scientificCalculatorDataEntity.mainString.takeLast(3)

        return when {
            lastThreeChars == "e+0" || lastThreeChars == "e-0" -> ScientificCalculatorFirstOperandInputState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.dropLast(3)
                )
            )
            lastThreeChars.contains("e+") || lastThreeChars.contains("e-") -> ScientificCalculatorFirstOperandPowerNumberInputState(
                scientificCalculatorDataEntity.copy(
                    mainString = "${scientificCalculatorDataEntity.mainString.dropLast(3)}e+0"
                )
            )
            else -> ScientificCalculatorFirstOperandPowerNumberInputState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.dropLast(1)
                )
            )
        }
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
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = scientificCalculatorDataEntity.historyString +
                        scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        ) +
                        "$HISTORY_STRING_SPACE_LETTER$operationString",
                scientificOperationType = scientificOperationType,
                operand = scientificCalculatorDataEntity.mainString.calcFormat(false)
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
                mainString = "1".divide(scientificCalculatorDataEntity.mainString,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = scientificCalculatorDataEntity.historyString +
                        "reciproc(${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
            ))
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                historyString = scientificCalculatorDataEntity.historyString +
                        "reciproc(${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})",
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            ))
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                historyString = scientificCalculatorDataEntity.historyString +
                        "reciproc(${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})",
                errorCode = UNKNOWN_ERROR_CODE
            ))
        }
    }

    /**
     * Do nothing
     */
    override fun calculateResult(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                )
            )
        )
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
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
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
        return if (scientificCalculatorDataEntity.prevState == null)
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                        scientificCalculatorDataEntity.isScientificNotation
                    )
                )
            )
        else {
            val returnData = scientificCalculatorDataEntity.prevState.scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                memoryNumber = scientificCalculatorDataEntity.memoryNumber,
                historyString = scientificCalculatorDataEntity.historyString +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})",
                isScientificNotation = scientificCalculatorDataEntity.isScientificNotation
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
     * ScientificCalculatorFirstOperandReadState if calculation is successful or ScientificCalculatorErrorState
     * if is not.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState if error occurred.
     */
    override fun calculateNaturalLogarithm(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        NATURAL_LOGARITHM_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
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
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        EXPONENT_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}powe(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}powe(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}powe(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Rounds number, entered to the mainString of calculator data. Operation recorded to historyString
     * of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun integerOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.numberPart(
                    INTEGRAL_PART_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}Int(" +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
            )
        )
    }

    /**
     * Discards whole part of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun fractionOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.numberPart(
                    FRACTIONAL_PART_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}frac(" +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
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
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        HYPERBOLIC_SINUS_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}sinh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sinh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sinh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
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
                mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                    HYPERBOLIC_ARC_SINUS_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}asinh(" +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
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
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "sind"
            RADIANS_ANGLE_CODE -> "sinr"
            GRADS_ANGLE_CODE -> "sing"
            else -> "sind"
        }

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.trigonometricFunction(
                    SINUS_FUNCTION_CODE,
                    angleUnitCode,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
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
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "asind"
            RADIANS_ANGLE_CODE -> "asinr"
            GRADS_ANGLE_CODE -> "asing"
            else -> "asind"
        }

        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString
                        .inverseTrigonometricFunction(
                            ARC_SINUS_FUNCTION_CODE,
                            angleUnitCode,
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        }
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
                    mainString = scientificCalculatorDataEntity.mainString.powerOfNumber(
                        "2",
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}sqr(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sqr(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sqr(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
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
                    mainString = scientificCalculatorDataEntity.mainString.factorial(
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}fact(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}fact(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = if (arithmeticException.message == "Overflow of calculated number.") OVERFLOW_ERROR_CODE
                    else INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}fact(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
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
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.decimalMinutes(
                    DMS_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}dms(" +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
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
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.decimalMinutes(
                    DEG_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}deg(" +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
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
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        HYPERBOLIC_COSINE_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}cosh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}cosh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}cosh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
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
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        HYPERBOLIC_ARC_COSINE_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
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
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "cosd"
            RADIANS_ANGLE_CODE -> "cosr"
            GRADS_ANGLE_CODE -> "cosg"
            else -> "cosd"
        }

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.trigonometricFunction(
                    COSINE_FUNCTION_CODE,
                    angleUnitCode,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
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
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "acosd"
            RADIANS_ANGLE_CODE -> "acosr"
            GRADS_ANGLE_CODE -> "acosg"
            else -> "acosd"
        }

        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString
                        .inverseTrigonometricFunction(
                            ARC_COSINE_FUNCTION_CODE,
                            angleUnitCode,
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    override fun mathOperation(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        scientificOperationType: ScientificOperationType,
        operationString: String
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    /**
     * Placed PI number to mainString field of calculator data.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun piNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = piNumber(
                    scientificCalculatorDataEntity.isScientificNotation
                )
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
                mainString = doublePiNumber(scientificCalculatorDataEntity.isScientificNotation)
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
                mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                    HYPERBOLIC_TANGENT_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}tanh(" +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
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
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        HYPERBOLIC_ARC_TANGENT_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
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
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "tand"
            RADIANS_ANGLE_CODE -> "tanr"
            GRADS_ANGLE_CODE -> "tang"
            else -> "tand"
        }

        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString
                        .trigonometricFunction(
                            TANGENT_FUNCTION_CODE,
                            angleUnitCode,
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = scientificCalculatorDataEntity.historyString +
                            "$operationString(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = scientificCalculatorDataEntity.historyString +
                            "$operationString(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception){
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = scientificCalculatorDataEntity.historyString +
                            "$operationString(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
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
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "atand"
            RADIANS_ANGLE_CODE -> "atanr"
            GRADS_ANGLE_CODE -> "atang"
            else -> "atand"
        }

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString
                    .inverseTrigonometricFunction(
                        ARC_TANGENT_FUNCTION_CODE,
                        angleUnitCode,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
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
                    mainString = scientificCalculatorDataEntity.mainString
                        .powerOfNumber(
                            "3",
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = "${scientificCalculatorDataEntity.historyString}cube(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}cube(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}cube(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
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
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString
                        .rootOfNumber(
                            "3",
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = "${scientificCalculatorDataEntity.historyString}cuberoot(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}cuberoot(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}cuberoot(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
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
                mainString = scientificCalculatorDataEntity.mainString.formatStringNumber(isScientificNotation),
                isScientificNotation = isScientificNotation
            )
        )
    }

    /**
     * Do nothing
     */
    override fun exponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return this
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
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        LOGARITHM_BASE_10_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}log(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}log(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        }
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
                    mainString = "10".powerOfNumber(
                        scientificCalculatorDataEntity.mainString,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}powten(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}powten(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}powten(" +
                            "${scientificCalculatorDataEntity.mainString.calcFormat(
                                scientificCalculatorDataEntity.isScientificNotation
                            )})",
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
                memoryNumber = scientificCalculatorDataEntity.memoryNumber,
            )
        )
    }
}