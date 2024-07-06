package ru.profitsw2000.navig

import android.content.SharedPreferences

interface Controller {

    fun startStandardCalculatorFragment()

    fun startScientificCalculatorFragment()

    fun startProgrammerCalculatorFragment()

    fun startStatisticCalculatorFragment()

    fun startUnitConversionSheet()

    fun startDateCalculationSheet()

    fun startMortgageSheet()

    fun startAutoLeasingSheet()

    fun startFuelEconomySheet()

}