package com.ihcl.payment.service

import com.ihcl.payment.config.Configuration
import com.ihcl.payment.exception.InternalServerException
import com.ihcl.payment.util.Constants
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.ihcl.payment.plugins.ConfigureClient
import io.ktor.http.HttpHeaders
import org.litote.kmongo.json

class OrderStatusService {
    private val prop = Configuration.env
    private val log: Logger = LoggerFactory.getLogger(javaClass)
    suspend fun getPaymentStatus(request: com.ihcl.payment.dto.OrderStatusRequest):HttpResponse{
        log.info("payment status request: ${request.json}")
        val orderStatusUrl = prop.baseUrl + "/${request.orderId}" + prop.orderStatusUrl
        try {
            val response: HttpResponse = ConfigureClient.client.get(orderStatusUrl) {
                timeout {
                    requestTimeoutMillis = Constants.REUESTTIMEOUTMILLIS.toLong()
                }
                headers{
                    append(HttpHeaders.ContentType,Constants.ORDER_STATUS_CONTENT_TYPE)
                    append(Constants.ORDER_STATUS_VERSION_KEY,prop.confirmationVersion)
                    append(HttpHeaders.Authorization,prop.authorization)
                    append(Constants.X_MERCHANTID, prop.refundMerchantId)
                }
            }
            log.info("order status api response received from juspay is ${response.bodyAsText()}")
            return response
        } catch (e: Exception) {
            log.info("Exception occured while calling api is ${e.message} due to ${e.printStackTrace()}")
            throw InternalServerException(e.message)
        }
    }
}