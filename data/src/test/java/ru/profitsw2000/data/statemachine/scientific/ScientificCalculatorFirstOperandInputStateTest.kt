package ru.profitsw2000.data.statemachine.scientific

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandInputState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandReadState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorInitialState

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
}