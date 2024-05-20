package com.mikhaellopez.presentation.scenes.base

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.mikhaellopez.domain.extensions.catchDelay
import com.mikhaellopez.domain.extensions.catchEmit
import com.mikhaellopez.domain.extensions.onStartEmit
import com.mikhaellopez.domain.extensions.zipDelay
import com.mikhaellopez.domain.model.TestModeEnum
import com.mikhaellopez.domain.model.TestModeEnum.Companion.isTest
import com.mikhaellopez.presentation.exception.ErrorMessageFactory
import com.mikhaellopez.ui.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlin.coroutines.CoroutineContext

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 * Abstract class representing a ViewModel in a MVI pattern.
 */
abstract class BaseViewModel<in View : BaseUiStateView>(
    private val context: CoroutineContext,
    private val errorMessageFactory: ErrorMessageFactory
) : ViewModel() {

    private lateinit var scope: CoroutineScope

    @set:VisibleForTesting
    var testMode = TestModeEnum.NONE

    open fun attach(view: View) {
        scope = if (!testMode.isTest()) {
            CoroutineScope(Dispatchers.Main)
        } else {
            CoroutineScope(TestCoroutineScheduler())
        }
    }

    protected fun subscribeUiState(view: View, vararg flows: Flow<UiState>) {
        flows.forEach { flow ->
            flow.flowOn(context)
                .onEach { state ->
                    Log.d("Render", state.toString())
                    view.uiStateFlow.value = state
                }
                .launchIn(scope)
        }
    }

    private fun getErrorMessage(error: Throwable): String =
        errorMessageFactory.getError(error)

    private fun onError(error: Throwable): UiState =
        UiState.Error(getErrorMessage(error))

    public override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }

    //region Extensions
    protected inline fun <reified T : Any> Flow<UiState?>.filterData(
        klass: Class<T> = T::class.java
    ): Flow<T> =
        filter { it != null && it is UiState.Content<*> && !it.isRefresh }
            .map { it as UiState.Content<*> }
            .filter { it.data?.let { data -> klass.isAssignableFrom(data.javaClass) } ?: false }
            .map { it.data as T }

    protected fun Flow<UiState?>.filterError(): Flow<String> =
        filter { it != null && it is UiState.Error && !it.isLoading }
            .map { (it as UiState.Error).message }

    protected fun <T> Flow<T>.asResult(withLoading: Boolean = true): Flow<UiState> =
        map { UiState.Content(it) }
            .onStart<UiState> { if (withLoading) emit(UiState.Loading()) }
            .catchEmit { UiState.Error(getErrorMessage(it)) }

    protected fun <T> Flow<T>.asRefresh(previousData: T): Flow<UiState> =
        zipDelay()
            .map { UiState.Content(it) }
            .onStartEmit { UiState.Content.createRefresh(previousData) }
            .catchEmit { UiState.Content.createSnack(previousData, getErrorMessage(it)) }

    protected fun <T> Flow<T>.asRetry(previousErrorMessage: String): Flow<UiState> =
        map { UiState.Content(it) }
            .onStartEmit { UiState.Error.createRetry(previousErrorMessage) }
            .catchDelay { onError(it) }
    //endregion
}
