package ru.profitsw2000.data.statemachine.domain

import ru.profitsw2000.data.constants.MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

interface GeneralCalculatorState : CalculatorState {

    val generalCalculatorDataEntity: GeneralCalculatorDataEntity

    fun clearAll(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorState

}