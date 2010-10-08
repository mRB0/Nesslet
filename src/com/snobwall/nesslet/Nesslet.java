package com.snobwall.nesslet;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;


public class Nesslet extends Activity {
    protected Player player;
	
    protected String logTag;
    
    protected void d(String message)
    {
        if (Log.isLoggable(logTag, Log.DEBUG))
        {
        	Log.d(logTag, message);
        }
    }
    
    protected void i(String message)
    {
        if (Log.isLoggable(logTag, Log.INFO))
        {
        	Log.i(logTag, message);
        }
    }
    
    protected void w(String message)
    {
        if (Log.isLoggable(logTag, Log.WARN))
        {
        	Log.w(logTag, message);
        }
    }
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        logTag = getString(R.string.log_name);
        
        System.err.println("onCreate.");
        i("d: onCreate in a log.");
        
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
    	
        player = new Player();
        player.startPlayback();
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
        
    	System.err.println("onStop.");
        player.stopPlayback();
    }
    
    @Override
    public void onConfigurationChanged(Configuration cfg)
    {
    	super.onConfigurationChanged(cfg);
    	System.err.println("onConfigurationChanged.");
    }
}
