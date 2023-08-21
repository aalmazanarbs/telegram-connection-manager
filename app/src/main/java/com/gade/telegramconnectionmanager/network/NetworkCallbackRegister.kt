package com.gade.telegramconnectionmanager.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities

fun registerNetworkCallback(context: Context, onCapabilitiesChanged: (networkCapabilities: NetworkStatus?) -> Unit): NetworkCallback {
    val networkCallback = object : NetworkCallback() {
        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            onCapabilitiesChanged(fromNetworkCapabilities(networkCapabilities))
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            onCapabilitiesChanged(null)
        }
    }

    val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    connectivityManager.registerDefaultNetworkCallback(networkCallback)
    return networkCallback
}

fun unRegisterNetworkCallback(context: Context, networkCallback: NetworkCallback) {
    val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    connectivityManager.unregisterNetworkCallback(networkCallback)
}
