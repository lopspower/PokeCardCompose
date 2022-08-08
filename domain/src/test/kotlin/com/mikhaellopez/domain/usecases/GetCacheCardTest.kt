package com.mikhaellopez.domain.usecases

import com.mikhaellopez.domain.exception.PersistenceException
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.model.RarityEnum
import com.mikhaellopez.domain.model.TypeEnum
import com.mikhaellopez.domain.repository.CardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetCacheCardTest {

    @Mock
    private lateinit var repository: CardRepository

    // Properties
    private val cardName: String = "Charizard"
    private val card =
        Card(
            cardName,
            "Dracaufeu",
            "リザードン",
            TypeEnum.FIRE,
            RarityEnum.RARE_HOLO,
            6,
            "https://lopspower.github.io/poke/img/BaseSet16.webp",
            "Mitsuhiro Arita",
            "https://bulbapedia.bulbagarden.net/wiki/Charizard_(Base_Set_4)",
            16,
            true
        )

    // Use Case
    private val useCase by lazy { GetCacheCard(repository) }

    @Test
    fun buildUseCase() = runTest {
        whenever(repository.getCacheCard(card.name)).thenReturn(flowOf(card))

        val flow = useCase.execute(cardName)
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == card)
    }

    @Test
    fun buildUseCaseWithPersistenceException() = runTest {
        whenever(repository.getCacheCard(card.name)).thenReturn(emptyFlow())

        val flow = useCase.execute(cardName)

        flow.catch { cause ->
            assert(cause is PersistenceException)
        }.collect()
    }

}
