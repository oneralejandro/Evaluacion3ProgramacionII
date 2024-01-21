package com.example.solicitudcuenta.ui

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.solicitudcuenta.Aplicacion
import com.example.solicitudcuenta.data.Solicitud
import com.example.solicitudcuenta.data.SolicitudDao
import com.example.solicitudcuenta.data.UbicacionRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaSolicitudesViewModel(
    private val solicitudDao:SolicitudDao,
    private val ubicacionRepository: UbicacionRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val aplicaion = (this[APPLICATION_KEY] as Aplicacion)
                ListaSolicitudesViewModel(aplicaion.solicitudDao, aplicaion.ubicacionRepository)
            }
        }
    }

    var solicitud by mutableStateOf(listOf<Solicitud>())
    var ubicacion by mutableStateOf<Location?>(null)

    fun refrescarUbicacion() {
        viewModelScope.launch(Dispatchers.IO) {
            ubicacion = ubicacionRepository.getUbicacionFromPlayServices()
        }
    }

    fun insertarSolicitud(solicitud: Solicitud) {
        viewModelScope.launch(Dispatchers.IO) {
            solicitudDao.insertar(solicitud)
            obtenerSolicitud()
        }
    }

    fun obtenerSolicitud(): List<Solicitud> {
        viewModelScope.launch(Dispatchers.IO) {
            solicitud = solicitudDao.obtenerTodos()
        }
        return solicitud
    }
}