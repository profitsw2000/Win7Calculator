package ru.profitsw2000.data.statemachine.general

import org.junit.Test
import org.junit.Assert.assertEquals
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorInitialState

private val baseData = GeneralCalculatorDataEntity()
private val numberInMemoryData = GeneralCalculatorDataEntity(memoryNumber = 15.5)

class ScientificCalculatorInitialStateTest {

    @Test
    fun clearMemoryTest() {
        assertEquals(
            null,
            GeneralCalculatorInitialState(numberInMemoryData).clearMemory(numberInMemoryData).generalCalculatorDataEntity.memoryNumber
        )
    }
}