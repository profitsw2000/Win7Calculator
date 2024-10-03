package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.OperationType
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorErrorState
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorFirstOperandInputState
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorFirstOperandReadState
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorInitialState
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorOperationResultState
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorPrimitiveMathOperationState
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorBaseState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorState
import kotlin.math.ln
import kotlin.math.sqrt

class ScientificCalculatorInitialState(
    override val scientificCalculatorDataEntity: ScientificCalculatorDataEntity
) : ScientificCalculatorBaseState {

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> TODO()
            CalculatorAction.AddToMemory -> TODO()
            CalculatorAction.ArcCosine -> TODO()
            CalculatorAction.ArcSinus -> TODO()
            CalculatorAction.ArcTangent -> TODO()
            CalculatorAction.Backspace -> TODO()
            CalculatorAction.Clear -> clearAll(scientificCalculatorDataEntity)
            CalculatorAction.ClearEntered -> TODO()
            CalculatorAction.ClearMemory -> TODO()
            CalculatorAction.Cosine -> TODO()
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
            CalculatorAction.Sinus -> TODO()
            CalculatorAction.SquareRoot -> TODO()
            CalculatorAction.SquaredX -> TODO()
            CalculatorAction.Subtract -> TODO()
            CalculatorAction.SubtractFromMemory -> TODO()
            CalculatorAction.Tangent -> TODO()
            CalculatorAction.TenToPowerOfX -> TODO()
            CalculatorAction.ThirdRootOfX -> TODO()
            CalculatorAction.XPowerThree -> TODO()
            CalculatorAction.XPowerY -> TODO()
            CalculatorAction.YRootOfX -> TODO()
            else -> this
        }
    }

    /**
     * Clear internal memory of calculator
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorInitialState with cleared memory of calculator data
     */
    override fun clearMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorInitialState(scientificCalculatorDataEntity.copy(memoryNumber = null))
    }

    /**
     * Reads internal memory and copy it to mainString of calculator data
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with new main string field of calculator data if memoryNumber is not null
     * or same state if it is.
     */
    override fun readMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return if (scientificCalculatorDataEntity.memoryNumber == null) {
            this
        } else {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(mainString = doubleToCalculatorString(scientificCalculatorDataEntity.memoryNumber))
            )
        }
    }

    /**
     * Save number from main string to memoryNumber field
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorInitialState with same calculator data but with new memoryNumber. mainString field converted to number and written to memoryNumber field.
     */
    override fun saveToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorInitialState(
            scientificCalculatorDataEntity.copy(
                memoryNumber = if (scientificCalculatorDataEntity.mainString == "0") null
                else calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Add number, that contain main string of calculators display, to memory number
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return Initial state with memory number field incremented on number, that contained in main string field
     */
    override fun addNumberToMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return if (scientificCalculatorDataEntity.memoryNumber == null) {
            if (scientificCalculatorDataEntity.mainString == "0") this
            else ScientificCalculatorInitialState(
                scientificCalculatorDataEntity.copy(
                    memoryNumber = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
                )
            )
        } else {
            ScientificCalculatorInitialState(
                scientificCalculatorDataEntity.copy(memoryNumber = (scientificCalculatorDataEntity.memoryNumber + calculatorStringToDouble(scientificCalculatorDataEntity.mainString)))
            )
        }
    }

    /**
     * Subtract number, that contain main string of calculators display, from memory number
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return Initial state with memory number field decremented on number, that contained in main string field
     */
    override fun subtractNumberFromMemory(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return if (scientificCalculatorDataEntity.memoryNumber == null) {
            if (scientificCalculatorDataEntity.mainString == "0") this
            else ScientificCalculatorInitialState(
                scientificCalculatorDataEntity.copy(
                    memoryNumber = 0 - calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
                )
            )
        } else {
            ScientificCalculatorInitialState(
                scientificCalculatorDataEntity.copy(memoryNumber = (scientificCalculatorDataEntity.memoryNumber - calculatorStringToDouble(scientificCalculatorDataEntity.mainString)))
            )
        }
    }

    /**
     * Clears all data, contained in fields mainString, historyString and operand fields of calculator data.
     * OperationType set to NO_OPERATION state
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return Initial state with some fields of calculator data set to default state
     */
    override fun clearAll(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorInitialState(ScientificCalculatorDataEntity(memoryNumber = scientificCalculatorDataEntity.memoryNumber))
    }

    /**
     * Changes inputs number sign to opposite and writes action to history.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return Initial state with changed main string and writes action to history string of calculator data
     */
    override fun negateOperand(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return this
    }

    /**
     * Calculates square root of input number and writes action to history of calculator data
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorOperationResultState with result of square root calculation in main string field and
     * action written to history string of calculator data
     */
    override fun calculateSquareRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            val sqrtDouble = sqrt(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
            val sqrtString = doubleToCalculatorString(sqrtDouble)

            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = sqrtString,
                    historyString = if (scientificCalculatorDataEntity.historyString == "") "sqrt(${scientificCalculatorDataEntity.mainString})"
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
     * Changed current state if clicked button is not 0. Clicked digit stored in main string field of calculator data.
     * @param1 scientificCalculatorDataEntity - contains current calculator data
     * @param2 digitToAppend - contain string with number of clicked button
     * @return ScientificCalculatorFirstOperandInputState if clicked digit is not 0 with digit stored in main string field, otherwise returns same state with same calculator data.
     */
    override fun inputDigit(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        digitToAppend: String
    ): CalculatorState {
        return if (digitToAppend  == "0") this
        else if(digitToAppend == ",") ScientificCalculatorFirstOperandInputState(scientificCalculatorDataEntity.copy(mainString = "0,"))
        else ScientificCalculatorFirstOperandInputState(scientificCalculatorDataEntity.copy(mainString = digitToAppend))
    }

    /**
     * Changes current state to ScientificCalculatorPrimitiveMathOperationState, input number and operation sign writes to history string of calculator data,
     * same as operation type.
     * @param1 scientificCalculatorDataEntity - contains current calculator data,
     * @param2 scientificOperationType - type of primitive math operation
     * @param3 operationString - operation sign, need to be added in history string
     * @return ScientificCalculatorPrimitiveMathOperationState with changed historyString and operationType fields of calculator data
     */
    override fun primitiveMathOperation(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        scientificOperationType: ScientificOperationType,
        operationString: String
    ): CalculatorState {
        val historyString = "${scientificCalculatorDataEntity.mainString}$HISTORY_STRING_SPACE_LETTER$operationString"

        return ScientificCalculatorPrimitiveMathOperationState(
            scientificCalculatorDataEntity.copy(
                historyString = historyString,
                scientificOperationType = scientificOperationType,
                operand = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Calculates inversely proportioned number to that contained in main string field of calculator data
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorOperationResultState if calculation completed successfully with result written in main string field and
     * action written in history string. If math operation throws exception, then ScientificCalculatorErrorState returned by function with
     * error code written to corresponded field and action written to history string of calculator data
     */
    override fun reciprocOperation(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorOperationResultState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(1/(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))),
                    historyString = "reciproc(${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (numberFormatException: NumberFormatException) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                    historyString = "reciproc(${scientificCalculatorDataEntity.mainString})",
                    errorCode = DIVIDE_ON_ZERO_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(scientificCalculatorDataEntity.copy(
                    historyString = "reciproc(${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /*
    * Not used in this state
     */
    override fun calculateResult(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return this
    }

    /*
    * Opens bracket to make new expression
    * @param scientificCalculatorDataEntity - contains current calculator data
    * @return ScientificCalculatorInitialState with same data, except history string (added open bracket sign)
    * State before this operation writes to prevState field
     */
    override fun openBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity(
                historyString = "${scientificCalculatorDataEntity.historyString}(",
                memoryNumber = scientificCalculatorDataEntity.memoryNumber,
                prevState = this
            )
        )
    }


    /*
    * Close bracket to make new expression
    * @param scientificCalculatorDataEntity - contains current calculator data
    * @return previous stata, stored in prevState field of scientificCalculatorDataEntity with changed historyString field
    * (added close bracket)
     */
    override fun closeBracket(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {

        if (scientificCalculatorDataEntity.prevState == null)
            return this
        else
            return ScientificCalculatorInitialState(
                scientificCalculatorDataEntity
                    .prevState
                    .scientificCalculatorDataEntity.copy(
                        historyString = "${scientificCalculatorDataEntity.historyString})"
                    )
            )
    }

    /*
    * Calculates natural logarithm
    * @param scientificCalculatorDataEntity - contains current calculator data
    * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and calculation result in mainString field
    * if calculation completed successfully
    * ScientificCalculatorErrorState if calculation completed with error
     */
    override fun calculateNaturalLogarithm(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    doubleToCalculatorString(ln(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))),
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (numberFormatException: NumberFormatException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = DIVIDE_ON_ZERO_ERROR_CODE
                )
            )
        }
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

    override fun hyperbolicSinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicArcSinus(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
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

    override fun decimalToDegrees(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicCosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun hyperbolicArcCosine(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {
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

    override fun tangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        TODO("Not yet implemented")
    }

    override fun arcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
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
}