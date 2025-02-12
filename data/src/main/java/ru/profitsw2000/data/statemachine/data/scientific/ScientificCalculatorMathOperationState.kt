package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.EXPONENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.NATURAL_LOGARITHM_FUNCTION_CODE
import ru.profitsw2000.data.constants.SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
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
        val historyString = scientificCalculatorDataEntity.historyString +
                HISTORY_STRING_SPACE_LETTER +
                "negate(${scientificCalculatorDataEntity.mainString})"

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
        val historyString = scientificCalculatorDataEntity.historyString +
                HISTORY_STRING_SPACE_LETTER +
                "sqrt(${scientificCalculatorDataEntity.mainString})"

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
        val historyString = scientificCalculatorDataEntity.historyString +
                "reciproc(" +
                "${scientificCalculatorDataEntity.mainString})"

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
        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        NATURAL_LOGARITHM_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = appendOperationString(scientificCalculatorDataEntity, "ln")
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = appendOperationString(scientificCalculatorDataEntity, "ln"),
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = appendOperationString(scientificCalculatorDataEntity, "ln"),
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
        return try {
            ScientificCalculatorSecondOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        EXPONENT_FUNCTION_CODE,
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = appendOperationString(scientificCalculatorDataEntity, "powe")
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = appendOperationString(scientificCalculatorDataEntity, "powe"),
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.calcFormat(
                        scientificCalculatorDataEntity.isScientificNotation
                    ),
                    historyString = appendOperationString(scientificCalculatorDataEntity, "powe"),
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

    private fun getAllStatesCalculationResult(scientificCalculatorBaseState: ScientificCalculatorBaseState): String {
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

    private fun appendOperationString(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        operationString: String
    ): String {
        return scientificCalculatorDataEntity.historyString +
                "$operationString(" +
                "${scientificCalculatorDataEntity.mainString})"
    }
}