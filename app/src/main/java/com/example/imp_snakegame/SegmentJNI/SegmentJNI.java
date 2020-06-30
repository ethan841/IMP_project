package com.example.imp_snakegame.SegmentJNI;

public class SegmentJNI {
    static {
        System.loadLibrary("hwset");
    }

    public native void open();
    public native void print(int num);
}
