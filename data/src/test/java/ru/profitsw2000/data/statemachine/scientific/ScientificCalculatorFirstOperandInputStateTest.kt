package ru.profitsw2000.data.statemachine.scientific

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorErrorState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandInputState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandReadState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorInitialState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorMathOperationState

class ScientificCalculatorFirstOperandInputStateTest {

    private val baseData = ScientificCalculatorDataEntity()
    private val baseState = ScientificCalculatorInitialState(baseData)

    @Test
    fun clearMemoryTest() {
        val numberInMemoryData = ScientificCalculatorDataEntity(
            mainString = "55,",
            memoryNumber = 15.0
        )
        val clearedMemoryData = ScientificCalculatorDataEntity(
            mainString = "55"
        )
        val numberInMemoryState = ScientificCalculatorFirstOperandInputState(
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
    fun backspaceButtonTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0"
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "-55"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "-5"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandInputState(nonZeroInputResultData)
        val zeroCommaInputData = ScientificCalculatorDataEntity(
            mainString = "0,"
        )
        val zerocommaInputState = ScientificCalculatorFirstOperandInputState(zeroCommaInputData)
        val zeroMinusCommaInputData = ScientificCalculatorDataEntity(
            mainString = "-0,"
        )
        val zerocommaMinusInputState = ScientificCalculatorFirstOperandInputState(zeroMinusCommaInputData)

        assertTrue(ReflectionEquals(zeroInputState).matches(
            zeroInputState.clearDigit(zeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.clearDigit(nonZeroInputData)
        ))
        assertTrue(ReflectionEquals(zeroInputState).matches(
            zerocommaInputState.clearDigit(zeroCommaInputData)
        ))
        assertTrue(ReflectionEquals(zeroInputState).matches(
            nonZeroInputResultState.clearDigit(nonZeroInputResultData)
        ))
        assertTrue(ReflectionEquals(zeroInputState).matches(
            zerocommaMinusInputState.clearDigit(zeroMinusCommaInputData)
        ))
    }

    @Test
    fun clearEnteredNumberTest() {
        val zeroResultData = ScientificCalculatorDataEntity(
            mainString = "0"
        )
        val zeroResultState = ScientificCalculatorFirstOperandInputState(zeroResultData)
        val nonZeroResultData = ScientificCalculatorDataEntity(
            mainString = "-333333"
        )
        val nonZeroResultState = ScientificCalculatorFirstOperandInputState(nonZeroResultData)
        val historyData = ScientificCalculatorDataEntity(
            mainString = "22,33",
            historyString = "43 + ("
        )
        val historyState = ScientificCalculatorFirstOperandInputState(historyData)
        val historyResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "43 + ("
        )
        val historyResultState = ScientificCalculatorFirstOperandInputState(historyResultData)

        assertTrue(ReflectionEquals(zeroResultState).matches(
            nonZeroResultState.clearEntered(nonZeroResultData)
        ))
        assertTrue(ReflectionEquals(historyResultState).matches(
            historyState.clearEntered(historyData)
        ))
        assertFalse(ReflectionEquals(zeroResultState).matches(
            historyState.clearEntered(historyData)
        ))
    }

    @Test
    fun readMemoryTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            memoryNumber = 3.99,
            prevState = baseState
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,99",
            memoryNumber = 3.99,
            prevState = baseState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "12,54",
            memoryNumber = 3.99
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,99",
            memoryNumber = 3.99
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.readMemory(zeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.readMemory(nonZeroInputData)
        ))
    }

    @Test
    fun saveToMemoryTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            prevState = baseState
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputMemoryData = ScientificCalculatorDataEntity(
            memoryNumber = 2.35
        )
        val zeroInputMemoryState = ScientificCalculatorFirstOperandInputState(zeroInputMemoryData)
        val zeroInputMemoryResultState = ScientificCalculatorFirstOperandReadState(
            ScientificCalculatorDataEntity()
        )
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "35,23"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "35,23",
            memoryNumber = 35.23
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val nonZeroInputMemoryData = ScientificCalculatorDataEntity(
            mainString = "7,68",
            memoryNumber = 35.23
        )
        val nonZeroInputMemoryState = ScientificCalculatorFirstOperandInputState(nonZeroInputMemoryData)
        val nonZeroInputMemoryResultData = ScientificCalculatorDataEntity(
            mainString = "7,68",
            memoryNumber = 7.68
        )
        val nonZeroInputMemoryResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputMemoryResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.saveToMemory(zeroInputData)
        ))
        assertTrue(ReflectionEquals(zeroInputMemoryResultState).matches(
            zeroInputMemoryState.saveToMemory(zeroInputMemoryData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.saveToMemory(nonZeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputMemoryResultState).matches(
            nonZeroInputMemoryState.saveToMemory(nonZeroInputMemoryData)
        ))
    }

    @Test
    fun addToMemoryTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            prevState = baseState
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputMemoryData = ScientificCalculatorDataEntity(
            memoryNumber = 2.35
        )
        val zeroInputMemoryState = ScientificCalculatorFirstOperandInputState(zeroInputMemoryData)
        val zeroInputMemoryResultState = ScientificCalculatorFirstOperandReadState(
            ScientificCalculatorDataEntity(
                memoryNumber = 2.35
            )
        )
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "35,23"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "35,23",
            memoryNumber = 35.23
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val nonZeroInputMemoryData = ScientificCalculatorDataEntity(
            mainString = "7,68",
            memoryNumber = 35.23
        )
        val nonZeroInputMemoryState = ScientificCalculatorFirstOperandInputState(nonZeroInputMemoryData)
        val nonZeroInputMemoryResultData = ScientificCalculatorDataEntity(
            mainString = "7,68",
            memoryNumber = 42.91
        )
        val nonZeroInputMemoryResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputMemoryResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.addNumberToMemory(zeroInputData)
        ))
        assertTrue(ReflectionEquals(zeroInputMemoryResultState).matches(
            zeroInputMemoryState.addNumberToMemory(zeroInputMemoryData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.addNumberToMemory(nonZeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputMemoryResultState).matches(
            nonZeroInputMemoryState.addNumberToMemory(nonZeroInputMemoryData)
        ))
    }

    @Test
    fun subtractFromMemoryTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            prevState = baseState
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputData)
        val zeroInputMemoryData = ScientificCalculatorDataEntity(
            memoryNumber = 2.35
        )
        val zeroInputMemoryState = ScientificCalculatorFirstOperandInputState(zeroInputMemoryData)
        val zeroInputMemoryResultState = ScientificCalculatorFirstOperandReadState(
            ScientificCalculatorDataEntity(
                memoryNumber = 2.35
            )
        )
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "35,23"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "35,23",
            memoryNumber = -35.23
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val nonZeroInputMemoryData = ScientificCalculatorDataEntity(
            mainString = "7,68",
            memoryNumber = 35.23
        )
        val nonZeroInputMemoryState = ScientificCalculatorFirstOperandInputState(nonZeroInputMemoryData)
        val nonZeroInputMemoryResultData = ScientificCalculatorDataEntity(
            mainString = "7,68",
            memoryNumber = 27.55
        )
        val nonZeroInputMemoryResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputMemoryResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.subtractNumberFromMemory(zeroInputData)
        ))
        assertTrue(ReflectionEquals(zeroInputMemoryResultState).matches(
            zeroInputMemoryState.subtractNumberFromMemory(zeroInputMemoryData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.subtractNumberFromMemory(nonZeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputMemoryResultState).matches(
            nonZeroInputMemoryState.subtractNumberFromMemory(nonZeroInputMemoryData)
        ))
    }

    @Test
    fun changeSignTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            prevState = baseState
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "6,427"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "-6,427"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandInputState(nonZeroInputResultData)
        val nonZeroNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,68"
        )
        val nonZeroNegativeInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,68"
        )
        val nonZeroNegativeInputResultState = ScientificCalculatorFirstOperandInputState(nonZeroNegativeInputResultData)

        assertTrue(ReflectionEquals(zeroInputState).matches(
            zeroInputState.negateOperand(zeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.negateOperand(nonZeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroNegativeInputResultState).matches(
            nonZeroNegativeInputState.negateOperand(nonZeroNegativeInputData)
        ))
    }

    @Test
    fun squareRootTest() {
        val zeroInputData = ScientificCalculatorDataEntity(
            prevState = baseState
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            historyString = "sqrt(0)",
            prevState = baseState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "5,76"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,4",
            historyString = "sqrt(5,76)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val nonZeroNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-5,68"
        )
        val nonZeroNegativeInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-5,68",
            historyString = "sqrt(-5,68)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val nonZeroNegativeInputResultState = ScientificCalculatorErrorState(nonZeroNegativeInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.calculateSquareRoot(zeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            nonZeroInputState.calculateSquareRoot(nonZeroInputData)
        ))
        assertTrue(ReflectionEquals(nonZeroNegativeInputResultState).matches(
            nonZeroNegativeInputState.calculateSquareRoot(nonZeroNegativeInputData)
        ))
    }

    @Test
    fun digitInputTest() {
        val zeroState = ScientificCalculatorFirstOperandInputState(baseData)
        val commaToZeroResultData = ScientificCalculatorDataEntity(
            mainString = "0,"
        )
        val commaToZeroResultState = ScientificCalculatorFirstOperandInputState(commaToZeroResultData)
        val nonZeroToZeroResultData = ScientificCalculatorDataEntity(
            mainString = "4"
        )
        val nonZeroToZeroResultState = ScientificCalculatorFirstOperandInputState(nonZeroToZeroResultData)
        val commaToNonZeroData = ScientificCalculatorDataEntity(
            mainString = "43"
        )
        val commaToNonZeroState = ScientificCalculatorFirstOperandInputState(commaToNonZeroData)
        val commaToNonZeroResultData = ScientificCalculatorDataEntity(
            mainString = "43,"
        )
        val commaToNonZeroResultState = ScientificCalculatorFirstOperandInputState(commaToNonZeroResultData)
        val nonZeroToNonZeroData = ScientificCalculatorDataEntity(
            mainString = "43"
        )
        val nonZeroToNonZeroState = ScientificCalculatorFirstOperandInputState(nonZeroToNonZeroData)
        val nonZeroToNonZeroResultData = ScientificCalculatorDataEntity(
            mainString = "439"
        )
        val nonZeroToNonZeroResultState = ScientificCalculatorFirstOperandInputState(nonZeroToNonZeroResultData)
        val commaToNegativeData = ScientificCalculatorDataEntity(
            mainString = "-75"
        )
        val commaToNegativeState = ScientificCalculatorFirstOperandInputState(commaToNegativeData)
        val commaToNegativeResultData = ScientificCalculatorDataEntity(
            mainString = "-75,"
        )
        val commaToNegativeResultState = ScientificCalculatorFirstOperandInputState(commaToNegativeResultData)
        val nonZeroToNegativeData = ScientificCalculatorDataEntity(
            mainString = "-23"
        )
        val nonZeroToNegativeState = ScientificCalculatorFirstOperandInputState(nonZeroToNegativeData)
        val nonZeroToNegativeResultData = ScientificCalculatorDataEntity(
            mainString = "-239"
        )
        val nonZeroToNegativeResultState = ScientificCalculatorFirstOperandInputState(nonZeroToNegativeResultData)
        val longNumberData = ScientificCalculatorDataEntity(
            mainString = "123456789012345"
        )
        val longNumberState = ScientificCalculatorFirstOperandInputState(longNumberData)
        val maxNumberData = ScientificCalculatorDataEntity(
            mainString = "1234567890123456"
        )
        val maxNumberState = ScientificCalculatorFirstOperandInputState(maxNumberData)
        val longNumberCommaData = ScientificCalculatorDataEntity(
            mainString = "123456789012345,"
        )
        val longNumberCommaState = ScientificCalculatorFirstOperandInputState(longNumberCommaData)
        val maxNumberCommaData = ScientificCalculatorDataEntity(
            mainString = "123456789012345,6"
        )
        val maxNumberCommaState = ScientificCalculatorFirstOperandInputState(maxNumberCommaData)

        assertTrue(ReflectionEquals(zeroState).matches(
            zeroState.inputDigit(baseData, "0")
        ))
        assertTrue(ReflectionEquals(commaToZeroResultState).matches(
            zeroState.inputDigit(baseData, ",")
        ))
        assertTrue(ReflectionEquals(nonZeroToZeroResultState).matches(
            zeroState.inputDigit(baseData, "4")
        ))
        assertTrue(ReflectionEquals(commaToNonZeroResultState).matches(
            commaToNonZeroState.inputDigit(commaToNonZeroData, ",")
        ))
        assertTrue(ReflectionEquals(nonZeroToNonZeroResultState).matches(
            nonZeroToNonZeroState.inputDigit(nonZeroToNonZeroData, "9")
        ))
        assertTrue(ReflectionEquals(commaToNegativeResultState).matches(
            commaToNegativeState.inputDigit(commaToNegativeData, ",")
        ))
        assertTrue(ReflectionEquals(nonZeroToNegativeResultState).matches(
            nonZeroToNegativeState.inputDigit(nonZeroToNegativeData, "9")
        ))
        assertTrue(ReflectionEquals(commaToNonZeroResultState).matches(
            commaToNonZeroResultState.inputDigit(commaToNonZeroResultData, ",")
        ))
        assertTrue(ReflectionEquals(commaToNegativeResultState).matches(
            commaToNegativeResultState.inputDigit(commaToNegativeResultData, ",")
        ))
        assertTrue(ReflectionEquals(maxNumberState).matches(
            longNumberState.inputDigit(longNumberData, "6")
        ))
        assertTrue(ReflectionEquals(maxNumberState).matches(
            maxNumberState.inputDigit(maxNumberData, "7")
        ))
        assertTrue(ReflectionEquals(maxNumberCommaState).matches(
            longNumberCommaState.inputDigit(longNumberCommaData, "6")
        ))
        assertTrue(ReflectionEquals(maxNumberCommaState).matches(
            maxNumberCommaState.inputDigit(maxNumberCommaData, "7")
        ))
        assertTrue(ReflectionEquals(longNumberCommaState).matches(
            longNumberCommaState.inputDigit(longNumberCommaData, ",")
        ))
    }

    @Test
    fun mathOperationTest() {
        val zeroInputData = baseData
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            historyString = "0$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = 0.0
        )
        val zeroInputResultState = ScientificCalculatorMathOperationState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "4,98"
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "4,98",
            historyString = "4,98$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = 4.98
        )
        val nonZeroInputResultState = ScientificCalculatorMathOperationState(nonZeroInputResultData)
        val nonZeroCommaInputData = ScientificCalculatorDataEntity(
            mainString = "498,"
        )
        val nonZeroCommaInputState = ScientificCalculatorFirstOperandInputState(nonZeroCommaInputData)
        val nonZeroCommaInputResultData = ScientificCalculatorDataEntity(
            mainString = "498",
            historyString = "498$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = 498.0
        )
        val nonZeroCommaInputResultState = ScientificCalculatorMathOperationState(nonZeroCommaInputResultData)
        val prevInputData = ScientificCalculatorDataEntity(
            mainString = "56",
            historyString = "0$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = zeroInputResultState
        )
        val prevInputState = ScientificCalculatorFirstOperandInputState(prevInputData)
        val prevInputResultData = ScientificCalculatorDataEntity(
            mainString = "56",
            historyString = "0$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(56$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = 56.0,
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
        assertTrue(ReflectionEquals(prevInputResultState).matches(
            prevInputState.primitiveMathOperation(prevInputData, ScientificOperationType.PLUS, "+")
        ))
    }
}