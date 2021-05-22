package com.e.lab_4


import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("DELETE FROM car_table")
    suspend fun deleteAllCars()

    @Query("SELECT * FROM car_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Car>>

}