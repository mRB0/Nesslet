package com.snobwall.nesslet;

public class NoteRunner implements IAudioProvider
{
	protected int _frequency;
	
	protected int _noteLen = 75; // milliseconds
	protected int _tickLen;
	protected int _numTicks = 3; // ticks per row (ie. per note change)
	
	public NoteRunner(int frequency)
	{
		_frequency = frequency;
		
		_tickLen = _frequency * _noteLen / 1000 / _numTicks;
		
		sqwave = new SquareWaveGen(frequency, 50);
		
		sqwave.set_amplitude((byte)60);
	}
	
	protected SquareWaveGen sqwave;
	
	protected int _songIdx = 0;
	protected int _playingIdx = _songIdx;
	
	public int get_songIdx() {
		return _songIdx;
	}

	public void set_songIdx(int songIdx) {
		if (songIdx >= _notes.length)
		{
			_songIdx = 0;
		}
		else
		{
			_songIdx = songIdx;
		}
	}

	protected Note _notes[][] =
	{
			{
				new Note((byte)56, (byte)64, (byte)50, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)56, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)56, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)52, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)56, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)59, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
			},
			{
				new Note((byte)46, (byte)64, (byte)50, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)46, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)46, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)46, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)46, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)51, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)47, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
			},
			{
				new Note((byte)30, (byte)64, (byte)50, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)30, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)30, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)30, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)30, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)47, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)35, (byte)64, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 8, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte) 0, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
				new Note((byte)-1, (byte)-1, (byte)-1, (byte)-1),
			},
	};
	
	// State!
	protected int _state_frameOffs = 0; // which frame, inside the tick, are we on?
	protected int _state_tickOffs = 0; // which tick are we on?
	protected int _state_noteOffs = 0; // which note are we playing?

	@Override
	public int nextSample()
	{
		int sample;
		
		if (_state_tickOffs == 0 && 
				_state_frameOffs == 0) 
		{
			if (_state_noteOffs == 0)
			{
				// song ended at last sample get
				_playingIdx = _songIdx;
			}
					
			Note newNote = _notes[_playingIdx][_state_noteOffs];
			
			// new note; change things as appropriate
			if (newNote.noteNum >= 0)
			{
				sqwave.set_frequency(Note.frequencyOf(newNote.noteNum));
			}
			if (newNote.vol >= 0)
			{
				sqwave.set_amplitude(newNote.vol);
			}
			if (newNote.duty >= 0)
			{
				sqwave.set_dutyCycle(newNote.duty);
			}
		}
		
		sample = sqwave.nextSample();
		
		// prepare for next frame
		_state_frameOffs = (_state_frameOffs + 1) % _tickLen;
		if (_state_frameOffs == 0)
		{
			_state_tickOffs = (_state_tickOffs + 1) % _numTicks;
			if (_state_tickOffs == 0)
			{
				_state_noteOffs = (_state_noteOffs + 1) % _notes[_playingIdx].length;
				// if _state_noteOffs is 0, then we've looped the song
			}
		}
		
		return sample;
	}
}
