package ru.profitsw2000.data.statemachine.general

import org.junit.Test
import org.junit.Assert.assertEquals
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorInitialState

class GeneralCalculatorBaseTest {
    @Test
    fun simpleNumber() {
        assertEquals(
            "1,2345678901234568e+22",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(12345678901234567890123.0)
        )
    }
}