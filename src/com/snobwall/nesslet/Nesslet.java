package com.snobwall.nesslet;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Nesslet extends Activity implements OnClickListener {
	
    protected String logTag;
    
    protected SvcAudioPlayer _player = null;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        logTag = getString(R.string.log_name);
        
        System.err.println("onCreate.");
        Log.i(logTag, "d: onCreate in a log.");
        
        Button button = (Button)findViewById(R.id.Button01);
        button.setOnClickListener(this);
        
        button = (Button)findViewById(R.id.Button02);
        button.setOnClickListener(this);
        
    	//player.startPlayback();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
        
        System.err.println("onResume.");
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
        
    	System.err.println("onPause.");
    }
    
    @Override
    public void onStart()
    {
    	super.onStart();
        
    	System.err.println("onStart.");

    	System.err.println("bindService = " + bindService(new Intent(this, SvcAudioPlayer.class),
    			_svcConnection, Context.BIND_AUTO_CREATE));

    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
        
    	System.err.println("onStop.");
    	
//    	if (_player.isPlaying())
//    	{
//            _player.stopPlayback();
//    	}
    }
    
    @Override
    public void onConfigurationChanged(Configuration cfg)
    {
    	super.onConfigurationChanged(cfg);
    	System.err.println("onConfigurationChanged.");
    }
    
    @Override
    public void onClick(View v)
    {
    	switch(v.getId())
    	{
    	case R.id.Button01:
    		startActivity(new Intent(Nesslet.this, TestEditor.class));
    		break;
    	
    	case R.id.Button02:
    		startStop();
    		break;
    	}
    }
    
    public void startStop()
    {
    	if (_player.isPlaying())
    	{
            _player.stopPlayback();
    	}
    	else
    	{
            _player.startPlayback();
    	}
    	System.err.println("The service's number is " + _player.getANumber());

    }
    
    
    private ServiceConnection _svcConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            _player = ((SvcAudioPlayer.LocalBinder)service).getService();
            
        }

        public void onServiceDisconnected(ComponentName className) {
            _player = null;
        }
    };

}
