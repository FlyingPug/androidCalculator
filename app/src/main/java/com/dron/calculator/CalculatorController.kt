package com.dron.calculator

import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorController(val outputTextView : TextView, val colorNormal : Int, val colorError : Int) {
    init
    {
        outputTextView.text = ""
    }
    var currentOperator = ""
    var prevNumber = "0"
    var waitForClear = false

    // добавляет цифру к текущему числу
    fun addNumber(str : String)
    {
        if(waitForClear) clear() // если операция была выполнена, очищаем для новой операции

        if(outputTextView.text.length >= 12) return // проверяем на соответствие длине поля (иначе
        if(outputTextView.text =="0") clear() // если 0, убираем несущественный
        outputTextView.append(str)
    }

    // добавляет "," к текущему числу
    fun addDecimal()
    {
        if(waitForClear) clear()
        if(outputTextView.text.isEmpty()) { // начинаем с 0,
            outputTextView.text = "0,"
            return
        }
        if(!outputTextView.text.contains(",")) outputTextView.append(",") // если запятой уже нет, добавляем
    }

    // указать текущее  действие ( + - * / )
    fun setOperation(newOperator : String)
    {
        if(waitForClear) waitForClear = false // продолжаем действие с вычесленным числом
        if(outputTextView.text.isEmpty()) prevNumber = "0" // если нету числа, c которым будем выполнять операцию, будем выполнять ее с 0
        if(currentOperator != "") // если это уже не первое действие, выисляем предыдущее значение
        {
            equal()
        }
        prevNumber = outputTextView.text.toString() // сохраняем текущее число
        currentOperator = newOperator // сохраняем текущее действие
        outputTextView.text = "" // подготавливаем для работы с новым числом
    }

    // меняет знак с между + и -
    fun changeSign()
    {
        if(waitForClear) waitForClear = false
        if(outputTextView.text.startsWith("-"))  outputTextView.text = outputTextView.text.toString().replace("-","");
        else outputTextView.text =  "-${outputTextView.text}"
    }

    // вычисляет значение выражения
    fun equal()
    {
        if (currentOperator.isEmpty()) {// если не с чем вычислять - не вычисляем
            return
        }
        try {
            val currentOperand = (outputTextView.text.toString()).replace(",", ".").toDouble()
            val secondOperand = prevNumber.replace(",", ".").toDouble()
            var result = 0.0
            when (currentOperator) {
                "+" -> result = currentOperand + secondOperand
                "-" -> result = secondOperand  - currentOperand
                "*" -> result = currentOperand * secondOperand
                "/" -> result = secondOperand  / currentOperand
            }
            clear()
            // благодоря bigdecimal избавляемся от незначимых нулей
            outputTextView.text = BigDecimal(result).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString().replace(".",",")
            waitForClear = true// говорим, что результат выражения выведен и мы готовы к работе с новым выражением
        }
        catch (e : Exception)
        {
            outputTextView.text = "ERROR"
            outputTextView.setTextColor(colorError)
            waitForClear = true
        }
    }

    // очистка экрана
    fun clear()
    {
        outputTextView.setTextColor(colorNormal)
        outputTextView.text = ""
        currentOperator = ""
        prevNumber = "0"
        waitForClear = false
    }

    // тупо деление числа на 100
    fun procent()
    {
        if(waitForClear) waitForClear = false
        val number = (outputTextView.text.toString()).replace(",",".").toDouble()
        outputTextView.text = (number/100).toString().replace(".",",")
    }

    // удаление последнего символа
    fun remove()
    {
        if(waitForClear) waitForClear = false
        if(outputTextView.text.isEmpty()) return
        outputTextView.text = outputTextView.text.toString().dropLast(1)

    }

}