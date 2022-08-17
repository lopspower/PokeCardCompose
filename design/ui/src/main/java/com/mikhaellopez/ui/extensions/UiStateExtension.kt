package com.mikhaellopez.ui.extensions

import com.mikhaellopez.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter

fun MutableStateFlow<UiState>.filterDefault() =
    filter { (it as? UiState.Loading)?.default == true }

inline fun <reified T> UiState.toContent(content: (T) -> Unit) {
    toContent<T>()?.also { content(it) }
}

inline fun <reified T> UiState.toContent(
    klass: Class<T> = T::class.java
): T? =
    if (this is UiState.Content<*> &&
        data?.let { data -> klass.isAssignableFrom(data.javaClass) } == true
    ) data as T else null

fun MutableStateFlow<UiState>.clearSnackMessage() {
    (this.value as? UiState.Content<*>)?.also {
        if (it.snackMessage != null) {
            this.value = it.copy(snackMessage = null)
        }
    }
}
