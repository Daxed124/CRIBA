package com.app.criba.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class LocationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    /**
     * Verifica el permiso REAL del sistema en el momento (no un estado cacheado).
     * Si el permiso fue "solo esta vez" y la app se reinició, aquí ya saldrá false,
     * lo que obliga a volver a solicitarlo. Con "mientras se usa la app" o
     * "permitir siempre", seguirá siendo true.
     */
    fun hasPermission(): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Pair<Double, Double>? {
        if (!hasPermission()) return null
        return try {
            val location: Location? = withTimeoutOrNull(5000) {
                fusedClient.getCurrentLocation(com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, null).await()
            }
            location?.let { Pair(it.latitude, it.longitude) }
        } catch (e: Exception) {
            null
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Pair<Double, Double>? {
        if (!hasPermission()) return null
        return try {
            val location: Location? = fusedClient.lastLocation.await()
            location?.let { Pair(it.latitude, it.longitude) }
        } catch (e: Exception) {
            null
        }
    }
}
