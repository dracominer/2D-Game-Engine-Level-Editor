package com.engine.entites.util;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.Entity;
import com.engine.entites.EntityPhysical;

public class BoundingBox extends BoundingShape {

	private static final float ALLOWANCE = 0.25f;

	public float left;
	public float right;
	public float top;
	public float bottom;

	private final float RELATIVE_left;
	private final float RELATIVE_right;
	private final float RELATIVE_top;
	private final float RELATIVE_bottom;

	public BoundingBox(EntityPhysical owner, float left, float right, float top, float bottom) {
		super(0, 0);
		RELATIVE_left = left;
		RELATIVE_right = right;
		RELATIVE_top = top;
		RELATIVE_bottom = bottom;
		update(owner);
	}

	public BoundingBox(EntityPhysical owner, float size) {
		super(0, 0);
		RELATIVE_left = -size;
		RELATIVE_right = size;
		RELATIVE_top = size;
		RELATIVE_bottom = -size;
		update(owner);
	}

	@Override
	public void update(Entity source) {
		Vector2f pos = source.getEntityCenter();
		this.setPosition(pos.x, pos.y);
	}

	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		left = RELATIVE_left + x;
		right = RELATIVE_right + x;
		top = RELATIVE_top + y;
		bottom = RELATIVE_bottom + y;
	}

	/**
	 * if (rect1.left < rect2.right &&
	rect1.x + rect1.width > rect2.x &&
	rect1.y < rect2.y + rect2.height &&
	rect1.height + rect1.y > rect2.y) {
	// collision detected!
	}
	 * */

	@Override
	public boolean collidesWith(BoundingShape target) {
		if (target instanceof BoundingBox) {
			BoundingBox other = (BoundingBox) target;
			return (other.left < right) && (other.right > left) && (other.bottom < top) && (other.top > bottom);
		}
		return false;
	}

	@Override
	public Vector2f getNormal(BoundingShape other) {
		float dx = other.x - this.x;
		float dy = other.x - this.x;
		Vector2f normal = new Vector2f(dx, dy);
		normal.normalise();
		return null;
	}

	public Vector2f getReflectionVector(EntityPhysical other) {
		return getReflectionVector(other.getVelocity(), other.getBoundingBox());
	}

	public Vector2f getReflectionVector(Vector2f inboundVec, BoundingShape other) {
		if (other instanceof BoundingBox) {
			BoundingBox box = (BoundingBox) other;
			Vector2f vec = new Vector2f(inboundVec.x, inboundVec.y);
			if (box.right - ALLOWANCE <= left || box.left + ALLOWANCE >= right) {
				// invert y axis
				vec.y *= -1;
			} else if (box.top - ALLOWANCE <= bottom || box.bottom + ALLOWANCE >= top) {
				//invert x axis
				vec.x *= -1;
			}
			return vec;
		}
		return new Vector2f(0, 0);
	}

}
