package com.engine.entites;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.util.BoundingCircle;
import com.engine.level.Level;
import com.engine.physics.Force;
import com.engine.render.DisplayManager;
import com.engine.render.textures.ModelTexture;

public class EntityPhysical extends Entity {

	private static final float MINIMUM_MOTION = 0.01f;

	protected Vector2f motion = new Vector2f();

	protected BoundingCircle shape;

	protected boolean isAtRest = true;

	protected boolean canCollide = true;

	/**
	 * the amount of force remaining after a collision
	 * */
	protected float restitution = 0.9f;

	protected float dx = 0;
	protected float dy = 0;

	public EntityPhysical(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
		shape = new BoundingCircle(this, scale);
	}

	public void applyForce(Force force) {
		motion.x += force.x * DisplayManager.getFrameTimeSeconds();
		motion.y += force.y * DisplayManager.getFrameTimeSeconds();
	}

	public void applyForce(Force force, EntityPhysical source) {
		motion.x += force.x * DisplayManager.getFrameTimeSeconds();
		motion.y += force.y * DisplayManager.getFrameTimeSeconds();
		if (source != null) source.applyForce(force.invert());
	}

	public void setForce(Force force) {
		motion.x += force.x;
		motion.y += force.y;
	}

	public void update() {
		doPhysics();
	}

	protected void doPhysics() {
		super.update();
		shape.update(this);
		move();
		canCollide = true;
	}

	protected boolean collision(float dx, float dy) {
		if (level.futureTileCollision(this, dx, 0)) { return true; }
		if (level.futureTileCollision(this, 0, dy)) { return true; }
		return false;
	}

	public void invertVelocity() {
		motion.x *= -1;
		motion.y *= -1;
	}

	protected void move() {
		motion.x *= getFrictionAtLocation();
		motion.y *= getFrictionAtLocation();
		dx = motion.x * DisplayManager.getFrameTimeSeconds();
		dy = motion.y * DisplayManager.getFrameTimeSeconds();
		checkVelocity();
		if (!isAtRest) {
			moveX(dx);
			moveY(dy);
		}
	}

	protected void moveX(float amount) {
		if (collision(amount, 0)) {
			onXCollision(amount);
		} else {
			increasePosition(amount, 0);
		}
	}

	protected void onXCollision(float amount) {
		this.motion.x *= -restitution * level.getRestitutionAtLocation(position.x + amount, position.y);
		increasePosition(motion.x * DisplayManager.getFrameTimeSeconds(), 0);
	}

	protected void moveY(float amount) {
		if (collision(0, amount)) {
			onYCollision(amount);
		} else {
			increasePosition(0, amount);
		}
	}

	protected void onYCollision(float amount) {
		this.motion.y *= -restitution * level.getRestitutionAtLocation(position.x, position.y + amount);
		increasePosition(0, motion.y * DisplayManager.getFrameTimeSeconds());
	}

	protected void checkVelocity() {
		if (Math.abs(motion.x) < MINIMUM_MOTION) motion.x = 0;
		if (Math.abs(motion.y) < MINIMUM_MOTION) motion.y = 0;
		isAtRest = motion.x == 0 && motion.y == 0;
	}

	protected float getFrictionAtLocation() {
		return level.getFrictionAtLocation(getEntityCenter());
	}

	/**
	 * @return the box
	 */
	public BoundingCircle getBoundingBox() {
		return shape;
	}

	/**
	 * @param box the box to set
	 */
	public void setBoundingShape(BoundingCircle box) {
		this.shape = box;
	}

	/**
	 * @return the velocity
	 */
	public Vector2f getVelocity() {
		return motion;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(Vector2f velocity) {
		this.motion = velocity;
	}

	public float getCloseness(EntityPhysical other) {
		Vector2f otherPos = other.getEntityCenter();
		Vector2f thisPos = getEntityCenter();
		float dx = otherPos.x - thisPos.x;
		float dy = otherPos.y - thisPos.y;
		float distance = (float) Math.sqrt((dx * dx) + (dy * dy));
		float maxDistance = (other.scale * 2) + (scale * 2);
		return maxDistance / distance;
	}

	public boolean isAtRest() {
		return isAtRest;
	}

	/**
	 * @return the motion
	 */
	public Vector2f getMotion() {
		return motion;
	}

	/**
	 * @param motion the motion to set
	 */
	public void setMotion(Vector2f motion) {
		this.motion = motion;
	}

	/**
	 * @return the shape
	 */
	public BoundingCircle getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(BoundingCircle shape) {
		this.shape = shape;
	}

	/**
	 * @return the canCollide
	 */
	public boolean isCanCollide() {
		return canCollide;
	}

	/**
	 * @param canCollide the canCollide to set
	 */
	public void setCanCollide(boolean canCollide) {
		this.canCollide = canCollide;
	}

	/**
	 * @return the restitution
	 */
	public float getRestitution() {
		return restitution;
	}

	/**
	 * @param restitution the restitution to set
	 */
	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	/**
	 * @return the dx
	 */
	public float getDx() {
		return dx;
	}

	/**
	 * @param dx the dx to set
	 */
	public void setDx(float dx) {
		this.dx = dx;
	}

	/**
	 * @return the dy
	 */
	public float getDy() {
		return dy;
	}

	/**
	 * @param dy the dy to set
	 */
	public void setDy(float dy) {
		this.dy = dy;
	}

	/**
	 * @param isAtRest the isAtRest to set
	 */
	public void setAtRest(boolean isAtRest) {
		this.isAtRest = isAtRest;
	}

}
