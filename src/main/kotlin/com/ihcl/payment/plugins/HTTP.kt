package com.ihcl.payment.plugins

import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.application.Application

fun Application.configureHTTP() {
    routing {
        swaggerUI(path = "/v1/openapi")
    }
    routing {
        openAPI(path = "v1/document")
    }
}
