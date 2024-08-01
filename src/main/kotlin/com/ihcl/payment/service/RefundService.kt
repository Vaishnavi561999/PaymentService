package com.ihcl.payment.service

import com.ihcl.payment.config.Configuration
import com.ihcl.payment.dto.OrderRefundRequest
import com.ihcl.payment.exception.InternalServerException
import com.ihcl.payment.plugins.ConfigureClient
import com.ihcl.payment.util.Constants
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.UUID

class RefundService {
    private val prop = Configuration.env
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @OptIn(InternalAPI::class)
    suspend fun refund(request: OrderRefundRequest):HttpResponse {
        log.info("Request received to refund amount is {}", request)
        val orderRefundUrl = prop.baseUrl + "/${request.orderId}" + Constants.REFUND
        try {
            val response = ConfigureClient.client.post(orderRefundUrl) {
                headers {
                    append(Constants.ORDER_STATUS_VERSION_KEY, prop.refundVersion)
                    append(HttpHeaders.ContentType, Constants.ORDER_STATUS_CONTENT_TYPE)
                    append(Constants.X_MERCHANTID, prop.refundMerchantId)
                    append(HttpHeaders.Authorization, prop.authorization)
                }
                body = TextContent("${Constants.UNIQUE_REQUEST_ID}=${UUID.randomUUID()}&${Constants.AMOUNT}=${request.amount.toDouble()}", ContentType.Application.FormUrlEncoded)
            }
            log.info("Juspay refund response received as ${response.bodyAsText()}")
            return response
        } catch (e: Exception) {
            log.info("Exception occurred while calling api is ${e.message} due to ${e.printStackTrace()}")
            throw InternalServerException(e.message)
        }
    }
}