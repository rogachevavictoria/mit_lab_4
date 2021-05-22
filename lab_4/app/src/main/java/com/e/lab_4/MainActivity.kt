package com.e.lab_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.lab_4.Car
import com.e.lab_4.CarAdapter
import com.e.lab_4.CarViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var carViewModel: CarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerViewer
        val adapter = CarAdapter()
        val rvCars: RecyclerView = findViewById(R.id.rvCars)
        rvCars.apply{
            this.adapter = adapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }


        //UserViewModel
        carViewModel = ViewModelProvider(this).get(CarViewModel::class.java)
        carViewModel.readAllData.observe(this, Observer {car ->
            adapter.setData(car)
        })

    }

    private fun populateList() {
        val list = ArrayList<Car>()

        list.add(Car( 0, "a","0000", "https://expeditionportal.com/media/2020/01/DSCF6074-1500x1000.jpg"))
        list.add(Car( 1, "b","1000", "https://www.autoguide.com/blog/wp-content/uploads/2018/03/international-hx620.jpg"))
        list.add(Car( 2, "c","2000", "https://static1.hotcarsimages.com/wordpress/wp-content/uploads/2020/03/ford-tonka.jpg"))

        for (car in list){
            carViewModel.addCar(car)
            Log.d("MAIN_ADD", "added $car")
        }
    }

}