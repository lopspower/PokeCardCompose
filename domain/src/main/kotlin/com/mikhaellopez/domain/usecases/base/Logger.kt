package com.mikhaellopez.domain.usecases.base

/**
 * Copyright (C) 2022 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
interface Logger {
    fun log(tag: String, message: () -> String)
    fun logError(tag: String, throwable: () -> String)
}