package com.snobwall.nesslet;

public class Mixer implements IAudioProvider
{
	
	private IAudioProvider[] _audioSources;
	private int _mixVol = 32;
	
	private short[] _mixTmp = new short[2];
	
	public Mixer(IAudioProvider[] audioSources)
	{
		_audioSources = audioSources;
	}
	
	@Override
	public void nextSample(short[] sampleBuf, int offs)
	{
		sampleBuf[offs] = 0;
		sampleBuf[offs+1] = 0;
		
		int mixResult;
		
		for(int i = 0; i < _audioSources.length; i++)
		{
			_audioSources[i].nextSample(_mixTmp, 0);
			
			for(int j = 0; j < 2; j++)
			{
				mixResult = (int)(sampleBuf[offs+j]) + (int)(_mixTmp[j]) / 64 * _mixVol;
				if (mixResult > 32767)
				{
					sampleBuf[offs+j] = 32767;
				}
				else if (mixResult < -32768)
				{
					sampleBuf[offs+j] = -32768;
				}
				else
				{
					sampleBuf[offs+j] = (short)mixResult;
				}
			}
		}
	}

}
