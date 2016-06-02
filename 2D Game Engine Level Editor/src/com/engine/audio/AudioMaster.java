package com.engine.audio;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class AudioMaster {

	private static List<Integer> buffers = new ArrayList<Integer>();
	private static List<Source> sources = new ArrayList<Source>();

	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void setListenerData() {
		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}

	public static int loadSound(String file) {
		int buffer = AL10.alGenBuffers();
		buffers.add(buffer);
		WaveData wave = WaveData.create(file);
		AL10.alBufferData(buffer, wave.format, wave.data, wave.samplerate);
		wave.dispose();
		return buffer;
	}

	public static void cleanup() {
		for (int id : buffers) {
			AL10.alDeleteBuffers(id);
		}
		for (Source s : sources) {
			s.delete();
		}
		AL.destroy();
	}

	public static void addSource(Source source) {
		sources.add(source);
	}

}
