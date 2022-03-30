package com.dore.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

public class DiscCircleView extends androidx.appcompat.widget.AppCompatImageView {
    public DiscCircleView(Context context, Bitmap bm) {
        super(context);

    }

    public DiscCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public DiscCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
