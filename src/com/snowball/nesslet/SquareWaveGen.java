package com.snowball.nesslet;

public class SquareWaveGen
{
	protected boolean _on;
	
	protected int _frequency; // of output device

	protected int _dutyCycle = 50; // 0..100
	protected byte _amplitude = 127; // 0..127 
	protected int _sampleRate;
	
	public int get_dutyCycle() {
		return _dutyCycle;
	}

	public void set_dutyCycle(int dutyCycle) {
		_dutyCycle = dutyCycle;
	}

	public int get_frequency() {
		return _frequency;
	}

	public void set_frequency(int frequency) {
		_frequency = frequency;
		
		_samplePeriod = _sampleRate / _frequency;
		_sampleOffset = 0;
	}

	public byte get_amplitude() {
		return _amplitude;
	}

	public void set_amplitude(byte amplitude) {
		_amplitude = amplitude;
	}

	public boolean get_on() {
		return _on;
	}

	public void set_on(boolean on) {
		_on = on;
	}

	public SquareWaveGen(int sampleRate, int dutyCycle)
	{
		_sampleRate = sampleRate;
		_dutyCycle = dutyCycle;
		
	}
	
	protected int _sampleOffset; // Offset in sample
	protected int _samplePeriod; // Period of sample (dependent on _sampleRate)
	
	public byte[] getSamples_8(int count)
	{
		byte[] buf = new byte[count];
		return getSamplesR_8(buf, 0, count);
	}
	
	public byte[] getSamplesR_8(byte[] buf, int writeOffs, int count)
	{
		int offs = 0;
		
		// periods of high/low duty cycles
		int high = _samplePeriod * _dutyCycle / 100;
		int low = _samplePeriod - high;
		
		byte genbyte;
		int num_gen;
		
		do
		{
			if (_sampleOffset >= high)
		    {
		    	// generate low samples
		    	genbyte = (byte)(127 - _amplitude);
		    	num_gen = low - (_sampleOffset - high);
		    }
		    else
		    {
		    	// generate high samples
		    	genbyte = (byte)(-128 + _amplitude);
		    	num_gen = high - _sampleOffset;
		    }
			if (num_gen > count - offs)
			{
				num_gen = count - offs;
			}
			for(int i = 0; i < num_gen; i++, offs++)
			{
				buf[writeOffs + offs] = genbyte;
			}
			_sampleOffset = (_sampleOffset + num_gen) % _samplePeriod;
		}
		while(offs < count);
		
		return buf;
	}
}
