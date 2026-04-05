package com.example.modul1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivDice1:ImageView = findViewById(R.id.ivDice1)
        val ivDice2:ImageView = findViewById(R.id.ivDice2)
        val tvStatus:TextView = findViewById(R.id.tvStatus)
        val btnRoll:Button = findViewById(R.id.btnRoll)

        btnRoll.setOnClickListener {
            val number1 = (1..6).random()
            val number2 = (1..6).random()

            ivDice1.setImageResource(getDiceImage(number1))
            ivDice2.setImageResource(getDiceImage(number2))

            tvStatus.visibility = View.VISIBLE

            if (number1 == number2) {
                tvStatus.text = "Selamat, anda dapat dadu double!"
            }
            else {
                tvStatus.text = "Anda belum beruntung!"
            }
        }
    }

    private fun getDiceImage(side: Int): Int {
        return when (side) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}