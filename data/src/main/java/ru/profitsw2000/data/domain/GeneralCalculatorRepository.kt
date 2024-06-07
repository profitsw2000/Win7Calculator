package ru.profitsw2000.data.domain

import kotlinx.coroutines.flow.StateFlow

interface GeneralCalculatorRepository : CommonCalculatorRepository {

    val mainStringDataSource: StateFlow<String>

    val historyStringDataSource: StateFlow<String>

    val memorySignDataSource: StateFlow<String>

}