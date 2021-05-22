package com.e.lab_4
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_table")
data class Car(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var model: String,
    var year: String,
    var imageURI: String?
)