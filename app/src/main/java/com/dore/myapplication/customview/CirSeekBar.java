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
import com.dore.myapplication.utilities.LogUtils;

public class CirSeekBar extends View {

    private final float mRadius;

    private Paint mPaintProgress;

    private Paint mPaintIndicator;

    private Paint mPaintBackGround;

    private final float mProgressWidth;

    private final float mBackgroundWidth;

    private @ColorInt
    final int mStartColor;

    private @ColorInt
    final int mEndColor;

    private @ColorInt
    final int mBackGroundColor;

    private float mPos;

    private float cx;

    private float cy;

    private float cInX;

    private float cInY;

    private float mMaxX;

    private float mMaxY;

    private RectF mProgressRing;

    private RectF mBackgroundRing;

    private final float mIndicatorRadius;

    private boolean isOnTouching = false;

    private OnChangeIndicatorPosition listener;

    @SuppressLint("CustomViewStyleable")
    public CirSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CirSeekBar);
        mProgressWidth = typedArray.getDimension(R.styleable.CirSeekBar_cir_progress_stroke_width, 10);
        mBackgroundWidth = typedArray.getDimension(R.styleable.CirSeekBar_cir_background_stroke_width, 5);

        mIndicatorRadius = mProgressWidth;
        mRadius = typedArray.getDimension(R.styleable.CirSeekBar_cir_seek_size, 50) / 2 - mProgressWidth / 2;
        mStartColor = typedArray.getColor(R.styleable.CirSeekBar_cir_seek_start_color, Color.BLUE);
        mEndColor = typedArray.getColor(R.styleable.CirSeekBar_cir_seek_end_color, Color.BLUE);
        mBackGroundColor = typedArray.getColor(R.styleable.CirSeekBar_cir_seek_background_color, Color.BLACK);

        initPaint();
        initBaseDimes();

        typedArray.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // always round up max size of view
        setMeasuredDimension((int)Math.ceil(mMaxX), (int)Math.ceil(mMaxY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paintBackGround(canvas);
        paintProgress(canvas);
        paintIndicator(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();
                float distanceTouchToCenter = (float) Math.sqrt((x-cx)*(x-cx) + (y-cy)*(y-cy));

                if(distanceTouchToCenter > mRadius - mIndicatorRadius * 2
                        && distanceTouchToCenter < mRadius + mIndicatorRadius * 2){
                    isOnTouching = true;
                    mPos = XYtoDegree(event.getX(), event.getY());
                    if(listener!= null){
                        listener.onChangingPos(mPos / 360 * 100);
                    }
                }else{
                    return true;
                }

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(isOnTouching && listener != null) {
                    mPos = XYtoDegree(event.getX(), event.getY());
                    listener.onChangingPos(mPos / 360 * 100);
                }
                break;

            }
            case MotionEvent.ACTION_UP: {
                if(isOnTouching) {
                    mPos = XYtoDegree(event.getX(), event.getY());
                    if (listener != null) {
                        listener.onChangedPos(mPos / 360 * 100);
                    }
                }
                isOnTouching = false;
                break;

            }
        }
        invalidate();
        return true;
    }

    private void initBaseDimes() {
        cx = mRadius + mProgressWidth / 2f + mIndicatorRadius;
        cy = mRadius + mProgressWidth / 2f + mIndicatorRadius;

        mMaxX = mRadius * 2 + mProgressWidth + 2 * mIndicatorRadius;
        mMaxY = mRadius * 2 + mProgressWidth + 2 * mIndicatorRadius;
    }

    private void initPaint() {
        float startProgressRect = mProgressWidth / 2f + mIndicatorRadius;
        float endProgressRect = mProgressWidth / 2f + 2 * mRadius + mIndicatorRadius;
        float startBackgroundRect = mBackgroundWidth / 2f + mIndicatorRadius;

        mProgressRing = new RectF(startProgressRect, startProgressRect, endProgressRect, endProgressRect);

        mBackgroundRing = new RectF(
                startBackgroundRect,
                startBackgroundRect,
                endProgressRect,
                endProgressRect);

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
        mPaintBackGround.setStrokeWidth(mBackgroundWidth);
        mPaintBackGround.setColor(mBackGroundColor);

        mPaintProgress = new Paint();
        mPaintProgress.setStyle(Paint.Style.STROKE);
        mPaintProgress.setStrokeWidth(mProgressWidth);
        mPaintProgress.setShader(gradient);
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setColor(Color.WHITE);

        mPaintIndicator = new Paint();
        mPaintIndicator.setShader(gradient);
        mPaintIndicator.setAntiAlias(true);
        mPaintIndicator.setColor(Color.WHITE);

    }

    private void paintBackGround(Canvas canvas) {
        canvas.drawArc(mBackgroundRing, 0, 360, false, mPaintBackGround);
    }

    private void paintProgress(Canvas canvas) {
        canvas.drawArc(mProgressRing, 270, mPos, false, mPaintProgress);
    }

    private void paintIndicator(Canvas canvas) {
        cInX = (float) (cx + Math.cos(Math.toRadians(mPos + 270)) * mRadius);
        cInY = (float) (cy + Math.sin(Math.toRadians(mPos + 270)) * mRadius);

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
