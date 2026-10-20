package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.platform.LocalContext

//import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {


                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    priceCalculator(on_click = {
                        val intent = Intent(this, SecondActivity::class.java)
                        startActivity(intent)
                    })
                }
            }


        }
    }

    override fun onResume(){
        super.onResume()
    }

}

@Composable
fun ButtonMasher(modifier: Modifier = Modifier) {

    var clicks by remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier
        .fillMaxSize()
        .clickable{
            focusManager.clearFocus()
        }
        .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Button(
            onClick = { clicks++ }
        ) {
            Text(
                text = "Me clica"
            )
        }
        Text(
            text = "Cliques: $clicks"
        )
    }
}

@Composable
fun priceCalculator(on_click: () -> Unit){
    var gasoline_price by rememberSaveable { mutableStateOf("") }
    var alcohol_price by rememberSaveable { mutableStateOf("") }

    var porcentage_switch by rememberSaveable() { mutableStateOf(false) }

    var calc_result by rememberSaveable() { mutableStateOf("") }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()
        .clickable{
            focusManager.clearFocus()
        }
        .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Álcool ou Gasolina?"
        )
        OutlinedTextField(
            value = gasoline_price,
            onValueChange = {
                gasoline_price = it
            },
            label = { Text("Preço da gasolina")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = alcohol_price,
            onValueChange = {
                alcohol_price = it
            },
            label = { Text("Preço do álcool")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth()
            .padding(top = 32.dp, bottom = 64.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) { Switch(
            checked = porcentage_switch,
            onCheckedChange = {
                porcentage_switch = it
            },
            modifier = Modifier.padding(end = 10.dp)
            )
            Text("75%")
        }
        Button(
            onClick = {
                focusManager.clearFocus()
                val alc = alcohol_price.toDoubleOrNull()
                val gas = gasoline_price.toDoubleOrNull()

                if(gas != null && alc != null){
                    if (porcentage_switch){
                        if(gas * 0.75 >= alc){
                            calc_result = "Alcool vale mais"
                        }
                        else{
                            calc_result = "Gasolina vale mais"
                        }
                    }
                    else{
                        if(gas * 0.70 >= alc){
                            calc_result = "Alcool vale mais"
                        }
                        else{
                            calc_result = "Gasolina vale mais"
                        }
                    }

                }

            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Calcular")
        }
        Text(
            text = calc_result
        )

        Button(
            onClick = {
                val intent = Intent(context, SecondActivity::class.java)
                context.startActivity(intent)
            },
//            modi
            modifier = Modifier.align(Alignment.End)
            .size(60.dp),
            shape = RoundedCornerShape(20
                .dp),
//            tex
        ) {
            Text( text = "+", fontSize = 20.sp, textAlign = TextAlign.Center)

        }
    }
}
