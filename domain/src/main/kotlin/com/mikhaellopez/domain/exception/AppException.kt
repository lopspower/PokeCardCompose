package com.mikhaellopez.domain.exception

sealed class AppException(message: String) : RuntimeException(message)

/**
 * Exception used when it is impossible to get data due to a lack of connection
 */
data object NoConnectedException : AppException("No connection") {
    private fun readResolve(): Any = NoConnectedException
}

/**
 * Exception used when persistence method return false on SingleUseCase
 */
data object PersistenceException : AppException("Data persistence error occurred") {
    private fun readResolve(): Any = PersistenceException
}
