package com.w3code.library.progress;

public interface CircularListener {
    String updateDataOnTick(long remainingTimeInMs);
    void onTimerFinished();
}