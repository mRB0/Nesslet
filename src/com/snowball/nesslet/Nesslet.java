package com.snowball.nesslet;

import android.app.Activity;
import android.os.Bundle;

public class Nesslet extends Activity {
    protected Player player;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        System.err.println("onCreate.");
        
        player = new Player();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
        
        System.err.println("onResume.");
        
    	player.startPlayback();
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
        
        System.err.println("onPause.");
        
        player.stopPlayback();
    }
    
}
