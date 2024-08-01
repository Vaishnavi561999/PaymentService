package com.ihcl.payment.service

import com.google.gson.Gson
import com.ihcl.payment.config.Configuration
import com.ihcl.payment.dto.processsdk.OrderDetails
import com.ihcl.payment.dto.processsdk.ProcessSdkRequest
import com.ihcl.payment.dto.processsdk.ProcessSdkResponse
import com.ihcl.payment.exception.InternalServerException
import com.ihcl.payment.repository.DatabaseRepository
import com.ihcl.payment.schema.Order
import com.ihcl.payment.schema.OrderType
import com.ihcl.payment.util.Constants
import com.ihcl.payment.util.JuspaySignatureGenerationUtil
import org.koin.java.KoinJavaComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FetchOrderService {
    val log: Logger = LoggerFactory.getLogger(javaClass)
    private val databaseRepository by KoinJavaComponent.inject<DatabaseRepository>(DatabaseRepository::class.java)
    private val generateSignature by KoinJavaComponent.inject<JuspaySignatureGenerationUtil>(
        JuspaySignatureGenerationUtil::class.java
    )
    private val gson = Gson()
    private val props = Configuration.env
    suspend fun fetchOrder(request: ProcessSdkRequest, customerHash: String): ProcessSdkResponse {
        val order = databaseRepository.findOrderByOrderIdAndCustomerHash(request.orderId,customerHash)
        try {
            return when (order.orderType) {
                OrderType.HOTEL_BOOKING -> {
                    generateProcessSDKResponse(request, Constants.METADATA_JUSPAY_GATEWAY_REFERENCE_ID_HOTEL,order)
                }
                OrderType.HOLIDAYS  -> {
                    generateProcessSDKResponse(request,Constants.METADATA_JUSPAY_GATEWAY_REFERENCE_ID_HOTEL,order)
                }

                OrderType.GIFT_CARD_PURCHASE -> {
                    generateProcessSDKResponse(request, Constants.METADATA_JUSPAY_GATEWAY_REFERENCE_ID_GC,order)
                }

                OrderType.RELOAD_BALANCE -> {
                    generateProcessSDKResponse(request, Constants.METADATA_JUSPAY_GATEWAY_REFERENCE_ID_GC,order)
                }

                OrderType.MEMBERSHIP_PURCHASE -> {
                    generateProcessSDKResponse(request, Constants.METADATA_JUSPAY_GATEWAY_REFERENCE_ID_EPICURE,order)
                }

                else -> {
                    throw InternalServerException("Invalid order type!!")
                }
            }

        } catch (e: Exception) {
            log.info("exception occurred as ${e.message} ")
            throw InternalServerException(e.message)
        }
    }

    private fun generateProcessSDKResponse(
        request: ProcessSdkRequest,
        gatewayReference: String,
        order: Order
    ): ProcessSdkResponse {
        try {
            val requestId = System.currentTimeMillis().toString()
            val customerHash = if(!request.isLogin){
                ""
            }else{
                order.customerHash
            }
            log.info("order received from order db is ${gson.toJson(order)}")
            val orderDetails = prepareOrderDetails(request.returnUrl, order, gatewayReference,customerHash)
            val ordDetails = gson.toJson(orderDetails)
            log.info("order details for generating signature $ordDetails")
            val signature = generateSignature.sign(
                ordDetails, props.filepath, false
            )
            log.info("Generated signature is $signature")
            val response = ProcessSdkResponse(
                com.ihcl.payment.dto.processsdk.Payload(
                    Constants.FETCH_ACTION,
                    order.payableAmount.toString(),
                    populateClientId(order.brandName),
                    order.customerEmail,
                    customerHash,
                    order.customerMobile,
                    Constants.FETCH_LANGUAGE,
                    props.merchantId,
                    props.merchantKeyId,
                    ordDetails,
                    request.orderId,
                    signature
                ),
                requestId,
                props.processSDKService
            )
            log.info("Fetch order response prepared as $response")
            return response
        } catch (e: Exception) {
            log.info("exception occurred as ${e.message} ")
            throw InternalServerException(e.message)
        }
    }

    private fun prepareOrderDetails(returnUrl: String, order: Order, gatewayReference: String,customerHash:String?): OrderDetails? {
        var orderDetails: OrderDetails? = null
        if (order.orderType == OrderType.HOTEL_BOOKING || order.orderType == OrderType.HOLIDAYS) {
            val confirmationNumbers = mutableListOf<String>()
            val rateCodes = mutableListOf<String>()
            val rateCodeRoomCode = mutableListOf<String>()
            val itineraryNumber = order.orderLineItems[0].hotel?.bookingNumber
            order.orderLineItems[0].hotel?.rooms?.forEach {
                it.confirmationId?.let { it1 -> confirmationNumbers.add(it1) }
            }
            order.orderLineItems[0].hotel?.rooms?.forEach {
                it.rateCode?.let { it1 -> rateCodes.add(it1) }
            }
            order.orderLineItems[0].hotel?.rooms?.forEach {
                rateCodeRoomCode.add("(${it.rateCode}/${it.roomType})")
            }
            val merchantParam1 = confirmationNumbers.joinToString("/")
            val merchantParam2 = order.orderLineItems[0].hotel?.rooms?.get(0)?.travellerDetails?.get(0)?.firstName
            val merchantParam3 = "(${order.orderLineItems[0].hotel?.checkIn},${order.orderLineItems[0].hotel?.checkOut})"
            val merchantParam4 = rateCodes.joinToString("/")

            val merchantParam5 = "(${confirmationNumbers[0].substring(0, minOf(confirmationNumbers[0].length,5))},${itineraryNumber?.substring(0,
                minOf(itineraryNumber.length,5))},${rateCodeRoomCode.joinToString("/")})"

            orderDetails = OrderDetails(
                order.orderId,
                props.merchantId,
                order.payableAmount.toString(),
                System.currentTimeMillis().toString(),
                customerHash,
                order.customerEmail,
                order.customerMobile,
                returnUrl,
                order.orderLineItems[0].hotel?.storeId,
                order.billingAddress.firstName,
                order.billingAddress.lastName,
                order.billingAddress.address1,
                order.billingAddress.address2,
                order.billingAddress.address3,
                order.billingAddress.city,
                order.billingAddress.state,
                order.billingAddress.pinCode,
                order.billingAddress.country,
                order.billingAddress.phoneNumber,
                order.billingAddress.countyCodeISO,
                "${Constants.WEB} $merchantParam1",
                merchantParam2,
                merchantParam3,
                "",
                "",
                "${confirmationNumbers[0].substring(0,minOf(confirmationNumbers[0].length,5))}/${Constants.FALSE}",
                merchantParam4,
                "",
                "",
                "",
                "",
                gatewayReference,
                Constants.FETCH_ENTRYPOINT,
                Constants.INTEGRATION,
                "",
                merchantParam1,
                merchantParam2,
                merchantParam3,
                merchantParam4,
                merchantParam5,
                order.orderLineItems[0].hotel?.storeId,
                props.webhook_url
            )
        }
        else if(order.orderType == OrderType.GIFT_CARD_PURCHASE){
            val tenderModes =  mutableListOf<String>()
            order.paymentDetails.transaction_1?.forEach {
                it.paymentType?.let { it1 -> tenderModes.add(it1) }
            }

            val merchantParam1 = tenderModes.joinToString("/")
            val merchantParam2: String = if(order.orderLineItems[0].giftCard?.giftCardDetails?.get(0)?.theme == Constants.PHYSICAL_GIFT_CARD){
                    Constants.PHYSICAL_GC
                }else{
                    Constants.E_GC
                }
            val merchantParam3 = order.orderLineItems[0].giftCard?.giftCardDetails?.get(0)?.sku
            val merchantParam4 = order.orderLineItems[0].giftCard?.quantity.toString()
            val merchantParam5 = if(order.orderLineItems[0].giftCard?.isMySelf == true){
                Constants.SELF
            }
            else{
                Constants.SOME_ONE_ELSE
            }
            orderDetails = OrderDetails(
                order.orderId,
                props.merchantId,
                order.payableAmount.toString(),
                System.currentTimeMillis().toString(),
                customerHash,
                order.customerEmail,
                order.customerMobile,
                returnUrl,
                "",
                order.billingAddress.firstName,
                order.billingAddress.lastName,
                order.billingAddress.address1,
                order.billingAddress.address2,
                order.billingAddress.address3,
                order.billingAddress.city,
                order.billingAddress.state,
                order.billingAddress.pinCode,
                order.billingAddress.country,
                order.billingAddress.phoneNumber,
                order.billingAddress.countyCodeISO,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                gatewayReference,
                Constants.FETCH_ENTRYPOINT,
                Constants.INTEGRATION,
                "",
                merchantParam1,
                merchantParam2,
                merchantParam3,
                merchantParam4,
                merchantParam5,
                "",
                props.webhook_url
            )
        }
        else if(order.orderType == OrderType.RELOAD_BALANCE){
            orderDetails = OrderDetails(
                order.orderId,
                props.merchantId,
                order.payableAmount.toString(),
                System.currentTimeMillis().toString(),
                customerHash,
                order.customerEmail,
                order.customerMobile,
                returnUrl,
                "",
                order.billingAddress.firstName,
                order.billingAddress.lastName,
                order.billingAddress.address1,
                order.billingAddress.address2,
                order.billingAddress.address3,
                order.billingAddress.city,
                order.billingAddress.state,
                order.billingAddress.pinCode,
                order.billingAddress.country,
                order.billingAddress.phoneNumber,
                order.billingAddress.countyCodeISO,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                gatewayReference,
                Constants.FETCH_ENTRYPOINT,
                Constants.INTEGRATION,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                props.webhook_url
            )
        }
        else if(order.orderType == OrderType.MEMBERSHIP_PURCHASE){
            val merchantParam1 = order.orderLineItems[0].loyalty?.membershipDetails?.memberId
            val merchantParam2 = order.orderLineItems[0].loyalty?.memberCardDetails?.extra_data?.epicure_type
            orderDetails = OrderDetails(
                order.orderId,
                props.merchantId,
                order.payableAmount.toString(),
                System.currentTimeMillis().toString(),
                customerHash,
                order.customerEmail,
                order.customerMobile,
                returnUrl,
                "",
                order.billingAddress.firstName,
                order.billingAddress.lastName,
                order.billingAddress.address1,
                order.billingAddress.address2,
                order.billingAddress.address3,
                order.billingAddress.city,
                order.billingAddress.state,
                order.billingAddress.pinCode,
                order.billingAddress.country,
                order.billingAddress.phoneNumber,
                order.billingAddress.countyCodeISO,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                gatewayReference,
                Constants.FETCH_ENTRYPOINT,
                Constants.INTEGRATION,
                "",
                merchantParam1,
                merchantParam2,
                "",
                "",
                "",
                "",
                props.webhook_url
            )
        }
        return orderDetails
    }

     fun populateClientId(brandName: String?): String {
         log.info("Received request from $brandName setting client id" )
        val props = Configuration.env
         return when {
             brandName != null && brandName.contains(Constants.GATEWAY) -> props.gatewayClientId
             else -> props.client_id
         }
     }
}