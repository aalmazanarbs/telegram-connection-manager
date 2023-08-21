package com.gade.telegramconnectionmanager.telegram

import com.gade.telegramconnectionmanager.telegram.client.TelegramClient
import com.gade.telegramconnectionmanager.telegram.dto.MessageRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TelegramAdapter(private val botApiToken: String) {

    private var client: TelegramClient = Retrofit.Builder()
        .baseUrl("https://api.telegram.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TelegramClient::class.java)

    suspend fun sendMessage(chatId: String, text: String): Boolean {
        val response = client.sendMessage(botApiToken, MessageRequest(chatId, text))
        return response.isSuccessful
    }
}