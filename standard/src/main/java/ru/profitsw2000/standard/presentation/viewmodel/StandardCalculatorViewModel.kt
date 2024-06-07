package ru.profitsw2000.standard.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import ru.profitsw2000.data.domain.GeneralCalculatorRepository

class StandardCalculatorViewModel(
    private val generalCalculatorRepository: GeneralCalculatorRepository
) : ViewModel() {

    val calculatorDisplayMainString: LiveData<String> = generalCalculatorRepository.mainStringDataSource.asLiveData()
    val calculatorDisplayHistoryString: LiveData<String> = generalCalculatorRepository.historyStringDataSource.asLiveData()
    val calculatorDisplayMemorySign: LiveData<String> = generalCalculatorRepository.memorySignDataSource.asLiveData()

    fun performOperation(buttonCode: Int) {
        generalCalculatorRepository.operationClicked(buttonCode)
    }

    fun appendDigit(digit: String) {
        generalCalculatorRepository.addDigit(digit)
    }
}