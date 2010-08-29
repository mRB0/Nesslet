package com.snowball.nesslet;

import java.util.Iterator;

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
	
	public byte[] getSamples_8(int count)
	{
		byte[] buf = new byte[count];
		return getSamplesR_8(buf, 0, count);
	}
	
    Generator<Byte> squareWaveGenerator = new Generator<Byte>() {
        @Override
        public void run() {
    		int i;
        	for(;;)
        	{
//        		for(i = 0; i < _samplePeriod * _dutyCycle / 100; i++)
//        		{
//        			yield((byte)(127 - _amplitude));
//        		}
//        		for(i = 0; i < _samplePeriod - (_samplePeriod * _dutyCycle / 100); i++)
//        		{
//        			yield((byte)(-128 + _amplitude));
//        		}
        		
        		for(i=0; i<50; i++)
        		{
        			yield((byte)0);
        		}
        		for(i=0; i<50; i++)
        		{
        			yield((byte)-1);
        		}
            }
        }
    };
    
    Iterator<Byte> squareWaveSamples = squareWaveGenerator.iterator();

	public byte[] getSamplesR_8(byte[] buf, int writeOffs, int count)
	{
		int offs;
		
		for(offs = 0; offs < count; offs++)
		{
			buf[writeOffs + offs] = squareWaveSamples.next();
		}
		
		return buf;
	}
}
