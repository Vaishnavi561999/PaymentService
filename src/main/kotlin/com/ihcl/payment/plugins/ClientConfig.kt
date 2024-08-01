package com.ihcl.payment.plugins

import com.ihcl.payment.config.Configuration
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.gson.gson
import okhttp3.ConnectionPool
import okhttp3.Protocol
import java.util.concurrent.TimeUnit


object ConfigureClient {
    private val prop = Configuration.env
    val client = HttpClient(OkHttp) {
        engine {
            config {
                // this: OkHttpClient.Builder
                connectionPool(ConnectionPool(100, 5, TimeUnit.MINUTES))
                readTimeout(prop.requestTimeoutMillis.toLong(), TimeUnit.MILLISECONDS)
                connectTimeout(prop.requestTimeoutMillis.toLong(), TimeUnit.MILLISECONDS)
                writeTimeout(prop.requestTimeoutMillis.toLong(), TimeUnit.MILLISECONDS)
                retryOnConnectionFailure(true)
                protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
                followRedirects(true)
            }
        }
        install(ContentNegotiation) {
            gson()
        }
        install(Logging) {
            level = LogLevel.INFO
        }
        install(HttpTimeout)
    }
}