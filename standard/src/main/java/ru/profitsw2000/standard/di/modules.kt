package ru.profitsw2000.standard.di

import org.koin.dsl.module
import ru.profitsw2000.data.data.GeneralCalculatorRepositoryImpl
import ru.profitsw2000.data.domain.GeneralCalculatorRepository
import ru.profitsw2000.data.mappers.GeneralCalculatorMapper
import ru.profitsw2000.standard.presentation.viewmodel.StandardCalculatorViewModel

val standardCalculatorModule = module {

    single { StandardCalculatorViewModel(get()) }
    single { GeneralCalculatorMapper() }
    single<GeneralCalculatorRepository> { GeneralCalculatorRepositoryImpl(get()) }

}