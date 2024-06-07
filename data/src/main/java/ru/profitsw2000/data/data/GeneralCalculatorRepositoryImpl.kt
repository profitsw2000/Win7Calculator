package ru.profitsw2000.data.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.profitsw2000.data.BUTTON_ADD_CODE
import ru.profitsw2000.data.BUTTON_BACKSPACE_CODE
import ru.profitsw2000.data.BUTTON_CLEAR_ALL_CODE
import ru.profitsw2000.data.BUTTON_CLEAR_ENTERED_CODE
import ru.profitsw2000.data.BUTTON_DIGIT_CODE
import ru.profitsw2000.data.BUTTON_DIVIDE_CODE
import ru.profitsw2000.data.BUTTON_EQUAL_CODE
import ru.profitsw2000.data.BUTTON_MEMORY_ADD_CODE
import ru.profitsw2000.data.BUTTON_MEMORY_CLEAR_CODE
import ru.profitsw2000.data.BUTTON_MEMORY_READ_CODE
import ru.profitsw2000.data.BUTTON_MEMORY_SAVE_CODE
import ru.profitsw2000.data.BUTTON_MEMORY_SUBTRACT_CODE
import ru.profitsw2000.data.BUTTON_MULTIPLY_CODE
import ru.profitsw2000.data.BUTTON_PERCENTAGE_CODE
import ru.profitsw2000.data.BUTTON_PLUS_MINUS_CODE
import ru.profitsw2000.data.BUTTON_RECIPROC_CODE
import ru.profitsw2000.data.BUTTON_SQUARE_ROOT_CODE
import ru.profitsw2000.data.BUTTON_SUBTRACT_CODE
import ru.profitsw2000.data.domain.GeneralCalculatorRepository
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.data.ErrorState
import ru.profitsw2000.data.statemachine.data.FirstOperandInputState
import ru.profitsw2000.data.statemachine.data.InitialState
import ru.profitsw2000.data.statemachine.data.OperationResultState
import ru.profitsw2000.data.statemachine.data.SecondOperandInputState
import ru.profitsw2000.data.statemachine.domain.CalculatorState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import kotlin.properties.Delegates

class GeneralCalculatorRepositoryImpl() : GeneralCalculatorRepository {

    private var currentState by Delegates.observable<GeneralCalculatorState>(InitialState(false)) { _, oldValue, newValue ->
        renderGeneralCalculatorState(newValue, oldValue)
    }

    private val mainStringMutableDataSource: MutableStateFlow<String> = MutableStateFlow("0")
    override val mainStringDataSource: StateFlow<String> = mainStringMutableDataSource

    private val historyStringMutableDataSource: MutableStateFlow<String> = MutableStateFlow("")
    override val historyStringDataSource: StateFlow<String> = historyStringMutableDataSource

    private val memorySignMutableDataSource: MutableStateFlow<String> = MutableStateFlow("")
    override val memorySignDataSource: StateFlow<String> = memorySignMutableDataSource

    override fun operationClicked(buttonCode: Int) {
        when(buttonCode) {
            BUTTON_MEMORY_CLEAR_CODE -> updateCurrentState(CalculatorAction.ClearMemory)
            BUTTON_MEMORY_READ_CODE -> updateCurrentState(CalculatorAction.ReadMemory)
            BUTTON_MEMORY_SAVE_CODE -> updateCurrentState(CalculatorAction.SaveToMemory)
            BUTTON_MEMORY_ADD_CODE -> updateCurrentState(CalculatorAction.AddToMemory)
            BUTTON_MEMORY_SUBTRACT_CODE -> updateCurrentState(CalculatorAction.SubtractFromMemory)
            BUTTON_BACKSPACE_CODE -> updateCurrentState(CalculatorAction.Backspace)
            BUTTON_CLEAR_ENTERED_CODE -> updateCurrentState(CalculatorAction.ClearEntered)
            BUTTON_CLEAR_ALL_CODE -> updateCurrentState(CalculatorAction.Clear)
            BUTTON_PLUS_MINUS_CODE -> updateCurrentState(CalculatorAction.PlusMinus)
            BUTTON_SQUARE_ROOT_CODE -> updateCurrentState(CalculatorAction.SquareRoot)
            BUTTON_DIVIDE_CODE -> updateCurrentState(CalculatorAction.Divide)
            BUTTON_MULTIPLY_CODE -> updateCurrentState(CalculatorAction.Multiply)
            BUTTON_SUBTRACT_CODE -> updateCurrentState(CalculatorAction.Subtract)
            BUTTON_ADD_CODE -> updateCurrentState(CalculatorAction.Add)
            BUTTON_PERCENTAGE_CODE -> updateCurrentState(CalculatorAction.Percentage)
            BUTTON_RECIPROC_CODE -> updateCurrentState(CalculatorAction.Recipoc)
            BUTTON_EQUAL_CODE -> updateCurrentState(CalculatorAction.Equal)
            else -> {}
        }
    }

    override fun addDigit(digit: String) {
        updateCurrentState(CalculatorAction.Digit(digit))
    }

    private fun updateCurrentState(action: CalculatorAction) {
        currentState = currentState.consumeAction(action) as GeneralCalculatorState
    }

    private fun renderGeneralCalculatorState(newState: GeneralCalculatorState, oldState: GeneralCalculatorState) {
        when(newState) {
            is InitialState -> setMemoryValue(newState.memoryWritten)
            is FirstOperandInputState -> setMainAndMemoryValues(newState.mainString, newState.memoryWritten)
            is SecondOperandInputState -> setCalculatorDisplayValues(newState.mainString, newState.historyString, newState.memoryWritten)
            is OperationResultState -> setCalculatorDisplayValues(newState.mainString, newState.historyString, newState.memoryWritten)
            is ErrorState -> setCalculatorDisplayValues(newState.mainString, newState.historyString, newState.memoryWritten)
            else -> {}
        }
    }

    private fun setMemoryValue(memoryWritten: Boolean) {
        if (memoryWritten) memorySignMutableDataSource.value = "M"
        else memorySignMutableDataSource.value = ""
    }

    private fun setMainAndMemoryValues(mainString: String, memoryWritten: Boolean) {
        setMemoryValue(memoryWritten)
        mainStringMutableDataSource.value = mainString
    }

    private fun setCalculatorDisplayValues(mainString: String, historyString: String, memoryWritten: Boolean) {
        setMainAndMemoryValues(mainString, memoryWritten)
        historyStringMutableDataSource.value = historyString
    }
}