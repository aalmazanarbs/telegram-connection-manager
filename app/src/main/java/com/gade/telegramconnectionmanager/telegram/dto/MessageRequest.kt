package com.gade.telegramconnectionmanager.telegram.dto

import com.google.gson.annotations.SerializedName

data class MessageRequest(
    @SerializedName("chat_id") val chatId: String,
    val text: String,
    @SerializedName("disable_notification") val disableNotification: Boolean = true
)