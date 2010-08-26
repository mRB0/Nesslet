package com.snowball.nesslet;

public class NoteRunner
{
	protected int _frequency;
	
	protected int _noteLen = 100; // milliseconds
	protected int _tickLen;
	
	public NoteRunner(int frequency)
	{
		_frequency = frequency;
		
		_tickLen = _frequency * _noteLen / 1000;
		
		sqwave = new SquareWaveGen(frequency, 50);
		sqwave.set_frequency((int)(_notes[_noteOffs] / 2));
		sqwave.set_dutyCycle(_duties[_noteOffs]);
		
		sqwave.set_amplitude((byte)80);
	}
	
	protected SquareWaveGen sqwave;
	
	protected int _tickOffs = 0;
	protected int _noteOffs = 0;
	protected int _notes[] = {
			523, 659, 784,
			523, 659, 784,
			523, 659, 784,
			698, 880, 1046,
			698, 880, 1046,
			698, 880, 1046,
			784, 988, 1175,
			784, 988, 1175,
			784, 988, 1175,
		};
	protected int _duties[] = {50, 50, 50};
	
	
	public byte[] getSamples_8(int count)
	{
		byte buf[] = new byte[count];
		int copied = 0;
		
		do
		{
			int numGen = _tickLen - _tickOffs;
			if (numGen > count - copied)
			{
				numGen = count - copied;
			}
			byte sqbuf[] = sqwave.getSamples_8(numGen);
			
			System.arraycopy(sqbuf, 0, buf, copied, sqbuf.length);
			copied += numGen;
			_tickOffs += numGen;
			
			if (_tickOffs >= _tickLen)
			{
				_tickOffs = 0;
				_noteOffs = (_noteOffs + 1) % _notes.length;
				sqwave.set_frequency((int)(_notes[_noteOffs] / 2));
				
				if (_noteOffs == 0)
				{
					int dc = sqwave.get_dutyCycle();
					dc += 10;
					if (dc > 50)
					{
						dc = 10;
					}
					sqwave.set_dutyCycle(dc);
				}
				
			}
		} while(copied < count);
		
		return buf;
	}
}
