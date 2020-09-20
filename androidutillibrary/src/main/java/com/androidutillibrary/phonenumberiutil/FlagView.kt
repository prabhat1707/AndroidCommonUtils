package com.androidutillibrary.phonenumberiutil

import `in`.androidUtil.library.phonenumberiutil.CCPCountry
import `in`.androidUtil.library.phonenumberiutil.PhoneUtil
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.androidutillibrary.AndroidUtilLiB
import com.androidutillibrary.R

class FlagView : LinearLayout {
    private var mFlagImage: ImageView? = null
    private var mPhoneCodeTv: TextView? = null
    internal var isContainPlus: Boolean = false
    private lateinit var mContext: Context
    private var countryPickerListener: CountryPicker.OnCountryPickerListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        mContext = context
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        @SuppressLint("CustomViewStyleable") val a =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.FlagOptions, 0, 0
            )
        val codeColor = a.getColor(
            R.styleable.FlagOptions_phoneCodeColor,
            resources.getColor(android.R.color.black)
        )
        val showCodeViewEnabled =
            a.getBoolean(R.styleable.FlagOptions_showCodeViewEnabled, true)

        val showFlagViewEnabled = a.getBoolean(
            R.styleable.FlagOptions_showFlagImageViewEnabled,
            true
        )
        a.recycle()
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.flag_view, this, true)
        var locale: String = ""
        var mCountry: Country = Country("IN", "India", "+91", R.drawable.flag_in, "INR")
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = context.resources.configuration.locales[0].country
            } else {
                locale = context.resources.configuration.locale.country
            }
            for (country in CountryList.getLibraryMasterCountriesEnglish()) {
                if (country.coutryCode.equals(locale, true)) {
                    val flag = country.loadFlagByCode(context)
                    mCountry = Country(locale, country.countryName, country.phoneCode, flag, "")
                }
            }
        } catch (e: java.lang.Exception) {
            e.message?.let { Log.e(AndroidUtilLiB, it) }
        }

        mPhoneCodeTv = getChildAt(1) as TextView
        mFlagImage = getChildAt(0) as ImageView
        mPhoneCodeTv?.setTextColor(codeColor)
        mPhoneCodeTv?.text = "+" + mCountry.dial_code
        mFlagImage?.setImageDrawable(resources.getDrawable(mCountry.flag))
        mPhoneCodeTv?.visibility = if (showCodeViewEnabled) View.VISIBLE else View.GONE
        mFlagImage?.visibility = if (showFlagViewEnabled) View.VISIBLE else View.GONE

        this.setOnClickListener {
            countryPickerListener?.let {
                CountryPicker(context, mCountry,
                    CountryPicker.OnCountryPickerListener { country ->
                        val flag = country.loadFlagByCode(
                            context
                        )
                        mFlagImage?.setImageDrawable(
                            resources.getDrawable(
                                flag
                            )
                        )
                        mPhoneCodeTv?.text = "+${country.phoneNumberCode}"
                        countryPickerListener?.onSelectCountry(country)
                        mCountry = Country(
                            country.coutryCode,
                            country.countryName,
                            country.phoneCode,
                            flag,
                            ""
                        )
                    })
            } ?: kotlin.run {
                throw java.lang.Exception("OnCountryPickerListener is NUll")
            }
        }
    }

    fun setFlag(id: Int) {
        mFlagImage?.setImageDrawable(resources.getDrawable(id))
    }

    fun setCode(code: String?) {
        mPhoneCodeTv!!.text = code
    }


    fun getFormatNumber(number: String?): CCPCountry? {
        number?.let { phoneNumber ->
            val trimNumber = phoneNumber.trim()
            if (trimNumber.isNotEmpty()) {
                PhoneUtil.isContainPlus = trimNumber.any { it.equals('+') }
                val tempNUmber = "+$trimNumber"
                for (codeData in CountryList.getLibraryMasterCountriesEnglish()) {
                    if (tempNUmber.contains("+" + codeData.phoneCode)) {
                        return codeData.apply {
                            setCode("+" + codeData.phoneNumberCode)
                            setFlag(codeData.loadFlagByCode(mContext))
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

    fun setCountryPickerListener(lis: CountryPicker.OnCountryPickerListener) {
        this.countryPickerListener = lis
    }


}