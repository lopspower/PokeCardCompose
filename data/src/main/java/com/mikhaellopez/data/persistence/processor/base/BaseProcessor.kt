package com.mikhaellopez.data.persistence.processor.base

import com.mikhaellopez.data.persistence.dao.base.BaseDao
import com.mikhaellopez.domain.extensions.checkPersistenceResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseProcessor<T>(private val baseDao: BaseDao<T>) {

    fun insert(entity: T): Flow<Unit> =
        flow { emit(baseDao.insert(entity) > 0) }
            .checkPersistenceResult()

    fun delete(entity: T): Flow<Unit> =
        flow { emit(baseDao.delete(entity) > 0) }
            .checkPersistenceResult()
}
