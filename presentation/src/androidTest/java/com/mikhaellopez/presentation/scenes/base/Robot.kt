package com.mikhaellopez.presentation.scenes.base

import com.mikhaellopez.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow

abstract class Robot(private val uiStateFlow: MutableStateFlow<UiState>) {

    protected fun <T> setUiState(currentClass: T, uiState: UiState, func: T.() -> Unit) =
        apply {
            this.uiStateFlow.value = uiState
            currentClass.func()
        }

}