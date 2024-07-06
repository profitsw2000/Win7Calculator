package ru.profitsw2000.scientific.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.profitsw2000.scientific.R
class ScientificCalculatorScreenFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scientific_calculator_screen, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ScientificCalculatorScreenFragment()
    }
}