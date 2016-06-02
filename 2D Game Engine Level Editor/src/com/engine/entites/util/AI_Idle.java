package com.engine.entites.util;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.eco.EntityEco;
import com.engine.physics.Force;
import com.engine.render.DisplayManager;

public class AI_Idle extends AIEco {

	protected float chanceToStop;
	protected float chanceToTurn;
	protected float turnRange;
	protected float maxIdleTime = 3.5f;
	protected float time = 0;
	protected AI_Eco_SystemEntity mainAI;

	public AI_Idle(EntityEco owner, Random rand, float chanceToStop, float chanceToTurn, float turnRange, AI_Eco_SystemEntity main) {
		super(owner, rand);
		this.chanceToStop = chanceToStop;
		this.chanceToTurn = chanceToTurn;
		this.turnRange = turnRange;
		mainAI = main;
	}

	@Override
	public void update() {
		time += DisplayManager.getFrameTimeSeconds();
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

	public boolean shouldIdle() {
		return time > maxIdleTime;
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
