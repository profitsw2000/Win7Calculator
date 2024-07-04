package ru.profitsw2000.standard.presentation.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.MenuCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.profitsw2000.data.constants.BUTTON_ADD_CODE
import ru.profitsw2000.data.constants.BUTTON_BACKSPACE_CODE
import ru.profitsw2000.data.constants.BUTTON_CLEAR_ALL_CODE
import ru.profitsw2000.data.constants.BUTTON_CLEAR_ENTERED_CODE
import ru.profitsw2000.data.constants.BUTTON_DIVIDE_CODE
import ru.profitsw2000.data.constants.BUTTON_EQUAL_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_ADD_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_CLEAR_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_READ_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_SAVE_CODE
import ru.profitsw2000.data.constants.BUTTON_MEMORY_SUBTRACT_CODE
import ru.profitsw2000.data.constants.BUTTON_MULTIPLY_CODE
import ru.profitsw2000.data.constants.BUTTON_PERCENTAGE_CODE
import ru.profitsw2000.data.constants.BUTTON_PLUS_MINUS_CODE
import ru.profitsw2000.data.constants.BUTTON_RECIPROC_CODE
import ru.profitsw2000.data.constants.BUTTON_SQUARE_ROOT_CODE
import ru.profitsw2000.data.constants.BUTTON_SUBTRACT_CODE
import ru.profitsw2000.data.constants.DIVIDE_ON_ZERO_ERROR_CODE
import ru.profitsw2000.data.constants.INVALID_INPUT_ERROR_CODE
import ru.profitsw2000.data.constants.MAIN_STRING_MAX_DIGIT_NUMBER
import ru.profitsw2000.data.constants.NO_ERROR_CODE
import ru.profitsw2000.data.model.GeneralCalculatorDataModel
import ru.profitsw2000.standard.R
import ru.profitsw2000.standard.databinding.FragmentStandardCalculatorScreenBinding
import ru.profitsw2000.standard.presentation.viewmodel.StandardCalculatorViewModel

class StandardCalculatorScreenFragment : Fragment(R.layout.fragment_standard_calculator_screen) {

    private var _binding: FragmentStandardCalculatorScreenBinding? = null
    private val binding get() = _binding!!
    private val standardCalculatorViewModel: StandardCalculatorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStandardCalculatorScreenBinding.bind(inflater.inflate(R.layout.fragment_standard_calculator_screen, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initViews()
    }

    private fun initViews() {
        initButtons()
        initMenuTextViews()
    }

    private fun initMenuTextViews() = with(binding) {
        menuViewItemTextView.setOnClickListener { showViewPopupMenu(it) }
    }

    private fun initButtons() {
        initMemoryButtons()
        initClearButtons()
        initBasicOperationButtons()
        initSimpleOperationButtons()
        initDigitButtons()
    }

    private fun initMemoryButtons() = with(binding) {
        mcButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_MEMORY_CLEAR_CODE
        ) }
        mrButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_MEMORY_READ_CODE
        ) }
        msButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_MEMORY_SAVE_CODE
        ) }
        mplusButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_MEMORY_ADD_CODE
        ) }
        mminusButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_MEMORY_SUBTRACT_CODE
        ) }
    }

    private fun initClearButtons() = with(binding) {
        backspaceButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_BACKSPACE_CODE
        ) }
        ceButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_CLEAR_ENTERED_CODE
        ) }
        cButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_CLEAR_ALL_CODE
        ) }
    }

    private fun initBasicOperationButtons() = with(binding) {
        plusMinusButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_PLUS_MINUS_CODE
        ) }
        divideButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_DIVIDE_CODE
        ) }
        multiplyButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_MULTIPLY_CODE
        ) }
        minusButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_SUBTRACT_CODE
        ) }
        addButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_ADD_CODE
        ) }
        equalButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_EQUAL_CODE
        ) }
    }

    private fun initDigitButtons() = with(binding) {
        zeroButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.zero_button_text)) }
        oneButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.one_button_text)) }
        twoButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.two_button_text)) }
        threeButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.three_button_text)) }
        fourButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.four_button_text)) }
        fiveButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.five_button_text)) }
        sixButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.six_button_text)) }
        sevenButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.seven_button_text)) }
        eightButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.eight_button_text)) }
        nineButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.nine_button_text)) }
        pointButton.setOnClickListener { standardCalculatorViewModel.appendDigit(
            resources.getString(ru.profitsw2000.core.R.string.comma_button_text)) }
    }

    private fun initSimpleOperationButtons() = with(binding) {
        sqrtButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_SQUARE_ROOT_CODE
        ) }
        percentageButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_PERCENTAGE_CODE
        ) }
        reciprocButton.setOnClickListener { standardCalculatorViewModel.performOperation(
            BUTTON_RECIPROC_CODE
        ) }
    }

    private fun observeData() {
        standardCalculatorViewModel.generalCalculatorDataModelLiveData.observe(viewLifecycleOwner) {
            populateDisplayTextViews(it)
        }
    }

    private fun populateDisplayTextViews(generalCalculatorDataModel: GeneralCalculatorDataModel) = with(binding) {
        populateMainString(generalCalculatorDataModel)
        populateHistoryString(generalCalculatorDataModel)
        populateMemorySign(generalCalculatorDataModel)
    }

    private fun populateMainString(generalCalculatorDataModel: GeneralCalculatorDataModel) = with(binding) {
        inputAndResultTextView.text = when(generalCalculatorDataModel.errorCode) {
            NO_ERROR_CODE -> generalCalculatorDataModel.mainString
            DIVIDE_ON_ZERO_ERROR_CODE -> resources.getString(ru.profitsw2000.core.R.string.divide_on_zero_error_text)
            INVALID_INPUT_ERROR_CODE -> resources.getString(ru.profitsw2000.core.R.string.invalid_input_error_text)
            else -> resources.getString(ru.profitsw2000.core.R.string.unknown_error_text)
        }
    }

    private fun populateHistoryString(generalCalculatorDataModel: GeneralCalculatorDataModel) = with(binding) {
        operationHistoryTextView.text = generalCalculatorDataModel.historyString
    }

    private fun populateMemorySign(generalCalculatorDataModel: GeneralCalculatorDataModel) = with(binding) {
        memorySignTextView.text = generalCalculatorDataModel.memorySign
    }

    private fun showViewPopupMenu(view: View) {
        val viewPopupMenu = PopupMenu(requireActivity(), view)
        var isSecondLevelMenu = false
        viewPopupMenu.inflate(ru.profitsw2000.core.R.menu.view_popup_menu)

        setMenuItemIcon(viewPopupMenu)

        viewPopupMenu
            .setOnMenuItemClickListener {
                when(it.itemId) {
                    ru.profitsw2000.core.R.id.general_calculator -> true
                    ru.profitsw2000.core.R.id.scientific_calculator -> true
                    ru.profitsw2000.core.R.id.programmer_calculator -> true
                    ru.profitsw2000.core.R.id.statistic_calculator -> true

                    ru.profitsw2000.core.R.id.digit_grouping -> true

                    ru.profitsw2000.core.R.id.unit_conversion -> true
                    ru.profitsw2000.core.R.id.date_calculation -> true
                    
                    ru.profitsw2000.core.R.id.calculation_sheets -> {
                        isSecondLevelMenu = true
                        true
                    }

                    ru.profitsw2000.core.R.id.mortgage_calculation -> true
                    ru.profitsw2000.core.R.id.autoleasing_calculation -> true
                    ru.profitsw2000.core.R.id.fuel_economy_miles_on_gallon -> true
                    ru.profitsw2000.core.R.id.fuel_economy_litres_on_km -> true

                    else -> true
                }
            }

        viewPopupMenu.setOnDismissListener {
            if (isSecondLevelMenu) {
                isSecondLevelMenu = false
            }
            else Toast.makeText(requireActivity(), "Dismissed", Toast.LENGTH_SHORT).show()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            viewPopupMenu.menu.setGroupDividerEnabled(true)
        }
        viewPopupMenu.show()
    }

    @SuppressLint("RestrictedApi")
    private fun setMenuItemIcon(popupMenu: PopupMenu) {
        if (popupMenu.menu is MenuBuilder) {
            val menuBuilder = popupMenu.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
        }
        popupMenu
            .menu
            .findItem(ru.profitsw2000.core.R.id.general_calculator)
            .setIcon(ru.profitsw2000.core.R.drawable.picked_menu_item_icon)
    }

    companion object {
        @JvmStatic
        fun newInstance() = StandardCalculatorScreenFragment()
    }
}