package com.mikhaellopez.data.repository

import com.mikhaellopez.data.mapper.CardMapper
import com.mikhaellopez.data.net.NetworkChecker
import com.mikhaellopez.data.net.api.PokeApi
import com.mikhaellopez.data.net.dto.CardDTO
import com.mikhaellopez.data.persistence.entity.CardEntity
import com.mikhaellopez.data.persistence.processor.CardProcessor
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.model.RarityEnum
import com.mikhaellopez.domain.model.TypeEnum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
class CardDataRepositoryTest {

    @Mock
    private lateinit var api: PokeApi

    @Mock
    private lateinit var mapper: CardMapper

    @Mock
    private lateinit var processor: CardProcessor

    @Mock
    private lateinit var networkChecker: NetworkChecker

    private lateinit var repository: CardDataRepository

    // Parameters
    private val cardName = "Charizard"

    @Before
    fun setup() {
        repository = CardDataRepository(api, mapper, processor, networkChecker)
    }

    //region LIST CARD
    @Test
    fun getListCard() = runTest {
        val cardList = mock<List<Card>>()
        val cardListDTO = mock<List<CardDTO>>()

        whenever(api.getCardList()).thenReturn(cardListDTO)
        whenever(mapper.transformCollection(cardListDTO)).thenReturn(cardList)

        val flow = repository.getListCard()
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == cardList)
    }

    @Test
    fun getCacheListCard() = runTest {
        val cardList = mock<List<Card>>()
        val cardListEntities = mock<List<CardEntity>>()

        whenever(processor.getAll()).thenReturn(flowOf(cardListEntities))
        whenever(mapper.transform(cardListEntities)).thenReturn(cardList)

        val flow = repository.getCacheListCard()
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == cardList)
    }

    @Test
    fun saveListCard() = runTest {
        val card = Card(
            "Charizard",
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
        val cardEntity = CardEntity(
            card.name,
            card.frenchName,
            card.japaneseName,
            card.type.name,
            card.rarity.name,
            card.pokemonNumber,
            card.picture,
            card.illustrator,
            card.wikiLink,
            card.number,
            card.isCheck
        )
        val cardList = listOf(card)

        whenever(processor.get(card.name)).thenReturn(flowOf(cardEntity))
        whenever(mapper.transformToEntity(card)).thenReturn(cardEntity)
        whenever(processor.insert(cardEntity)).thenReturn(flowOf(Unit))

        val flow = repository.saveListCard(cardList)
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == Unit)
    }
    //endregion

    //region CARD
    @Test
    fun getCacheCard() = runTest {
        val card = mock<Card>()
        val cardEntity = mock<CardEntity>()

        whenever(processor.get(cardName)).thenReturn(flowOf(cardEntity))
        whenever(mapper.transform(cardEntity)).thenReturn(card)

        val flow = repository.getCacheCard(cardName)
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == card)
    }

    @Test
    fun saveCard() = runTest {
        val card = Card(
            "Charizard",
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
        val cardEntity = CardEntity(
            card.name,
            card.frenchName,
            card.japaneseName,
            card.type.name,
            card.rarity.name,
            card.pokemonNumber,
            card.picture,
            card.illustrator,
            card.wikiLink,
            card.number,
            card.isCheck
        )

        whenever(processor.get(card.name)).thenReturn(flowOf(cardEntity))
        whenever(mapper.transformToEntity(card)).thenReturn(cardEntity)
        whenever(processor.insert(cardEntity)).thenReturn(flowOf(Unit))

        val flow = repository.saveCard(card)
        val count = flow.count()
        val first = flow.first()

        assert(count == 1)
        assert(first == Unit)
    }
    //endregion
}
