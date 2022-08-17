package com.mikhaellopez.ui.state

import androidx.compose.runtime.Immutable

@Immutable
sealed class UiState {
    data class Content<T>(
        val data: T,
        val isRefresh: Boolean = false,
        val snackMessage: String? = null
    ) : UiState() {
        companion object {
            fun <T> createRefresh(data: T): Content<T> =
                Content(data, isRefresh = true)

            fun <T> createSnack(data: T, message: String): Content<T> =
                Content(data, snackMessage = message)
        }

        override fun toString(): String =
            "UiState.Content(isRefresh=$isRefresh, snackMessage=$snackMessage, data=$data)"
    }

    data class Error(val message: String, val isLoading: Boolean = false) : UiState() {
        companion object {
            fun createRetry(message: String) = Error(message, isLoading = true)
        }

        override fun toString(): String =
            "UiState.Error(isLoading=$isLoading, message=$message)"
    }

    data class Loading(val default: Boolean = false) : UiState() {
        companion object {
            fun createDefault() = Loading(default = true)
        }

        override fun toString(): String =
            "UiState.Loading(default=$default)"
    }
}
