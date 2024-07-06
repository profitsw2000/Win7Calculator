package ru.profitsw2000.win7calculator.presentation.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import ru.profitsw2000.navig.Controller
import ru.profitsw2000.standard.presentation.view.StandardCalculatorScreenFragment
import ru.profitsw2000.utils.IS_AUTO_LEASING_SHEET
import ru.profitsw2000.utils.IS_DATE_CALCULATION_SHEET
import ru.profitsw2000.utils.IS_FUEL_ECONOMY_SHEET
import ru.profitsw2000.utils.IS_GENERAL_CALCULATOR
import ru.profitsw2000.utils.IS_MORTGAGE_SHEET
import ru.profitsw2000.utils.IS_PROGRAMMER_CALCULATOR
import ru.profitsw2000.utils.IS_SCIENTIFIC_CALCULATOR
import ru.profitsw2000.utils.IS_STATISTIC_CALCULATOR
import ru.profitsw2000.utils.IS_UNIT_CONVERSION_SHEET
import ru.profitsw2000.utils.LAST_FRAGMENT_KEY
import ru.profitsw2000.utils.SHARED_PREFERENCE_NAME
import ru.profitsw2000.win7calculator.R
import ru.profitsw2000.win7calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Controller {

    private lateinit var binding: ActivityMainBinding
    private val sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startLastOpenedFragment(sharedPreferences)
    }

    override fun startStandardCalculatorFragment(sharedPreferences: SharedPreferences) {

        startAndSaveFragment(sharedPreferences, StandardCalculatorScreenFragment.newInstance())
    }

    override fun startScientificCalculatorFragment(sharedPreferences: SharedPreferences) {
        TODO("Not yet implemented")
    }

    override fun startProgrammerCalculatorFragment(sharedPreferences: SharedPreferences) {
        TODO("Not yet implemented")
    }

    override fun startStatisticCalculatorFragment(sharedPreferences: SharedPreferences) {
        TODO("Not yet implemented")
    }

    override fun startUnitConversionSheet(sharedPreferences: SharedPreferences) {
        TODO("Not yet implemented")
    }

    override fun startDateCalculationSheet(sharedPreferences: SharedPreferences) {
        TODO("Not yet implemented")
    }

    override fun startMortgageSheet(sharedPreferences: SharedPreferences) {
        TODO("Not yet implemented")
    }

    override fun startAutoLeasingSheet(sharedPreferences: SharedPreferences) {
        TODO("Not yet implemented")
    }

    override fun startFuelEconomySheet(sharedPreferences: SharedPreferences) {
        TODO("Not yet implemented")
    }

    private fun startLastOpenedFragment(sharedPreferences: SharedPreferences) {
        when(sharedPreferences.getInt(LAST_FRAGMENT_KEY, IS_GENERAL_CALCULATOR)) {
            IS_GENERAL_CALCULATOR -> startStandardCalculatorFragment(sharedPreferences)
            IS_SCIENTIFIC_CALCULATOR -> startScientificCalculatorFragment(sharedPreferences)
            IS_PROGRAMMER_CALCULATOR -> startProgrammerCalculatorFragment(sharedPreferences)
            IS_STATISTIC_CALCULATOR -> startStatisticCalculatorFragment(sharedPreferences)
            IS_UNIT_CONVERSION_SHEET -> startUnitConversionSheet(sharedPreferences)
            IS_DATE_CALCULATION_SHEET -> startDateCalculationSheet(sharedPreferences)
            IS_MORTGAGE_SHEET -> startMortgageSheet(sharedPreferences)
            IS_AUTO_LEASING_SHEET -> startAutoLeasingSheet(sharedPreferences)
            IS_FUEL_ECONOMY_SHEET -> startFuelEconomySheet(sharedPreferences)
            else -> startStandardCalculatorFragment(sharedPreferences)
        }
    }

    private fun startAndSaveFragment(
        sharedPreferences: SharedPreferences,
        fragment: Fragment
    ) {
        sharedPreferences
            .edit()
            .putInt(LAST_FRAGMENT_KEY, IS_GENERAL_CALCULATOR)
            .apply()

        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss()
        }
    }
}