package com.gade.telegramconnectionmanager.preferences

data class Preferences(
    val telegramBotApiToken: String,
    val telegramChatId: String,
    val startSharedConnectionCommand: String,
    val stopSharedConnectionCommand: String
)