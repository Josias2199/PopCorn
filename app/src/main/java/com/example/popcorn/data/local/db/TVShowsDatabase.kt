package com.example.popcorn.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.popcorn.data.local.dao.TVShowDao
import com.example.popcorn.data.local.entity.TVShow
import kotlinx.coroutines.internal.synchronized

@Database(entities = arrayOf(TVShow::class), version =   1, exportSchema = false)
public abstract class TVShowsDatabase: RoomDatabase() {
    abstract fun tvShowDao(): TVShowDao

    companion object{
        @Volatile
        private var INSTANCE: TVShowsDatabase? = null


        fun getTvShowDatabase(context: Context): TVShowsDatabase{
            return INSTANCE ?: kotlin.synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    TVShowsDatabase::class.java,
                    "tv_show_db")
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }

        }
    }


}