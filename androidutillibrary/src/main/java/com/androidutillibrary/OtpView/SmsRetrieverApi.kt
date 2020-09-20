package com.androidutillibrary.OtpView

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.lang.ref.WeakReference

class SmsRetrieverApi(var context: WeakReference<Context>, var smsCallback: SmsRetrieverCallback) {

    init {
        val client = context.get()?.let { SmsRetriever.getClient(it) }
        val task = client?.startSmsRetriever()

        task?.addOnSuccessListener {
            Log.v("SmsRetrieverApi", "success")
        }

        task?.addOnFailureListener {
            smsCallback.OnSmsFailureListener(it.message)
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            Log.v("SmsRetrieverApi", it.message!!)
        }
    }

    interface SmsRetrieverCallback {
        fun OnSmsSuccessListener(message: String)

        fun OnSmsFailureListener(message: String?)
    }
}