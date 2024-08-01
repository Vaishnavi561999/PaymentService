package com.ihcl.payment.plugins

import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.defaultheaders.*

fun Application.corsConfig() {
    install(DefaultHeaders){
        header("Content-Security-Policy", "default-src 'self'; script-src * 'unsafe-inline' 'self'; style-src 'unsafe-inline' 'self' https://unpkg.com; object-src 'self'; connect-src 'self' https://unpkg.com *.tajhotels.com; font-src 'self' https://unpkg.com; frame-src 'self'; img-src 'self' data: http://w3.org/svg/2000; manifest-src 'self' app.link; media-src 'self'; worker-src 'self';")
        header("X-Content-Type-Options", "nosniff")
        header("X-XSS-Protection", "1")
    }
    install(CORS){
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }
}