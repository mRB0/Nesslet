package com.snobwall.nesslet;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Nesslet extends Activity implements OnClickListener {
	
    protected Player player;
	
    protected String logTag;
    
    protected boolean playing = false;
    
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
    	
    }
    
    @Override
    public void onStop()
    {
    	super.onStop();
        
    	System.err.println("onStop.");
    	
    	if (playing)
    	{
            player.stopPlayback();
            playing = false;
    	}
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
	        Intent testIntent = new Intent();
	        testIntent.setClassName("com.snobwall.nesslet", "com.snobwall.nesslet.TestEditor");
	        this.startActivity(testIntent);
    		break;
    	
    	case R.id.Button02:
    		startStop();
    		break;
    	}
    }
    
    public void startStop()
    {
    	if (playing)
    	{
            player.stopPlayback();
    	}
    	else
    	{
            player = new Player();
            player.startPlayback();
    	}
    	playing = !playing;
    }
}
