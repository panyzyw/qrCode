package com.yongyida.robot.utils;

import android.content.Context;
import android.content.Intent;

public class VoiceUtils {
	public static void startVoice(Context context,String text){
		//原本是在本应用语音合成text的,但是在系统中源码编译后不出声,嫌麻烦就发广播在别的地方语音合成
//		Intent intent = new Intent(context,VoiceService.class);
//		intent.putExtra("voiceContent", text);
//		context.startService(intent);
//		Log.i(Constant.TAG, "startVoice");
		
		Intent intent = new Intent("com.yongyida.VOICE_CONTENT");
		intent.putExtra("content", text);
		context.sendBroadcast(intent);
	}
}
