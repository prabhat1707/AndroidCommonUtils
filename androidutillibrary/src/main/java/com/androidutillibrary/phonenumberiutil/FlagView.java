package com.androidutillibrary.phonenumberiutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.androidutillibrary.R;

public class FlagView extends LinearLayout {
    private ImageView mFlagImage;
    private TextView mPhoneCodeTv;
    public FlagView(Context context) {
        super(context);
        init(context,null);
    }

    public FlagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public FlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlagView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        @SuppressLint("CustomViewStyleable") TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FlagOptions, 0, 0);
        @SuppressWarnings("ResourceAsColor")
        int codeColor = a.getColor(R.styleable.FlagOptions_phoneCodeColor, android.R.color.black);
        boolean showCodeViewEnabled= a.getBoolean(R.styleable.FlagOptions_showCodeViewEnabled, false);
        boolean showFlagViewEnabled= a.getBoolean(R.styleable.FlagOptions_showFlagImageViewEnabled, false);
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.flag_view, this, true);
        mPhoneCodeTv = (TextView) getChildAt(1);
        mFlagImage = (ImageView) getChildAt(0);
        mPhoneCodeTv.setTextColor(codeColor);
        mPhoneCodeTv.setVisibility(showCodeViewEnabled?VISIBLE:GONE);
        mPhoneCodeTv.setVisibility(showFlagViewEnabled?VISIBLE:GONE);
    }

    public void setFlag(Drawable drawable){
        mFlagImage.setImageDrawable(drawable);
    }

    public void setCode(String code){
        mPhoneCodeTv.setText(code);
    }


}

