package com.mikhaellopez.data.mapper

import com.mikhaellopez.data.net.dto.CardDTO
import com.mikhaellopez.data.persistence.entity.CardEntity
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.model.RarityEnum
import com.mikhaellopez.domain.model.TypeEnum
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CardMapperTest {

    private lateinit var mapper: CardMapper

    @Before
    fun setup() {
        mapper = CardMapper()
    }

    //region DTO to MODEL
    @Test
    fun dtoToModel() {
        val dto = CardDTO(
            "Charizard",
            "Dracaufeu",
            "リザードン",
            "FIRE",
            "RARE_HOLO",
            6,
            "img/BaseSet16.webp",
            "Mitsuhiro Arita",
            "Charizard_(Base_Set_4)",
            16
        )
        val model = mapper.transform(dto, false)

        model.compareTo(dto)
    }

    @Test
    fun dtoCollectionToModelList() {
        val dtoCollection = listOf(
            CardDTO(
                "Charizard",
                "Dracaufeu",
                "リザードン",
                "FIRE",
                "RARE_HOLO",
                6,
                "img/BaseSet16.webp",
                "Mitsuhiro Arita",
                "Charizard_(Base_Set_4)",
                16
            )
        )
        val models = mapper.transformCollection(dtoCollection)

        assertNotNull(models)
        assertTrue(models.size == 1)

        val dto = dtoCollection[0]
        val model = models[0]

        model.compareTo(dto)
    }

    private fun Card.compareTo(dto: CardDTO) {
        assertNotNull(this)
        assertEquals(dto.name, name)
        assertEquals(dto.frenchName, frenchName)
        assertEquals(dto.japaneseName, japaneseName)
        assertEquals(dto.type, type.name)
        assertEquals(dto.rarity, rarity.name)
        assertEquals(dto.pokemonNumber, pokemonNumber)
        assertEquals("https://lopspower.github.io/poke/${dto.picture}", picture)
        assertEquals(dto.illustrator, illustrator)
        assertEquals("https://bulbapedia.bulbagarden.net/wiki/${dto.wikiLink}", wikiLink)
        assertEquals(dto.number, number)
        assertFalse(isCheck)
    }
    //endregion

    //region ENTITY to MODEL
    @Test
    fun entityToModel() {
        val entity = CardEntity(
            "Charizard",
            "Dracaufeu",
            "リザードン",
            "FIRE",
            "RARE_HOLO",
            6,
            "https://lopspower.github.io/poke/img/BaseSet16.webp",
            "Mitsuhiro Arita",
            "https://bulbapedia.bulbagarden.net/wiki/Charizard_(Base_Set_4)",
            16,
            false
        )

        val model = mapper.transform(entity)

        model.compareTo(entity)
    }

    @Test
    fun entityCollectionToModelList() {
        val entities = listOf(
            CardEntity(
                "Charizard",
                "Dracaufeu",
                "リザードン",
                "FIRE",
                "RARE_HOLO",
                6,
                "https://lopspower.github.io/poke/img/BaseSet16.webp",
                "Mitsuhiro Arita",
                "https://bulbapedia.bulbagarden.net/wiki/Charizard_(Base_Set_4)",
                16,
                false
            )
        )
        val models = mapper.transform(entities)

        assertNotNull(models)
        assertTrue(models.size == 1)

        val entity = entities[0]
        val model = models[0]

        model.compareTo(entity)
    }

    private fun Card.compareTo(entity: CardEntity) {
        assertNotNull(this)
        assertEquals(entity.name, name)
        assertEquals(entity.frenchName, frenchName)
        assertEquals(entity.japaneseName, japaneseName)
        assertEquals(entity.type, type.name)
        assertEquals(entity.rarity, rarity.name)
        assertEquals(entity.pokemonNumber, pokemonNumber)
        assertEquals(entity.picture, picture)
        assertEquals(entity.illustrator, illustrator)
        assertEquals(entity.wikiLink, wikiLink)
        assertEquals(entity.number, number)
        assertEquals(entity.isCheck, isCheck)
    }
    //endregion

    //region MODEL to ENTITY
    @Test
    fun modelToEntity() {
        val model = Card(
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
            false
        )
        val entity = mapper.transformToEntity(model)

        entity.compareTo(model)
    }

    @Test
    fun modelCollectionToEntityList() {
        val models = listOf(
            Card(
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
                false
            )
        )
        val entities = mapper.transformToEntity(models)

        assertNotNull(entities)
        assertTrue(entities.size == 1)

        val model = models[0]
        val entity = entities[0]

        entity.compareTo(model)
    }

    private fun CardEntity.compareTo(model: Card) {
        assertNotNull(this)
        assertEquals(model.name, name)
        assertEquals(model.frenchName, frenchName)
        assertEquals(model.japaneseName, japaneseName)
        assertEquals(model.type.name, type)
        assertEquals(model.rarity.name, rarity)
        assertEquals(model.pokemonNumber, pokemonNumber)
        assertEquals(model.picture, picture)
        assertEquals(model.illustrator, illustrator)
        assertEquals(model.wikiLink, wikiLink)
        assertEquals(model.number, number)
        assertEquals(model.isCheck, isCheck)
    }
    //endregion
}
