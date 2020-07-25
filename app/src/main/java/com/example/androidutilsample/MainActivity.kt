package com.example.androidutilsample

import `in`.androidUtil.library.phonenumberiutil.PhoneUtil
import `in`.androidUtil.library.phonenumberiutil.CCPCountry
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var number = "+919716932279"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editTextTextPersonNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 5){
                    PhoneUtil.getFormatNumber(s.toString())?.apply {
                        code.text = phoneCode
                        phone_number.text = formattedNumber
                    }
                }else{
                    code.text = "Phone Code"
                    phone_number.text = "Formatted Number"
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun formatNumber(number: String, codee: CCPCountry):String{
        return number.removePrefix(codee.phoneCode)
    }
}