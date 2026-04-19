package com.example.xml

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var etBill: EditText
    private lateinit var spinnerTip: Spinner
    private lateinit var switchRoundUp: Switch
    private lateinit var tvTipAmount: TextView
    private val tipPercentages = intArrayOf(10, 15, 20)
    private val displayOptions = arrayOf("10%", "15%", "20%")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupSpinner()
        setupListeners()
        calculateTip()
    }

    private fun initViews() {
        etBill = findViewById(R.id.etBill)
        spinnerTip = findViewById(R.id.sOption)
        switchRoundUp = findViewById(R.id.sRoundup)
        tvTipAmount = findViewById(R.id.tvAmount)
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            displayOptions
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerTip.adapter = adapter
    }

    private fun setupListeners() {

        switchRoundUp.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }

        spinnerTip.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    calculateTip()
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {}
            }

        etBill.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                calculateTip()
            }

            override fun afterTextChanged(
                s: Editable?
            ) {}
        })
    }

    private fun calculateTip() {

        val billAmount =
            etBill.text.toString().toDoubleOrNull() ?: 0.0

        val position = spinnerTip.selectedItemPosition

        if (position == AdapterView.INVALID_POSITION) {
            return
        }

        val tipPercent = tipPercentages[position]

        var tipAmount =
            billAmount * tipPercent / 100.0

        if (switchRoundUp.isChecked) {
            tipAmount = ceil(tipAmount)
        }

        tvTipAmount.text =
            "Tip Amount: \$%.2f".format(tipAmount)
    }
}