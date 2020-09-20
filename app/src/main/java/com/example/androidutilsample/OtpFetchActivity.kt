package com.example.androidutilsample

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.androidutillibrary.OtpView.*
import com.androidutillibrary.Utils
import com.androidutillibrary.getOtpCode
import java.lang.Exception
import java.lang.ref.WeakReference

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
        val sig = AutoReadSmsUtil.getSmsAppSignature(WeakReference(this))
        Log.v("SMS_LIB","signature---->"+sig)
        AutoReadSmsUtil.setSmsListener(WeakReference(this))
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

    private val smsBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(MySmsRetriever.SMSLOCALBROADCASTE)){
                val data = intent?.getBundleExtra("extra")
                val mess  = intent?.getStringExtra(MySmsRetriever.SMS_DATA)
                otpView?.setOTP(mess?.getOtpCode(6)!!)
            }
        }
    }

    override fun onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(smsBroadcastReceiver, IntentFilter(MySmsRetriever.SMSLOCALBROADCASTE))
        super.onResume()
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(smsBroadcastReceiver)
        super.onPause()
    }

}