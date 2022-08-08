package com.mikhaellopez.domain.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import org.koin.ksp.generated.module
import kotlin.coroutines.CoroutineContext

val domainUseCaseModule = module {
    single<CoroutineContext> {
        Dispatchers.IO
    }
}

val koinDomainModules = listOf(
    domainUseCaseModule,
    UseCasesModule().module
)