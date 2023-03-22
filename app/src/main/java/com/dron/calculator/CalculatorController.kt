package com.dron.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorController() {
    private val _outputColor = MutableLiveData<Status>()
    val outputColor: LiveData<Status> get() = _outputColor

    private val currentNumber = MutableLiveData(EMPTY)
    val outputText: LiveData<String> get() = currentNumber

    companion object {
        const val MAX_NUMBER_LENGTH = 12
        const val DEFAULT_NUMBER = "0"
        const val REAL_BASE = "0,"
        const val COMMA = ","
        const val ZERO = "0"
        const val MINUS = "-"
        const val EMPTY = ""
        const val POINT = "."
        const val DIVISION_TO_ZERO = "ZERO DIVIDE"
        const val scaleValue = 5
    }

    var currentOperator = Operation.EMPTY
    var prevNumber = ZERO
    var status = Status.WORKING

    fun makeAction(operation: Operation) {
        when (operation) {
            Operation.PERCENT -> percent()
            Operation.AC -> clear()
            Operation.EQUAL  -> equal()
            Operation.REMOVE -> remove()
            Operation.DECIMAL -> addDecimal()
            Operation.CHANGE_SIGN -> changeSign()
            Operation.PLUS -> setOperation(operation)
            Operation.MINUS -> setOperation(operation)
            Operation.MULTIPLE -> setOperation(operation)
            Operation.DIVIDE -> setOperation(operation)
            else -> {return}
        }
    }

    fun addNumber(str: String) {
        if (status != Status.WORKING) clear()

        if (currentNumber.value!!.length >= MAX_NUMBER_LENGTH) return
        if (currentNumber.value == DEFAULT_NUMBER) clear()
        currentNumber.value += str
    }

    // добавляет "," к текущему числу
    fun addDecimal() {
        if (status != Status.WORKING) clear()
        if (currentNumber.value!!.isEmpty()) {
            currentNumber.value = REAL_BASE
            return
        }
        if (!currentNumber.value!!.contains(COMMA)) currentNumber.value += COMMA // если запятой уже нет, добавляем
    }

    // указать текущее  действие ( + - * / )
    fun setOperation(newOperator: Operation) {
        if (status == Status.DONE) status = Status.WORKING // продолжаем действие с вычесленным числом
        else if (status == Status.ERROR) {
            clear()
            return
        }
        if (currentNumber.value!!.isEmpty()) prevNumber =  ZERO
        if (currentOperator != Operation.EMPTY) // если это уже не первое действие, выисляем предыдущее значение
        {
            equal()
        }
        prevNumber = currentNumber.value.toString()
        currentOperator = newOperator
        currentNumber.value = ""
    }

    // меняет знак с между + и -
    fun changeSign() {
        if (status == Status.DONE) status =
            Status.WORKING
        else if (status == Status.ERROR) {
            clear()
            _outputColor.value = status
            return
        }
        if (currentNumber.value!!.startsWith(MINUS)) currentNumber.value = currentNumber.value!!.replace(MINUS, EMPTY)
        else currentNumber.value = MINUS + currentNumber.value
    }

    // вычисляет значение выражения
    fun equal() {
        if (currentOperator == Operation.EMPTY) {
            return
        }

        val currentOperand = currentNumber.value!!.replace(COMMA, POINT).toDouble()
        val secondOperand = prevNumber.replace(COMMA, POINT).toDouble()
        print(secondOperand)
        if(currentOperator == Operation.DIVIDE && currentOperand == 0.0)
        {
            currentNumber.value = DIVISION_TO_ZERO
            status = Status.ERROR
            return
        }
        val result = when (currentOperator) {
            Operation.PLUS -> currentOperand + secondOperand
            Operation.MINUS -> secondOperand - currentOperand
            Operation.MULTIPLE -> currentOperand * secondOperand
            Operation.DIVIDE -> secondOperand / currentOperand
            else -> {
                return
            }
        }
        clear()
        currentNumber.value =
            BigDecimal(result).setScale(scaleValue, RoundingMode.HALF_UP).stripTrailingZeros()
                .toPlainString().replace(POINT, COMMA)
        status = Status.DONE
    }

    // очистка экрана
    fun clear() {
        currentNumber.value = EMPTY
        currentOperator = Operation.EMPTY
        prevNumber = DEFAULT_NUMBER
        status = Status.WORKING
        _outputColor.value = status
    }

    // тупо деление числа на 100
    fun percent() {
        if (status == Status.DONE) status =
            Status.WORKING
        else if (status == Status.ERROR) {
            clear()
            return
        }
        if (currentNumber.value!!.isEmpty()) return
        val number = currentNumber.value!!.replace(COMMA, POINT).toDouble()
        currentNumber.value = (number / 100).toString().replace(POINT, COMMA)
    }

    // удаление последнего символа
    fun remove() {
        if (status == Status.DONE) status =
            Status.WORKING
        else if (status == Status.ERROR) {
            clear()
            return
        }
        if (currentNumber.value!!.isEmpty()) return
        currentNumber.value = currentNumber.value!!.dropLast(1)

    }

}