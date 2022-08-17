package com.mikhaellopez.presentation.scenes.base

import com.mikhaellopez.ui.state.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

abstract class BaseViewModelTest {

    protected fun await(millis: Long = 100) {
        Thread.sleep(millis)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun TestScope.launchTest(
        view: BaseUiStateView
    ): Pair<List<UiState>, Job> {
        val results = mutableListOf<UiState>()
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            view.uiStateFlow.toList(results)
        }
        return results to job
    }
}
