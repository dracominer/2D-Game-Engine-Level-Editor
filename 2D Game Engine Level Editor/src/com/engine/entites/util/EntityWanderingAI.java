package com.engine.entites.util;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.EntityLiving;
import com.engine.physics.Force;

public class EntityWanderingAI extends EntityAI {

	protected float chanceToStop;
	protected float chanceToTurn;
	protected float turnRange;

	public EntityWanderingAI(EntityLiving owner, Random rand, float chanceToStop, float chanceToTurn, float turnRange) {
		super(owner, rand);
		this.chanceToStop = chanceToStop;
		this.chanceToTurn = chanceToTurn;
		this.turnRange = turnRange;
	}

	@Override
	public void update() {
		Vector2f vel = owner.getVelocity();
		if (rand.nextFloat() < chanceToStop) {
			owner.applyForce(new Force(-vel.x, -vel.y));
		} else if (rand.nextFloat() < chanceToTurn) {
			float dx = (float) (owner.getMoveSpeed() * Math.sin(turnRange * rand.nextFloat()));
			float dy = (float) (owner.getMoveSpeed() * Math.sin(turnRange * rand.nextFloat()));
			Vector2f add = new Vector2f(dx, dy);
			Vector2f.add(vel, add, vel);
		}
		updateRotation(vel);
	}

	/**
	 * @return the chanceToStop
	 */
	public float getChanceToStop() {
		return chanceToStop;
	}

	/**
	 * @return the chanceToTurn
	 */
	public float getChanceToTurn() {
		return chanceToTurn;
	}

	/**
	 * @return the turnRange
	 */
	public float getTurnRange() {
		return turnRange;
	}

	/**
	 * @param chanceToStop the chanceToStop to set
	 */
	public void setChanceToStop(float chanceToStop) {
		this.chanceToStop = chanceToStop;
	}

	/**
	 * @param chanceToTurn the chanceToTurn to set
	 */
	public void setChanceToTurn(float chanceToTurn) {
		this.chanceToTurn = chanceToTurn;
	}

	/**
	 * @param turnRange the turnRange to set
	 */
	public void setTurnRange(float turnRange) {
		this.turnRange = turnRange;
	}

}
