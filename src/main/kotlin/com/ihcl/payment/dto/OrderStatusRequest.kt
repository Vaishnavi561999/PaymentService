package com.ihcl.payment.dto

import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern
import kotlinx.serialization.Serializable

@Serializable
data class OrderStatusRequest(
    val orderId: String?
)

val validateOrderStatusRequest = Validation{
    OrderStatusRequest::orderId required {
        pattern("\\S+") //should not be empty
    }
}