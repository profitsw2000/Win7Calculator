package ru.profitsw2000.data.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.profitsw2000.data.constants.BUTTON_ADD_CODE
import ru.profitsw2000.data.constants.BUTTON_BACKSPACE_CODE
import ru.profitsw2000.data.constants.BUTTON_CLEAR_ALL_CODE
import ru.profitsw2000.data.constants.BUTTON_CLEAR_ENTERED_CODE
import ru.profitsw2000.data.constants.BUTTON_DIVIDE_CODE
import ru.profitsw2000.data.constants.BUTTON_EQUAL_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_ADD_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_CLEAR_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_READ_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_SAVE_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_SUBTRACT_CODE
import ru.profitsw2000.data.constants.BUTTON_MULTIPLY_CODE
import ru.profitsw2000.data.constants.BUTTON_PERCENTAGE_CODE
import ru.profitsw2000.data.constants.BUTTON_PLUS_MINUS_CODE
import ru.profitsw2000.data.constants.BUTTON_RECIPROC_CODE
import ru.profitsw2000.data.constants.BUTTON_SQUARE_ROOT_CODE
import ru.profitsw2000.data.constants.BUTTON_SUBTRACT_CODE
import ru.profitsw2000.data.constants.HISTORY_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.HISTORY_STRING_OVERFLOW_SIGN
import ru.profitsw2000.data.constants.HISTORY_STRING_SPACE_LETTER
import ru.profitsw2000.data.domain.GeneralCalculatorRepository
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.mappers.CalculatorMapper
import ru.profitsw2000.data.model.GeneralCalculatorDataModel
import ru.profitsw2000.data.statemachine.action.CalculatorAction
import ru.profitsw2000.data.statemachine.data.general.GeneralCalculatorInitialState
import ru.profitsw2000.data.statemachine.domain.GeneralCalculatorState
import kotlin.properties.Delegates

class GeneralCalculatorRepositoryImpl(
    private val calculatorMapper: CalculatorMapper
) : GeneralCalculatorRepository {

    private var currentState by Delegates.observable<GeneralCalculatorState>(
        GeneralCalculatorInitialState(
        GeneralCalculatorDataEntity()
    )
    ) { _, _, newValue ->
        renderGeneralCalculatorState(newValue)
    }

    private val generalCalculatorMutableDataSource: MutableStateFlow<GeneralCalculatorDataModel> = MutableStateFlow(GeneralCalculatorDataModel())
    override val generalCalculatorDataSource: StateFlow<GeneralCalculatorDataModel> = generalCalculatorMutableDataSource

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

    override fun renderGeneralCalculatorState(newState: GeneralCalculatorState) {
        val historyStringLength = newState.generalCalculatorDataEntity.historyString.replace(
            HISTORY_STRING_SPACE_LETTER, " ").length
        val historyString = if (historyStringLength > HISTORY_STRING_MAX_DIGIT_NUMBER)
            "$HISTORY_STRING_OVERFLOW_SIGN" +
                    "${newState.generalCalculatorDataEntity.historyString.drop(historyStringLength - HISTORY_STRING_MAX_DIGIT_NUMBER)}"
        else newState.generalCalculatorDataEntity.historyString

        generalCalculatorMutableDataSource.value = calculatorMapper.map(
            newState.generalCalculatorDataEntity.copy(
                historyString = historyString
            )
        )
    }
}