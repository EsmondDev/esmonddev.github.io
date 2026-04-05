package com.w3code.library.json;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.multidex.MultiDex;

import java.text.SimpleDateFormat;
import java.util.List;

public class BaseCode extends Fragment {

    public BaseCode() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(getContext());
    }
    public static class w3Code {
        //TODO - w3Code for BaseCode
        public static List<Activity> a0101;
        public static List<BaseTicker> a0102;
        public static List<BroadcastReceiver> a0103;
        public static List<Button> a0104, b0104, c0104;
        public static List<Context> a0105;
        public static List<EditText> a0106, b0106;
        public static List<ImageButton> a0107;
        public static List<ImageView> a0108, b0108;
        public static List<Intent> a0109, b0109;
        public static List<IntentFilter> a0110;
        public static List<LinearLayout> a0111;
        public static List<ProgressBar> a0112, b0112;
        public static List<RotateAnimation> a0113;
        public static List<SharedPreferences> a0114;
        public static List<SharedPreferences.Editor> a0115;
        public static List<SimpleDateFormat> a0116;
        public static List<SwitchCompat> a0117;
        public static List<View> a0118;
        //TODO - Main w3Code for BaseCode
    }
}