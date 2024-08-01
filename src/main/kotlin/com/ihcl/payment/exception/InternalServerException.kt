package com.ihcl.payment.exception

import io.ktor.http.*

class InternalServerException (
    override val message: String?
):Exception()

class HttpResponseException(val data: Any, val statusCode: HttpStatusCode) : Exception()