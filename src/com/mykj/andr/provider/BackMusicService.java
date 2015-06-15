package com.mykj.andr.provider;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackMusicService extends Service {
	private MediaPlayer mp;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		AssetManager assetManager = getApplicationContext().getAssets();
		mp  = new MediaPlayer();
		try {
			
			AssetFileDescriptor fileDescriptor = assetManager.openFd("sound/back_music.mp3");
			mp.setDataSource(fileDescriptor.getFileDescriptor(),
			                              fileDescriptor.getStartOffset(),
			                              fileDescriptor.getLength());
			mp.prepare();
			mp.setLooping(true);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		mp.start();
		return START_NOT_STICKY;
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mp.stop();
		mp.release();
	}
	
	
}
