package com.ihcl.payment.util

import org.koin.dsl.module

val utilModule = module {
    single{
        JuspaySignatureGenerationUtil()
    }
}