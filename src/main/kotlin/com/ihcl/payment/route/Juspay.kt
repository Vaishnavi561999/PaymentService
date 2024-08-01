package com.ihcl.payment.route

import com.ihcl.payment.dto.OrderRefundRequest
import com.ihcl.payment.dto.validateOrderRefundRequest
import com.ihcl.payment.dto.OrderStatusErrorResponse
import com.ihcl.payment.dto.OrderStatusRequest
import com.ihcl.payment.dto.validateOrderStatusRequest
import com.ihcl.payment.dto.OrderStatusResponseDto
import com.ihcl.payment.dto.initiatesdk.Initiatesdkreq
import com.ihcl.payment.dto.initiatesdk.validateInitiatereq
import com.ihcl.payment.dto.processsdk.ProcessSdkRequest
import com.ihcl.payment.dto.processsdk.validateProcessSdkRequest
import com.ihcl.payment.dto.refundOrder.RefundRes
import com.ihcl.payment.exception.HttpResponseException
import com.ihcl.payment.service.*
import com.ihcl.payment.util.Constants
import com.ihcl.payment.util.Constants.CUSTOMERHASH
import com.ihcl.payment.util.validateRequestBody
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.java.KoinJavaComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Application.juspay(){
    val initiateSDKService by KoinJavaComponent.inject<InitiateSdkService>(InitiateSdkService::class.java)
    val orderStatusService by KoinJavaComponent.inject<OrderStatusService>(OrderStatusService::class.java)
    val fetchOrderService by KoinJavaComponent.inject<FetchOrderService>(FetchOrderService::class.java)
    val refundService by KoinJavaComponent.inject<RefundService>(RefundService::class.java)
    val webhookService by KoinJavaComponent.inject<WebhookService>(WebhookService::class.java)
    val log: Logger = LoggerFactory.getLogger(javaClass)
    routing {
        get("/"){
            call.respond("Payment service......")
        }
        route("/v1/juspay"){
        post("/initiate-sdk") {
            val request = call.receive<Initiatesdkreq>()
            var referer = call.request.headers[Constants.HEADER_ORIGIN]
            log.info("Received request from $referer")
            if(referer.isNullOrEmpty()){
                referer=call.request.headers[Constants.HEADER_TENANT]
            }
            log.info("referer final value received is $referer")
            val validateRequest = validateInitiatereq.validate(request)
            validateRequestBody(validateRequest)
            log.info("successfully validated request")
            log.info("Received Initiate_sdk request as $request")
            val response = initiateSDKService.processInitiateSDK(request,referer)
            log.info("Initiate_sdk response prepared as $response")
            call.respond(response)
        }
        post("/fetch-order") {
            val request = call.receive<ProcessSdkRequest>()
            val validateRequest = validateProcessSdkRequest.validate(request)
            validateRequestBody(validateRequest)
            log.info("successfully validated request")
            log.info("Request received as $request")
            val customerHash = call.request.headers[CUSTOMERHASH] ?: throw HttpResponseException("CustomerHash is mandatory",
                HttpStatusCode.BadRequest)
            val response = fetchOrderService.fetchOrder(request,customerHash)
            log.info("fetch order details response prepared as $response")
            call.respond(response)
        }
        post("/payment-confirmation") {
            val request = call.receive<OrderStatusRequest>()
            val validateRequest = validateOrderStatusRequest.validate(request)
            validateRequestBody(validateRequest)
            log.info("Request Received as $request")
            val response = orderStatusService.getPaymentStatus(request)
            if(response.status == HttpStatusCode.OK){
                call.respond(response.body() as OrderStatusResponseDto)
            }else{
                call.respond(HttpStatusCode.BadRequest,response.body() as OrderStatusErrorResponse)
            }
        }
        post("/refund-order"){
            val request = call.receive<OrderRefundRequest>()
            val validateRequest = validateOrderRefundRequest.validate(request)
            validateRequestBody(validateRequest)
            log.info("Request Received as $request")
            val response = refundService.refund(request)
            if(response.status.value == Constants.BAD_REQUEST){
                call.respond(HttpStatusCode.BadRequest,response.body() as OrderStatusErrorResponse)
            }else{
                call.respond(response.body() as RefundRes)
            }
        }
        post("/webhook") {
            val request = call.receive<Map<String, Any>>()
            log.info("webhook API request {}", request)
            val response = webhookService.storeOrderDetails(request)
            call.respondText(response)
        }
    }
}}