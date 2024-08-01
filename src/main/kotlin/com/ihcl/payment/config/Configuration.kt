package com.ihcl.payment.config

import com.ihcl.payment.model.ConfigParameters
import io.ktor.server.application.ApplicationEnvironment

object Configuration {
    lateinit var env: ConfigParameters

    fun initConfig(environment: ApplicationEnvironment) {
        env = ConfigParameters(
            port = environment.config.property("ktor.deployment.port").getString(),
            connectionString = environment.config.property("ktor.database.connection-string").getString(),
            databaseName = environment.config.property("ktor.database.database-name").getString(),
            baseUrl = environment.config.property("ktor.api.juspay.base-url").getString(),
            filepath = environment.config.property("ktor.api.juspay.filepath").getString(),
            merchantId = environment.config.property("ktor.api.juspay.merchant-id").getString(),
            client_id = environment.config.property("ktor.api.juspay.client-id").getString(),
            merchantKeyId = environment.config.property("ktor.api.juspay.merchant-key-id").getString(),
            workingKey = environment.config.property("ktor.api.ccavenue.working-key").getString(),
            cartUrl = environment.config.property("ktor.api.ccavenue.cart-url").getString(),
            returnUrl = environment.config.property("ktor.api.ccavenue.return-url").getString(),
            orderStatusUrl = environment.config.property("ktor.api.juspay.order-status-url").getString(),
            webhook_url = environment.config.property("ktor.api.juspay.webhook-url").getString(),
            refundVersion = environment.config.property("ktor.api.juspay.refund-version").getString(),
            refundMerchantId = environment.config.property("ktor.api.juspay.refund-merchantId").getString(),
            confirmationVersion = environment.config.property("ktor.api.juspay.confirmation-version").getString(),
            authorization = environment.config.property("ktor.api.juspay.authorization").getString(),
            processSDKService = environment.config.property("ktor.api.juspay.process-sdk-service").getString(),
            initiateEnvironment = environment.config.property("ktor.api.juspay.initiate-env").getString(),
            requestTimeoutMillis = environment.config.property("ktor.api.juspay.requestTimeoutMillis").getString(),
            connectionPoolMinSize = environment.config.property("ktor.database.connectionPoolMinSize").getString(),
            connectionPoolMaxSize = environment.config.property("ktor.database.connectionPoolMaxSize").getString(),
            gatewayClientId = environment.config.property("ktor.api.juspay.gateway.client_id").getString(),
            gatewayReturnUrl = environment.config.property("ktor.api.ccavenue.gateway.return-url").getString(),
            gatewayWorkingkey = environment.config.property("ktor.api.ccavenue.gateway.working-key").getString()
        )
    }
}