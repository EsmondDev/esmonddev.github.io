package com.w3code.library.json;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseTicker extends FrameLayout {

    public float mSpaceWidth;
    public int mTextColor, mData;
    public final Paint mPaint = new Paint();
    public final Set<View> mAnimating = new HashSet<>();
    public List<String> mTicker = Collections.emptyList();
    public static final TimeInterpolator LINEAR = new LinearInterpolator();
    public static final long TRAVEL_SPEED = 8L;
    public BaseTicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(getResources());
    }
    public void setBaseTicker(List<String> ticker) {
        mTicker = (ticker != null) ? ticker : Collections.emptyList();
    }
    public void setTextColor(@ColorInt int color) {
        mTextColor = color;
    }
    public void setTextSize(int size) {
        mSpaceWidth = (float) size;
    }
    public void run() {
        if (mTicker.isEmpty()) {
            throw new IllegalStateException("App Data System");
        }
        launchNext();
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                String string = button.getText().toString();
                int w = (int) mPaint.measureText(string, 0, string.length());
                int h = button.getMeasuredHeight();
                button.layout(0, 0, w, h);
            }
        }
    }
    public void init(Resources ignoredRes) {
        mSpaceWidth = 50;
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(mSpaceWidth);
        mTextColor = getResources().getColor(android.R.color.white, null);
        if (getBackground() == null) {
            setBackgroundColor(getColorResource());
        }
    }
    @ColorInt
    public int getColorResource() {
        return getResources().getColor(android.R.color.white, null);
    }
    public void launchNext() {
        int index = nextIndex();
        String string = mTicker.get(index);
        Button button = makeTextView(string, index);
        button.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> animateEnter((Button) v));
        addView(button);
    }
    public Button makeTextView(String headline, int index) {
        Button button = new Button(getContext());
        button.setGravity(Gravity.CENTER_VERTICAL);
        button.setBackgroundColor(getResources().getColor(android.R.color.transparent, null));
        button.setTextColor(mTextColor);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSpaceWidth);
        button.setText(headline);
        button.setPadding(0, 0,0, 0);
        button.setSingleLine(true);
        button.setTag(index);
        return button;
    }
    public int nextIndex() {
        mData = (mData + 1) % mTicker.size();
        return mData;
    }
    public void animateEnter(final Button button) {
        if (mAnimating.contains(button)) {
            return;
        }
        button.setTranslationX(getWidth());
        int distance = button.getWidth() + (int) mSpaceWidth;
        button.animate()
                .translationXBy(-distance)
                .setDuration(distance * TRAVEL_SPEED)
                .setInterpolator(LINEAR)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        mAnimating.add(button);
                    }
                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        launchNext();
                        animateExit(button);
                    }
                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {
                        mAnimating.remove(button);
                    }
                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {
                    }
                })
                .start();
    }
    public void animateExit(final Button button) {
        int distance = getWidth() - (int) mSpaceWidth;
        button.animate()
                .translationXBy(-distance)
                .setDuration(distance * TRAVEL_SPEED)
                .setInterpolator(LINEAR)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                    }
                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        removeView(button);
                        mAnimating.remove(button);
                    }
                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {
                        mAnimating.remove(button);
                    }
                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {
                    }
                })
                .start();
    }
}