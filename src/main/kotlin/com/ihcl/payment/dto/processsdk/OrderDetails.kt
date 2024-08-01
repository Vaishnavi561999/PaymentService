package com.ihcl.payment.dto.processsdk

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
@Serializable
data class OrderDetails (
    @SerializedName("order_id")
    val orderId:String?,
    @SerializedName("merchant_id")
    val merchantId:String?,
    val amount:String?,
    val timestamp:String?,
    @SerializedName("customer_id")
    val customerId:String?,
    @SerializedName("customer_email")
    val customerEmail:String?,
    @SerializedName("customer_phone")
    val customerPhone:String?,
    @SerializedName("return_url")
    val returnUrl:String?,
    @SerializedName("product_id")
    val productId:String?,
    @SerializedName("billing_address_first_name")
    val billingAddressFirstName:String?,
    @SerializedName("billing_address_last_name")
    val billingAddressLastName:String?,
    @SerializedName("billing_address_line1")
    val billingAddressLine1:String?,
    @SerializedName("billing_address_line2")
    val billingAddressLine2:String?,
    @SerializedName("billing_address_line3")
    val billingAddressLine3:String?,
    @SerializedName("billing_address_city")
    val billingAddressCity:String?,
    @SerializedName("billing_address_state")
    val billingAddressState:String?,
    @SerializedName("billing_address_postal_code")
    val billingAddressPostalCode:String?,
    @SerializedName("billing_address_country")
    val billingAddressCountry:String?,
    @SerializedName("billing_address_phone")
    val billingAddressPhone:String?,
    @SerializedName("billing_address_country_code_iso")
    val billingAddressCountryCodeIso:String?,
    val udf1:String?,
    val udf2:String?,
    val udf3:String?,
    val udf4:String?,
    val udf5:String?,
    val udf6:String?,
    val udf7:String?,
    val udf8:String?,
    val udf9:String?,
    val udf10:String?,
    @SerializedName("offer_details")
    val offerDetails:String?,
    @SerializedName("metadata.JUSPAY:gateway_reference_id")
    val metadataJuspayGatewayReferenceId:String?,
    @SerializedName("entry_point")
    val entryPoint:String?,
    val integration:String?,
    val loyalCustomer:String?,
    @SerializedName("metadata.CCAVENUE_V2:merchant_param1")
    val metadataCcavenueV2MerchantParam1:String?,
    @SerializedName("metadata.CCAVENUE_V2:merchant_param2")
    val metadataCcavenueV2MerchantParam2:String?,
    @SerializedName("metadata.CCAVENUE_V2:merchant_param3")
    val metadataCcavenueV2MerchantParam3:String?,
    @SerializedName("metadata.CCAVENUE_V2:merchant_param4")
    val metadataCcavenueV2MerchantParam4:String?,
    @SerializedName("metadata.CCAVENUE_V2:merchant_param5")
    val metadataCcavenueV2MerchantParam5:String?,
    @SerializedName("metadata.CCAVENUE_V2:sub_account_id")
    val metadataCcavenueV2SubAccountId:String?,
    @SerializedName("metadata.webhook_url")
    val metadataWebhookUrl:String?
//    @SerializedName("metadata.CCAVENUE_V2:gateway_reference_id")
//    val metadata_CCAVENUE_V2_gateway_reference_id:String?,
)