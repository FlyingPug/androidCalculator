package com.dron.calculator

import android.os.Bundle
import android.view.animation.AnimationUtils.loadAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dron.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val animationAlpha = loadAnimation(this, R.anim.alpha)
        val calculator = CalculatorController()

        val normalColor = ContextCompat.getColor(this, R.color.numberColor)
        val errorColor = ContextCompat.getColor(this, R.color.errorColor)

        // возможно это не самый красивый способ, и имеет смысл пройтись по группе кнопок циклом, но хотяб удобно
        val numberButtons = listOf(
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
            }
        }

        val actionButtons = mapOf(
            binding.buttonDecimal to Operation.DECIMAL,
            binding.buttonAC to Operation.AC,
            binding.buttonPlusMinus to Operation.CHANGE_SIGN,
            binding.buttonProcent to Operation.PERCENT,
            binding.buttonDivide to Operation.DIVIDE,
            binding.buttonMultiplie to Operation.MULTIPLE,
            binding.buttonPlus to Operation.MULTIPLE,
            binding.buttonMinus to Operation.MINUS,
            binding.buttonEqual to Operation.EQUAL,
            binding.buttonRemove to Operation.REMOVE
        )


        calculator.outputColor.observe(this) {
            changeColorBasedOnStatus(it, normalColor, errorColor)
        }
        calculator.outputText.observe(this) {
            binding.outputText.text = it.toString()
        }

        actionButtons.forEach { (key, value) ->
            key.setOnClickListener {
                calculator.makeAction(value)
                it.startAnimation(animationAlpha)
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