package com.example.androidutilsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button4.setOnClickListener {
            val i = Intent(it.context,PhoneUtilSample::class.java)
            startActivity(i)
        }

        button3.setOnClickListener {
            val i = Intent(it.context,OtpFetchActivity::class.java)
            startActivity(i)
        }


    }
}