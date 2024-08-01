package com.ihcl.payment.exception

data class BadRequestException(
    var statusCode: Int?,
    var errorMessage: List<String>,
    override val message: String?
):Exception()
