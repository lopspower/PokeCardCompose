package com.mikhaellopez.data.persistence.processor

import com.mikhaellopez.data.persistence.dao.CardDao
import com.mikhaellopez.data.persistence.entity.CardEntity
import com.mikhaellopez.domain.exception.PersistenceException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CardProcessorTest {

    @Mock
    private lateinit var dao: CardDao

    private lateinit var processor: CardProcessor

    @Before
    fun setup() {
        processor = CardProcessor(dao)
    }

    @Test
    fun insertCard() = runTest {
        val rowID = 1L
        val entity = mock<CardEntity>()

        whenever(dao.insert(entity)).thenReturn(rowID)

        val flow = processor.insert(entity)
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == Unit)
    }

    @Test
    fun insertCardFail() = runTest {
        val entity = mock<CardEntity>()

        whenever(dao.insert(entity)).thenReturn(0L)

        val flow = processor.insert(entity)

        flow.catch { cause ->
            assert(cause is PersistenceException)
        }.collect()
    }

    @Test
    fun deleteCard() = runTest {
        val nbEntityDeleted = 1
        val entity = mock<CardEntity>()

        whenever(dao.delete(entity)).thenReturn(nbEntityDeleted)

        val flow = processor.delete(entity)
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == Unit)
    }

    @Test
    fun deleteCardFail() = runTest {
        val entity = mock<CardEntity>()

        whenever(dao.delete(entity)).thenReturn(0)

        val flow = processor.delete(entity)

        flow.catch { cause ->
            assert(cause is PersistenceException)
        }.collect()
    }

    @Test
    fun getCard() = runTest {
        val name = "Charizard"
        val entity = mock<CardEntity>()

        whenever(dao.get(name)).thenReturn(entity)

        val flow = processor.get(name)
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == entity)
    }

    @Test
    fun getCardEmpty() = runTest {
        val name = "Charizard"

        whenever(dao.get(name)).thenReturn(null)

        val flow = processor.get(name)
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == null)
    }

    @Test
    fun getListCard() = runTest {
        val cardList = mock<List<CardEntity>>()

        whenever(dao.getAll()).thenReturn(cardList)

        val flow = processor.getAll()
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == cardList)
    }

    @Test
    fun updateIsCheck() = runTest {
        val name = "Charizard"
        val isCheck = true
        val nbEntityUpdated = 1

        whenever(dao.updateIsCheck(name, isCheck)).thenReturn(nbEntityUpdated)

        val flow = processor.updateIsCheck(name, isCheck)
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == Unit)
    }

    @Test
    fun updateIsCheckFail() = runTest {
        val name = "Charizard"
        val isCheck = true

        whenever(dao.updateIsCheck(name, isCheck)).thenReturn(0)

        val flow = processor.updateIsCheck(name, isCheck)

        flow.catch { cause ->
            assert(cause is PersistenceException)
        }.collect()
    }
}
