package com.mikhaellopez.data.net.api

import com.mikhaellopez.data.net.dto.CardDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.koin.core.annotation.Single

@Single
class PokeApi(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://mikhaellopez.com/github"
    }

    suspend fun getCardList(): List<CardDTO> =
        client.get("$BASE_URL/poke.json").body()
}
