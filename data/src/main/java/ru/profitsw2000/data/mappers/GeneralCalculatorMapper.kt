package ru.profitsw2000.data.mappers

import ru.profitsw2000.data.entity.GeneralCalculatorDataEntity
import ru.profitsw2000.data.model.GeneralCalculatorDataModel

class GeneralCalculatorMapper {

    fun map(generalCalculatorDataEntity: GeneralCalculatorDataEntity): GeneralCalculatorDataModel {
        return GeneralCalculatorDataModel(
            mainString = generalCalculatorDataEntity.mainString,
            historyString = generalCalculatorDataEntity.historyString,
            memorySign = if (generalCalculatorDataEntity.memoryNumber == null) "" else "M",
            errorCode = generalCalculatorDataEntity.errorCode
        )
    }

}