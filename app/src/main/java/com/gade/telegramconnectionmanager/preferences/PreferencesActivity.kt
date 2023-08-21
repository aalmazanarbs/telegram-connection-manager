package com.gade.telegramconnectionmanager.preferences

import android.app.StatusBarManager
import android.content.ComponentName
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gade.telegramconnectionmanager.R
import com.gade.telegramconnectionmanager.TelegramConnectionManagerTileService
import com.gade.telegramconnectionmanager.ui.screen.PreferencesScreen
import com.gade.telegramconnectionmanager.ui.theme.TelegramConnectionManagerTheme

class PreferencesActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelegramConnectionManagerTheme {
                PreferencesScreen()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestAddTile()
    }

    private fun requestAddTile() {
        getSystemService(StatusBarManager::class.java).requestAddTileService(
            ComponentName(application, TelegramConnectionManagerTileService::class.java),
            resources.getString(R.string.app_name),
            Icon.createWithResource(this, R.drawable.telegram_connection_manager_tile_icon),
            {},
            {}
        )
    }
}
