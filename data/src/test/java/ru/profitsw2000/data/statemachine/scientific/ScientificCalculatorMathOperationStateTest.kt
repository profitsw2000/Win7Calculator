package ru.profitsw2000.data.statemachine.scientific
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.internal.matchers.Equals
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import ru.profitsw2000.data.constants.DEGREES_ANGLE_CODE
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.GRADS_ANGLE_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.OVERFLOW_ERROR_CODE
import ru.profitsw2000.data.constants.RADIANS_ANGLE_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorErrorState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandInputState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandPowerNumberInputState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandReadState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorInitialState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorMathOperationState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorOperationResultState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorSecondOperandInputState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorSecondOperandPowerNumberInputState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorSecondOperandReadState

class ScientificCalculatorMathOperationStateTest {
    val baseSCMORS = ScientificCalculatorMathOperationState(
        ScientificCalculatorDataEntity()
    )

    val prevState = ScientificCalculatorMathOperationState(
        ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5"
        )
    )

    @Test
    fun historyStringInsertionTest(){
        val negOperation = "negate"
        val sqrtOperation = "sqrt"

        val simpleHistoryString = "33$HISTORY_STRING_SPACE_LETTER+"
        val simpleHistoryStringNegate = "33${HISTORY_STRING_SPACE_LETTER}+${HISTORY_STRING_SPACE_LETTER}negate(5)"

        val historyString1 = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(33$HISTORY_STRING_SPACE_LETTER+"
        val historyString1Sqrt = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(33$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}sqrt(5)"

        val historyString9 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(" +
                "33$HISTORY_STRING_SPACE_LETTER+"
        val historyString9Sqrt = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(" +
                "33$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}sqrt(5)"


        assertEquals(
            simpleHistoryStringNegate,
            baseSCMORS.appendOperationString(
                ScientificCalculatorDataEntity(
                    mainString = "5",
                    historyString = simpleHistoryString
                ),
                negOperation)
        )

        assertEquals(
            historyString1Sqrt,
            baseSCMORS.appendOperationString(
                ScientificCalculatorDataEntity(
                    mainString = "5",
                    historyString = historyString1
                ),
                sqrtOperation)
        )

        assertEquals(
            historyString9Sqrt,
            baseSCMORS.appendOperationString(
                ScientificCalculatorDataEntity(
                    mainString = "5",
                    historyString = historyString9
                ),
                sqrtOperation)
        )
    }

    @Test
    fun allStatesCalculationTest(){

        val nullState = ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity()
        )
        val firstState = ScientificCalculatorMathOperationState(
            ScientificCalculatorDataEntity(
                mainString = "6",
                operand = "6",
                historyString = "(6$HISTORY_STRING_SPACE_LETTER+",
                scientificOperationType = ScientificOperationType.PLUS,
                prevState = nullState
            )
        )
        val secondState = ScientificCalculatorMathOperationState(
            ScientificCalculatorDataEntity(
                mainString = "5",
                historyString = "6$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER" +
                        "(5$HISTORY_STRING_SPACE_LETTER*",
                operand = "5",
                scientificOperationType = ScientificOperationType.MULTIPLY,
                prevState = firstState
            )
        )
        val thirdState = ScientificCalculatorMathOperationState(
            ScientificCalculatorDataEntity(
                mainString = "4",
                historyString = "6$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER" +
                        "(5$HISTORY_STRING_SPACE_LETTER*" +
                        "(4$HISTORY_STRING_SPACE_LETTER/",
                operand = "4",
                scientificOperationType = ScientificOperationType.DIVIDE,
                prevState = secondState
            )
        )
        val fourthState = ScientificCalculatorMathOperationState(
            ScientificCalculatorDataEntity(
                mainString = "22",
                historyString = "6$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER" +
                        "(5$HISTORY_STRING_SPACE_LETTER*" +
                        "(4$HISTORY_STRING_SPACE_LETTER/" +
                        "(22$HISTORY_STRING_SPACE_LETTER-",
                operand = "22",
                scientificOperationType = ScientificOperationType.MINUS,
                prevState = thirdState
            )
        )
        val fifthState = ScientificCalculatorMathOperationState(
            ScientificCalculatorDataEntity(
                mainString = "14",
                historyString = "6$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER" +
                        "(5$HISTORY_STRING_SPACE_LETTER*" +
                        "(4$HISTORY_STRING_SPACE_LETTER/" +
                        "(22$HISTORY_STRING_SPACE_LETTER-" +
                        "(14${HISTORY_STRING_SPACE_LETTER}mod",
                operand = "14",
                scientificOperationType = ScientificOperationType.MODULUS,
                prevState = fourthState
            )
        )
        val sixthState = ScientificCalculatorMathOperationState(
            ScientificCalculatorDataEntity(
                mainString = "2",
                historyString = "6$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER" +
                        "(5$HISTORY_STRING_SPACE_LETTER*" +
                        "(4$HISTORY_STRING_SPACE_LETTER/" +
                        "(22$HISTORY_STRING_SPACE_LETTER-" +
                        "(14${HISTORY_STRING_SPACE_LETTER}mod" +
                        "(2${HISTORY_STRING_SPACE_LETTER}root",
                operand = "2",
                scientificOperationType = ScientificOperationType.ROOT_OF,
                prevState = fifthState
            )
        )
        val seventhState = ScientificCalculatorMathOperationState(
            ScientificCalculatorDataEntity(
                mainString = "3",
                historyString = "6$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER" +
                        "(5$HISTORY_STRING_SPACE_LETTER*" +
                        "(4$HISTORY_STRING_SPACE_LETTER/" +
                        "(22$HISTORY_STRING_SPACE_LETTER-" +
                        "(14${HISTORY_STRING_SPACE_LETTER}mod" +
                        "(2${HISTORY_STRING_SPACE_LETTER}root" +
                        "(3${HISTORY_STRING_SPACE_LETTER}^",
                operand = "3",
                scientificOperationType = ScientificOperationType.POWER_OF,
                prevState = sixthState
            )
        )

        val resultString = "6,9372924057894815643192036113543"
        val resultString1 = "11"
        val resultString2 = "6,9090909090909090909090909090909"

        assertEquals(
            resultString,
            seventhState.getAllStatesCalculationResult(seventhState)
        )
        assertEquals(
            resultString1,
            thirdState.getAllStatesCalculationResult(thirdState)
        )
        assertEquals(
            resultString2,
            fifthState.getAllStatesCalculationResult(fifthState)
        )
    }

    @Test
    fun clearMemoryTest() {
        val numberInMemoryData = ScientificCalculatorDataEntity(
            mainString = "55",
            historyString = "55$HISTORY_STRING_SPACE_LETTER/",
            memoryNumber = "15"
        )
        val clearedMemoryData = ScientificCalculatorDataEntity(
            mainString = "55",
            historyString = "55$HISTORY_STRING_SPACE_LETTER/"
        )
        val numberInMemoryState = ScientificCalculatorMathOperationState(
            numberInMemoryData
        )
        val clearedMemoryState = ScientificCalculatorMathOperationState(
            clearedMemoryData
        )

        assertTrue(ReflectionEquals(clearedMemoryState).matches(
                numberInMemoryState.clearMemory(numberInMemoryData)
            )
        )
        assertFalse(ReflectionEquals(numberInMemoryState).matches(
                numberInMemoryState.clearMemory(numberInMemoryData)
            )
        )
    }

    @Test
    fun clearEnteredNumberTest() {
        val nonZeroData = ScientificCalculatorDataEntity(
            mainString = "6",
            historyString = "reciproc(reciproc(6))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val nonZeroState = ScientificCalculatorMathOperationState(nonZeroData)
        val nonZeroResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "reciproc(reciproc(6))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val nonZeroResultState = ScientificCalculatorSecondOperandInputState(nonZeroResultData)
        val historyData = ScientificCalculatorDataEntity(
            mainString = "6",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val historyState = ScientificCalculatorMathOperationState(historyData)
        val historyResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val historyResultState = ScientificCalculatorSecondOperandInputState(historyResultData)

        assertTrue(
            ReflectionEquals(nonZeroResultState).matches(
                nonZeroState.clearEntered(nonZeroData)
            ))
        assertTrue(
            ReflectionEquals(historyResultState).matches(
                historyState.clearEntered(historyData)
            ))
    }

    @Test
    fun readMemoryTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "234,543",
            historyString = "234,543$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            memoryNumber = "3,99"
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,99",
            historyString = "234,543$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            memoryNumber = "3,99"
        )
        val zeroInputResultState = ScientificCalculatorSecondOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+23",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            memoryNumber = "3,99",
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,99e+0",
            memoryNumber = "3,99",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorSecondOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.readMemory(zeroInputData)
            )
        )
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
                nonZeroInputState.readMemory(nonZeroInputData)
            )
        )
    }

    @Test
    fun saveToMemoryTest() {
        val zeroInputMemoryData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(0))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            memoryNumber = "2,35"
        )
        val zeroInputMemoryState = ScientificCalculatorMathOperationState(zeroInputMemoryData)
        val zeroInputMemoryResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(0))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            memoryNumber = null
        )
        val zeroInputMemoryResultState = ScientificCalculatorMathOperationState(zeroInputMemoryResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            memoryNumber = "2,35"
        )
        val nonZeroInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            memoryNumber = "2"
        )
        val nonZeroInputResultState = ScientificCalculatorMathOperationState(nonZeroInputResultData)

        assertTrue(
            ReflectionEquals(zeroInputMemoryResultState).matches(
                zeroInputMemoryState.saveToMemory(zeroInputMemoryData)
            ))
        assertTrue(
            ReflectionEquals(nonZeroInputResultState).matches(
                nonZeroInputState.saveToMemory(nonZeroInputData)
            ))
    }

    @Test
    fun addToMemoryTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sqrt(sqr(0))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sqrt(sqr(0))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS
        )
        val zeroInputResultState = ScientificCalculatorMathOperationState(zeroInputResultData)
        val zeroInputMemoryData = ScientificCalculatorDataEntity(
            mainString = "5,6",
            historyString = "sqrt(sqr(3,5))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            memoryNumber = "2,35"
        )
        val zeroInputMemoryState = ScientificCalculatorMathOperationState(zeroInputMemoryData)
        val zeroInputMemoryResultState = ScientificCalculatorMathOperationState(
            ScientificCalculatorDataEntity(
                mainString = "5,6",
                historyString = "sqrt(sqr(3,5))$HISTORY_STRING_SPACE_LETTER-",
                scientificOperationType = ScientificOperationType.MINUS,
                memoryNumber = "7,95"
            )
        )
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "3,523e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,523e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            memoryNumber = "3523000",
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorMathOperationState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.addNumberToMemory(zeroInputData)
            )
        )
        assertTrue(ReflectionEquals(zeroInputMemoryResultState).matches(
                zeroInputMemoryState.addNumberToMemory(zeroInputMemoryData)
            )
        )
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
                nonZeroInputState.addNumberToMemory(nonZeroInputData)
            )
        )
    }

    @Test
    fun subtractFromMemoryTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "0$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "0$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF
        )
        val zeroInputResultState = ScientificCalculatorMathOperationState(zeroInputResultData)
        val zeroInputMemoryData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            memoryNumber = "2,35"
        )
        val zeroInputMemoryState = ScientificCalculatorMathOperationState(zeroInputMemoryData)
        val zeroInputMemoryResultState = ScientificCalculatorMathOperationState(
            ScientificCalculatorDataEntity(
                mainString = "5",
                historyString = "5$HISTORY_STRING_SPACE_LETTER^",
                scientificOperationType = ScientificOperationType.POWER_OF,
                memoryNumber = "-2,65"
            )
        )
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "3,523e+4",
            historyString = "3,523e+4$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,523e+4",
            historyString = "3,523e+4$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            memoryNumber = "-35230",
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorMathOperationState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.subtractNumberFromMemory(zeroInputData)
            )
        )
        assertTrue(ReflectionEquals(zeroInputMemoryResultState).matches(
                zeroInputMemoryState.subtractNumberFromMemory(zeroInputMemoryData)
            )
        )
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
                nonZeroInputState.subtractNumberFromMemory(nonZeroInputData)
            )
        )
    }

    @Test
    fun changeSignTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "0$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "0$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}negate(0)",
            scientificOperationType = ScientificOperationType.MULTIPLY
        )
        val zeroInputResultState = ScientificCalculatorSecondOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "6,427",
            historyString = "6,427$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val nonZeroInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "-6,427",
            historyString = "6,427$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}negate(6,427)",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val nonZeroInputResultState = ScientificCalculatorSecondOperandReadState(nonZeroInputResultData)
        val nonZeroNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,68",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))$HISTORY_STRING_SPACE_LETTER/"
        )
        val nonZeroNegativeInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,68",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}negate(-5,68)"
        )
        val nonZeroNegativeInputResultState = ScientificCalculatorSecondOperandReadState(nonZeroNegativeInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.negateOperand(zeroInputData)
            )
        )
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
                nonZeroInputState.negateOperand(nonZeroInputData)
            )
        )
        assertTrue(ReflectionEquals(nonZeroNegativeInputResultState).matches(
                nonZeroNegativeInputState.negateOperand(nonZeroNegativeInputData)
            )
        )
    }

    @Test
    fun squareRootTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "0${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "0${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}sqrt(0)",
            scientificOperationType = ScientificOperationType.MODULUS
        )
        val zeroInputResultState = ScientificCalculatorSecondOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "9",
            historyString = "9${HISTORY_STRING_SPACE_LETTER}/",
            scientificOperationType = ScientificOperationType.DIVIDE
        )
        val nonZeroInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3",
            historyString = "9${HISTORY_STRING_SPACE_LETTER}/${HISTORY_STRING_SPACE_LETTER}sqrt(9)",
            scientificOperationType = ScientificOperationType.DIVIDE
        )
        val nonZeroInputResultState = ScientificCalculatorSecondOperandReadState(nonZeroInputResultData)
        val nonZeroNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,68e+0",
            historyString = "-5,68e+0$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            isScientificNotation = true
        )
        val nonZeroNegativeInputState = ScientificCalculatorMathOperationState(nonZeroNegativeInputData)
        val nonZeroNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-5,68e+0",
            historyString = "-5,68e+0$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}sqrt(-5,68e+0)",
            scientificOperationType = ScientificOperationType.MINUS,
            errorCode = INVALID_INPUT_ERROR_CODE,
            isScientificNotation = true
        )
        val nonZeroNegativeInputResultState = ScientificCalculatorErrorState(nonZeroNegativeInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.calculateSquareRoot(zeroInputData)
            )
        )
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
                nonZeroInputState.calculateSquareRoot(nonZeroInputData)
            )
        )
        assertTrue(ReflectionEquals(nonZeroNegativeInputResultState).matches(
                nonZeroNegativeInputState.calculateSquareRoot(nonZeroNegativeInputData)
            )
        )
    }

    @Test
    fun digitInputTest() {
        val zeroData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sqrt(0)$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS
        )
        val zeroState = ScientificCalculatorMathOperationState(zeroData)

        val zeroDataResult = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sqrt(0)$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS
        )
        val zeroStateResult = ScientificCalculatorSecondOperandInputState(zeroDataResult)

        val zeroCommaDataResult = ScientificCalculatorDataEntity(
            mainString = "0,",
            historyString = "sqrt(0)$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS
        )
        val zeroCommaStateResult = ScientificCalculatorSecondOperandInputState(zeroCommaDataResult)

        val nonZeroInputToZeroData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "sqrt(0)$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS
        )
        val nonZeroInputToZeroState = ScientificCalculatorSecondOperandInputState(nonZeroInputToZeroData)

        val nonZeroData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(2$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val nonZeroState = ScientificCalculatorMathOperationState(nonZeroData)
        val zeroInputToNonZeroData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(2$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val zeroInputToNonZeroState = ScientificCalculatorSecondOperandInputState(zeroInputToNonZeroData)
        val nonZeroInputToNonZeroData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(2$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val nonZeroInputToNonZeroState = ScientificCalculatorSecondOperandInputState(nonZeroInputToNonZeroData)
        val commaInputToNonZeroData = ScientificCalculatorDataEntity(
            mainString = "0,",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(2$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val commaInputToNonZeroState = ScientificCalculatorSecondOperandInputState(commaInputToNonZeroData)

        assertTrue(ReflectionEquals(zeroStateResult).matches(
                zeroState.inputDigit(zeroData, "0")
            )
        )
        assertTrue(ReflectionEquals(zeroCommaStateResult).matches(
                zeroState.inputDigit(zeroData, ",")
            )
        )
        assertTrue(ReflectionEquals(nonZeroInputToZeroState).matches(
                zeroState.inputDigit(zeroData, "5")
            )
        )
        assertTrue(ReflectionEquals(zeroInputToNonZeroState).matches(
                nonZeroState.inputDigit(nonZeroData, "0")
            )
        )
        assertTrue(ReflectionEquals(nonZeroInputToNonZeroState).matches(
                nonZeroState.inputDigit(nonZeroData, "5")
            )
        )
        assertTrue(ReflectionEquals(commaInputToNonZeroState).matches(
                nonZeroState.inputDigit(nonZeroData, ",")
            )
        )
    }

    @Test
    fun mathOperationTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sqrt(0)$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "0"
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sqrt(0)$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "0"
        )
        val zeroInputResultState = ScientificCalculatorMathOperationState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "49,8",
            historyString = "sqrt(2500)$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "49,8"
        )
        val nonZeroInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "49,8",
            historyString = "sqrt(2500)$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "49,8"
        )
        val nonZeroInputResultState = ScientificCalculatorMathOperationState(nonZeroInputResultData)

        val nonZeroCommaInputData = ScientificCalculatorDataEntity(
            mainString = "-4",
            historyString = "negate(sqrt(sqrt(64)))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-4"
        )
        val nonZeroCommaInputState = ScientificCalculatorMathOperationState(nonZeroCommaInputData)
        val nonZeroCommaInputResultData = ScientificCalculatorDataEntity(
            mainString = "-4",
            historyString = "negate(sqrt(sqrt(64)))$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-4"
        )
        val nonZeroCommaInputResultState = ScientificCalculatorMathOperationState(nonZeroCommaInputResultData)

        val nonZeroCommaInputDataSN = ScientificCalculatorDataEntity(
            mainString = "4,98e+3",
            historyString = "4,98e+3${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "4980",
            isScientificNotation = true
        )
        val nonZeroCommaInputStateSN = ScientificCalculatorMathOperationState(nonZeroCommaInputDataSN)
        val nonZeroCommaInputResultDataSN = ScientificCalculatorDataEntity(
            mainString = "4,98e+3",
            historyString = "4,98e+3$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "4980",
            isScientificNotation = true
        )
        val nonZeroCommaInputResultStateSN = ScientificCalculatorMathOperationState(nonZeroCommaInputResultDataSN)

        val prevInputData = ScientificCalculatorDataEntity(
            mainString = "56",
            historyString = "0$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(negate(-5))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "56",
            prevState = zeroInputResultState
        )
        val prevInputState = ScientificCalculatorMathOperationState(prevInputData)
        val prevInputResultData = ScientificCalculatorDataEntity(
            mainString = "56",
            historyString = "0$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(negate(-5))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "56",
            prevState = zeroInputResultState
        )
        val prevInputResultState = ScientificCalculatorMathOperationState(prevInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.primitiveMathOperation(zeroInputData, ScientificOperationType.MINUS, "-")
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.primitiveMathOperation(nonZeroInputData, ScientificOperationType.MULTIPLY, "*")
        ))
        assertTrue(ReflectionEquals(nonZeroCommaInputResultState).matches(
            nonZeroCommaInputState.primitiveMathOperation(nonZeroCommaInputData, ScientificOperationType.POWER_OF, "^")
        ))
        assertTrue(ReflectionEquals(nonZeroCommaInputResultStateSN).matches(
            nonZeroCommaInputStateSN.primitiveMathOperation(nonZeroCommaInputDataSN, ScientificOperationType.DIVIDE, "/")
        ))
        assertTrue(ReflectionEquals(prevInputResultState).matches(
            prevInputState.primitiveMathOperation(prevInputData, ScientificOperationType.PLUS, "+")
        ))
    }

    @Test
    fun reciprocationTest() {
        val zeroErrorInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(0)$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "0"
        )
        val zeroErrorInputState = ScientificCalculatorMathOperationState(zeroErrorInputData)
        val zeroErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(0)$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}reciproc(0)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            errorCode = DIVIDE_ON_ZERO_ERROR_CODE,
            operand = "0"
        )
        val zeroErrorInputResultState = ScientificCalculatorErrorState(zeroErrorInputResultData)

        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "2,5e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(-25)${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "25",
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "4,e-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(" +
                    "negate(-25)${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}reciproc(2,5e+1)",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "25",
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorSecondOperandReadState(nonZeroInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-12,5",
            historyString = "negate(12,5)$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-12,5"
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,08",
            historyString = "negate(12,5)$HISTORY_STRING_SPACE_LETTER^${HISTORY_STRING_SPACE_LETTER}reciproc(-12,5)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-12,5"
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(zeroErrorInputResultState).matches(
            zeroErrorInputState.reciprocOperation(zeroErrorInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.reciprocOperation(nonZeroInputData)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.reciprocOperation(negativeInputData)
        ))
    }
/*
    @Test
    fun openBracketTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((",
            prevState = zeroInputState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "2,3e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandReadState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,3e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((",
            prevState = nonZeroInputState,
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "6,35"
        )
        val commaInputState = ScientificCalculatorFirstOperandReadState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,35",
            historyString = "(",
            prevState = commaInputState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.openBracket(zeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.openBracket(nonZeroInputData)
        ))
        assertTrue(ReflectionEquals(commaInputResultState).matches(
            commaInputState.openBracket(commaInputData)
        ))
    }

    @Test
    fun closeBracketTest() {
        val nullPrevData = ScientificCalculatorDataEntity(
            mainString = "3,3e+4",
            isScientificNotation = true
        )
        val nullPrevState = ScientificCalculatorFirstOperandPowerNumberInputState(nullPrevData)
        val nullPrevResultData = ScientificCalculatorDataEntity(
            mainString = "3,3e+4",
            isScientificNotation = true
        )
        val nullPrevResultState = ScientificCalculatorFirstOperandReadState(nullPrevResultData)

        val prevSCFOISData = ScientificCalculatorDataEntity(
            mainString = "5"
        )
        val prevSCFOISState = ScientificCalculatorFirstOperandInputState(prevSCFOISData)
        val currentSCFORSData = ScientificCalculatorDataEntity(
            mainString = "3,34e+3",
            historyString = "(",
            memoryNumber = "21,564",
            prevState = prevSCFOISState,
            isScientificNotation = true
        )
        val currentSCFORSState = ScientificCalculatorFirstOperandInputState(currentSCFORSData)
        val resultSCFORSData = ScientificCalculatorDataEntity(
            mainString = "3,34e+3",
            historyString = "(3,34e+3)",
            memoryNumber = "21,564",
            isScientificNotation = true
        )
        val resultSCFORSState = ScientificCalculatorFirstOperandReadState(resultSCFORSData)


        val prevSCFOPNISData = ScientificCalculatorDataEntity(
            mainString = "5,e+3"
        )
        val prevSCFOPNISState = ScientificCalculatorFirstOperandPowerNumberInputState(prevSCFOPNISData)
        val currentSCFOPNISData = currentSCFORSData.copy(
            prevState = prevSCFOPNISState
        )
        val currentSCFOPNISState = ScientificCalculatorFirstOperandReadState(currentSCFOPNISData)
        val resultSCFOPNISState = ScientificCalculatorFirstOperandReadState(resultSCFORSData)


        val prevSCFORSData = ScientificCalculatorDataEntity(
            mainString = "5"
        )
        val prevSCFORSState = ScientificCalculatorFirstOperandReadState(prevSCFORSData)
        val currentSCFORSData2 = currentSCFORSData.copy(
            prevState = prevSCFORSState
        )
        val currentSCFORSState2 = ScientificCalculatorFirstOperandReadState(currentSCFORSData2)
        val resultSCFORSState2 = ScientificCalculatorFirstOperandReadState(resultSCFORSData)

        val prevSCISData = ScientificCalculatorDataEntity(
            mainString = "0"
        )
        val prevSCISState = ScientificCalculatorInitialState(prevSCISData)
        val currentSCISData = currentSCFORSData.copy(
            prevState = prevSCISState
        )
        val currentSCISState = ScientificCalculatorFirstOperandReadState(currentSCISData)
        val resultSCISState = ScientificCalculatorFirstOperandReadState(resultSCFORSData)


        val prevSCORSData = ScientificCalculatorDataEntity(
            mainString = "123"
        )
        val prevSCORSState = ScientificCalculatorOperationResultState(prevSCORSData)
        val currentSCORSData = currentSCFORSData.copy(
            prevState = prevSCORSState
        )
        val currentSCORSState = ScientificCalculatorFirstOperandReadState(currentSCORSData)
        val resultSCORSState = ScientificCalculatorFirstOperandReadState(resultSCFORSData)

        val prevSCMOSData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+"
        )
        val prevSCMOSState = ScientificCalculatorMathOperationState(prevSCMOSData)
        val currentSCMOSData = currentSCFORSData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevSCMOSState
        )
        val currentSCMOSState = ScientificCalculatorFirstOperandReadState(currentSCMOSData)
        val resultSCMOSData = resultSCFORSData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(3,34e+3)"
        )
        val resultSCMOSState = ScientificCalculatorSecondOperandReadState(resultSCMOSData)

        val prevSCSOISData = ScientificCalculatorDataEntity(
            mainString = "123,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER"
        )
        val prevSCSOISState = ScientificCalculatorSecondOperandInputState(prevSCSOISData)
        val currentSCSOISData = currentSCFORSData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevSCSOISState
        )
        val currentSCSOISState = ScientificCalculatorFirstOperandReadState(currentSCSOISData)
        val resultSCSOISData = resultSCFORSData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(3,34e+3)"
        )
        val resultSCSOISState = ScientificCalculatorSecondOperandReadState(resultSCSOISData)

        val prevSCSOPNISData = ScientificCalculatorDataEntity(
            mainString = "5,e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER"
        )
        val prevSCSOPNISState = ScientificCalculatorSecondOperandPowerNumberInputState(prevSCSOPNISData)
        val currentSCSOPNISData = currentSCFORSData.copy(
            historyString = "5,e+3$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevSCSOPNISState
        )
        val currentSCSOPNISState = ScientificCalculatorFirstOperandReadState(currentSCSOPNISData)
        val resultSCSOPNISData = resultSCFORSData.copy(
            historyString = "5,e+3$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(3,34e+3)"
        )
        val resultSCSOPNISState = ScientificCalculatorSecondOperandReadState(resultSCSOPNISData)


        val prevSCSORSData = ScientificCalculatorDataEntity(
            mainString = "678",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER"
        )
        val prevSCSORSState = ScientificCalculatorSecondOperandReadState(prevSCSORSData)
        val currentSCSORSData = currentSCFORSData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevSCSORSState
        )
        val currentSCSORSState = ScientificCalculatorFirstOperandReadState(currentSCSORSData)
        val resultSCSORSData = resultSCFORSData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(3,34e+3)"
        )
        val resultSCSORSState = ScientificCalculatorSecondOperandReadState(resultSCSORSData)

        assertTrue(ReflectionEquals(nullPrevResultState).matches(
            nullPrevState.closeBracket(nullPrevData)
        ))
        assertTrue(ReflectionEquals(resultSCFORSState).matches(
            currentSCFORSState.closeBracket(currentSCFORSData)
        ))
        assertTrue(ReflectionEquals(resultSCFOPNISState).matches(
            currentSCFOPNISState.closeBracket(currentSCFOPNISData)
        ))
        assertTrue(ReflectionEquals(resultSCFORSState2).matches(
            currentSCFORSState2.closeBracket(currentSCFORSData2)
        ))
        assertTrue(ReflectionEquals(resultSCISState).matches(
            currentSCISState.closeBracket(currentSCISData)
        ))
        assertTrue(ReflectionEquals(resultSCORSState).matches(
            currentSCORSState.closeBracket(currentSCORSData)
        ))
        assertTrue(ReflectionEquals(resultSCMOSState).matches(
            currentSCMOSState.closeBracket(currentSCMOSData)
        ))
        assertTrue(ReflectionEquals(resultSCSOISState).matches(
            currentSCSOISState.closeBracket(currentSCSOISData)
        ))
        assertTrue(ReflectionEquals(resultSCSOPNISState).matches(
            currentSCSOPNISState.closeBracket(currentSCSOPNISData)
        ))
        assertTrue(ReflectionEquals(resultSCSORSState).matches(
            currentSCSORSState.closeBracket(currentSCSORSData)
        ))
    }

    @Test
    fun naturalLogarithmTest() {
        val negativeNumberData = ScientificCalculatorDataEntity(
            mainString = "-1,2e-1",
            memoryNumber = "-0,12",
            isScientificNotation = true
        )
        val negativeNumberState = ScientificCalculatorFirstOperandReadState(negativeNumberData)
        val negativeNumberResultData = ScientificCalculatorDataEntity(
            mainString = "-1,2e-1",
            historyString = "ln(-1,2e-1)",
            memoryNumber = "-0,12",
            errorCode = INVALID_INPUT_ERROR_CODE,
            isScientificNotation = true
        )
        val negativeNumberResultState = ScientificCalculatorErrorState(negativeNumberResultData)

        val zeroNumberData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "negate(sqrt(0))",
            memoryNumber = "2,3"
        )
        val zeroNumberState = ScientificCalculatorFirstOperandReadState(zeroNumberData)
        val zeroNumberResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "ln(negate(sqrt(0)))",
            memoryNumber = "2,3",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val zeroNumberResultState = ScientificCalculatorErrorState(zeroNumberResultData)

        val positiveNumberData = ScientificCalculatorDataEntity(
            mainString = "6,5e+3",
            historyString = "negate(negate(6,5e+3))",
            memoryNumber = "2,3",
            isScientificNotation = true
        )
        val positiveNumberState = ScientificCalculatorFirstOperandReadState(positiveNumberData)
        val positiveNumberResultData = ScientificCalculatorDataEntity(
            mainString = "8,7795574558837284786902296841602e+0",
            historyString = "ln(negate(negate(6,5e+3)))",
            memoryNumber = "2,3",
            isScientificNotation = true
        )
        val positiveNumberResultState = ScientificCalculatorFirstOperandReadState(positiveNumberResultData)

        val firstState = ScientificCalculatorMathOperationState(ScientificCalculatorDataEntity())
        val prevData = ScientificCalculatorDataEntity(
            mainString = "123000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(15129000000)",
            prevState = firstState
        )
        val prevState = ScientificCalculatorFirstOperandReadState(prevData)
        val prevResultData = ScientificCalculatorDataEntity(
            mainString = "11,719939634354554547315982974013",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(ln(sqrt(15129000000))",
            prevState = firstState
        )
        val prevResultState = ScientificCalculatorFirstOperandReadState(prevResultData)

        assertTrue(ReflectionEquals(negativeNumberResultState).matches(
            negativeNumberState.calculateNaturalLogarithm(negativeNumberData)
        ))
        assertTrue(ReflectionEquals(zeroNumberResultState).matches(
            zeroNumberState.calculateNaturalLogarithm(zeroNumberData)
        ))
        assertTrue(ReflectionEquals(positiveNumberResultState).matches(
            positiveNumberState.calculateNaturalLogarithm(positiveNumberData)
        ))
        assertTrue(ReflectionEquals(prevResultState).matches(
            prevState.calculateNaturalLogarithm(prevData)
        ))
    }

    @Test
    fun exponentTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,33e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(-3,33e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,8973852666366134260275960952126e+14",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(negate(-3,33e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val bigPositiveInputData = ScientificCalculatorDataEntity(
            mainString = "9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(9,9999e+12)",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigPositiveInputState = ScientificCalculatorFirstOperandReadState(bigPositiveInputData)
        val bigPositiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(sqrt(9,9999e+12))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val bigPositiveInputResultState = ScientificCalculatorErrorState(bigPositiveInputResultData)

        val bigNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(9,9999e+6)",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigNegativeInputState = ScientificCalculatorFirstOperandReadState(bigNegativeInputData)
        val bigNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(negate(9,9999e+6))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val bigNegativeInputResultState = ScientificCalculatorErrorState(bigNegativeInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.calculateExponent(zeroInputData)
        ))
        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.calculateExponent(positiveInputData)
        ))
        assertTrue(ReflectionEquals(bigPositiveInputResultState).matches(
            bigPositiveInputState.calculateExponent(bigPositiveInputData)
        ))
        assertTrue(ReflectionEquals(bigNegativeInputResultState).matches(
            bigNegativeInputState.calculateExponent(bigNegativeInputData)
        ))
    }

    @Test
    fun integerOfNumberTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,3452e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,3452e+2)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-3,34e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(Int(negate(3,3452e+2))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val posInputData = ScientificCalculatorDataEntity(
            mainString = "3,3452e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(negate(negate(2323,323e+10)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val posInputState = ScientificCalculatorFirstOperandReadState(posInputData)
        val posInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,3452e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(Int(sqrt(negate(negate(2323,323e+10))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val posInputResultState = ScientificCalculatorFirstOperandReadState(posInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "33,66",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(negate(sqrt(1923,37)))",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandReadState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "33",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(Int(reciproc(negate(sqrt(1923,37))))",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)


        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.integerOfNumber(negativeInputData)
        ))
        assertTrue(ReflectionEquals(posInputResultState).matches(
            posInputState.integerOfNumber(posInputData)
        ))
        assertTrue(ReflectionEquals(commaInputResultState).matches(
            commaInputState.integerOfNumber(commaInputData)
        ))
    }

    @Test
    fun fractionOfNumberTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,3452e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,3452e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-4,52e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(frac(negate(3,3452e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "33,55",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(negate(sqrt(1234)))",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandReadState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,55",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(frac(negate(negate(sqrt(1234))))",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)


        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.fractionOfNumber(negativeInputData)
        ))
        assertTrue(ReflectionEquals(commaInputResultState).matches(
            commaInputState.fractionOfNumber(commaInputData)
        ))
    }

    @Test
    fun hyperbolicSinusCalculationTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,2e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,2e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-3,9481480091340347580489011311222e+13",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinh(negate(3,2e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-0,3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqrt(0,09))",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandReadState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,3045202934471426189584352670051",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinh(negate(sqrt(0,09)))",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "999999",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(999999))",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandReadState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "999999",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinh(reciproc(reciproc(999999)))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorErrorState(bigInputResultData)

        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.hyperbolicSinus(negativeInputData)
        ))
        assertTrue(ReflectionEquals(commaInputResultState).matches(
            commaInputState.hyperbolicSinus(commaInputData)
        ))
        assertTrue(ReflectionEquals(bigInputResultState).matches(
            bigInputState.hyperbolicSinus(bigInputData)
        ))
    }

    @Test
    fun hyperbolicArcSinusTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,2e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,8798635843969098368386569906444e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((asinh(-3,2e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-30",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((((negate(30)",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandReadState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "-4,0946222243305305699593547694559",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((((asinh(negate(30))",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(reciproc(negate(reciproc(9,99999e+5))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandReadState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,4508656738523969413691847922481e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((asinh(negate(reciproc(negate(reciproc(9,99999e+5)))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorFirstOperandReadState(bigInputResultData)

        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.hyperbolicArcSinus(negativeInputData)
            ))
        assertTrue(
            ReflectionEquals(commaInputResultState).matches(
                commaInputState.hyperbolicArcSinus(commaInputData)
            ))
        assertTrue(
            ReflectionEquals(bigInputResultState).matches(
                bigInputState.hyperbolicArcSinus(bigInputData)
            ))
    }

    @Test
    fun degreesSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "7,0e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,3969262078590838405410927732473e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sind(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(70)",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,93969262078590838405410927732473",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sind(negate(70))",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqrt(0))",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sind(negate(sqrt(0)))",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "30",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandReadState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "sind(30)",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "90",
            historyString = "(((negate(-90)",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandReadState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "(((sind(negate(-90))",
            prevState = prevState
        )
        val ninetyInputResultState = ScientificCalculatorFirstOperandReadState(ninetyInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.sinus(positiveInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.sinus(negativeInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.sinus(zeroInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(thirtyInputResultState).matches(
                thirtyInputState.sinus(thirtyInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(ninetyInputResultState).matches(
                ninetyInputState.sinus(ninetyInputData, DEGREES_ANGLE_CODE)
            ))
    }

    @Test
    fun radiansSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "7,7389068155788909778733062514199e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sinr(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "negate(sqrt(4900))",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,77389068155788909778733062514199",
            historyString = "sinr(negate(sqrt(4900)))",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(0)",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sinr(sqrt(0))",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "0,52359877559829887307710723054658",
            historyString = "(((((",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandReadState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "(((((sinr(0,52359877559829887307710723054658)",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "1,5707963267948966192313216916398",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((reciproc(negate(2,2323928474))",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandReadState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sinr(reciproc(negate(2,2323928474)))",
            prevState = prevState
        )
        val ninetyInputResultState = ScientificCalculatorFirstOperandReadState(ninetyInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.sinus(positiveInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.sinus(negativeInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.sinus(zeroInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(thirtyInputResultState).matches(
                thirtyInputState.sinus(thirtyInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(ninetyInputResultState).matches(
                ninetyInputState.sinus(ninetyInputData, RADIANS_ANGLE_CODE)
            ))
    }

    @Test
    fun gradsSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "8,9100652418836786235970957141363e-1",
            historyString = "sing(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(70)",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,89100652418836786235970957141363",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sing(negate(70))",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "((",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "((sing(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "33,333333333333333333333333333333",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((reciproc(0,03)",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandReadState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sing(reciproc(0,03))",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "100",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(100))",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandReadState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sing(reciproc(reciproc(100)))",
            prevState = prevState
        )
        val ninetyInputResultState = ScientificCalculatorFirstOperandReadState(ninetyInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.sinus(positiveInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.sinus(negativeInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.sinus(zeroInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(thirtyInputResultState).matches(
                thirtyInputState.sinus(thirtyInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(ninetyInputResultState).matches(
                ninetyInputState.sinus(ninetyInputData, GRADS_ANGLE_CODE)
            ))
    }

    @Test
    fun degreesArcSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((asind(5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-30",
            historyString = "asind(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "110",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((reciproc(reciproc(110))",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandReadState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "110",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((asind(reciproc(reciproc(110)))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,e+2",
            historyString = "(((",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandReadState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,e+2",
            historyString = "(((asind(-2,e+2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(sqr(0))",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(sqrt(sqr(0)))",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandReadState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "90",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((asind(1)",
            prevState = prevState
        )
        val ninetyInputResultState = ScientificCalculatorFirstOperandReadState(ninetyInputResultData)


        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcSinus(positiveInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcSinus(negativeInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(positiveErrorInputResultState).matches(
                positiveErrorInputState.arcSinus(positiveErrorInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeErrorInputResultState).matches(
                negativeErrorInputState.arcSinus(negativeErrorInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.arcSinus(zeroInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(ninetyInputResultState).matches(
                ninetyInputState.arcSinus(ninetyInputData, DEGREES_ANGLE_CODE)
            ))
    }

    @Test
    fun radiansArcSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,2359877559829887307710723054658e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((asinr(5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,52359877559829887307710723054658",
            historyString = "asinr(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(negate(negate(1,21e+4)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandReadState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((asinr(sqrt(negate(negate(1,21e+4))))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-200",
            historyString = "(((negate(200)",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandReadState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-200",
            historyString = "(((asinr(negate(200))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcSinus(positiveInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcSinus(negativeInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(positiveErrorInputResultState).matches(
                positiveErrorInputState.arcSinus(positiveErrorInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeErrorInputResultState).matches(
                negativeErrorInputState.arcSinus(negativeErrorInputData, RADIANS_ANGLE_CODE)
            ))
    }

    @Test
    fun gradsArcSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,3333333333333333333333333333333e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((asing(5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,e-1",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-33,333333333333333333333333333333",
            historyString = "asing(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(negate(negate(121)))",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandReadState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((asing(sqrt(negate(negate(121))))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,e+1",
            historyString = "(((negate(negate(negate(20)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandReadState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,e+1",
            historyString = "(((asing(negate(negate(negate(20))))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcSinus(positiveInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcSinus(negativeInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(positiveErrorInputResultState).matches(
                positiveErrorInputState.arcSinus(positiveErrorInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeErrorInputResultState).matches(
                negativeErrorInputState.arcSinus(negativeErrorInputData, GRADS_ANGLE_CODE)
            ))
    }

    @Test
    fun squareNumberCalculationTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "6,6e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(reciproc(6,6e+3))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "4,356e+7",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqr(reciproc(reciproc(6,6e+3)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7700",
            historyString = "((",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "59290000",
            historyString = "((sqr(-7700)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+6000",
            historyString = "((sqr(1,e+3000)",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandReadState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e+6000",
            historyString = "((sqr(sqr(1,e+3000))",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.squareNumber(positiveInputData)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.squareNumber(negativeInputData)
        ))
        assertTrue(ReflectionEquals(positiveErrorInputResultState).matches(
            positiveErrorInputState.squareNumber(positiveErrorInputData)
        ))
    }

    @Test
    fun factorialTest() {
        val positiveIntInputData = ScientificCalculatorDataEntity(
            mainString = "6,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(-6,e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveIntInputState = ScientificCalculatorFirstOperandReadState(positiveIntInputData)
        val positiveIntInputResultData = ScientificCalculatorDataEntity(
            mainString = "7,2e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((fact(negate(-6,e+0))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveIntInputResultState = ScientificCalculatorFirstOperandReadState(positiveIntInputResultData)

        val positiveFractionInputData = ScientificCalculatorDataEntity(
            mainString = "5,6",
            historyString = "((sqrt(sqr(5,6)",
            prevState = prevState
        )
        val positiveFractionInputState = ScientificCalculatorFirstOperandReadState(positiveFractionInputData)
        val positiveFractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "344,70192403521989539187168914402",
            historyString = "((fact(sqrt(sqr(5,6))",
            prevState = prevState
        )
        val positiveFractionInputResultState = ScientificCalculatorFirstOperandReadState(positiveFractionInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(1)",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((fact(negate(1))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorErrorState(negativeInputResultData)

        val negativeFracInputData = ScientificCalculatorDataEntity(
            mainString = "-5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeFracInputState = ScientificCalculatorFirstOperandReadState(negativeFracInputData)
        val negativeFracInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,7724538509055160272981674833411e+0",
            historyString = "fact(-5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeFracInputResultState = ScientificCalculatorFirstOperandReadState(negativeFracInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+4",
            historyString = "((sqrt(sqr(negate(-1,e+4)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandReadState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e+4",
            historyString = "((fact(sqrt(sqr(negate(-1,e+4))))",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        assertTrue(ReflectionEquals(positiveIntInputResultState).matches(
            positiveIntInputState.factorial(positiveIntInputData)
        ))
        assertTrue(ReflectionEquals(positiveFractionInputResultState).matches(
            positiveFractionInputState.factorial(positiveFractionInputData)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.factorial(negativeInputData)
        ))
        assertTrue(ReflectionEquals(negativeFracInputResultState).matches(
            negativeFracInputState.factorial(negativeFracInputData)
        ))
        assertTrue(ReflectionEquals(positiveErrorInputResultState).matches(
            positiveErrorInputState.factorial(positiveErrorInputData)
        ))
    }

    @Test
    fun fromDecimalToMinutesFormatTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "6,4e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((negate(negate(6,4e+0))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,24e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((dms(negate(negate(6,4e+0)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.decimalToMinutes(positiveInputData)
            ))
    }

    @Test
    fun fromMinutesToDecimalFormatTest() {
        val underSixtyMinutesInputData = ScientificCalculatorDataEntity(
            mainString = "5,45",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(sqr(5,45))",
            prevState = prevState
        )
        val underSixtyMinutesInputState = ScientificCalculatorFirstOperandReadState(underSixtyMinutesInputData)
        val underSixtyMinutesInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,75",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(deg(sqrt(sqr(5,45)))",
            prevState = prevState
        )
        val underSixtyMinutesInputResultState = ScientificCalculatorFirstOperandReadState(underSixtyMinutesInputResultData)

        val overSixtyMinutesInputData = ScientificCalculatorDataEntity(
            mainString = "8,9e+0",
            historyString = "sqrt(sqr(reciproc(reciproc(8,9e+0))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val overSixtyMinutesInputState = ScientificCalculatorFirstOperandReadState(overSixtyMinutesInputData)
        val overSixtyMinutesInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,5e+0",
            historyString = "deg(sqrt(sqr(reciproc(reciproc(8,9e+0)))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val overSixtyMinutesInputResultState = ScientificCalculatorFirstOperandReadState(overSixtyMinutesInputResultData)

        assertTrue(
            ReflectionEquals(underSixtyMinutesInputResultState).matches(
                underSixtyMinutesInputState.minutesToDecimal(underSixtyMinutesInputData)
            ))
        assertTrue(
            ReflectionEquals(overSixtyMinutesInputResultState).matches(
                overSixtyMinutesInputState.minutesToDecimal(overSixtyMinutesInputData)
            ))
    }

    @Test
    fun hyperbolicCosineTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,2e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,2e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2286646200543857429363171495054e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosh(negate(3,2e+0))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,2",
            historyString = "(((",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "12,286646200543857429363171495054",
            historyString = "(((cosh(3,2)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(6)",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandReadState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "201,71563612245589448340511285541",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((cosh(negate(6))",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "sqr(reciproc(reciproc(3,333e+2)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandReadState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "cosh(sqr(reciproc(reciproc(3,333e+2))))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorErrorState(bigInputResultData)

        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.hyperbolicCosine(negativeInputData)
        ))
        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.hyperbolicCosine(positiveInputData)
        ))
        assertTrue(ReflectionEquals(commaInputResultState).matches(
            commaInputState.hyperbolicCosine(commaInputData)
        ))
        assertTrue(ReflectionEquals(bigInputResultState).matches(
            bigInputState.hyperbolicCosine(bigInputData)
        ))
    }

    @Test
    fun hyperbolicArcCosineTest() {
        val errorInputData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(2,e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val errorInputState = ScientificCalculatorFirstOperandReadState(errorInputData)
        val errorInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((acosh(reciproc(2,e+0))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val errorInputResultState = ScientificCalculatorErrorState(errorInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "3",
            historyString = "((negate(-3)",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandReadState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,7627471740390860504652186499596",
            historyString = "((acosh(negate(-3))",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "sqr(sqr(1,66e+2))",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandReadState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,4508656738523469412691846422479e+1",
            historyString = "acosh(sqr(sqr(1,66e+2)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorFirstOperandReadState(bigInputResultData)

        assertTrue(
            ReflectionEquals(errorInputResultState).matches(
                errorInputState.hyperbolicArcCosine(errorInputData)
            ))
        assertTrue(
            ReflectionEquals(commaInputResultState).matches(
                commaInputState.hyperbolicArcCosine(commaInputData)
            ))
        assertTrue(
            ReflectionEquals(bigInputResultState).matches(
                bigInputState.hyperbolicArcCosine(bigInputData)
            ))
    }

    @Test
    fun degreesCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(sqr(7,e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,4202014332566873304409961468226e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((cosd(sqrt(sqr(7,e+1)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "negate(70)",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,34202014332566873304409961468226",
            historyString = "cosd(negate(70))",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "(sqr(sqr(sqrt(0)))",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "(cosd(sqr(sqr(sqrt(0))))",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "60",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(3600)",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandReadState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((cosd(sqrt(3600))",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "90",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(-90)",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandReadState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosd(negate(-90))",
            prevState = prevState
        )
        val ninetyInputResultState = ScientificCalculatorFirstOperandReadState(ninetyInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.cosine(positiveInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cosine(negativeInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.cosine(zeroInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(thirtyInputResultState).matches(
                thirtyInputState.cosine(thirtyInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(ninetyInputResultState).matches(
                ninetyInputState.cosine(ninetyInputData, DEGREES_ANGLE_CODE)
            ))
    }

    @Test
    fun radiansCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((sqr(sqrt(70))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,63331920308629983233201150240736",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((cosr(sqr(sqrt(70)))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,3331920308629983233201150240736e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosr(negate(7,e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "((negate(negate(negate(0)))",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "((cosr(negate(negate(negate(0))))",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.cosine(positiveInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cosine(negativeInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.cosine(zeroInputData, RADIANS_ANGLE_CODE)
            ))
    }

    @Test
    fun gradsCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70",
            historyString = "((sqrt(sqr(reciproc(reciproc(70))))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,45399049973954679156040836635787",
            historyString = "((cosg(sqrt(sqr(reciproc(reciproc(70)))))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "4,5399049973954679156040836635787e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosg(negate(7,e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosg(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "66,666666666666666666666666666667",
            historyString = "sqrt(sqr(reciproc(200)))",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandReadState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "cosg(sqrt(sqr(reciproc(200))))",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "100",
            historyString = "(((",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandReadState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "(((cosg(100)",
            prevState = prevState
        )
        val ninetyInputResultState = ScientificCalculatorFirstOperandReadState(ninetyInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.cosine(positiveInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cosine(negativeInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.cosine(zeroInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(thirtyInputResultState).matches(
                thirtyInputState.cosine(thirtyInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(ninetyInputResultState).matches(
                ninetyInputState.cosine(ninetyInputData, GRADS_ANGLE_CODE)
            ))
    }

    @Test
    fun degreesArcCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(negate(negate(2)))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "60",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((acosd(reciproc(negate(negate(2))))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,e-1",
            historyString = "((negate(5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2e+2",
            historyString = "((acosd(negate(5,e-1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(1,21e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandReadState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((acosd(sqrt(1,21e+0))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-20000",
            historyString = "((sqrt(sqr(negate(20000)))",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandReadState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-20000",
            historyString = "((acosd(sqrt(sqr(negate(20000))))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "reciproc(1)",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "acosd(reciproc(1))",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandReadState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "90",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((acosd(0)",
            prevState = prevState
        )
        val ninetyInputResultState = ScientificCalculatorFirstOperandReadState(ninetyInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcCosine(positiveInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcCosine(negativeInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(positiveErrorInputResultState).matches(
            positiveErrorInputState.arcCosine(positiveErrorInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeErrorInputResultState).matches(
            negativeErrorInputState.arcCosine(negativeErrorInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.arcCosine(zeroInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(ninetyInputResultState).matches(
            ninetyInputState.arcCosine(ninetyInputData, DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansArcCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "negate(negate(sqrt(0,25)))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,0471975511965977461542144610932",
            historyString = "acosr(negate(negate(sqrt(0,25))))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqrt(2,5e-1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,0943951023931954923084289221863e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosr(negate(sqrt(2,5e-1)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+0",
            historyString = "((sqr(reciproc(1,5e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandReadState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+0",
            historyString = "((acosr(sqr(reciproc(1,5e+1)))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(-2)",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandReadState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((acosr(negate(-2))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcCosine(positiveInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcCosine(negativeInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(positiveErrorInputResultState).matches(
                positiveErrorInputState.arcCosine(positiveErrorInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeErrorInputResultState).matches(
                negativeErrorInputState.arcCosine(negativeErrorInputData, RADIANS_ANGLE_CODE)
            ))
    }

    @Test
    fun gradsArcCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,6666666666666666666666666666667e+1",
            historyString = "acosg(5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(0,5)",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "133,33333333333333333333333333333",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((acosg(negate(0,5))",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(negate(negate(0,9)))",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandReadState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((acosg(reciproc(negate(negate(0,9))))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,e+0",
            historyString = "negate(2,e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandReadState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,e+0",
            historyString = "acosg(negate(2,e+0))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcCosine(positiveInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcCosine(negativeInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(positiveErrorInputResultState).matches(
                positiveErrorInputState.arcCosine(positiveErrorInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeErrorInputResultState).matches(
                negativeErrorInputState.arcCosine(negativeErrorInputData, GRADS_ANGLE_CODE)
            ))
    }

    @Test
    fun piNumberTest() {
        val piData = ScientificCalculatorDataEntity(
            mainString = "-5,e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(sqr(sqrt(reciproc(2,e-2))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val piState = ScientificCalculatorFirstOperandReadState(piData)
        val piResultData = ScientificCalculatorDataEntity(
            mainString = "3,1415926535897932384626433832795e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((",
            prevState = prevState,
            isScientificNotation = true
        )
        val piResultState = ScientificCalculatorFirstOperandReadState(piResultData)

        assertTrue(
            ReflectionEquals(piResultState).matches(
                piState.piNumber(piData)
            ))
    }

    @Test
    fun doublePiNumberTest() {
        val piData = ScientificCalculatorDataEntity(
            mainString = "-5,e+0",
            historyString = "((negate(negate(sqr(sqrt(5,e+0))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val piState = ScientificCalculatorFirstOperandReadState(piData)
        val piResultData = ScientificCalculatorDataEntity(
            mainString = "6,283185307179586476925286766559e+0",
            historyString = "((",
            prevState = prevState,
            isScientificNotation = true
        )
        val piResultState = ScientificCalculatorFirstOperandReadState(piResultData)

        assertTrue(
            ReflectionEquals(piResultState).matches(
                piState.doublePiNumber(piData)
            ))
    }

    @Test
    fun hyperbolicTangentTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(3,e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-9,9505475368673045133188018525549e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((tanh(negate(3,e+0))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,2",
            historyString = "((sqr(sqrt(3,2))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,9966823978396511561809680630614",
            historyString = "((tanh(sqr(sqrt(3,2)))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqr(sqr(9,9e+2))",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandReadState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((tanh(sqr(sqr(9,9e+2)))",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorErrorState(bigInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.hyperbolicTangent(positiveInputData)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.hyperbolicTangent(negativeInputData)
            ))
        assertTrue(ReflectionEquals(bigInputResultState).matches(
            bigInputState.hyperbolicTangent(bigInputData)
        ))
    }

    @Test
    fun hyperbolicArcTangentTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-9,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqr(3,e-1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,4722194895832202300045137159439e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanh(negate(sqr(3,e-1)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,9",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(sqrt(sqr(0,9)))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,4722194895832202300045137159439",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((atanh(negate(sqrt(sqr(0,9))))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "2,e+0",
            historyString = "(reciproc(0,5)",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandReadState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,e+0",
            historyString = "(atanh(reciproc(0,5))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorErrorState(bigInputResultData)

        val oneInputData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "reciproc(reciproc(1))",
            prevState = prevState
        )
        val oneInputState = ScientificCalculatorFirstOperandReadState(oneInputData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "atanh(reciproc(reciproc(1)))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val oneInputResultState = ScientificCalculatorErrorState(oneInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.hyperbolicArcTangent(positiveInputData)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.hyperbolicArcTangent(negativeInputData)
            ))
        assertTrue(
            ReflectionEquals(bigInputResultState).matches(
                bigInputState.hyperbolicArcTangent(bigInputData)
            ))
        assertTrue(
            ReflectionEquals(oneInputResultState).matches(
                oneInputState.hyperbolicArcTangent(oneInputData)
            ))
    }

    @Test
    fun degreesTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(negate(7,e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,7474774194546222787616640264977e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(negate(negate(7,e+1)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "((negate(reciproc(reciproc(70)))",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,7474774194546222787616640264977",
            historyString = "((tand(negate(reciproc(reciproc(70))))",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val firstErrorInputData = ScientificCalculatorDataEntity(
            mainString = "9,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqr(sqrt(9,e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val firstErrorInputState = ScientificCalculatorFirstOperandReadState(firstErrorInputData)
        val firstErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((tand(sqr(sqrt(9,e+1)))",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE,
            isScientificNotation = true
        )
        val firstErrorInputResultState = ScientificCalculatorErrorState(firstErrorInputResultData)

        val secondErrorInputData = ScientificCalculatorDataEntity(
            mainString = "270",
            historyString = "reciproc(reciproc(270))",
            prevState = prevState
        )
        val secondErrorInputState = ScientificCalculatorFirstOperandReadState(secondErrorInputData)
        val secondErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "270",
            historyString = "tand(reciproc(reciproc(270)))",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val secondErrorInputResultState = ScientificCalculatorErrorState(secondErrorInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(0)",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(sqrt(0))",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "45",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(reciproc(sqrt(23)))",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandReadState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((tand(negate(reciproc(sqrt(23))))",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.tangent(positiveInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.tangent(negativeInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(firstErrorInputResultState).matches(
            firstErrorInputState.tangent(firstErrorInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(secondErrorInputResultState).matches(
            secondErrorInputState.tangent(secondErrorInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(thirtyInputResultState).matches(
            thirtyInputState.tangent(thirtyInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.tangent(zeroInputData, DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2219599181369432780892227563596e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanr(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(70)",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,2219599181369432780892227563596",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanr(negate(70))",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "((sqrt(sqr(0))",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "((tanr(sqrt(sqr(0)))",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "0,78539816339744830961566084581988",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((reciproc(sqrt(3))",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandReadState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((tanr(reciproc(sqrt(3)))",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "1,5707963267948966192313216916398",
            historyString = "sqrt(sqrt(4,5))",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandReadState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,5707963267948966192313216916398",
            historyString = "tanr(sqrt(sqrt(4,5)))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val ninetyInputResultState = ScientificCalculatorErrorState(ninetyInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.tangent(positiveInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.tangent(negativeInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.tangent(zeroInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(thirtyInputResultState).matches(
                thirtyInputState.tangent(thirtyInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(ninetyInputResultState).matches(
                ninetyInputState.tangent(ninetyInputData, RADIANS_ANGLE_CODE)
            ))
    }

    @Test
    fun gradsTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(sqr(70))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,9626105055051505823046404262119",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((tang(sqrt(sqr(70)))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7,e+1",
            historyString = "((reciproc(negate(1,445322e+1))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,9626105055051505823046404262119e+0",
            historyString = "((tang(reciproc(negate(1,445322e+1)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val firstErrorInputData = ScientificCalculatorDataEntity(
            mainString = "100",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val firstErrorInputState = ScientificCalculatorFirstOperandReadState(firstErrorInputData)
        val firstErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "100",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tang(100)",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val firstErrorInputResultState = ScientificCalculatorErrorState(firstErrorInputResultData)

        val secondErrorInputData = ScientificCalculatorDataEntity(
            mainString = "3,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(sqr(3,e+2))",
            prevState = prevState,
            isScientificNotation = true
        )
        val secondErrorInputState = ScientificCalculatorFirstOperandReadState(secondErrorInputData)
        val secondErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((tang(sqrt(sqr(3,e+2)))",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE,
            isScientificNotation = true
        )
        val secondErrorInputResultState = ScientificCalculatorErrorState(secondErrorInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.tangent(positiveInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.tangent(negativeInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(firstErrorInputResultState).matches(
                firstErrorInputState.tangent(firstErrorInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(secondErrorInputResultState).matches(
                secondErrorInputState.tangent(secondErrorInputData, GRADS_ANGLE_CODE)
            ))
    }

    @Test
    fun degreesArcTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(9)",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "71,565051177077989351572193720453",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atand(sqrt(9))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "(negate(negate(-3,e+0))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-7,1565051177077989351572193720453e+1",
            historyString = "(atand(negate(negate(-3,e+0)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val fortyFiveInputData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(reciproc(1))",
            prevState = prevState,
        )
        val fortyFiveInputState = ScientificCalculatorFirstOperandReadState(fortyFiveInputData)
        val fortyFiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "45",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((atand(sqrt(reciproc(1)))",
            prevState = prevState,
        )
        val fortyFiveInputResultState = ScientificCalculatorFirstOperandReadState(fortyFiveInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcTangent(positiveInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcTangent(negativeInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(fortyFiveInputResultState).matches(
                fortyFiveInputState.arcTangent(fortyFiveInputData, DEGREES_ANGLE_CODE)
            ))
    }

    @Test
    fun radiansArcTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(sqr(3))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2490457723982544258299170772811",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((atanr(sqrt(sqr(3)))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "(((reciproc(reciproc(negate(-3,e+0)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,2490457723982544258299170772811e+0",
            historyString = "(((atanr(reciproc(reciproc(negate(-3,e+0))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcTangent(positiveInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcTangent(negativeInputData, RADIANS_ANGLE_CODE)
            ))
    }

    @Test
    fun gradsArcTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(9)",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "79,516723530086654835080215244948",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((atang(sqrt(9))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "((negate(reciproc(reciproc(3,e+0)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-7,9516723530086654835080215244948e+1",
            historyString = "((atang(negate(reciproc(reciproc(3,e+0))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val fortyFiveInputData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(reciproc(1))",
            prevState = prevState,
        )
        val fortyFiveInputState = ScientificCalculatorFirstOperandReadState(fortyFiveInputData)
        val fortyFiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "50",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((atang(sqrt(reciproc(1)))",
            prevState = prevState,
        )
        val fortyFiveInputResultState = ScientificCalculatorFirstOperandReadState(fortyFiveInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcTangent(positiveInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcTangent(negativeInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(fortyFiveInputResultState).matches(
                fortyFiveInputState.arcTangent(fortyFiveInputData, GRADS_ANGLE_CODE)
            ))
    }

    @Test
    fun cubeNumberTest() {
        val errorInputData = ScientificCalculatorDataEntity(
            mainString = "3,e+4000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(sqr(sqr(1,1346573e+500)))",
            prevState = prevState
        )
        val errorInputState = ScientificCalculatorFirstOperandReadState(errorInputData)
        val errorInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,e+4000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cube(sqr(sqr(sqr(1,1346573e+500))))",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState
        )
        val errorInputResultState = ScientificCalculatorErrorState(errorInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "((negate(3,e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,7e+1",
            historyString = "((cube(negate(3,e+0))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(errorInputResultState).matches(
                errorInputState.cubeNumber(errorInputData)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cubeNumber(negativeInputData)
            ))
    }

    @Test
    fun cubeRootNumberTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "195112",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((reciproc(sqrt(sqr(0,00012387)))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "58",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((cuberoot(reciproc(sqrt(sqr(0,00012387))))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1,7576e+4",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,6e+1",
            historyString = "cuberoot(-1,7576e+4)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.cubeRoot(positiveInputData)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cubeRoot(negativeInputData)
            ))
    }

    @Test
    fun formatChangeTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "1951120000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(sqr(45098)))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,95112e+9",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(sqr(45098)))",
            isScientificNotation = true,
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1,7576e-1",
            historyString = "((negate(sqrt(3,4898e-2))",
            isScientificNotation = true,
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,17576",
            historyString = "((negate(sqrt(3,4898e-2))",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.fixedToExponentialFormat(positiveInputData)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.fixedToExponentialFormat(negativeInputData)
            ))
    }

    @Test
    fun toExponentialFormatOfNumberEnterTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "195112",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(sqrt(195112))",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "195112",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(sqrt(195112))",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val positiveCommaInputData = ScientificCalculatorDataEntity(
            mainString = "195112",
            historyString = "((reciproc(reciproc(195112))",
            prevState = prevState
        )
        val positiveCommaInputState = ScientificCalculatorFirstOperandReadState(positiveCommaInputData)
        val positiveCommaInputResultData = ScientificCalculatorDataEntity(
            mainString = "195112",
            historyString = "((reciproc(reciproc(195112))",
            prevState = prevState
        )
        val positiveCommaInputResultState = ScientificCalculatorFirstOperandReadState(positiveCommaInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1,7576e+3",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,7576e+3",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.exponentialFormat(positiveInputData)
            ))
        assertTrue(
            ReflectionEquals(positiveCommaInputResultState).matches(
                positiveCommaInputState.exponentialFormat(positiveCommaInputData)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.exponentialFormat(negativeInputData)
            ))
    }

    @Test
    fun logarithmBaseTenTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(reciproc(0))",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((log(reciproc(reciproc(0)))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorErrorState(zeroInputResultData)

        val fractionInputData = ScientificCalculatorDataEntity(
            mainString = "0,01",
            historyString = "(((reciproc(sqr(10))",
            prevState = prevState
        )
        val fractionInputState = ScientificCalculatorFirstOperandReadState(fractionInputData)
        val fractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2",
            historyString = "(((log(reciproc(sqr(10)))",
            prevState = prevState
        )
        val fractionInputResultState = ScientificCalculatorFirstOperandReadState(fractionInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+5",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,e+0",
            historyString = "log(1,e+5)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqr(sqrt(-1,e+0))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((log(sqr(sqrt(-1,e+0)))",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorErrorState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.logarithmBaseTen(zeroInputData)
            ))
        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.logarithmBaseTen(positiveInputData)
            ))
        assertTrue(
            ReflectionEquals(fractionInputResultState).matches(
                fractionInputState.logarithmBaseTen(fractionInputData)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.logarithmBaseTen(negativeInputData)
            ))
    }

    @Test
    fun tenToPowerOfNumberTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(sqr(0))",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((powten(negate(sqr(0)))",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val fractionInputData = ScientificCalculatorDataEntity(
            mainString = "5,5",
            historyString = "((reciproc(sqrt(0,37827))",
            prevState = prevState
        )
        val fractionInputState = ScientificCalculatorFirstOperandReadState(fractionInputData)
        val fractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "316227,76601683793319988935444327",
            historyString = "((powten(reciproc(sqrt(0,37827)))",
            prevState = prevState
        )
        val fractionInputResultState = ScientificCalculatorFirstOperandReadState(fractionInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+5",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e+5",
            historyString = "powten(1,e+5)",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorErrorState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqrt(sqr(1,e+0)))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandReadState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powten(negate(sqrt(sqr(1,e+0))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.tenPowerX(zeroInputData)
            ))
        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.tenPowerX(positiveInputData)
            ))
        assertTrue(
            ReflectionEquals(fractionInputResultState).matches(
                fractionInputState.tenPowerX(fractionInputData)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.tenPowerX(negativeInputData)
            ))
    }

    @Test
    fun clearAllTest() {
        val resultState = ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity(
                memoryNumber = "6,9"
            )
        )
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "5,55e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(sqr(reciproc(cuberoot(4,2435e+6))))",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandReadState(positiveInputData)
        val memoryInputData = ScientificCalculatorDataEntity(
            mainString = "5,55e-3",
            historyString = "((reciproc(2,234e+3)",
            prevState = prevState,
            memoryNumber = "6,9"
        )
        val memoryInputState = ScientificCalculatorFirstOperandReadState(memoryInputData)

        assertTrue(ReflectionEquals(ScientificCalculatorInitialState(ScientificCalculatorDataEntity())).matches(
            positiveInputState.clearAll(positiveInputData)
        )
        )
        assertTrue(ReflectionEquals(resultState).matches(
            memoryInputState.clearAll(memoryInputData)
        )
        )
    }*/
}