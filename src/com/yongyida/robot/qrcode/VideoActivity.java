package com.yongyida.robot.qrcode;

import com.yongyida.robot.qrcode.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class VideoActivity extends Activity{

	private Button mbtn;
	private ImageButton pause_btn;
	private boolean isPlaying = true;
	private VideoView vv;
	MediaController mediaController;
	
	Handler handler = new Handler(){
        public void handleMessage(Message msg) {
        	switch (msg.what) {
			case 1:
				mbtn.setVisibility(View.GONE);
				pause_btn.setVisibility(View.GONE);
				break;

			default:
				break;
			}
        }
    };
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.video);
		mbtn = (Button) this.findViewById(R.id.btn);
		pause_btn = (ImageButton) this.findViewById(R.id.pause_btn);
		 vv = (VideoView)this.findViewById(R.id.video);
		 mediaController = new MediaController(this);
		 
		//String uri = "android.resource://" + getPackageName() + "/" + R.raw.video;
		 String uri = "android.resource://" + getPackageName();
		vv.setVideoURI(Uri.parse(uri));
		
		// 设置VideView与MediaController建立关联  
        //vv.setMediaController(mediaController);  
        // 设置MediaController与VideView建立关联  
        //mediaController.setMediaPlayer(vv);  
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		vv.setLayoutParams(layoutParams);
		vv.start();
		
		vv.setOnCompletionListener(new OnCompletionListener() {			
			@Override
			public void onCompletion(MediaPlayer arg0) {
				finish();
			}
		});	
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(mbtn.getVisibility() == View.GONE){
			mbtn.setVisibility(View.VISIBLE);
			pause_btn.setVisibility(View.VISIBLE);
			
			handler.removeMessages(1);
			Message msg = handler.obtainMessage();
			msg.what = 1;
			handler.sendMessageDelayed(msg, 3000);
			}else if(mbtn.getVisibility() == View.VISIBLE){
				mbtn.setVisibility(View.GONE);
				pause_btn.setVisibility(View.GONE);
				
			}
		}
		return super.onTouchEvent(event);
	}

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn:
			//Intent in = new Intent(this, MainActivity.class);
			//startActivity(in);
			finish();
			break;
		case R.id.pause_btn:
			if(vv.isPlaying()){
				pause_btn.setBackgroundResource(R.drawable.play);
				vv.pause();
				
			}else{
				vv.seekTo(vv.getCurrentPosition());
				vv.start();
				pause_btn.setBackgroundResource(R.drawable.pause);
			}
			break;
		default:
			break;
		}
	}

	
}
