package ru.profitsw2000.standard.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import ru.profitsw2000.data.domain.GeneralCalculatorRepository
import ru.profitsw2000.data.model.GeneralCalculatorDataModel

class StandardCalculatorViewModel(
    private val generalCalculatorRepository: GeneralCalculatorRepository
) : ViewModel() {

    val generalCalculatorDataModelLiveData: LiveData<GeneralCalculatorDataModel> = generalCalculatorRepository.generalCalculatorDataSource.asLiveData()

    fun performOperation(buttonCode: Int) {
        generalCalculatorRepository.operationClicked(buttonCode)
    }

    fun appendDigit(digit: String) {
        generalCalculatorRepository.addDigit(digit)
    }
}