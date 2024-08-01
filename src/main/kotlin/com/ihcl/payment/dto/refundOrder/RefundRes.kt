package com.ihcl.payment.dto.refundOrder

import com.google.gson.annotations.SerializedName
import com.ihcl.payment.dto.Card
import com.ihcl.payment.dto.PaymentGatewayResponse
import com.ihcl.payment.dto.PaymentLinks
import com.ihcl.payment.dto.TxnDetail
import kotlinx.serialization.Serializable

@Serializable
data class RefundRes(
    val card:Card?,
    @SerializedName("order_id")
    val orderId:String?,
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
    val status:String?,
    val amount:Double?,
    @SerializedName("auth_type")
    val authType:String?,
    val refunded:Boolean?,
    @SerializedName("payment_method")
    val paymentMethod:String?,
    @SerializedName("gateway_id")
    val gatewayId:Int?,
    val refunds:List<RefundData?>?,
    @SerializedName("payment_method_type")
    val paymentMethodType:String?,
    @SerializedName("txn_uuid")
    val txnUuid:String?,
    @SerializedName("customer_id")
    val customerId:String?,
    @SerializedName("bank_pg")
    val bankPg:String?,
    @SerializedName("payment_links")
    val paymentLinks:PaymentLinks?,
    @SerializedName("effective_amount")
    val effectiveAmount:Double?,
    @SerializedName("payment_gateway_response")
    val paymentGatewayResponse:PaymentGatewayResponse?,
    @SerializedName("product_id")
    val productId:String?,
    @SerializedName("txn_detail")
    val txnDetail:TxnDetail?,
    @SerializedName("amount_refunded")
    val amountRefunded:Int?,
    @SerializedName("customer_email")
    val customerEmail:String?,
    val currency:String?,
    @SerializedName("customer_phone")
    val customerPhone:String?,
    @SerializedName("bank_error_message")
    val bankErrorMessage:String?,
    val id:String?,
    @SerializedName("txn_id")
    val txnId:String?,
    @SerializedName("merchant_id")
    val merchantId:String?,
    @SerializedName("maximum_eligible_refund_amount")
    val maximumEligibleRefundAmount:Double?,
    @SerializedName("date_created")
    val dateCreated:String?,
    @SerializedName("bank_error_code")
    val bankErrorCode:String?,
    @SerializedName("gateway_reference_id")
    val gatewayReferenceId:String?,
    @SerializedName("return_url")
    val returnUrl:String?,
    @SerializedName("status_id")
    val statusId:String?
)
@Serializable
data class RefundData(
    val status:String?,
    val amount:Double?,
    @SerializedName("sent_to_gateway")
    val sentToGateway:Boolean?,
    @SerializedName("unique_request_id")
    val uniqueRequestId:String?,
    @SerializedName("error_code")
    val errorCode:String?,
    val created:String?,
    @SerializedName("initiated_by")
    val initiatedBy:String?,
    @SerializedName("refund_source")
    val refundSource:String?,
    @SerializedName("error_message")
    val errorMessage:String?,
    val id:String?,
    @SerializedName("refund_type")
    val refundType:String?,
    val ref:String?
)
