package com.ihcl.payment.plugins

import com.ihcl.payment.dto.ValidationError
import com.ihcl.payment.exception.BadRequestException
import com.ihcl.payment.exception.HttpResponseException
import com.ihcl.payment.exception.InternalServerException
import com.ihcl.payment.util.fireHttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import org.slf4j.LoggerFactory

fun Application.statusPages(){
    val log = LoggerFactory.getLogger(javaClass)
    install(StatusPages){
        exception<InternalServerException>{ call, cause ->
            log.error("Exception occurred while calling api"+cause.stackTraceToString())
            call.respond(HttpStatusCode.InternalServerError, com.ihcl.payment.dto.InternalServerError(cause.message!!))
        }
        exception<HttpResponseException> { call, cause ->
            log.error("error {} ",cause.message)
            call.fireHttpResponse(cause.statusCode, cause.data)
        }
        exception<BadRequestException> { call, cause ->
            log.error("Validation error occurred: ${cause.errorMessage}")
            call.respond(HttpStatusCode.BadRequest, ValidationError(
                cause.statusCode,
                cause.errorMessage,
                cause.message
            )
            )
        }
    }
}