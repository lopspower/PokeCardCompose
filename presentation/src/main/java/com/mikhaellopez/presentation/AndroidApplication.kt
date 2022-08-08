package com.mikhaellopez.presentation

import android.app.Application
import com.mikhaellopez.data.di.koinDataSourceModules
import com.mikhaellopez.domain.di.koinDomainModules
import com.mikhaellopez.presentation.di.koinPresentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * Android Main Application
 */
class AndroidApplication : Application() {

    override fun onCreate() {
        // Koin
        startKoin {
            androidContext(this@AndroidApplication)
            modules(koinDataSourceModules)
            modules(koinDomainModules)
            modules(koinPresentationModules)
        }

        super.onCreate()

        // Init Debug log
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
