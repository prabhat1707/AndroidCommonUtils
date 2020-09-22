package `in`.androidUtil.library.phonenumberiutil

import `in`.androidUtil.library.phonenumberiutil.PhoneUtil.isContainPlus
import android.content.Context
import java.util.*

class ParsedNumberData(
    var coutryCode: String,
    var phoneNumberCode: String,
    var countryName: String,
    private var defaultFlagRes: Int
) {

    val phoneCode: String
        get() {
            return if (isContainPlus) "+$phoneNumberCode" else phoneNumberCode
        }


    var formattedNumber: String? = null

    inline fun setFormattedNumber(number: () -> String) {
        formattedNumber = number()
    }

    fun loadFlagByCode(context: Context): Int {

        try {
            this.defaultFlagRes = context.resources
                .getIdentifier(
                    "flag_" + this.coutryCode, "drawable",
                    context.packageName
                )
        } catch (e: Exception) {
            e.printStackTrace()
            context.resources
                .getIdentifier(
                    "flag_" + "IN".toLowerCase(Locale.ENGLISH), "drawable",
                    context.packageName
                )
        }
        return defaultFlagRes
    }


}