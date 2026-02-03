package com.fathan.architechture.presentation.calculator

import com.fathan.architechture.domain.model.CalculatorOperation

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null
)
