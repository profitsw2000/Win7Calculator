package ru.profitsw2000.data.statemachine.action

sealed class CalculatorAction {
    data class Digit(val digit: String) : CalculatorAction()
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
    data object Equal : CalculatorAction()

    data object RightBracket : CalculatorAction()

    data object LeftBracket : CalculatorAction()

    data object NaturalLogarithm : CalculatorAction()

    data object Inverse : CalculatorAction()

    data object Integer : CalculatorAction()

    data object HyperbolicSinus : CalculatorAction()

    data object Sinus : CalculatorAction()

    data object SquaredX : CalculatorAction()

    data object Factorial : CalculatorAction()

    data object Dms : CalculatorAction()

    data object HyperbolicCosine : CalculatorAction()

    data object Cosine : CalculatorAction()

    data object XPowerY : CalculatorAction()

    data object YRootOfX : CalculatorAction()

    data object Pi : CalculatorAction()

    data object HyperbolicTangent : CalculatorAction()

    data object Tangent : CalculatorAction()

    data object XPowerThree : CalculatorAction()

    data object ThirdRootOfX : CalculatorAction()

    data object FixedToExponent : CalculatorAction()

    data object ExponentialForm : CalculatorAction()

    data object Modulus : CalculatorAction()

    data object Logarithm : CalculatorAction()

    data object TenToPowerOfX : CalculatorAction()

    data object ExponentOfX : CalculatorAction()

    data object Fraction : CalculatorAction()

    data object HyperbolicArcSinus : CalculatorAction()

    data object ArcSinus : CalculatorAction()

    data object DecimalDegrees : CalculatorAction()

    data object HyperbolicArcCosine : CalculatorAction()

    data object ArcCosine : CalculatorAction()

    data object DoublePi : CalculatorAction()

    data object HyperbolicArcTangent : CalculatorAction()

    data object ArcTangent : CalculatorAction()



}