package com.gade.telegramconnectionmanager.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataStore(context: Context) {

    companion object {
        val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore("preferences")

        const val TELEGRAM_BOT_API_TOKEN_KEY = "telegram_bot_api_token_key"
        const val TELEGRAM_CHAT_ID_KEY = "telegram_chat_id_key"
        const val START_SHARED_CONNECTION_COMMAND_KEY = "start_shared_connection_command_key"
        const val STOP_SHARED_CONNECTION_COMMAND_KEY = "stop_shared_connection_command_key"

        private val TELEGRAM_BOT_API_TOKEN = stringPreferencesKey(TELEGRAM_BOT_API_TOKEN_KEY)
        private val TELEGRAM_CHAT_ID = stringPreferencesKey(TELEGRAM_CHAT_ID_KEY)
        private val START_SHARED_CONNECTION_COMMAND = stringPreferencesKey(START_SHARED_CONNECTION_COMMAND_KEY)
        private val STOP_SHARED_CONNECTION_COMMAND = stringPreferencesKey(STOP_SHARED_CONNECTION_COMMAND_KEY)
    }

    val preferences: Flow<Preferences> =
        context.dataStore.data.map {
            Preferences(
                it[TELEGRAM_BOT_API_TOKEN].orEmpty(),
                it[TELEGRAM_CHAT_ID].orEmpty(),
                it[START_SHARED_CONNECTION_COMMAND].orEmpty(),
                it[STOP_SHARED_CONNECTION_COMMAND].orEmpty()
            )
        }
}