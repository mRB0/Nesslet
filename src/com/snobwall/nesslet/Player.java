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
    		int minSize = AudioTrack.getMinBufferSize(sampleRate,
                    AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT);
    		int bufsize = minSize < minBufferSize ? minBufferSize : minSize;
            
    		AudioTrack audio = new AudioTrack(
                    AudioManager.STREAM_MUSIC, sampleRate,
                    AudioFormat.CHANNEL_CONFIGURATION_STEREO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufsize,
                    AudioTrack.MODE_STREAM);
    		audio.setStereoVolume(1.0f, 1.0f);
    		
    		Mixer audioSource;
    		audioSource = new Mixer(notes);
    		
    		short[] buffer = new short[bufsize / 2]; // this is num SAMPLES: divide by 2 for 16-bit
    		
    		// pre-fill buffer before starting playback
        	for(int offs = 0; offs < buffer.length; offs += 2)
    		{
        		audioSource.nextSample(buffer, offs);
    		}
    		
			audio.write(buffer, 0, buffer.length);
    		audio.play();

            // fill buffer repeatedly
    		while(!stopPlaying)
            {
            	for(int offs = 0; offs < buffer.length; offs += 2)
        		{
            		audioSource.nextSample(buffer, offs);
        		}
        		
    			audio.write(buffer, 0, buffer.length);
        		
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
