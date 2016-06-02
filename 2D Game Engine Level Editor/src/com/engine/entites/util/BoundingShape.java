package com.engine.entites.util;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.Entity;
import com.engine.entites.EntityPhysical;

public abstract class BoundingShape {

	protected float x;
	protected float y;

	public BoundingShape(float x, float y) {
		setPosition(x, y);
	}

	public abstract void update(Entity source);

	public abstract boolean collidesWith(BoundingShape target);

	public boolean collidesWith(EntityPhysical target) {
		return collidesWith(target.getBoundingBox());
	}

	public abstract Vector2f getNormal(BoundingShape other);

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public abstract Vector2f getReflectionVector(Vector2f inboundVec, BoundingShape other);

}
