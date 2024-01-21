package com.example.solicitudcuenta

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solicitudcuenta.ui.ListaSolicitudesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import com.example.solicitudcuenta.data.Solicitud
import com.example.solicitudcuenta.ui.UbicacioRepository
import com.google.android.gms.location.LocationServices


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppSolicitudesUI()


        }
    }
}



@Composable
fun AppSolicitudesUI(
    navController: NavHostController = rememberNavController(),
    vmListaSolicitudes: ListaSolicitudesViewModel = viewModel(factory = ListaSolicitudesViewModel.Factory)
) {
   NavHost(navController = navController, startDestination = "inicio" )
   {
       composable("inicio"){
           PantallaListaSolicitudes(
               solicitud = vmListaSolicitudes.solicitud,
               onAdd = {navController.navigate("form")}
               )
       }
       composable("form"){
        PantallaFormSolicitudes(  )
           AppUbicacionUI()

           
       }


    }


}






@Preview(showSystemUi = true, showBackground = false)
@Composable
fun PantallaFormSolicitudes(
    onSaveSolicitud: (solicitud:Solicitud) -> Unit ={},
    vmListaSolicitudes: ListaSolicitudesViewModel = viewModel(factory = ListaSolicitudesViewModel.Factory)


){
    val contexto = LocalContext.current
    var telefono by rememberSaveable { mutableStateOf(0) }
    var nombre by rememberSaveable { mutableStateOf("") }
    var rut by rememberSaveable { mutableStateOf("") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }

    val lanzadorPermisosUbicacion =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = {
                if ( it.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
                    ||
                    it.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
                ) {
                    // permisos ok, recuperar ubicación
                    vmListaSolicitudes.refrescarUbicacion()
                } else {
                    // mostrar mensaje error,
                    // explicación permisos requeridos
                }
            }
        )




    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ){
        TextField(
            value = telefono.toString() ,
            onValueChange = {telefono = it.toIntOrNull() ?:0},
            label = { Text("Telefono")}
        )
        TextField(
            value = nombre.toString() ,
            onValueChange = {nombre = it} ,
            placeholder = { Text("Ingrese su nombre")},
                    label = { Text("Nombre")}
        )
        TextField(
            value = rut.toString() ,
            onValueChange = {rut = it} ,
            placeholder = { Text("Ingrese su rut")},
            label = {Text("Rut")}
        )
        TextField(
            value = fecha.toString() ,
            onValueChange = {fecha = it},
            placeholder = { Text("2024-12-25")},
            label = {Text("FECHA")}
            )
        TextField(value = correo.toString() ,
            onValueChange = {correo = it},
            placeholder = { Text("Ingrese su correo electronico")},
            label = { Text("Correo")}

        )
        Button(onClick = {
            lanzadorPermisosUbicacion.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            )
        }) {
            Text("Ubicación")
        }
        vmListaSolicitudes.ubicacion?.let {
            Text("Lat: ${it.latitude} | Long: ${it.longitude}")
        }

        Button(onClick = {

        }) {
            Text("Guardar registro de gasto")
        }
    }





}


@Composable
fun PantallaListaSolicitudes(
    solicitud: List<Solicitud> = listOf(),
    onAdd:() -> Unit = {}
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
            onAdd()
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription =  "Agregar solicitud ")
                
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = it.calculateTopPadding())
        ){
            items(solicitud){
                Text(it.nombre)
            }
    }


    }
}
@Preview(showSystemUi = true)
@Composable
fun AppUbicacionUI(){
    val contexto = LocalContext.current
    var mensaje by rememberSaveable { mutableStateOf("Ubicacion:")}
    val lanzadorPermisos = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions() ,
        onResult = {
            if(
                it.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION,false) ||
                it.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION,false)
            ){
                val locationService = LocationServices.getFusedLocationProviderClient(contexto)
                val repository = UbicacioRepository(locationService)
                repository.conseguirUbicacion(
                    onExito = {
                        mensaje = "lat: ${it.latitude} | Long : ${it.longitude}"
                    },
                    onError = {
                        mensaje = "no se pudo conseguir la ubicacion "
                        Log.e("appUbicacionUi::conseguiUbicacion", it.message ?: "" )
                    }
                )
            }else{
                mensaje = "debe otorgar permisos a la plaicacs"
            }
        }
    )

    



}


