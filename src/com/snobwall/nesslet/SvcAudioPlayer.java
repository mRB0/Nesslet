package com.snobwall.nesslet;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SvcAudioPlayer extends Service {

	//
	// Local binding-related stuff
	//
	
    public class LocalBinder extends Binder {
        SvcAudioPlayer getService() {
            return SvcAudioPlayer.this;
        }
    }

    private final IBinder _binder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return _binder;
	}
	
	//
	// Service overrides
	//
	
    @Override
    public void onCreate()
    {
    	System.err.println("SvcAudioPlayer onCreate");
    }
	
    @Override
    public void onStart(Intent intent, int startId)
    {
    	System.err.println("SvcAudioPlayer onStart");
    }

    @Override
    public void onDestroy()
    {
    	System.err.println("SvcAudioPlayer onDestroy");
    	
    	stopPlayback();
    }
    
    //
    // Everything else
    //
    
    protected Player player = null;
	
    public boolean isPlaying() {
		return (player != null);
	}

	public void stopPlayback()
    {
    	if (player != null)
    	{
	    	player.stopPlayback();
	    	player = null;
    	}
    }
    
    public void startPlayback()
    {
    	player = new Player();
    	player.startPlayback();
    }
    
    protected int aNumber = 0;
    
    public int getANumber()
    {
    	if (aNumber == 0) 
    	{
    		aNumber = new Random().nextInt();
    	}
    	
    	return aNumber;
    }
}

