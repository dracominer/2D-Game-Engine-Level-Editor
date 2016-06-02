package com.engine.gameState;

import java.util.HashMap;

import com.engine.audio.AudioMaster;
import com.engine.audio.Source;

public class SoundRegistry {

	private static HashMap<String, String> soundFiles = new HashMap<String, String>();

	static {
		//load all of the default sounds
		registerSound("background", "audio/bg.wav");
	}

	public static void registerSound(String name, String file) {
		soundFiles.put(name, file);
	}

	public static String getFilePath(String name) {
		return soundFiles.get(name);
	}

	public static Source play(String name, float gain, float pitch) {
		return new Source().setGain(gain).setPitch(pitch).play(AudioMaster.loadSound(getFilePath(name)));
	}

	public static Source loadSource(String name, float gain, float pitch) {
		return new Source().setGain(gain).setPitch(pitch);
	}

}
