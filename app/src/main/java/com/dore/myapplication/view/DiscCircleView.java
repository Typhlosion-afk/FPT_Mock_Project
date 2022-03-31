package com.dore.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import java.lang.reflect.Array;

public class DiscCircleView extends androidx.appcompat.widget.AppCompatImageView {
    public DiscCircleView(Context context) {
        super(context);

    }

    public DiscCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Path p = new Path();
        float[] fs = {15.0f,15.0f,15.0f,15.0f,0.0f,0.0f,0.0f,0.0f};
        float width = this.getWidth();
        float height = this.getHeight();
//        p.addRoundRect(new RectF(0.0f,0.0f, width, height));
        canvas.clipPath(p);

        super.onDraw(canvas);
        float startX = this.getX();
        float startY = this.getY();
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);


    }
}
