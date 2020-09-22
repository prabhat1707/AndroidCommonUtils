package com.androidutillibrary.otputil

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import java.lang.ref.WeakReference

object AutoReadSmsUtil {
    fun setSmsListener(activity: WeakReference<Activity>) {
        val smsReceiver = MySmsRetriever()
        activity.get()?.let {
            SmsRetrieverApi(
                WeakReference<Context>(it),
                object : SmsRetrieverApi.SmsRetrieverCallback {
                    override fun OnSmsFailureListener(message: String?) {
                        Log.d("OTPMESSFail", message + "chk")
                    }

                    override fun OnSmsSuccessListener(message: String) {
                        Log.d("OTPMESSSuccess", message + "chk")
                        if (it.isDestroyed || it.isFinishing) {
                            return
                        }
                        val intentFilter = IntentFilter()
                        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
                        it.registerReceiver(smsReceiver, intentFilter)
                    }
                })
        }

    }

    fun getSmsAppSignature(context: WeakReference<Context>): String {
        return AppSignatureHelper(context.get()).appSignatures.get(0)
    }


}