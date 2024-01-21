package com.example.solicitudcuenta.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity
data class Solicitud (
    @PrimaryKey(autoGenerate = true) var id:Long? = null,

    var telefono: Long,
    var nombre: String,
    var rut: String,
    var fecha: LocalDate,
    var correo: String,


)

