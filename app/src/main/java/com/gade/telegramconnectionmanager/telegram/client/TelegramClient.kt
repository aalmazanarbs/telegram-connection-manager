package com.gade.telegramconnectionmanager.telegram.client

import com.gade.telegramconnectionmanager.telegram.dto.MessageRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface TelegramClient {

    @POST("/bot{botApiToken}/sendMessage")
    suspend fun sendMessage(@Path("botApiToken") botApiToken: String, @Body messageRequest: MessageRequest): Response<Void>
}