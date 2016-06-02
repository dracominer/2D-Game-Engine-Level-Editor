package com.engine.entites.projectiles;

import java.util.Random;

import com.engine.entites.Entity;
import com.engine.render.textures.ModelTexture;

public class EntityArrow extends Projectile {

	private final float maxDistance;

	public EntityArrow(Entity owner, float maxDistance) {
		super(owner);
		this.maxDistance = maxDistance;
		init();
	}

	public EntityArrow(Entity owner, ModelTexture texture, float maxDistance) {
		super(owner, texture);
		this.maxDistance = maxDistance;
		init();
	}

	public EntityArrow(Entity owner, float maxDistance, float speed) {
		super(owner);
		this.maxDistance = maxDistance;
		this.fire(speed);
		init();
	}

	public EntityArrow(Entity owner, ModelTexture texture, float maxDistance, float speed) {
		super(owner, texture);
		this.maxDistance = maxDistance;
		this.fire(speed);
		init();
	}

	/**
	 * this will actually start the prjectile moving. Without this function, it wont start moving
	 * */
	public Projectile fire(float metersPerSecond) {
		Random r = new Random();
		float offAngle = r.nextFloat() * inaccuracy * (r.nextBoolean() ? 1 : -1);
		this.rotation = owner.getRotation() + offAngle;
		this.speed = metersPerSecond;
		velX = (float) -(speed * Math.sin(Math.toRadians(rotation)));
		velY = (float) (speed * Math.cos(Math.toRadians(rotation)));
		return this;
	}

	public EntityArrow setInnacuracy(float value) {
		this.inaccuracy = value;
		return this;
	}

	private void init() {
		this.setScale(owner.getScale() * 0.25f);
	}

	@Override
	protected void checkIsStillAlive() {
		super.checkIsStillAlive();
		if (level.isInSolidTile(this)) level.remove(this);
		if (distanceTraveled > maxDistance) {
			level.remove(this);
		}
	}

	@Override
	protected float getAirDragConstant() {
		return 0.9999f;
	}

	public float getAttackCooldown() {
		return level.getRandom().nextFloat() + 0.1f;
	}

}
