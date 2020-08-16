package com.example.androidutilsample

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.androidutillibrary.OtpView.OTPListener
import com.androidutillibrary.OtpView.OtpTextView

class OtpFetchActivity : AppCompatActivity() {
    private var otpView: OtpTextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_fetch)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.black)
        }
        val errorButton = findViewById<Button>(R.id.button)
        val successButton = findViewById<Button>(R.id.button2)
        otpView = findViewById(R.id.otp_view)
        otpView?.requestFocusOTP()
        otpView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                Toast.makeText(this@OtpFetchActivity, "The OTP is $otp", Toast.LENGTH_SHORT).show()
            }
        }
        errorButton.setOnClickListener { otpView?.showError() }
        successButton.setOnClickListener { otpView?.showSuccess() }
    }



}