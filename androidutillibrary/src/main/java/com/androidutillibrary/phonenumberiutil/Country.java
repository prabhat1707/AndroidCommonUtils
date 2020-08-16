package com.androidutillibrary.phonenumberiutil;

import android.content.Context;
import android.text.TextUtils;

import java.util.Locale;

public class Country {

    public String code;
    public String name;
    public String dial_code;
    public int flag;


    public Country(String code, String name, String dial_code, int flag, String currency) {
        this.code = code;
        this.name = name;
        this.dial_code = dial_code;
        this.flag = flag;


    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        if (TextUtils.isEmpty(name)) {
            name = new Locale("", code).getDisplayName();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDial_code() {
        return dial_code;
    }

    public void setDial_code(String dial_code) {
        this.dial_code = dial_code;
    }



    public int loadFlagByCode(Context context) {

        try {
            this.flag = context.getResources()
                    .getIdentifier("flag_" + this.code.toLowerCase(Locale.ENGLISH), "drawable",
                            context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            context.getResources()
                    .getIdentifier("flag_" + "IN".toLowerCase(Locale.ENGLISH), "drawable",
                            context.getPackageName());
        }

        return  flag;




    }
}
