package com.engine.audio;

import org.lwjgl.openal.AL10;

public class Source {

	public static void play(String file, float gain, float pitch) {
		new Source().setGain(gain).setPitch(pitch).play(AudioMaster.loadSound(file));
	}

	public static void play(String file, float gain) {
		play(file, gain, 1);
	}

	public static void play(String file) {
		play(file, 1, 1);
	}

	private int sourceID;

	public Source(boolean addToList) {
		this.sourceID = AL10.alGenSources();
		AL10.alSourcef(sourceID, AL10.AL_GAIN, 1f);
		AL10.alSourcef(sourceID, AL10.AL_PITCH, 1f);
		AL10.alSource3f(sourceID, AL10.AL_POSITION, 0, 0, 0);
		if (addToList) AudioMaster.addSource(this);
	}

	public Source() {
		this(true);
	}

	public Source setGain(float gain) {
		AL10.alSourcef(sourceID, AL10.AL_GAIN, gain);
		return this;
	}

	public Source setPitch(float pitch) {
		AL10.alSourcef(sourceID, AL10.AL_PITCH, pitch);
		return this;
	}

	public Source play(int buffer) {
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(sourceID);
		return this;
	}

	public Source pause(int buffer) {
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
		AL10.alSourcePause(sourceID);
		return this;
	}

	public Source enableLooping(int buffer) {
		AL10.alSourcei(sourceID, AL10.AL_LOOPING, AL10.AL_TRUE);
		return this;
	}

	public Source disableLooping(int buffer) {
		AL10.alSourcei(sourceID, AL10.AL_LOOPING, AL10.AL_FALSE);
		return this;
	}

	public Source stop(int buffer) {
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
		AL10.alSourceStop(sourceID);
		AL10.alSourceRewind(sourceID);
		return this;
	}

	public void delete() {
		AL10.alDeleteSources(sourceID);
	}

}
