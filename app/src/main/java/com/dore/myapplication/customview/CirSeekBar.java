package com.dore.myapplication.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.dore.myapplication.R;

public class CirSeekBar extends View {

    private final int mRadius;
    private Paint mPaintProgress;
    private Paint mPaintIndicator;
    private Paint mPaintBackGround;
    private final int mStrokeSize;
    private @ColorInt
    final int mStartColor;
    private @ColorInt
    final int mEndColor;
    private @ColorInt
    final int mBackGroundColor;
    private float mPos;
    private float cx;
    private float cy;
    private int mMaxX;
    private int mMaxY;
    private RectF mRing;
    private final int mIndicatorRadius;
    private boolean isOnTouching = false;
    private OnChangeIndicatorPosition listener;

    @SuppressLint("CustomViewStyleable")
    public CirSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CirSeekBar);
        mStrokeSize = a.getDimensionPixelSize(R.styleable.CirSeekBar_cir_seek_stroke, 10);
        mIndicatorRadius = mStrokeSize;
        mRadius = a.getDimensionPixelSize(R.styleable.CirSeekBar_cir_seek_size, 50) / 2 - mStrokeSize / 2;
        mStartColor = a.getColor(R.styleable.CirSeekBar_cir_seek_start_color, Color.BLUE);
        mEndColor = a.getColor(R.styleable.CirSeekBar_cir_seek_end_color, Color.BLUE);
        mBackGroundColor = a.getColor(R.styleable.CirSeekBar_cir_seek_background_color, Color.BLACK);

        initPaint();
        initBaseDimes();

        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paintBackGround(canvas);
        paintProgress(canvas);
        paintIndicator(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mMaxX, mMaxY);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isOnTouching = true;
                mPos = XYtoDegree(event.getX(), event.getY());
                listener.onChangingPos(mPos / 360 * 100);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mPos = XYtoDegree(event.getX(), event.getY());
                listener.onChangingPos(mPos / 360 * 100);
                break;
            }
            case MotionEvent.ACTION_UP: {
                mPos = XYtoDegree(event.getX(), event.getY());
                if (listener != null) {
                    listener.onChangedPos(mPos / 360 * 100);
                }
                isOnTouching = false;
                break;
            }

        }
        invalidate();
        return true;
    }

    private void initBaseDimes() {
        cx = mRadius + mStrokeSize / 2f + mIndicatorRadius;
        cy = mRadius + mStrokeSize / 2f + mIndicatorRadius;

        mMaxX = mRadius * 2 + mStrokeSize + 2 * mIndicatorRadius;
        mMaxY = mRadius * 2 + mStrokeSize + 2 * mIndicatorRadius;
    }

    private void initPaint() {
        float startRect = mStrokeSize / 2f + mIndicatorRadius;
        float endRect = mStrokeSize / 2f + 2 * mRadius + mIndicatorRadius;
        mRing = new RectF(startRect, startRect, endRect, endRect);

        Shader gradient = new LinearGradient(
                0,
                0,
                0,
                2 * mRadius,
                mStartColor,
                mEndColor, Shader.TileMode.CLAMP);

        mPaintBackGround = new Paint();
        mPaintBackGround.setAntiAlias(true);
        mPaintBackGround.setStyle(Paint.Style.STROKE);
        mPaintBackGround.setStrokeWidth(mStrokeSize);
        mPaintBackGround.setColor(mBackGroundColor);

        mPaintProgress = new Paint();
        mPaintProgress.setStyle(Paint.Style.STROKE);
        mPaintProgress.setStrokeWidth(mStrokeSize);
        mPaintProgress.setShader(gradient);
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setColor(Color.WHITE);

        mPaintIndicator = new Paint();
        mPaintIndicator.setShader(gradient);
        mPaintIndicator.setAntiAlias(true);
        mPaintIndicator.setColor(Color.WHITE);

    }

    private void paintBackGround(Canvas canvas) {
        canvas.drawArc(mRing, 0, 360, false, mPaintBackGround);

    }

    private void paintProgress(Canvas canvas) {
        canvas.drawArc(mRing, 270, mPos, false, mPaintProgress);
    }

    private void paintIndicator(Canvas canvas) {

        float cInX = (float) (cx + Math.cos(Math.toRadians(mPos + 270)) * mRadius);
        float cInY = (float) (cy + Math.sin(Math.toRadians(mPos + 270)) * mRadius);

        canvas.drawCircle(cInX, cInY, mIndicatorRadius, mPaintIndicator);
    }


    private float XYtoDegree(float touchX, float touchY) {
        float x = cx - touchX;
        float y = cy - touchY;

        double v = Math.toDegrees(Math.atan(y / x));
        return (float) (x > 0 ? v + 270 : v + 90);
    }

    public void onChangeIndicatorPosition(OnChangeIndicatorPosition listener) {
        this.listener = listener;
    }

    public void setProgress(float pos) {
        if (!isOnTouching) {
            this.mPos = pos / 100 * 360;
            invalidate();
        }
    }

    public interface OnChangeIndicatorPosition {
        void onChangingPos(float percent);
        void onChangedPos(float percent);
    }

}
