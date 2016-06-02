package com.engine.lights;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Light {

	public static final Light DEFAULT = new Light(new Vector2f(0, 0), new Vector3f(0, 0, 0));
	protected Vector2f position;
	protected Vector3f color;
	protected Vector3f attenuation;

	public Light(Vector2f position, Vector3f color, Vector3f attenuation) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
	}

	public Light(Vector2f position, Vector3f color) {
		this(position, color, new Vector3f(1, 0, 0));
	}

	/**
	 * @return the position
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	/**
	 * @return the color
	 */
	public Vector3f getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Vector3f color) {
		this.color = color;
	}

	/**
	 * @return the attenuation
	 */
	public Vector3f getAttenuation() {
		return attenuation;
	}

	/**
	 * @param attenuation the attenuation to set
	 */
	public void setAttenuation(Vector3f attenuation) {
		this.attenuation = attenuation;
	}

}
