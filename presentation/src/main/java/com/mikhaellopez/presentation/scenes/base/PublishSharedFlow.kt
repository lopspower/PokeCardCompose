package com.mikhaellopez.presentation.scenes.base

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

object PublishSharedFlow {

    fun <T> create(): MutableSharedFlow<T> =
        MutableSharedFlow(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
}
