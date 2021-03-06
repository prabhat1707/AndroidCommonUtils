package `in`.androidUtil.library.phonenumberiutil

import com.androidutillibrary.phonenumberiutil.CountryList

object PhoneUtil {
    internal var isContainPlus: Boolean = false

    fun getFormatNumber(number: String?): ParsedNumberData? {
        number?.let { phoneNumber ->
            val trimNumber = phoneNumber.trim()
            if (trimNumber.isNotEmpty()) {
                isContainPlus = trimNumber.any { it.equals('+') }
                for (codeData in CountryList.getLibraryMasterCountriesEnglish()) {
                    if (trimNumber.contains(codeData.phoneCode)) {
                        return codeData.apply {
                            setFormattedNumber {
                                return@setFormattedNumber trimNumber.removePrefix(codeData.phoneCode)
                            }
                        }
                    }
                }
            } else {
                throw Exception("Number length not be less then zero")
            }
        } ?: kotlin.run {
            throw Exception("Number not be null")
        }
        return null
    }
}