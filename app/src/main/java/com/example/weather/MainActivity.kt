package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val categorybutton = findViewById<Button>(R.id.button)
        categorybutton.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))}
    }
}