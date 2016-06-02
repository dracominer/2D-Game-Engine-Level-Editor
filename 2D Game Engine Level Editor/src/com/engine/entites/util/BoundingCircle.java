package com.engine.entites.util;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.Entity;
import com.engine.entites.EntityPhysical;

public class BoundingCircle extends BoundingShape {

	public float radius = 0;

	public BoundingCircle(EntityPhysical source, float radius) {
		super(0, 0);
		update(source);
		this.radius = Math.abs(radius);
	}

	@Override
	public void update(Entity source) {
		Vector2f pos = source.getEntityCenter();
		this.radius = source.getScale();
		setPosition(pos.x, pos.y);
	}

	@Override
	public boolean collidesWith(BoundingShape target) {
		if (target instanceof BoundingCircle) {
			BoundingCircle other = (BoundingCircle) target;
			float dx = x - other.x;
			float dy = y - other.y;
			float dist = (float) Math.sqrt((dx * dx) + (dy * dy));
			return (dist < radius + other.radius);
		}
		return false;
	}

	@Override
	public Vector2f getNormal(BoundingShape other) {
		float dx = this.x - other.x;
		float dy = this.x - other.x;
		Vector2f normal = new Vector2f(dx, dy);
		normal.normalise();
		return normal;
	}

	@Override
	public Vector2f getReflectionVector(Vector2f inboundVec, BoundingShape other) {

		return null;
	}

	public Vector2f getReflectionVector(EntityPhysical other) {
		return getReflectionVector(other.getVelocity(), other.getBoundingBox());
	}

}
