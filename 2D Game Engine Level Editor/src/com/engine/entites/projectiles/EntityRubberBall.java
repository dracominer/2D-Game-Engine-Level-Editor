package com.engine.entites.projectiles;

import com.engine.entites.Entity;
import com.engine.render.DisplayManager;

public class EntityRubberBall extends Projectile {

	protected float restitution = 0.9897f;

	public EntityRubberBall(Entity owner) {
		super(owner);
	}

	protected float moveX() {
		velX *= getAirDragConstant();
		float dx = velX * DisplayManager.getFrameTimeSeconds();
		if (level.futureTileCollision(this, dx, 0)) {
			velX *= -restitution * level.getRestitutionAtLocation(position.x + dx, position.y);
			dx = velX * DisplayManager.getFrameTimeSeconds();
		}
		this.increasePosition(dx, 0);
		return dx;
	}

	protected boolean emitParticles() {
		return false;
	}

	protected float moveY() {
		velY *= getAirDragConstant();
		float dy = velY * DisplayManager.getFrameTimeSeconds();
		if (level.futureTileCollision(this, 0, dy)) {
			velY *= -restitution * level.getRestitutionAtLocation(position.x, position.y + dy);
			dy = velY * DisplayManager.getFrameTimeSeconds();
		}
		this.increasePosition(0, dy);
		return dy;
	}

}
