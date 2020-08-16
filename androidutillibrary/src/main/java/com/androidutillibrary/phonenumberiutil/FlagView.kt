package com.androidutillibrary.phonenumberiutil

import `in`.androidUtil.library.phonenumberiutil.CCPCountry
import `in`.androidUtil.library.phonenumberiutil.PhoneUtil
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.telephony.PhoneNumberUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.androidutillibrary.R
import java.lang.Exception

class FlagView : LinearLayout {
    private var mFlagImage: ImageView? = null
    private var mPhoneCodeTv: TextView? = null
    internal var isContainPlus:Boolean = false;
    private lateinit var mContext:Context;


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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        mContext = context;
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
        mPhoneCodeTv = getChildAt(0) as TextView
        mFlagImage = getChildAt(1) as ImageView
        mPhoneCodeTv!!.setTextColor(codeColor)
        mFlagImage?.setImageDrawable(resources.getDrawable(R.drawable.flag_in))
        mPhoneCodeTv!!.visibility = if (showCodeViewEnabled) View.VISIBLE else View.GONE
        mFlagImage!!.visibility = if (showFlagViewEnabled) View.VISIBLE else View.GONE
    }

    fun setFlag(id: Int) {
        mFlagImage!!.setImageDrawable(resources.getDrawable(id))
    }

    fun setCode(code: String?) {
        mPhoneCodeTv!!.text = code
    }


    public fun getFormatNumber(number: String?): CCPCountry? {
        number?.let {phoneNumber ->
            val trimNumber = phoneNumber.trim()
            if (trimNumber.isNotEmpty()){
                PhoneUtil.isContainPlus = trimNumber.any {it.equals('+')}
                for (codeData in CountryList.getLibraryMasterCountriesEnglish()){
                    if (trimNumber.contains(codeData.phoneCode)){
                        return codeData.apply {
                            setCode(codeData.phoneCode)
                            setFlag(codeData.loadFlagByCode(mContext))
                            setFormattedNumber {
                                return@setFormattedNumber trimNumber.removePrefix(codeData.phoneCode)
                            }
                        }
                    }
                }
            }else{
                throw Exception("Number length not be less then zero")
            }
        }?: kotlin.run {
            throw Exception("Number not be null")
        }
        return null
    }


}