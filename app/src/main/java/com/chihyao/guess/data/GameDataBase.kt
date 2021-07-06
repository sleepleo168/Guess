package com.chihyao.guess.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Record::class, Word::class), version = 1)
abstract class GameDataBase : RoomDatabase() {
    abstract fun recordDao() : RecordDao
    companion object {
        private var instance : GameDataBase? = null
        fun getInstance(context: Context) : GameDataBase? {
            if (instance == null){
                instance = Room.databaseBuilder(context,
                    GameDataBase::class.java,
                    "game.db").build()
            }
            return instance
        }
    }
}