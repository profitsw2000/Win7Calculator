package ru.profitsw2000.data.mappers

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.entity.ScientificCalculatorDataEntity
import ru.profitsw2000.data.model.GeneralCalculatorDataModel

class CalculatorMapper {

    fun map(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorDataModel {
        return GeneralCalculatorDataModel(
            mainString = generalCalculatorDataEntity.mainString,
            historyString = generalCalculatorDataEntity.historyString,
            memorySign = if (generalCalculatorDataEntity.memoryNumber == null) "" else "M",
            errorCode = generalCalculatorDataEntity.errorCode
        )
    }

    fun map(scientificCalculatorDataEntity: ScientificCalculatorDataEntity): GeneralCalculatorDataModel {
        return GeneralCalculatorDataModel(
            mainString = scientificCalculatorDataEntity.mainString,
            historyString = scientificCalculatorDataEntity.historyString,
            memorySign = if (scientificCalculatorDataEntity.memoryNumber == null) "" else "M",
            errorCode = scientificCalculatorDataEntity.errorCode
        )
    }
}