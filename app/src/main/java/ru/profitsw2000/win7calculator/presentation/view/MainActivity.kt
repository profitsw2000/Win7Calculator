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
import ru.profitsw2000.scientific.presentation.view.ScientificCalculatorScreenFragment
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
    private val sharedPreferences: SharedPreferences by lazy {
        this.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startLastOpenedFragment()
    }

    override fun startStandardCalculatorFragment() {
        startAndSaveFragment(sharedPreferences, StandardCalculatorScreenFragment.newInstance(), IS_GENERAL_CALCULATOR)
    }

    override fun startScientificCalculatorFragment() {
        startAndSaveFragment(sharedPreferences, ScientificCalculatorScreenFragment.newInstance(), IS_SCIENTIFIC_CALCULATOR)
    }

    override fun startProgrammerCalculatorFragment() {
        TODO("Not yet implemented")
    }

    override fun startStatisticCalculatorFragment() {
        TODO("Not yet implemented")
    }

    override fun startUnitConversionSheet() {
        TODO("Not yet implemented")
    }

    override fun startDateCalculationSheet() {
        TODO("Not yet implemented")
    }

    override fun startMortgageSheet() {
        TODO("Not yet implemented")
    }

    override fun startAutoLeasingSheet() {
        TODO("Not yet implemented")
    }

    override fun startFuelEconomySheet() {
        TODO("Not yet implemented")
    }

    private fun startLastOpenedFragment() {
        when(sharedPreferences.getInt(LAST_FRAGMENT_KEY, IS_GENERAL_CALCULATOR)) {
            IS_GENERAL_CALCULATOR -> startStandardCalculatorFragment()
            IS_SCIENTIFIC_CALCULATOR -> startScientificCalculatorFragment()
            IS_PROGRAMMER_CALCULATOR -> startProgrammerCalculatorFragment()
            IS_STATISTIC_CALCULATOR -> startStatisticCalculatorFragment()
            IS_UNIT_CONVERSION_SHEET -> startUnitConversionSheet()
            IS_DATE_CALCULATION_SHEET -> startDateCalculationSheet()
            IS_MORTGAGE_SHEET -> startMortgageSheet()
            IS_AUTO_LEASING_SHEET -> startAutoLeasingSheet()
            IS_FUEL_ECONOMY_SHEET -> startFuelEconomySheet()
            else -> startStandardCalculatorFragment()
        }
    }

    private fun startAndSaveFragment(
        sharedPreferences: SharedPreferences,
        fragment: Fragment,
        fragmentKey: Int
    ) {
        sharedPreferences
            .edit()
            .putInt(LAST_FRAGMENT_KEY, fragmentKey)
            .apply()

        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss()
        }
    }
}