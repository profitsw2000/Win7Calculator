package ru.profitsw2000.data.statemachine.scientific

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.internal.matchers.Equals
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandReadState

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
}