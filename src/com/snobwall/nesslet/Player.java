package com.snobwall.nesslet;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class Player
{
	private int minBufferSize = 4096;
	private int sampleRate = 44100;

    protected boolean stopPlaying = false;

    protected NoteRunner notes;
    protected Thread playThread;
    
    public class AudioPlayer implements Runnable
    {
    	public void playAudio()
        {
            int minSize = AudioTrack.getMinBufferSize(sampleRate,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_8BIT);
            AudioTrack audio = new AudioTrack(
                    AudioManager.STREAM_MUSIC, sampleRate,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_8BIT,
                    minSize < minBufferSize ? minBufferSize : minSize,
                    AudioTrack.MODE_STREAM);

        	audio.play();
            
            int bufsize = minSize < minBufferSize ? minBufferSize : minSize;
            
            byte[] buffer = new byte[bufsize];
            
            while(!stopPlaying)
            {
        		for(int offs = 0; offs < bufsize; offs++)
        		{
        			buffer[offs] = notes.nextSample();
        		}
            	
            	audio.write(buffer, 0, bufsize);
            }
            
            audio.stop();
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
    	notes = new NoteRunner(sampleRate);
    	
    	stopPlaying = false;
    	
        AudioPlayer player = new AudioPlayer();
        playThread = new Thread(player);
        playThread.start();
    }
    
}
