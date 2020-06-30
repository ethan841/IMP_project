package com.example.imp_snakegame.Piazo;

public class PiazoJNI {
    static {
        System.loadLibrary("hwset");
    }

    public native int write(int value);
}
