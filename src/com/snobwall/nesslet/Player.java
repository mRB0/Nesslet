package com.snobwall.nesslet;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class Player
{
	private int minBufferSize = 16384;
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
                    AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT);
    		int bufsize = minSize < minBufferSize ? minBufferSize : minSize;
            
    		AudioTrack audio[] = new AudioTrack[channels];
    		
    		for(i = 0; i < channels; i++)
    		{
	    		audio[i] = new AudioTrack(
	                    AudioManager.STREAM_MUSIC, sampleRate,
	                    AudioFormat.CHANNEL_CONFIGURATION_STEREO,
	                    AudioFormat.ENCODING_PCM_16BIT,
	                    bufsize,
	                    AudioTrack.MODE_STREAM);
	         	
	    		audio[i].setStereoVolume(1.0f, 1.0f);
	    		audio[i].play();
    		}
    		
    		short[][] buffer = new short[channels][bufsize / 2]; // this is num SAMPLES: divide by 2 for 16-bit
    		
    		System.err.println("Buffer length is " + buffer[0].length);
    		
            while(!stopPlaying)
            {
        		for(i = 0; i < channels; i++)
        		{
	            	for(int offs = 0; offs < buffer[i].length; offs+=2)
	        		{
	            		int sample = notes[i].nextSample();
	            		buffer[i][offs] = (short)(sample >> 16);
	            		buffer[i][offs+1] = (short)(sample);
	        		}
        		}
        		
        		for(i = 0; i < channels; i++)
        		{
        			audio[i].write(buffer[i], 0, buffer[i].length);
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
    	notes = new NoteRunner[3];
    	
    	notes[0] = new NoteRunner(sampleRate);
    	notes[0].set_songIdx(0);
    	notes[1] = new NoteRunner(sampleRate);
    	notes[1].set_songIdx(1);
    	notes[2] = new NoteRunner(sampleRate);
    	notes[2].set_songIdx(2);
    	
    	stopPlaying = false;
    	
        AudioPlayer player = new AudioPlayer();
        playThread = new Thread(player);
        playThread.start();
    }
    
}
