package com.ihcl.payment.util

import com.ihcl.payment.exception.BadRequestException
import io.konform.validation.Invalid
import io.konform.validation.ValidationResult
import io.ktor.http.HttpStatusCode

fun <T> validateRequestBody(validateResult: ValidationResult<T>){
    if (validateResult is Invalid) {
        throw BadRequestException(
            HttpStatusCode.BadRequest.value,
            message = ResponseMessages.VALIDATION_ERROR_OCCURRED + "${validateResult.errors}",
            errorMessage = generateErrors(validateResult)
        )
    }
}