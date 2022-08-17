package com.mikhaellopez.data.di

import com.mikhaellopez.data.net.HttpClientFactory
import com.mikhaellopez.data.persistence.AppDatabase
import com.mikhaellopez.data.persistence.DatabaseFactory
import com.mikhaellopez.data.repository.CardDataRepository
import com.mikhaellopez.domain.repository.CardRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ksp.generated.module

val netModule = module {
    single {
        HttpClientFactory().createHttpClient(context = get())
    }
}

val persistenceModule = module {
    single {
        DatabaseFactory.getDatabase(get())
    }
    single {
        get<AppDatabase>().cardDao()
    }
}

val repositoryModule = module {
    singleOf(::CardDataRepository) { bind<CardRepository>() }
}

val koinDataSourceModules = listOf(
    netModule,
    NetModule().module,
    persistenceModule,
    ProcessorModule().module,
    MapperModule().module,
    repositoryModule
)
