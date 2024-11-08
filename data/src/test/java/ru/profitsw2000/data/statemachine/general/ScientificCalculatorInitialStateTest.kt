package ru.profitsw2000.data.statemachine.general

import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import ru.profitsw2000.data.constants.DEGREES_ANGLE_CODE
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.GRADS_ANGLE_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.RADIANS_ANGLE_CODE
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificOperationType
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorErrorState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandInputState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorFirstOperandReadState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorInitialState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorMathOperationState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorOperationResultState
import ru.profitsw2000.data.statemachine.data.scientific.ScientificCalculatorSecondOperandReadState
import java.sql.Ref

class ScientificCalculatorInitialStateTest {

    private val baseCalculatorData = ScientificCalculatorDataEntity()
    private val baseInitialState = ScientificCalculatorInitialState(
        baseCalculatorData
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

    @Test
    fun addToMemoryTest() {
        val dataWithNumber = ScientificCalculatorDataEntity(
            mainString = "3,6"
        )
        val dataWithAddedNumber = ScientificCalculatorDataEntity(
            mainString = "3,6",
            memoryNumber = 3.6
        )
        val dataWithMemory = ScientificCalculatorDataEntity(
            mainString = "10,2",
            memoryNumber = 5.3
        )
        val dataWithAddedMemory = ScientificCalculatorDataEntity(
            mainString = "10,2",
            memoryNumber = 15.5
        )
        val initialStateWithNumber = ScientificCalculatorInitialState(dataWithNumber)
        val initialStateWithAddedNumber = ScientificCalculatorInitialState(dataWithAddedNumber)
        val initialStateWithData = ScientificCalculatorInitialState(dataWithMemory)
        val initialStateWithAddedData = ScientificCalculatorInitialState(dataWithAddedMemory)
        val falseInitialStateWithMemory =  ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity(memoryNumber = 15.5)
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
        assertFalse(ReflectionEquals(ScientificCalculatorInitialState(dataWithAddedNumber.copy(memoryNumber = 4.0))).matches(
            initialStateWithNumber.addNumberToMemory(dataWithNumber)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorInitialState(dataWithMemory.copy(memoryNumber = 15.6))).matches(
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
            memoryNumber = -3.6
        )
        val dataWithMemory = ScientificCalculatorDataEntity(
            mainString = "1,2",
            memoryNumber = 5.3
        )
        val dataWithSubtractedMemory = ScientificCalculatorDataEntity(
            mainString = "1,2",
            memoryNumber = 4.1
        )
        val initialStateWithNumber = ScientificCalculatorInitialState(dataWithNumber)
        val initialStateWithSubtractedNumber = ScientificCalculatorInitialState(dataWithSubtractedNumber)
        val initialStateWithData = ScientificCalculatorInitialState(dataWithMemory)
        val initialStateWithSubtractedData = ScientificCalculatorInitialState(dataWithSubtractedMemory)
        val falseInitialStateWithMemory =  ScientificCalculatorInitialState(
            ScientificCalculatorDataEntity(memoryNumber = 15.5)
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
        assertFalse(ReflectionEquals(ScientificCalculatorInitialState(dataWithSubtractedNumber.copy(memoryNumber = 4.0))).matches(
            initialStateWithNumber.subtractNumberFromMemory(dataWithNumber)
        ))
        assertFalse(ReflectionEquals(ScientificCalculatorInitialState(dataWithMemory.copy(memoryNumber = 15.6))).matches(
            initialStateWithData.subtractNumberFromMemory(dataWithMemory)
        ))
    }

    @Test
    fun clearAllTest() {
        val calculatorWithData = ScientificCalculatorDataEntity(
            mainString = "523,12",
            historyString = "1 +",
            scientificOperationType = ScientificOperationType.PLUS,
            memoryNumber = 5.5
        )
        val initialStateWithMemory = ScientificCalculatorInitialState(calculatorWithData)
        val baseData = baseCalculatorData.copy(memoryNumber = 5.5)

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

        assertTrue(ReflectionEquals(ScientificCalculatorFirstOperandReadState(baseCalculatorData.copy(historyString = "sqrt(0)"))).matches(
            baseInitialState.calculateSquareRoot(baseCalculatorData)
        ))
        assertFalse(ReflectionEquals(falseInitialState).matches(
            baseInitialState.calculateSquareRoot(falseCalculatorData)
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
            operand = 0.0
        )
        val divideCalculatorData = ScientificCalculatorDataEntity(
            historyString = "0$HISTORY_STRING_SPACE_LETTER/",
            scientificOperationType = ScientificOperationType.DIVIDE,
            operand = 0.0
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
            operand = 0.0
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
            operand = 0.0
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
            operand = 2.0
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
        val firstStateData = baseCalculatorData.copy(memoryNumber = 2.36)
        val firstState = ScientificCalculatorInitialState(firstStateData)
        val secondStateData = baseCalculatorData.copy(
            historyString = "(",
            memoryNumber = 2.36,
            prevState = firstState
        )
        val secondState = ScientificCalculatorInitialState(secondStateData)
        val thirdStateData = baseCalculatorData.copy(
            historyString = "((",
            memoryNumber = 2.36,
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
            ScientificCalculatorInitialState(firstStateData.copy(memoryNumber = 2.35))
        ))
    }

    @Test
    fun closeBracketInitialStateTest() {
        val firstStateData = baseCalculatorData.copy(memoryNumber = 2.36)
        val firstState = ScientificCalculatorInitialState(firstStateData)
        val recoveredFirstStateData = baseCalculatorData.copy(
            historyString = "((0))",
            memoryNumber = 0.0
        )
        val recoveredFirstState = ScientificCalculatorInitialState(recoveredFirstStateData)
        val secondStateData = baseCalculatorData.copy(
            historyString = "(",
            memoryNumber = 2.36,
            prevState = firstState
        )
        val recoveredSecondStateData = baseCalculatorData.copy(
            historyString = "((0)",
            memoryNumber = 0.0,
            prevState = firstState
        )
        val recoveredSecondState = ScientificCalculatorInitialState(recoveredSecondStateData)
        val secondState = ScientificCalculatorInitialState(secondStateData)
        val thirdStateData = baseCalculatorData.copy(
            historyString = "((",
            memoryNumber = 0.0,
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
            memoryNumber = 7.0
        )
        val firstState = ScientificCalculatorMathOperationState(firstStateData)
        val recoveredFirstStateData = baseCalculatorData.copy(
            historyString = "9,8$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(0)",
            mainString = "0",
            memoryNumber = 0.0
        )
        val recoveredFirstState = ScientificCalculatorSecondOperandReadState(recoveredFirstStateData)
        val secondStateData = baseCalculatorData.copy(
            historyString = "9,8$HISTORY_STRING_SPACE_LETTER+$HISTORY_STRING_SPACE_LETTER(",
            memoryNumber = 0.0,
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
            mainString = "7,38905609893065",
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
            mainString = "1,175201193643801",
            historyString = "sinh(1)",
            prevState = baseInitialState
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val errorResultData = ScientificCalculatorDataEntity(
            mainString = "10000",
            historyString = "sinh(10000)",
            errorCode = INVALID_INPUT_ERROR_CODE
        )
        val errorResultState = ScientificCalculatorErrorState(errorResultData)

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
        assertTrue(ReflectionEquals(errorResultState).matches(
            baseInitialState.hyperbolicSinus(baseCalculatorData.copy(
                mainString = "10000"
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
            mainString = "2,312438341272752",
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
            mainString = "2,312438341272753",
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
            mainString = "0,939692620785908",
            historyString = "sind(70)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)

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
            mainString = "0,773890681557889",
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
            mainString = "0,891006524188368",//891006524188368
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
            mainString = "0,523598775598299",
            historyString = "asinr(0,5)"
        )
        val nonZeroInputResultState = ScientificCalculatorFirstOperandReadState(nonZeroInputResultData)
        val oneInputResultData = ScientificCalculatorDataEntity(
            mainString = "1,570796326794897",
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
            mainString = "33,333333333333336",
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
}