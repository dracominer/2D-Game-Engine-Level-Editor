package com.engine.particle;

import org.lwjgl.util.vector.Vector3f;

import com.engine.camera.Camera;
import com.engine.level.Level;
import com.engine.render.DisplayManager;

public class ParticleSpinning extends Particle {

	private float turnSpeed = 45;
	private float turnDirection = 1;

	public ParticleSpinning(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale, Level level) {
		super(texture, position, velocity, gravityEffect, lifeLength, rotation, scale, level);
	}

	public ParticleSpinning(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale, Level level, float turnSpeed) {
		this(texture, position, velocity, gravityEffect, lifeLength, rotation, scale, level);
		this.turnSpeed = turnSpeed;
	}

	@Override
	protected boolean update(Camera cam) {
		velocity.z += level.getGravity() * gravityEffect * DisplayManager.getFrameTimeSeconds();
		rotation += turnSpeed * turnDirection * DisplayManager.getFrameTimeSeconds();
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

	public Particle setSpinDirection(float direction) {
		this.turnDirection = direction;
		return this;
	}

}
