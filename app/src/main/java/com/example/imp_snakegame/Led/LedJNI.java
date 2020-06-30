package com.example.imp_snakegame.Led;

public class
LedJNI {
    static{
        System.loadLibrary("hwset");
    }

    public native void on(char data);
}
