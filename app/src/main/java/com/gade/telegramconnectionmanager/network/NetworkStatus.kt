package com.gade.telegramconnectionmanager.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI

data class NetworkStatus(val wifi: Boolean, val data: Boolean)

fun fromContext(context: Context):  NetworkStatus? {
    val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    return fromNetworkCapabilities(connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork))
}

fun fromNetworkCapabilities(networkCapabilities: NetworkCapabilities?): NetworkStatus? {
    return if (isConnected(networkCapabilities))
                NetworkStatus(
                    networkCapabilities!!.hasTransport(TRANSPORT_WIFI),
                    networkCapabilities.hasTransport(TRANSPORT_CELLULAR)
                )
           else null
}

private fun isConnected(networkCapabilities: NetworkCapabilities?): Boolean {
    return networkCapabilities != null &&
           networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET) &&
           networkCapabilities.hasCapability(NET_CAPABILITY_VALIDATED)
}