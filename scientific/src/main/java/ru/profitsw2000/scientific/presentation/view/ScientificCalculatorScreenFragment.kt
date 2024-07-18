package ru.profitsw2000.scientific.presentation.view

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.profitsw2000.navig.Controller
import ru.profitsw2000.scientific.R
import ru.profitsw2000.scientific.databinding.FragmentScientificCalculatorScreenBinding

class ScientificCalculatorScreenFragment : Fragment(R.layout.fragment_scientific_calculator_screen) {

    private var _binding: FragmentScientificCalculatorScreenBinding? = null
    private val binding get() = _binding!!
    private val controller by lazy {
        activity as Controller
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is Controller)
            throw IllegalStateException(
                resources.getString(ru.profitsw2000.core.R.string.activity_not_implement_controller_interface_exception_text)
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScientificCalculatorScreenBinding.bind(inflater.inflate(R.layout.fragment_scientific_calculator_screen, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = ScientificCalculatorScreenFragment()
    }
}