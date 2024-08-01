package com.ihcl.payment.model

data class ConfigParameters(
    val port: String,
    val connectionString: String,
    val databaseName: String,
    val baseUrl: String,
    val filepath: String,
    val merchantId: String,
    val client_id: String,
    val merchantKeyId: String,
    val workingKey:String,
    val cartUrl:String,
    val returnUrl:String,
    val orderStatusUrl:String,
    val webhook_url:String,
    val refundVersion:String,
    val refundMerchantId:String,
    val confirmationVersion:String,
    val authorization:String,
    val processSDKService:String,
    val initiateEnvironment:String,
    val requestTimeoutMillis:String,
    val connectionPoolMinSize: String,
    val connectionPoolMaxSize: String,
    val gatewayClientId:String,
    val gatewayReturnUrl:String,
    val gatewayWorkingkey:String
)