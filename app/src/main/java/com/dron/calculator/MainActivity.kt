package com.dron.calculator

import android.os.Bundle
import android.view.animation.AnimationUtils.loadAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dron.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val animationAlpha = loadAnimation(this, R.anim.alpha);
        val calculator = CalculatorController()

        val normalColor = ContextCompat.getColor(this, R.color.numberColor)
        val errorColor = ContextCompat.getColor(this, R.color.errorColor)

        // возможно это не самый красивый способ, и имеет смысл пройтись по группе кнопок циклом, но хотяб удобно
        var numberButtons = listOf(
            binding.button0,
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8,
            binding.button9
        )

        numberButtons.forEachIndexed { index, numberButton ->
            numberButton.setOnClickListener {
                calculator.addNumber(index.toString())
                it.startAnimation(animationAlpha)
                binding.outputText.text = calculator.getOutput()
                changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
            }
        }

        val actionButtons = mapOf(
            binding.buttonDecimal to Operation.DECIMAL,
            binding.buttonAC to Operation.AC,
            binding.buttonPlusMinus to Operation.CHANGE_SIGN,
            binding.buttonProcent to Operation.PROCENT,
            binding.buttonDivide to Operation.DIVIDE,
            binding.buttonMultiplie to Operation.MULTIPLE,
            binding.buttonPlus to Operation.MULTIPLE,
            binding.buttonMinus to Operation.MINUS,
            binding.buttonEqual to Operation.EQUAL,
            binding.buttonRemove to Operation.REMOVE
        )
        // не уверен< что я правильно пользуюсь этими штуками
        val outputColorLiveData: MutableLiveData<Status> = MutableLiveData()
        outputColorLiveData.observe(this, Observer {
            changeColorBasedOnStatus(it, normalColor, errorColor)
        })
        val outputTextLiveData: MutableLiveData<String> = MutableLiveData()
        outputTextLiveData.observe(this, Observer {
            binding.outputText.text = it.toString()
        })

        actionButtons.forEach { (key, value) ->
            key.setOnClickListener {
                calculator.makeAction(value)
                it.startAnimation(animationAlpha)
                outputColorLiveData.value = calculator.status
                outputTextLiveData.value = calculator.getOutput()
                changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
            }
        }
    }

    private fun changeColorBasedOnStatus(status: Status, normalColor: Int, errorColor: Int) {
        when (status) {
            Status.ERROR -> binding.outputText.setTextColor(errorColor)
            else -> binding.outputText.setTextColor(normalColor)
        }
    }
}