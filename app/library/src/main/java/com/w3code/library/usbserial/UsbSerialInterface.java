package com.w3code.library.usbserial;

public interface UsbSerialInterface {
    int DATA_BITS_8 = 8;
    int STOP_BITS_1 = 1;
    int PARITY_NONE = 0;
    int FLOW_CONTROL_OFF = 0;
    boolean open();
    void read(UsbReadCallback mCallback);
    void setBaudRate(int baudRate);
    void setDataBits(int dataBits);
    void setStopBits(int stopBits);
    void setParity(int parity);
    void setFlowControl(int flowControl);
    interface UsbReadCallback {
    }
}