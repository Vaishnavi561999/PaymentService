package com.ihcl.payment.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.gson.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.routing.get


fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson{
            serializeNulls()
        }
        json()
    }

    routing {
        get("/json/kotlinx-serialization") {
                call.respond(mapOf("hello" to "world"))
            }
    }
}
