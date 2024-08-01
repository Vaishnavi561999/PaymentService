package com.ihcl.payment.dto.processsdk

import kotlinx.serialization.Serializable

@Serializable
data class ProcessSdkResponse (
    val payload:Payload,
    val requestId:String,
    val service:String
)