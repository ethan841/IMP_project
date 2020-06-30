package com.example.imp_snakegame.TextLcd;

public class TextLcdJNI {
    static{
        System.loadLibrary("hwset");
    }

    public native void on();
    public native void clear();

    public native void print1Line(String str);
    public native void print2Line(String str);
}
