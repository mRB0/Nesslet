package com.snobwall.nesslet;

public interface IAudioProvider {
	public void nextSample(short[] sampleBuf, int offs);
}
