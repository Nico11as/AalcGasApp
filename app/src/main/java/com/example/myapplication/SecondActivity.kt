package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.json.JSONObject

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {


                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    gasStationTable()
                }
            }
        }
    }
}

    @Composable
    fun gasStationTable(){
        val context = LocalContext.current

        Column( modifier = Modifier.fillMaxSize()
            .padding(18.dp, 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Text(text = "Postos de gasolina")

            Button(
                onClick = {
                    saveGasStation(context, gasStation("oi", 1.0, 1.0))
                    Log.d("Coisa",getGasStationJSON(context).toString())
                }
            ) {
                Text(text = "Novo Posto")
            }
        }

    }

fun saveGasStation(context: Context, gas_station: gasStation){
    val sharedFileName = "lastGasStationJSON"
    var shared_pref: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    var editor = shared_pref.edit()

    val jsonObject = gasStationToJson(gas_station)

    editor.putString("gasJSON",jsonObject.toString())
    editor.apply()
}

fun getGasStationJSON(context: Context): gasStation? {
    val prefs = context.getSharedPreferences(
        "lastGasStationJSON",
        Context.MODE_PRIVATE
    )

    val jsonString = prefs.getString("gasJSON", null)
        ?: return null

    if (jsonString.isBlank()) return null

    return try {
        jsonToGasStation(JSONObject(jsonString))
    } catch (e: Exception) {
        Log.e("GasStation", "Failed to load JSON", e)
        null
    }
}


fun gasStationToJson(gas_station: gasStation) : JSONObject {
    return JSONObject().apply {
        put("name", gas_station.name)
        put("lat", gas_station.lat)
        put("lgt", gas_station.lgt)
    }
}

fun jsonToGasStation(json: JSONObject?): gasStation {
    //Caso o json seja inválido existe um valor default (ternário a seguir)
    val name = json?.optString("name", "") ?: ""
    val lat = json?.optDouble("lat", 0.0) ?: 0.0
    val lgt = json?.optDouble("lgt", 0.0) ?: 0.0
    return gasStation(name, lat, lgt)
}

class gasStation(val name: String, val lat: Double, val lgt: Double){
}