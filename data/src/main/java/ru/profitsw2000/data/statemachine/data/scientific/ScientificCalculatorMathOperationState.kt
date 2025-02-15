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
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorReadState

class ScientificCalculatorMathOperationState(
    override val scientificCalculatorDataEntity: ScientificCalculatorDataEntity
) : ScientificCalculatorReadState {

    override val scale: Int
        get() = SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        TODO("Not yet implemented")
    }

    /**
     * Set to null memoryNumber field of calculator data. State of calculator is not changed.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorMathOperationState with updated calculator data
     */
    override fun clearMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = null
            )
        )
    }

    /**
     * Copied number from memoryNumber field to mainString field of calculator data
     * if it is not null, otherwise set mainString field to "0". Changes current state.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun readMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val memoryNumber = scientificCalculatorDataEntity.memoryNumber

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = if (memoryNumber != null) memoryNumber
                else "0"
            )
        )
    }

    /**
     * Writes data from mainString field of calculator data to memoryNumber.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorMathOperationState with updated calculator data
     */
    override fun saveToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = scientificCalculatorDataEntity.mainString
            )
        )
    }

    /**
     * Add number from mainString field to number in memoryNumber field of calculator data.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorMathOperationState with updated calculator data
     */
    override fun addNumberToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val memoryNumber = scientificCalculatorDataEntity.memoryNumber
        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = if (memoryNumber != null) memoryNumber.add(scientificCalculatorDataEntity.mainString)
                else scientificCalculatorDataEntity.mainString
            )
        )
    }

    /**
     * Subtract number from memoryNumber field with number placed in mainString field of calculator data.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorMathOperationState with updated calculator data
     */
    override fun subtractNumberFromMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val memoryNumber = scientificCalculatorDataEntity.memoryNumber
        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = if (memoryNumber != null) memoryNumber.subtract(scientificCalculatorDataEntity.mainString)
                else "0".subtract(scientificCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Clears mainString field of calculator data (set to "0").
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorMathOperationState with updated calculator data
     */
    override fun clearEntered(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                mainString = "0"
            )
        )
    }

    /**
     * Reset calculator to initial state, clears all calculator data fields, except memoryNumber.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorInitialState with updated calculator data
     */
    override fun clearAll(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity(
                memoryNumber = scientificCalculatorDataEntity.memoryNumber
            )
        )
    }

    /**
     * Changes sign number in mainString field of
     * calculator data and writes operation to historyString field.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun negateOperand(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "negate"
        )

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.negate(),
                historyString = historyString
            )
        )
    }

    /**
     * Calculate square root of entered to mainString number and write result number back to mainString.
     * Completed operation writes to historyString, current state changed.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if
     * number in mainString is equal or more than zero
     * ScientificCalculatorErrorState with appropriate code in errorCode field
     */
    override fun calculateSquareRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "sqrt"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.sqrt(scientificCalculatorDataEntity.isScientificNotation),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Clears mainString field of calculator data and place there digitToAppend parameter of function.
     * Changes current state to ScientificCalculatorSecondOperandInputState
     * @param scientificCalculatorDataEntity - contains calculator data
     * @param digitToAppend - string to insert to mainString field
     * @return ScientificCalculatorSecondOperandInputState with updated calculator data
     */
    override fun inputDigit(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        digitToAppend: String
    ): CalculatorState {
        return ScientificCalculatorSecondOperandInputState(
            scientificCalculatorDataEntity.copy(
                mainString = if (digitToAppend != ",") digitToAppend
                else "0,"
            )
        )
    }

    /**
     * Sets scientificOperationType field of calculator data according
     * to value in scientificOperationType parameter of this function. Also
     * replaced last operation string in historyString field with operationString
     * parameter.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorMathOperationState with updated calculator data
     */
    override fun primitiveMathOperation(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        scientificOperationType: ScientificOperationType,
        operationString: String
    ): CalculatorState {
        val historyString = scientificCalculatorDataEntity.historyString
            .replaceAfterLast(HISTORY_STRING_SPACE_LETTER, operationString)

        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                scientificOperationType = scientificOperationType,
                historyString = historyString
            )
        )
    }

    /**
     * Calculates result of one divided on number in mainString field and
     * writes it back to mainString. Designator of committed operation appended to history string.
     * Changes current state depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if
     * divider is not equal to zero
     * ScientificCalculatorErrorState with updated calculator data if divider equal 0
     */
    override fun reciprocOperation(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "reciproc"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(scientificCalculatorDataEntity.copy(
                mainString = "1".divide(scientificCalculatorDataEntity.mainString,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = historyString
            ))
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                historyString = historyString,
                errorCode = DIVIDE_ON_ZERO_ERROR_CODE
            ))
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                historyString = historyString,
                errorCode = UNKNOWN_ERROR_CODE
            ))
        }
    }

    /**
     * Calculates result of math operation between numbers located in operand and
     * mainString field of calculator data. Type of math operation placed
     * in operationType field of calculator data. Result
     * of calculation records to mainString field, prevState field reset to default, which
     * is null, historyString is set to empty string. Function changes current state
     * to ScientificCalculatorOperationResultState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorOperationResultState with updated calculator data
     */
    override fun calculateResult(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorOperationResultState(
                scientificCalculatorDataEntity.copy(
                    mainString = getAllStatesCalculationResult(this),
                    historyString = "",
                    prevState = null
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                historyString = scientificCalculatorDataEntity.historyString,
                errorCode = INVALID_INPUT_ERROR_CODE
            ))
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                historyString = scientificCalculatorDataEntity.historyString,
                errorCode = UNKNOWN_ERROR_CODE
            ))
        }
    }


    /**
     * Calculates natural logarithm of number placed in mainString field of calculator data and
     * place result back to the same field. History of operation appends to historyString field.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState if error occurred.
     */
    override fun calculateNaturalLogarithm(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "ln"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        NATURAL_LOGARITHM_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates exponent raised to the power of number in mainString field.
     * History of operation appends to historyString field.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorSecondOperandReadState with operation saved in historyString and calculation result in mainString field
     * if calculation completed successfully
     * ScientificCalculatorErrorState if calculation completed with error
     */
    override fun calculateExponent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "powe"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        EXPONENT_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Changes current state to ScientificCalculatorFirstOperandReadState while current
     * state recorded to prevState field of new state. mainString field of calculator data set
     * to "0", '(' is appended to historyString field.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun openBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = "${scientificCalculatorDataEntity.historyString}${HISTORY_STRING_SPACE_LETTER}("

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = "0",
                historyString = historyString,
                prevState = this
            )
        )
    }

    /**
     * If bracket was not opened before, then do nothing. Otherwise changes state, depending on state
     * that was before opening bracket(contains in prevState field of calculator data). Execute
     * math operation between number in operand field and mainString field of calculator data.
     * History of operation recorded in historyString field. prevState field of calculator data
     * of state preceded to opening bracket recorded to calculator data of newly created state.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return this - if bracket was not opened,
     * otherwise - ScientificCalculatorFirstOperandReadState or
     * ScientificCalculatorSecondOperandReadState
     * (depending on state preceded bracket opening)with updated calculator data
     */
    override fun closeBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return if (scientificCalculatorDataEntity.prevState == null) this
        else {
            val historyString = scientificCalculatorDataEntity.historyString +
                    HISTORY_STRING_SPACE_LETTER +
                    "${scientificCalculatorDataEntity.operand})"
            val result = when(scientificCalculatorDataEntity.scientificOperationType) {
                ScientificOperationType.PLUS -> scientificCalculatorDataEntity.mainString.add(scientificCalculatorDataEntity.operand,scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.MINUS -> scientificCalculatorDataEntity.mainString.subtract(scientificCalculatorDataEntity.operand,scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.MULTIPLY -> scientificCalculatorDataEntity.mainString.multiply(scientificCalculatorDataEntity.operand,scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.DIVIDE -> scientificCalculatorDataEntity.mainString.divide(scientificCalculatorDataEntity.operand,scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.MODULUS -> scientificCalculatorDataEntity.mainString.modulus(scientificCalculatorDataEntity.operand,scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.POWER_OF -> scientificCalculatorDataEntity.mainString.powerOfNumber(scientificCalculatorDataEntity.operand,scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.ROOT_OF -> scientificCalculatorDataEntity.mainString.rootOfNumber(scientificCalculatorDataEntity.operand,scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.NO_OPERATION -> scientificCalculatorDataEntity.mainString
            }

            val returnData = scientificCalculatorDataEntity.prevState.scientificCalculatorDataEntity.copy(
                mainString = result,
                memoryNumber = scientificCalculatorDataEntity.memoryNumber,
                historyString = historyString,
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
     * Rounds number, entered to the mainString of calculator data. Operation recorded to historyString
     * of calculator data. Changed current state to ScientificCalculatorSecondOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun integerOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "Int"
        )

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.numberPart(
                    INTEGRAL_PART_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = historyString
            )
        )
    }

    /**
     * Discards whole part of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Changed current state to ScientificCalculatorSecondOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun fractionOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "frac"
        )

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.numberPart(
                    FRACTIONAL_PART_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = historyString
            )
        )
    }

    /**
     * Calculates hyperbolic sinus of number in the mainString field of calculator data.
     * Operation recorded to historyString of calculator data.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
     * successfully or
     * ScientificCalculatorErrorState with corresponding error code.
     */
    override fun hyperbolicSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "sinh"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        HYPERBOLIC_SINUS_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        } catch (outOfMemoryError: OutOfMemoryError) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates hyperbolic arcsinus of number in the mainString field of calculator data.
     * Operation recorded to historyString of calculator data.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data.
     */
    override fun hyperbolicArcSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "asinh"
        )

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                    HYPERBOLIC_ARC_SINUS_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = historyString
            )
        )
    }

    /**
     * Calculate sinus of number in the mainString field. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data.
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
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.trigonometricFunction(
                    SINUS_FUNCTION_CODE,
                    angleUnitCode,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = historyString
            )
        )
    }

    /**
     * Calculate arcsinus of number in mainString field. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
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
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString
                        .inverseTrigonometricFunction(
                            ARC_SINUS_FUNCTION_CODE,
                            angleUnitCode,
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        }
    }

    /**
     * Raised number in mainString field of calculator data, to the power of 2 and place
     * result to mainString field. Operation
     * recorded to historyString of calculator data. Return ScientificCalculatorSecondOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun squareNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "sqr"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.powerOfNumber(
                        "2",
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates factorial of number in mainString field of calculator data. Operation
     * recorded to historyString of calculator data. Return ScientificCalculatorSecondOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun factorial(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "fact"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.factorial(
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = if (arithmeticException.message == "Overflow of calculated number.") OVERFLOW_ERROR_CODE
                    else INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Converts number in mainString field of calculator data, from degrees unit
     * with decimal fractional part to degrees unit with fractional part presented in minutes.
     * Operation recorded to historyString of calculator data.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun decimalToMinutes(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "dms"
        )

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.decimalMinutes(
                    DMS_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = historyString
            )
        )
    }

    /**
     * Converts number in mainString field of calculator data, from degrees
     * with fractional part presented in minutes to degrees with decimal fractional part.
     * Operation recorded to historyString of calculator data.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun minutesToDecimal(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "deg"
        )

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.decimalMinutes(
                    DEG_FUNCTION_CODE,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = historyString
            )
        )
    }

    /**
     * Calculates hyperbolic cosine of number in the mainString field of calculator data. Operation recorded to
     * historyString of calculator data. Returns ScientificCalculatorSecondOperandReadState or
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState with corresponding error code.
     */
    override fun hyperbolicCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "cosh"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        HYPERBOLIC_COSINE_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        } catch (outOfMemoryError: OutOfMemoryError) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates hyperbolic arccosine of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Return ScientificCalculatorSecondOperandReadState
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState with corresponding error code if error occurred.
     */
    override fun hyperbolicArcCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "acosh"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        HYPERBOLIC_ARC_COSINE_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculate cosine of number in mainString field. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data.
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
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.trigonometricFunction(
                    COSINE_FUNCTION_CODE,
                    angleUnitCode,
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = historyString
            )
        )
    }

    /**
     * Calculate arccosine of number in mainString field. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Return ScientificCalculatorSecondOperandReadState
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
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
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString
                        .inverseTrigonometricFunction(
                            ARC_COSINE_FUNCTION_CODE,
                            angleUnitCode,
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
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
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun piNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorSecondOperandReadState(
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
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun doublePiNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doublePiNumber(scientificCalculatorDataEntity.isScientificNotation)
            )
        )
    }

    /**
     * Calculates hyperbolic tangent of number in mainString field of calculator data. Operation recorded to
     * historyString of calculator data. Return ScientificCalculatorSecondOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
     * successfully.
     */
    override fun hyperbolicTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "tanh"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        HYPERBOLIC_TANGENT_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (outOfMemoryError: OutOfMemoryError) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates hyperbolic arctangent of number in the mainString field of calculator data. Operation recorded to
     * historyString of calculator data. Return ScientificCalculatorSecondOperandReadState
     * or ScientificCalculatorErrorState, depending on entered to mainString number.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if modulus of a number
     * of entered to mainString number is less than 1
     * ScientificCalculatorErrorState with corresponding error code otherwise.
     */
    override fun hyperbolicArcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "atanh"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        HYPERBOLIC_ARC_TANGENT_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculate tangent of number in mainString field. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Return ScientificCalculatorSecondOperandReadState
     * or ScientificCalculatorErrorState, depending on entered number.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if
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
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString
                        .trigonometricFunction(
                            TANGENT_FUNCTION_CODE,
                            angleUnitCode,
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception){
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates arctangent of number in mainString field. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
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
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString
                    .inverseTrigonometricFunction(
                        ARC_TANGENT_FUNCTION_CODE,
                        angleUnitCode,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                historyString = historyString
            )
        )
    }

    /**
     * Calculates number in mainString field of calculator data, to the power of 3. Operation
     * recorded to historyString of calculator data. Return ScientificCalculatorSecondOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun cubeNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "cube"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString
                        .powerOfNumber(
                            "3",
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates cube root of number in mainString field and write result number back to mainString.
     * Completed operation writes to historyString, current state not changed.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun cubeRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "cuberoot"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString
                        .rootOfNumber(
                            "3",
                            scientificCalculatorDataEntity.isScientificNotation
                        ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Changes the number display format of number in mainString field of calculator data
     * from conventional to scientific notation and backward. Return ScientificCalculatorSecondOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data
     */
    override fun fixedToExponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val isScientificNotation = !(scientificCalculatorDataEntity.isScientificNotation)

        return ScientificCalculatorSecondOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.formatStringNumber(isScientificNotation),
                isScientificNotation = isScientificNotation
            )
        )
    }

    /**
     * Do nothing
     **/
    override fun exponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return this
    }

    /**
     * Calculates logarithm base 10 of the number in mainString field of calculator data.
     * Operation recorded to historyString field of calculator data. Return
     * ScientificCalculatorSecondOperandReadState or ScientificCalculatorErrorState, depending on
     * number in mainString field.
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if
     * number in mainString is more than zero
     * ScientificCalculatorErrorState with appropriate code in errorCode field if otherwise
     */
    override fun logarithmBaseTen(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "log"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        LOGARITHM_BASE_10_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates 10 to the power of number in mainString field of calculator data. Operation
     * recorded to historyString field of calculator data. Changes calculator state to ScientificCalculatorSecondOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorSecondOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState with corresponding error code in calculator data if error occurred.
     */
    override fun tenPowerX(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = appendOperationString(
            scientificCalculatorDataEntity,
            "powten"
        )

        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = "10".powerOfNumber(
                        scientificCalculatorDataEntity.mainString,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = historyString
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = historyString,
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates result of consecutive math operations with numbers, placed in all states variables and returns it.
     * @param scientificCalculatorBaseState - calculator state, that contains numbers and operation types
     * @return String with result number
     */
    fun getAllStatesCalculationResult(scientificCalculatorBaseState: ScientificCalculatorBaseState): String {
        var prevState: ScientificCalculatorBaseState? = scientificCalculatorBaseState
        var currentOperand = scientificCalculatorBaseState.scientificCalculatorDataEntity.mainString
        val isScientificNotation = scientificCalculatorBaseState.scientificCalculatorDataEntity.isScientificNotation

        while (prevState !=null) {
            val prevOperand = prevState.scientificCalculatorDataEntity.operand
            currentOperand = when(prevState.scientificCalculatorDataEntity.scientificOperationType) {
                ScientificOperationType.PLUS -> prevOperand.add(currentOperand, isScientificNotation)
                ScientificOperationType.MINUS -> prevOperand.subtract(currentOperand, isScientificNotation)
                ScientificOperationType.MULTIPLY -> prevOperand.multiply(currentOperand, isScientificNotation)
                ScientificOperationType.DIVIDE -> prevOperand.divide(currentOperand, isScientificNotation)
                ScientificOperationType.MODULUS -> prevOperand.modulus(currentOperand, isScientificNotation)
                ScientificOperationType.ROOT_OF -> prevOperand.rootOfNumber(currentOperand, isScientificNotation)
                ScientificOperationType.POWER_OF -> prevOperand.powerOfNumber(currentOperand, isScientificNotation)
                ScientificOperationType.NO_OPERATION -> currentOperand
            }
            prevState = prevState.scientificCalculatorDataEntity.prevState
        }
        return currentOperand
    }

    /**
     * Appends designator of operation with number in mainString as parameter of operation
     * to historyString field of calculator data.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @param operationString - string with designator of commited math operation
     */
    fun appendOperationString(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        operationString: String
    ): String {
        return scientificCalculatorDataEntity.historyString +
                HISTORY_STRING_SPACE_LETTER +
                "$operationString(" +
                "${scientificCalculatorDataEntity.mainString})"
    }
}