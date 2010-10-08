package com.snobwall.nesslet;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

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
    		
    		ShortBuffer[] buffer = new ShortBuffer[channels];
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
	    		
	    		ByteBuffer bb = ByteBuffer.allocateDirect(bufsize);
	    		buffer[i] = bb.asShortBuffer();
    		}
    		
    		//[bufsize / 2]; // this is num SAMPLES: divide by 2 for 16-bit
    		
    		System.err.println("Buffer length is " + buffer[0].capacity());
    		System.err.println("Buffer is direct?? " + buffer[0].isDirect());
    		
    		short[] buf = new short[bufsize / 2];
    		
            while(!stopPlaying)
            {
        		for(i = 0; i < channels; i++)
        		{
        			buffer[i].rewind();
	            	while(buffer[i].hasRemaining())
	        		{
	            		notes[i].nextSample(buffer[i]);
	        		}
        		}
        		
        		for(i = 0; i < channels; i++)
        		{
        			buffer[i].rewind();
        			buffer[i].get(buf);
        			audio[i].write(buf, 0, buf.length);
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
