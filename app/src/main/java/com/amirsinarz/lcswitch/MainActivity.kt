package com.amirsinarz.lcswitch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amirsinarz.lcswitch.ui.theme.LCSwitchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LCSwitchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Switches(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Switches(modifier: Modifier = Modifier) {

    var isChecked by remember {
        mutableStateOf(false)
    }

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
        SwitcherC(
            checked = isChecked,
            onCheckedChange = {
                isChecked = isChecked.not()
            },
            width = 60.dp,
            height = 60.dp,
            elevation = 10.dp
        )
        SwitcherL(
            checked = isChecked,
            onCheckedChange = {
                isChecked = isChecked.not()
            },
            width = 66.dp,
            height = 46.dp,
            elevation = 10.dp
        )

        SwitcherC(
            checked = isChecked,
            onCheckedChange = {
                isChecked = isChecked.not()
            },
            width = 60.dp,
            height = 60.dp,
            onColor = Color.Black,
            offColor = Color.Gray,
            elevation = 10.dp
        )
        SwitcherL(
            checked = isChecked,
            onCheckedChange = {
                isChecked = isChecked.not()
            },
            width = 86.dp,
            height = 46.dp,
            onColor = Color.Cyan,
            offColor = Color.Blue,
            iconColor = Color.Yellow
        )
    }

}