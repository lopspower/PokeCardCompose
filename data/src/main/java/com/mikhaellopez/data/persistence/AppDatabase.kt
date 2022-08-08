package com.mikhaellopez.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mikhaellopez.data.persistence.dao.CardDao
import com.mikhaellopez.data.persistence.entity.CardEntity

@Database(entities = [(CardEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

}
