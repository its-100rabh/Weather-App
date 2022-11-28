package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.view.View
import android.webkit.JsPromptResult
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import android.os.AsyncTask


class MainActivity2 : AppCompatActivity() {
    val CITY: String = "Noida,India"
    val API: String = "2fe15da856a18480b1a8803cba6ed439"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        weatherTask().execute()
        refreshApp()
}
    private fun refreshApp(){
        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipe.setOnRefreshListener{
            Toast.makeText(this ,"Page Refreshed", Toast.LENGTH_SHORT).show()
            swipe.isRefreshing = false
        }
    }

    inner class weatherTask() : AsyncTask<String, Void, String>()
    {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.load).visibility= View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.error).visibility = View.GONE
        }

        override fun doInBackground(vararg p0: String?): String? {
            var response:String?
            try{
                response=java.net.URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API")
                    .readText(Charsets.UTF_8)
            }
            catch (e:Exception)
            {
                response= null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt: Long = jsonObj.getLong("dt")
                val updatedAtText =
                    "Updated At : " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                        Date(updatedAt * 1000)
                    )
                val feelslike = main.getString("feels_like") + "°C"
                val tempMax = "Max Temp : " + main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise: Long = sys.getLong("sunrise")
                val sunset: Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name") + ", " + sys.getString("country")


                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text = updatedAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = feelslike
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                    Date(sunrise*10000)
                )
                findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity
                findViewById<ProgressBar>(R.id.load).visibility=View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility=View.VISIBLE
            }
            catch (e:Exception){
                findViewById<ProgressBar>(R.id.load).visibility=View.GONE
                findViewById<TextView>(R.id.error).visibility=View.VISIBLE
            }
        }
    }
}