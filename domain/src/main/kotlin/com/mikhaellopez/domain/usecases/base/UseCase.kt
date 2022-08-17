package com.mikhaellopez.domain.usecases.base

import kotlin.coroutines.CoroutineContext

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 *
 * Abstract class for a Use Case
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each Use Case implementation will return the result using a [CoroutineContext]
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
abstract class UseCase<in P, R>(private val logger: Logger?) {

    /**
     * Builds which will be used when executing the current [UseCase].
     */
    protected abstract suspend fun build(param: P): R

    /**
     * Executes the current use case.
     */
    suspend fun execute(param: P): R = execute(param, false)

    protected open suspend fun execute(param: P, fromUseCase: Boolean): R {
        logger?.log(tag = "UseCase [${javaClass.simpleName}]") { "Param => $param" }
        return build(param)
    }
}
