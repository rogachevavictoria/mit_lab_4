package com.e.lab_4

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Car::class], version = 2, exportSchema = false)
abstract class CarDataBase: RoomDatabase() {
    abstract fun carDao(): CarDao

    private class CarDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    Log.d("CARDATABASE","database created for the first time")
                    populateDatabase(database.carDao())
                }
            }
        }

        suspend fun populateDatabase(carDao: CarDao) {
            // Delete all content here.
            carDao.deleteAllCars()

            // Add cars
            carDao.addCar(Car( 1, "Chevrolet Colorado","2021", "https://cars.usnews.com/static/images/Auto/izmo/i159614300/2021_chevrolet_colorado_angularfront.jpg"))
            carDao.addCar(Car( 2, "GMC Canyon","2021", "https://cars.usnews.com/static/images/Auto/izmo/i159614247/2021_gmc_canyon_angularfront.jpg"))
            carDao.addCar(Car( 3, "Rod Hall","1990", "https://static0.hotcarsimages.com/wordpress/wp-content/uploads/2018/06/barn-finds.jpg?q=50&fit=crop&w=740&h=555&dpr=1.5"))
            carDao.addCar(Car( 4, "International CXT","2004", "https://static1.hotcarsimages.com/wordpress/wp-content/uploads/2018/06/wheelsage.org_-1.jpg?q=50&fit=crop&w=740&h=555&dpr=1.5"))
            carDao.addCar(Car( 5, "Jeep CJ-8","1981", "https://static3.hotcarsimages.com/wordpress/wp-content/uploads/2018/06/barrett-jackson.jpg-1.jpg?q=50&fit=crop&w=740&h=495&dpr=1.5"))
            carDao.addCar(Car( 6, "Nissan Hardboy","1986", "https://static3.hotcarsimages.com/wordpress/wp-content/uploads/2018/06/youtube-1-1.jpg?q=50&fit=crop&w=740&h=416&dpr=1.5"))
            carDao.addCar(Car( 7, "Toyota Hilux","1975", "https://static1.hotcarsimages.com/wordpress/wp-content/uploads/2018/06/classic-cars-from-uk-e1529088401164.jpg?q=50&fit=crop&w=740&h=420&dpr=1.5"))
            carDao.addCar(Car( 8, "Kaiser Jeep","1969 ", "https://static1.hotcarsimages.com/wordpress/wp-content/uploads/2018/06/curbside-classic.jpg?q=50&fit=crop&w=740&h=555&dpr=1.5"))
            carDao.addCar(Car( 9, "Jeep Gladiator","1963", "https://static0.hotcarsimages.com/wordpress/wp-content/uploads/2018/06/wikipedia-4.jpg?q=50&fit=crop&w=740&h=393&dpr=1.5"))
            carDao.addCar(Car( 10, "Ford Ranger","2021", "https://cars.usnews.com/static/images/Auto/izmo/i159614420/2021_ford_ranger_angularfront.jpg"))

        }
    }

    companion object{
        @Volatile
        private var INSTANCE: CarDataBase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): CarDataBase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, CarDataBase::class.java, "car_database"
                ).addMigrations(MIGRATION_1_2)
                    .addCallback(CarDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

    }

}