package com.engine.render.textures;

import org.lwjgl.util.vector.Vector2f;

public class AnimatedTexture extends ModelTexture {

	private int frame = 0;
	private float x = 0;
	private float y = 0;

	public AnimatedTexture(int textureID) {
		super(textureID);
	}

	public AnimatedTexture(ModelTexture modelTexture) {
		super(modelTexture.getID());
		this.setNumberOfRows(modelTexture.getNumberOfRows());
	}

	public void nextFrame() {
		frame++;
		frame %= numberOfRows * numberOfRows;
		x = frame % numberOfRows;
		y = frame / numberOfRows;
	}

	/**
	 * @return the frame
	 */
	public int getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrame(int frame) {
		this.frame = frame;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x / (numberOfRows);
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y / (numberOfRows);
	}

	public Vector2f getOffsets() {
		Vector2f off = new Vector2f(getX(), getY());
		return off;
	}

}
