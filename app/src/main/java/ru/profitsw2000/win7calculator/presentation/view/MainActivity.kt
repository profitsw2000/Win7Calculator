package ru.profitsw2000.win7calculator.presentation.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.profitsw2000.navig.Controller
import ru.profitsw2000.standard.presentation.view.StandardCalculatorScreenFragment
import ru.profitsw2000.win7calculator.R
import ru.profitsw2000.win7calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Controller {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startStandardCalculatorFragment()
    }

    override fun startStandardCalculatorFragment() {
        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.fragment_container, StandardCalculatorScreenFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }

    override fun startScientificCalculatorFragment() {
        TODO("Not yet implemented")
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
}