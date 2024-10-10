package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.constants.DEGREES_ANGLE_CODE
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.GRADS_ANGLE_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.OVERFLOW_ERROR_CODE
import ru.profitsw2000.data.constants.RADIANS_ANGLE_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorPrimitiveMathOperationState
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorBaseState
import ru.profitsw2000.utils.calcCosh
import ru.profitsw2000.utils.calcSinh
import ru.profitsw2000.utils.factorial
import ru.profitsw2000.utils.powerTo
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.acosh
import kotlin.math.asin
import kotlin.math.asinh
import kotlin.math.atanh
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.math.tanh
import kotlin.math.truncate

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

        return ScientificCalculatorMathOperationState(
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

    /**
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


    /**
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

    /**
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
                    mainString = doubleToCalculatorString(ln(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))),
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
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
    * Calculates exponent raised to the power of entered number
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
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (numberFormatException: NumberFormatException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}powe(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}powe(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
    * Rounds entered number(placed in mainString field of scientificCalculatorDataEntity) to the next whole towards zero
    * @param scientificCalculatorDataEntity - contains current calculator data
    * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
    * rounded number placed in mainString field
     */
    override fun integerOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    truncate(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}Int(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
    * Calculates fractional part of entered number(placed in mainString field of scientificCalculatorDataEntity)
    * and place it to the same field. History of operation writes to historyString field
    * @param scientificCalculatorDataEntity - contains current calculator data
    * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
    * fractional part of number placed in mainString field
     */
    override fun fractionOfNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    calculatorStringToDouble(scientificCalculatorDataEntity.mainString) % 1),
                historyString = "${scientificCalculatorDataEntity.historyString}frac(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
    * Calculates hyperbolic sinus of entered number(placed in mainString field of scientificCalculatorDataEntity)
    * and place it to the same field. History of operation writes to historyString field
    * @param scientificCalculatorDataEntity - contains current calculator data
    * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
    * result of implemented operation placed in mainString field
     */
    override fun hyperbolicSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {

        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        calculatorStringToDouble(scientificCalculatorDataEntity.mainString).calcSinh()
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}sinh(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sinh(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sinh(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
    * Calculates hyperbolic arcsinus of entered number(placed in mainString field of scientificCalculatorDataEntity)
    * and place it to the same field. History of operation writes to historyString field
    * @param scientificCalculatorDataEntity - contains current calculator data
    * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
    * result of implemented operation placed in mainString field
     */
    override fun hyperbolicArcSinus(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    asinh(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}asinh(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
    * Calculates sinus of entered angle(placed in mainString field of scientificCalculatorDataEntity)
    * and place result to the same field. Unit of angle depends on what is in angleUnitCode argument.
    * History of operation writes to historyString field and also depends on angleUnitCode.
    * @param scientificCalculatorDataEntity - contains current calculator data
    * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
    * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
    * result of implemented operation placed in mainString field
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
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
    * Calculates arcsinus of entered number(placed in mainString field of scientificCalculatorDataEntity)
    * and place result angle to the same field. Unit of angle depends on what is in angleUnitCode argument.
    * History of operation writes to historyString field and also depends on angleUnitCode.
    * @param scientificCalculatorDataEntity - contains current calculator data
    * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
    * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
    * result of implemented operation placed in mainString field
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
                        "${scientificCalculatorDataEntity.mainString})",
                errorCode = UNKNOWN_ERROR_CODE
            )
        ) else ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(result),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Calculates square of entered number(placed in mainString field of scientificCalculatorDataEntity)
     * Result of operation placed to mainString field.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
     * result of implemented operation placed in mainString field.
     * ScientificCalculatorErrorState - if result of operation is too big
     * and overflow of Double number was happened.
     */
    override fun squareNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        calculatorStringToDouble(scientificCalculatorDataEntity.mainString).powerTo(2.0)
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}sqr(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sqr(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}sqr(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates factorial of entered number(placed in mainString field of scientificCalculatorDataEntity)
     * Result of operation placed to mainString field.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
     * result of implemented operation placed in mainString field.
     * ScientificCalculatorErrorState - if result of operation is too big
     * and overflow of Double number was happened.
     */
    override fun factorial(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        calculatorStringToDouble(scientificCalculatorDataEntity.mainString).factorial()
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}fact(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}fact(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}fact(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Converts fractional part of entered angle value(placed in mainString field of scientificCalculatorDataEntity)
     * from decimal format to minutes format.
     * Result of conversion placed to mainString field.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
     * result of implemented operation placed in mainString field.
     */
    override fun decimalToDegrees(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        val fraction = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)%1
        val integer = truncate(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
        val convertedFraction = (fraction*60)/100
        val convertedValue = integer + convertedFraction

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(convertedValue),
                historyString = "${scientificCalculatorDataEntity.historyString}dms(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Calculates hyperbolic cosine of entered number(placed in mainString field of scientificCalculatorDataEntity)
     * and place result to the same field. History of operation writes to historyString field
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
     * result of implemented operation placed in mainString field if no exception happened.
     * Otherwise ScientificCalculatorErrorState with appropriate error code
     */
    override fun hyperbolicCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        calculatorStringToDouble(scientificCalculatorDataEntity.mainString).calcCosh()
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}cosh(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}cosh(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}cosh(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates hyperbolic arccosine of entered number(placed in mainString field of scientificCalculatorDataEntity)
     * and place it to the same field. History of operation writes to historyString field
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
     * result of implemented operation placed in mainString field
     */
    override fun hyperbolicArcCosine(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    acosh(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Calculates cosine of entered angle(placed in mainString field of scientificCalculatorDataEntity)
     * and place result to the same field. Unit of angle depends on what is in angleUnitCode argument.
     * History of operation writes to historyString field and also depends on angleUnitCode.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
     * result of implemented operation placed in mainString field
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
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Calculates arccosine of entered number(placed in mainString field of scientificCalculatorDataEntity)
     * and place result angle to the same field. Unit of angle depends on what is in angleUnitCode argument.
     * History of operation writes to historyString field and also depends on angleUnitCode.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code of angle units(can be degrees, radians or grads)
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
     * result of implemented operation placed in mainString field or
     * ScientificCalculatorErrorState if number in mainString field is greater than 1.
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
                        "${scientificCalculatorDataEntity.mainString})",
                errorCode = UNKNOWN_ERROR_CODE
            )
        ) else ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(result),
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString})"
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
        val historyString = "${scientificCalculatorDataEntity.mainString}$HISTORY_STRING_SPACE_LETTER$operationString"
        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                historyString = historyString,
                scientificOperationType = scientificOperationType,
                operand = calculatorStringToDouble(scientificCalculatorDataEntity.mainString)
            )
        )
    }

    /**
     * Placed Pi number to the mainString field of scientificCalculatorDataEntity
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with scientificCalculatorDataEntity with Pi
     * value placed in mainString field
     */
    override fun piNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(PI)
            )
        )
    }

    /**
     * Placed 2*Pi number to the mainString field of scientificCalculatorDataEntity
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with scientificCalculatorDataEntity with 2*Pi
     * value placed in mainString field
     */
    override fun doublePiNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(2*PI)
            )
        )
    }

    /**
     * Calculate hyperbolic tangent of number, placed in mainString field of scientificCalculatorDataEntity
     * and placed result back to the same field. This operation added to historyString field.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with calculator data placed in scientificCalculatorDataEntity.
     * mainString of scientificCalculatorDataEntity contain result of tangent calculation,
     * whereas historyString field appends with record of current operation.
     */
    override fun hyperbolicTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = doubleToCalculatorString(
                    tanh(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                ),
                historyString = "${scientificCalculatorDataEntity.historyString}tanh(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Calculate hyperbolic arctangent of number, placed in mainString field of scientificCalculatorDataEntity
     * and placed result back to the same field. This operation added to historyString field.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return
     * - ScientificCalculatorFirstOperandReadState with calculator data placed in scientificCalculatorDataEntity
     * if abs of number in mainString field equal or less than 1.
     * mainString of scientificCalculatorDataEntity contain result of tangent calculation,
     * whereas historyString field appends with record of current operation.
     * - ScientificCalculatorErrorState if abs of number in mainString field more than 1.
     * Error code then recorded in appropriate field.
     */
    override fun hyperbolicArcTangent(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return if (abs(calculatorStringToDouble(scientificCalculatorDataEntity.mainString)) <= 1)
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = doubleToCalculatorString(
                        atanh(calculatorStringToDouble(scientificCalculatorDataEntity.mainString))
                    ),
                    historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        else ScientificCalculatorErrorState(
            scientificCalculatorDataEntity.copy(
                historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                        "${scientificCalculatorDataEntity.mainString})",
                errorCode = UNKNOWN_ERROR_CODE
            )
        )
    }

    /**
     * Calculate tangent of number, placed in mainString field of scientificCalculatorDataEntity
     * and placed result back to the same field. This operation added to historyString field.
     * Unit of angle depends on what is in angleUnitCode argument.
     * History of operation writes to historyString field and also depends on angleUnitCode.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return
     * - ScientificCalculatorFirstOperandReadState with calculator data placed in scientificCalculatorDataEntity.
     * mainString of scientificCalculatorDataEntity contain result of tangent calculation,
     * whereas historyString field appends with record of current operation if calculation
     * completed successfully.
     * - ScientificCalculatorErrorState if overflow is happened.
     * Error code then recorded in appropriate field.
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
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}" +
                            "$operationString(" +
                            "${scientificCalculatorDataEntity.mainString})",
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
}