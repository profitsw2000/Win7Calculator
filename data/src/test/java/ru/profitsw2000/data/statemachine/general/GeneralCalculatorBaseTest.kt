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
            "1,e+16",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(10000000000000000.0)
        )
    }

    @Test
    fun veryBigNegativeNumber() {
        assertEquals(
            "-1,e+16",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(-10000000000000000.0)
        )
    }

    @Test
    fun verySmallPositiveNumber() {
        assertEquals(
            "9,e-17",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(0.00000000000000009)
        )
    }

    @Test
    fun verySmallNegativeNumber() {
        assertEquals(
            "-9,e-17",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(-0.00000000000000009)
        )
    }

    @Test
    fun veryBigPositiveNumberManyDigits() {
        assertEquals(
            "1,234567890123457e+19",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(12345678901234567890.0)
        )
    }

    @Test
    fun veryBigNegativeNumberManyDigits() {
        assertEquals(
            "-1,234567890123457e+19",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(-12345678901234567890.0)
        )
    }

    @Test
    fun verySmallPositiveNumberManyDigits() {
        assertEquals(
            "1,234567890123457e-17",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(0.00000000000000001234567890123456789)
        )
    }

    @Test
    fun verySmallNegativeNumberManyDigits() {
        assertEquals(
            "-1,234567890123457e-17",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(-0.00000000000000001234567890123456789)
        )
    }

    @Test
    fun simpleNumberScNot() {
        assertEquals(
            "1,2345e+4",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(12345.0, true)
        )
    }

    @Test
    fun oneDigitNumberScNot() {
        assertEquals(
            "5,e+0",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(5.0, true)
        )
    }

    @Test
    fun oneDigitNegativeNumberScNot() {
        assertEquals(
            "-5,e+0",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(-5.0, true)
        )
    }

    @Test
    fun multipleDigitNumberScNot() {
        assertEquals(
            "1,234567e+2",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(123.4567, true)
        )
    }

    @Test
    fun multipleDigitNegativeNumberScNot() {
        assertEquals(
            "-1,234567e+2",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(-123.4567, true)
        )
    }

    @Test
    fun smallFractionalNumberScNot() {
        assertEquals(
            "1,234567e-4",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(0.0001234567, true)
        )
    }

    @Test
    fun fractionalNumberScNot() {
        assertEquals(
            "1,230001234567e+2",
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).doubleToCalculatorString(123.0001234567, true)
        )
    }

    @Test
    fun simpleNumberRev() {
        assertEquals(
            123.45,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("123,45"),
            0.0
        )
    }

    @Test
    fun overSixtyDecimalRev() {
        assertEquals(
            0.12345678901234568,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("0,1234567890123456789"),
            0.0
        )
    }

    @Test
    fun fourIntSixtyDecimalRev() {
        assertEquals(
            1234.123456789012,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("1234,123456789012"),
            0.0
        )
    }

    @Test
    fun negativeNumberRev() {
        assertEquals(
            -1234.567890,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("-1234,56789"),
            0.0
        )
    }

    @Test
    fun veryBigPositiveNumberRev() {
        assertEquals(
            10000000000000000.0,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("1,e+16"),
            0.0
        )
    }

    @Test
    fun veryBigNegativeNumberRev() {
        assertEquals(
            -10000000000000000.0,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("-1,e+16"),
            0.0
        )
    }

    @Test
    fun verySmallPositiveNumberRev() {
        assertEquals(
            0.00000000000000009,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("9,e-17"),
            0.0
        )
    }

    @Test
    fun verySmallNegativeNumberRev() {
        assertEquals(
            -0.00000000000000009,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("-9,e-17"),
            0.0
        )
    }

    @Test
    fun veryBigPositiveNumberManyDigitsRev() {
        assertEquals(
            1.2345678901234567E19,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("1,2345678901234567e+19"),
            0.0
        )
    }

    @Test
    fun veryBigNegativeNumberManyDigitsRev() {
        assertEquals(
            -1.2345678901234567E19,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("-1,2345678901234567e+19"),
            0.0
        )
    }

    @Test
    fun verySmallPositiveNumberManyDigitsRev() {
        assertEquals(
            1.2345678901234568E-17,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("1,2345678901234568e-17"),
            0.0
        )
    }

    @Test
    fun verySmallNegativeNumberManyDigitsRev() {
        assertEquals(
            -0.00000000000000001234567890123456789,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("-1,2345678901234568e-17"),
            0.0
        )
    }

    @Test
    fun simpleNumberScNotRev() {
        assertEquals(
            12345.0,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("1,2345e+4"),
            0.0
        )
    }

    @Test
    fun oneDigitNumberScNotRev() {
        assertEquals(
            5.0,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("5,e+0"),
            0.0
        )
    }

    @Test
    fun oneDigitNegativeNumberScNotRev() {
        assertEquals(
            -5.0,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("-5,e+0"),
            0.0
        )
    }

    @Test
    fun multipleDigitNumberScNotRev() {
        assertEquals(
            12345.67,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("123,4567e+2"),
            0.0
        )
    }

    @Test
    fun multipleDigitNegativeNumberScNotRev() {
        assertEquals(
            -12345.67,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("-123,4567e+2"),
            0.0
        )
    }

    @Test
    fun smallFractionalNumberScNotRev() {
        assertEquals(
            0.0001234567,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("1,234567e-4"),
            0.0
        )
    }

    @Test
    fun fractionalNumberScNotRev() {
        assertEquals(
            123.0001234567,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("1,230001234567e+2"),
            0.0
        )
    }

    @Test
    fun mixedMantissaNumberScNotRev() {
        assertEquals(
            5.56E34,
            GeneralCalculatorInitialState(
                GeneralCalculatorDataEntity()
            ).calculatorStringToDouble("556,e+32"),
            0.0
        )
    }
}