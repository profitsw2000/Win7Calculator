package ru.profitsw2000.data.statemachine.general

import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandReadState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorInitialState

class ScientificCalculatorInitialStateTest {

    private val baseInitialState = ScientificCalculatorInitialState(
        ScientificCalculatorDataEntity()
    )

    @Test
    fun clearMemoryTest() {
        val numberInMemoryData = ScientificCalculatorDataEntity(memoryNumber = 15.0)
        val numberInMemoryInitialState = ScientificCalculatorInitialState(
            numberInMemoryData
        )

        assertTrue(ReflectionEquals(baseInitialState).matches(
            numberInMemoryInitialState.clearMemory(numberInMemoryData)
        ))
        assertFalse(ReflectionEquals(numberInMemoryInitialState).matches(
            numberInMemoryInitialState.clearMemory(numberInMemoryData)
        ))
    }

    @Test
    fun readMemoryTest() {
        val trueFirstOperand = ScientificCalculatorFirstOperandReadState(
            ScientificCalculatorDataEntity(
                mainString = "15",
                memoryNumber = 15.0
            )
        )
        val falseFirstOperand = ScientificCalculatorFirstOperandReadState(
            ScientificCalculatorDataEntity(
                mainString = "1",
                memoryNumber = 15.0
            )
        )
        val numberInMemoryData = ScientificCalculatorDataEntity(memoryNumber = 15.0)
        val numberInMemoryInitialState = ScientificCalculatorInitialState(
            numberInMemoryData
        )

        assertTrue(ReflectionEquals(trueFirstOperand).matches(
                numberInMemoryInitialState.readMemory(numberInMemoryData)
            )
        )
        assertFalse(ReflectionEquals(falseFirstOperand).matches(
                numberInMemoryInitialState.readMemory(numberInMemoryData)
            )
        )
    }

    @Test
    fun savedToMemoryTest() {
        val falseInitialState = ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity(
                memoryNumber = 15.0
            )
        )

        assertTrue(ReflectionEquals(baseInitialState).matches(
            baseInitialState.readMemory(ScientificCalculatorDataEntity())
        ))
        assertFalse(ReflectionEquals(falseInitialState).matches(
            baseInitialState.readMemory(ScientificCalculatorDataEntity())
        ))
    }
}