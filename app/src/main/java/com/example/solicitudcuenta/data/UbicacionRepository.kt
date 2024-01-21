package com.example.solicitudcuenta.data

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UbicacionRepository(
    val locationService: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    suspend fun getUbicacionFromPlayServices():Location? {
        return withContext(Dispatchers.IO) {
            try {
                val ubicacion = locationService.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null).await()
                ubicacion
            } catch (e:Exception) {
                null
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getUbicacionFromPlayServices(callbackExito: (l:Location)->Unit, callbackError:(e:Exception)->Unit) {
        val tarea = locationService.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
        tarea.addOnSuccessListener {
            callbackExito(it)
        }
        tarea.addOnFailureListener {
            callbackError(it)
        }
    }

}