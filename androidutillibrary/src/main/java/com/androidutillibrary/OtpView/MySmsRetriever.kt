package com.androidutillibrary.OtpView

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MySmsRetriever : BroadcastReceiver() {

    companion object {
        const val SMS_DATA = "mess"
        var SMSLOCALBROADCASTE = "smslocalbroadcaste"
    }


    override fun onReceive(context: Context?, intentt: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intentt?.action) {
            val extras = intentt.extras!!
            val status = extras.get(SmsRetriever.EXTRA_STATUS) as Status
            val intent = Intent(SMSLOCALBROADCASTE)
            // You can also include some extra data.
            intent.putExtra("extra", extras)
            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                    // message = message.replace("Your confidential One Time Password is ", "").split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[30]
                    intent.putExtra(SMS_DATA, message)
                }
                CommonStatusCodes.TIMEOUT -> {
                    intent.putExtra(SMS_DATA, "TimeOut Exception")
                }
                CommonStatusCodes.API_NOT_CONNECTED -> {
                    intent.putExtra(SMS_DATA, "Api Not Connected Exception")
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    intent.putExtra(SMS_DATA, "Please Check Your Network")
                }
                CommonStatusCodes.ERROR -> {
                    intent.putExtra(SMS_DATA, "SOME THING WENT WRONG")
                }
            }

            LocalBroadcastManager.getInstance(context!!).sendBroadcast(intent)
        }
    }

}