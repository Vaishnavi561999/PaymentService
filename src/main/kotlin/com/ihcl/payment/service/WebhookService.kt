package com.ihcl.payment.service

import com.ihcl.payment.repository.DatabaseRepository
import com.ihcl.payment.util.Constants
import com.ihcl.payment.util.Constants.CONTENT
import com.ihcl.payment.util.Constants.CREATED
import com.ihcl.payment.util.Constants.EVENT_NAME
import com.ihcl.payment.util.Constants.GATEWAY_ID
import com.ihcl.payment.util.Constants.GATEWAY_RESPONSE
import com.ihcl.payment.util.Constants.ORDER
import com.ihcl.payment.util.Constants.ORDER_FAILED
import com.ihcl.payment.util.Constants.ORDER_ID
import com.ihcl.payment.util.Constants.ORDER_SUCCEEDED
import com.ihcl.payment.util.Constants.PAYMENT_GATEWAY_RESPONSE
import com.ihcl.payment.util.Constants.STATUS
import com.ihcl.payment.util.Constants.TXN_CREATED
import com.ihcl.payment.util.Constants.TXN_DETAILS
import com.ihcl.payment.util.Constants.TXN_ID
import com.ihcl.payment.util.Constants.TXN_UUID
import com.ihcl.payment.util.Constants.WEBHOOK_PAYMENT_METHOD
import com.ihcl.payment.util.Constants.WEBHOOK_PAYMENT_MODE
import org.koin.java.KoinJavaComponent
import org.litote.kmongo.json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class WebhookService {
    val log: Logger = LoggerFactory.getLogger(javaClass)
    private val databaseRepository by KoinJavaComponent.inject<DatabaseRepository>(DatabaseRepository::class.java)

    suspend fun storeOrderDetails(request: Map<String, Any>): String {

        log.info("request: ${request.json}")
        val content = request[CONTENT] as? Map<*, *>
        val order = content?.get(ORDER) as? Map<*, *>
        val orderId = order?.get(ORDER_ID)
        val paymentGatewayResponse = order?.get(PAYMENT_GATEWAY_RESPONSE) as? Map<*, *>
        val gatewayResponse = paymentGatewayResponse?.get(GATEWAY_RESPONSE) as? Map<*, *>
        val txnDetail = order?.get(TXN_DETAILS) as? Map<*, *>
        val gatewayId = txnDetail?.get(GATEWAY_ID) as? Double ?: 0.0
        log.info("webhook event name ${request[EVENT_NAME].toString()}")
        val ihclOrder = databaseRepository.getOrder(orderId.toString())
        log.info("Order received from db is $ihclOrder")
        var response = request[EVENT_NAME].toString()
        val paymentDetails = ihclOrder.paymentDetails.transaction_1
        if (request[EVENT_NAME] == ORDER_SUCCEEDED || request[EVENT_NAME] == ORDER_FAILED || request[EVENT_NAME] == TXN_CREATED) {
            paymentDetails?.withIndex()?.find {
                it.value.paymentType == Constants.JUS_PAY && it.value.txnStatus != Constants.CHARGED
            }?.let { paymentDetail ->
                paymentDetail.value.paymentMethod = order?.get(WEBHOOK_PAYMENT_METHOD).toString()
                paymentDetail.value.txnGateway = gatewayId.toInt()
                paymentDetail.value.txnId = txnDetail?.get(TXN_ID).toString()
                paymentDetail.value.txnUUID = txnDetail?.get(TXN_UUID).toString()
                paymentDetail.value.transactionDateAndTime = txnDetail?.get(CREATED).toString()
                paymentDetail.value.paymentMethodType = gatewayResponse?.get(WEBHOOK_PAYMENT_MODE).toString()
                //updating transaction status
                when {
                    txnDetail?.get(STATUS).toString() == Constants.CHARGED -> {
                        paymentDetail.value.txnStatus = Constants.CHARGED
                    }
                    (txnDetail?.get(STATUS).toString() == Constants.PENDING_VBV) -> {
                        paymentDetail.value.txnStatus = Constants.INITIATED
                    }
                    (txnDetail?.get(STATUS).toString() == Constants.AUTHORIZATION_FAILED) -> {
                        paymentDetail.value.txnStatus = Constants.FAILED
                    }
                }
                // updating order status
                when{
                    (request[EVENT_NAME] == ORDER_FAILED)-> {
                        ihclOrder.orderStatus = Constants.FAILED
                        ihclOrder.paymentStatus = Constants.FAILED
                    }
                    (ihclOrder.orderStatus == Constants.INITIATED)->{
                        ihclOrder.modifiedTimestamp = Date()
                        ihclOrder.orderStatus = Constants.PENDING
                    }
                }
                databaseRepository.findOneAndUpdateOrder(orderId.toString(), ihclOrder)
                log.info("Webhook details updated for orderId $orderId with status ${order?.get(STATUS)}")
                response = "Webhook details updated for orderId $orderId with status ${order?.get(STATUS)}"
            } ?: run {
                response = "Charged order $orderId"
            }
            return response
        }
        return response
    }
}
