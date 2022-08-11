package com.mikhaellopez.presentation.di

import android.util.Log
import com.mikhaellopez.domain.usecases.base.Logger
import com.mikhaellopez.presentation.exception.ErrorMessageFactory
import com.mikhaellopez.presentation.scenes.carddetail.CardDetailViewModel
import com.mikhaellopez.presentation.scenes.cardlist.CardListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val helperModule = module {
    single<Logger> {
        object : Logger {
            override fun log(tag: String, message: () -> String) {
                Log.d(tag, message())
            }

            override fun logError(tag: String, throwable: () -> String) {
                Log.e(tag, throwable())
            }
        }
    }
    singleOf(::ErrorMessageFactory)
}

val viewModelModule = module {
    viewModelOf(::CardListViewModel)
    viewModelOf(::CardDetailViewModel)
}

val koinPresentationModules = listOf(
    helperModule,
    viewModelModule
)
