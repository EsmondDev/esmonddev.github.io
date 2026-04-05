package com.w3code.library.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class Wave extends View {

    public int mWaveHeight;
    public int mAboveWaveColor;
    public int mBlowWaveColor;
    public float mWaveHz;
    public float mMaxRight;
    public float mBlowOffset;
    public float mWaveLength;
    public float mWaveMultiple;
    public float mAboveOffset = 0.0f;
    public final Path mBlowWavePath = new Path();
    public final Path mAboveWavePath = new Path();
    public final Paint mAboveWavePaint = new Paint();
    public final Paint mBlowWavePaint = new Paint();
    public final int DEFAULT_ABOVE_WAVE_ALPHA = 40;
    public final int DEFAULT_BLOW_WAVE_ALPHA = 60;
    public final float X_SPACE = 20;
    public RefreshProgressRunnable mRefreshProgressRunnable;
    public int mLeft, mRight, mBottom;
    public double mOmega;
    public Wave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mBlowWavePath, mBlowWavePaint);
        canvas.drawPath(mAboveWavePath, mAboveWavePaint);
    }
    public void setAboveWaveColor(int aboveWaveColor) {
        this.mAboveWaveColor = aboveWaveColor;
    }
    public void setBlowWaveColor(int blowWaveColor) {
        this.mBlowWaveColor = blowWaveColor;
    }
    public Paint getAboveWavePaint() {
        return mAboveWavePaint;
    }
    public Paint getBlowWavePaint() {
        return mBlowWavePaint;
    }
    public void initializeWaveSize(int waveMultiple, int waveHeight, int waveHz) {
        mWaveMultiple = getWaveMultiple(waveMultiple);
        mWaveHeight = getWaveHeight(waveHeight);
        mWaveHz = getWaveHz(waveHz);
        mBlowOffset = mWaveHeight * 0.4f;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                mWaveHeight * 2);
        setLayoutParams(params);
    }
    public void initializePainters() {
        mAboveWavePaint.setColor(mAboveWaveColor);
        mAboveWavePaint.setAlpha(DEFAULT_ABOVE_WAVE_ALPHA);
        mAboveWavePaint.setStyle(Paint.Style.FILL);
        mAboveWavePaint.setAntiAlias(true);
        mBlowWavePaint.setColor(mBlowWaveColor);
        mBlowWavePaint.setAlpha(DEFAULT_BLOW_WAVE_ALPHA);
        mBlowWavePaint.setStyle(Paint.Style.FILL);
        mBlowWavePaint.setAntiAlias(true);
    }
    public float getWaveMultiple(int size) {
        float WAVE_LENGTH_MULTIPLE_LARGE = 1.5f;
        float WAVE_LENGTH_MULTIPLE_MEDIUM = 1f;
        float WAVE_LENGTH_MULTIPLE_SMALL = 0.5f;
        switch (size) {
            case WaveView.LARGE:
                return WAVE_LENGTH_MULTIPLE_LARGE;
            case WaveView.MEDIUM:
                return WAVE_LENGTH_MULTIPLE_MEDIUM;
            case WaveView.SMALL:
                return WAVE_LENGTH_MULTIPLE_SMALL;
        }
        return 0;
    }
    public int getWaveHeight(int size) {
        int WAVE_HEIGHT_LARGE = 16;
        int WAVE_HEIGHT_MEDIUM = 8;
        int WAVE_HEIGHT_SMALL = 5;
        switch (size) {
            case WaveView.LARGE:
                return WAVE_HEIGHT_LARGE;
            case WaveView.MEDIUM:
                return WAVE_HEIGHT_MEDIUM;
            case WaveView.SMALL:
                return WAVE_HEIGHT_SMALL;
        }
        return 0;
    }
    public float getWaveHz(int size) {
        float WAVE_HZ_FAST = 0.13f;
        float WAVE_HZ_NORMAL = 0.09f;
        float WAVE_HZ_SLOW = 0.05f;
        switch (size) {
            case WaveView.LARGE:
                return WAVE_HZ_FAST;
            case WaveView.MEDIUM:
                return WAVE_HZ_NORMAL;
            case WaveView.SMALL:
                return WAVE_HZ_SLOW;
        }
        return 0;
    }
    public void calculatePath() {
        mAboveWavePath.reset();
        mBlowWavePath.reset();
        getWaveOffset();
        float y;
        mAboveWavePath.moveTo(mLeft, mBottom);
        for (float x = 0; x <= mMaxRight; x += X_SPACE) {
            y = (float) (mWaveHeight * Math.sin(mOmega * x + mAboveOffset) + mWaveHeight);
            mAboveWavePath.lineTo(x, y);
        }
        mAboveWavePath.lineTo(mRight, mBottom);
        mBlowWavePath.moveTo(mLeft, mBottom);
        for (float x = 0; x <= mMaxRight; x += X_SPACE) {
            y = (float) (mWaveHeight * Math.sin(mOmega * x + mBlowOffset) + mWaveHeight);
            mBlowWavePath.lineTo(x, y);
        }
        mBlowWavePath.lineTo(mRight, mBottom);
    }
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (View.GONE == visibility) {
            removeCallbacks(mRefreshProgressRunnable);
        } else {
            removeCallbacks(mRefreshProgressRunnable);
            mRefreshProgressRunnable = new RefreshProgressRunnable();
            post(mRefreshProgressRunnable);
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            if (mWaveLength == 0) {
                startWave();
            }
        }
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mWaveLength==0){
            startWave();
        }
    }
    public void startWave() {
        if (getWidth() != 0) {
            int width = getWidth();
            mWaveLength = width * mWaveMultiple;
            mLeft = getLeft();
            mRight = getRight();
            mBottom = getBottom() + 2;
            mMaxRight = mRight + X_SPACE;
            double PI2 = 2 * Math.PI;
            mOmega = PI2 / mWaveLength;
        }
    }
    public void getWaveOffset() {
        if (mBlowOffset > Float.MAX_VALUE - 100) {
            mBlowOffset = 0;
        } else {
            mBlowOffset += mWaveHz;
        }
        if (mAboveOffset > Float.MAX_VALUE - 100) {
            mAboveOffset = 0;
        } else {
            mAboveOffset += mWaveHz;
        }
    }
    public class RefreshProgressRunnable implements Runnable {
        public void run() {
            synchronized (Wave.this) {
                long start = System.currentTimeMillis();
                calculatePath();
                invalidate();
                long gap = 16 - (System.currentTimeMillis() - start);
                postDelayed(this, gap < 0 ? 0 : gap);
            }
        }
    }
}