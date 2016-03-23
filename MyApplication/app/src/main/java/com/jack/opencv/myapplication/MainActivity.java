package com.jack.opencv.myapplication;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, PreviewCallback {

    SurfaceHolder surfaceHolder;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.ic)).getBitmap();
                int w = bitmap.getWidth(), h = bitmap.getHeight();
                int[] pix = new int[w * h];
                bitmap.getPixels(pix, 0, w, 0, 0, w, h);
                int[] resultPixes = OpenCVHelper.gray(pix, w, h);
                Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
                result.setPixels(resultPixes, 0, w, 0, 0, w, h);
                imageView.setImageBitmap(result);
            }
        });

        SurfaceView view = (SurfaceView) findViewById(R.id.surfaceView);
        view.getHolder().addCallback(this);
        view.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    public void surfaceCreated(SurfaceHolder holder) {

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(holder);
            Parameters params = camera.getParameters();
            params.setPictureSize(640, 480); //指定拍照图片的大小
            params.setPreviewSize(320, 240);// 指定preview的大小


            // 横竖屏镜头自动调整
            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                params.set("orientation", "portrait"); //
                params.set("rotation", 90); // 镜头角度转90度（默认摄像头是横拍）
                camera.setDisplayOrientation(90); // 在2.2以上可以使用
            } else {// 如果是横屏
                params.set("orientation", "landscape"); //
                camera.setDisplayOrientation(0); // 在2.2以上可以使用
            }
            camera.setParameters(params);
            camera.startPreview();
            camera.setPreviewCallback(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) camera.release();
        camera = null;
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        //预览图原数据
    }
}
