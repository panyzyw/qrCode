package com.yongyida.robot.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.yongyida.robot.bean.QrBean;
import com.yongyida.robot.data.Constant;

public class BeanUtils {
	public static QrBean parseQrJson(String json){
		try{
			QrBean qrBean =  new Gson().fromJson(json, QrBean.class);
			return qrBean;
		}catch(Exception e){
			e.printStackTrace();
			Log.e(Constant.TAG, "parseQrJson err");
			return null;
		}
	}
}
