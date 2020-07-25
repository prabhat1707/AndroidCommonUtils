package `in`.androidUtil.library.phonenumberiutil

import `in`.androidUtil.library.phonenumberiutil.PhoneUtil.isContainPlus

class CCPCountry(
    var coutryCode: String,
    var phoneNumberCode: String,
    var countryName: String,
    var defaultFlagRes: Int
) {

    val phoneCode:String
        get() {
        return if (isContainPlus) "+$phoneNumberCode" else phoneNumberCode
    }


    var formattedNumber: String? = null

    inline fun setFormattedNumber(number: () -> String){
         formattedNumber = number()
    }



}