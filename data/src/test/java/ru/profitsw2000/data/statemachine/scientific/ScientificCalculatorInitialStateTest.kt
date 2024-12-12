package ru.profitsw2000.data.statemachine.scientific

import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import ru.profitsw2000.data.constants.DEGREES_ANGLE_CODE
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
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
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorSecondOperandReadState

class ScientificCalculatorInitialStateTest {

    private val baseCalculatorData = ScientificCalculatorDataEntity()
    private val baseInitialState = ScientificCalculatorInitialState(
        baseCalculatorData
    )

    @Test
    fun clearMemoryTest() {
        val numberInMemoryData = ScientificCalculatorDataEntity(memoryNumber = "15")
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
                memoryNumber = "15"
            )
        )
        val falseFirstOperand = ScientificCalculatorFirstOperandReadState(
            ScientificCalculatorDataEntity(
                mainString = "1",
                memoryNumber = "15"
            )
        )
        val numberInMemoryData = ScientificCalculatorDataEntity(memoryNumber = "15")
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
                mainString = "15",
                memoryNumber = "15"
            )
        )

        assertTrue(ReflectionEquals(baseInitialState).matches(
            baseInitialState.saveToMemory(ScientificCalculatorDataEntity())
        ))
        assertTrue(ReflectionEquals(falseInitialState).matches(
            baseInitialState.saveToMemory(ScientificCalculatorDataEntity(
                mainString = "15"
            ))
        ))
    }

    @Test
    fun addToMemoryTest() {
        val dataWithNumber = ScientificCalculatorDataEntity(
            mainString = "3,6"
        )
        val dataWithAddedNumber = ScientificCalculatorDataEntity(
            mainString = "3,6",
            memoryNumber = "3,6"
        )
        val dataWithMemory = ScientificCalculatorDataEntity(
            mainString = "10,2",
            memoryNumber = "5,3"
        )
        val dataWithAddedMemory = ScientificCalculatorDataEntity(
            mainString = "10,2",
            memoryNumber = "15,5"
        )
        val initialStateWithNumber = ScientificCalculatorInitialState(dataWithNumber)
        val initialStateWithAddedNumber = ScientificCalculatorInitialState(dataWithAddedNumber)
        val initialStateWithData = ScientificCalculatorInitialState(dataWithMemory)
        val initialStateWithAddedData = ScientificCalculatorInitialState(dataWithAddedMemory)
        val falseInitialStateWithMemory =  ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity(memoryNumber = "15,5")
        )

        assertTrue(ReflectionEquals(baseInitialState).matches(
            baseInitialState.addNumberToMemory(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(initialStateWithAddedData).matches(
            initialStateWithData.addNumberToMemory(dataWithMemory)
        ))
        assertTrue(ReflectionEquals(initialStateWithAddedNumber).matches(
            initialStateWithNumber.addNumberToMemory(dataWithNumber)
        ))
        assertFalse(ReflectionEquals(falseInitialStateWithMemory).matches(
            baseInitialState.addNumberToMemory(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorInitialState(dataWithAddedNumber.copy(memoryNumber = "4,0"))).matches(
            initialStateWithNumber.addNumberToMemory(dataWithNumber)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorInitialState(dataWithMemory.copy(memoryNumber = "15,6"))).matches(
            initialStateWithData.addNumberToMemory(dataWithMemory)
        ))
    }

    @Test
    fun subtractFromMemoryTest() {
        val dataWithNumber = ScientificCalculatorDataEntity(
            mainString = "3,6"
        )
        val dataWithSubtractedNumber = ScientificCalculatorDataEntity(
            mainString = "3,6",
            memoryNumber = "-3,6"
        )
        val dataWithMemory = ScientificCalculatorDataEntity(
            mainString = "1,2",
            memoryNumber = "5,3"
        )
        val dataWithSubtractedMemory = ScientificCalculatorDataEntity(
            mainString = "1,2",
            memoryNumber = "4,1"
        )
        val initialStateWithNumber = ScientificCalculatorInitialState(dataWithNumber)
        val initialStateWithSubtractedNumber = ScientificCalculatorInitialState(dataWithSubtractedNumber)
        val initialStateWithData = ScientificCalculatorInitialState(dataWithMemory)
        val initialStateWithSubtractedData = ScientificCalculatorInitialState(dataWithSubtractedMemory)
        val falseInitialStateWithMemory =  ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity(memoryNumber = "15,5")
        )

        assertTrue(ReflectionEquals(baseInitialState).matches(
            baseInitialState.subtractNumberFromMemory(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(initialStateWithSubtractedData).matches(
            initialStateWithData.subtractNumberFromMemory(dataWithMemory)
        ))
        assertTrue(ReflectionEquals(initialStateWithSubtractedNumber).matches(
            initialStateWithNumber.subtractNumberFromMemory(dataWithNumber)
        ))
        assertFalse(ReflectionEquals(falseInitialStateWithMemory).matches(
            baseInitialState.subtractNumberFromMemory(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorInitialState(dataWithSubtractedNumber.copy(memoryNumber = "4,0"))).matches(
            initialStateWithNumber.subtractNumberFromMemory(dataWithNumber)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorInitialState(dataWithMemory.copy(memoryNumber = "15,6"))).matches(
            initialStateWithData.subtractNumberFromMemory(dataWithMemory)
        ))
    }

    @Test
    fun clearAllTest() {
        val calculatorWithData = ScientificCalculatorDataEntity(
            mainString = "523,12",
            historyString = "1 +",
            scientificOperationType = ScientificOperationType.PLUS,
            memoryNumber = "5,5"
        )
        val initialStateWithMemory = ScientificCalculatorInitialState(calculatorWithData)
        val baseData = baseCalculatorData.copy(memoryNumber = "5,5")

        assertTrue(ReflectionEquals(ScientificCalculatorInitialState(baseData)).matches(
            initialStateWithMemory.clearAll(calculatorWithData)
        ))
        assertTrue(ReflectionEquals(ScientificCalculatorInitialState(baseData)).matches(
            ScientificCalculatorInitialState(baseData).clearAll(baseData)
        ))
        assertFalse(ReflectionEquals(initialStateWithMemory).matches(
            initialStateWithMemory.clearAll(calculatorWithData)
        ))
    }

    @Test
    fun negateMainStringTest() {
        assertTrue(ReflectionEquals(baseInitialState).matches(
            baseInitialState.negateOperand(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorInitialState(baseCalculatorData.copy(mainString = "-1"))).matches(
            baseInitialState.negateOperand(baseCalculatorData)
        ))
    }

    @Test
    fun squareRootTest() {
        val falseCalculatorData = ScientificCalculatorDataEntity(
            mainString = "25",
            historyString = "sqrt(0)"
        )
        val falseInitialState = ScientificCalculatorInitialState(falseCalculatorData)
        val errorResultState = ScientificCalculatorErrorState(
            baseCalculatorData.copy(
                mainString = "-5",
                historyString = "sqrt(-5)",
                errorCode = INVALID_INPUT_ERROR_CODE
            )
        )

        assertTrue(ReflectionEquals(ScientificCalculatorFirstOperandReadState(baseCalculatorData.copy(historyString = "sqrt(0)"))).matches(
            baseInitialState.calculateSquareRoot(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(falseInitialState).matches(
            baseInitialState.calculateSquareRoot(falseCalculatorData)
        ))
        assertTrue(ReflectionEquals(errorResultState).matches(
            baseInitialState.calculateSquareRoot(baseCalculatorData.copy(
                mainString = "-5"
            ))
        ))
    }

    @Test
    fun digitInputTest() {
        val commaInputData = ScientificCalculatorDataEntity(mainString = "0,")
        val digitInputData = ScientificCalculatorDataEntity(mainString = "3")
        val falseDigitInputData = ScientificCalculatorDataEntity(mainString = "7")
        val commaInputState = ScientificCalculatorFirstOperandInputState(commaInputData)
        val digitInputState = ScientificCalculatorFirstOperandInputState(digitInputData)
        val falseDigitInputState = ScientificCalculatorFirstOperandInputState(falseDigitInputData)

        assertTrue(ReflectionEquals(commaInputState).matches(
            baseInitialState.inputDigit(baseCalculatorData, ",")
        ))
        assertTrue(ReflectionEquals(digitInputState).matches(
            baseInitialState.inputDigit(baseCalculatorData, "3")
        ))
        assertFalse(ReflectionEquals(falseDigitInputState).matches(
            baseInitialState.inputDigit(baseCalculatorData, "3")
        ))
    }

    @Test
    fun primitiveMathOperationTest() {
        val addCalculatorData = ScientificCalculatorDataEntity(
            historyString = "0$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "0"
        )
        val divideCalculatorData = ScientificCalculatorDataEntity(
            historyString = "0$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = "0"
        )
        val addOperationState = ScientificCalculatorMathOperationState(addCalculatorData)
        val divideOperationState = ScientificCalculatorMathOperationState(divideCalculatorData)

        assertTrue(ReflectionEquals(addOperationState).matches(
            baseInitialState.primitiveMathOperation(
                baseCalculatorData,
                ScientificOperationType.PLUS,
                "+"
            )
        ))
        assertTrue(ReflectionEquals(divideOperationState).matches(
            baseInitialState.primitiveMathOperation(
                baseCalculatorData,
                ScientificOperationType.DIVIDE,
                "/"
            )
        ))
        assertFalse(ReflectionEquals(addOperationState).matches(
            baseInitialState.primitiveMathOperation(
                baseCalculatorData,
                ScientificOperationType.DIVIDE,
                "/"
            )
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorDataEntity(
            historyString = "0$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "0"
        )).matches(
            baseInitialState.primitiveMathOperation(
                baseCalculatorData,
                ScientificOperationType.PLUS,
                "+"
            )
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorDataEntity(
            historyString = "0$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.MODULUS,
            operand = "0"
        )).matches(
            baseInitialState.primitiveMathOperation(
                baseCalculatorData,
                ScientificOperationType.PLUS,
                "+"
            )
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorDataEntity(
            historyString = "0$HISTORY_STRING_SPACE_LETTER+",
            scientificOperationType = ScientificOperationType.PLUS,
            operand = "2"
        )).matches(
            baseInitialState.primitiveMathOperation(
                baseCalculatorData,
                ScientificOperationType.PLUS,
                "+"
            )
        ))
    }

    @Test
    fun reciprocationTest() {
        val errorData = ScientificCalculatorDataEntity(
            historyString = "reciproc(0)",
            errorCode = DIVIDE_ON_ZERO_ERROR_CODE
        )
        val errorState = ScientificCalculatorErrorState(errorData)
        val resultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "reciproc(0)"
        )
        val resultState = ScientificCalculatorOperationResultState(resultData)

        assertTrue(ReflectionEquals(errorState).matches(
            baseInitialState.reciprocOperation(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(resultState).matches(
            baseInitialState.reciprocOperation(baseCalculatorData)
        ))
    }

    @Test
    fun resultTest() {
        assertTrue(ReflectionEquals(baseInitialState).matches(
            baseInitialState.calculateResult(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorOperationResultState(
            ScientificCalculatorDataEntity())).matches(
                baseInitialState.calculateResult(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorOperationResultState(
            ScientificCalculatorDataEntity(
                mainString = "23"
            ))).matches(
            baseInitialState.calculateResult(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorOperationResultState(
            ScientificCalculatorDataEntity(
                historyString = "23"
            ))).matches(
            baseInitialState.calculateResult(baseCalculatorData)
        ))
    }

    @Test
    fun openBracketTest() {
        val firstStateData = baseCalculatorData.copy(memoryNumber = "2,36")
        val firstState = ScientificCalculatorInitialState(firstStateData)
        val secondStateData = baseCalculatorData.copy(
            historyString = "(",
            memoryNumber = "2,36",
            prevState = firstState
        )
        val secondState = ScientificCalculatorInitialState(secondStateData)
        val thirdStateData = baseCalculatorData.copy(
            historyString = "((",
            memoryNumber = "2,36",
            prevState = secondState
        )
        val thirdState = ScientificCalculatorInitialState(thirdStateData)

        assertTrue(ReflectionEquals(secondState).matches(
            firstState.openBracket(firstStateData)
        ))
        assertTrue(ReflectionEquals(thirdState).matches(
            secondState.openBracket(secondStateData)
        ))
        assertFalse(ReflectionEquals(thirdState).matches(
            firstState.openBracket(firstStateData)
        ))
        assertFalse(ReflectionEquals(secondState).matches(
            ScientificCalculatorInitialState(firstStateData.copy(memoryNumber = "2,35"))
        ))
    }

    @Test
    fun closeBracketInitialStateTest() {
        val firstStateData = baseCalculatorData.copy(memoryNumber = "2,36")
        val firstState = ScientificCalculatorInitialState(firstStateData)
        val recoveredFirstStateData = baseCalculatorData.copy(
            historyString = "((0))",
            memoryNumber = "0"
        )
        val recoveredFirstState = ScientificCalculatorInitialState(recoveredFirstStateData)
        val secondStateData = baseCalculatorData.copy(
            historyString = "(",
            memoryNumber = "0",
            prevState = firstState
        )
        val recoveredSecondStateData = baseCalculatorData.copy(
            historyString = "((0)",
            memoryNumber = "0",
            prevState = firstState
        )
        val recoveredSecondState = ScientificCalculatorInitialState(recoveredSecondStateData)
        val secondState = ScientificCalculatorInitialState(secondStateData)
        val thirdStateData = baseCalculatorData.copy(
            historyString = "((",
            memoryNumber = "0",
            prevState = secondState
        )
        val thirdState = ScientificCalculatorInitialState(thirdStateData)

        assertTrue(ReflectionEquals(recoveredSecondState).matches(
            thirdState.closeBracket(thirdStateData)
        ))
        assertTrue(ReflectionEquals(recoveredFirstState).matches(
            recoveredSecondState.closeBracket(recoveredSecondStateData)
        ))
        assertTrue(ReflectionEquals(baseInitialState).matches(
            baseInitialState.closeBracket(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(secondState).matches(
            thirdState.closeBracket(thirdStateData)
        ))
        assertFalse(ReflectionEquals(firstState).matches(
            recoveredSecondState.closeBracket(recoveredSecondStateData)
        ))
    }

    @Test
    fun closeBracketMathOperationStateTest(){
        val firstStateData = baseCalculatorData.copy(
            historyString = "9,8$HISTORY_STRING_SPACE_LETTER+",
            mainString = "9,8",
            memoryNumber = "7,0"
        )
        val firstState = ScientificCalculatorMathOperationState(firstStateData)
        val recoveredFirstStateData = baseCalculatorData.copy(
            historyString = "9,8$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(0)",
            mainString = "0",
            memoryNumber = "0"
        )
        val recoveredFirstState = ScientificCalculatorSecondOperandReadState(recoveredFirstStateData)
        val secondStateData = baseCalculatorData.copy(
            historyString = "9,8$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            memoryNumber = "0",
            mainString = "0",
            prevState = firstState
        )
        val secondState = ScientificCalculatorInitialState(secondStateData)

        assertTrue(ReflectionEquals(recoveredFirstState).matches(
            secondState.closeBracket(secondStateData)
        ))
    }

    @Test
    fun naturalLogarithmCalculationTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            historyString = "ln(0)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val zeroInputResultState = ScientificCalculatorErrorState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            historyString = "ln(1)",
            mainString = "0"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.calculateNaturalLogarithm(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.calculateNaturalLogarithm(baseCalculatorData.copy(
                mainString = "1"
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.calculateNaturalLogarithm(baseCalculatorData.copy(
                mainString = "1"
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.calculateNaturalLogarithm(baseCalculatorData)
        ))
    }

    @Test
    fun exponentCalculationTest() {
        val zeroExpResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "powe(0)"
        )
        val zeroExpResultState = ScientificCalculatorFirstOperandReadState(zeroExpResultData)
        val nonZeroExpResultData = ScientificCalculatorDataEntity(
            mainString = "7,389056098930650227230427460575",
            historyString = "powe(2)"
        )
        val nonZeroExpResultState = ScientificCalculatorFirstOperandReadState(nonZeroExpResultData)

        assertTrue(ReflectionEquals(zeroExpResultState).matches(
            baseInitialState.calculateExponent(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(nonZeroExpResultState).matches(
            baseInitialState.calculateExponent(baseCalculatorData.copy(
                mainString = "2"
            ))
        ))
        assertFalse(ReflectionEquals(zeroExpResultState).matches(
            baseInitialState.calculateExponent(baseCalculatorData.copy(
                mainString = "2"
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroExpResultState).matches(
            baseInitialState.calculateExponent(baseCalculatorData)
        ))
    }

    @Test
    fun integerOfNumberCalculationTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "Int(0)"
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "7",
            historyString = "Int(7,38905609893065)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.integerOfNumber(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.integerOfNumber(baseCalculatorData.copy(
                mainString = "7,38905609893065"
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.integerOfNumber(baseCalculatorData.copy(
                mainString = "7,38905609893065"
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.integerOfNumber(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(ScientificCalculatorFirstOperandReadState(ScientificCalculatorDataEntity(
                mainString = "7",
                historyString = "Int(7,)"
            )
        )).matches(
            baseInitialState.integerOfNumber(baseCalculatorData.copy(
                mainString = "7,"
            ))
        ))
    }

    @Test
    fun fractionOfNumberCalculationTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "frac(0)"
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,38905609893065",
            historyString = "frac(7,38905609893065)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.fractionOfNumber(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.fractionOfNumber(baseCalculatorData.copy(
                mainString = "7,38905609893065"
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.fractionOfNumber(baseCalculatorData.copy(
                mainString = "7,38905609893065"
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.fractionOfNumber(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(ScientificCalculatorFirstOperandReadState(ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "frac(7,)"
        )
        )).matches(
            baseInitialState.fractionOfNumber(baseCalculatorData.copy(
                mainString = "7,"
            ))
        ))
    }

    @Test
    fun hyperbolicSinusTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sinh(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,1752011936438014568823818505956",
            historyString = "sinh(1)",
            prevState = baseInitialState
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
/*
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "10000",
            historyString = "sinh(10000)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorResultState = ScientificCalculatorErrorState(errorResultData)
*/

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.hyperbolicSinus(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicSinus(baseCalculatorData.copy(
                mainString = "1",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.hyperbolicSinus(baseCalculatorData.copy(
                mainString = "1",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicSinus(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorFirstOperandReadState(ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sinh(0)"
        )
        )).matches(
            baseInitialState.hyperbolicSinus(baseCalculatorData.copy(
                mainString = "0",
                prevState = baseInitialState
            ))
        ))
    }

    @Test
    fun hyperbolicArcSinusTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "asinh(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,3124383412727526202535623413644",
            historyString = "asinh(5)",
            prevState = baseInitialState
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.hyperbolicArcSinus(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicArcSinus(baseCalculatorData.copy(
                mainString = "5",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.hyperbolicArcSinus(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicArcSinus(baseCalculatorData.copy(
                mainString = "5"
            ))
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorFirstOperandReadState(ScientificCalculatorDataEntity(
            mainString = "2,3124383412727526202535623413644",
            historyString = "asinh(0)"
        )
        )).matches(
            baseInitialState.hyperbolicArcSinus(baseCalculatorData.copy(
                mainString = "0"
            ))
        ))
    }

    @Test
    fun degreesSinusTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sind(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,93969262078590838405410927732472",
            historyString = "sind(70)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val angleInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "sind(90)"
        )
        val angleInputResultState = ScientificCalculatorFirstOperandReadState(angleInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                mainString = "70"
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(angleInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                mainString = "90"
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                mainString = "70", prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData, DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansSinusTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sinr(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,77389068155788909778733062514199",
            historyString = "sinr(70)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                mainString = "70"
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                mainString = "70", prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData, RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsSinusTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sing(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,89100652418836786235970957141361",
            historyString = "sing(70)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                mainString = "70"
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData.copy(
                mainString = "70", prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.sinus(baseCalculatorData, GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun degreesArcSinusTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "asind(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "30",
            historyString = "asind(0,5)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "90",
            historyString = "asind(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "asind(2)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "0,5"
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "0,5", prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "1"
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(errorState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "2"
            ), DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansArcSinusTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "asinr(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,52359877559829887307710723054658",
            historyString = "asinr(0,5)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,5707963267948966192313216916398",
            historyString = "asinr(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "asinr(2)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "0,5"
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "0,5", prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "1"
            ), RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(errorState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "2"
            ), RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsArcSinusTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "asing(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "33,333333333333333333333333333333",
            historyString = "asing(0,5)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "100",
            historyString = "asing(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "asing(2)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "0,5"
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "0,5", prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "1"
            ), GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(errorState).matches(
            baseInitialState.arcSinus(baseCalculatorData.copy(
                mainString = "2"
            ), GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun squareNumberTest() {

        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "sqr(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "81",
            historyString = "sqr(9)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val bigInputResultData = ScientificCalculatorDataEntity(
            mainString = "99999999999999980000000000000001",
            historyString = "sqr(9999999999999999)"
        )
        val bigInputResultState = ScientificCalculatorFirstOperandReadState(bigInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "9,e+5000",
            historyString = "sqr(9,e+5000)",
            errorCode = OVERFLOW_ERROR_CODE
        )
        val errorState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.squareNumber(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.squareNumber(baseCalculatorData.copy(
                mainString = "9"
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.squareNumber(baseCalculatorData.copy(
                mainString = "9",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.squareNumber(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(bigInputResultState).matches(
            baseInitialState.squareNumber(baseCalculatorData.copy(
                mainString = "9999999999999999"
            ))
        ))
        assertTrue(ReflectionEquals(errorState).matches(
            baseInitialState.squareNumber(baseCalculatorData.copy(
                mainString = "9,e+5000"
            ))
        ))
    }

    @Test
    fun factorialTest() {

        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "fact(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "3628800",
            historyString = "fact(10)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val fractionInputResultData = ScientificCalculatorDataEntity(
            mainString = "220,41492110473621160003136255321",
            historyString = "fact(5,35)"
        )
        val fractionInputResultState = ScientificCalculatorFirstOperandReadState(fractionInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "20000",
            historyString = "fact(20000)",
            errorCode = OVERFLOW_ERROR_CODE
        )
        val errorState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.factorial(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.factorial(baseCalculatorData.copy(
                mainString = "10"
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.factorial(baseCalculatorData.copy(
                mainString = "10",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.factorial(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(fractionInputResultState).matches(
            baseInitialState.factorial(baseCalculatorData.copy(
                mainString = "5,35"
            ))
        ))
        assertTrue(ReflectionEquals(errorState).matches(
            baseInitialState.factorial(baseCalculatorData.copy(
                mainString = "20000"
            ))
        ))
    }

    @Test
    fun decimalToMinutesTest() {

        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "dms(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "10,54",
            historyString = "dms(10,9)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val negativeNonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "-10,54",
            historyString = "dms(-10,9)"
        )
        val negativeNonZeroInputResultState = ScientificCalculatorFirstOperandReadState(negativeNonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.decimalToMinutes(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.decimalToMinutes(baseCalculatorData.copy(
                mainString = "10,9"
            ))
        ))
        assertTrue(ReflectionEquals(negativeNonZeroInputResultState).matches(
            baseInitialState.decimalToMinutes(baseCalculatorData.copy(
                mainString = "-10,9"
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.decimalToMinutes(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.decimalToMinutes(baseCalculatorData)
        ))
    }

    @Test
    fun minutesToDecimalTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "deg(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "11,5",
            historyString = "deg(10,9)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val negativeNonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "-11,5",
            historyString = "deg(-10,9)"
        )
        val negativeNonZeroInputResultState = ScientificCalculatorFirstOperandReadState(negativeNonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.minutesToDecimal(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.minutesToDecimal(baseCalculatorData.copy(
                mainString = "10,9"
            ))
        ))
        assertTrue(ReflectionEquals(negativeNonZeroInputResultState).matches(
            baseInitialState.minutesToDecimal(baseCalculatorData.copy(
                mainString = "-10,9"
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.minutesToDecimal(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.minutesToDecimal(baseCalculatorData)
        ))
    }

    @Test
    fun hyperbolicCosineTest() {
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "cosh(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,5430806348152437784779056207571",
            historyString = "cosh(1)",
            prevState = baseInitialState
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "100000",
            historyString = "cosh(100000)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorResultState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.hyperbolicCosine(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicCosine(baseCalculatorData.copy(
                mainString = "1",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.hyperbolicCosine(baseCalculatorData.copy(
                mainString = "1",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicCosine(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorFirstOperandReadState(ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "cosh(0)"
        )
        )).matches(
            baseInitialState.hyperbolicCosine(baseCalculatorData.copy(
                mainString = "0",
                prevState = baseInitialState
            ))
        ))
/*        assertTrue(ReflectionEquals(errorResultState).matches(
            baseInitialState.hyperbolicCosine(baseCalculatorData.copy(
                mainString = "100000"
            ))
        ))*/
    }

    @Test
    fun hyperbolicArcCosineTest() {
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "acosh(1)",
            prevState = baseInitialState
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,292431669561177687800787311348",
            historyString = "acosh(5)",
            prevState = baseInitialState
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            historyString = "acosh(0)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorResultState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.hyperbolicArcCosine(baseCalculatorData.copy(
                mainString = "1",
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicArcCosine(baseCalculatorData.copy(
                mainString = "5",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicArcCosine(ScientificCalculatorDataEntity(
                mainString = "1",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.hyperbolicArcCosine(baseCalculatorData.copy(
                mainString = "1"
            ))
        ))
        assertTrue(ReflectionEquals(errorResultState).matches(
            baseInitialState.hyperbolicArcCosine(baseCalculatorData)
        ))
    }

    @Test
    fun degreesCosineTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "cosd(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,34202014332566873304409961468229",
            historyString = "cosd(70)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val angleInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "cosd(90)"
        )
        val angleInputResultState = ScientificCalculatorFirstOperandReadState(angleInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(angleInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                mainString = "90"
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                mainString = "70"
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                mainString = "70", prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData, DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansCosineTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "cosr(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "-0,11038724383904755811786665581349",
            historyString = "cosr(80)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                mainString = "80"
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                mainString = "80", prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData, RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsCosineTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1",
            historyString = "cosg(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,45399049973954679156040836635789",
            historyString = "cosg(70)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                mainString = "70"
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData.copy(
                mainString = "70", prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.cosine(baseCalculatorData, GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun degreesArcCosineTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "90",
            historyString = "acosd(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "60",
            historyString = "acosd(0,5)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "acosd(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "acosd(2)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "0,5"
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "0,5", prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "1"
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(errorState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "2"
            ), DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansArcCosineTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,5707963267948966192313216916398",
            historyString = "acosr(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,0471975511965977461542144610932",
            historyString = "acosr(0,5)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "acosr(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "acosr(2)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "0,5"
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "0,5", prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "1"
            ), RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(errorState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "2"
            ), RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsArcCosineTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "100",
            historyString = "acosg(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "66,666666666666666666666666666667",
            historyString = "acosg(0,5)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "acosg(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "2",
            historyString = "acosg(2)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "0,5"
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "0,5", prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "1"
            ), GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(errorState).matches(
            baseInitialState.arcCosine(baseCalculatorData.copy(
                mainString = "2"
            ), GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun piNumberTest() {

        val piResultData = baseCalculatorData.copy(
            mainString = "3,1415926535897932384626433832795"
        )
        val piResultState = ScientificCalculatorFirstOperandReadState(piResultData)
        val falseResultData = baseCalculatorData.copy(
            mainString = "4"
        )
        val falseResultState = ScientificCalculatorFirstOperandReadState(falseResultData)

        assertTrue(ReflectionEquals(piResultState).matches(
            baseInitialState.piNumber(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(falseResultState).matches(
            baseInitialState.piNumber(baseCalculatorData)
        ))
    }

    @Test
    fun doublePiNumberTest() {

        val doublePiResultData = baseCalculatorData.copy(
            mainString = "6,283185307179586476925286766559"
        )
        val doublePiResultState = ScientificCalculatorFirstOperandReadState(doublePiResultData)
        val falseResultData = baseCalculatorData.copy(
            mainString = "5"
        )
        val falseResultState = ScientificCalculatorFirstOperandReadState(falseResultData)

        assertTrue(ReflectionEquals(doublePiResultState).matches(
            baseInitialState.doublePiNumber(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(falseResultState).matches(
            baseInitialState.doublePiNumber(baseCalculatorData)
        ))
    }


    @Test
    fun hyperbolicTangentTest() {
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "tanh(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,76159415595576488811945828260479",
            historyString = "tanh(1)",
            prevState = baseInitialState
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val negativeResultData = ScientificCalculatorDataEntity(
            mainString = "-1",
            historyString = "tanh(-1000)"
        )
        val negativeResultState = ScientificCalculatorFirstOperandReadState(negativeResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.hyperbolicTangent(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicTangent(baseCalculatorData.copy(
                mainString = "1",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.hyperbolicTangent(baseCalculatorData.copy(
                mainString = "1",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicTangent(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorFirstOperandReadState(ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "tanh(0)"
        )
        )).matches(
            baseInitialState.hyperbolicTangent(baseCalculatorData.copy(
                mainString = "0",
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(negativeResultState).matches(
            baseInitialState.hyperbolicTangent(baseCalculatorData.copy(
                mainString = "-1000"
            ))
        ))
    }

    @Test
    fun hyperbolicArcTangentTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "atanh(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,54930614433405484569762261846126",
            historyString = "atanh(0,5)",
            prevState = baseInitialState
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            historyString = "atanh(1)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorResultState = ScientificCalculatorErrorState(errorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.hyperbolicArcTangent(baseCalculatorData.copy(
                mainString = "0",
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicArcTangent(baseCalculatorData.copy(
                mainString = "0,5",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicArcTangent(ScientificCalculatorDataEntity(
                mainString = "0",
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.hyperbolicArcTangent(baseCalculatorData.copy(
                mainString = "0,5"
            ))
        ))
        assertTrue(ReflectionEquals(errorResultState).matches(
            baseInitialState.hyperbolicArcTangent(baseCalculatorData.copy(
                mainString = "1"
            ))
        ))
    }

    @Test
    fun degreesTangentTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "tand(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "2,7474774194546222787616640264977",
            historyString = "tand(70)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            historyString = "tand(90)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorResultState = ScientificCalculatorErrorState(errorResultData)
        val additionalErrorResultData = ScientificCalculatorDataEntity(
            historyString = "tand(270)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val additionalErrorResultState = ScientificCalculatorErrorState(additionalErrorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData.copy(
                prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData.copy(
                mainString = "70"
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData.copy(
                mainString = "70", prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(errorResultState).matches(
            baseInitialState.tangent(
                baseCalculatorData.copy(mainString = "90"),
                DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(additionalErrorResultState).matches(
            baseInitialState.tangent(
                baseCalculatorData.copy(mainString = "270"),
                DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansTangentTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "tanr(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,2219599181369432780892227563596",
            historyString = "tanr(70)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            historyString = "tanr(1,5707963267948966192313216916398)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorResultState = ScientificCalculatorErrorState(errorResultData)
        val additionalErrorResultData = ScientificCalculatorDataEntity(
            historyString = "tanr(4,7123889803846898576939650749193)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val additionalErrorResultState = ScientificCalculatorErrorState(additionalErrorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData.copy(
                prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData.copy(
                mainString = "70"
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData.copy(
                mainString = "70", prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(errorResultState).matches(
            baseInitialState.tangent(
                baseCalculatorData.copy(mainString = "1,5707963267948966192313216916398"),
                RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(additionalErrorResultState).matches(
            baseInitialState.tangent(
                baseCalculatorData.copy(mainString = "4,7123889803846898576939650749193"),
                RADIANS_ANGLE_CODE)
        ))
    }
/*
    @Test
    fun gradsTangentTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "tang(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,96261050550515",
            historyString = "tang(70)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            historyString = "tang(100)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorResultState = ScientificCalculatorErrorState(errorResultData)
        val additionalErrorResultData = ScientificCalculatorDataEntity(
            historyString = "tang(300)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val additionalErrorResultState = ScientificCalculatorErrorState(additionalErrorResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData.copy(
                prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData.copy(
                mainString = "70"
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData.copy(
                mainString = "70", prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.tangent(baseCalculatorData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(errorResultState).matches(
            baseInitialState.tangent(
                baseCalculatorData.copy(mainString = "100"),
                GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(additionalErrorResultState).matches(
            baseInitialState.tangent(
                baseCalculatorData.copy(mainString = "300"),
                GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun degreesArcTangentTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "atand(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "89,99999999427042",
            historyString = "atand(9999999999)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "45",
            historyString = "atand(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                mainString = "9999999999"
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                mainString = "9999999999", prevState = baseInitialState
            ), DEGREES_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData, DEGREES_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                mainString = "1"
            ), DEGREES_ANGLE_CODE)
        ))
    }

    @Test
    fun radiansArcTangentTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "atanr(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,570796326694897",
            historyString = "atanr(9999999999)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "0,785398163397448",
            historyString = "atanr(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                mainString = "9999999999"
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                mainString = "9999999999", prevState = baseInitialState
            ), RADIANS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData, RADIANS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                mainString = "1"
            ), RADIANS_ANGLE_CODE)
        ))
    }

    @Test
    fun gradsArcTangentTest() {
        val zeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "atang(0)",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandReadState(zeroInputResultData)
        val nonZeroInputResultData = ScientificCalculatorDataEntity(
            mainString = "99,99999999363381",
            historyString = "atang(9999999999)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "50",
            historyString = "atang(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                mainString = "9999999999"
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                mainString = "9999999999", prevState = baseInitialState
            ), GRADS_ANGLE_CODE)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData, GRADS_ANGLE_CODE)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.arcTangent(baseCalculatorData.copy(
                mainString = "1"
            ), GRADS_ANGLE_CODE)
        ))
    }

    @Test
    fun cubePowerOfNumberTest() {
        val zeroResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "cube(0)",
            prevState = baseInitialState
        )
        val zeroResultState = ScientificCalculatorFirstOperandReadState(zeroResultData)
        val nonZeroResultData = ScientificCalculatorDataEntity(
            mainString = "1000",
            historyString = "cube(10)"
        )
        val nonZeroResultState = ScientificCalculatorFirstOperandReadState(nonZeroResultData)
        val bigNumberResultData =  ScientificCalculatorDataEntity(
            historyString = "cube(1,e+150)",
            errorCode = OVERFLOW_ERROR_CODE
        )
        val bigNumberResultState = ScientificCalculatorErrorState(bigNumberResultData)
        val smallNumberResultData = ScientificCalculatorDataEntity(
            mainString = "1,e-300",
            historyString = "cube(1,e-100)"
        )
        val smallNumberResultState = ScientificCalculatorFirstOperandReadState(smallNumberResultData)

        assertTrue(ReflectionEquals(zeroResultState).matches(
            baseInitialState.cubeNumber(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroResultState).matches(
            baseInitialState.cubeNumber(baseCalculatorData.copy(
                mainString = "10"
            ))
        ))
        assertFalse(ReflectionEquals(zeroResultState).matches(
            baseInitialState.cubeNumber(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(nonZeroResultState).matches(
            baseInitialState.cubeNumber(baseCalculatorData.copy(
                mainString = "0"
            ))
        ))
        assertTrue(ReflectionEquals(bigNumberResultState).matches(
            baseInitialState.cubeNumber(baseCalculatorData.copy(
                mainString = "1,e+150"
            ))
        ))
        assertTrue(ReflectionEquals(smallNumberResultState).matches(
            baseInitialState.cubeNumber(baseCalculatorData.copy(
                mainString = "1,e-100"
            ))
        ))
    }

    @Test
    fun cubeRootOfNumberTest() {
        val zeroResultData = ScientificCalculatorDataEntity(
            mainString = "0",
            historyString = "cuberoot(0)",
            prevState = baseInitialState
        )
        val zeroResultState = ScientificCalculatorFirstOperandReadState(zeroResultData)
        val nonZeroResultData = ScientificCalculatorDataEntity(
            mainString = "10000",
            historyString = "cuberoot(1000000000000)"
        )
        val nonZeroResultState = ScientificCalculatorFirstOperandReadState(nonZeroResultData)
        val bigNumberResultData =  ScientificCalculatorDataEntity(
            mainString = "1,e+50",
            historyString = "cuberoot(1,e+150)"
        )
        val bigNumberResultState = ScientificCalculatorFirstOperandReadState(bigNumberResultData)
        val smallNumberResultData = ScientificCalculatorDataEntity(
            mainString = "1,e-100",
            historyString = "cuberoot(1,e-300)"
        )
        val smallNumberResultState = ScientificCalculatorFirstOperandReadState(smallNumberResultData)

        assertTrue(ReflectionEquals(zeroResultState).matches(
            baseInitialState.cubeRoot(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroResultState).matches(
            baseInitialState.cubeRoot(baseCalculatorData.copy(
                mainString = "1000000000000"
            ))
        ))
        assertFalse(ReflectionEquals(zeroResultState).matches(
            baseInitialState.cubeRoot(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(nonZeroResultState).matches(
            baseInitialState.cubeRoot(baseCalculatorData.copy(
                mainString = "0"
            ))
        ))
        assertTrue(ReflectionEquals(bigNumberResultState).matches(
            baseInitialState.cubeRoot(baseCalculatorData.copy(
                mainString = "1,e+150"
            ))
        ))
        assertTrue(ReflectionEquals(smallNumberResultState).matches(
            baseInitialState.cubeRoot(baseCalculatorData.copy(
                mainString = "1,e-300"
            ))
        ))
    }

    @Test
    fun formatChangeTest() {
        val zeroResultState = ScientificCalculatorFirstOperandReadState(baseCalculatorData)
        val zeroSNResultData = baseCalculatorData.copy(
            mainString = "0,e+0",
            isScientificNotation = true
        )
        val zeroSNResultState = ScientificCalculatorFirstOperandReadState(zeroSNResultData)
        val nonZeroResultData = baseCalculatorData.copy(
            mainString = "5"
        )
        val nonZeroSNResultData = baseCalculatorData.copy(
            mainString = "5,e+0",
            isScientificNotation = true
        )
        val nonZeroResultState = ScientificCalculatorFirstOperandReadState(nonZeroResultData)
        val nonZeroSNResultState = ScientificCalculatorFirstOperandReadState(nonZeroSNResultData)
        val bigNonZeroResultData = baseCalculatorData.copy(
            mainString = "-4567"
        )
        val bigNonZeroSNResultData = baseCalculatorData.copy(
            mainString = "-4,567e+3",
            isScientificNotation = true
        )
        val bigNonZeroResultState = ScientificCalculatorFirstOperandReadState(bigNonZeroResultData)
        val bigNonZeroSNResultState = ScientificCalculatorFirstOperandReadState(bigNonZeroSNResultData)
        val fractionNonZeroResultData = baseCalculatorData.copy(
            mainString = "0,004567"
        )
        val fractionNonZeroSNResultData = baseCalculatorData.copy(
            mainString = "4,567e-3",
            isScientificNotation = true
        )
        val fractionNonZeroResultState = ScientificCalculatorFirstOperandReadState(fractionNonZeroResultData)
        val fractionNonZeroSNResultState = ScientificCalculatorFirstOperandReadState(fractionNonZeroSNResultData)

        assertTrue(ReflectionEquals(zeroSNResultState).matches(
            baseInitialState.fixedToExponentialFormat(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(zeroResultState).matches(
            baseInitialState.fixedToExponentialFormat(baseCalculatorData.copy(
                mainString = "0,e+0",
                isScientificNotation = true
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroSNResultState).matches(
            baseInitialState.fixedToExponentialFormat(nonZeroResultData)
        ))
        assertTrue(ReflectionEquals(nonZeroResultState).matches(
            baseInitialState.fixedToExponentialFormat(nonZeroSNResultData)
        ))
        assertTrue(ReflectionEquals(bigNonZeroSNResultState).matches(
            baseInitialState.fixedToExponentialFormat(bigNonZeroResultData)
        ))
        assertTrue(ReflectionEquals(bigNonZeroResultState).matches(
            baseInitialState.fixedToExponentialFormat(bigNonZeroSNResultData)
        ))
        assertTrue(ReflectionEquals(fractionNonZeroSNResultState).matches(
            baseInitialState.fixedToExponentialFormat(fractionNonZeroResultData)
        ))
        assertTrue(ReflectionEquals(fractionNonZeroResultState).matches(
            baseInitialState.fixedToExponentialFormat(fractionNonZeroSNResultData)
        ))
    }

    @Test
    fun exponentialFormatInputTest() {
        val zeroInputResultData = baseCalculatorData.copy(
            mainString = "0,e+0",
            prevState = baseInitialState
        )
        val zeroInputResultState = ScientificCalculatorFirstOperandPowerNumberInputState(zeroInputResultData)
        val nonZeroInputResultData = baseCalculatorData.copy(
            mainString = "123,e+0"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandPowerNumberInputState(
            nonZeroInputResultData
        )
        val fractionInputResultData = baseCalculatorData.copy(
            mainString = "2,356e+0"
        )
        val fractionInputResultState = ScientificCalculatorFirstOperandPowerNumberInputState(
            fractionInputResultData
        )

        assertTrue(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.exponentialFormat(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.exponentialFormat(baseCalculatorData.copy(
                mainString = "123"
            ))
        ))
        assertTrue(ReflectionEquals(fractionInputResultState).matches(
            baseInitialState.exponentialFormat(baseCalculatorData.copy(
                mainString = "2,356"
            ))
        ))
        assertFalse(ReflectionEquals(zeroInputResultState).matches(
            baseInitialState.exponentialFormat(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.exponentialFormat(baseCalculatorData.copy(
                mainString = "12,3"
            ))
        ))
    }

    @Test
    fun logarithmTest(){
        val zeroResultData = baseCalculatorData.copy(
            historyString = "log(0)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val zeroResultState = ScientificCalculatorErrorState(zeroResultData)
        val oneInputResultData = baseCalculatorData.copy(
            mainString = "0",
            historyString = "log(1)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val negativeInputResultData = baseCalculatorData.copy(
            historyString = "log(-4)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val negativeInputResultState = ScientificCalculatorErrorState(negativeInputResultData)
        val fractionInputResultData = baseCalculatorData.copy(
            mainString = "-2",
            historyString = "log(0,01)"
        )
        val fractionInputResultState = ScientificCalculatorFirstOperandReadState(fractionInputResultData)
        val nonZeroInputResultData = baseCalculatorData.copy(
            mainString = "3",
            historyString = "log(1000)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

        assertTrue(ReflectionEquals(zeroResultState).matches(
            baseInitialState.logarithmBaseTen(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.logarithmBaseTen(baseCalculatorData.copy(
                mainString = "1"
            ))
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            baseInitialState.logarithmBaseTen(baseCalculatorData.copy(
                mainString = "-4"
            ))
        ))
        assertTrue(ReflectionEquals(nonZeroInputResultState).matches(
            baseInitialState.logarithmBaseTen(baseCalculatorData.copy(
                mainString = "1000"
            ))
        ))
        assertTrue(ReflectionEquals(fractionInputResultState).matches(
            baseInitialState.logarithmBaseTen(baseCalculatorData.copy(
                mainString = "0,01"
            ))
        ))
        assertFalse(ReflectionEquals(fractionInputResultState).matches(
            baseInitialState.logarithmBaseTen(baseCalculatorData.copy(
                mainString = "1000"
            ))
        ))
    }

    @Test
    fun tenToPowerOfNumberTest() {
        val zeroResultData = baseCalculatorData.copy(
            mainString = "1",
            historyString = "10^(0)",
            prevState = baseInitialState
        )
        val zeroResultState = ScientificCalculatorFirstOperandReadState(zeroResultData)
        val oneInputResultData = baseCalculatorData.copy(
            mainString = "10000",
            historyString = "10^(4)"
        )
        val oneInputResultState = ScientificCalculatorFirstOperandReadState(oneInputResultData)
        val negativeInputResultData = baseCalculatorData.copy(
            mainString = "0,0001",
            historyString = "10^(-4)",
        )
        val negativeInputResultState = ScientificCalculatorFirstOperandReadState(negativeInputResultData)
        val fractionInputResultData = baseCalculatorData.copy(
            mainString = "4,466835921509632",
            historyString = "10^(0,65)"
        )
        val fractionInputResultState = ScientificCalculatorFirstOperandReadState(fractionInputResultData)
        val errorInputResultData = baseCalculatorData.copy(
            historyString = "10^(1000)",
            errorCode = OVERFLOW_ERROR_CODE
        )
        val errorInputResultState = ScientificCalculatorErrorState(errorInputResultData)

        assertTrue(ReflectionEquals(zeroResultState).matches(
            baseInitialState.tenPowerX(baseCalculatorData.copy(
                prevState = baseInitialState
            ))
        ))
        assertFalse(ReflectionEquals(zeroResultState).matches(
            baseInitialState.tenPowerX(baseCalculatorData)
        ))
        assertTrue(ReflectionEquals(oneInputResultState).matches(
            baseInitialState.tenPowerX(baseCalculatorData.copy(
                mainString = "4"
            ))
        ))
        assertTrue(ReflectionEquals(negativeInputResultState).matches(
            baseInitialState.tenPowerX(baseCalculatorData.copy(
                mainString = "-4"
            ))
        ))
        assertTrue(ReflectionEquals(errorInputResultState).matches(
            baseInitialState.tenPowerX(baseCalculatorData.copy(
                mainString = "1000"
            ))
        ))
        assertTrue(ReflectionEquals(fractionInputResultState).matches(
            baseInitialState.tenPowerX(baseCalculatorData.copy(
                mainString = "0,65"
            ))
        ))
        assertFalse(ReflectionEquals(fractionInputResultState).matches(
            baseInitialState.tenPowerX(baseCalculatorData.copy(
                mainString = "10"
            ))
        ))
    }*/
}