package com.w3code.library.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class Circular extends View {

    public String mDigits;
    public Paint mPaintDigits;
    public Paint mPaintProgress;
    public Paint mPaintBackground;
    public Paint mPaintProgressBackground;
    public int mProgressDigitsColor = Color.BLACK;
    public int mProgressBackgroundColor;
    public int mBackgroundColor;
    public int mProgressColor;
    public int mStartAngle;
    public float mRadius;
    public float mDrawUpTo = 0;
    public float mTextSize = 90;
    public final float mMaxValue = 100;
    public final RectF mArcBounds = new RectF();
    public CountDownTimer mCountDownTimer;
    public Boolean isClockWise = true;
    public Circular(Context context) {
        super(context);
    }
    public Circular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }
    public Circular(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPaints();
    }
    public void initPaints() {
        mPaintProgress = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintProgress.setStyle(Paint.Style.FILL);
        mPaintProgress.setColor(mProgressColor);
        mPaintProgress.setStyle(Paint.Style.STROKE);
        mPaintProgress.setStrokeWidth(10 * getResources().getDisplayMetrics().density);
        mPaintProgress.setStrokeCap(Paint.Cap.BUTT);
        String pc = String.format("#%06X", (0xFFFFFF & mProgressColor));
        mPaintProgress.setColor(Color.parseColor(pc));
        mPaintProgressBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintProgressBackground.setStyle(Paint.Style.FILL);
        mPaintProgressBackground.setColor(mProgressBackgroundColor);
        mPaintProgressBackground.setStyle(Paint.Style.STROKE);
        mPaintProgressBackground.setStrokeWidth(10 * getResources().getDisplayMetrics().density);
        mPaintProgressBackground.setStrokeCap(Paint.Cap.SQUARE);
        String bc = String.format("#%06X", (0xFFFFFF & mProgressBackgroundColor));
        mPaintProgressBackground.setColor(Color.parseColor(bc));
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground.setStyle(Paint.Style.FILL);
        mPaintBackground.setColor(mBackgroundColor);
        String bcfill = String.format("#%06X", (0xFFFFFF & mBackgroundColor));
        mPaintBackground.setColor(Color.parseColor(bcfill));
        mPaintDigits = new TextPaint();
        mPaintDigits.setColor(mProgressDigitsColor);
        String c = String.format("#%06X", (0xFFFFFF & mProgressDigitsColor));
        mPaintDigits.setColor(Color.parseColor(c));
        mPaintDigits.setTextSize(mTextSize);
        mPaintDigits.setAntiAlias(true);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w, h) / 2f;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(w, h);
        setMeasuredDimension(size, size);
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        float mouthInset = mRadius / 3;
        canvas.drawCircle(mRadius, mRadius, mouthInset * 2, mPaintBackground);
        mArcBounds.set(mouthInset, mouthInset, mRadius * 2 - mouthInset, mRadius * 2 - mouthInset);
        canvas.drawArc(mArcBounds, 0f, 360f, false, mPaintProgressBackground);
        if (isClockWise) {
            canvas.drawArc(mArcBounds, mStartAngle, (mDrawUpTo / getMaxValue() * 360), false, mPaintProgress);
        } else {
            canvas.drawArc(mArcBounds, mStartAngle, (mDrawUpTo / getMaxValue() * -360), false, mPaintProgress);
        }
        String drawnText = mDigits;
        if (!TextUtils.isEmpty(mDigits)) {
            float textHeight = mPaintDigits.descent() + mPaintDigits.ascent();
            canvas.drawText(drawnText, (getWidth() - mPaintDigits.measureText(drawnText)) / 2.0f, (getWidth() - textHeight) / 2.0f, mPaintDigits);
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDetachedFromWindow();
    }
    public void setProgress(float f) {
        mDrawUpTo = f;
        invalidate();
    }
    public float getProgress() {
        return mDrawUpTo;
    }
    public void setProgressColor(int color) {
        mProgressColor = color;
        mPaintProgress.setColor(color);
        invalidate();
    }
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        mPaintBackground.setColor(color);
        invalidate();
    }
    public void setProgressBackgroundColor(int color) {
        mProgressBackgroundColor = color;
        mPaintProgressBackground.setColor(color);
        invalidate();
    }
    public float getMaxValue() {
        return mMaxValue;
    }
    public void setText(String progressText) {
        mDigits = progressText;
        invalidate();
    }
    public String getText() {
        return mDigits;
    }
    public void setTextColor(int color) {
        mProgressDigitsColor = color;
        mPaintDigits.setColor(color);
        invalidate();
    }
    public void setTextColor(String color) {
        mPaintDigits.setColor(Color.parseColor(color));
        invalidate();
    }
    public int getTextColor() {
        return mProgressDigitsColor;
    }
    public void setClockwise(Boolean clockwise) {
        isClockWise = clockwise;
        invalidate();
    }
    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
        invalidate();
    }
    public void setStartAngle(int startAngle) {
        this.mStartAngle = startAngle;
        invalidate();
    }
    public void setCircularTimerListener(final CircularListener circularListener, long time, CircularEnum circularEnum, long timeInterval) {
        long timeInMillis = 0;
        switch (circularEnum) {
            case MILLIS:
                timeInMillis = time;
                break;
            case SECONDS:
                timeInMillis = time * 1000;
                break;
            case MINUTES:
                timeInMillis = time * 1000 * 60;
                break;
            case HOUR:
                timeInMillis = time * 1000 * 60 * 60;
                break;
            case DAY:
                timeInMillis = time * 1000 * 60 * 60 * 24;
                break;
        }
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        final long maxTime = timeInMillis;
        mCountDownTimer = new CountDownTimer(maxTime, timeInterval) {
            @Override
            public void onTick(long l) {
                double percentTimeCompleted = ((maxTime - l) / (double) maxTime);
                mDrawUpTo = (float) (mMaxValue * percentTimeCompleted);
                mDigits = circularListener.updateDataOnTick(l);
                invalidate();
            }
            @Override
            public void onFinish() {
                double percentTimeCompleted = 1;
                mDrawUpTo = (float) (mMaxValue * percentTimeCompleted);
                mDigits = circularListener.updateDataOnTick(0);
                circularListener.onTimerFinished();
                invalidate();
            }
        };
    }
    public void startTimer() {
        mCountDownTimer.start();
    }
}