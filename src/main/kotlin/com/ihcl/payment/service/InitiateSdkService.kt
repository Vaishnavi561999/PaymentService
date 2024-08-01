package com.ihcl.payment.service

import com.google.gson.Gson
import com.ihcl.payment.config.Configuration
import com.ihcl.payment.dto.initiatesdk.Initiatesdkreq
import com.ihcl.payment.dto.initiatesdk.Initiatesdkres
import com.ihcl.payment.dto.initiatesdk.Signaturepayload
import com.ihcl.payment.dto.initiatesdk.Payload
import com.ihcl.payment.exception.InternalServerException
import com.ihcl.payment.util.Constants
import com.ihcl.payment.util.JuspaySignatureGenerationUtil
import org.koin.java.KoinJavaComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class InitiateSdkService {
    val log: Logger = LoggerFactory.getLogger(javaClass)
    private val generateSignature by KoinJavaComponent.inject<JuspaySignatureGenerationUtil>(JuspaySignatureGenerationUtil::class.java)
    private val fetchOrderService by KoinJavaComponent.inject<FetchOrderService>(FetchOrderService::class.java)

    val gson = Gson()
     fun processInitiateSDK(request: Initiatesdkreq,tenant : String?): Initiatesdkres {
         try {
             val props = Configuration.env
             val signatureRes = Signaturepayload(
                 props.merchantId,
                 request.customerHash,
                 request.mobileNo,
                 request.emailAddress,
                 System.currentTimeMillis().toString()
             )
             val signaturePayload = gson.toJson(signatureRes)
             val signature = generateSignature.sign(signaturePayload, props.filepath, false)
             log.info("signature generated is$signature")
             val payloadRes = Payload(
                 Constants.INITIATE_ACTION,
                 props.merchantKeyId,
                 Constants.INTEGRATION,
                 signaturePayload,
                 signature,
                 props.initiateEnvironment,
                 fetchOrderService.populateClientId(tenant),
                 Constants.INITIATE_HYPERSDKDIV,
                 props.merchantId
             )
             return Initiatesdkres(
                 UUID.randomUUID().toString(),
                 Constants.INITIATE_SERVICE,
                 Constants.JUSPAY_SERVICE,
                 payloadRes
             )
         } catch (e: Exception) {
             log.info("exception occurred as ${e.message} ")
             throw InternalServerException(e.message)
         }
    }
}
