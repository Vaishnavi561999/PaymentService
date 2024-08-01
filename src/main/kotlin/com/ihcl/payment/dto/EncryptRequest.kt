package com.ihcl.payment.dto

import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern
import kotlinx.serialization.Serializable

@Serializable
data class EncryptRequest (
    val data:String
)

val validateEncryptRequest = Validation{
    EncryptRequest::data required{
        pattern("^.+$") // should not be empty
    }
}