package com.engine.particle;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.engine.camera.Camera;
import com.engine.level.Level;
import com.engine.render.DisplayManager;

public class Particle {

	protected static final float PARTICLE_MIN_HEIGHT = 0.01f;

	protected Vector3f position;
	protected Vector3f velocity;
	protected float gravityEffect;
	protected float lifeLength;
	protected float rotation;
	protected float scale;

	protected float elapsedTime = 0;

	protected Vector2f textureOffset1 = new Vector2f();
	protected Vector2f textureOffset2 = new Vector2f();
	protected float blend;

	/**
	 * the distance the particle is from the camera.
	 * */
	protected float distance;

	protected ParticleTexture texture;

	protected Level level;

	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale, Level level) {
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		this.level = level;
		ParticleMaster.addParticle(this);
	}

	public float getDistance() {
		return distance;
	}

	public float getBlend() {
		return blend;
	}

	public void setBlend(float blend) {
		this.blend = blend;
	}

	public Vector2f getOffset1() {
		return textureOffset1;
	}

	public void setOffset1(Vector2f offset1) {
		this.textureOffset1 = offset1;
	}

	public Vector2f getOffset2() {
		return textureOffset2;
	}

	public void setOffset2(Vector2f offset2) {
		this.textureOffset2 = offset2;
	}

	public ParticleTexture getTexture() {
		return texture;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public float getGravityEffect() {
		return gravityEffect;
	}

	public void setGravityEffect(float gravityEffect) {
		this.gravityEffect = gravityEffect;
	}

	public float getLifeLength() {
		return lifeLength;
	}

	public void setLifeLength(float lifeLength) {
		this.lifeLength = lifeLength;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	protected boolean update(Camera cam) {
		velocity.z += level.getGravity() * gravityEffect * DisplayManager.getFrameTimeSeconds();
		rotation = (float) Math.toDegrees(Math.atan2(velocity.y, velocity.x)) - 90;
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);
		if (position.z <= PARTICLE_MIN_HEIGHT) position.z = PARTICLE_MIN_HEIGHT;
		if (level.isInSolidTile(position)) return false;
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		updateTexture();
		calcDistance(cam);
		return elapsedTime < lifeLength;
	}

	protected void updateTexture() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgress = lifeFactor * (float) stageCount;
		int index1 = (int) Math.floor(atlasProgress);
		int index2 = index1 < stageCount ? index1 + 1 : index1;
		this.blend = atlasProgress % 1;
		setTextureOffset(textureOffset1, index1);
		setTextureOffset(textureOffset2, index2);
	}

	protected void calcDistance(Camera cam) {
		distance = Vector3f.sub(cam.getPosition(), getPosition(), null).lengthSquared();
	}

	protected void setTextureOffset(Vector2f offset, int index) {
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = (float) column / texture.getNumberOfRows();
		offset.y = (float) row / texture.getNumberOfRows();
	}

}
