package com.jack.opencv.myapplication;

/**
 * Created by jordan on 16/3/12.
 */
public class OpenCVHelper {

    static{
        System.loadLibrary("OpenCV");
    }

    public static native int[] gray(int[] buf, int w, int h);
}
