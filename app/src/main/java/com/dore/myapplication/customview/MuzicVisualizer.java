package com.dore.myapplication.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.dore.myapplication.R;

public class MuzicVisualizer extends View {

    private float maxHeight;

    private float maxWidth;

    private @ColorInt
    int mStartColor;

    private @ColorInt
    int mEndColor;

    private Visualizer mVisualizer;

    private Paint mColPaint;

    private byte[] mBytes;

    private int mAudioSession = -1;

    private float mCenterY;

    private float mLineWidth;

    private float mLineSpace;

    private int mNumOfLine;

    private float mStartFirstLine;

    public MuzicVisualizer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mAudioSession = 0;
        mLineWidth = 10;
        mLineSpace = 20;
        mNumOfLine = 16;

        initPaint();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MuzicVisualizer);

        mStartColor = a.getColor(R.styleable.MuzicVisualizer_visualizer_start_color, Color.RED);
        mEndColor = a.getColor(R.styleable.MuzicVisualizer_visualizer_end_color, Color.RED);
        mNumOfLine = a.getInt(R.styleable.MuzicVisualizer_visualizer_numOfLine, 16);
        mLineWidth = a.getDimension(R.styleable.MuzicVisualizer_visualizer_line_width, 10f);
        mLineSpace = a.getDimension(R.styleable.MuzicVisualizer_visualizer_line_space, 20f);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        maxHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        maxWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mCenterY = maxHeight/2f;

        float widthOfAllLine = mNumOfLine * (mLineSpace + mLineWidth) - mLineSpace;

        mStartFirstLine = (maxWidth - widthOfAllLine) / 2f;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAllCol(canvas);

    }

    private void initPaint(){
        Shader gradient = new LinearGradient(
                0,
                0,
                0,
                maxHeight,
                mStartColor,
                mEndColor,
                Shader.TileMode.CLAMP);

        mColPaint = new Paint();

        mColPaint.setAntiAlias(true);
//        mColPaint.setShader(gradient);
        mColPaint.setColor(Color.WHITE);
        mColPaint.setStrokeWidth(10);

    }

    public void setUpWithAudioSession(int audioSession){
        mAudioSession = audioSession;
        if(mVisualizer != null){
            mVisualizer.release();
            mVisualizer = null;
        }
        mVisualizer = new Visualizer(audioSession);
        if(mVisualizer.getEnabled()){
            mVisualizer.setEnabled(false);
        }
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                mBytes = waveform;
                invalidate();
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);
        mVisualizer.setEnabled(true);
    }

    private void drawAllCol(Canvas canvas){

        float colHeight = mLineWidth + 0f;
        int lineCounter = 0;

        if(mBytes == null){
            for (int i = 0; i < 16; i ++) {
                drawOneCol(canvas, mStartFirstLine + lineCounter * (mLineWidth + mLineSpace), colHeight);
                lineCounter++;
            }
        }else {
            for (int i = 0; i < mBytes.length; i += 8) {
                colHeight = (1f - Math.abs(mBytes[i]) / (128.00f)) * maxHeight + mLineWidth;
                drawOneCol(canvas, mStartFirstLine + lineCounter * (mLineWidth + mLineSpace), colHeight);
                lineCounter++;
            }
        }

    }

    private void drawOneCol(Canvas canvas, float startLineX, float colHeight){
        canvas.drawRoundRect(
                startLineX,
                mCenterY - colHeight / 2f,
                startLineX + mLineWidth,
                mCenterY + colHeight / 2f,
                mLineWidth, // radius x of line
                mLineWidth, // radius y of line
                mColPaint);
    }

    public void stopVisualizer(){
        if(mVisualizer != null) {
            mVisualizer.release();
            mVisualizer = null;
            Log.d("Visualizer", "stopped Visualizer" );
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stopVisualizer();
        super.onDetachedFromWindow();
    }
}

