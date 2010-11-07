package com.snobwall.nesslet;

public class Note
{
	// -1 for unset for all fields
	public byte noteNum;
	public byte vol; // 0 .. 127
	public byte duty;
	public byte vibrato;
	
	public Note(byte noteNum, byte vol, byte duty, byte vibrato) {
		super();
		this.noteNum = noteNum;
		this.vol = vol;
		this.duty = duty;
		this.vibrato = vibrato;
	}
	
	static int frequencyOf(byte note)
	{
		return (int)(440.0 * Math.pow(Math.pow(2.0, (1.0/12.0)), (double)(note - 57)));
	}
	
	static String[] note_names = {
		"C",
		"C#",
		"D",
		"D#",
		"E",
		"F",
		"F#",
		"G",
		"G#",
		"A",
		"A#",
		"B"
	};
	
	static String nameOf(byte note)
	{
		if (note == -1)
		{
			return "...";
		}
		StringBuilder sb = new StringBuilder();
		java.util.Formatter fb = new java.util.Formatter(sb);
		
		fb.format("%-2s%d", note_names[note % 12], note / 12);
		
		return sb.toString().replace(' ', '-');
	}

}

