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

import java.util.Arrays;

public class MuzicVisualizer extends View {

    private float viewHeight;

    private float viewWidth;

    private @ColorInt int mStartColor;

    private @ColorInt
    final int mEndColor;

    private Visualizer mVisualizer;

    private Paint mColPaint;

    private byte[] mBytes;

    private int mAudioSession = -1;

    private float mCenterY;

    private float mLineWidth;

    private float mLineSpace;

    private int mNumOfLine;

    private float mStartFirstLine;

    //The height of the line when value equal 0
    private final float mBaseLineHeight;

    private final byte[] mBaseBytes = new byte[128];

    private final byte[] testBytes = new byte[128];

    public MuzicVisualizer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mAudioSession = 0;
        mLineWidth = 10;
        mLineSpace = 20;
        mNumOfLine = 16;

        Arrays.fill(mBaseBytes, (byte) 128);

        initPaint();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MuzicVisualizer);

        mStartColor = a.getColor(R.styleable.MuzicVisualizer_visualizer_start_color, Color.RED);
        mEndColor = a.getColor(R.styleable.MuzicVisualizer_visualizer_end_color, Color.BLUE);
        mNumOfLine = a.getInt(R.styleable.MuzicVisualizer_visualizer_numOfLine, 16);
        mLineWidth = a.getDimension(R.styleable.MuzicVisualizer_visualizer_line_width, 10f);
        mLineSpace = a.getDimension(R.styleable.MuzicVisualizer_visualizer_line_space, 20f);
        mBaseLineHeight = a.getDimension(R.styleable.MuzicVisualizer_visualizer_default_line_width, mLineWidth);

        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec) + mBaseLineHeight;
        viewWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        mCenterY = viewHeight/ 2f;

        float widthOfAllLine = mNumOfLine * (mLineSpace + mLineWidth) - mLineSpace;

        mStartFirstLine = (viewWidth - widthOfAllLine) / 2f;

        setMeasuredDimension((int)Math.ceil(viewWidth), (int)Math.ceil(viewHeight));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAllCol(canvas);

    }

    private void initPaint(){
        mColPaint = new Paint();

        mColPaint.setAntiAlias(true);
        mColPaint.setColor(Color.WHITE);
    }

    private void setGradientPaint(float colHeight){
        Shader gradient = new LinearGradient(
                0,
                mCenterY - colHeight / 2f,
                0,
                mCenterY + colHeight / 2f,
                mStartColor,
                mEndColor,
                Shader.TileMode.CLAMP);
        mColPaint.setShader(gradient);
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

        float colHeight;
        int lineCounter = 0;

        if(mBytes == null){
            mBytes = mBaseBytes;
        }

        for (int i = 0; i < mBytes.length; i += 8) {
            colHeight = (1f - Math.abs(mBytes[i]) / (128.00f)) * (viewHeight - mBaseLineHeight) + mBaseLineHeight;
            drawSingleCol(canvas, mStartFirstLine + lineCounter * (mLineWidth + mLineSpace), colHeight);
            lineCounter++;
        }

    }

    private void drawSingleCol(Canvas canvas, float startLineX, float colHeight){
        setGradientPaint(colHeight);

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

    private byte[] getBaseBytesForTest(){
        for (int i = 0; i < mBaseBytes.length; i ++){
            mBaseBytes[i] = (byte) i;
        }
        return mBaseBytes;
    }

    @Override
    protected void onDetachedFromWindow() {
        stopVisualizer();
        super.onDetachedFromWindow();
    }
}
