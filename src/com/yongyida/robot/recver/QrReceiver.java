package com.yongyida.robot.recver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yongyida.robot.activity.BindActivity;
import com.yongyida.robot.activity.DownloadActivity;
import com.yongyida.robot.bean.QrBean;
import com.yongyida.robot.data.MyIntent;
import com.yongyida.robot.utils.BeanUtils;

public class QrReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if( action != null ){
			
			if( action.equals(MyIntent.INTENT_YYDCHAT) ){
				String result = intent.getStringExtra("result");
				QrBean qrBean = BeanUtils.parseQrJson(result);
				if(qrBean.operation.equals("qrcode_download")){	
					Log.i("zx123","qrcode_download");
					Intent intentDownload = new  Intent(context,DownloadActivity.class);
					intentDownload.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intentDownload);
				}else if(qrBean.operation.equals("qrcode_bind")){
					Log.i("zx123","qrcode_bind");
					Intent intentBind = new  Intent(context,BindActivity.class);
					intentBind.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intentBind);								
				}
				
			}
			
		}
	}

}
