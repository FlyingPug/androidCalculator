package com.dron.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.dron.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animationAlpha = loadAnimation(this, R.anim.alpha);
        val normalColor = ContextCompat.getColor(this, R.color.numberColor)
        var errorColor = ContextCompat.getColor(this, R.color.errorColor)
        val calculator = CalculatorController(findViewById(R.id.outputText),normalColor,errorColor)

        binding.button0.setOnClickListener { calculator.addNumber("0"); it.startAnimation(animationAlpha) }
        binding.button1.setOnClickListener { calculator.addNumber("1"); it.startAnimation(animationAlpha) }
        binding.button2.setOnClickListener { calculator.addNumber("2"); it.startAnimation(animationAlpha) }
        binding.button3.setOnClickListener { calculator.addNumber("3"); it.startAnimation(animationAlpha) }
        binding.button4.setOnClickListener { calculator.addNumber("4"); it.startAnimation(animationAlpha) }
        binding.button5.setOnClickListener { calculator.addNumber("5"); it.startAnimation(animationAlpha) }
        binding.button6.setOnClickListener { calculator.addNumber("6"); it.startAnimation(animationAlpha) }
        binding.button7.setOnClickListener { calculator.addNumber("7"); it.startAnimation(animationAlpha) }
        binding.button8.setOnClickListener { calculator.addNumber("8"); it.startAnimation(animationAlpha) }
        binding.button9.setOnClickListener { calculator.addNumber("9"); it.startAnimation(animationAlpha) }

        binding.buttonDecimal.setOnClickListener { calculator.addDecimal(); it.startAnimation(animationAlpha) }
        binding.buttonAC.setOnClickListener { calculator.clear(); it.startAnimation(animationAlpha) }
        binding.buttonPlusMinus.setOnClickListener { calculator.changeSign(); it.startAnimation(animationAlpha) }
        binding.buttonProcent.setOnClickListener { calculator.procent(); it.startAnimation(animationAlpha) }
        binding.buttonDivide.setOnClickListener { calculator.setOperation("/"); it.startAnimation(animationAlpha)}
        binding.buttonMultiplie.setOnClickListener { calculator.setOperation("*"); it.startAnimation(animationAlpha)}
        binding.buttonPlus.setOnClickListener { calculator.setOperation("+"); it.startAnimation(animationAlpha) }
        binding.buttonMinus.setOnClickListener { calculator.setOperation("-"); it.startAnimation(animationAlpha) }
        binding.buttonEqual.setOnClickListener { calculator.equal(); it.startAnimation(animationAlpha) }
        binding.removeButton.setOnClickListener { calculator.remove(); it.startAnimation(animationAlpha) }
    }
}