package ru.profitsw2000.data.statemachine.data.scientific

import ru.profitsw2000.data.constants.ARC_COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.ARC_SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.ARC_TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.COSINE_FUNCTION_CODE
import ru.profitsw2000.data.constants.DEGREES_ANGLE_CODE
import ru.profitsw2000.data.constants.DEGREES_TO_RADIANS_FUNCTION_CODE
import ru.profitsw2000.data.constants.DEG_FUNCTION_CODE
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.DMS_FUNCTION_CODE
import ru.profitsw2000.data.constants.EXPONENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.FRACTIONAL_PART_FUNCTION_CODE
import ru.profitsw2000.data.constants.GRADS_ANGLE_CODE
import ru.profitsw2000.data.constants.GRADS_TO_RADIANS_FUNCTION_CODE
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
import ru.profitsw2000.data.constants.RADIANS_TO_DEGREES_FUNCTION_CODE
import ru.profitsw2000.data.constants.RADIANS_TO_GRADS_FUNCTION_CODE
import ru.profitsw2000.data.constants.SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.SINUS_FUNCTION_CODE
import ru.profitsw2000.data.constants.TANGENT_FUNCTION_CODE
import ru.profitsw2000.data.constants.UNKNOWN_ERROR_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.ScientificCalculatorBaseState
import ru.profitsw2000.utils.powerTo
import kotlin.math.log10

class ScientificCalculatorInitialState(
    override val scientificCalculatorDataEntity: ScientificCalculatorDataEntity
) : ScientificCalculatorBaseState {

    override val scale: Int
        get() = SCIENTIFIC_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER

    override fun consumeAction(action: CalculatorAction): CalculatorState {
        return when(action) {
            CalculatorAction.Add -> primitiveMathOperation(scientificCalculatorDataEntity, ScientificOperationType.PLUS, "+")
            CalculatorAction.AddToMemory -> addNumberToMemory(scientificCalculatorDataEntity)
            is CalculatorAction.ArcCosine -> arcCosine(scientificCalculatorDataEntity, action.angleUnitCode)
            is CalculatorAction.ArcSinus -> arcSinus(scientificCalculatorDataEntity, action.angleUnitCode)
            is CalculatorAction.ArcTangent -> arcTangent(scientificCalculatorDataEntity, action.angleUnitCode)
            CalculatorAction.Backspace -> this
            CalculatorAction.Clear -> clearAll(scientificCalculatorDataEntity)
            CalculatorAction.ClearEntered -> this
            CalculatorAction.ClearMemory -> clearMemory(scientificCalculatorDataEntity)
            is CalculatorAction.Cosine -> cosine(scientificCalculatorDataEntity, action.angleUnitCode)
            CalculatorAction.DecimalDegrees -> minutesToDecimal(scientificCalculatorDataEntity)
            is CalculatorAction.Digit -> inputDigit(scientificCalculatorDataEntity, action.digit)
            CalculatorAction.Divide -> primitiveMathOperation(scientificCalculatorDataEntity, ScientificOperationType.DIVIDE, "/")
            CalculatorAction.Dms -> decimalToMinutes(scientificCalculatorDataEntity)
            CalculatorAction.DoublePi -> doublePiNumber(scientificCalculatorDataEntity)
            CalculatorAction.Equal -> calculateResult(scientificCalculatorDataEntity)
            CalculatorAction.ExponentOfX -> calculateExponent(scientificCalculatorDataEntity)
            CalculatorAction.ExponentialForm -> exponentialFormat(scientificCalculatorDataEntity)
            CalculatorAction.Factorial -> factorial(scientificCalculatorDataEntity)
            CalculatorAction.FixedToExponent -> fixedToExponentialFormat(scientificCalculatorDataEntity)
            CalculatorAction.Fraction -> fractionOfNumber(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicArcCosine -> hyperbolicArcCosine(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicArcSinus -> hyperbolicArcSinus(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicArcTangent -> hyperbolicArcTangent(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicCosine -> hyperbolicCosine(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicSinus -> hyperbolicSinus(scientificCalculatorDataEntity)
            CalculatorAction.HyperbolicTangent -> hyperbolicTangent(scientificCalculatorDataEntity)
            CalculatorAction.Integer -> integerOfNumber(scientificCalculatorDataEntity)
            CalculatorAction.Inverse -> this
            CalculatorAction.LeftBracket -> openBracket(scientificCalculatorDataEntity)
            CalculatorAction.Logarithm -> logarithmBaseTen(scientificCalculatorDataEntity)
            CalculatorAction.Modulus -> primitiveMathOperation(scientificCalculatorDataEntity, ScientificOperationType.MODULUS, "mod")
            CalculatorAction.Multiply -> primitiveMathOperation(scientificCalculatorDataEntity, ScientificOperationType.MULTIPLY, "*")
            CalculatorAction.NaturalLogarithm -> calculateNaturalLogarithm(scientificCalculatorDataEntity)
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
            CalculatorAction.Subtract -> primitiveMathOperation(scientificCalculatorDataEntity, ScientificOperationType.MINUS, "-")
            CalculatorAction.SubtractFromMemory -> subtractNumberFromMemory(scientificCalculatorDataEntity)
            is CalculatorAction.Tangent -> tangent(scientificCalculatorDataEntity, action.angleUnitCode)
            CalculatorAction.TenToPowerOfX -> tenPowerX(scientificCalculatorDataEntity)
            CalculatorAction.ThirdRootOfX -> cubeRoot(scientificCalculatorDataEntity)
            CalculatorAction.XPowerThree -> cubeNumber(scientificCalculatorDataEntity)
            CalculatorAction.XPowerY -> primitiveMathOperation(scientificCalculatorDataEntity, ScientificOperationType.POWER_OF, "pow")
            CalculatorAction.YRootOfX -> primitiveMathOperation(scientificCalculatorDataEntity, ScientificOperationType.ROOT_OF, "root")
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
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.memoryNumber
                )
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
                else scientificCalculatorDataEntity.mainString
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
                    memoryNumber = scientificCalculatorDataEntity.mainString
                )
            )
        } else {
            ScientificCalculatorInitialState(
                scientificCalculatorDataEntity.copy(memoryNumber = (scientificCalculatorDataEntity.memoryNumber.add(scientificCalculatorDataEntity.mainString)))
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
                    memoryNumber = "0".subtract(scientificCalculatorDataEntity.mainString)
                )
            )
        } else {
            ScientificCalculatorInitialState(
                scientificCalculatorDataEntity.copy(memoryNumber = scientificCalculatorDataEntity.memoryNumber.subtract(scientificCalculatorDataEntity.mainString))
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
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.sqrt(),
                    historyString = if (scientificCalculatorDataEntity.historyString == "") "sqrt(${scientificCalculatorDataEntity.mainString})"
                    else "sqrt(${scientificCalculatorDataEntity.historyString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
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
        return when (digitToAppend) {
            "0" -> this
            "," -> ScientificCalculatorFirstOperandInputState(scientificCalculatorDataEntity.copy(mainString = "0,"))
            else -> ScientificCalculatorFirstOperandInputState(scientificCalculatorDataEntity.copy(mainString = digitToAppend))
        }
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
                operand = scientificCalculatorDataEntity.mainString
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
                    mainString = "1".divide(scientificCalculatorDataEntity.mainString),
                    historyString = "reciproc(${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
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
        val historyString = if (scientificCalculatorDataEntity.historyString.endsWith('('))
            "${scientificCalculatorDataEntity.historyString}0)"
        else "${scientificCalculatorDataEntity.historyString})"

        return if (scientificCalculatorDataEntity.prevState == null)
            this
        else{
            val returnData = scientificCalculatorDataEntity.prevState.scientificCalculatorDataEntity.copy(
                mainString = "0",
                memoryNumber = scientificCalculatorDataEntity.memoryNumber,
                historyString = historyString
            )
            when(scientificCalculatorDataEntity.prevState) {
                is ScientificCalculatorInitialState -> ScientificCalculatorInitialState(returnData)
                is ScientificCalculatorMathOperationState -> ScientificCalculatorSecondOperandReadState(returnData)
                else -> ScientificCalculatorInitialState(returnData)
            }
        }

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
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(
                        NATURAL_LOGARITHM_FUNCTION_CODE),
                    historyString = "${scientificCalculatorDataEntity.historyString}ln(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
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
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(EXPONENT_FUNCTION_CODE),
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
                mainString  = scientificCalculatorDataEntity.mainString.numberPart(INTEGRAL_PART_FUNCTION_CODE),
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
                mainString = scientificCalculatorDataEntity.mainString.numberPart(FRACTIONAL_PART_FUNCTION_CODE),
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
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(HYPERBOLIC_SINUS_FUNCTION_CODE),
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
                mainString = scientificCalculatorDataEntity.mainString.mathFunction(HYPERBOLIC_ARC_SINUS_FUNCTION_CODE),
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
            DEGREES_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                .convert(DEGREES_TO_RADIANS_FUNCTION_CODE)
                .mathFunction(SINUS_FUNCTION_CODE)
            RADIANS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString.mathFunction(SINUS_FUNCTION_CODE)
            GRADS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                .convert(GRADS_TO_RADIANS_FUNCTION_CODE)
                .mathFunction(SINUS_FUNCTION_CODE)
            else -> scientificCalculatorDataEntity.mainString.mathFunction(SINUS_FUNCTION_CODE)
        }
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "sind"
            RADIANS_ANGLE_CODE -> "sinr"
            GRADS_ANGLE_CODE -> "sing"
            else -> "sind"
        }

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = result,
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
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "asind"
            RADIANS_ANGLE_CODE -> "asinr"
            GRADS_ANGLE_CODE -> "asing"
            else -> "asind"
        }
        return try {
            val result = when(angleUnitCode) {
                DEGREES_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                    .highPrecisionMathFunction(ARC_SINUS_FUNCTION_CODE)
                    .convert(RADIANS_TO_DEGREES_FUNCTION_CODE)
                RADIANS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString.mathFunction(ARC_SINUS_FUNCTION_CODE)
                GRADS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                    .highPrecisionMathFunction(ARC_SINUS_FUNCTION_CODE)
                    .convert(RADIANS_TO_GRADS_FUNCTION_CODE)
                else -> scientificCalculatorDataEntity.mainString.mathFunction(ARC_SINUS_FUNCTION_CODE)
            }

            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = result,
                    historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        }
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
                    mainString = scientificCalculatorDataEntity.mainString.powerOfNumber("2"),
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
                    mainString = scientificCalculatorDataEntity.mainString.factorial(),
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
    override fun decimalToMinutes(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.decimalMinutes(DMS_FUNCTION_CODE),
                historyString = "${scientificCalculatorDataEntity.historyString}dms(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Converts fractional part of entered angle value(placed in mainString field of scientificCalculatorDataEntity)
     * from minutes format to decimal format.
     * Result of conversion placed to mainString field.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstOperandReadState with operation saved in historyString and
     * result of implemented operation placed in mainString field.
     */
    override fun minutesToDecimal(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.decimalMinutes(DEG_FUNCTION_CODE),
                historyString = "${scientificCalculatorDataEntity.historyString}deg(" +
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
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(HYPERBOLIC_COSINE_FUNCTION_CODE),
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
                ScientificCalculatorDataEntity(
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
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(HYPERBOLIC_ARC_COSINE_FUNCTION_CODE),
                    historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
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
            DEGREES_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                .highPrecisionConvert(DEGREES_TO_RADIANS_FUNCTION_CODE)
                .mathFunction(COSINE_FUNCTION_CODE)
            RADIANS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                .mathFunction(COSINE_FUNCTION_CODE)
            GRADS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                .highPrecisionConvert(GRADS_TO_RADIANS_FUNCTION_CODE)
                .mathFunction(COSINE_FUNCTION_CODE)
            else -> scientificCalculatorDataEntity.mainString
                .mathFunction(COSINE_FUNCTION_CODE)
        }
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "cosd"
            RADIANS_ANGLE_CODE -> "cosr"
            GRADS_ANGLE_CODE -> "cosg"
            else -> "cosd"
        }

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = result,
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
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "acosd"
            RADIANS_ANGLE_CODE -> "acosr"
            GRADS_ANGLE_CODE -> "acosg"
            else -> "acosd"
        }
        return try {
            val result = when(angleUnitCode) {
                DEGREES_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                    .highPrecisionMathFunction(ARC_COSINE_FUNCTION_CODE)
                    .convert(RADIANS_TO_DEGREES_FUNCTION_CODE)
                RADIANS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString.mathFunction(ARC_COSINE_FUNCTION_CODE)
                GRADS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                    .highPrecisionMathFunction(ARC_COSINE_FUNCTION_CODE)
                    .convert(RADIANS_TO_GRADS_FUNCTION_CODE)
                else -> scientificCalculatorDataEntity.mainString
                    .highPrecisionMathFunction(ARC_COSINE_FUNCTION_CODE)
                    .convert(RADIANS_TO_DEGREES_FUNCTION_CODE)
            }
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = result,
                    historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                scientificCalculatorDataEntity.copy(
                    historyString = "${scientificCalculatorDataEntity.historyString}acosh(" +
                            "${scientificCalculatorDataEntity.mainString})",
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
        val historyString = scientificCalculatorDataEntity.historyString +
                scientificCalculatorDataEntity.mainString.commaTruncate() +
                HISTORY_STRING_SPACE_LETTER +
                operationString

        return ScientificCalculatorMathOperationState(
            scientificCalculatorDataEntity.copy(
                historyString = historyString,
                scientificOperationType = scientificOperationType,
                operand = scientificCalculatorDataEntity.mainString
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
                mainString = piNumber()
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
                mainString = doublePiNumber()
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
                mainString = scientificCalculatorDataEntity.mainString.mathFunction(HYPERBOLIC_TANGENT_FUNCTION_CODE),
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
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(HYPERBOLIC_ARC_TANGENT_FUNCTION_CODE),
                    historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}atanh(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
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

        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "tand"
            RADIANS_ANGLE_CODE -> "tanr"
            GRADS_ANGLE_CODE -> "tang"
            else -> "tand"
        }

        return try {
            val angleInRadians = when(angleUnitCode) {
                DEGREES_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                    .convert(DEGREES_TO_RADIANS_FUNCTION_CODE)
                    //.highPrecisionMathFunction(TANGENT_FUNCTION_CODE)
/*                RADIANS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                    .mathFunction(TANGENT_FUNCTION_CODE)*/
                GRADS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                    .convert(GRADS_TO_RADIANS_FUNCTION_CODE)
                    //.mathFunction(TANGENT_FUNCTION_CODE)
                else -> scientificCalculatorDataEntity.mainString
                    //.mathFunction(TANGENT_FUNCTION_CODE)
            }
            val sin = angleInRadians.mathFunction(SINUS_FUNCTION_CODE)
            val cos = angleInRadians.mathFunction(COSINE_FUNCTION_CODE)
            val result = sin.divide(cos)

            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = result,
                    historyString = scientificCalculatorDataEntity.historyString +
                            "$operationString(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = scientificCalculatorDataEntity.historyString +
                            "$operationString(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        } catch (exception: Exception){
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = scientificCalculatorDataEntity.historyString +
                            "$operationString(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }

    }

    /**
     * Calculates arctangent of number from mainString field of scientificCalculatorDataEntity.
     * mainString field contains String type variable, therefore it converts to Double type,
     * make calculation, convert it to String and write it back to mainString field.
     * Result number depends on required angle units(degrees, radians or grads). Required
     * angle units defined by angleUnitCode parameter.
     * Symbol of completed operation appended to historyString field of scientificCalculatorDataEntity.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @param angleUnitCode - contains code for required angle units.
     * @return ScientificCalculatorFirstOperandReadState with changed
     * mainString and historyString fields of scientificCalculatorDataEntity field.
     */
    override fun arcTangent(
        scientificCalculatorDataEntity: ScientificCalculatorDataEntity,
        angleUnitCode: Int
    ): CalculatorState {

        val result = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                .convert(DEGREES_TO_RADIANS_FUNCTION_CODE)
                .mathFunction(ARC_TANGENT_FUNCTION_CODE)
            RADIANS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                .mathFunction(ARC_TANGENT_FUNCTION_CODE)
            GRADS_ANGLE_CODE -> scientificCalculatorDataEntity.mainString
                .convert(GRADS_TO_RADIANS_FUNCTION_CODE)
                .mathFunction(ARC_TANGENT_FUNCTION_CODE)
            else -> scientificCalculatorDataEntity.mainString
                .mathFunction(ARC_TANGENT_FUNCTION_CODE)
        }
        val operationString = when(angleUnitCode) {
            DEGREES_ANGLE_CODE -> "atand"
            RADIANS_ANGLE_CODE -> "atanr"
            GRADS_ANGLE_CODE -> "atang"
            else -> "atand"
        }

        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = result,
                historyString = "${scientificCalculatorDataEntity.historyString}$operationString(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Calculates third power of number placed in mainString field of scientificCalculatorDataEntity.
     * mainString field contains String type variable, therefore it converts to Double type,
     * make calculation, convert it to String and write it back to mainString field.
     * Symbol of completed operation appended to historyString field of scientificCalculatorDataEntity.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return
     * - ScientificCalculatorFirstOperandReadState with changed
     * mainString and historyString fields of scientificCalculatorDataEntity field if operation
     * completed successfully.
     * - ScientificCalculatorErrorState if operation completed with error(overflow or other). Error code placed
     * to errorCode field of new created scientificCalculatorDataEntity, where historyString
     * remain the same, but appended by symbol of operation.
     *
     */
    override fun cubeNumber(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = scientificCalculatorDataEntity.mainString.powerOfNumber("3"),
                    historyString = "${scientificCalculatorDataEntity.historyString}cube(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}cube(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}cube(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }

    /**
     * Calculates cube root of number placed in mainString field of scientificCalculatorDataEntity.
     * mainString field contains String type variable, therefore it converts to Double type,
     * make calculation, convert it to String and write it back to mainString field.
     * Symbol of completed operation appended to historyString field of scientificCalculatorDataEntity.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return
     * - ScientificCalculatorFirstOperandReadState with changed
     * mainString and historyString fields of scientificCalculatorDataEntity field if operation
     * completed successfully.
     * - ScientificCalculatorErrorState if operation completed with error(overflow or other). Error code placed
     * to errorCode field of new created scientificCalculatorDataEntity, where historyString
     * remain the same, but appended by symbol of operation.
     *
     */
    override fun cubeRoot(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return ScientificCalculatorFirstOperandReadState(
            scientificCalculatorDataEntity.copy(
                mainString = scientificCalculatorDataEntity.mainString.rootOfNumber("3"),
                historyString = "${scientificCalculatorDataEntity.historyString}cuberoot(" +
                        "${scientificCalculatorDataEntity.mainString})"
            )
        )
    }

    /**
     * Changed boolean isScientificNotation to opposite value and according to it
     * converts string with double number placed in mainString field of scientificCalculatorData from
     * plain string format to scientific notation format or vice versa. Result of conversion placed to the same field.
     * @param scientificCalculatorDataEntity - contains current calculator data
     * @return ScientificCalculatorFirstInputReadState with converted mainString field of scientificCalculatorDataEntity
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
     * Changed mainString field of scientificCalculatorDataEntity parameter by adding
     * to string additional symbols, transform it from conventional format of displayed number
     * to format with power (exponential) part(looks like (base),e+(power)). Changed parameter
     * writes to returned state instance.
     * @param - scientificCalculatorDataEntity contains current calculator data
     * @return - ScientificCalculatorFirstOperandPowerNumberInputState with changed scientificCalculatorDataEntity
     * parameter as constructor.
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
     * Takes number by converting mainString field of scientificCalculatorDataEntity
     * parameter to double and calculates logarithm base ten of that number. Result number
     * converts to string and writes it to mainString field of variable get by copying
     * scientificCalculatorDataEntity parameter. Symbol of operation appended to historyString
     * field and writes to historyString newly created variable. If calculation of logarithm
     * completed with error, creates new variable of ScientificCalculatorDataEntity type,
     * writes to historyString symbol of operation appended to historyString field of
     * scientificCalculatorDataEntity parameter and error code number to errorCode field.
     * Newly created var writes to returned state instance.
     * @param - scientificCalculatorDataEntity contains current calculator data
     * @return - ScientificCalculatorFirstOperandPowerNumberInputState with changed scientificCalculatorDataEntity
     * parameter as constructor.
     * - ScientificCalculatorErrorState if calculation completed with error.
     */
    override fun logarithmBaseTen(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                ScientificCalculatorDataEntity(
                    mainString = scientificCalculatorDataEntity.mainString.mathFunction(LOGARITHM_BASE_10_FUNCTION_CODE),
                    historyString = "${scientificCalculatorDataEntity.historyString}log(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}log(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = INVALID_INPUT_ERROR_CODE
                )
            )
        }
    }

    /**
     * Takes number by converting mainString field of scientificCalculatorDataEntity
     * parameter to double and calculates result got from raising 10 to power of number.
     * Result number converts to string and writes it to mainString field of variable get by copying
     * scientificCalculatorDataEntity parameter. Symbol of operation appended to historyString
     * field of param and writes to historyString newly created variable.
     * @param - scientificCalculatorDataEntity contains current calculator data
     * @return - ScientificCalculatorFirstOperandReadState with changed scientificCalculatorDataEntity
     * parameter as constructor.
     */
    override fun tenPowerX(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): CalculatorState {
        return try {
            ScientificCalculatorFirstOperandReadState(
                scientificCalculatorDataEntity.copy(
                    mainString = "10".powerOfNumber(scientificCalculatorDataEntity.mainString),
                    historyString = "${scientificCalculatorDataEntity.historyString}10^(" +
                            "${scientificCalculatorDataEntity.mainString})"
                )
            )
        } catch (arithmeticException: ArithmeticException) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}10^(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = OVERFLOW_ERROR_CODE
                )
            )
        } catch (exception: Exception) {
            ScientificCalculatorErrorState(
                ScientificCalculatorDataEntity(
                    historyString = "${scientificCalculatorDataEntity.historyString}10^(" +
                            "${scientificCalculatorDataEntity.mainString})",
                    errorCode = UNKNOWN_ERROR_CODE
                )
            )
        }
    }
}