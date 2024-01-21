package com.example.solicitudcuenta

import android.app.Application
import androidx.room.Room
import com.example.solicitudcuenta.data.BaseDatos
import com.example.solicitudcuenta.data.UbicacionRepository
import com.google.android.gms.location.LocationServices

class Aplicacion : Application(){

    val db by lazy{ Room.databaseBuilder(this,BaseDatos::class.java,"solicitud.db").build()}
    val solicitudDao by lazy { db.solicitudDao() }
    val locationService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    val ubicacionRepository by lazy { UbicacionRepository(locationService) }



}