package com.sorrytale.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Notifications.createNotificationChannel(this)
        findViewById<Button>(R.id.launch).setOnClickListener {
            val forecast = findViewById<EditText>(R.id.forecast).text.toString()
            Notifications.sendStaticNotification(
                this,
                "Current Weather",
                forecast,
                100,
                R.drawable.ic_weather
            )
        }
    }
}
