package com.ihcl.payment.dto.initiatesdk

import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern
import kotlinx.serialization.Serializable

@Serializable
data class Initiatesdkreq(
    val customerHash:String,
    val mobileNo:String,
    val emailAddress:String
)

val validateInitiatereq = Validation{
        Initiatesdkreq::customerHash required {
            pattern("\\S+") //should not be empty
        }
}