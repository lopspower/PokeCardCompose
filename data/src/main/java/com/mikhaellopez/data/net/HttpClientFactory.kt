package com.mikhaellopez.data.net

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.mikhaellopez.data.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
open class HttpClientFactory {

    open fun createHttpClient(context: Context): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = BuildConfig.DEBUG
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
            socketTimeoutMillis = requestTimeoutMillis
            connectTimeoutMillis = requestTimeoutMillis
        }

        if (BuildConfig.DEBUG) {
            engine {
                addInterceptor(ChuckerInterceptor.Builder(context).build())
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        if (message.isNotEmpty()) {
                            Log.v("Ktor", message)
                        }
                    }
                }
                level = LogLevel.INFO
            }
        }
    }
}
