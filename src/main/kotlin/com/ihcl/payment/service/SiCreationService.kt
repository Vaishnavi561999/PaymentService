package com.ihcl.payment.service

import com.ihcl.payment.config.Configuration
import com.ihcl.payment.exception.InternalServerException
import com.ihcl.payment.plugins.ConfigureClient
import com.ihcl.payment.repository.DatabaseRepository
import com.ihcl.payment.schema.Order
import com.ihcl.payment.util.AesCryptUtil
import com.ihcl.payment.util.Constants
import io.ktor.client.plugins.timeout
import io.ktor.client.request.delete
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import org.koin.java.KoinJavaComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
class SiCreationService {
    val log: Logger = LoggerFactory.getLogger(javaClass)
    private val props = Configuration.env
    private val databaseRepository by KoinJavaComponent.inject<DatabaseRepository>(DatabaseRepository::class.java)

    fun encrypt(request: com.ihcl.payment.dto.EncryptRequest,referer:String?): String {
        try {
            log.info("Request received from front end is {}", request)
            val encrypter = if (referer != null && referer.contains(Constants.GATEWAY)) {
                log.info("Received request from gateway using gateway working key")
                AesCryptUtil(props.gatewayWorkingkey)
            } else {
                log.info("Received request from taj using taj working key")
                AesCryptUtil(props.workingKey)
            }
            val encryptedRes = encrypter.encrypt(request.data)
            log.info("Encrypted data is $encryptedRes")
            return encryptedRes!!
        } catch (e: Exception) {
            log.info("Exception occurred while calling encrypt api is {}", e.printStackTrace())
            throw InternalServerException(e.message)
        }
    }
    suspend fun decrypt(encResp:String,referer: String?):String{
        try{
            log.info("Request received from front end is $encResp")
            val decrypter = if (referer != null && referer.contains(Constants.GATEWAY)) {
                log.info("Received request from gateway using gateway working key")
             AesCryptUtil(props.gatewayWorkingkey)
            } else {
                log.info("Received request from taj using taj working key")
                AesCryptUtil(props.workingKey)
            }
            val decryptedRes = decrypter.decrypt(encResp)
            val redirect:String = getRedirectUrl(decryptedRes)
            log.info("Decrypted data is $decryptedRes")
            return redirect
        }catch (e:Exception){
            log.info("Exception occured while calling decrypt api is ${e.message} and ${e.cause}")
            throw InternalServerException(e.message)
        }
    }

    private suspend fun getRedirectUrl(decryptedRes: String): String {
        val splitedRes = decryptedRes.split("&")
        val map = mutableMapOf<String, String>()
        for (element in splitedRes) {
            var value: String? = null
            if (element.endsWith("=")) {
                value = "$element\$"
            }
            if (value == null) {
                val keyValue = element.split("=")
                for (j in 0 until keyValue.size - 1) {
                    map[keyValue[j]] = keyValue[j + 1]
                }
            } else {
                val s2 = value.split("=")
                for (j in 0 until s2.size - 1) {
                    map[s2[j]] = s2[j + 1]
                }
            }
        }
        log.info("converted response {}", map)
        val order = databaseRepository.getOrder(map["order_id"])
        log.info("order details before updating payment details {}", order)
        val status = updateOrderDetails(order,map)
        return  getReturnUrl(order,map,status);
    }
    private fun getReturnUrl(order: Order, map: Map<String, String>, status: String,): String {
        return when {
            order.brandName == Constants.GATEWAY ->
                "${props.gatewayReturnUrl}/en-in/booking/booking-confirmed?order_id=${map["order_id"]}&status=${status}"
            else ->
                "${props.returnUrl}/en-in/booking/booking-confirmed?order_id=${map["order_id"]}&status=${status}"
        }
    }
    private suspend fun updateOrderDetails(order:Order, map:MutableMap<String,String>):String{
        val status: String
        val paymentsDetails = when (order.modifyBookingCount) {
            0 -> order.paymentDetails.transaction_1
            1 -> order.paymentDetails.transaction_2
            else -> order.paymentDetails.transaction_3
        }
        if (map["order_status"] == "Success") {
            order.paymentMethod = "PAY AT HOTEL"
            paymentsDetails?.get(0)?.paymentMethod = map[Constants.CARD_NAME]
            paymentsDetails?.get(0)?.txnNetAmount = map[Constants.AMOUNT]?.toDouble()
            paymentsDetails?.get(0)?.txnId = map[Constants.TRACKING_ID]
            paymentsDetails?.get(0)?.ccAvenueTxnId = map[Constants.SI_REF_NO]
            paymentsDetails?.get(0)?.txnUUID = map[Constants.BANK_REF_NO]
            paymentsDetails?.get(0)?.txnStatus = Constants.CHARGED
            paymentsDetails?.get(0)?.paymentMethodType = map[Constants.PAYMENT_MODE]
            paymentsDetails?.get(0)?.transactionDateAndTime = map[Constants.TRANS_DATE]
            order.paymentStatus = Constants.CHARGED
            emptyCart(order.customerHash)
            log.info("order details after updating payment details {}", order)
            status = Constants.CHARGED
        } else if (map["order_status"] == "Failure") {
            order.paymentMethod = "PAY AT HOTEL"
            paymentsDetails?.get(0)?.paymentMethod = map[Constants.CARD_NAME]
            paymentsDetails?.get(0)?.txnNetAmount = map[Constants.AMOUNT]?.toDouble()
            paymentsDetails?.get(0)?.txnId = map[Constants.TRACKING_ID]
            paymentsDetails?.get(0)?.ccAvenueTxnId = map[Constants.SI_REF_NO]
            paymentsDetails?.get(0)?.txnUUID = map[Constants.BANK_REF_NO]
            paymentsDetails?.get(0)?.txnStatus = Constants.FAILED
            paymentsDetails?.get(0)?.paymentMethodType = map[Constants.PAYMENT_MODE]
            paymentsDetails?.get(0)?.transactionDateAndTime = map[Constants.TRANS_DATE]
            order.paymentStatus = Constants.FAILED
            emptyCart(order.customerHash)
            status = Constants.FAILED
        } else if(map["order_status"] == Constants.ABORTED){
            order.paymentStatus = Constants.FAILED
            status = Constants.FAILED
            paymentsDetails?.get(0)?.txnStatus = Constants.FAILED
            if (order.paymentMethod == Constants.PAY_AT_HOTEL){
                paymentsDetails?.get(0)?.txnId = map[Constants.TRACKING_ID]
                paymentsDetails?.get(0)?.ccAvenueTxnId = map[Constants.SI_REF_NO]
            }
        }else{
            order.paymentStatus = map["order_status"].toString()
            status = "${map[Constants.FAILURE_MESSAGE]}"
            paymentsDetails?.get(0)?.txnStatus = Constants.FAILED
            if (order.paymentMethod == Constants.PAY_AT_HOTEL){
                paymentsDetails?.get(0)?.txnId = map[Constants.TRACKING_ID]
                paymentsDetails?.get(0)?.ccAvenueTxnId = map[Constants.SI_REF_NO]
            }
        }
        databaseRepository.findOneAndUpdateOrder(order.orderId, order)
        return status
    }
    private suspend fun emptyCart(customerHash:String) {
        val response: HttpResponse = ConfigureClient.client.delete(props.cartUrl) {
            timeout {
                requestTimeoutMillis = 80000
            }
            headers {
                append(Constants.CART_CUSTOMER_HASH, customerHash)
            }
        }
        log.info("removing items from cart {}", response)
    }
}