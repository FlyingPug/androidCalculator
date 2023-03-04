package com.dron.calculator

import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

enum class Operation {
    PLUS, MINUS, MULTIPLE, DIVIDE, EMPTY
}

enum class Status {
    WORKING, ERROR, DONE
}
class CalculatorController() {
    var currentOperator = Operation.EMPTY
    var prevNumber = "0"
    var currentNumber = ""
    var status = Status.WORKING

    // функция, позволяющая контролировать вывод при необходимости
    fun getOutput() : String
    {
        return currentNumber
    }

    // добавляет цифру к текущему числу
    fun addNumber(str : String)
    {
        if(status != Status.WORKING) clear() // если контроллер завершил работу, очищаем для ввода нового числа

        if(currentNumber.length >= 12) return // проверяем на соответствие длине поля (иначе
        if(currentNumber  =="0") clear() // если 0, убираем несущественный
        currentNumber += str
    }

    // добавляет "," к текущему числу
    fun addDecimal()
    {
        if(status != Status.WORKING) clear()
        if(currentNumber.isEmpty()) { // начинаем с 0,
            currentNumber = "0,"
            return
        }
        if(!currentNumber.contains(",")) currentNumber += "," // если запятой уже нет, добавляем
    }

    // указать текущее  действие ( + - * / )
    fun setOperation(newOperator : Operation)
    {
        if(status == Status.DONE) status = Status.WORKING // продолжаем действие с вычесленным числом
        else if(status == Status.ERROR)
        {
            clear()
            return
        }
        if(currentNumber.isEmpty()) prevNumber = "0" // если нету числа, c которым будем выполнять операцию, будем выполнять ее с 0
        if(currentOperator != Operation.EMPTY) // если это уже не первое действие, выисляем предыдущее значение
        {
            equal()
        }
        prevNumber = currentNumber.toString() // сохраняем текущее число
        currentOperator = newOperator // сохраняем текущее действие
        currentNumber = "" // подготавливаем для работы с новым числом
    }

    // меняет знак с между + и -
    fun changeSign()
    {
        if(status == Status.DONE) status = Status.WORKING // продолжаем действие с вычесленным числом
        else if(status == Status.ERROR)
        {
            clear()
            return
        }
        if(currentNumber.startsWith("-"))  currentNumber = currentNumber.replace("-","");
        else currentNumber =  "-${currentNumber}"
    }

    // вычисляет значение выражения
    fun equal()
    {
        if (currentOperator == Operation.EMPTY) {// если не с чем вычислять - не вычисляем
            return
        }
        try {
            val currentOperand = currentNumber.replace(",", ".").toDouble()
            val secondOperand = prevNumber.replace(",", ".").toDouble()
            var result = 0.0
            when (currentOperator) {
                Operation.PLUS -> result = currentOperand + secondOperand
                Operation.MINUS -> result = secondOperand  - currentOperand
                Operation.MULTIPLE  -> result = currentOperand * secondOperand
                Operation.DIVIDE -> result = secondOperand  / currentOperand
                else -> {return}
            }
            clear()
            // благодоря bigdecimal избавляемся от незначимых нулей
            currentNumber = BigDecimal(result).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString().replace(".",",")
            status =  Status.DONE// говорим, что результат выражения выведен и мы готовы к работе с новым выражением
        }
        catch (e : Exception)
        {
            currentNumber = "ERROR"
            status = Status.ERROR
        }
    }

    // очистка экрана
    fun clear()
    {
        currentNumber = ""
        currentOperator = Operation.EMPTY
        prevNumber = "0"
        status = Status.WORKING
    }

    // тупо деление числа на 100
    fun procent()
    {
        if(status == Status.DONE) status = Status.WORKING // продолжаем действие с вычесленным числом
        else if(status == Status.ERROR)
        {
            clear()
            return
        }
        if(currentNumber.isEmpty()) return
        val number = currentNumber.replace(",",".").toDouble()
        currentNumber = (number/100).toString().replace(".",",")
    }

    // удаление последнего символа
    fun remove()
    {
        if(status == Status.DONE) status = Status.WORKING // продолжаем действие с вычесленным числом
        else if(status == Status.ERROR)
        {
            clear()
            return
        }
        if(currentNumber.isEmpty()) return
        currentNumber = currentNumber.dropLast(1)

    }

}