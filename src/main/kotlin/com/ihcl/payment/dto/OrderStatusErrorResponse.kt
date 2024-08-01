package com.ihcl.payment.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OrderStatusErrorResponse(
    @SerializedName("error_code")
    val errorCode: String?,
    @SerializedName("error_message")
    val errorMessage: String?,
    @SerializedName("order_id")
    val orderId: String?,
    val status: String?,
    @SerializedName("status_id")
    val statusId: Int?
)