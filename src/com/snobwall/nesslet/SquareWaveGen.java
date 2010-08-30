package com.snobwall.nesslet;

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
	
	protected int _samplePeriod; // Period of sample (dependent on _sampleRate)
    protected int _sampleIdx = 0;
    
	public byte nextSample()
	{
		int middle = _samplePeriod * _dutyCycle / 100;
		
		_sampleIdx = (_sampleIdx + 1) % _samplePeriod;
		
		if (_amplitude == 0)
		{
			return -128;
		}
		
		if (_sampleIdx < middle)
		{
			return (byte)(127 - _amplitude);
		}
		else
		{
			return (byte)(-128 + _amplitude);
		}	
    }
    
}
