package com.engine.physics;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.Player;

public class Force extends Vector2f {

	private static final long serialVersionUID = -1604191314019295053L;

	public static final Force CARDINAL_UP = new Force(0, 1, true);
	public static final Force CARDINAL_DOWN = new Force(0, -1, true);
	public static final Force CARDINAL_RIGHT = new Force(0, 1, true);
	public static final Force CARDINAL_LEFT = new Force(0, -1, true);

	public static final Force PLAYER_UP = new Force(0, Player.MOVE_SPEED, true);
	public static final Force PLAYER_DOWN = new Force(0, -Player.MOVE_SPEED, true);
	public static final Force PLAYER_RIGHT = new Force(Player.MOVE_SPEED, 0, true);
	public static final Force PLAYER_LEFT = new Force(-Player.MOVE_SPEED, 0, true);

	private boolean isAffectedByFriction = true;

	private boolean isDead = false;

	public Force(float x, float y, boolean useFriction) {
		super(x, y);
		isAffectedByFriction = useFriction;
	}

	public Force(float x, float y) {
		this(x, y, true);
	}

	public Force() {
		this(0, 0, true);
	}

	public void setIsAffectedByFriction(boolean value) {
		isAffectedByFriction = value;
	}

	public boolean isAffectedByFriction() {
		return isAffectedByFriction;
	}

	/**
	 * @return the isDead
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * @param isDead the isDead to set
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public Force invert() {
		x *= -1;
		y *= -1;
		return this;
	}

	public static Force fromVec(Vector2f vec) {
		return new Force(vec.x, vec.y);
	}

	public static Force getForceFromVelocity(float mass, Vector2f velocity) {
		Vector2f vec = new Vector2f(velocity.x, velocity.y);
		vec.scale(mass);
		return fromVec(vec);
	}

}
