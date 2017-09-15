package com.yongyida.robot.application;

import com.yongyida.robot.version.VersionControl;

import android.app.Application;

public class MyApp extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
	
		VersionControl.initVersion(); //版本配置初始化
		
		super.onCreate();
	}
}
