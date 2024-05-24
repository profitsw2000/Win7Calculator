package ru.profitsw2000.data.statemachine.action

sealed class CalculatorAction {
    class Digit(digit: String) : CalculatorAction()
    data object ClearMemory : CalculatorAction()
    data object ReadMemory : CalculatorAction()
    data object SaveToMemory : CalculatorAction()
    data object AddToMemory : CalculatorAction()
    data object SubtractFromMemory : CalculatorAction()
    data object Backspace : CalculatorAction()
    data object ClearEntered : CalculatorAction()
    data object Clear : CalculatorAction()
    data object PlusMinus : CalculatorAction()
    data object SquareRoot : CalculatorAction()
    data object Divide : CalculatorAction()
    data object Percentage : CalculatorAction()
    data object Multiply : CalculatorAction()
    data object Recipoc : CalculatorAction()
    data object Subtract : CalculatorAction()
    data object Add : CalculatorAction()
    data object EqualCjk : CalculatorAction()

}