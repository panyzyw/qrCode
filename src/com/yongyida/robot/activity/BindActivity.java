package com.yongyida.robot.activity;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
//import android.os.SystemProperties;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.yongyida.robot.qrcode.QRCodeUtil;
import com.yongyida.robot.qrcode.R;
import com.yongyida.robot.utils.VoiceUtils;
import com.yongyida.robot.version.VersionControl;

public class BindActivity extends Activity {

    ImageView mIvBind;

    private String sdKey;
    private String str;

    private BroadcastReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_bind);

        View view = View.inflate(this, R.layout.activity_bind, null);
        ImageView tv_bind = (ImageView) view.findViewById(R.id.iv_bind);
        tv_bind.setBackgroundResource(VersionControl.mBindBg);

        setContentView(view);

        initStopRecver();

        initView();

        initData();
    }

    private void initStopRecver() {
        // TODO Auto-generated method stub

        myReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                BindActivity.this.finish();
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.yydrobot.STOP");
        registerReceiver(myReceiver, filter);
    }

    private void initView() {
        // TODO Auto-generated method stub
        mIvBind = (ImageView) findViewById(R.id.iv_bind);
    }

    private void initData() {
        // TODO Auto-generated method stub

//        str = SystemProperties.get("gsm.serial");
        str = getSystemString("gsm.serial");

        //生产下载app的二维码
        //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub

            final String filePath = getFileRoot(BindActivity.this) + File.separator
                    + "qr_" + System.currentTimeMillis() + ".jpg";

            if (str == null || str.equals("")) {
                VoiceUtils.startVoice(BindActivity.this, BindActivity.this.getString(R.string.id_err));
                return;
            }
            sdKey = str.substring(0, 32);
            if (TextUtils.isEmpty(sdKey)) {
                VoiceUtils.startVoice(BindActivity.this, BindActivity.this.getString(R.string.id_err));
                return;
            }
            boolean success = QRCodeUtil.createQRImage(sdKey, 800, 800,
                    BitmapFactory.decodeResource(getResources(), VersionControl.mBindBg),
                    filePath);
            if (success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mIvBind.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        File file = new File(filePath);
                        file.delete();
                    }
                });
            }

            VoiceUtils.startVoice(BindActivity.this, BindActivity.this.getString(R.string.bind_qrcode));
        }

    };

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }
        super.onDestroy();
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
