package ru.profitsw2000.navig

import android.content.SharedPreferences

interface Controller {

    fun startStandardCalculatorFragment(sharedPreferences: SharedPreferences)

    fun startScientificCalculatorFragment(sharedPreferences: SharedPreferences)

    fun startProgrammerCalculatorFragment(sharedPreferences: SharedPreferences)

    fun startStatisticCalculatorFragment(sharedPreferences: SharedPreferences)

    fun startUnitConversionSheet(sharedPreferences: SharedPreferences)

    fun startDateCalculationSheet(sharedPreferences: SharedPreferences)

    fun startMortgageSheet(sharedPreferences: SharedPreferences)

    fun startAutoLeasingSheet(sharedPreferences: SharedPreferences)

    fun startFuelEconomySheet(sharedPreferences: SharedPreferences)

}