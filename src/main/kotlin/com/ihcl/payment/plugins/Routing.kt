package com.ihcl.payment.plugins

import com.ihcl.payment.route.ccavenue
import com.ihcl.payment.route.juspay
import io.ktor.server.application.Application

fun Application.configureRouting() {
    juspay()
    ccavenue()
}
