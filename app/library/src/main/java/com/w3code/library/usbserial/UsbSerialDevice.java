package com.w3code.library.usbserial;

import android.hardware.usb.UsbDeviceConnection;

public abstract class UsbSerialDevice implements UsbSerialInterface {

    protected final UsbDeviceConnection mConnection;
    protected boolean mAsyncMode;
    public UsbSerialDevice(UsbDeviceConnection connection) {
        this.mConnection = connection;
        this.mAsyncMode = true;
    }
    @Override
    public abstract boolean open();
    @Override
    public void read(UsbReadCallback mCallback) {
    }
    @Override
    public abstract void setBaudRate(int baudRate);
    @Override
    public abstract void setDataBits(int dataBits);
    @Override
    public abstract void setStopBits(int stopBits);
    @Override
    public abstract void setParity(int parity);
    @Override
    public abstract void setFlowControl(int flowControl);
}