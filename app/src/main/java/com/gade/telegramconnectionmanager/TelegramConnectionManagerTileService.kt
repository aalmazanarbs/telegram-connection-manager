package com.gade.telegramconnectionmanager

import android.content.ComponentName
import android.content.Intent
import android.net.ConnectivityManager.NetworkCallback
import android.os.IBinder
import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_INACTIVE
import android.service.quicksettings.Tile.STATE_UNAVAILABLE
import android.service.quicksettings.TileService
import com.gade.telegramconnectionmanager.network.NetworkStatus
import com.gade.telegramconnectionmanager.network.fromContext
import com.gade.telegramconnectionmanager.network.registerNetworkCallback
import com.gade.telegramconnectionmanager.network.unRegisterNetworkCallback
import com.gade.telegramconnectionmanager.preferences.PreferencesDataStore
import com.gade.telegramconnectionmanager.telegram.TelegramAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TelegramConnectionManagerTileService: TileService() {

    private var networkCallback: NetworkCallback? = null

    override fun onBind(intent: Intent?): IBinder? {
        requestListeningState(this,
            ComponentName(this, TelegramConnectionManagerTileService::class.java)
        )
        return super.onBind(intent)
    }

    override fun onTileAdded() {
        super.onTileAdded()
        setTileState(fromContext(applicationContext))
        startNetworkListener()
    }

    override fun onStartListening() {
        super.onStartListening()
        startNetworkListener()
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        stopNetworkListener()
    }

    override fun onClick() {
        super.onClick()
        if (isLocked) {
            unlockAndRun { doOnClick() }
        } else {
            doOnClick()
        }
    }

    private fun doOnClick() {
        CoroutineScope(IO).launch {
            when(qsTile.state) {
                STATE_INACTIVE -> manageRemoteConnection(true)
                STATE_ACTIVE -> manageRemoteConnection(false)
            }
        }
    }

    private fun startNetworkListener() {
        if (networkCallback == null) {
            networkCallback = registerNetworkCallback(applicationContext, ::setTileState)
        }
    }

    private fun stopNetworkListener() {
        if (networkCallback != null) {
            unRegisterNetworkCallback(applicationContext, networkCallback!!)
            networkCallback = null
        }
    }

    private suspend fun manageRemoteConnection(start: Boolean) {
        PreferencesDataStore(applicationContext).preferences.collect {
            val command = if (start) it.startSharedConnectionCommand else
                                     it.stopSharedConnectionCommand
            val messageSent = TelegramAdapter(it.telegramBotApiToken).sendMessage(it.telegramChatId, command)
            if (messageSent) {
                notifyUser(start)
            } else {
                setTileInformation(R.string.telegram_connection_manager_tile_error)
            }
        }
    }

    private fun notifyUser(start: Boolean) {
        if (start) {
            setTileInformation(R.string.telegram_connection_manager_start_success)
        } else {
            setTileInformation(R.string.telegram_connection_manager_stop_success)
        }
    }

    private fun setTileState(networkStatus: NetworkStatus?) {
        when {
            networkStatus == null -> qsTile.state = STATE_UNAVAILABLE
            networkStatus.data -> qsTile.state = STATE_INACTIVE
            networkStatus.wifi -> qsTile.state = STATE_ACTIVE
        }
        qsTile.subtitle = ""
        qsTile.updateTile()
    }

    private fun setTileInformation(id: Int) {
        qsTile.subtitle = resources.getString(id)
        qsTile.updateTile()
    }
}
