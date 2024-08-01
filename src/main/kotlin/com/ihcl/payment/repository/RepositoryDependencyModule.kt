package com.ihcl.payment.repository

import org.koin.dsl.module

val repositoryModule = module{
    single {
        DatabaseRepository()
    }
}