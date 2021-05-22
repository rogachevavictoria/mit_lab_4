package com.e.lab_4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.e.lab_4.Car
import com.e.lab_4.CarDataBase
import com.e.lab_4.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Car>>
    private val repository: CarRepository

    init {
        val carDao = CarDataBase.getDatabase(application, viewModelScope).carDao()
        repository = CarRepository(carDao)
        readAllData = repository.readAllData
    }

    fun addCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCar(car)
        }
    }

    fun deleteAllCars() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCars()
        }
    }
}