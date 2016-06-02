package com.engine.particle;

import org.lwjgl.util.vector.Vector3f;

import com.engine.level.Level;

public class ParticleSystemSpinning extends ParticleSystem {

	private float averageTurnSpeed = 2f;
	private float error = 0.5f;

	public ParticleSystemSpinning(ParticleTexture texture, float pps, float speed, float gravityComplient, float lifeLength, float scale, Level level) {
		super(texture, pps, speed, gravityComplient, lifeLength, scale, level);
	}

	@Override
	protected void emitParticle(Vector3f center) {
		Vector3f velocity = null;
		if (direction != null) {
			velocity = generateRandomUnitVectorWithinCone(direction, directionDeviation);
		} else {
			velocity = generateRandomUnitVector();
		}
		velocity.normalise();
		velocity.scale(generateValue(averageSpeed, speedError));
		float scale = generateValue(averageScale, scaleError);
		float lifeLength = generateValue(averageLifeLength, lifeError);
		float spin = generateValue(averageTurnSpeed, error);
		float startRotation = ((float) random.nextInt(360)) * random.nextFloat();
		float spinDirection = random.nextBoolean() ? 1 : -1;
		new ParticleSpinning(texture, new Vector3f(center), velocity, gravityComplient, lifeLength, generateRotation(), scale, level, spin).setSpinDirection(spinDirection).setRotation(startRotation);
	}

	/**
	 * @return the defaultTurnSpeed
	 */
	public float getAverageTurnSpeed() {
		return averageTurnSpeed;
	}

	/**
	 * @param defaultTurnSpeed the defaultTurnSpeed to set
	 */
	public void setAverageTurnSpeed(float defaultTurnSpeed) {
		this.averageTurnSpeed = defaultTurnSpeed;
	}

	/**
	 * @return the error
	 */
	public float getSpinSpeedError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setSpinSpeedError(float error) {
		this.error = error;
	}


}
