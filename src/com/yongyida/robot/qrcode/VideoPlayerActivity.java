package com.yongyida.robot.qrcode;

import java.io.File;

import com.yongyida.robot.qrcode.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.Toast;

public class VideoPlayerActivity extends Activity {

    private String path;
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private boolean pause;
    private int position;
	private Handler mHandler = new Handler();
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.video_activity);
		
		mediaPlayer = new MediaPlayer();

        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        //把输送给surfaceView的视频画面，直接显示到屏幕上,不要维持它自身的缓冲区
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 144);
        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().addCallback(new SurfaceCallback());
        
        mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				File file = new File(Environment.getExternalStorageDirectory(), "video.3gp");
				if(file.exists()){
					path = file.getAbsolutePath();
					play(0);
				}else{
					path = null;
					Log.i("Video", "path为空");
				}
			}
		}, 1000);
	}
	private final class SurfaceCallback implements Callback{
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}
		public void surfaceCreated(SurfaceHolder holder) {
			if(position>0 && path!=null){
				play(position);
				position = 0;
			}
		}
		public void surfaceDestroyed(SurfaceHolder holder) {
			if(mediaPlayer.isPlaying()){
				position = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
			}
		}
    }

	@Override
	protected void onDestroy() {
    	mediaPlayer.release();
    	mediaPlayer = null;
		super.onDestroy();
	}

	/**
	public void mediaplay(View v){
    	switch (v.getId()) {
		case R.id.playbutton:
			String filename = nameText.getText().toString();
			if(filename.startsWith("http")){
				path = filename;
				play(0);
			}else{
				File file = new File(Environment.getExternalStorageDirectory(), filename);
				if(file.exists()){
					path = file.getAbsolutePath();
					play(0);
				}else{
					path = null;
					Toast.makeText(this, R.string.filenoexsit, 1).show();
				}
			}
		
			break;

		case R.id.pausebutton:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.pause();
				pause = true;
			}else{
				if(pause){
					mediaPlayer.start();
					pause = false;
				}
			}
			break;
			
		case R.id.resetbutton:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.seekTo(0);
			}else{
				if(path!=null){
					play(0);
				}
			}
			break;
		case R.id.stopbutton:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
			break;
		}
    }
*/
	private void play(int position) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(path);
			mediaPlayer.setDisplay(surfaceView.getHolder());
			mediaPlayer.prepare();//缓冲
			mediaPlayer.setOnPreparedListener(new PrepareListener(position));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private final class PrepareListener implements OnPreparedListener{
		private int position;
		
		public PrepareListener(int position) {
		     this.position = position;
		}

		public void onPrepared(MediaPlayer mp) {
			mediaPlayer.start();//播放视频
			if(position>0) mediaPlayer.seekTo(position);
		}
	}
}