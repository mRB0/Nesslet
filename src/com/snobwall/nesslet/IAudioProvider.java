package com.snobwall.nesslet;

import java.nio.ShortBuffer;

public interface IAudioProvider {
	public void nextSample(ShortBuffer sampleBuf);
}
