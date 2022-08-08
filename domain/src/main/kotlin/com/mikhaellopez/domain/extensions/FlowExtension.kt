package com.mikhaellopez.domain.extensions

import com.mikhaellopez.domain.exception.PersistenceException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

fun Flow<Boolean>.checkPersistenceResult(): Flow<Unit> =
    map { result ->
        if (result) Unit
        else throw PersistenceException
    }

fun <T> Flow<T?>.defaultIfEmpty(default: T): Flow<T> =
    map { value -> value ?: default }

@OptIn(FlowPreview::class)
fun <T> Flow<T?>.switchIfEmpty(flow: Flow<T>): Flow<T> =
    flatMapConcat { value ->
        if (value == null) flow
        else flowOf(value)
    }

fun <T> Flow<T?>.throwIfEmpty(throwable: Throwable): Flow<T> =
    map { value -> value ?: throw throwable }

fun <T> Flow<T>.onStartEmit(start: () -> T) = onStart { emit(start.invoke()) }

fun <T> List<T>.toFlow(): Flow<T> =
    flow { forEach { value: T -> emit(value) } }

fun <T> Flow<T>.catchEmit(error: (Throwable) -> T) =
    catch { throwable ->
        emit(error.invoke(throwable))
    }

fun <T> Flow<T>.catchDelay(delayMillis: Long = 1000, error: (Throwable) -> T) =
    catch { throwable ->
        delay(delayMillis)
        emit(error.invoke(throwable))
    }

fun <T> Flow<T>.catchLog(log: (Throwable) -> Unit) =
    catch { throwable ->
        log(throwable)
        throw throwable
    }

fun <T> Flow<T>.share(context: CoroutineContext): Flow<T> =
    shareIn(
        CoroutineScope(context),
        started = SharingStarted.WhileSubscribed(),
        replay = 1
    )

fun <T> Flow<T>.zipDelay(delayMillis: Long = 1000): Flow<T> =
    zip(flowOf(Unit).onEach { delay(delayMillis) }) { value, _ -> value }
