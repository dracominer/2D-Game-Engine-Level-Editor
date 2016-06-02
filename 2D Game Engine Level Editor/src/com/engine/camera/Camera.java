package com.engine.camera;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.engine.render.DisplayManager;
import com.engine.toolbox.Maths;

public class Camera {

	private Vector3f position;
	private Vector3f rotation;

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public Camera(Vector3f position) {
		this(position, new Vector3f(0, 0, 0));
	}

	/**
	 * @return the position
	 */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	/**
	 * @return the rotation
	 */
	public Vector3f getRotation() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setPitch(float pitch) {
		rotation.x = pitch;
	}

	public void setYaw(float yaw) {
		rotation.y = yaw;
	}

	public void setRoll(float roll) {
		rotation.z = roll;
	}

	public float getPitch() {
		return rotation.x;
	}

	public float getYaw() {
		return rotation.y;
	}

	public float getRoll() {
		return rotation.z;
	}

	public Matrix4f getViewMatrix() {
		return Maths.createViewMatrix(this);
	}

	public void increasePosition(int x, int y, float z) {
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
	}

	public void update(float mincamz, float maxcamz, float mouseSensitivity, Vector2f playerPosition) {
		float dz = -Mouse.getDWheel() * mouseSensitivity * DisplayManager.getFrameTimeSeconds();
		increasePosition(0, 0, dz);
		if (position.z < mincamz) position.z = mincamz;
		if (position.z > maxcamz) position.z = maxcamz;
		position.x = playerPosition.x;
		position.y = playerPosition.y;
	}

	public void update(float mincamz, float maxcamz, float mouseSensitivity, Vector3f playerPos) {
		this.update(mincamz, maxcamz, mouseSensitivity, new Vector2f(playerPos.x, playerPos.y));
	}

}
