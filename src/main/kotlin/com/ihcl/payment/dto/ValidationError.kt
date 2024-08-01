package com.ihcl.payment.dto

import kotlinx.serialization.Serializable

@Serializable
data class ValidationError (
    var statusCode: Int?,
    var errorMessage: List<String>,
    val message: String?
)