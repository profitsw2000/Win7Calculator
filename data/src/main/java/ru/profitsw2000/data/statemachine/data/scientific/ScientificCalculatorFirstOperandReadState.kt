package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
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
        TODO("Not yet implemented")
    }

    override fun clearDigit(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorInitialState(
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
        val historyString = if (scientificCalculatorDataEntity.historyString.isEmpty())
                "negate(${scientificCalculatorDataEntity.mainString.calcFormat(
                    scientificCalculatorDataEntity.isScientificNotation
                )})"
            else getHistoryStringWithInsertedOperationString(
                scientificCalculatorDataEntity.historyString,
                "negate"
            )
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.negateExponent(),
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
        val historyString = if (scientificCalculatorDataEntity.historyString.isEmpty())
            "sqrt(${scientificCalculatorDataEntity.mainString.calcFormat(
                scientificCalculatorDataEntity.isScientificNotation
            )})"
        else getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity.historyString,
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
                mainString = digitToAppend,
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
     * Calculates result of one divided on number in mainString field and
     * writes it back to mainString. Designator of committed operation appended to history string.
     * Changes current state depending on result.
     * @param scientificCalculatorDataEntity - contains calculator data
     * @return ScientificCalculatorFirstOperandInputState with updated calculator data if
     * divider is not equal to zero
     * ScientificCalculatorErrorState with updated calculator data if divider equal 0
     */
    override fun reciprocOperation(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val historyString = if (scientificCalculatorDataEntity.historyString.isEmpty())
            "sqrt(${scientificCalculatorDataEntity.mainString.calcFormat(
                scientificCalculatorDataEntity.isScientificNotation
            )})"
        else getHistoryStringWithInsertedOperationString(
            scientificCalculatorDataEntity.historyString,
            "sqrt"
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

    /**
     * Inserts operationString to historyString to a certain place. This place is after
     * last opening bracket (inserted to historyString when open bracket button of calculator and
     * new state is created) and before string with last operation recording.
     * @param historyString - string that need to be modified
     * @param operationString - string to insert
     * @return result string
     */
    fun getHistoryStringWithInsertedOperationString(historyString: String, operationString: String): String {
        val stringBeforeLastSpace = historyString.substringBeforeLast(HISTORY_STRING_SPACE_LETTER, "")
        val stringAfterLastSpace = historyString.substringAfterLast(HISTORY_STRING_SPACE_LETTER)
        val spaceBeforeOpeningBracket = if (stringBeforeLastSpace.isEmpty()) ""
        else HISTORY_STRING_SPACE_LETTER
        val prevStateOpeningBrackets = stringAfterLastSpace.takeWhile { !it.isLetterOrDigit() }
        val stringAfterPrevStateOpeningBrackets = stringAfterLastSpace.substringAfter(prevStateOpeningBrackets)
        val openBracketString = "("
        val closeBracketString = ")"

        return stringBeforeLastSpace +
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

    fun getAllStatesCalculationResult(scientificCalculatorBaseState: ScientificCalculatorBaseState): String {
        var prevState: ScientificCalculatorBaseState = scientificCalculatorBaseState.scientificCalculatorDataEntity.prevState!!
        var currentOperand = scientificCalculatorBaseState.scientificCalculatorDataEntity.mainString

        while (!prevState.equals(null)) {
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
            prevState = prevState.scientificCalculatorDataEntity.prevState!!
        }
        return currentOperand
    }
}