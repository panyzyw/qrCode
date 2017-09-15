package com.yongyida.robot.version;

import android.os.Build;
import android.util.Log;

import com.yongyida.robot.qrcode.DataUtil;
import com.yongyida.robot.qrcode.R;

public class VersionControl {

	//存在的版本
	public final static int VERSION_DEV = 0;//y50b大陆:
	public final static int VERSION_DEV_HK = 1;//y50b香港:
	public final static int VERSION_ST = 2;//y50b ST大陆:
	public final static int VERSION_ST_HK = 3;//y50b ST香港:
	public final static int VERSION_CMCC = 4;//y50b CMCC大陆:
	public final static int VERSION_CMCC_HK = 5;//y50b CMCC香港:
	public final static int VERSION_TW_HK = 6;//y50b 台湾 香港服
	
	//默认设置
	public static int mVersion = VERSION_DEV; //当前的版本
	
	public static int mTip = R.string.remind_dev;
	
	public static String mDownloadUrl = DataUtil.DEV_URL;

	public static int mDownloadBg = R.drawable.xy; // 下载app二维码的背景图
//	public static int mDownloadBg = R.drawable.lc; // 下载app二维码的背景图

	public static int mDownloadTip = R.string.scan_dev;
	
	public static int mBindBg = R.drawable.yyd;// 绑定机器人二维码的背景图
//	public static int mBindBg = R.drawable.lc;// 绑定机器人二维码的背景图

	public static int mBindQrcode = R.string.bind_qrcode;//绑定二维码的文字
	public static int mCheckBindedUerBg = R.drawable.bg_btn;
	
	public static int mBindQrcodeTV = R.string.bindQrcode_dev;
	public static int mBindUserListTV = R.string.binding_user_list_dev;
	public static int mOffOlineDevTV = R.string.off_oline_dev;
	public static int mOlineDevTV = R.string.oline_dev;

	/**
	 * 初始化配置与版本相关的内容,避免每次切换版本反复修改一些内容
	 */
	public static void initVersion(){
		
		//获取机器人版本(现有版本:正式版 三土 移动 , 每个版本又分大陆(中) 香港(英文))
		//mVersion = getVersion();
		mVersion = VERSION_DEV;
		
		Log.i("VersionControl", "mVersion = " + mVersion);
		
		switch (mVersion) {
		
		case VERSION_DEV:
			setVersionDev();
			break;
		case VERSION_DEV_HK:
			setVersionDevHk();
			break;
		case VERSION_ST:
			setVersionSt();
			break;
		case VERSION_ST_HK:
			setVersionStHk();
			break;
		case VERSION_CMCC:
			setVersionCmcc();
			break;
		case VERSION_CMCC_HK:
			setVersionCmccHk();
			break;
		case VERSION_TW_HK:
			setVersionTWHk();
			break;			
		}
	}
	
	/**
	 * 获取机器人的版本
	 * @return
	 */
	private static int getVersion() {
		//...		
		String s = Build.DISPLAY;
		
		if( s.contains("_ST_") ){//三土版本
			return VERSION_ST;
		}
		
		if(s.contains("_CMCC_")){ // 中移动
			return VERSION_CMCC;
		}
		
		if(s.contains("_MBN")){ //nuance
			return VERSION_DEV;  //暂时没处理
		}
				
		//其他都认为是正式发布版
		return VERSION_DEV;
	}

	private static void setVersionDev() {	
		mTip = R.string.remind_dev;		
		mDownloadUrl = DataUtil.DEV_URL;

		mDownloadBg = R.drawable.xy; // 下载app二维码的背景图
//		mDownloadBg = R.drawable.lc; // 下载app二维码的背景图

		mDownloadTip = R.string.scan_dev;

		mBindBg = R.drawable.yyd;// 绑定机器人二维码的背景图
//		mBindBg = R.drawable.lc;// 绑定机器人二维码的背景图

		mCheckBindedUerBg = R.drawable.bg_btn;
		mBindQrcodeTV = R.string.bindQrcode_dev;
		mBindUserListTV = R.string.binding_user_list_dev;
		mOffOlineDevTV = R.string.off_oline_dev;
		mOlineDevTV = R.string.oline_dev;
	}

	private static void setVersionDevHk() {		
		mTip = R.string.remind_dev_hk;		
		mDownloadUrl = DataUtil.DEV_URL_HK;
		mDownloadBg = R.drawable.xy; // 下载app二维码的背景图
		mDownloadTip = R.string.scan_dev_hk; 	
		mBindBg = R.drawable.yyd;// 绑定机器人二维码的背景图		
		
		mCheckBindedUerBg = R.drawable.bg_btn;
		mBindQrcodeTV = R.string.bindQrcode_dev_hk;
		mBindUserListTV = R.string.binding_user_list_dev_hk;
		mOffOlineDevTV = R.string.off_oline_dev_hk;
		mOlineDevTV = R.string.oline_dev_hk;
	}

	private static void setVersionSt() {
		mTip = R.string.remind_st;		
		mDownloadUrl = DataUtil.ST_URL;
		mDownloadBg = R.drawable.st; // 下载app二维码的背景图
		mDownloadTip = R.string.scan_st; 	
		mBindBg = R.drawable.st;// 绑定机器人二维码的背景图	
		
		mCheckBindedUerBg = R.drawable.bg_btn;
		mBindQrcodeTV = R.string.bindQrcode_st;
		mBindUserListTV = R.string.binding_user_list_st;
		mOffOlineDevTV = R.string.off_oline_st;
		mOlineDevTV = R.string.oline_st;
	}

	private static void setVersionStHk() {	
		mTip = R.string.remind_st_hk;		
		mDownloadUrl = DataUtil.ST_URL_HK;
		mDownloadBg = R.drawable.st; // 下载app二维码的背景图
		mDownloadTip = R.string.scan_st_hk; 	
		mBindBg = R.drawable.st;// 绑定机器人二维码的背景图			
		
		mCheckBindedUerBg = R.drawable.bg_btn;
		mBindQrcodeTV = R.string.bindQrcode_st_hk;
		mBindUserListTV = R.string.binding_user_list_st_hk;
		mOffOlineDevTV = R.string.off_oline_st_hk;
		mOlineDevTV = R.string.oline_st_hk;
	}

	private static void setVersionCmcc() {	
		mTip = R.string.remind_cmcc;		
		mDownloadUrl = DataUtil.CMCC_URL;
		mDownloadBg = R.drawable.z; // 下载app二维码的背景图
		mDownloadTip = R.string.scan_cmcc; 	
		mBindBg = R.drawable.z;// 绑定机器人二维码的背景图		
		
		mCheckBindedUerBg = R.drawable.bg_btn;
		mBindQrcodeTV = R.string.bindQrcode_cmcc;
		mBindUserListTV = R.string.binding_user_list_cmcc;
		mOffOlineDevTV = R.string.off_oline_cmcc;
		mOlineDevTV = R.string.oline_cmcc;
	}

	private static void setVersionCmccHk() {		
		mTip = R.string.remind_cmcc_hk;		
		mDownloadUrl = DataUtil.CMCC_URL_HK;
		mDownloadBg = R.drawable.z; // 下载app二维码的背景图
		mDownloadTip = R.string.scan_cmcc_hk; 	
		mBindBg = R.drawable.z;// 绑定机器人二维码的背景图		
		
		mCheckBindedUerBg = R.drawable.bg_btn;
		mBindQrcodeTV = R.string.bindQrcode_cmcc_hk;
		mBindUserListTV = R.string.binding_user_list_cmcc_hk;
		mOffOlineDevTV = R.string.off_oline_cmcc_hk;
		mOlineDevTV = R.string.oline_cmcc_hk;
	}

	private static void setVersionTWHk(){
		mTip = R.string.remind_tw;		
		mDownloadUrl = DataUtil.CMCC_TW_HK;
		mDownloadBg = R.drawable.xy; // 下载app二维码的背景图
		mDownloadTip = R.string.scan_tw; 	
		mBindBg = R.drawable.yyd;// 绑定机器人二维码的背景图	
		
		mCheckBindedUerBg = R.drawable.bg_btn_tw;
		mBindQrcodeTV = R.string.bindQrcode_tw;
		mBindUserListTV = R.string.binding_user_list_tw;
		mOffOlineDevTV = R.string.off_oline_tw;
		mOlineDevTV = R.string.oline_tw;
	}
	
}
