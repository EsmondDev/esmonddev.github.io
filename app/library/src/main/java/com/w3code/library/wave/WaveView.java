package com.w3code.library.wave;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class WaveView extends LinearLayout {

    public int mProgress;
    public final Wave mWave;
    protected static final int LARGE = 1;
    protected static final int MEDIUM = 2;
    protected static final int SMALL = 3;
    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        int mAboveWaveColor = getResources().getColor(android.R.color.white, null);
        int mBlowWaveColor = getResources().getColor(android.R.color.white, null);
        mProgress = 80;
        mWave = new Wave(context, null);
        mWave.initializeWaveSize(LARGE, MEDIUM, SMALL);
        mWave.setAboveWaveColor(mAboveWaveColor);
        mWave.setBlowWaveColor(mBlowWaveColor);
        mWave.initializePainters();
        WaveSolid mWaveSolid = new WaveSolid(context, null);
        mWaveSolid.setAboveWavePaint(mWave.getAboveWavePaint());
        mWaveSolid.setBlowWavePaint(mWave.getBlowWavePaint());
        addView(mWave);
        addView(mWaveSolid);
        setProgress(mProgress);
    }
    public void setProgress(int progress) {
        this.mProgress = Math.min(progress, 100);
        computeWaveToTop();
    }
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            computeWaveToTop();
        }
    }
    public void computeWaveToTop() {
        int mWaveToTop = (int) (getHeight() * (1f - mProgress / 100f));
        ViewGroup.LayoutParams params = mWave.getLayoutParams();
        if (params != null) {
            ((LayoutParams) params).topMargin = mWaveToTop;
        }
        mWave.setLayoutParams(params);
    }
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.progress = mProgress;
        return ss;
    }
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setProgress(ss.progress);
    }
    public static class SavedState extends BaseSavedState {
        int progress;
        SavedState(Parcelable superState) {
            super(superState);
        }
        private SavedState(Parcel in) {
            super(in);
            progress = in.readInt();
        }
        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(progress);
        }
        public static final Creator<SavedState> CREATOR = new Creator<>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}