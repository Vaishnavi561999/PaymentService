package com.ihcl.payment

import com.ihcl.payment.config.Configuration
import com.ihcl.payment.config.MongoConfig
import com.ihcl.payment.plugins.configureSerialization
import com.ihcl.payment.plugins.configureHTTP
import com.ihcl.payment.plugins.configureDependencyInjection
import com.ihcl.payment.plugins.configureRouting
import com.ihcl.payment.plugins.ConfigureClient
import com.ihcl.payment.plugins.corsConfig
import com.ihcl.payment.plugins.statusPages
import io.ktor.server.application.Application
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    Configuration.initConfig(this.environment)
    configureSerialization()
    configureHTTP()
    configureDependencyInjection()
    configureRouting()
    corsConfig()
    ConfigureClient.client
    MongoConfig.getDatabase()
    statusPages()
}
