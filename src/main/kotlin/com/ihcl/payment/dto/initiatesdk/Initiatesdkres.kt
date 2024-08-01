package com.ihcl.payment.dto.initiatesdk

import kotlinx.serialization.Serializable

@Serializable
data class Initiatesdkres(
    val requestId:String,
    val service:String,
    val betaAssets:String,
    val payload: Payload
)
@Serializable
data class Payload(
    val action: String,
    val merchantKeyId: String,
    val integrationType: String,
    val signaturePayload: String,
    val signature: String,
    val environment: String,
    val clientId:String,
    val hyperSDKDiv:String,
    val merchantId:String
)