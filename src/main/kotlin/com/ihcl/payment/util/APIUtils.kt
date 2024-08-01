package com.ihcl.payment.util

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*


private data class APIResponse<T : Any>(
    val path: String, val timestamp: Date, val statusCode: HttpStatusCode, val data: T
)

suspend fun <T : Any> ApplicationCall.fireHttpResponse(statusCode: HttpStatusCode, data: T) {
    respond(statusCode, APIResponse(request.path(), Date(), statusCode, data))
}