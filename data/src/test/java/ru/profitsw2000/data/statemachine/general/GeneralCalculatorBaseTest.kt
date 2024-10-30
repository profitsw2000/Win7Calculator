package ru.profitsw2000.data.statemachine.general

import org.junit.Test
import org.junit.Assert.assertEquals
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorInitialState

class GeneralCalculatorBaseTest {

    @Test
    fun simpleNumber() {
        assertEquals(
            "123,45",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(123.45)
        )
    }

    @Test
    fun overSixtyDecimal() {
        assertEquals(
            "0,123456789012346",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(0.123456789012345678)
        )
    }

    @Test
    fun fourIntSixtyDecimal() {
        assertEquals(
            "1234,123456789012",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(1234.1234567890123456)
        )
    }

    @Test
    fun negativeNumber() {
        assertEquals(
            "-1234,56789",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(-1234.567890)
        )
    }

    @Test
    fun veryBigPositiveNumber() {
        assertEquals(
            "1e+16",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(10000000000000000.0)
        )
    }

    @Test
    fun veryBigNegativeNumber() {
        assertEquals(
            "-1e+16",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(-10000000000000000.0)
        )
    }

    @Test
    fun verySmallPositiveNumber() {
        assertEquals(
            "9e-16",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(0.00000000000000009)
        )
    }

    @Test
    fun verySmallNegativeNumber() {
        assertEquals(
            "-9e-16",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(-0.00000000000000009)
        )
    }

}