package com.gade.telegramconnectionmanager.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gade.telegramconnectionmanager.preferences.PreferencesDataStore.Companion.dataStore
import com.gade.telegramconnectionmanager.R
import com.gade.telegramconnectionmanager.preferences.PreferencesDataStore.Companion.START_SHARED_CONNECTION_COMMAND_KEY
import com.gade.telegramconnectionmanager.preferences.PreferencesDataStore.Companion.STOP_SHARED_CONNECTION_COMMAND_KEY
import com.gade.telegramconnectionmanager.preferences.PreferencesDataStore.Companion.TELEGRAM_BOT_API_TOKEN_KEY
import com.gade.telegramconnectionmanager.preferences.PreferencesDataStore.Companion.TELEGRAM_CHAT_ID_KEY
import com.jamal.composeprefs3.ui.PrefsScreen
import com.jamal.composeprefs3.ui.prefs.EditTextPref

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PreferencesScreen() {

    val context = LocalContext.current

    Scaffold(topBar = { PreferencesTopBar() }) { padding ->

        PrefsScreen(dataStore = context.dataStore, modifier = Modifier.padding(padding)) {

            prefsGroup(context.resources.getString(R.string.preferences_telegram)) {

                prefsItem {
                    EditTextPref(
                        key = TELEGRAM_BOT_API_TOKEN_KEY,
                        title = stringResource(R.string.preferences_telegram_bot_api_token_title),
                        summary = stringResource(R.string.preferences_telegram_bot_api_token_summary),
                        dialogTitle = stringResource(R.string.preferences_telegram_bot_api_token_title)
                    )
                }

                prefsItem {
                    EditTextPref(
                        key = TELEGRAM_CHAT_ID_KEY,
                        title = stringResource(R.string.preferences_telegram_chat_id_title),
                        summary = stringResource(R.string.preferences_telegram_chat_id_summary),
                        dialogTitle = stringResource(R.string.preferences_telegram_chat_id_title)
                    )
                }

                prefsItem {
                    EditTextPref(
                        key = START_SHARED_CONNECTION_COMMAND_KEY,
                        title = stringResource(R.string.preferences_telegram_commands_start_title),
                        summary = stringResource(R.string.preferences_telegram_commands_start_summary),
                        dialogTitle = stringResource(R.string.preferences_telegram_commands_start_title)
                    )
                }

                prefsItem {
                    EditTextPref(
                        key = STOP_SHARED_CONNECTION_COMMAND_KEY,
                        title = stringResource(R.string.preferences_telegram_commands_stop_title),
                        summary = stringResource(R.string.preferences_telegram_commands_stop_summary),
                        dialogTitle = stringResource(R.string.preferences_telegram_commands_stop_title)
                    )
                }
            }
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.preferences),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    )
}