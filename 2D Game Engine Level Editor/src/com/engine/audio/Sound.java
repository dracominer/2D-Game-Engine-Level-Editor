package com.engine.audio;

public class Sound {

	private Source source;
	private int soundBuffer;

	public Sound(Source source, int soundBuffer) {
		this.source = source;
		this.soundBuffer = soundBuffer;
	}

	public Sound(String filePath) {
		this(new Source(), AudioMaster.loadSound(filePath));
	}

	public void play() {
		source.play(soundBuffer);
	}

	public void pause() {
		source.pause(soundBuffer);
	}

	public void stop() {
		source.stop(soundBuffer);
	}

	/**
	 * @return the source
	 */
	public Source getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * @return the soundBuffer
	 */
	public int getSoundBuffer() {
		return soundBuffer;
	}

	/**
	 * @param soundBuffer the soundBuffer to set
	 */
	public void setSoundBuffer(int soundBuffer) {
		this.soundBuffer = soundBuffer;
	}

	/**
	 * @param gain
	 * @return
	 * @see com.engine.audio.Source#setGain(float)
	 */
	public Source setGain(float gain) {
		return source.setGain(gain);
	}

	/**
	 * @param pitch
	 * @return
	 * @see com.engine.audio.Source#setPitch(float)
	 */
	public Source setPitch(float pitch) {
		return source.setPitch(pitch);
	}

	public void enableLooping() {
		source.enableLooping(soundBuffer);
	}

	public void disableLooping() {
		source.disableLooping(soundBuffer);
	}

}
