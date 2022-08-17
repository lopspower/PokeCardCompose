package com.mikhaellopez.data.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mikhaellopez.data.persistence.AppDatabase
import com.mikhaellopez.data.persistence.DatabaseFactoryTest
import com.mikhaellopez.data.persistence.entity.CardEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
open class CardDaoTest {

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database =
            DatabaseFactoryTest.getDatabase(InstrumentationRegistry.getInstrumentation().context)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insert() = runTest {
        val name = "Charizard"
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

        assert(database.cardDao().insert(entity) > 0)
        assert(database.cardDao().get(name).compareTo(entity))
    }

    @Test
    fun delete() = runTest {
        val name = "Charizard"
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

        // Insert
        assert(database.cardDao().insert(entity) > 0)
        assert(database.cardDao().get(name).compareTo(entity))

        // Delete
        assert(database.cardDao().delete(entity) == 1)
        assert(database.cardDao().get(name) == null)
    }

    @Test
    fun get() = runTest {
        // Check table is empty
        val name = "Charizard"
        assert(database.cardDao().get(name) == null)

        // Insert a card & select it
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

        assert(database.cardDao().insert(entity) > 0)
        assert(database.cardDao().get(name).compareTo(entity))
    }

    @Test
    fun getAll() = runTest {
        // Check table is empty
        assert(database.cardDao().getAll().isEmpty())

        // Insert two cards & select it
        val entity1 = CardEntity(
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
        val entity2 = CardEntity(
            "Bulbasaur",
            "Bulbizarre",
            "フシギダネ",
            "GRASS",
            "COMMON",
            1,
            "https://lopspower.github.io/poke/img/BaseSet1.webp",
            "Mitsuhiro Arita",
            "https://bulbapedia.bulbagarden.net/wiki/Bulbasaur_(Base_Set_44)",
            1,
            false
        )

        assert(database.cardDao().insert(entity1) > 0)
        assert(database.cardDao().insert(entity2) > 0)

        assert(
            database.cardDao().getAll().let {
                it.size == 2 &&
                    it[0].compareTo(entity1) &&
                    it[1].compareTo(entity2)
            }
        )
    }

    @Test
    fun updateIsCheck() = runTest {
        val name = "Charizard"
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

        // Insert & check isCheck == false
        assert(database.cardDao().insert(entity) > 0)
        assert(!database.cardDao().get(name)!!.isCheck)

        // Update & check isCheck == true
        assert(database.cardDao().updateIsCheck(name, !entity.isCheck) == 1)
        assert(database.cardDao().get(name)!!.isCheck)
    }

    private fun CardEntity?.compareTo(entity: CardEntity): Boolean =
        this?.run {
            name == entity.name &&
                frenchName == entity.frenchName &&
                japaneseName == entity.japaneseName &&
                type == entity.type &&
                rarity == entity.rarity &&
                pokemonNumber == entity.pokemonNumber &&
                picture == entity.picture &&
                illustrator == entity.illustrator &&
                wikiLink == entity.wikiLink &&
                number == entity.number &&
                isCheck == entity.isCheck
        } ?: false
}
