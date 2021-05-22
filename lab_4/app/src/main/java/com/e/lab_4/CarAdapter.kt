package com.e.lab_4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.lab_4.R
import com.e.lab_4.Car

class CarAdapter : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private var list = emptyList<Car>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_car,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val currentItem = list[position]

        holder.tvNumber.text = (position + 1).toString()
        holder.tvModel.text = currentItem.model
        holder.tvYear.text = currentItem.year
        DownloadImageFromInternet(holder.ivCar).execute(currentItem.imageURI)

    }

    override fun getItemCount() = list.size

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCar: ImageView = itemView.findViewById(R.id.ivCar)
        val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        val tvModel: TextView = itemView.findViewById(R.id.tvModel)
        val tvYear: TextView = itemView.findViewById(R.id.tvYear)
    }


    private inner class DownloadImageFromInternet(var imageView: ImageView) :
        AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }

    fun setData(cars: List<Car>) {
        this.list = cars
        notifyDataSetChanged()
    }
}