package com.ihcl.payment.service
import org.koin.dsl.module

val serviceModule = module {
    single {
        InitiateSdkService()
    }
    single {
        FetchOrderService()
    }
    single {
        OrderStatusService()
    }
    single {
        SiCreationService()
    }
    single {
        RefundService()
    }
    single {
        WebhookService()
    }
}