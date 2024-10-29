package ru.profitsw2000.data.statemachine.general

import org.junit.Test
import org.junit.Assert.assertEquals
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorInitialState

class GeneralCalculatorBaseTest {
    @Test
    fun simpleNumber() {
        assertEquals(
            "123456789012345,1",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(123456789012345.123)
        )
    }
}