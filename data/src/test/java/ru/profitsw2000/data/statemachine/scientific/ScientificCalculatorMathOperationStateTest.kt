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

    @Test
    fun openBracketTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(0$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "0",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(0$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}(",
            prevState = zeroInputState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "2,3e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(2,3e+3${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "2,3e+3",
            prevState = prevState,
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorMathOperationState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(2,3e+3${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}(",
            prevState = nonZeroInputState,
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "6,35",
            historyString = "6,35$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            memoryNumber = "5,33",
            operand = "6,35"
        )
        val commaInputState = ScientificCalculatorMathOperationState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "6,35$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}(",
            memoryNumber = "5,33",
            prevState = commaInputState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.openBracket(zeroInputData)
            )
        )
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
                nonZeroInputState.openBracket(nonZeroInputData)
            )
        )
        assertTrue(ReflectionEquals(commaInputResultState).matches(
                commaInputState.openBracket(commaInputData)
            )
        )
    }

    @Test
    fun closeBracketTest() {
        val nullPrevData = ScientificCalculatorDataEntity(
            mainString = "3,3e+4${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "3,3e+4",
            isScientificNotation = true
        )
        val nullPrevState = ScientificCalculatorMathOperationState(nullPrevData)

        val prevSCFOISData = ScientificCalculatorDataEntity(
            mainString = "5"
        )
        val prevSCFOISState = ScientificCalculatorFirstOperandInputState(prevSCFOISData)
        val currentSCMOSData = ScientificCalculatorDataEntity(
            mainString = "3,34e+3",
            historyString = "(3,34e+3${HISTORY_STRING_SPACE_LETTER}/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "3,34e+3",
            memoryNumber = "21,564",
            prevState = prevSCFOISState,
            isScientificNotation = true
        )
        val currentSCMOSState = ScientificCalculatorMathOperationState(currentSCMOSData)
        val resultSCMOSData = ScientificCalculatorDataEntity(
            mainString = "1,e+0",
            historyString = "(3,34e+3${HISTORY_STRING_SPACE_LETTER}/${HISTORY_STRING_SPACE_LETTER}3,34e+3)",
            memoryNumber = "21,564",
            isScientificNotation = true
        )
        val resultSCMOSState = ScientificCalculatorFirstOperandReadState(resultSCMOSData)

        val prevSCFOPNISData = ScientificCalculatorDataEntity(
            mainString = "5,e+3"
        )
        val prevSCFOPNISState = ScientificCalculatorFirstOperandPowerNumberInputState(prevSCFOPNISData)
        val currentSCFOPNISData = currentSCMOSData.copy(
            prevState = prevSCFOPNISState
        )
        val currentSCFOPNISState = ScientificCalculatorMathOperationState(currentSCFOPNISData)
        val resultSCFOPNISState = ScientificCalculatorFirstOperandReadState(resultSCMOSData)

        val prevSCFORSData = ScientificCalculatorDataEntity(
            mainString = "5"
        )
        val prevSCFORSState = ScientificCalculatorFirstOperandReadState(prevSCFORSData)
        val currentSCFORSData = currentSCMOSData.copy(
            prevState = prevSCFORSState
        )
        val currentSCFORSState = ScientificCalculatorMathOperationState(currentSCFORSData)
        val resultSCFORSState = ScientificCalculatorFirstOperandReadState(resultSCMOSData)

        val prevSCISData = ScientificCalculatorDataEntity(
            mainString = "0"
        )
        val prevSCISState = ScientificCalculatorInitialState(prevSCISData)
        val currentSCISData = currentSCMOSData.copy(
            prevState = prevSCISState
        )
        val currentSCISState = ScientificCalculatorMathOperationState(currentSCISData)
        val resultSCISState = ScientificCalculatorFirstOperandReadState(resultSCMOSData)

        val prevSCORSData = ScientificCalculatorDataEntity(
            mainString = "123"
        )
        val prevSCORSState = ScientificCalculatorOperationResultState(prevSCORSData)
        val currentSCORSData = currentSCMOSData.copy(
            prevState = prevSCORSState
        )
        val currentSCORSState = ScientificCalculatorMathOperationState(currentSCORSData)
        val resultSCORSState = ScientificCalculatorFirstOperandReadState(resultSCMOSData)

        val prevSCMOSData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val prevSCMOSState = ScientificCalculatorMathOperationState(prevSCMOSData)
        val currentSCMOSData2 = ScientificCalculatorDataEntity(
            mainString = "6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(6$HISTORY_STRING_SPACE_LETTER*",
            operand = "6",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            prevState = prevSCMOSState
        )
        val currentSCMOSState2 = ScientificCalculatorMathOperationState(currentSCMOSData2)
        val resultSCMOSData2 = ScientificCalculatorDataEntity(
            mainString = "36",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(6$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}6)",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val resultSCMOSState2 = ScientificCalculatorSecondOperandReadState(resultSCMOSData2)

        val prevSCSOISData = ScientificCalculatorDataEntity(
            mainString = "123,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val prevSCSOISState = ScientificCalculatorSecondOperandInputState(prevSCSOISData)
        val currentSCSOISData = currentSCMOSData2.copy(
            prevState = prevSCSOISState
        )
        val currentSCSOISState = ScientificCalculatorMathOperationState(currentSCSOISData)
        val resultSCSOISState = ScientificCalculatorSecondOperandReadState(resultSCMOSData2)

        val prevSCSOPNISData = ScientificCalculatorDataEntity(
            mainString = "5,e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val prevSCSOPNISState = ScientificCalculatorSecondOperandPowerNumberInputState(prevSCSOPNISData)
        val currentSCSOPNISData = currentSCMOSData2.copy(
            prevState = prevSCSOPNISState
        )
        val currentSCSOPNISState = ScientificCalculatorMathOperationState(currentSCSOPNISData)
        val resultSCSOPNISState = ScientificCalculatorSecondOperandReadState(resultSCMOSData2)

        val prevSCSORSData = ScientificCalculatorDataEntity(
            mainString = "678",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER",
            scientificOperationType = ScientificOperationType.PLUS
        )
        val prevSCSORSState = ScientificCalculatorSecondOperandReadState(prevSCSORSData)
        val currentSCSORSData = currentSCMOSData2.copy(
            prevState = prevSCSORSState
        )
        val currentSCSORSState = ScientificCalculatorMathOperationState(currentSCSORSData)
        val resultSCSORSState = ScientificCalculatorSecondOperandReadState(resultSCMOSData2)

        assertTrue(ReflectionEquals(nullPrevState).matches(
                nullPrevState.closeBracket(nullPrevData)
            )
        )
        assertTrue(ReflectionEquals(resultSCMOSState).matches(
                currentSCMOSState.closeBracket(currentSCMOSData)
            )
        )
        assertTrue(ReflectionEquals(resultSCFOPNISState).matches(
                currentSCFOPNISState.closeBracket(currentSCFOPNISData)
            )
        )
        assertTrue(ReflectionEquals(resultSCFORSState).matches(
                currentSCFORSState.closeBracket(currentSCFORSData)
            )
        )
        assertTrue(ReflectionEquals(resultSCISState).matches(
                currentSCISState.closeBracket(currentSCISData)
            )
        )
        assertTrue(ReflectionEquals(resultSCORSState).matches(
                currentSCORSState.closeBracket(currentSCORSData)
            )
        )
        assertTrue(ReflectionEquals(resultSCMOSState2).matches(
                currentSCMOSState2.closeBracket(currentSCMOSData2)
            )
        )
        assertTrue(ReflectionEquals(resultSCSOISState).matches(
                currentSCSOISState.closeBracket(currentSCSOISData)
            )
        )
        assertTrue(ReflectionEquals(resultSCSOPNISState).matches(
                currentSCSOPNISState.closeBracket(currentSCSOPNISData)
            )
        )
        assertTrue(ReflectionEquals(resultSCSORSState).matches(
                currentSCSORSState.closeBracket(currentSCSORSData)
            )
        )
    }

    @Test
    fun naturalLogarithmTest() {
        val negativeNumberData = ScientificCalculatorDataEntity(
            mainString = "-1,2e-1",
            historyString = "-1,2e-1$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-0,12",
            memoryNumber = "-0,12",
            isScientificNotation = true
        )
        val negativeNumberState = ScientificCalculatorMathOperationState(negativeNumberData)
        val negativeNumberResultData = ScientificCalculatorDataEntity(
            mainString = "-1,2e-1",
            historyString = "-1,2e-1$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}ln(-1,2e-1)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-0,12",
            memoryNumber = "-0,12",
            errorCode = INVALID_INPUT_ERROR_CODE,
            isScientificNotation = true
        )
        val negativeNumberResultState = ScientificCalculatorErrorState(negativeNumberResultData)

        val zeroNumberData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "0$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "0",
            memoryNumber = "2,3"
        )
        val zeroNumberState = ScientificCalculatorMathOperationState(zeroNumberData)
        val zeroNumberResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "0$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}ln(0)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "0",
            memoryNumber = "2,3",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val zeroNumberResultState = ScientificCalculatorErrorState(zeroNumberResultData)

        val positiveNumberData = ScientificCalculatorDataEntity(
            mainString = "6,5e+3",
            historyString = "negate(negate(6,5e+3))$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "6,5e+3",
            memoryNumber = "2,3",
            isScientificNotation = true
        )
        val positiveNumberState = ScientificCalculatorMathOperationState(positiveNumberData)
        val positiveNumberResultData = ScientificCalculatorDataEntity(
            mainString = "8,7795574558837284786902296841602e+0",
            historyString = "negate(negate(6,5e+3))$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}ln(6,5e+3)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "6,5e+3",
            memoryNumber = "2,3",
            isScientificNotation = true
        )
        val positiveNumberResultState = ScientificCalculatorSecondOperandReadState(positiveNumberResultData)

        val firstState = ScientificCalculatorMathOperationState(ScientificCalculatorDataEntity())
        val prevData = ScientificCalculatorDataEntity(
            mainString = "123000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(15129000000)$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "123000",
            prevState = firstState
        )
        val prevState = ScientificCalculatorMathOperationState(prevData)
        val prevResultData = ScientificCalculatorDataEntity(
            mainString = "11,719939634354554547315982974013",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(15129000000)$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}ln(123000)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "123000",
            prevState = firstState
        )
        val prevResultState = ScientificCalculatorSecondOperandReadState(prevResultData)

        assertTrue(ReflectionEquals(negativeNumberResultState).matches(
                negativeNumberState.calculateNaturalLogarithm(negativeNumberData)
            )
        )
        assertTrue(ReflectionEquals(zeroNumberResultState).matches(
                zeroNumberState.calculateNaturalLogarithm(zeroNumberData)
            )
        )
        assertTrue(ReflectionEquals(positiveNumberResultState).matches(
                positiveNumberState.calculateNaturalLogarithm(positiveNumberData)
            )
        )
        assertTrue(ReflectionEquals(prevResultState).matches(
                prevState.calculateNaturalLogarithm(prevData)
            )
        )
    }

    @Test
    fun exponentTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(0$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "0",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(0$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}powe(0)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "0",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorSecondOperandReadState(zeroInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,33e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(-3,33e+1)${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "3,33e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,8973852666366134260275960952126e+14",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(-3,33e+1)${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}powe(3,33e+1)",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "3,33e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val bigPositiveInputData = ScientificCalculatorDataEntity(
            mainString = "9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(9,9999e+12)$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "9,9999e+6",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigPositiveInputState = ScientificCalculatorMathOperationState(bigPositiveInputData)
        val bigPositiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(9,9999e+12)$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}powe(9,9999e+6)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "9,9999e+6",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val bigPositiveInputResultState = ScientificCalculatorErrorState(bigPositiveInputResultData)

        val bigNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(9,9999e+6)$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-9,9999e+6",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigNegativeInputState = ScientificCalculatorMathOperationState(bigNegativeInputData)
        val bigNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(9,9999e+6)$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}powe(-9,9999e+6)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-9,9999e+6",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,3452e+2)${HISTORY_STRING_SPACE_LETTER}^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-3,3452e+2",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-3,34e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,3452e+2)${HISTORY_STRING_SPACE_LETTER}^${HISTORY_STRING_SPACE_LETTER}Int(-3,3452e+2)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-3,3452e+2",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val posInputData = ScientificCalculatorDataEntity(
            mainString = "3,3452e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(negate(negate(2323,323e+10)))${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "3,3452e+6",
            prevState = prevState,
            isScientificNotation = true
        )
        val posInputState = ScientificCalculatorMathOperationState(posInputData)
        val posInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,3452e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(negate(negate(2323,323e+10)))${HISTORY_STRING_SPACE_LETTER}yroot${HISTORY_STRING_SPACE_LETTER}Int(3,3452e+6)",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "3,3452e+6",
            prevState = prevState,
            isScientificNotation = true
        )
        val posInputResultState = ScientificCalculatorSecondOperandReadState(posInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "33,66",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(negate(sqrt(1923,37)))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "33,66",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorMathOperationState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "33",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(negate(sqrt(1923,37)))$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}Int(33,66)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "33,66",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorSecondOperandReadState(commaInputResultData)


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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,3452e+1)$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-3,3452e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-4,52e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,3452e+1)$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}frac(-3,3452e+1)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-3,3452e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "33,55",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(negate(sqrt(1234)))$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "33,55",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorMathOperationState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,55",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(negate(sqrt(1234)))$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}frac(33,55)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "33,55",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorSecondOperandReadState(commaInputResultData)


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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,2e+1)${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-3,2e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-3,9481480091340347580489011311222e+13",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,2e+1)${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}sinh(-3,2e+1)",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-3,2e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-0,3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqrt(0,09))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-0,3",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorMathOperationState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,3045202934471426189584352670051",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqrt(0,09))$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}sinh(-0,3)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-0,3",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorSecondOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "999999",
            historyString = "reciproc(reciproc(999999))${HISTORY_STRING_SPACE_LETTER}^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "999999"
        )
        val bigInputState = ScientificCalculatorMathOperationState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "999999",
            historyString = "reciproc(reciproc(999999))${HISTORY_STRING_SPACE_LETTER}^${HISTORY_STRING_SPACE_LETTER}sinh(999999)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "999999",
            errorCode = INVALID_INPUT_ERROR_CODE
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((-3,2e+0${HISTORY_STRING_SPACE_LETTER}*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "-3,2e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,8798635843969098368386569906444e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((-3,2e+0${HISTORY_STRING_SPACE_LETTER}*${HISTORY_STRING_SPACE_LETTER}asinh(-3,2e+0)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "-3,2e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-30",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((((negate(30)${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "-30",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorMathOperationState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "-4,0946222243305305699593547694559",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((((negate(30)${HISTORY_STRING_SPACE_LETTER}yroot${HISTORY_STRING_SPACE_LETTER}asinh(-30)",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "-30",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorSecondOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(reciproc(negate(reciproc(9,99999e+5))))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "9,99999e+5",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorMathOperationState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,4508656738523969413691847922481e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(reciproc(negate(reciproc(9,99999e+5))))$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}asinh(9,99999e+5)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "9,99999e+5",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorSecondOperandReadState(bigInputResultData)

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
            mainString = "7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((7,e+1$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,3969262078590838405410927732473e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((7,e+1$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}sind(7,e+1)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(70)$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-70",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,93969262078590838405410927732473",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(70)$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}sind(-70)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-70",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.sinus(positiveInputData, DEGREES_ANGLE_CODE)
            )
        )
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.sinus(negativeInputData, DEGREES_ANGLE_CODE)
            )
        )
    }

    @Test
    fun radiansSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((7,e+1$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "7,7389068155788909778733062514199e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((7,e+1$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}sinr(7,e+1)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "negate(sqrt(4900))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-70",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,77389068155788909778733062514199",
            historyString = "negate(sqrt(4900))${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}sinr(-70)",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-70",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.sinus(positiveInputData, RADIANS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.sinus(negativeInputData, RADIANS_ANGLE_CODE)
            ))
    }

    @Test
    fun gradsSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "7,e+1",
            historyString = "7,e+1${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "8,9100652418836786235970957141363e-1",
            historyString = "7,e+1${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}sing(7,e+1)",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(70)${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "-70",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,89100652418836786235970957141363",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(70)${HISTORY_STRING_SPACE_LETTER}yroot${HISTORY_STRING_SPACE_LETTER}sing(-70)",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "-70",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.sinus(positiveInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.sinus(negativeInputData, GRADS_ANGLE_CODE)
            ))
    }

    @Test
    fun degreesArcSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((5,e-1$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((5,e-1$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}asind(5,e-1)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "-0,5$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "-0,5",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-30",
            historyString = "-0,5$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}asind(-0,5)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "-0,5",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcSinus(positiveInputData, DEGREES_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcSinus(negativeInputData, DEGREES_ANGLE_CODE)
            ))
    }

    @Test
    fun radiansArcSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((5,e-1$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,2359877559829887307710723054658e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((5,e-1$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}asinr(5,e-1)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "-0,5$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-0,5",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,52359877559829887307710723054658",
            historyString = "-0,5$HISTORY_STRING_SPACE_LETTER^${HISTORY_STRING_SPACE_LETTER}asinr(-0,5)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-0,5",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(negate(negate(1,21e+4)))$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "1,1e+2",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorMathOperationState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(negate(negate(1,21e+4)))$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}asinr(1,1e+2)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "1,1e+2",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

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
    }

    @Test
    fun gradsArcSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((5,e-1$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,3333333333333333333333333333333e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((5,e-1$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}asing(5,e-1)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "-0,5$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-0,5"
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-33,333333333333333333333333333333",
            historyString = "-0,5$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}asing(-0,5)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-0,5"
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)


        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcSinus(positiveInputData, GRADS_ANGLE_CODE)
            ))
        assertTrue(
            ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcSinus(negativeInputData, GRADS_ANGLE_CODE)
            ))
    }

    @Test
    fun squareNumberCalculationTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "6,6e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(reciproc(6,6e+3))$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "6,6e+3",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "4,356e+7",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(reciproc(6,6e+3))$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}sqr(6,6e+3)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "6,6e+3",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7700",
            historyString = "((-7700$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-7700",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "59290000",
            historyString = "((-7700$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}sqr(-7700)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-7700",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+6000",
            historyString = "((sqr(1,e+3000)$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "1,e+6000",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorMathOperationState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e+6000",
            historyString = "((sqr(1,e+3000)$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}sqr(1,e+6000)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "1,e+6000",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(-6,e+0)$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "6,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveIntInputState = ScientificCalculatorMathOperationState(positiveIntInputData)
        val positiveIntInputResultData = ScientificCalculatorDataEntity(
            mainString = "7,2e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(-6,e+0)$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}fact(6,e+0)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "6,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveIntInputResultState = ScientificCalculatorSecondOperandReadState(positiveIntInputResultData)

        val positiveFractionInputData = ScientificCalculatorDataEntity(
            mainString = "5,6",
            historyString = "((sqrt(sqr(5,6)$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,6",
            prevState = prevState
        )
        val positiveFractionInputState = ScientificCalculatorMathOperationState(positiveFractionInputData)
        val positiveFractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "344,70192403521989539187168914402",
            historyString = "((sqrt(sqr(5,6)$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}fact(5,6)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,6",
            prevState = prevState
        )
        val positiveFractionInputResultState = ScientificCalculatorSecondOperandReadState(positiveFractionInputResultData)

        assertTrue(ReflectionEquals(positiveIntInputResultState).matches(
            positiveIntInputState.factorial(positiveIntInputData)
        ))
        assertTrue(ReflectionEquals(positiveFractionInputResultState).matches(
            positiveFractionInputState.factorial(positiveFractionInputData)
        ))
    }

    @Test
    fun fromDecimalToMinutesFormatTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "6,4e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((negate(negate(6,4e+0))${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "6,4e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,24e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((negate(negate(6,4e+0))${HISTORY_STRING_SPACE_LETTER}yroot${HISTORY_STRING_SPACE_LETTER}dms(6,4e+0)",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "6,4e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.decimalToMinutes(positiveInputData)
            ))
    }

    @Test
    fun fromMinutesToDecimalFormatTest() {
        val underSixtyMinutesInputData = ScientificCalculatorDataEntity(
            mainString = "5,45",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(sqr(5,45))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,45",
            prevState = prevState
        )
        val underSixtyMinutesInputState = ScientificCalculatorMathOperationState(underSixtyMinutesInputData)
        val underSixtyMinutesInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,75",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(sqr(5,45))$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}deg(5,45)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,45",
            prevState = prevState
        )
        val underSixtyMinutesInputResultState = ScientificCalculatorSecondOperandReadState(underSixtyMinutesInputResultData)

        val overSixtyMinutesInputData = ScientificCalculatorDataEntity(
            mainString = "8,9e+0",
            historyString = "sqrt(sqr(reciproc(reciproc(8,9e+0))))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "8,9e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val overSixtyMinutesInputState = ScientificCalculatorMathOperationState(overSixtyMinutesInputData)
        val overSixtyMinutesInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,5e+0",
            historyString = "sqrt(sqr(reciproc(reciproc(8,9e+0))))$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}deg(8,9e+0)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "8,9e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val overSixtyMinutesInputResultState = ScientificCalculatorSecondOperandReadState(overSixtyMinutesInputResultData)

        assertTrue(ReflectionEquals(underSixtyMinutesInputResultState).matches(
                underSixtyMinutesInputState.minutesToDecimal(underSixtyMinutesInputData)
            ))
        assertTrue(ReflectionEquals(overSixtyMinutesInputResultState).matches(
                overSixtyMinutesInputState.minutesToDecimal(overSixtyMinutesInputData)
            )
        )
    }

    @Test
    fun hyperbolicCosineTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,2e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,2e+0)$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "-3,2e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2286646200543857429363171495054e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(3,2e+0)$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}cosh(-3,2e+0)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "-3,2e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,2",
            historyString = "(((3,2$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "3,2",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "12,286646200543857429363171495054",
            historyString = "(((3,2$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}cosh(3,2)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "3,2",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(6)${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-6",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorMathOperationState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "201,71563612245589448340511285541",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(6)${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}cosh(-6)",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-6",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorSecondOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "sqr(reciproc(reciproc(3,333e+2)))${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "9,99999e+5",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorMathOperationState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "sqr(reciproc(reciproc(3,333e+2)))${HISTORY_STRING_SPACE_LETTER}yroot${HISTORY_STRING_SPACE_LETTER}cosh(9,99999e+5)",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "9,99999e+5",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(2,e+0)${HISTORY_STRING_SPACE_LETTER}^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val errorInputState = ScientificCalculatorMathOperationState(errorInputData)
        val errorInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(2,e+0)${HISTORY_STRING_SPACE_LETTER}^${HISTORY_STRING_SPACE_LETTER}acosh(5,e-1)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "5,e-1",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val errorInputResultState = ScientificCalculatorErrorState(errorInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "3",
            historyString = "((negate(-3)$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "3",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorMathOperationState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,7627471740390860504652186499596",
            historyString = "((negate(-3)$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}acosh(3)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "3",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorSecondOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "sqr(sqr(1,66e+2))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "9,99999e+5",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorMathOperationState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,4508656738523469412691846422479e+1",
            historyString = "sqr(sqr(1,66e+2))$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}acosh(9,99999e+5)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "9,99999e+5",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorSecondOperandReadState(bigInputResultData)

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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(sqr(7,e+1))$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,4202014332566873304409961468226e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(sqr(7,e+1))$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}cosd(7,e+1)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70",
            historyString = "negate(70)$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-70",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,34202014332566873304409961468226",
            historyString = "negate(70)$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}cosd(-70)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-70",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.cosine(positiveInputData, DEGREES_ANGLE_CODE)
            )
        )
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cosine(negativeInputData, DEGREES_ANGLE_CODE)
            )
        )
    }

    @Test
    fun radiansCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((sqr(sqrt(70))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "70",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,63331920308629983233201150240736",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((((sqr(sqrt(70))${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}cosr(70)",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "70",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(7,e+1)${HISTORY_STRING_SPACE_LETTER}^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,3331920308629983233201150240736e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(7,e+1)${HISTORY_STRING_SPACE_LETTER}^${HISTORY_STRING_SPACE_LETTER}cosr(-7,e+1)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.cosine(positiveInputData, RADIANS_ANGLE_CODE)
            )
        )
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cosine(negativeInputData, RADIANS_ANGLE_CODE)
            )
        )
    }

    @Test
    fun gradsCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70",
            historyString = "((sqrt(sqr(reciproc(reciproc(70))))${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "70",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,45399049973954679156040836635787",
            historyString = "((sqrt(sqr(reciproc(reciproc(70))))${HISTORY_STRING_SPACE_LETTER}yroot${HISTORY_STRING_SPACE_LETTER}cosg(70)",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "70",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(7,e+1)$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "4,5399049973954679156040836635787e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(7,e+1)$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}cosg(-7,e+1)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.cosine(positiveInputData, GRADS_ANGLE_CODE)
            )
        )
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cosine(negativeInputData, GRADS_ANGLE_CODE)
            )
        )
    }

    @Test
    fun degreesArcCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(negate(negate(2)))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "0,5",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "60",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(negate(negate(2)))$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}acosd(0,5)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "0,5",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,e-1",
            historyString = "((negate(5,e-1)${HISTORY_STRING_SPACE_LETTER}-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "-5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2e+2",
            historyString = "((negate(5,e-1)${HISTORY_STRING_SPACE_LETTER}-${HISTORY_STRING_SPACE_LETTER}acosd(-5,e-1)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "-5,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)


        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcCosine(positiveInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcCosine(negativeInputData, DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansArcCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "negate(negate(sqrt(0,25)))${HISTORY_STRING_SPACE_LETTER}-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "0,5",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,0471975511965977461542144610932",
            historyString = "negate(negate(sqrt(0,25)))${HISTORY_STRING_SPACE_LETTER}-${HISTORY_STRING_SPACE_LETTER}acosr(0,5)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "0,5",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+0",
            historyString = "((sqr(reciproc(1,5e+1))${HISTORY_STRING_SPACE_LETTER}-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "1,1e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorMathOperationState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+0",
            historyString = "((sqr(reciproc(1,5e+1))${HISTORY_STRING_SPACE_LETTER}-${HISTORY_STRING_SPACE_LETTER}acosr(1,1e+0)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "1,1e+0",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcCosine(positiveInputData, RADIANS_ANGLE_CODE)
            )
        )
        assertTrue(ReflectionEquals(positiveErrorInputResultState).matches(
                positiveErrorInputState.arcCosine(positiveErrorInputData, RADIANS_ANGLE_CODE)
            )
        )
    }

    @Test
    fun gradsArcCosineTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(0,5)$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-0,5",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "133,33333333333333333333333333333",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(0,5)$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}acosg(-0,5)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-0,5",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,e+0",
            historyString = "negate(2,e+0)$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-2,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputState = ScientificCalculatorMathOperationState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,e+0",
            historyString = "negate(2,e+0)$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}acosg(-2,e+0)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-2,e+0",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcCosine(negativeInputData, GRADS_ANGLE_CODE)
            )
        )
        assertTrue(ReflectionEquals(negativeErrorInputResultState).matches(
                negativeErrorInputState.arcCosine(negativeErrorInputData, GRADS_ANGLE_CODE)
            )
        )
    }

    @Test
    fun piNumberTest() {
        val piData = ScientificCalculatorDataEntity(
            mainString = "-5,e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(sqr(sqrt(reciproc(2,e-2))))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-5,e+3",
            prevState = prevState,
            isScientificNotation = true
        )
        val piState = ScientificCalculatorMathOperationState(piData)
        val piResultData = ScientificCalculatorDataEntity(
            mainString = "3,1415926535897932384626433832795e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(sqr(sqrt(reciproc(2,e-2))))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-5,e+3",
            prevState = prevState,
            isScientificNotation = true
        )
        val piResultState = ScientificCalculatorSecondOperandReadState(piResultData)

        assertTrue(
            ReflectionEquals(piResultState).matches(
                piState.piNumber(piData)
            ))
    }

    @Test
    fun doublePiNumberTest() {
        val piData = ScientificCalculatorDataEntity(
            mainString = "-5,e+0",
            historyString = "((negate(negate(sqr(sqrt(5,e+0))))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-5,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val piState = ScientificCalculatorMathOperationState(piData)
        val piResultData = ScientificCalculatorDataEntity(
            mainString = "6,283185307179586476925286766559e+0",
            historyString = "((negate(negate(sqr(sqrt(5,e+0))))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-5,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val piResultState = ScientificCalculatorSecondOperandReadState(piResultData)

        assertTrue(
            ReflectionEquals(piResultState).matches(
                piState.doublePiNumber(piData)
            ))
    }

    @Test
    fun hyperbolicTangentTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(3,e+0)$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-9,9505475368673045133188018525549e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((negate(3,e+0)$HISTORY_STRING_SPACE_LETTER^${HISTORY_STRING_SPACE_LETTER}tanh(-3,e+0)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,2",
            historyString = "((sqr(sqrt(3,2))${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "3,2",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,9966823978396511561809680630614",
            historyString = "((sqr(sqrt(3,2))${HISTORY_STRING_SPACE_LETTER}yroot${HISTORY_STRING_SPACE_LETTER}tanh(3,2)",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "3,2",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqr(sqr(9,9e+2))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "9,99999e+5",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorMathOperationState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqr(sqr(9,9e+2))$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}tanh(9,99999e+5)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "9,99999e+5",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqr(3,e-1))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "-9,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,4722194895832202300045137159439e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqr(3,e-1))$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}atanh(-9,e-1)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "-9,e-1",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,9",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(sqrt(sqr(0,9)))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "0,9",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,4722194895832202300045137159439",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(sqrt(sqr(0,9)))$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}atanh(0,9)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "0,9",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "2,e+0",
            historyString = "(reciproc(0,5)$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "2,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorMathOperationState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,e+0",
            historyString = "(reciproc(0,5)$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}atanh(2,e+0)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "2,e+0",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorErrorState(bigInputResultData)

        val oneInputData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "reciproc(reciproc(1))$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "1",
            prevState = prevState
        )
        val oneInputState = ScientificCalculatorMathOperationState(oneInputData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "reciproc(reciproc(1))$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}atanh(1)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "1",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(negate(7,e+1))${HISTORY_STRING_SPACE_LETTER}/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,7474774194546222787616640264977e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(negate(7,e+1))${HISTORY_STRING_SPACE_LETTER}/${HISTORY_STRING_SPACE_LETTER}tand(7,e+1)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val firstErrorInputData = ScientificCalculatorDataEntity(
            mainString = "9,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqr(sqrt(9,e+1))$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "9,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val firstErrorInputState = ScientificCalculatorMathOperationState(firstErrorInputData)
        val firstErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqr(sqrt(9,e+1))$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}tand(9,e+1)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "9,e+1",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE,
            isScientificNotation = true
        )
        val firstErrorInputResultState = ScientificCalculatorErrorState(firstErrorInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.tangent(positiveInputData, DEGREES_ANGLE_CODE)
            )
        )
        assertTrue(ReflectionEquals(firstErrorInputResultState).matches(
                firstErrorInputState.tangent(firstErrorInputData, DEGREES_ANGLE_CODE)
            )
        )
    }

    @Test
    fun radiansTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(7,e+1$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2219599181369432780892227563596e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(7,e+1$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}tanr(7,e+1)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "7,e+1",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "1,5707963267948966192313216916398",
            historyString = "sqrt(sqrt(4,5))$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "1,5707963267948966192313216916398",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorMathOperationState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,5707963267948966192313216916398",
            historyString = "sqrt(sqrt(4,5))$HISTORY_STRING_SPACE_LETTER/${HISTORY_STRING_SPACE_LETTER}tanr(1,5707963267948966192313216916398)",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "1,5707963267948966192313216916398",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val ninetyInputResultState = ScientificCalculatorErrorState(ninetyInputResultData)

        assertTrue(
            ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.tangent(positiveInputData, RADIANS_ANGLE_CODE)
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(sqr(70))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "70",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,9626105055051505823046404262119",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(sqr(70))${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}tang(70)",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "70",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val firstErrorInputData = ScientificCalculatorDataEntity(
            mainString = "100",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(100${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "100",
            prevState = prevState
        )
        val firstErrorInputState = ScientificCalculatorMathOperationState(firstErrorInputData)
        val firstErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "100",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(100${HISTORY_STRING_SPACE_LETTER}mod${HISTORY_STRING_SPACE_LETTER}tang(100)",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "100",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val firstErrorInputResultState = ScientificCalculatorErrorState(firstErrorInputResultData)

       assertTrue(ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.tangent(positiveInputData, GRADS_ANGLE_CODE)
            )
       )
        assertTrue(ReflectionEquals(firstErrorInputResultState).matches(
                firstErrorInputState.tangent(firstErrorInputData, GRADS_ANGLE_CODE)
            )
        )
    }

    @Test
    fun degreesArcTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(9)$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "3",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "71,565051177077989351572193720453",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(9)$HISTORY_STRING_SPACE_LETTER^${HISTORY_STRING_SPACE_LETTER}atand(3)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "3",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "(negate(negate(-3,e+0))$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-7,1565051177077989351572193720453e+1",
            historyString = "(negate(negate(-3,e+0))$HISTORY_STRING_SPACE_LETTER^${HISTORY_STRING_SPACE_LETTER}atand(-3,e+0)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.arcTangent(positiveInputData, DEGREES_ANGLE_CODE)
            )
        )
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.arcTangent(negativeInputData, DEGREES_ANGLE_CODE)
            )
        )
    }

    @Test
    fun radiansArcTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(sqr(3))${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "3",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2490457723982544258299170772811",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(sqr(3))${HISTORY_STRING_SPACE_LETTER}yroot${HISTORY_STRING_SPACE_LETTER}atanr(3)",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "3",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "(((reciproc(reciproc(negate(-3,e+0)))${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,2490457723982544258299170772811e+0",
            historyString = "(((reciproc(reciproc(negate(-3,e+0)))${HISTORY_STRING_SPACE_LETTER}yroot${HISTORY_STRING_SPACE_LETTER}atanr(-3,e+0)",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(9)$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "3",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "79,516723530086654835080215244948",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(9)$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}atang(3)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "3",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "((negate(reciproc(reciproc(3,e+0)))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-7,9516723530086654835080215244948e+1",
            historyString = "((negate(reciproc(reciproc(3,e+0)))$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}atang(-3,e+0)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        val fortyFiveInputData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(reciproc(1))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "1",
            prevState = prevState,
        )
        val fortyFiveInputState = ScientificCalculatorMathOperationState(fortyFiveInputData)
        val fortyFiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "50",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((sqrt(reciproc(1))$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}atang(1)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "1",
            prevState = prevState,
        )
        val fortyFiveInputResultState = ScientificCalculatorSecondOperandReadState(fortyFiveInputResultData)

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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(sqr(sqr(1,1346573e+500)))$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "3,e+4000",
            prevState = prevState
        )
        val errorInputState = ScientificCalculatorMathOperationState(errorInputData)
        val errorInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,e+4000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(sqr(sqr(1,1346573e+500)))$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}cube(3,e+4000)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "3,e+4000",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState
        )
        val errorInputResultState = ScientificCalculatorErrorState(errorInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "((negate(3,e+0)$HISTORY_STRING_SPACE_LETTER-",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,7e+1",
            historyString = "((negate(3,e+0)$HISTORY_STRING_SPACE_LETTER-${HISTORY_STRING_SPACE_LETTER}cube(-3,e+0)",
            scientificOperationType = ScientificOperationType.MINUS,
            operand = "-3,e+0",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(errorInputResultState).matches(
                errorInputState.cubeNumber(errorInputData)
            )
        )
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cubeNumber(negativeInputData)
            )
        )
    }

    @Test
    fun cubeRootNumberTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "195112",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((reciproc(sqrt(sqr(0,00012387)))$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "195112",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "58",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((reciproc(sqrt(sqr(0,00012387)))$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}cuberoot(195112)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "195112",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorSecondOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1,7576e+4",
            historyString = "-1,7576e+4$HISTORY_STRING_SPACE_LETTER*",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "-1,7576e+4",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,6e+1",
            historyString = "-1,7576e+4$HISTORY_STRING_SPACE_LETTER*${HISTORY_STRING_SPACE_LETTER}cuberoot(-1,7576e+4)",
            scientificOperationType = ScientificOperationType.MULTIPLY,
            operand = "-1,7576e+4",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorSecondOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
                positiveInputState.cubeRoot(positiveInputData)
            )
        )
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
                negativeInputState.cubeRoot(negativeInputData)
            )
        )
    }

    @Test
    fun formatChangeTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "1951120000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(sqr(45098)))$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "1951120000",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,95112e+9",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(sqr(45098)))$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "1951120000",
            isScientificNotation = true,
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorMathOperationState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1,7576e-1",
            historyString = "((negate(sqrt(3,4898e-2))$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-1,7576e-1",
            isScientificNotation = true,
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,17576",
            historyString = "((negate(sqrt(3,4898e-2))$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "-1,7576e-1",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorMathOperationState(negativeInputResultData)

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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(sqrt(195112))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "195112",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)

        val positiveCommaInputData = ScientificCalculatorDataEntity(
            mainString = "195112",
            historyString = "((reciproc(reciproc(195112))${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "195112",
            prevState = prevState
        )
        val positiveCommaInputState = ScientificCalculatorMathOperationState(positiveCommaInputData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1,7576e+3",
            historyString = "-1,7576e+3${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "-1,7576e+3",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorMathOperationState(negativeInputData)

        assertTrue(
            ReflectionEquals(positiveInputState).matches(
                positiveInputState.exponentialFormat(positiveInputData)
            ))
        assertTrue(
            ReflectionEquals(positiveCommaInputState).matches(
                positiveCommaInputState.exponentialFormat(positiveCommaInputData)
            ))
        assertTrue(
            ReflectionEquals(negativeInputState).matches(
                negativeInputState.exponentialFormat(negativeInputData)
            ))
    }

    @Test
    fun logarithmBaseTenTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(reciproc(0))$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "0",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((reciproc(reciproc(0))$HISTORY_STRING_SPACE_LETTER^${HISTORY_STRING_SPACE_LETTER}log(0)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "0",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorErrorState(zeroInputResultData)

        val fractionInputData = ScientificCalculatorDataEntity(
            mainString = "0,01",
            historyString = "(((reciproc(sqr(10))$HISTORY_STRING_SPACE_LETTER^",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "0,01",
            prevState = prevState
        )
        val fractionInputState = ScientificCalculatorMathOperationState(fractionInputData)
        val fractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2",
            historyString = "(((reciproc(sqr(10))$HISTORY_STRING_SPACE_LETTER^${HISTORY_STRING_SPACE_LETTER}log(0,01)",
            scientificOperationType = ScientificOperationType.POWER_OF,
            operand = "0,01",
            prevState = prevState
        )
        val fractionInputResultState = ScientificCalculatorSecondOperandReadState(fractionInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.logarithmBaseTen(zeroInputData)
            )
        )
        assertTrue(ReflectionEquals(fractionInputResultState).matches(
                fractionInputState.logarithmBaseTen(fractionInputData)
            )
        )
    }

    @Test
    fun tenToPowerOfNumberTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(sqr(0))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "0",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorMathOperationState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(((negate(sqr(0))$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}powten(0)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "0",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorSecondOperandReadState(zeroInputResultData)

        val fractionInputData = ScientificCalculatorDataEntity(
            mainString = "5,5",
            historyString = "((reciproc(sqrt(0,37827))$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,5",
            prevState = prevState
        )
        val fractionInputState = ScientificCalculatorMathOperationState(fractionInputData)
        val fractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "316227,76601683793319988935444327",
            historyString = "((reciproc(sqrt(0,37827))$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}powten(5,5)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "5,5",
            prevState = prevState
        )
        val fractionInputResultState = ScientificCalculatorSecondOperandReadState(fractionInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+5",
            historyString = "1,e+5$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "1,e+5",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e+5",
            historyString = "1,e+5$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}powten(1,e+5)",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "1,e+5",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorErrorState(positiveInputResultData)

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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(sqr(reciproc(cuberoot(4,2435e+6))))${HISTORY_STRING_SPACE_LETTER}yroot",
            scientificOperationType = ScientificOperationType.ROOT_OF,
            operand = "5,55e+2",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorMathOperationState(positiveInputData)
        val memoryInputData = ScientificCalculatorDataEntity(
            mainString = "5,55e-3",
            historyString = "((reciproc(2,234e+3)${HISTORY_STRING_SPACE_LETTER}mod",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "5,55e-3",
            prevState = prevState,
            memoryNumber = "6,9"
        )
        val memoryInputState = ScientificCalculatorMathOperationState(memoryInputData)

        assertTrue(ReflectionEquals(ScientificCalculatorInitialState(ScientificCalculatorDataEntity())).matches(
                positiveInputState.clearAll(positiveInputData)
            )
        )
        assertTrue(ReflectionEquals(resultState).matches(
                memoryInputState.clearAll(memoryInputData)
            )
        )
    }
}