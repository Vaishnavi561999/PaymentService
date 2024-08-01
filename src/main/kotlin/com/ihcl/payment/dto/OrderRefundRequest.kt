package com.ihcl.payment.dto

import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern
import kotlinx.serialization.Serializable

@Serializable
data class OrderRefundRequest(
    val orderId:String,
    val amount: String
)

val validateOrderRefundRequest = Validation{
    OrderRefundRequest::orderId required {
        pattern("\\S+") //should not be empty
    }
    OrderRefundRequest::amount required {
        pattern("\\S+") //should not be empty
    }
}