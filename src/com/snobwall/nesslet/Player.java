package com.snobwall.nesslet;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class Player
{
	private int minBufferSize = 4096;
	private int sampleRate = 44100;

    protected boolean stopPlaying = false;

    protected NoteRunner notes[];
    protected Thread playThread;
    
    public class AudioPlayer implements Runnable
    {
    	public void playAudio()
        {
    		int channels = notes.length;
    		int i;
    		
    		int minSize = AudioTrack.getMinBufferSize(sampleRate,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_8BIT);
            
    		AudioTrack audio[] = new AudioTrack[channels];
    		
    		for(i = 0; i < channels; i++)
    		{
	    		audio[i] = new AudioTrack(
	                    AudioManager.STREAM_MUSIC, sampleRate,
	                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
	                    AudioFormat.ENCODING_PCM_8BIT,
	                    minSize < minBufferSize ? minBufferSize : minSize,
	                    AudioTrack.MODE_STREAM);
	         	
	    		audio[i].play();
    		}
    		
            int bufsize = minSize < minBufferSize ? minBufferSize : minSize;
            
            byte[][] buffer = new byte[channels][bufsize];
            
            while(!stopPlaying)
            {
        		for(i = 0; i < channels; i++)
        		{
	            	for(int offs = 0; offs < bufsize; offs++)
	        		{
	        			buffer[i][offs] = notes[i].nextSample();
	        		}
        		}
        		
        		for(i = 0; i < channels; i++)
        		{
        			audio[i].write(buffer[i], 0, bufsize);
        		}
        		
            }
            
    		for(i = 0; i < channels; i++)
    		{
    			audio[i].stop();
    		}
        }
        
        public void run()
        {
        	playAudio();
        }
    	
    }
    
    public void stopPlayback()
    {
    	stopPlaying = true;
        try
        {
        	playThread.join();
        }
        catch (InterruptedException e)
        {
            System.err.println("Join interrupted");
        }
        playThread = null;
    }
    
    public void startPlayback()
    {
    	notes = new NoteRunner[2];
    	
    	notes[0] = new NoteRunner(sampleRate);
    	notes[0].set_songIdx(0);
    	notes[1] = new NoteRunner(sampleRate);
    	notes[1].set_songIdx(1);
    	
    	stopPlaying = false;
    	
        AudioPlayer player = new AudioPlayer();
        playThread = new Thread(player);
        playThread.start();
    }
    
}
