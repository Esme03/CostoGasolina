package com.mexiti.costogasolina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mexiti.costogasolina.ui.theme.CostoGasolinaTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostoGasolinaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                    .size(50.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CostGasLayout("Android")
                }
            }
        }
    }
}



@Composable
fun CostGasLayout(name: String) {
    var precioEntradalitro by remember {
       mutableStateOf("0.0")
    }
    var cantEntradalitro  by remember {
        mutableStateOf("0.0")
    }
    var propinaEntrada by remember {
        mutableStateOf("0.0")
    }
    var agregarPropina by remember {
        mutableStateOf(false)
    }
    val precioLitro = precioEntradalitro.toDoubleOrNull()?: 0.0
    val cantLitros = cantEntradalitro.toDoubleOrNull()?:0.0
    val propina = propinaEntrada.toDoubleOrNull()?: 0.0
    val total = CalcularMonto(precioLitro, cantLitros, agregarPropina= agregarPropina, propina= propina )


    Column (
        modifier = Modifier. fillMaxSize()
            .padding(30.dp)
        .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    )
    {

        Text(
            text = stringResource(R.string.calcular_monto),
            fontWeight = FontWeight.ExtraBold,


            )
        EditNumberField(
            label = R.string.ingresa_gasolina,
            leadingIcon = R.drawable.money_gas ,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = precioEntradalitro ,
            onValueChanged = {precioEntradalitro=it }
       )
        EditNumberField(
            label = R.string.litros,
            leadingIcon =R.drawable.gasolina,
            keyboardsOptions =KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ) ,
            value = cantEntradalitro,
            onValueChanged = { cantEntradalitro= it


            }
        )
        EditNumberField(
            label = R.string.string_propina,
            leadingIcon =R.drawable.propina ,
            keyboardsOptions =KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ) ,
            value = propinaEntrada,
            onValueChanged = {
                propinaEntrada = it
            }
        )

        Switch(
            checked = agregarPropina,
            onCheckedChange = {agregarPropina= it},
        )
        Text(
            text = stringResource(id = R.string.monto_total, total),
           fontWeight = FontWeight.ExtraBold,

        )


    }

}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardsOptions:KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        label = { Text(text = stringResource(id = label))  },
        value = value,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon) , contentDescription = null) },
        keyboardOptions = keyboardsOptions,
        modifier = modifier,
        onValueChange = onValueChanged
    )

}
private fun CalcularMonto(precio: Double, cantLitros: Double, propina: Double, agregarPropina: Boolean ):String{
    var total = precio * cantLitros
    if ( agregarPropina){
        total +=  propina
    }
    return NumberFormat.getCurrencyInstance().format(total)

}
@Preview(showBackground = true)
@Composable
fun CostGasLayoutPreview() {
    CostoGasolinaTheme {
        CostGasLayout("Android")
    }

}