package com.e.lab_4

import androidx.lifecycle.LiveData
import com.e.lab_4.Car
import com.e.lab_4.CarDao

class CarRepository(private val carDao: CarDao) {
    val readAllData: LiveData<List<Car>> = carDao.readAllData()

    suspend fun addCar(car: Car) {
        carDao.addCar(car)
    }

    suspend fun deleteAllCars() {
        carDao.deleteAllCars()
    }
}