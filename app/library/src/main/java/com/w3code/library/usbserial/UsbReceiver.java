package com.w3code.library.usbserial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.util.Objects;

public class UsbReceiver extends BroadcastReceiver {

    public Context context;
    public UsbDevice mDevice;
    public UsbManager mUSBManager;
    public UsbSerialDevice mSerialPort;
    public UsbDeviceConnection mConnection;
    public final String ACTION_USB_PERMISSION = "com.opengradle.page.USB_PERMISSION";
    UsbSerialInterface.UsbReadCallback usbReadCallback = new UsbSerialInterface.UsbReadCallback() {
    };
    public void onReceive(Context context, Intent intent) {
        switch (Objects.requireNonNull(intent.getAction())) {
            case ACTION_USB_PERMISSION:
                boolean granted = Objects.requireNonNull(intent.getExtras()).getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    mConnection = mUSBManager.openDevice(mDevice);
                    if (mSerialPort != null) {
                        if (mSerialPort.open()) {
                            mSerialPort.setBaudRate(9600);
                            mSerialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            mSerialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            mSerialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            mSerialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            mSerialPort.read(usbReadCallback);
                        } else {
                            Log.e("SERIAL","PORT NOT OPEN");
                        }
                    } else {
                        Log.e("SERIAL","PORT IS NULL");
                    }
                } else {
                    Log.e("SERIAL","PERM NOT GRANTED");
                }
                break;
            case UsbManager.ACTION_USB_DEVICE_ATTACHED:
            case UsbManager.ACTION_USB_DEVICE_DETACHED:
                break;
        }
    }
}