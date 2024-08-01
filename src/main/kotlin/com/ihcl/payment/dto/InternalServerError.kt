package com.ihcl.payment.dto

import kotlinx.serialization.Serializable

@Serializable
data class InternalServerError (
    val message:String
)