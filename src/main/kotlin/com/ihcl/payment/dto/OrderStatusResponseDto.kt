package com.ihcl.payment.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OrderStatusResponseDto(
    val amount: Double? = null,
    @SerializedName("amount_refunded")
    val amountRefunded: Double? = null,
    @SerializedName("auth_type")
    val authType: String? = null,
    @SerializedName("bank_error_code")
    val bankErrorCode: String? = null,
    @SerializedName("bank_error_message")
    val bankErrorMessage: String? = null,
    val card: Card? = null,
    val currency: String? = null,
    @SerializedName("customer_email")
    val customerEmail: String? = null,
    @SerializedName("customer_id")
    val customerId: String? = null,
    @SerializedName("customer_phone")
    val customerPhone: String? = null,
    @SerializedName("date_created")
    val dateCreated: String? = null,
    @SerializedName("effective_amount")
    val effectiveAmount: Double? = null,
    @SerializedName("gateway_id")
    val gatewayId: Int? = null,
    @SerializedName("gateway_reference_id")
    val gatewayReferenceId: String? = null,
    val id: String? = null,
    @SerializedName("maximum_eligible_refund_amount")
    val maximumEligibleRefundAmount: Double? = null,
    @SerializedName("merchant_id")
    val merchantId: String? = null,
    val metadata: Metadata? = null,
    val offers: List<String>? = null,
    @SerializedName("order_id")
    val orderId: String? = null,
    @SerializedName("payment_gateway_response")
    val paymentGatewayResponse: PaymentGatewayResponse? = null,
    @SerializedName("payment_links")
    val paymentLinks: PaymentLinks? = null,
    @SerializedName("payment_method")
    val paymentMethod: String? = null,
    @SerializedName("payment_method_type")
    val paymentMethodType: String? = null,
    @SerializedName("product_id")
    val productId: String? = null,
    val refunded: Boolean? = null,
    @SerializedName("resp_code")
    val respCode: String? = null,
    @SerializedName("resp_message")
    val respMessage: String? = null,
    @SerializedName("return_url")
    val returnUrl: String? = null,
    val status: String? = null,
    @SerializedName("status_id")
    val statusId: Int? = null,
    @SerializedName("txn_detail")
    val txnDetail: TxnDetail? = null,
    @SerializedName("txn_id")
    val txnId: String? = null,
    @SerializedName("txn_uuid")
    val txnUuid: String? = null,
    val udf1: String? = null,
    val udf10: String? = null,
    val udf2: String? = null,
    val udf3: String? = null,
    val udf4: String? = null,
    val udf5: String? = null,
    val udf6: String? = null,
    val udf7: String? = null,
    val udf8: String? = null,
    val udf9: String? = null
)
@Serializable
data class Metadata(
    @SerializedName("CCAVENUE_V2:merchant_param1")
    val ccavenueV2MerchantParam1: String? = null,
    @SerializedName("CCAVENUE_V2:merchant_param2")
    val ccavenueV2MerchantParam2: String? = null,
    @SerializedName("CCAVENUE_V2:merchant_param3")
    val ccavenueV2MerchantParam3: String? = null,
    @SerializedName("CCAVENUE_V2:merchant_param4")
    val ccavenueV2MerchantParam4: String? = null,
    @SerializedName("CCAVENUE_V2:merchant_param5")
    val ccavenueV2MerchantParam5: String? = null,
    @SerializedName("CCAVENUE_V2:sub_account_id")
    val ccavenueV2SubAccountId: String? = null,
    @SerializedName("JUSPAY:gateway_reference_id")
    val juspayGatewayReferenceId: String? = null,
    @SerializedName("payment_links")
    val paymentLinks: PaymentLinks? = null,
    @SerializedName("payment_page_client_id")
    val paymentPageClientId: String? = null
)
@Serializable
data class PaymentGatewayResponse(
    @SerializedName("auth_id_code")
    val authIdCode: String? = null,
    val created: String? = null,
    @SerializedName("epg_txn_id")
    val epgTxnId: String? = null,
    @SerializedName("gateway_response")
    val gatewayResponse: GatewayResponse? = null,
    @SerializedName("resp_code")
    val respCode: String? = null,
    @SerializedName("resp_message")
    val respMessage: String? = null,
    val rrn: String? = null,
    @SerializedName("txn_id")
    val txnId: String? = null
)
@Serializable
data class PaymentLinks(
    val iframe: String? = null,
    val mobile: String? = null,
    val web: String? = null
)
@Serializable
data class TxnDetail(
    val created: String? = null,
    val currency: String? = null,
    @SerializedName("error_code")
    val errorCode: String? = null,
    @SerializedName("error_message")
    val errorMessage: String? = null,
    @SerializedName("express_checkout")
    val expressCheckout: Boolean? = null,
    val gateway: String? = null,
    @SerializedName("gateway_id")
    val gatewayId: Int? = null,
    @SerializedName("net_amount")
    val netAmount: Double? = null,
    @SerializedName("offer_deduction_amount")
    val offerDeductionAmount: Double? = null,
    @SerializedName("order_id")
    val orderId: String? = null,
    val redirect: Boolean? = null,
    val status: String? = null,
    @SerializedName("surcharge_amount")
    val surchargeAmount: Double? = null,
    @SerializedName("tax_amount")
    val taxAmount: Double? = null,
    @SerializedName("txn_amount")
    val txnAmount: Double? = null,
    @SerializedName("txn_flow_type")
    val txnFlowType: String? = null,
    @SerializedName("txn_id")
    val txnId: String? = null,
    @SerializedName("txn_uuid")
    val txnUuid: String? = null,
    val metadata: TxnDetailMetaData? = null
)
@Serializable
data class TxnDetailMetaData(
    val paymentChannel:String? = null
)
@Serializable
data class GatewayResponse(
    val amount: Double? = null,
    @SerializedName("bank_ref_no")
    val bankRefNo: String? = null,
    @SerializedName("billing_address")
    val billingAddress: String? = null,
    @SerializedName("billing_city")
    val billingCity: String? = null,
    @SerializedName("billing_country")
    val billingCountry: String? = null,
    @SerializedName("billing_email")
    val billingEmail: String? = null,
    @SerializedName("billing_name")
    val billingName: String? = null,
    @SerializedName("billing_notes")
    val billingNotes: String? = null,
    @SerializedName("billing_state")
    val billingState: String? = null,
    @SerializedName("billing_tel")
    val billingTel: String? = null,
    @SerializedName("billing_zip")
    val billingZip: String? = null,
    @SerializedName("bin_country")
    val binCountry: String? = null,
    @SerializedName("card_name")
    val cardName: String? = null,
    val currency: String? = null,
    @SerializedName("delivery_address")
    val deliveryAddress: String? = null,
    @SerializedName("delivery_city")
    val deliveryCity: String? = null,
    @SerializedName("delivery_country")
    val deliveryCountry: String? = null,
    @SerializedName("delivery_name")
    val deliveryName: String? = null,
    @SerializedName("delivery_state")
    val deliveryState: String? = null,
    @SerializedName("delivery_tel")
    val deliveryTel: String? = null,
    @SerializedName("delivery_zip")
    val deliveryZip: String? = null,
    @SerializedName("discount_value")
    val discountValue: String? = null,
    @SerializedName("eci_value")
    val eciValue: String? = null,
    @SerializedName("failure_message")
    val failureMessage: String? = null,
    @SerializedName("mer_amount")
    val merAmount: Double? = null,
    @SerializedName("merchant_param1")
    val merchantParam1: String? = null,
    @SerializedName("merchant_param2")
    val merchantParam2: String? = null,
    @SerializedName("merchant_param3")
    val merchantParam3: String? = null,
    @SerializedName("merchant_param4")
    val merchantParam4: String? = null,
    @SerializedName("merchant_param5")
    val merchantParam5: String? = null,
    @SerializedName("offer_code")
    val offerCode: String? = null,
    @SerializedName("offer_type")
    val offerType: String? = null,
    @SerializedName("order_id")
    val orderId: String? = null,
    @SerializedName("order_status")
    val orderStatus: String? = null,
    @SerializedName("payment_mode")
    val paymentMode: String? = null,
    @SerializedName("response_code")
    val responseCode: String? = null,
    val retry: String? = null,
    @SerializedName("status_code")
    val statusCode: String? = null,
    @SerializedName("status_message")
    val statusMessage: String? = null,
    @SerializedName("sub_account_id")
    val subAccountId: String? = null,
    @SerializedName("tracking_id")
    val trackingId: String? = null,
    @SerializedName("trans_date")
    val transDate: String? = null,
    val vault: String? = null
)
@Serializable
data class Card(
    @SerializedName("card_brand")
    val cardBrand: String? = null,
    @SerializedName("card_fingerprint")
    val cardFingerprint: String? = null,
    @SerializedName("card_isin")
    val cardIsin: String? = null,
    @SerializedName("card_issuer")
    val cardIssuer: String? = null,
    @SerializedName("card_issuer_country")
    val cardIssuerCountry: String? = null,
    @SerializedName("card_reference")
    val cardReference: String? = null,
    @SerializedName("card_type")
    val cardType: String? = null,
    @SerializedName("expiry_month")
    val expiryMonth: String? = null,
    @SerializedName("expiry_year")
    val expiryYear: String? = null,
    @SerializedName("extended_card_type")
    val extendedCardType: String? = null,
    @SerializedName("juspay_bank_code")
    val juspayBankCode: String? = null,
    @SerializedName("last_four_digits")
    val lastFourDigits: String? = null,
    @SerializedName("name_on_card")
    val nameOnCard: String? = null,
    @SerializedName("payment_account_reference")
    val paymentAccountReference: String? = null,
    @SerializedName("saved_to_locker")
    val savedToLocker: Boolean? = null,
    val token:Token? = null,
    val tokens: List<Token?>? = null,
    @SerializedName("using_saved_card")
    val usingSavedCard: Boolean? = null,
    @SerializedName("using_token")
    val usingToken: Boolean? = null
)
@Serializable
data class Token(
    @SerializedName("card_reference")
    val cardReference:String? = null,
    @SerializedName("card_fingerprint")
    val cardFingerprint:String? = null,
    @SerializedName("last_four_digits")
    val lastFourDigits:String? = null,
    @SerializedName("card_isin")
    val cardIsin:String? = null,
    @SerializedName("expiry_year")
    val expiryYear:String? = null,
    @SerializedName("expiry_month")
    val expiryMonth:String? = null,
    val par:String? = null,
    @SerializedName("tokenization_status")
    val tokenizationStatus:String? = null,
    val provider:String? = null,
    @SerializedName("provider_category")
    val providerCategory:String? = null
 )