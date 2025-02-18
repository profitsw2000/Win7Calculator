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

class ScientificCalculatorFirstOperandReadState(
    override val scientificCalculatorDataEntity: ScientificCalculatorDataEntity
) : ScientificCalculatorReadState{

    override val scale: Int
        get() = SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(
                scientificCalculatorDataEntity,
                ScientificOperationType.PLUS,
                "+"
            )
            CalculatorAction.AddToMemory -> addNumberToMemory(scientificCalculatorDataEntity)
            is CalculatorAction.ArcCosine -> arcCosine(
                scientificCalculatorDataEntity,
                action.angleUnitCode
            )
            is CalculatorAction.ArcSinus -> arcSinus(
                scientificCalculatorDataEntity,
                action.angleUnitCode
            )
            is CalculatorAction.ArcTangent -> arcTangent(
                scientificCalculatorDataEntity,
                action.angleUnitCode
            )
            CalculatorAction.Backspace -> this
            CalculatorAction.Clear -> clearAll(scientificCalculatorDataEntity)
            CalculatorAction.ClearEntered -> clearEntered(scientificCalculatorDataEntity)
            CalculatorAction.ClearMemory -> clearMemory(scientificCalculatorDataEntity)
            is CalculatorAction.Cosine -> cosine(
                scientificCalculatorDataEntity,
                action.angleUnitCode
            )
            CalculatorAction.DecimalDegrees -> decimalToMinutes(scientificCalculatorDataEntity)
            is CalculatorAction.Digit -> inputDigit(scientificCalculatorDataEntity, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(
                scientificCalculatorDataEntity,
                ScientificOperationType.DIVIDE,
                "/"
            )
            CalculatorAction.Dms -> minutesToDecimal(scientificCalculatorDataEntity)
            CalculatorAction.DoublePi -> doublePiNumber(scientificCalculatorDataEntity)
            CalculatorAction.Equal -> calculateResult(scientificCalculatorDataEntity)
            CalculatorAction.ExponentOfX -> calculateExponent(scientificCalculatorDataEntity)
            CalculatorAction.ExponentialForm -> exponentialFormat(scientificCalculatorDataEntity)
            CalculatorAction.Factorial -> factorial(scientificCalculatorDataEntity)
            CalculatorAction.FixedToExponent -> fixedToExponentialFormat(
                scientificCalculatorDataEntity
            )
            CalculatorAction.Fraction -> fractionOfNumber(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicArcCosine -> hyperbolicArcCosine(
                scientificCalculatorDataEntity
            )
            CalculatorAction.HyperbolicArcSinus -> hyperbolicArcSinus(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicArcTangent -> hyperbolicArcTangent(
                scientificCalculatorDataEntity
            )
            CalculatorAction.HyperbolicCosine -> hyperbolicCosine(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicSinus -> hyperbolicSinus(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicTangent -> hyperbolicTangent(scientificCalculatorDataEntity)
            CalculatorAction.Integer -> integerOfNumber(scientificCalculatorDataEntity)
            CalculatorAction.Inverse -> this
            CalculatorAction.LeftBracket -> openBracket(scientificCalculatorDataEntity)
            CalculatorAction.Logarithm -> logarithmBaseTen(scientificCalculatorDataEntity)
            CalculatorAction.Modulus -> primitiveMathOperation(
                scientificCalculatorDataEntity,
                ScientificOperationType.MODULUS,
                "mod"
            )
            CalculatorAction.Multiply -> primitiveMathOperation(
                scientificCalculatorDataEntity,
                ScientificOperationType.MULTIPLY,
                "*"
            )
            CalculatorAction.NaturalLogarithm -> calculateNaturalLogarithm(
                scientificCalculatorDataEntity
            )
            CalculatorAction.Percentage -> this
            CalculatorAction.Pi -> piNumber(scientificCalculatorDataEntity)
            CalculatorAction.PlusMinus -> negateOperand(scientificCalculatorDataEntity)
            CalculatorAction.ReadMemory -> readMemory(scientificCalculatorDataEntity)
            CalculatorAction.Recipoc -> reciprocOperation(scientificCalculatorDataEntity)
            CalculatorAction.RightBracket -> closeBracket(scientificCalculatorDataEntity)
            CalculatorAction.SaveToMemory -> saveToMemory(scientificCalculatorDataEntity)
            is CalculatorAction.Sinus -> sinus(scientificCalculatorDataEntity, action.angleUnitCode)
            CalculatorAction.SquareRoot -> calculateSquareRoot(scientificCalculatorDataEntity)
            CalculatorAction.SquaredX -> squareNumber(scientificCalculatorDataEntity)
            CalculatorAction.Subtract -> subtractNumberFromMemory(scientificCalculatorDataEntity)
            CalculatorAction.SubtractFromMemory -> subtractNumberFromMemory(
                scientificCalculatorDataEntity
            )
            is CalculatorAction.Tangent -> tangent(
                scientificCalculatorDataEntity,
                action.angleUnitCode
            )
            CalculatorAction.TenToPowerOfX -> tenPowerX(scientificCalculatorDataEntity)
            CalculatorAction.ThirdRootOfX -> cubeRoot(scientificCalculatorDataEntity)
            CalculatorAction.XPowerThree -> cubeNumber(scientificCalculatorDataEntity)
            CalculatorAction.XPowerY -> primitiveMathOperation(
                scientificCalculatorDataEntity,
                ScientificOperationType.POWER_OF,
                "^"
            )
            CalculatorAction.YRootOfX -> primitiveMathOperation(
                scientificCalculatorDataEntity,
                ScientificOperationType.ROOT_OF,
                "yroot"
            )
        }
    }

    override fun clearEntered(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandInputState(
            scientificCalculatorDataEntity.copy(
                mainString = "0",
                historyString = getHistoryStringWithRemovedLastMathFunctionHistory(
                    scientificCalculatorDataEntity.historyString
                )
            )
        )
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
                historyString = getHistoryStringWithRemovedLastMathFunctionHistory(
                    scientificCalculatorDataEntity.historyString
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

    /**
     * Changes sign number in mainString field of
     * calculator data and writes operation to historyString field.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun negateOperand(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "negate"
        )

        return ScientificCalculatorFirstOperandReadState(
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
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if
     * number in mainString is equal or more than zero
     * ScientificCalculatorErrorState with appropriate code in errorCode field
     */
    override fun calculateSquareRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "sqrt"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Clears mainString field of calculator data and place there digitToAppend parameter of function
     * Also clears all historyString field if prevState field is null or clears string after open bracket
     * sign if not null. Changes current state to ScientificCalculatorFirstOperandInputState
     * @param scientificCalculatorDataEntity - contains calculator data
     * @param digitToAppend - string to insert to mainString field
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun inputDigit(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        digitToAppend: String
    ): CalculatorState {
        return ScientificCalculatorFirstOperandInputState(
            scientificCalculatorDataEntity.copy(
                mainString = if (digitToAppend != ",") digitToAppend
                else "0,",
                historyString = getHistoryStringWithRemovedLastMathFunctionHistory(
                    scientificCalculatorDataEntity.historyString
                )
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
        val historyString = if (scientificCalculatorDataEntity.historyString.substringAfterLast("(", "").isEmpty())
            scientificCalculatorDataEntity.mainString.calcFormat(
                scientificCalculatorDataEntity.isScientificNotation
            )
        else scientificCalculatorDataEntity.historyString

        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = historyString + HISTORY_STRING_SPACE_LETTER + operationString,
                scientificOperationType = scientificOperationType,
                operand = scientificCalculatorDataEntity.mainString.calcFormat(false)
            )
        )
    }

    /**
     * Calculates result of one divided on number in mainString field and
     * writes it back to mainString. Designator of committed operation appended to history string.
     * Changes current state depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data if
     * divider is not equal to zero
     * ScientificCalculatorErrorState with updated calculator data if divider equal 0
     */
    override fun reciprocOperation(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "reciproc"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(scientificCalculatorDataEntity.copy(
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
     * Calculate number by commiting math operations in all states begins from last. Result
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
     * Changes current state to ScientificCalculatorFirstOperandReadState while current
     * state recorded to prevState field of new state. Calculator data of new state remains
     * the same, except historyString field to which character '(' is appended.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun openBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithRemovedLastMathFunctionHistory(
            scientificCalculatorDataEntity.historyString
        )

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = "$historyString(",
                memoryNumber = scientificCalculatorDataEntity.memoryNumber,
                isScientificNotation = scientificCalculatorDataEntity.isScientificNotation,
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
            val historyString = if (scientificCalculatorDataEntity.historyString.substringAfterLast("(", "").isEmpty())
                scientificCalculatorDataEntity.historyString +
                        "${scientificCalculatorDataEntity.mainString.calcFormat(
                            scientificCalculatorDataEntity.isScientificNotation
                        )})"
            else "${scientificCalculatorDataEntity.historyString})"
            val returnData = scientificCalculatorDataEntity.prevState.scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
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
     * Calculates natural logarithm of number, entered to mainString and remain current state
     * the same if calculation is successful or ScientificCalculatorErrorState
     * if it is not.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState if error occurred.
     */
    override fun calculateNaturalLogarithm(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "ln"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculates exponent raised to the power of entered number.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and calculation result in mainString field
     * if calculation completed successfully
     * ScientificCalculatorErrorState if calculation completed with error
     */
    override fun calculateExponent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "powe"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Rounds number, entered to the mainString of calculator data. Operation recorded to historyString
     * of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data
     */
    override fun integerOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "Int"
        )

        return ScientificCalculatorFirstOperandReadState(
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
     * historyString of calculator data. Changed current state to ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun fractionOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "frac"
        )

        return ScientificCalculatorFirstOperandReadState(
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
     * Calculates hyperbolic sinus of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState with corresponding error code.
     */
    override fun hyperbolicSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "sinh"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculates hyperbolic arcsinus of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data.
     */
    override fun hyperbolicArcSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "asinh"
        )

        return ScientificCalculatorFirstOperandReadState(
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
     * Calculate sinus of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads.
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
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return ScientificCalculatorFirstOperandReadState(
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
     * Calculate arcsinus of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads.
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
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculates number, entered to mainString of calculator data, to the power of 2. Operation
     * recorded to historyString of calculator data. Return ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun squareNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "sqr"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculates factorial of number, entered to mainString of calculator data. Operation
     * recorded to historyString of calculator data. Return same state
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun factorial(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "fact"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Converts number, entered to mainString field of calculator data, from degrees unit
     * with decimal fractional part to degrees unit with fractional part presented in minutes.
     * Operation recorded to historyString of calculator data. Returns same state.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun decimalToMinutes(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "dms"
        )

        return ScientificCalculatorFirstOperandReadState(
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
     * Converts number, entered to mainString field of calculator data, from degrees
     * with fractional part presented in minutes to degrees with decimal fractional part.
     * Operation recorded to historyString of calculator data. Return same state.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun minutesToDecimal(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "deg"
        )

        return ScientificCalculatorFirstOperandReadState(
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
     * Calculates hyperbolic cosine of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Returns same state or
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState with corresponding error code.
     */
    override fun hyperbolicCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "cosh"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * historyString of calculator data. Return ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState, depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data if operation completed
     * successfully
     * ScientificCalculatorErrorState with corresponding error code if error occurred.
     */
    override fun hyperbolicArcCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "acosh"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculate cosine of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads.
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
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return ScientificCalculatorFirstOperandReadState(
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
     * Calculate arccosine of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Return ScientificCalculatorFirstOperandReadState
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
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity, ""
        ) + HISTORY_STRING_SPACE_LETTER + operationString

        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                historyString = historyString,
                scientificOperationType = scientificOperationType,
                operand = scientificCalculatorDataEntity.mainString.commaTruncate()
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
                mainString = piNumber(
                    scientificCalculatorDataEntity.isScientificNotation
                ),
                historyString = getHistoryStringWithRemovedLastMathFunctionHistory(
                    scientificCalculatorDataEntity.historyString
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
                mainString = doublePiNumber(scientificCalculatorDataEntity.isScientificNotation),
                historyString = getHistoryStringWithRemovedLastMathFunctionHistory(
                    scientificCalculatorDataEntity.historyString
                )
            )
        )
    }

    /**
     * Calculates hyperbolic tangent of entered to the mainString number of calculator data. Operation recorded to
     * historyString of calculator data. Return ScientificCalculatorFirstOperandReadState.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully.
     */
    override fun hyperbolicTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "tanh"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculates hyperbolic arctangent of entered number to the mainString of calculator data. Operation recorded to
     * historyString of calculator data. Return ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState, depending on entered to mainString number.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data if modulus of a number
     * of entered to mainString number is less than 1
     * ScientificCalculatorErrorState with corresponding error code otherwise.
     */
    override fun hyperbolicArcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "atanh"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculate tangent of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads. Return ScientificCalculatorFirstOperandReadState
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
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculates arctangent of entered to mainString number. Operation recorded to historyString
     * field of calculator data. Result of operation depends on angleUnitCode parameter -
     * it contains code of applied angle units and defines whether number is in degrees,
     * radians or grads.
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
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            operationString
        )

        return ScientificCalculatorFirstOperandReadState(
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
     * Calculates number, entered to mainString of calculator data, to the power of 3. Operation
     * recorded to historyString of calculator data. Return ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState if error occurred with corresponding error code in calculator data.
     */
    override fun cubeNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "cube"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculates cube root of entered to mainString number and write result number back to mainString.
     * Completed operation writes to historyString, current state not changed.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data
     */
    override fun cubeRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "cuberoot"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Changes the number display format, entered to mainString field of calculator data,
     * from conventional to scientific notation and backward. Return ScientificCalculatorFirstOperandReadState.
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
     **/
    override fun exponentialFormat(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return this
    }

    /**
     * Calculates logarithm base 10 of the number, entered to mainString field of calculator data.
     * Operation recorded to historyString field of calculator data. Return
     * ScientificCalculatorFirstOperandReadState or ScientificCalculatorErrorState, depending on
     * number in mainString field.
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if
     * number in mainString is more than zero
     * ScientificCalculatorErrorState with appropriate code in errorCode field if otherwise
     *
     */
    override fun logarithmBaseTen(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "log"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Calculates 10 to the power of number, entered to mainString field of calculator data. Operation
     * recorded to historyString field of calculator data. Changes calculator state to ScientificCalculatorFirstOperandReadState
     * or ScientificCalculatorErrorState if number is too big and overflow occurred.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with updated calculator data if operation completed
     * successfully;
     * ScientificCalculatorErrorState with corresponding error code in calculator data if error occurred.
     */
    override fun tenPowerX(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity,
            "powten"
        )

        return try {
            ScientificCalculatorFirstOperandReadState(
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
     * Inserts operationString to historyString to a certain place. This place is after
     * last opening bracket (inserted to historyString when open bracket button of calculator and
     * new state is created) and before string with last operation recording.
     * @param scientificCalculatorDataEntity - instance, that contain string, that need to be modified
     * @param operationString - string to insert
     * @return result string
     */
    fun getHistoryStringWithInsertedOperationString(scientificCalculatorDataEntity: ScientificCalculatorDataEntity, operationString: String): String {
        val stringBeforeLastSpace = scientificCalculatorDataEntity.historyString.substringBeforeLast(HISTORY_STRING_SPACE_LETTER, "")
        val stringAfterLastSpace = scientificCalculatorDataEntity.historyString.substringAfterLast(HISTORY_STRING_SPACE_LETTER)
        val spaceBeforeOpeningBracket = if (stringBeforeLastSpace.isEmpty()) ""
        else HISTORY_STRING_SPACE_LETTER
        val prevStateOpeningBrackets = stringAfterLastSpace.takeWhile { !it.isLetterOrDigit() }
        val stringAfterPrevStateOpeningBrackets = stringAfterLastSpace.substringAfter(prevStateOpeningBrackets)
        val openBracketString = "("
        val closeBracketString = ")"

        return if (scientificCalculatorDataEntity.historyString.substringAfterLast("(", "").isEmpty())
            stringBeforeLastSpace +
            spaceBeforeOpeningBracket +
            prevStateOpeningBrackets +
            "$operationString$openBracketString${scientificCalculatorDataEntity.mainString.calcFormat(
                scientificCalculatorDataEntity.isScientificNotation
            )}$closeBracketString"
        else stringBeforeLastSpace +
                spaceBeforeOpeningBracket +
                prevStateOpeningBrackets +
                operationString +
                openBracketString +
                stringAfterPrevStateOpeningBrackets +
                closeBracketString
    }

    /**
     * Removes from history string recordings about last commited math functions in current
     * state. If there are had previous state, then removes recordings only after last
     * opening bracket, that create new state.
     * @param historyString - string that need to be modified
     * @return result string
     */
    fun getHistoryStringWithRemovedLastMathFunctionHistory(historyString: String): String {
        val stringBeforeLastSpace = historyString.substringBeforeLast(HISTORY_STRING_SPACE_LETTER, "")
        val stringAfterLastSpace = historyString.substringAfterLast(HISTORY_STRING_SPACE_LETTER)
        val spaceBeforeOpeningBracket = if (stringBeforeLastSpace.isEmpty()) ""
        else HISTORY_STRING_SPACE_LETTER
        val prevStateOpeningBrackets = stringAfterLastSpace.takeWhile { !it.isLetterOrDigit() }

        return stringBeforeLastSpace +
                spaceBeforeOpeningBracket +
                prevStateOpeningBrackets
    }

    /**
     * Calculates result of consecutive math operations with numbers, placed in all states variables and returns it.
     * @param scientificCalculatorBaseState - calculator state, that contains numbers and operation types
     * @return String with result number
     */
    fun getAllStatesCalculationResult(scientificCalculatorBaseState: ScientificCalculatorBaseState): String {
        var prevState: ScientificCalculatorBaseState? = scientificCalculatorBaseState.scientificCalculatorDataEntity.prevState
        var currentOperand = scientificCalculatorBaseState.scientificCalculatorDataEntity.mainString

        while (prevState != null) {
            val prevOperand = prevState.scientificCalculatorDataEntity.operand
            currentOperand = when(prevState.scientificCalculatorDataEntity.scientificOperationType) {
                ScientificOperationType.PLUS -> prevOperand.add(currentOperand, prevState.scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.MINUS -> prevOperand.subtract(currentOperand, prevState.scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.MULTIPLY -> prevOperand.multiply(currentOperand, prevState.scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.DIVIDE -> prevOperand.divide(currentOperand, prevState.scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.MODULUS -> prevOperand.modulus(currentOperand, prevState.scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.ROOT_OF -> prevOperand.rootOfNumber(currentOperand, prevState.scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.POWER_OF -> prevOperand.powerOfNumber(currentOperand, prevState.scientificCalculatorDataEntity.isScientificNotation)
                ScientificOperationType.NO_OPERATION -> currentOperand
            }
            prevState = prevState.scientificCalculatorDataEntity.prevState
        }
        return currentOperand
    }
}