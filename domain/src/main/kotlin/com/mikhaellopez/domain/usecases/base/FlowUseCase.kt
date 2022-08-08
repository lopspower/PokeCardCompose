package com.mikhaellopez.domain.usecases.base

import com.mikhaellopez.domain.extensions.catchLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
abstract class FlowUseCase<P, R : Any>
constructor(
    private val logger: Logger?
) : UseCase<P, Flow<R>>(logger) {

    override suspend fun execute(param: P, fromUseCase: Boolean): Flow<R> =
        super.execute(param, fromUseCase)
            .catchLog { logger?.logError { "UseCase Error => $it" } }
            .onEach { logger?.log { "UseCase [${javaClass.simpleName}] Result => $it" } }

}
