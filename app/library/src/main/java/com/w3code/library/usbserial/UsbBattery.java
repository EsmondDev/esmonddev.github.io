package com.w3code.library.usbserial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class UsbBattery extends BroadcastReceiver {

    public float mVoltage;
    public boolean isCharging;
    public boolean usbCharge;
    public boolean acCharge;
    public int mTemperature;
    public int mBattery;
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        mBattery = level * 100 / scale;
        mVoltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) / (float) 1000;
        mTemperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10;
        onBatteryReceive();
    }
    public void onBatteryReceive() {
    }
}