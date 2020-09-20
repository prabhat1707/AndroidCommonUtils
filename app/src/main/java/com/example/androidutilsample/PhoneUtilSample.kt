package com.example.androidutilsample

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidutillibrary.phonenumberiutil.CountryPicker
import kotlinx.android.synthetic.main.activity_phone_util_sample.*

class PhoneUtilSample : AppCompatActivity() {
    var number = "+910123456789"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_util_sample)

        flag_view.setCountryPickerListener(CountryPicker.OnCountryPickerListener { country ->
            Toast.makeText(this,country.phoneCode +","+country.countryName,Toast.LENGTH_SHORT).show()
        })


        no_of_test.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    fetchData()
                    handled = true
                }
                return handled
            }

        })

    }

    private fun fetchData() {
        flag_view.getFormatNumber(no_of_test.text.trim().toString())?.apply {
            code.text = "Phone Code is " + phoneCode
            phone_number.text = "Phone Number is " + formattedNumber
            editTextTextPersonNumber.setText(formattedNumber)
        }
    }
}