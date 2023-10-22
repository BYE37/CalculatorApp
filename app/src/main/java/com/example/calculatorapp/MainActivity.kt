package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private var tvInput: TextView ?= null
    private var buttonClear: Button ?= null
    private var buttonDot: Button ?= null
    private var buttonEqual: Button ?= null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    var operator: String ?= null
    var operatorContains: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
        buttonClear = findViewById(R.id.btnCLR)
        buttonDot = findViewById(R.id.btnDOT)
        buttonEqual = findViewById(R.id.btnEQUAL)

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
                    operatorContains = false
                    operator = null
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
    }
    fun clear() {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
        operator = null
        operatorContains = false
    }

    fun onDigit(view: View) {
        // Have to ensure null safety
        tvInput?.append((view as Button).text)
        lastNumeric = true
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