package com.ihcl.payment.dto.initiatesdk

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Signaturepayload(
    @SerializedName("merchant_id")
    val merchantId:String,
    @SerializedName("customer_id")
    val customerId:String,
    @SerializedName("mobile_number")
    val mobileNumber:String,
    @SerializedName("email_address")
    val emailAddress:String,
    val timestamp:String
    )