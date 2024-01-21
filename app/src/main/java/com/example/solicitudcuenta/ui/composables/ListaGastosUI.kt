package com.example.solicitudcuenta.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.solicitudcuenta.data.Solicitud


@Preview(showSystemUi = true)
@Composable
fun PantallaListaGastos(
    gastos: List<Solicitud> = listOf(),
    onAdd:() -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAdd()
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Agregar registro de gasto")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = it.calculateTopPadding())
        ) {
            items(gastos) {
                Text(it.nombre)
            }
        }
    }

}