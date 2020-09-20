package com.androidutillibrary

import android.content.Context
import android.util.TypedValue
import java.util.regex.Matcher
import java.util.regex.Pattern

internal const val AndroidUtilLiB:String = "Android_Util_Lib_Logs"

object Utils {
    internal fun getPixels(context: Context, valueInDp: Int): Int {
        val r = context.resources
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp.toFloat(), r.displayMetrics)
        return px.toInt()
    }

    internal fun getPixels(context: Context, valueInDp: Float): Int {
        val r = context.resources
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, r.displayMetrics)
        return px.toInt()
    }

    internal fun getPixelsSp(context: Context, valueInSp: Int): Int {
        val r = context.resources
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, valueInSp.toFloat(), r.displayMetrics)
        return px.toInt()
    }

    internal fun getPixelsSp(context: Context, valueInSp: Float): Int {
        val r = context.resources
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, valueInSp, r.displayMetrics)
        return px.toInt()
    }


}



fun String.getOtpCode(lengthOfCode:Int):String{
    val pattern: Pattern = Pattern.compile("(\\d{$lengthOfCode})")
    val matcher: Matcher = pattern.matcher(this)
    var otp = ""
    if (matcher.find()) {
        matcher.group(0)?.let {
            otp = it
        }
    }
    return otp
}