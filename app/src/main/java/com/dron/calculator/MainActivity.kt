package com.dron.calculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Button
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import com.dron.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    fun changeColorBasedOnStatus(status: Status, normalColor : Int, errorColor: Int)
    {
        when (status) {
            Status.ERROR -> binding.outputText.setTextColor(errorColor)
            else -> binding.outputText.setTextColor(normalColor)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val animationAlpha = loadAnimation(this, R.anim.alpha);
        val calculator = CalculatorController()

        val normalColor = ContextCompat.getColor(this, R.color.numberColor)
        val errorColor = ContextCompat.getColor(this, R.color.errorColor)

        val numberGroup : Group = binding.numberGroup
        for (id in numberGroup.referencedIds) {
            val numberButton : Button = findViewById(id)
            numberButton.setOnClickListener {
                calculator.addNumber(numberButton.text.toString());
                it.startAnimation(animationAlpha)
                binding.outputText.text = calculator.getOutput()
                changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
            }
        }

        binding.buttonDecimal.setOnClickListener {
            calculator.addDecimal()
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }

        binding.buttonAC.setOnClickListener {
            calculator.clear();
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }
        binding.buttonPlusMinus.setOnClickListener {
            calculator.changeSign();
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }
        binding.buttonProcent.setOnClickListener {
            calculator.procent();
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }
        binding.buttonDivide.setOnClickListener {
            calculator.setOperation(Operation.DIVIDE);
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }
        binding.buttonMultiplie.setOnClickListener {
            calculator.setOperation(Operation.MULTIPLE);
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }
        binding.buttonPlus.setOnClickListener {
            calculator.setOperation(Operation.PLUS);
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }
        binding.buttonMinus.setOnClickListener {
            calculator.setOperation(Operation.MINUS);
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }
        binding.buttonEqual.setOnClickListener {
            calculator.equal();
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }
        binding.removeButton.setOnClickListener {
            calculator.remove();
            it.startAnimation(animationAlpha)
            binding.outputText.text = calculator.getOutput()
            changeColorBasedOnStatus(calculator.status, normalColor, errorColor)
        }
    }
}