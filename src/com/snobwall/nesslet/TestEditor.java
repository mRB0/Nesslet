package com.snobwall.nesslet;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class TestEditor extends Activity {
	
    protected SvcAudioPlayer _player = null;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testeditor);
        
        System.err.println("TestEditor.onCreate: bindService = " + bindService(new Intent(this, SvcAudioPlayer.class),
    			_svcConnection, Context.BIND_AUTO_CREATE));
        
    }
    
    public void onPlayerReady()
    {
        if (_player == null)
        {
        	System.err.println("Player is null!");
        }
        else
        {
        	System.err.println("TestEditor.onCreate: Service's number is " + _player.getANumber());
        }
    }

    private ServiceConnection _svcConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            _player = ((SvcAudioPlayer.LocalBinder)service).getService();
        	System.err.println("TestEditor._svcConnection is getting a service (" + _player + ").");
        	onPlayerReady();
        }

        public void onServiceDisconnected(ComponentName className) {
            _player = null;
        }
    };

}
