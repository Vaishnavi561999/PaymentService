package com.ihcl.payment.dto.processsdk

import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern
import kotlinx.serialization.Serializable

@Serializable
data class ProcessSdkRequest(
    val orderId: String,
    val returnUrl:String,
    val isLogin: Boolean
)

val validateProcessSdkRequest = Validation{
    ProcessSdkRequest::orderId required {
        pattern("\\S+") //should not be empty
    }
    ProcessSdkRequest::returnUrl required {
        pattern("\\S+") //should not be empty
    }
}