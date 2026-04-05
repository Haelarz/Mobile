package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                DiceRollerApp()
                }
            }
        }
    }

@Composable
fun DiceRollerApp() {
    var dice1 by remember { mutableStateOf(0) }
    var dice2 by remember { mutableStateOf(0) }
    var statusMessage by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            DiceImage(dice1)
            Spacer(modifier = Modifier.width(15.dp))
            DiceImage(dice2)
        }

        Button(
            onClick = {
                dice1 = (1..6).random()
                dice2 = (1..6).random()

                statusMessage = if (dice1 == dice2) "Selamat, anda dapat dadu double!"
                                else "Anda belum beruntung!"
            },
            modifier = Modifier.padding(top = 30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD0BCFF))
        ) {
            Text("Roll", color = Color(0xFF381E72))
        }

        if (statusMessage.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .background(Color(0xFFEADDFF))
                    .padding(15.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = statusMessage, color = Color.Black)
            }
        }
    }
}
@Composable
fun DiceImage(number: Int){
    val imageRes = when (number) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_0
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = Modifier.size(150.dp)
    )
}