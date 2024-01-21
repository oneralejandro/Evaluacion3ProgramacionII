package com.example.solicitudcuenta.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface  SolicitudDao {
    @Query("SELECT * FROM Solicitud ORDER BY fecha DESC")
    suspend fun obtenerTodos(): List<Solicitud>


    @Query("SELECT * FROM Solicitud WHERE id = :id")
    suspend fun obtenerPorId(id:Long):Solicitud

    @Insert
    suspend fun insertar(solicitud:Solicitud)

    @Update
    suspend fun modificar(solicitud: Solicitud)

    @Delete
    suspend fun eliminar(solicitud: Solicitud)


}