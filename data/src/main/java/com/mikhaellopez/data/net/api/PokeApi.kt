package com.mikhaellopez.data.net.api

import com.mikhaellopez.data.net.dto.CardDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.annotation.Single

@Single
class PokeApi(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://lopspower.github.io/poke"
    }

    suspend fun getCardList(): List<CardDTO> =
        client.get("$BASE_URL/poke.json").body()
}
