package com.ihcl.payment.plugins

import com.ihcl.payment.repository.repositoryModule
import com.ihcl.payment.service.serviceModule
import com.ihcl.payment.util.utilModule
import io.ktor.server.application.Application
import org.koin.core.context.startKoin

fun Application.configureDependencyInjection(){
    startKoin {
        modules(serviceModule,utilModule,repositoryModule)
    }
}