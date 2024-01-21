package com.example.solicitudcuenta.ui

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class UbicacioRepository(
    val fusedLocationProviderClient: FusedLocationProviderClient
) {

        @SuppressLint("MissingPermission")
        fun conseguirUbicacion(
            onExito:(u: Location) -> Unit,
            onError:(e:Exception) -> Unit
        ){
            val tarea = fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                null
            )
            tarea.addOnSuccessListener { onExito(it) }
            tarea.addOnFailureListener{ onError(it) }
        }

}