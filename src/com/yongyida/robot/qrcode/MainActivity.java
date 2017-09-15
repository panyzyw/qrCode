package com.yongyida.robot.qrcode;


import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
//import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyida.robot.qrcode.R;
import com.yongyida.robot.version.VersionControl;

public class MainActivity extends Activity implements OnClickListener {

    private final static String TAG = "MainActivityTAG";
    private Handler mHandler = new Handler();
    private TextView tip;
    private Button mbtn;
    private TextView contentET1;
    private TextView contentET2;
    private String sdKey;
    private String str;
    private ImageView imageView;
    private ImageView imageViewApp;
    private TextView downloadTip;
    private TextView bindTip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        View view = View.inflate(this, R.layout.test_layout, null);
//        ImageView tv_download = (ImageView) view.findViewById(R.id.download_qr_iv);
//        tv_download.setBackgroundResource(VersionControl.mDownloadBg);

//        ImageView tv_bind = (ImageView) view.findViewById(R.id.create_qr_iv);
//        tv_bind.setBackgroundResource(VersionControl.mBindBg);

        setContentView(R.layout.cdkey_activity);
        //setContentView(R.layout.cdkey_activity);

        //Build.DISPLAY
//        str = SystemProperties.get("gsm.serial");
        str = getSystemString("gsm.serial");
        //str = "Y50B-566";
        try {
            tip = (TextView) findViewById(R.id.tishi_tv);
            tip.setText(getString(VersionControl.mTip));

            //内容
            contentET1 = (TextView) this.findViewById(R.id.create_qr_content);
            contentET2 = (TextView) this.findViewById(R.id.create_qr_content2);

            //查看二维码按钮的背景
            mbtn = (Button) this.findViewById(R.id.user_btn);
            mbtn.setBackgroundResource(VersionControl.mCheckBindedUerBg);
            mbtn.setOnClickListener(this);

            //显示二维码图片
            imageView = (ImageView) this.findViewById(R.id.create_qr_iv);
            imageView.setBackgroundResource(VersionControl.mBindBg);

            imageViewApp = (ImageView) this.findViewById(R.id.download_qr_iv);
            imageViewApp.setBackgroundResource(VersionControl.mDownloadBg);

            downloadTip = (TextView) findViewById(R.id.scan_tv);
            downloadTip.setText(getString(VersionControl.mDownloadTip));

            //sid  id 下面的绑定二维码提示文本
            bindTip = (TextView) findViewById(R.id.create_qr_cdKeyword);
            bindTip.setText(getString(VersionControl.mBindQrcodeTV));

            //是否添加Logo
            // final CheckBox addLogoCB = (CheckBox) findViewById(R.id.create_qr_addLogo);
            //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
            new Thread(runnable).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final String filePath = getFileRoot(MainActivity.this) + File.separator
                    + "qr_" + System.currentTimeMillis() + ".jpg";
            final String filePathApp = getFileRoot(MainActivity.this) + File.separator
                    + "qr_app" + System.currentTimeMillis() + ".jpg";

            boolean successApp = QRCodeUtil.createQRImage(VersionControl.mDownloadUrl, 800, 800,
                    BitmapFactory.decodeResource(getResources(), VersionControl.mDownloadBg),
                    filePathApp);
            Log.i("Main", "successApp:" + successApp);
            if (successApp) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d(TAG, "下载图片路径 : " + filePathApp);
                        imageViewApp.setImageBitmap(BitmapFactory.decodeFile(filePathApp));
                        File fileApp = new File(filePathApp);
                        fileApp.delete();
                    }
                });
            }


            if (str == null || str.equals("")) {
                return;
            }
            sdKey = str.substring(0, 32);
            if (TextUtils.isEmpty(sdKey)) {
                return;
            }
            boolean success = QRCodeUtil.createQRImage(sdKey, 800, 800,
                    BitmapFactory.decodeResource(getResources(), VersionControl.mBindBg),
                    filePath);
            if (success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String key1 = sdKey.substring(0, sdKey.indexOf("-"));
                        String key2 = sdKey.substring(sdKey.indexOf("-") + 1, sdKey.length());
                        Resources res = getResources();
                        String text = res.getString(R.string.cdkeyid) + key1.trim();
                        String text2 = res.getString(R.string.cdkeysid) + key2.trim();
                        contentET1.setText(text);
                        contentET2.setText(text2);
                        imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        File file = new File(filePath);
                        file.delete();
                    }
                });
            }

        }

    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_btn:
                Log.i(TAG, "btn");
                Intent in = new Intent(MainActivity.this, UserActivity.class);
                MainActivity.this.startActivity(in);
                break;

            default:
                break;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }


    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
    }

    private String getSystemString(String key) {
        Class<?> clazz;
        try {
            clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getDeclaredMethod("get", String.class);
            return (String) method.invoke(clazz.newInstance(), key);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "Y50B-566";
    }
}