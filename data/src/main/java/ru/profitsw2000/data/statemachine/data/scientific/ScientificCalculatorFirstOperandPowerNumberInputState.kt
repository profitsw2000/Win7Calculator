package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
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
        return if (scientificCalculatorDataEntity.mainString.length > 4 ||
            digitToAppend == ",") this
        else
            ScientificCalculatorFirstOperandPowerNumberInputState(
                scientificCalculatorDataEntity.copy(
                    mainString = "${scientificCalculatorDataEntity.mainString}$digitToAppend"
                )
            )
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
        TODO("Not yet implemented")
    }

    override fun clearEntered(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
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