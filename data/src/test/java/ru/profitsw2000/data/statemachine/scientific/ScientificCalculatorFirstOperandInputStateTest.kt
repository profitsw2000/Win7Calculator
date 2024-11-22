package ru.profitsw2000.data.statemachine.scientific

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.GENERAL_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
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

    @Test
    fun reciprocationTest() {
        val prevData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = 5.0
        )
        val prevState = ScientificCalculatorMathOperationState(prevData)
        val zeroInputData = ScientificCalculatorDataEntity(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(reciproc(0)",
            prevState = prevState,
            errorCode = DIVIDE_ON_ZERO_ERROR_CODE
        )
        val zeroInputResultState = ScientificCalculatorErrorState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "10,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(" +
                    "reciproc(10)",
            prevState = prevState
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-12,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,08",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(" +
                    "reciproc(-12,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            zeroInputState.reciprocOperation(zeroInputData)
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
        val prevData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = 5.0
        )
        val prevState = ScientificCalculatorMathOperationState(prevData)
        val zeroInputData = ScientificCalculatorDataEntity(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((",
            prevState = zeroInputState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputData = ScientificCalculatorDataEntity(
            mainString = "23,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val nonZeroInputState = ScientificCalculatorFirstOperandInputState(nonZeroInputData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "23",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER((",
            prevState = nonZeroInputState
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "6,35"
        )
        val commaInputState = ScientificCalculatorFirstOperandInputState(commaInputData)
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
            mainString = "33,"
        )
        val nullPrevState = ScientificCalculatorFirstOperandInputState(nullPrevData)
        val nullPrevResultData = ScientificCalculatorDataEntity(
            mainString = "33"
        )
        val nullPrevResultState = ScientificCalculatorFirstOperandReadState(nullPrevResultData)


        val prevSCFOISData = ScientificCalculatorDataEntity(
            mainString = "5,"
        )
        val prevSCFOISState = ScientificCalculatorFirstOperandInputState(prevSCFOISData)
        val currentSCFOISData = ScientificCalculatorDataEntity(
            mainString = "33,",
            historyString = "(",
            memoryNumber = 21.564,
            prevState = prevSCFOISState
        )
        val currentSCFOISState = ScientificCalculatorFirstOperandInputState(currentSCFOISData)
        val resultSCFOISData = ScientificCalculatorDataEntity(
            mainString = "33",
            historyString = "(33)",
            memoryNumber = 21.564
        )
        val resultSCFOISState = ScientificCalculatorFirstOperandReadState(resultSCFOISData)


        val prevSCFOPNISData = ScientificCalculatorDataEntity(
            mainString = "5,e+3"
        )
        val prevSCFOPNISState = ScientificCalculatorFirstOperandPowerNumberInputState(prevSCFOPNISData)
        val currentSCFOPNISData = currentSCFOISData.copy(
            prevState = prevSCFOPNISState
        )
        val currentSCFOPNISState = ScientificCalculatorFirstOperandInputState(currentSCFOPNISData)
        val resultSCFOPNISState = ScientificCalculatorFirstOperandReadState(resultSCFOISData)


        val prevSCFORSData = ScientificCalculatorDataEntity(
            mainString = "5"
        )
        val prevSCFORSState = ScientificCalculatorFirstOperandReadState(prevSCFORSData)
        val currentSCFORSData = currentSCFOISData.copy(
            prevState = prevSCFORSState
        )
        val currentSCFORSState = ScientificCalculatorFirstOperandInputState(currentSCFOPNISData)
        val resultSCFORSState = ScientificCalculatorFirstOperandReadState(resultSCFOISData)

        val prevSCISData = ScientificCalculatorDataEntity(
            mainString = "0"
        )
        val prevSCISState = ScientificCalculatorInitialState(prevSCISData)
        val currentSCISData = currentSCFOISData.copy(
            prevState = prevSCISState
        )
        val currentSCISState = ScientificCalculatorFirstOperandInputState(currentSCISData)
        val resultSCISState = ScientificCalculatorFirstOperandReadState(resultSCFOISData)


        val prevSCORSData = ScientificCalculatorDataEntity(
            mainString = "123"
        )
        val prevSCORSState = ScientificCalculatorOperationResultState(prevSCORSData)
        val currentSCORSData = currentSCFOISData.copy(
            prevState = prevSCORSState
        )
        val currentSCORSState = ScientificCalculatorFirstOperandInputState(currentSCORSData)
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
        val currentSCMOSState = ScientificCalculatorFirstOperandInputState(currentSCMOSData)
        val resultSCMOSData = resultSCFOISData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(33)"
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
        val currentSCSOISState = ScientificCalculatorFirstOperandInputState(currentSCSOISData)
        val resultSCSOISData = resultSCFOISData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(33)"
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
        val currentSCSOPNISState = ScientificCalculatorFirstOperandInputState(currentSCSOPNISData)
        val resultSCSOPNISData = resultSCFOISData.copy(
            historyString = "5,e+3$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(33)"
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
        val currentSCSORSState = ScientificCalculatorFirstOperandInputState(currentSCSORSData)
        val resultSCSORSData = resultSCFOISData.copy(
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(33)"
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
            mainString = "-12,",
            memoryNumber = 2.3
        )
        val negativeNumberState = ScientificCalculatorFirstOperandInputState(negativeNumberData)
        val negativeNumberResultData = ScientificCalculatorDataEntity(
            mainString = "-12,",
            historyString = "ln(-12)",
            memoryNumber = 2.3,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val negativeNumberResultState = ScientificCalculatorErrorState(negativeNumberResultData)

        val zeroNumberData = ScientificCalculatorDataEntity(
            mainString = "0,",
            memoryNumber = 2.3
        )
        val zeroNumberState = ScientificCalculatorFirstOperandInputState(zeroNumberData)
        val zeroNumberResultData = ScientificCalculatorDataEntity(
            mainString = "0,",
            historyString = "ln(0)",
            memoryNumber = 2.3,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val zeroNumberResultState = ScientificCalculatorErrorState(zeroNumberResultData)

        val positiveNumberData = ScientificCalculatorDataEntity(
            mainString = "6,",
            memoryNumber = 2.3
        )
        val positiveNumberState = ScientificCalculatorFirstOperandInputState(positiveNumberData)
        val positiveNumberResultData = ScientificCalculatorDataEntity(
            mainString = "1,791759469228055",
            historyString = "ln(6)",
            memoryNumber = 2.3
        )
        val positiveNumberResultState = ScientificCalculatorFirstOperandReadState(positiveNumberResultData)

        val firstState = ScientificCalculatorMathOperationState(baseData)
        val prevData = ScientificCalculatorDataEntity(
            mainString = "13,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = firstState
        )
        val prevState = ScientificCalculatorFirstOperandInputState(prevData)
        val prevResultData = ScientificCalculatorDataEntity(
            mainString = "2,564949357461537",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(ln(13)",
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
        val prevData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+",
        )
        val prevState = ScientificCalculatorMathOperationState(prevData)

        val zeroInputData = ScientificCalculatorDataEntity(
            mainString = "0,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val zeroInputState = ScientificCalculatorFirstOperandInputState(zeroInputData)
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(0)",
            prevState = prevState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "20,08553692318767",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(3)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val bigPositiveInputData = ScientificCalculatorDataEntity(
            mainString = "9999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val bigPositiveInputState = ScientificCalculatorFirstOperandInputState(bigPositiveInputData)
        val bigPositiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "9999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(9999)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val bigPositiveInputResultState = ScientificCalculatorErrorState(bigPositiveInputResultData)

        val bigNegativeInputData = ScientificCalculatorDataEntity(
            mainString = "-99999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val bigNegativeInputState = ScientificCalculatorFirstOperandInputState(bigNegativeInputData)
        val bigNegativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(powe(-99999)",
            prevState = prevState
        )
        val bigNegativeInputResultState = ScientificCalculatorFirstOperandReadState(bigNegativeInputResultData)

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
        val prevData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+",
        )
        val prevState = ScientificCalculatorMathOperationState(prevData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-33,452",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-33",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(Int(-33,452)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "33,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "33",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(Int(33)",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)


        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.integerOfNumber(negativeInputData)
        ))
        assertTrue(ReflectionEquals(commaInputResultState).matches(
            commaInputState.integerOfNumber(commaInputData)
        ))
    }

    @Test
    fun fractionOfNumberTest() {
        val prevData = ScientificCalculatorDataEntity(
            mainString = "5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+",
        )
        val prevState = ScientificCalculatorMathOperationState(prevData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-33,452",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,452",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(frac(-33,452)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "33,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(frac(33)",
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
}