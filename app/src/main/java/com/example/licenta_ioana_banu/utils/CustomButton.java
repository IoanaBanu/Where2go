package com.example.licenta_ioana_banu.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


public class CustomButton extends androidx.appcompat.widget.AppCompatButton {
    public CustomButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }



    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("ArbFONTS-Cairo-Light.ttf", context);
        setTypeface(customFont);
    }
}
