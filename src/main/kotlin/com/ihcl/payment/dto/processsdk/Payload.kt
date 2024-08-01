package com.ihcl.payment.dto.processsdk

import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val action:String,
    val amount:String,
    val clientId:String,
    val customerEmail:String,
    val customerId:String,
    val customerMobile:String,
    val language:String,
    val merchantId:String,
    val merchantKeyId:String,
    val orderDetails: String,
    val orderId:String,
    val signature:String
)