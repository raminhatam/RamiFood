package ir.hm.ramifood.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.hm.ramifood.Models.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class MyDatabase() : RoomDatabase() {

    abstract val foodDao: FoodDao

    // static mode
    companion object {

        private var database: MyDatabase? = null

        fun getDatabase(context: Context):MyDatabase {

            var instance = database

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "myDatabase.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return instance
        }

    }
}