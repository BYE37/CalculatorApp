package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.round


class MainActivity : AppCompatActivity() {
    // Init variables
    private var tvInput: TextView ?= null
    private var buttonClear: Button ?= null
    private var buttonDot: Button ?= null
    private var buttonEqual: Button ?= null
    private var buttonRound: Button ?= null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    var operator: String ?= null
    var operatorContains: Boolean = false
    var lastResult: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
        buttonClear = findViewById(R.id.btnCLR)
        buttonDot = findViewById(R.id.btnDOT)
        buttonEqual = findViewById(R.id.btnEQUAL)
        buttonRound = findViewById(R.id.btnROUND)

        // Listener to clear button
        buttonClear?.setOnClickListener {
            clear()
        }

        // decimal point
        buttonDot?.setOnClickListener {
            if (lastNumeric && !lastDot) {
                tvInput?.append(".")
                lastDot = true
                lastNumeric = false
            }
        }

        // equal button (enter in operation)
        buttonEqual?.setOnClickListener {
            if (lastNumeric && operatorContains) {
                var tvValue = tvInput?.text.toString()
                var prefix = ""

                // try and catch block
                try {
                    if (tvValue.startsWith("-")) {
                        prefix = "-"

                        // Create a substring at position 1
                        tvValue = tvValue.substring(1)
                    }

                    if (operator == "-") {
                        val splitValue = tvValue.split("-")

                        val one = prefix + splitValue[0]
                        val two = splitValue[1]

                        tvInput?.text = (one.toDouble() - two.toDouble()).toString()
                    } else if (operator == "*") {
                        val splitValue = tvValue.split("*")

                        val one = prefix + splitValue[0]
                        val two = splitValue[1]

                        tvInput?.text = (one.toDouble() * two.toDouble()).toString()
                    } else if (operator == "/") {
                        val splitValue = tvValue.split("/")

                        val one = prefix + splitValue[0]
                        val two = splitValue[1]

                        tvInput?.text = (one.toDouble() / two.toDouble()).toString()
                    } else if (operator == "+") {
                        val splitValue = tvValue.split("+")

                        val one = prefix + splitValue[0]
                        val two = splitValue[1]

                        tvInput?.text = (one.toDouble() + two.toDouble()).toString()
                    }

                    // Change booleans for control flow
                    operatorContains = false
                    operator = null
                    lastResult = true

                    // if result contains a dot, set containsdot to true
                    if (tvInput?.text.toString().contains(".")) {
                        lastDot = true
                    }

                } catch (e: Exception) {
                    Toast.makeText(this, "Error in calculation: resetting all parameters", Toast.LENGTH_LONG).show()
                    clear()
                }
            } else {
                Toast.makeText(this, "Error: Complete equation with values", Toast.LENGTH_LONG).show()
            }
        }

        // round button to int
        buttonRound?.setOnClickListener {
            if (lastResult) {
                var tvValue = tvInput?.text.toString()
                var tvRound = round(tvValue.toDouble())
                tvInput?.text = tvRound.toInt().toString()
                lastDot = false
            } else {
                Toast.makeText(this, "Error: Can only round on answers", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun clear() {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
        operator = null
        operatorContains = false
        lastResult = false
    }

    fun onDigit(view: View) {
        // Have to ensure null safety
        // If text starts with 0, remove the 0
        if (tvInput?.text.toString().startsWith("0") && tvInput?.text.toString().length <= 1 ) {
            tvInput?.text = ""
        }
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastResult = false
    }
    fun onOperator(view: View) {
        // if text input with text exists, we can call function
        tvInput?.text?.let {
            // "it" is the char sequence we get from the lambda
            // Check if past numeric and if we have an operator already
            if (!operatorContains && tvInput?.text.toString() != "") {
                operator = (view as Button).text.toString()
                tvInput?.append((view).text)
                lastDot = false
                operatorContains = true
                lastNumeric = false
            } else if (tvInput?.text.toString() == "" && (view as Button).text.toString() == "-") {
                tvInput?.append((view).text)
            }
        }
    }

}