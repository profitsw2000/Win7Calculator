package ru.profitsw2000.data.statemachine.scientific

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import ru.profitsw2000.data.constants.DEGREES_ANGLE_CODE
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.GENERAL_CALCULATOR_MAIN_STRING_MAX_DIGIT_NUMBER
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
import java.sql.Ref

class ScientificCalculatorFirstOperandInputStateTest {

    private val baseData = ScientificCalculatorDataEntity()
    private val baseState = ScientificCalculatorInitialState(baseData)

    private val prevData = ScientificCalculatorDataEntity(
        mainString = "5",
        historyString = "5$HISTORY_STRING_SPACE_LETTER+",
        scientificOperationType = ScientificOperationType.PLUS,
        operand = 5.0
    )
    private val prevState = ScientificCalculatorMathOperationState(prevData)

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

    @Test
    fun hyperbolicSinusCalculationTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-12,2458839965655",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinh(-3,2)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "-10,0178749274099",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinh(-3)",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "999999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val bigInputState = ScientificCalculatorFirstOperandInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "999999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinh(999999)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
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
            mainString = "-3,2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,87986358439691",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinh(-3,2)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,81844645923207",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinh(-3)",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "999999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val bigInputState = ScientificCalculatorFirstOperandInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "14,50865673852397",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinh(999999)",
            prevState = prevState
        )
        val bigInputResultState = ScientificCalculatorFirstOperandReadState(bigInputResultData)

        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.hyperbolicArcSinus(negativeInputData)
        ))
        assertTrue(ReflectionEquals(commaInputResultState).matches(
            commaInputState.hyperbolicArcSinus(commaInputData)
        ))
        assertTrue(ReflectionEquals(bigInputResultState).matches(
            bigInputState.hyperbolicArcSinus(bigInputData)
        ))
    }

    @Test
    fun degreesSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,939692620785908",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sind(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,93969262078591",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sind(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.sinus(positiveInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.sinus(negativeInputData, DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,773890681557889",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinr(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,77389068155789",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sinr(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.sinus(positiveInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.sinus(negativeInputData, RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,891006524188368",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sing(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,89100652418837",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sing(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.sinus(positiveInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.sinus(negativeInputData, GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun degreesArcSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "30",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(0,5)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-30",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(1,1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asind(-2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcSinus(positiveInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcSinus(negativeInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(positiveErrorInputResultState).matches(
            positiveErrorInputState.arcSinus(positiveErrorInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeErrorInputResultState).matches(
            negativeErrorInputState.arcSinus(negativeErrorInputData, DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansArcSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,523598775598299",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinr(0,5)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,5235987755983",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinr(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinr(1,1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asinr(-2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcSinus(positiveInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcSinus(negativeInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(positiveErrorInputResultState).matches(
            positiveErrorInputState.arcSinus(positiveErrorInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeErrorInputResultState).matches(
            negativeErrorInputState.arcSinus(negativeErrorInputData, RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsArcSinusTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "33,33333333333334",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asing(0,5)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-33,3333333333333",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asing(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asing(1,1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(asing(-2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcSinus(positiveInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcSinus(negativeInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(positiveErrorInputResultState).matches(
            positiveErrorInputState.arcSinus(positiveErrorInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeErrorInputResultState).matches(
            negativeErrorInputState.arcSinus(negativeErrorInputData, GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun squareNumberCalculationTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "6,6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "43,56",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(6,6)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-7,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "49",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(-7)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,e+300",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,e+300",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(sqr(1,e+300)",
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
            mainString = "6,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveIntInputState = ScientificCalculatorFirstOperandInputState(positiveIntInputData)
        val positiveIntInputResultData = ScientificCalculatorDataEntity(
            mainString = "720",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(6)",
            prevState = prevState
        )
        val positiveIntInputResultState = ScientificCalculatorFirstOperandReadState(positiveIntInputResultData)

        val positiveFractionInputData = ScientificCalculatorDataEntity(
            mainString = "5,6",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveFractionInputState = ScientificCalculatorFirstOperandInputState(positiveFractionInputData)
        val positiveFractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "344,7017962565533",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(5,6)",
            prevState = prevState
        )
        val positiveFractionInputResultState = ScientificCalculatorFirstOperandReadState(positiveFractionInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-1,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(-1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorErrorState(negativeInputResultData)

        val negativeFracInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeFracInputState = ScientificCalculatorFirstOperandInputState(negativeFracInputData)
        val negativeFracInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,685722612676315",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(-0,5)",
            prevState = prevState
        )
        val negativeFracInputResultState = ScientificCalculatorFirstOperandReadState(negativeFracInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1000",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(fact(1000)",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState
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
            mainString = "6,4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "6,24",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(dms(6,4)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.decimalToMinutes(positiveInputData)
        ))
    }

    @Test
    fun fromMinutesToDecimalFormatTest() {
        val underSixtyMinutesInputData = ScientificCalculatorDataEntity(
            mainString = "5,45",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val underSixtyMinutesInputState = ScientificCalculatorFirstOperandInputState(underSixtyMinutesInputData)
        val underSixtyMinutesInputResultData = ScientificCalculatorDataEntity(
            mainString = "5,75",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(deg(5,45)",
            prevState = prevState
        )
        val underSixtyMinutesInputResultState = ScientificCalculatorFirstOperandReadState(underSixtyMinutesInputResultData)

        val overSixtyMinutesInputData = ScientificCalculatorDataEntity(
            mainString = "8,9",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val overSixtyMinutesInputState = ScientificCalculatorFirstOperandInputState(overSixtyMinutesInputData)
        val overSixtyMinutesInputResultData = ScientificCalculatorDataEntity(
            mainString = "9,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(deg(8,9)",
            prevState = prevState
        )
        val overSixtyMinutesInputResultState = ScientificCalculatorFirstOperandReadState(overSixtyMinutesInputResultData)

        assertTrue(ReflectionEquals(underSixtyMinutesInputResultState).matches(
            underSixtyMinutesInputState.minutesToDecimal(underSixtyMinutesInputData)
        ))
        assertTrue(ReflectionEquals(overSixtyMinutesInputResultState).matches(
            overSixtyMinutesInputState.minutesToDecimal(overSixtyMinutesInputData)
        ))
    }

    @Test
    fun hyperbolicCosineTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "12,28664620054386",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosh(-3,2)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "12,28664620054386",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosh(3,2)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "-6,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "201,7156361224559",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosh(-6)",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "999999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val bigInputState = ScientificCalculatorFirstOperandInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "999999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosh(999999)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
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
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val errorInputState = ScientificCalculatorFirstOperandInputState(errorInputData)
        val errorInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosh(0,5)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val errorInputResultState = ScientificCalculatorErrorState(errorInputResultData)

        val commaInputData = ScientificCalculatorDataEntity(
            mainString = "3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val commaInputState = ScientificCalculatorFirstOperandInputState(commaInputData)
        val commaInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,762747174039086",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosh(3)",
            prevState = prevState
        )
        val commaInputResultState = ScientificCalculatorFirstOperandReadState(commaInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "999999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val bigInputState = ScientificCalculatorFirstOperandInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "14,50865673852347",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosh(999999)",
            prevState = prevState
        )
        val bigInputResultState = ScientificCalculatorFirstOperandReadState(bigInputResultData)

        assertTrue(ReflectionEquals(errorInputResultState).matches(
            errorInputState.hyperbolicArcCosine(errorInputData)
        ))
        assertTrue(ReflectionEquals(commaInputResultState).matches(
            commaInputState.hyperbolicArcCosine(commaInputData)
        ))
        assertTrue(ReflectionEquals(bigInputResultState).matches(
            bigInputState.hyperbolicArcCosine(bigInputData)
        ))
    }

    @Test
    fun degreesCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,342020143325669",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosd(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,342020143325669",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosd(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.cosine(positiveInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.cosine(negativeInputData, DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,6333192030863",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosr(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,6333192030863",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosr(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.cosine(positiveInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.cosine(negativeInputData, RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,453990499739547",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosg(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,453990499739547",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cosg(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.cosine(positiveInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.cosine(negativeInputData, GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun degreesArcCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "60",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(0,5)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "120",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(1,1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosd(-2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

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
    }

    @Test
    fun radiansArcCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,047197551196598",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosr(0,5)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,094395102393196",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosr(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosr(1,1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosr(-2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcCosine(positiveInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcCosine(negativeInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(positiveErrorInputResultState).matches(
            positiveErrorInputState.arcCosine(positiveErrorInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeErrorInputResultState).matches(
            negativeErrorInputState.arcCosine(negativeErrorInputData, RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsArcCosineTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "66,66666666666667",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosg(0,5)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "133,3333333333333",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosg(-0,5)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveErrorInputData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveErrorInputState = ScientificCalculatorFirstOperandInputState(positiveErrorInputData)
        val positiveErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosg(1,1)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val positiveErrorInputResultState = ScientificCalculatorErrorState(positiveErrorInputResultData)

        val negativeErrorInputData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeErrorInputState = ScientificCalculatorFirstOperandInputState(negativeErrorInputData)
        val negativeErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(acosg(-2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val negativeErrorInputResultState = ScientificCalculatorErrorState(negativeErrorInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcCosine(positiveInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcCosine(negativeInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(positiveErrorInputResultState).matches(
            positiveErrorInputState.arcCosine(positiveErrorInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeErrorInputResultState).matches(
            negativeErrorInputState.arcCosine(negativeErrorInputData, GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun piNumberTest() {
        val piData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val piState = ScientificCalculatorFirstOperandInputState(piData)
        val piResultData = ScientificCalculatorDataEntity(
            mainString = "3,141592653589793",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val piResultState = ScientificCalculatorFirstOperandReadState(piResultData)

        assertTrue(ReflectionEquals(piResultState).matches(
            piState.piNumber(piData)
        ))
    }

    @Test
    fun doublePiNumberTest() {
        val piData = ScientificCalculatorDataEntity(
            mainString = "-0,5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val piState = ScientificCalculatorFirstOperandInputState(piData)
        val piResultData = ScientificCalculatorDataEntity(
            mainString = "6,283185307179586",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val piResultState = ScientificCalculatorFirstOperandReadState(piResultData)

        assertTrue(ReflectionEquals(piResultState).matches(
            piState.doublePiNumber(piData)
        ))
    }

    @Test
    fun hyperbolicTangentTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,99505475368673",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanh(-3)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,2",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,996682397839651",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanh(3,2)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "999999,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val bigInputState = ScientificCalculatorFirstOperandInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanh(999999)",
            prevState = prevState
        )
        val bigInputResultState = ScientificCalculatorFirstOperandReadState(bigInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.hyperbolicTangent(positiveInputData)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.hyperbolicTangent(negativeInputData)
        ))
        assertTrue(ReflectionEquals(bigInputResultState).matches(
            bigInputState.hyperbolicTangent(bigInputData)
        ))
    }

    @Test
    fun hyperbolicArcTangentTest() {
        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-0,9",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,47221948958322",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanh(-0,9)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "0,9",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,47221948958322",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanh(0,9)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val bigInputData = ScientificCalculatorDataEntity(
            mainString = "2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val bigInputState = ScientificCalculatorFirstOperandInputState(bigInputData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanh(2)",
            errorCode = INVALID_INPUT_ERROR_CODE,
            prevState = prevState
        )
        val bigInputResultState = ScientificCalculatorErrorState(bigInputResultData)

        val oneInputData = ScientificCalculatorDataEntity(
            mainString = "1,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val oneInputState = ScientificCalculatorFirstOperandInputState(oneInputData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanh(1)",
            errorCode = DIVIDE_ON_ZERO_ERROR_CODE,
            prevState = prevState
        )
        val oneInputResultState = ScientificCalculatorErrorState(oneInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.hyperbolicArcTangent(positiveInputData)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.hyperbolicArcTangent(negativeInputData)
        ))
        assertTrue(ReflectionEquals(bigInputResultState).matches(
            bigInputState.hyperbolicArcTangent(bigInputData)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            oneInputState.hyperbolicArcTangent(oneInputData)
        ))
    }

    @Test
    fun degreesTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,747477419454622",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-2,74747741945462",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val firstErrorInputData = ScientificCalculatorDataEntity(
            mainString = "90,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val firstErrorInputState = ScientificCalculatorFirstOperandInputState(firstErrorInputData)
        val firstErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "90,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(90)",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val firstErrorInputResultState = ScientificCalculatorErrorState(firstErrorInputResultData)

        val secondErrorInputData = ScientificCalculatorDataEntity(
            mainString = "270,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val secondErrorInputState = ScientificCalculatorFirstOperandInputState(secondErrorInputData)
        val secondErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "270,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tand(270)",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val secondErrorInputResultState = ScientificCalculatorErrorState(secondErrorInputResultData)

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
    }

    @Test
    fun radiansTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,221959918136943",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanr(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,22195991813694",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tanr(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.tangent(positiveInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.tangent(negativeInputData, RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,96261050550515",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tang(70)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-70,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,96261050550515",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tang(-70)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        val firstErrorInputData = ScientificCalculatorDataEntity(
            mainString = "100,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val firstErrorInputState = ScientificCalculatorFirstOperandInputState(firstErrorInputData)
        val firstErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "100,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tang(100)",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val firstErrorInputResultState = ScientificCalculatorErrorState(firstErrorInputResultData)

        val secondErrorInputData = ScientificCalculatorDataEntity(
            mainString = "300,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val secondErrorInputState = ScientificCalculatorFirstOperandInputState(secondErrorInputData)
        val secondErrorInputResultData = ScientificCalculatorDataEntity(
            mainString = "300,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(tang(300)",
            prevState = prevState,
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val secondErrorInputResultState = ScientificCalculatorErrorState(secondErrorInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.tangent(positiveInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.tangent(negativeInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(firstErrorInputResultState).matches(
            firstErrorInputState.tangent(firstErrorInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(secondErrorInputResultState).matches(
            secondErrorInputState.tangent(secondErrorInputData, GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun degreesArcTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "71,56505117707799",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atand(3)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-71,565051177078",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atand(-3)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcTangent(positiveInputData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcTangent(negativeInputData, DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansArcTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,249045772398254",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanr(3)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,24904577239825",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atanr(-3)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcTangent(positiveInputData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcTangent(negativeInputData, RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsArcTangentTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "79,51672353008667",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atang(3)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-79,5167235300867",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(atang(-3)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.arcTangent(positiveInputData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.arcTangent(negativeInputData, GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun cubeNumberTest() {
        val errorInputData = ScientificCalculatorDataEntity(
            mainString = "3,e+200",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val errorInputState = ScientificCalculatorFirstOperandInputState(errorInputData)
        val errorInputResultData = ScientificCalculatorDataEntity(
            mainString = "3,e+200",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cube(3,e+200)",
            errorCode = OVERFLOW_ERROR_CODE,
            prevState = prevState
        )
        val errorInputResultState = ScientificCalculatorErrorState(errorInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-3,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-27",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cube(-3)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(errorInputResultState).matches(
            errorInputState.cubeNumber(errorInputData)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.cubeNumber(negativeInputData)
        ))
    }

    @Test
    fun cubeRootNumberTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "195112,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "58",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cuberoot(195112)",
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-17576,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-26",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(cuberoot(-17576)",
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.cubeRoot(positiveInputData)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.cubeRoot(negativeInputData)
        ))
    }

    @Test
    fun formatChangeTest() {
        val positiveInputData = ScientificCalculatorDataEntity(
            mainString = "195112,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val positiveInputState = ScientificCalculatorFirstOperandInputState(positiveInputData)
        val positiveInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,95112e+5",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            isScientificNotation = true,
            prevState = prevState
        )
        val positiveInputResultState = ScientificCalculatorFirstOperandReadState(positiveInputResultData)

        val negativeInputData = ScientificCalculatorDataEntity(
            mainString = "-17576,",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            prevState = prevState
        )
        val negativeInputState = ScientificCalculatorFirstOperandInputState(negativeInputData)
        val negativeInputResultData = ScientificCalculatorDataEntity(
            mainString = "-1,7576e+4",
            historyString = "5$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            isScientificNotation = true,
            prevState = prevState
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)

        assertTrue(ReflectionEquals(positiveInputResultState).matches(
            positiveInputState.fixedToExponentialFormat(positiveInputData)
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            negativeInputState.fixedToExponentialFormat(negativeInputData)
        ))
    }
}