package com.ihcl.payment.route

import com.ihcl.payment.dto.EncryptRequest
import com.ihcl.payment.dto.validateEncryptRequest
import com.ihcl.payment.service.SiCreationService
import com.ihcl.payment.util.Constants
import com.ihcl.payment.util.validateRequestBody
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.*
import io.ktor.server.routing.routing
import io.ktor.server.routing.route
import io.ktor.server.routing.post
import org.koin.java.KoinJavaComponent
import org.litote.kmongo.json
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Application.ccavenue() {
    val siCreationService by KoinJavaComponent.inject<SiCreationService>(SiCreationService::class.java)
    val log: Logger = LoggerFactory.getLogger(javaClass)

    routing {
        route("/v1/ccavenue") {
            post("/encrypt") {
                val req = call.receive<EncryptRequest>()
                var referer = call.request.headers[Constants.HEADER_ORIGIN]
                if(referer.isNullOrEmpty()){
                    referer=call.request.headers[Constants.HEADER_TENANT]
                }
                log.info("referer final value received is $referer")
                log.info("encrypt request ${req.json}")
                val validateRequest = validateEncryptRequest.validate(req)
                validateRequestBody(validateRequest)
                val response = siCreationService.encrypt(req,referer)
                call.respond(response)
            }
            post("/decrypt") {
                val formData = call.receiveParameters()
                val encResp = formData["encResp"]
                log.info("Received request for Taj decryption")
                log.info("decrypt formdata $formData")
                val redirectUrl = siCreationService.decrypt(encResp!!,Constants.TAJ)
                call.respond(redirectUrl)
                }
            post("/gateway-decrypt") {
                val formData = call.receiveParameters()
                val encResp = formData["encResp"]
                log.info("Received request for gateway decryption")
                log.info("decrypt formdata $formData")
                val redirectUrl = siCreationService.decrypt(encResp!!,Constants.GATEWAY)
                call.respond(redirectUrl)
            }
            }

    }
        }

