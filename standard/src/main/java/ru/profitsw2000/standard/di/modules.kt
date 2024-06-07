package ru.profitsw2000.standard.di

import org.koin.dsl.module
import ru.profitsw2000.data.data.GeneralCalculatorRepositoryImpl
import ru.profitsw2000.data.domain.GeneralCalculatorRepository

val standardCalculatorModule = module {

    single<GeneralCalculatorRepository> { GeneralCalculatorRepositoryImpl() }

}