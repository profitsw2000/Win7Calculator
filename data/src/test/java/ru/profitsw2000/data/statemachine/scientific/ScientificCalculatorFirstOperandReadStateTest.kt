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

class ScientificCalculatorFirstOperandReadStateTest {

    val baseSCFORS = ScientificCalculatorFirstOperandReadState(
        ScientificCalculatorDataEntity()
    )

    @Test
    fun historyStringInsertionTest(){
        val negOperation = "negate"
        val sqrtOperation = "sqrt"
        val reciprocOperation = "reciproc"

        val simpleHistoryString = "sqrt(5)"
        val simpleHistoryStringNegate = "negate(sqrt(5))"

        val historyString1 = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(5)"
        val historyString1Sqrt = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(sqrt(5))"

        val historyString2 = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(5)"
        val historyString2Reciproc = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((reciproc(sqrt(5))"

        val historyString3 = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(reciproc(5))"
        val historyString3Negate = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(sqrt(reciproc(5)))"

        val historyString4 = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(reciproc(5))"
        val historyString4Sqrt = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(sqrt(reciproc(5)))"

        val historyString5 = "(sqrt(5)"
        val historyString5Sqrt = "(sqrt(sqrt(5))"

        val historyString6 = "((sqrt(5)"
        val historyString6Reciproc = "((reciproc(sqrt(5))"

        val historyString7 = "(sqrt(reciproc(5))"
        val historyString7Negate = "(negate(sqrt(reciproc(5)))"

        val historyString8 = "((sqrt(reciproc(5))"
        val historyString8Sqrt = "((sqrt(sqrt(reciproc(5)))"

        val historyString9 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(" +
                "sqrt(5)"
        val historyString9Sqrt = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(" +
                "sqrt(sqrt(5))"

        val historyString10 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}((" +
                "sqrt(5)"
        val historyString10Reciproc = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}((" +
                "reciproc(sqrt(5))"

        val historyString11 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(" +
                "sqrt(reciproc(5))"
        val historyString11Negate = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(" +
                "negate(sqrt(reciproc(5)))"

        val historyString12 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}((" +
                "sqrt(reciproc(5))"
        val historyString12Sqrt = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}((" +
                "sqrt(sqrt(reciproc(5)))"

        val historyString13 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(("
        val historyString13Sqrt = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}((" +
                "sqrt(5,3548)"

        assertEquals(
            simpleHistoryStringNegate,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = simpleHistoryString
                ),
                negOperation)
        )

        assertEquals(
            historyString1Sqrt,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString1
                ),
                sqrtOperation)
        )

        assertEquals(
            historyString2Reciproc,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString2
                ),
                reciprocOperation)
        )

        assertEquals(
            historyString3Negate,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString3
                ),
                negOperation)
        )

        assertEquals(
            historyString4Sqrt,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString4
                ),
                sqrtOperation)
        )

        assertEquals(
            historyString5Sqrt,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString5
                ),
                sqrtOperation)
        )

        assertEquals(
            historyString6Reciproc,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString6
                ),
                reciprocOperation)
        )

        assertEquals(
            historyString7Negate,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString7
                ),
                negOperation)
        )

        assertEquals(
            historyString8Sqrt,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString8
                ),
                sqrtOperation)
        )

        assertEquals(
            historyString9Sqrt,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString9
                ),
                sqrtOperation)
        )

        assertEquals(
            historyString10Reciproc,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString10
                ),
                reciprocOperation)
        )

        assertEquals(
            historyString11Negate,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString11
                ),
                negOperation)
        )

        assertEquals(
            historyString12Sqrt,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    historyString = historyString12
                ),
                sqrtOperation)
        )

        assertEquals(
            historyString13Sqrt,
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    mainString = "5,3548",
                    historyString = historyString13
                ),
                sqrtOperation)
        )

        assertEquals(
            "sqrt(5,3548)",
            baseSCFORS.getHistoryStringWithInsertedOperationString(
                ScientificCalculatorDataEntity(
                    mainString = "5,3548",
                    historyString = ""
                ),
                sqrtOperation)
        )
    }

    @Test
    fun historyStringRemovingTest(){
        val negOperation = "negate"
        val sqrtOperation = "sqrt"
        val reciprocOperation = "reciproc"

        val simpleHistoryString = "sqrt(5)"
        val simpleHistoryStringResult = ""

        val historyString1 = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(5)"
        val historyString1Sqrt = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER("

        val historyString2 = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(5)"
        val historyString2Reciproc = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(("

        val historyString3 = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(reciproc(5))"
        val historyString3Negate = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER("

        val historyString4 = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((sqrt(reciproc(5))"
        val historyString4Sqrt = "33$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(("

        val historyString5 = "(sqrt(5)"
        val historyString5Sqrt = "("

        val historyString6 = "((sqrt(5)"
        val historyString6Reciproc = "(("

        val historyString7 = "(sqrt(reciproc(5))"
        val historyString7Negate = "("

        val historyString8 = "((sqrt(reciproc(5))"
        val historyString8Sqrt = "(("

        val historyString9 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(" +
                "sqrt(5)"
        val historyString9Sqrt = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}("

        val historyString10 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}((" +
                "sqrt(5)"
        val historyString10Reciproc = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(("

        val historyString11 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(" +
                "sqrt(reciproc(5))"
        val historyString11Negate = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}("

        val historyString12 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}((" +
                "sqrt(reciproc(5))"
        val historyString12Sqrt = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(("

        val historyString13 = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(("
        val historyString13Sqrt = "33$HISTORY_STRING_SPACE_LETTER" +
                "+$HISTORY_STRING_SPACE_LETTER(" +
                "17$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}3)" +
                "$HISTORY_STRING_SPACE_LETTER+${HISTORY_STRING_SPACE_LETTER}(("

        assertEquals(
            simpleHistoryStringResult,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(
                simpleHistoryString
            )
        )

        assertEquals(
            historyString1Sqrt,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(
                historyString1
            )
        )

        assertEquals(
            historyString2Reciproc,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(
                historyString2)
        )

        assertEquals(
            historyString3Negate,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString3)
        )

        assertEquals(
            historyString4Sqrt,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString4)
        )

        assertEquals(
            historyString5Sqrt,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString5)
        )

        assertEquals(
            historyString6Reciproc,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString6)
        )

        assertEquals(
            historyString7Negate,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString7)
        )

        assertEquals(
            historyString8Sqrt,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString8)
        )

        assertEquals(
            historyString9Sqrt,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString9)
        )

        assertEquals(
            historyString10Reciproc,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString10)
        )

        assertEquals(
            historyString11Negate,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString11)
        )

        assertEquals(
            historyString12Sqrt,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString12)
        )

        assertEquals(
            historyString13Sqrt,
            baseSCFORS.getHistoryStringWithRemovedLastMathFunctionHistory(historyString13)
        )
    }


    @Test
    fun clearMemoryTest() {
        val numberInMemoryData = ScientificCalculatorDataEntity(
            mainString = "55",
            memoryNumber = "15"
        )
        val clearedMemoryData = ScientificCalculatorDataEntity(
            mainString = "55"
        )
        val numberInMemoryState = ScientificCalculatorFirstOperandReadState(
            numberInMemoryData
        )
        val clearedMemoryState = ScientificCalculatorFirstOperandReadState(
            clearedMemoryData
        )

        assertTrue(
            ReflectionEquals(clearedMemoryState).matches(
                numberInMemoryState.clearMemory(numberInMemoryData)
            ))
        assertFalse(
            ReflectionEquals(numberInMemoryState).matches(
                numberInMemoryState.clearMemory(numberInMemoryData)
            ))
    }

    @Test
    fun clearEnteredNumberTest() {
        val nonZeroData = ScientificCalculatorDataEntity(
            mainString = "6",
            historyString = "reciproc(reciproc(6))"
        )
        val nonZeroState = ScientificCalculatorFirstOperandReadState(nonZeroData)
        val nonZeroResultData = ScientificCalculatorDataEntity(
            mainString = "0"
        )
        val nonZeroResultState = ScientificCalculatorFirstOperandInputState(nonZeroResultData)
        val historyData = ScientificCalculatorDataEntity(
            mainString = "6",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))"
        )
        val historyState = ScientificCalculatorFirstOperandReadState(historyData)
        val historyResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER("
        )
        val historyResultState = ScientificCalculatorFirstOperandInputState(historyResultData)

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
            historyString = "sqrt(reciproc(234))",
            memoryNumber = "3,99"
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,99",
            memoryNumber = "3,99"
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+23",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))",
            memoryNumber = "3,99",
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandReadState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,99e+0",
            memoryNumber = "3,99",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(
            ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.readMemory(zeroInputData)
            ))
        assertTrue(
            ReflectionEquals(nonZeroInputResultState).matches(
                nonZeroInputState.readMemory(nonZeroInputData)
            ))
    }

    @Test
    fun saveToMemoryTest() {
        val zeroInputMemoryData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(0))",
            memoryNumber = "2,35"
        )
        val zeroInputMemoryState = ScientificCalculatorFirstOperandReadState(zeroInputMemoryData)
        val zeroInputMemoryResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(0))",
            memoryNumber = null
        )
        val zeroInputMemoryResultState = ScientificCalculatorFirstOperandReadState(zeroInputMemoryResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))",
            memoryNumber = "2,35"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandReadState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))",
            memoryNumber = "2"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

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
            mainString = "3",
            historyString = "sqrt(reciproc(234))"
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3",
            memoryNumber = "3",
            historyString = "sqrt(reciproc(234))"
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val zeroInputMemoryData = ScientificCalculatorDataEntity(
            mainString = "5,6",
            historyString = "reciproc(reciproc(5,6))",
            memoryNumber = "2,35"
        )
        val zeroInputMemoryState = ScientificCalculatorFirstOperandReadState(zeroInputMemoryData)
        val zeroInputMemoryResultState = ScientificCalculatorFirstOperandReadState(
            ScientificCalculatorDataEntity(
                mainString = "5,6",
                historyString = "reciproc(reciproc(5,6))",
                memoryNumber = "7,95"
            )
        )
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "35,23e+5",
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandReadState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,523e+6",
            memoryNumber = "35,23e+5",
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

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
            mainString = "5",
            historyString = "reciproc(reciproc(5))"
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "reciproc(reciproc(5))",
            memoryNumber = "-5"
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val zeroInputMemoryData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "reciproc(reciproc(5))",
            memoryNumber = "2,35"
        )
        val zeroInputMemoryState = ScientificCalculatorFirstOperandReadState(zeroInputMemoryData)
        val zeroInputMemoryResultState = ScientificCalculatorFirstOperandReadState(
            ScientificCalculatorDataEntity(
                mainString = "5",
                historyString = "reciproc(reciproc(5))",
                memoryNumber = "-2,65"
            )
        )
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "35,23e+3",
            historyString = "reciproc(reciproc(5))",
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandReadState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,523e+4",
            historyString = "reciproc(reciproc(5))",
            memoryNumber = "-35230",
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
                zeroInputState.subtractNumberFromMemory(zeroInputData)
        ))
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
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "negate(0)"
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "6,427",
            historyString = "sqrt(5)"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandReadState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "-6,427",
            historyString = "negate(sqrt(5))"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val nonZeroNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,68",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(reciproc(6))"
        )
        val nonZeroNegativeInputState = ScientificCalculatorFirstOperandReadState(nonZeroInputData)
        val nonZeroNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,68",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(reciproc(reciproc(6)))"
        )
        val nonZeroNegativeInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroNegativeInputResultData)

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
            historyString = "sqrt(0)"
        )
        val zeroInputState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sqrt(sqrt(0))"
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "9",
            historyString = "sqrt(81)"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandReadState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3",
            historyString = "sqrt(sqrt(81))"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val nonZeroNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,68e+0",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(negate(reciproc(reciproc(6)))",
            isScientificNotation = true
        )
        val nonZeroNegativeInputState = ScientificCalculatorFirstOperandReadState(nonZeroNegativeInputData)
        val nonZeroNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-5,68e+0",
            historyString = "43$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqrt(negate(reciproc(reciproc(6))))",
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
/*
    @Test
    fun digitInputTest() {
        val zeroData = ScientificCalculatorDataEntity(
            mainString = "2,e+0"
        )
        val zeroState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroData)
        val nonZeroInputToZeroData = ScientificCalculatorDataEntity(
            mainString = "2,e+5"
        )
        val nonZeroInputToZeroState = ScientificCalculatorFirstOperandPowerNumberInputState(nonZeroInputToZeroData)

        val nonZeroData = ScientificCalculatorDataEntity(
            mainString = "2,e+43"
        )
        val nonZeroState = ScientificCalculatorFirstOperandPowerNumberInputState(nonZeroData)
        val zeroInputToNonZeroData = ScientificCalculatorDataEntity(
            mainString = "2,e+430"
        )
        val zeroInputToNonZeroState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputToNonZeroData)
        val nonZeroInputToNonZeroData = ScientificCalculatorDataEntity(
            mainString = "2,e+435"
        )
        val nonZeroInputToNonZeroState = ScientificCalculatorFirstOperandPowerNumberInputState(nonZeroInputToNonZeroData)

        val fullData = ScientificCalculatorDataEntity(
            mainString = "2,e+1234"
        )
        val fullState = ScientificCalculatorFirstOperandPowerNumberInputState(nonZeroData)

        assertTrue(ReflectionEquals(zeroState).matches(
            zeroState.inputDigit(zeroData, "0")
        ))
        assertTrue(ReflectionEquals(zeroState).matches(
            zeroState.inputDigit(zeroData, ",")
        ))
        assertTrue(ReflectionEquals(nonZeroInputToZeroState).matches(
            zeroState.inputDigit(zeroData, "5")
        ))
        assertTrue(ReflectionEquals(zeroInputToNonZeroState).matches(
            nonZeroState.inputDigit(nonZeroData, "0")
        ))
        assertTrue(ReflectionEquals(nonZeroState).matches(
            nonZeroState.inputDigit(nonZeroData, ",")
        ))
        assertTrue(ReflectionEquals(nonZeroInputToNonZeroState).matches(
            nonZeroState.inputDigit(nonZeroData, "5")
        ))
        assertTrue(ReflectionEquals(fullState).matches(
            fullState.inputDigit(fullData, "0")
        ))
        assertTrue(ReflectionEquals(fullState).matches(
            fullState.inputDigit(fullData, ",")
        ))
        assertTrue(ReflectionEquals(fullState).matches(
            fullState.inputDigit(fullData, "5")
        ))
    }

    @Test
    fun mathOperationTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "876,e+0"
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "876",
            historyString = "876$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "876"
        )
        val zeroInputResultState = ScientificCalculatorMathOperationState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "49,8e+38"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "4,98e+39",
            historyString = "4,98e+39$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "4,98e+39"
        )
        val nonZeroInputResultState = ScientificCalculatorMathOperationState(nonZeroInputResultData)
        val nonZeroCommaInputData = ScientificCalculatorDataEntity(
            mainString = "498,e+3"
        )
        val nonZeroCommaInputState = ScientificCalculatorFirstOperandPowerNumberInputState(nonZeroCommaInputData)
        val nonZeroCommaInputResultData = ScientificCalculatorDataEntity(
            mainString = "498000",
            historyString = "498000$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "498000"
        )
        val nonZeroCommaInputResultState = ScientificCalculatorMathOperationState(nonZeroCommaInputResultData)
        val nonZeroCommaInputDataSN = ScientificCalculatorDataEntity(
            mainString = "498,e+3",
            isScientificNotation = true
        )
        val nonZeroCommaInputStateSN = ScientificCalculatorFirstOperandPowerNumberInputState(nonZeroCommaInputDataSN)
        val nonZeroCommaInputResultDataSN = ScientificCalculatorDataEntity(
            mainString = "4,98e+5",
            historyString = "4,98e+5$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "498000",
            isScientificNotation = true
        )
        val nonZeroCommaInputResultStateSN = ScientificCalculatorMathOperationState(nonZeroCommaInputResultDataSN)
        val prevInputData = ScientificCalculatorDataEntity(
            mainString = "56,e+3",
            historyString = "0$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = zeroInputResultState
        )
        val prevInputState = ScientificCalculatorFirstOperandPowerNumberInputState(prevInputData)
        val prevInputResultData = ScientificCalculatorDataEntity(
            mainString = "56000",
            historyString = "0$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(56000$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "56000",
            prevState = zeroInputResultState
        )
        val prevInputResultState = ScientificCalculatorMathOperationState(prevInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.primitiveMathOperation(zeroInputData, ScientificOperationType.PLUS, "+")
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.primitiveMathOperation(nonZeroInputData, ScientificOperationType.DIVIDE, "/")
        ))
        assertTrue(ReflectionEquals(nonZeroCommaInputResultState).matches(
            nonZeroCommaInputState.primitiveMathOperation(nonZeroCommaInputData, ScientificOperationType.DIVIDE, "/")
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
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "8,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,125",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(8)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val zeroErrorInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroErrorInputData)
        val zeroErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(0)",
            errorCode = DIVIDE_ON_ZERO_ERROR_CODE,
            prevState = prevState
        )
        val zeroErrorInputResultState = ScientificCalculatorErrorState(zeroErrorInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "10,e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e-6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(" +
                    "reciproc(1,e+6)",
            prevState = prevState,
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-12,5e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-80",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(" +
                    "reciproc(-0,0125)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.reciprocOperation(zeroInputData)
        ))
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
            mainString = "44,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "44",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((",
            prevState = zeroInputState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "23,e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,3e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((",
            prevState = nonZeroInputState,
            isScientificNotation = true
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "6,35e+4"
        )
        val commaInputState = ScientificCalculatorFirstOperandPowerNumberInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "63500",
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
            mainString = "33,e+4",
            isScientificNotation = true
        )
        val nullPrevState = ScientificCalculatorFirstOperandPowerNumberInputState(nullPrevData)
        val nullPrevResultData = ScientificCalculatorDataEntity(
            mainString = "3,3e+5",
            isScientificNotation = true
        )
        val nullPrevResultState = ScientificCalculatorFirstOperandReadState(nullPrevResultData)

        val prevSCFOISData = ScientificCalculatorDataEntity(
            mainString = "5,"
        )
        val prevSCFOISState = ScientificCalculatorFirstOperandInputState(prevSCFOISData)
        val currentSCFOISData = ScientificCalculatorDataEntity(
            mainString = "33,4e+3",
            historyString = "(",
            memoryNumber = "21,564",
            prevState = prevSCFOISState,
            isScientificNotation = true
        )
        val currentSCFOISState = ScientificCalculatorFirstOperandInputState(currentSCFOISData)
        val resultSCFOISData = ScientificCalculatorDataEntity(
            mainString = "3,34e+4",
            historyString = "(3,34e+4)",
            memoryNumber = "21,564",
            isScientificNotation = true
        )
        val resultSCFOISState = ScientificCalculatorFirstOperandReadState(resultSCFOISData)


        val prevSCFOPNISData = ScientificCalculatorDataEntity(
            mainString = "5,e+3"
        )
        val prevSCFOPNISState = ScientificCalculatorFirstOperandPowerNumberInputState(prevSCFOPNISData)
        val currentSCFOPNISData = currentSCFOISData.copy(
            prevState = prevSCFOPNISState
        )
        val currentSCFOPNISState = ScientificCalculatorFirstOperandPowerNumberInputState(currentSCFOPNISData)
        val resultSCFOPNISState = ScientificCalculatorFirstOperandReadState(resultSCFOISData)


        val prevSCFORSData = ScientificCalculatorDataEntity(
            mainString = "5"
        )
        val prevSCFORSState = ScientificCalculatorFirstOperandReadState(prevSCFORSData)
        val currentSCFORSData = currentSCFOISData.copy(
            prevState = prevSCFORSState
        )
        val currentSCFORSState = ScientificCalculatorFirstOperandPowerNumberInputState(currentSCFOPNISData)
        val resultSCFORSState = ScientificCalculatorFirstOperandReadState(resultSCFOISData)

        val prevSCISData = ScientificCalculatorDataEntity(
            mainString = "0"
        )
        val prevSCISState = ScientificCalculatorInitialState(prevSCISData)
        val currentSCISData = currentSCFOISData.copy(
            prevState = prevSCISState
        )
        val currentSCISState = ScientificCalculatorFirstOperandPowerNumberInputState(currentSCISData)
        val resultSCISState = ScientificCalculatorFirstOperandReadState(resultSCFOISData)


        val prevSCORSData = ScientificCalculatorDataEntity(
            mainString = "123"
        )
        val prevSCORSState = ScientificCalculatorOperationResultState(prevSCORSData)
        val currentSCORSData = currentSCFOISData.copy(
            prevState = prevSCORSState
        )
        val currentSCORSState = ScientificCalculatorFirstOperandPowerNumberInputState(currentSCORSData)
        val resultSCORSState = ScientificCalculatorFirstOperandReadState(resultSCFOISData)

        val prevSCMOSData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+"
        )
        val prevSCMOSState = ScientificCalculatorMathOperationState(prevSCMOSData)
        val currentSCMOSData = currentSCFOISData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevSCMOSState
        )
        val currentSCMOSState = ScientificCalculatorFirstOperandPowerNumberInputState(currentSCMOSData)
        val resultSCMOSData = resultSCFOISData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(3,34e+4)"
        )
        val resultSCMOSState = ScientificCalculatorSecondOperandReadState(resultSCMOSData)

        val prevSCSOISData = ScientificCalculatorDataEntity(
            mainString = "123,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER"
        )
        val prevSCSOISState = ScientificCalculatorSecondOperandInputState(prevSCSOISData)
        val currentSCSOISData = currentSCFOISData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevSCSOISState
        )
        val currentSCSOISState = ScientificCalculatorFirstOperandPowerNumberInputState(currentSCSOISData)
        val resultSCSOISData = resultSCFOISData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(3,34e+4)"
        )
        val resultSCSOISState = ScientificCalculatorSecondOperandReadState(resultSCSOISData)

        val prevSCSOPNISData = ScientificCalculatorDataEntity(
            mainString = "5,e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER"
        )
        val prevSCSOPNISState = ScientificCalculatorSecondOperandPowerNumberInputState(prevSCSOPNISData)
        val currentSCSOPNISData = currentSCFOISData.copy(
            historyString = "5,e+3$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevSCSOPNISState
        )
        val currentSCSOPNISState = ScientificCalculatorFirstOperandPowerNumberInputState(currentSCSOPNISData)
        val resultSCSOPNISData = resultSCFOISData.copy(
            historyString = "5,e+3$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(3,34e+4)"
        )
        val resultSCSOPNISState = ScientificCalculatorSecondOperandReadState(resultSCSOPNISData)


        val prevSCSORSData = ScientificCalculatorDataEntity(
            mainString = "678",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER"
        )
        val prevSCSORSState = ScientificCalculatorSecondOperandPowerNumberInputState(prevSCSORSData)
        val currentSCSORSData = currentSCFOISData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevSCSORSState
        )
        val currentSCSORSState = ScientificCalculatorFirstOperandPowerNumberInputState(currentSCSORSData)
        val resultSCSORSData = resultSCFOISData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(3,34e+4)"
        )
        val resultSCSORSState = ScientificCalculatorSecondOperandReadState(resultSCSORSData)

        assertTrue(ReflectionEquals(nullPrevResultState).matches(
            nullPrevState.closeBracket(nullPrevData)
        ))
        assertTrue(ReflectionEquals(resultSCFOISState).matches(
            currentSCFOISState.closeBracket(currentSCFOISData)
        ))
        assertTrue(ReflectionEquals(resultSCFOPNISState).matches(
            currentSCFOPNISState.closeBracket(currentSCFOPNISData)
        ))
        assertTrue(ReflectionEquals(resultSCFORSState).matches(
            currentSCFORSState.closeBracket(currentSCFORSData)
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
            mainString = "-12,e-2",
            memoryNumber = "2,3",
            isScientificNotation = true
        )
        val negativeNumberState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeNumberData)
        val negativeNumberResultData = ScientificCalculatorDataEntity(
            mainString = "-12,e-2",
            historyString = "ln(-1,2e-1)",
            memoryNumber = "2,3",
            errorCode = INVALID_INPUT_ERROR_CODE,
            isScientificNotation = true
        )
        val negativeNumberResultState = ScientificCalculatorErrorState(negativeNumberResultData)

        val zeroNumberData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            memoryNumber = "2,3"
        )
        val zeroNumberState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroNumberData)
        val zeroNumberResultData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "ln(0)",
            memoryNumber = "2,3",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val zeroNumberResultState = ScientificCalculatorErrorState(zeroNumberResultData)

        val positiveNumberData = ScientificCalculatorDataEntity(
            mainString = "65,e+2",
            memoryNumber = "2,3",
            isScientificNotation = true
        )
        val positiveNumberState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveNumberData)
        val positiveNumberResultData = ScientificCalculatorDataEntity(
            mainString = "8,7795574558837284786902296841602e+0",
            historyString = "ln(6,5e+3)",
            memoryNumber = "2,3",
            isScientificNotation = true
        )
        val positiveNumberResultState = ScientificCalculatorFirstOperandReadState(positiveNumberResultData)

        val firstState = ScientificCalculatorMathOperationState(baseData)
        val prevData = ScientificCalculatorDataEntity(
            mainString = "12,3e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = firstState
        )
        val prevState = ScientificCalculatorFirstOperandPowerNumberInputState(prevData)
        val prevResultData = ScientificCalculatorDataEntity(
            mainString = "11,719939634354554547315982974013",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(ln(123000)",
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
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,33e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,8973852666366134260275960952126e+14",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(3,33e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val bigPositiveInputData = ScientificCalculatorDataEntity(
            mainString = "99999,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigPositiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(bigPositiveInputData)
        val bigPositiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(9,9999e+6)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val bigPositiveInputResultState = ScientificCalculatorErrorState(bigPositiveInputResultData)

        val bigNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-99999,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigNegativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(bigNegativeInputData)
        val bigNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-9,9999e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(-9,9999e+6)",
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
            mainString = "-33,452e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(Int(-3,3452e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val posInputData = ScientificCalculatorDataEntity(
            mainString = "33,452e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val posInputState = ScientificCalculatorFirstOperandPowerNumberInputState(posInputData)
        val posInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,3452e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(Int(3,3452e+6)",
            prevState = prevState,
            isScientificNotation = true
        )
        val posInputResultState = ScientificCalculatorFirstOperandReadState(posInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "33,e+66",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandPowerNumberInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,3e+67",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(Int(3,3e+67)",
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
            mainString = "-33,452e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-4,52e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(frac(-3,3452e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "33,e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandPowerNumberInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(frac(33000)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-3,9481480091340347580489011311222e+13",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinh(-3,2e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandPowerNumberInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,3045202934471426189584352670051",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinh(-0,3)",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "999999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandPowerNumberInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "999999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinh(9,99999e+5)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,8798635843969098368386569906444e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinh(-3,2e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandPowerNumberInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "-4,0946222243305305699593547694559",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinh(-30)",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "999,999e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandPowerNumberInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,4508656738523969413691847922481e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinh(9,99999e+5)",
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
            mainString = "70,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,3969262078590838405410927732473e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sind(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,93969262078590838405410927732473",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sind(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sind(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "30,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sind(30)",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "90,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sind(90)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "7,7389068155788909778733062514199e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinr(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,77389068155788909778733062514199",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinr(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinr(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "52,359877559829887307710723054658e-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinr(0,52359877559829887307710723054658)",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "157,07963267948966192313216916398e-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinr(1,5707963267948966192313216916398)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "8,9100652418836786235970957141363e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sing(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,89100652418836786235970957141363",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sing(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sing(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "0,33333333333333333333333333333333e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sing(33,333333333333333333333333333333)",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sing(100)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-30",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(110)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(-2,e+2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "90",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(1)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,2359877559829887307710723054658e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinr(5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,52359877559829887307710723054658",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinr(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinr(1,1e+2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinr(-200)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,3333333333333333333333333333333e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asing(5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-33,333333333333333333333333333333",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asing(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asing(11)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asing(-2,e+1)",
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
            mainString = "66,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "4,356e+7",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(6,6e+3)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-77,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "59290000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(-7700)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+6000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e+6000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(1,e+6000)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveIntInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveIntInputData)
        val positiveIntInputResultData = ScientificCalculatorDataEntity(
            mainString = "7,2e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(6,e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveIntInputResultState = ScientificCalculatorFirstOperandReadState(positiveIntInputResultData)

        val positiveFractionInputData = ScientificCalculatorDataEntity(
            mainString = "0,056e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveFractionInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveFractionInputData)
        val positiveFractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "344,70192403521989539187168914402",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(5,6)",
            prevState = prevState
        )
        val positiveFractionInputResultState = ScientificCalculatorFirstOperandReadState(positiveFractionInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,01e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,01e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(-1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorErrorState(negativeInputResultData)

        val negativeFracInputData = ScientificCalculatorDataEntity(
            mainString = "-5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeFracInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeFracInputData)
        val negativeFracInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,7724538509055160272981674833411e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(-5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeFracInputResultState = ScientificCalculatorFirstOperandReadState(negativeFracInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(1,e+4)",
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
            mainString = "0,0064e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,24e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(dms(6,4e+0)",
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
            mainString = "545,e-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val underSixtyMinutesInputState = ScientificCalculatorFirstOperandPowerNumberInputState(underSixtyMinutesInputData)
        val underSixtyMinutesInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,75",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(deg(5,45)",
            prevState = prevState
        )
        val underSixtyMinutesInputResultState = ScientificCalculatorFirstOperandReadState(underSixtyMinutesInputResultData)

        val overSixtyMinutesInputData = ScientificCalculatorDataEntity(
            mainString = "0,000089e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val overSixtyMinutesInputState = ScientificCalculatorFirstOperandPowerNumberInputState(overSixtyMinutesInputData)
        val overSixtyMinutesInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,5e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(deg(8,9e+0)",
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
            mainString = "-0,0000032e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2286646200543857429363171495054e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosh(-3,2e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,0000032e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "12,286646200543857429363171495054",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosh(3,2)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-6,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandPowerNumberInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "201,71563612245589448340511285541",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosh(-6)",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandPowerNumberInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosh(9,99999e+5)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val errorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(errorInputData)
        val errorInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosh(5,e-1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val errorInputResultState = ScientificCalculatorErrorState(errorInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "3,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandPowerNumberInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,7627471740390860504652186499596",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosh(3)",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandPowerNumberInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,4508656738523469412691846422479e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosh(9,99999e+5)",
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
            mainString = "0,7e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,4202014332566873304409961468226e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosd(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,07e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,34202014332566873304409961468226",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosd(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosd(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "0,006e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandInputState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosd(60)",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "0,9e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandInputState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosd(90)",
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
            mainString = "0,00070e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,63331920308629983233201150240736",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosr(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,7e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,3331920308629983233201150240736e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosr(-7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosr(0)",
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
            mainString = "0,000000007e+10",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,45399049973954679156040836635787",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosg(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,0007e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "4,5399049973954679156040836635787e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosg(-7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosg(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "0,66666666666666666666666666666667e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosg(66,666666666666666666666666666667)",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "0,1e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosg(100)",
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
            mainString = "500,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "60",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(0,5)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-500,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(-5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(1,1e+3)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(-20000)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,001e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(1)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "90",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(0)",
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
            mainString = "500000,e-6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,0471975511965977461542144610932",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosr(0,5)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-500000,e-6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,0943951023931954923084289221863e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosr(-5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "11000000e-7",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "11000000e-7",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosr(1,1e+0)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-200000,e-5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-200000,e-5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosr(-2)",
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
            mainString = "500,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,6666666666666666666666666666667e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosg(5,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-500,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "133,33333333333333333333333333333",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosg(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "11000,e-4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "11000,e-4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosg(1,1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosg(-2,e+0)",
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
            mainString = "-500,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val piState = ScientificCalculatorFirstOperandPowerNumberInputState(piData)
        val piResultData = ScientificCalculatorDataEntity(
            mainString = "3,1415926535897932384626433832795e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
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
            mainString = "-0,005e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val piState = ScientificCalculatorFirstOperandPowerNumberInputState(piData)
        val piResultData = ScientificCalculatorDataEntity(
            mainString = "6,283185307179586476925286766559e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
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
            mainString = "-3000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-9,9505475368673045133188018525549e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanh(-3,e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3200,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,9966823978396511561809680630614",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanh(3,2)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandPowerNumberInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,99999e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanh(9,99999e+5)",
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
            mainString = "-0,0009e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,4722194895832202300045137159439e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanh(-9,e-1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "900,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,4722194895832202300045137159439",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanh(0,9)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "2000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputState = ScientificCalculatorFirstOperandPowerNumberInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "2000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanh(2,e+0)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val bigInputResultState = ScientificCalculatorErrorState(bigInputResultData)

        val oneInputData = ScientificCalculatorDataEntity(
            mainString = "1000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val oneInputState = ScientificCalculatorFirstOperandPowerNumberInputState(oneInputData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "1000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanh(1)",
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
            mainString = "700,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,7474774194546222787616640264977e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7000,e-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,7474774194546222787616640264977",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val firstErrorInputData = ScientificCalculatorDataEntity(
            mainString = "0,009e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val firstErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(firstErrorInputData)
        val firstErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,009e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(9,e+1)",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE,
            isScientificNotation = true
        )
        val firstErrorInputResultState = ScientificCalculatorErrorState(firstErrorInputResultData)

        val secondErrorInputData = ScientificCalculatorDataEntity(
            mainString = "2,70e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val secondErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(secondErrorInputData)
        val secondErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,70e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(270)",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val secondErrorInputResultState = ScientificCalculatorErrorState(secondErrorInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "4500,e-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(45)",
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
            mainString = "70000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2219599181369432780892227563596e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanr(7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-700,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,2219599181369432780892227563596",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanr(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanr(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val thirtyInputData = ScientificCalculatorDataEntity(
            mainString = "78539816339744830961566084581988,e-32",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val thirtyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(thirtyInputData)
        val thirtyInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanr(0,78539816339744830961566084581988)",
            prevState = prevState
        )
        val thirtyInputResultState = ScientificCalculatorFirstOperandReadState(thirtyInputResultData)

        val ninetyInputData = ScientificCalculatorDataEntity(
            mainString = "0,15707963267948966192313216916398e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val ninetyInputState = ScientificCalculatorFirstOperandPowerNumberInputState(ninetyInputData)
        val ninetyInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,15707963267948966192313216916398e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanr(1,5707963267948966192313216916398)",
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
            mainString = "700000,e-4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,9626105055051505823046404262119",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tang(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,70e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,9626105055051505823046404262119e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tang(-7,e+1)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val firstErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1000,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val firstErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(firstErrorInputData)
        val firstErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1000,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tang(100)",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val firstErrorInputResultState = ScientificCalculatorErrorState(firstErrorInputResultData)

        val secondErrorInputData = ScientificCalculatorDataEntity(
            mainString = "3,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val secondErrorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(secondErrorInputData)
        val secondErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,e+2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tang(3,e+2)",
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
            mainString = "300,e-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "71,565051177077989351572193720453",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atand(3)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,003e+3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-7,1565051177077989351572193720453e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atand(-3,e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val fortyFiveInputData = ScientificCalculatorDataEntity(
            mainString = "1000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
        )
        val fortyFiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(fortyFiveInputData)
        val fortyFiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "45",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atand(1)",
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
            mainString = "300,e-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2490457723982544258299170772811",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanr(3)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,0003e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,2490457723982544258299170772811e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanr(-3,e+0)",
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
            mainString = "3000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "79,516723530086654835080215244948",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atang(3)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-7,9516723530086654835080215244948e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atang(-3,e+0)",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val fortyFiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,0001e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
        )
        val fortyFiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(fortyFiveInputData)
        val fortyFiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "50",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atang(1)",
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
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val errorInputState = ScientificCalculatorFirstOperandPowerNumberInputState(errorInputData)
        val errorInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,e+4000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cube(3,e+4000)",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState
        )
        val errorInputResultState = ScientificCalculatorErrorState(errorInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,7e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cube(-3,e+0)",
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
            mainString = "19,5112e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "58",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cuberoot(195112)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-17576000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,6e+1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cuberoot(-1,7576e+4)",
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
            mainString = "195112,e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,95112e+9",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            isScientificNotation = true,
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-17576,e-5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,7576e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            isScientificNotation = true,
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
            mainString = "195112,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "195112,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputResultData)

        val positiveCommaInputData = ScientificCalculatorDataEntity(
            mainString = "195112,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveCommaInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveCommaInputData)
        val positiveCommaInputResultData = ScientificCalculatorDataEntity(
            mainString = "195112,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveCommaInputResultState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveCommaInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-17576,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-17576,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputResultData)

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
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(log(0)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorErrorState(zeroInputResultData)

        val fractionInputData = ScientificCalculatorDataEntity(
            mainString = "1,e-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val fractionInputState = ScientificCalculatorFirstOperandPowerNumberInputState(fractionInputData)
        val fractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(log(0,01)",
            prevState = prevState
        )
        val fractionInputResultState = ScientificCalculatorFirstOperandReadState(fractionInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(log(1,e+5)",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1000,e-3",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(log(-1,e+0)",
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
            mainString = "0,e+0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powten(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val fractionInputData = ScientificCalculatorDataEntity(
            mainString = "55000,e-4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val fractionInputState = ScientificCalculatorFirstOperandPowerNumberInputState(fractionInputData)
        val fractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "316227,76601683793319988935444327",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powten(5,5)",
            prevState = prevState
        )
        val fractionInputResultState = ScientificCalculatorFirstOperandReadState(fractionInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powten(1,e+5)",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputResultState = ScientificCalculatorErrorState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,000001e+6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val negativeInputState = ScientificCalculatorFirstOperandPowerNumberInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e-1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powten(-1,e+0)",
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
        val resultState = ScientificCalculatorInitialState(baseData.copy(
            memoryNumber = "6,9"
        ))
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "555,e+32",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            isScientificNotation = true
        )
        val positiveInputState = ScientificCalculatorFirstOperandPowerNumberInputState(positiveInputData)
        val memoryInputData = ScientificCalculatorDataEntity(
            mainString = "555,e-333",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState,
            memoryNumber = "6,9"
        )
        val memoryInputState = ScientificCalculatorFirstOperandPowerNumberInputState(memoryInputData)

        assertTrue(
            ReflectionEquals(baseState).matches(
                positiveInputState.clearAll(positiveInputData)
            ))
        assertTrue(
            ReflectionEquals(resultState).matches(
                memoryInputState.clearAll(memoryInputData)
            ))
    }
    */
}