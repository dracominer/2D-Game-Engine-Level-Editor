package com.engine.particle;

public class ParticleTexture {

	private int textureID;
	private int numberOfRows;
	private boolean usesAdditive = false;

	public ParticleTexture(int textureID, int numberOfRows, boolean additive) {
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
		usesAdditive = additive;
	}

	public ParticleTexture(int textureID, int numberOfRows) {
		this(textureID, numberOfRows, false);
	}

	public ParticleTexture(int textureID) {
		this(textureID, 1, false);
	}

	public boolean usesAdditive() {
		return usesAdditive;
	}

	public ParticleTexture setUsesAdditive(boolean usesAdditive) {
		this.usesAdditive = usesAdditive;
		return this;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

}
