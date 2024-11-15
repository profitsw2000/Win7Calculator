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
    }
}