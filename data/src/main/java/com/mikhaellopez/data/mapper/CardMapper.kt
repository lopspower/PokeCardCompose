package com.mikhaellopez.data.mapper

import com.mikhaellopez.data.net.dto.CardDTO
import com.mikhaellopez.data.persistence.entity.CardEntity
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.domain.model.RarityEnum
import com.mikhaellopez.domain.model.TypeEnum
import org.koin.core.annotation.Single

/**
 * Mapper class used to transform [CardDTO] or [CardEntity] (in the data layer) to [Card]
 * in the domain layer and vice versa.
 */
@Single
class CardMapper {

    //region DTO to MODEL
    /**
     * Transform a [CardDTO] into an [Card]
     * @param dto  Object to be transformed
     * @param isCheck if card is check
     * @return [Card] if valid [CardDTO]
     */
    fun transform(dto: CardDTO, isCheck: Boolean): Card =
        Card(
            dto.name,
            dto.frenchName,
            dto.japaneseName,
            TypeEnum.toTypeEnum(dto.type),
            RarityEnum.toRarityEnum(dto.rarity),
            dto.pokemonNumber,
            "https://lopspower.github.io/poke/${dto.picture}",
            dto.illustrator,
            "https://bulbapedia.bulbagarden.net/wiki/${dto.wikiLink}",
            dto.number,
            isCheck
        )

    /**
     * Transform a Collection of [CardDTO] into a List of [Card]
     * @param dtoCollection Object Collection to be transformed
     * @return list of [Card]
     */
    fun transformCollection(dtoCollection: Collection<CardDTO>): List<Card> =
        dtoCollection.map { transform(it, false) }
    //endregion

    //region ENTITY to MODEL
    /**
     * Transform a [CardEntity] into an [Card]
     * @param entity Object to be transformed
     * @return [Card] if valid [CardEntity]
     */
    fun transform(entity: CardEntity): Card =
        Card(
            entity.name,
            entity.frenchName,
            entity.japaneseName,
            TypeEnum.toTypeEnum(entity.type),
            RarityEnum.toRarityEnum(entity.rarity),
            entity.pokemonNumber,
            entity.picture,
            entity.illustrator,
            entity.wikiLink,
            entity.number,
            entity.isCheck
        )

    /**
     * Transform a Collection of [CardEntity] into a List of [Card].
     * @param entityCollection Object Collection to be transformed.
     * @return list of [Card]
     */
    fun transform(entityCollection: Collection<CardEntity>): List<Card> =
        entityCollection.map { transform(it) }
    //endregion

    //region MODEL to ENTITY
    /**
     * Transform a [Card] into an [CardEntity].
     * @param model Object to be transformed.
     * @return [Card] if valid [CardEntity] otherwise null.
     */
    fun transformToEntity(model: Card): CardEntity =
        CardEntity(
            model.name,
            model.frenchName,
            model.japaneseName,
            model.type.name,
            model.rarity.name,
            model.pokemonNumber,
            model.picture,
            model.illustrator,
            model.wikiLink,
            model.number,
            model.isCheck
        )

    /**
     * Transform a Collection of [Card] into a List of [CardEntity].
     * @param modelCollection Object Collection to be transformed.
     * @return list of [CardEntity]
     */
    fun transformToEntity(modelCollection: Collection<Card>): List<CardEntity> =
        modelCollection.map { transformToEntity(it) }
    //endregion

}