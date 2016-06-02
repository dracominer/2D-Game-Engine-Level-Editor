package com.engine.render.textures;

public class ModelTexture {

	protected int textureID;

	protected float shineDamper = 1;
	protected float reflectivity = 0;
	protected boolean hasTransparency = false;

	protected int numberOfRows = 1;
	protected boolean isAdditive;

	public ModelTexture(int textureID) {
		this.textureID = textureID;
	}

	public void setAdditive(boolean isAdditive) {
		this.isAdditive = isAdditive;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public ModelTexture setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
		return this;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public boolean hasTransparency() {
		return hasTransparency;
	}

	public ModelTexture setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
		return this;
	}

	public int getID() {
		return textureID;
	}

	public boolean usesAdditive() {
		return isAdditive;
	}

}
