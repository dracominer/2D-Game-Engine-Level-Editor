package com.engine.entites.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.Entity;
import com.engine.entites.living.eco.EntityEco;
import com.engine.physics.Force;
import com.engine.render.DisplayManager;

public class AI_Flee extends AIEco {

	protected float maxSearchRange = 20f;
	protected float importanceSearchRange = 5f;

	protected float maxEnemiesBeforePanic = 3;

	protected float panicAmount = 0.75f;

	protected List<EntityEco> enemies = new ArrayList<EntityEco>();
	protected float refreshEnemiesRate = 1.5f;
	protected float time = 0;
	protected AI_Eco_SystemEntity mainAI;

	public AI_Flee(EntityEco owner, Random rand, AI_Eco_SystemEntity main) {
		super(owner, rand);
		mainAI = main;
	}

	@Override
	public void update() {
		time += DisplayManager.getFrameTimeSeconds();
		if (time > refreshEnemiesRate) {
			updateTargets();
			time = 0;
		}
		updateTargets();
		if (enemies.isEmpty()) {
			mainAI.getIdle().update();
		} else {
			move();
		}
	}

	public void updateTargets() {
		enemies.clear();
		Entity last = ownerEco.getLastAttacker();
		if (last != null) {
			if (last instanceof EntityEco) {
				EntityEco other = (EntityEco) last;
				if (other.canEat(ownerEco)) enemies.add(other);
			}
		}
		List<EntityEco> ecos = level.getEcoEntities(ownerEco, maxSearchRange);
		if (ecos == null) return;
		if (ecos.isEmpty()) return;
		for (EntityEco e : ecos) {
			if (isViableTarget(e)) {
				enemies.add(e);
			}
		}
	}

	private boolean isViableTarget(EntityEco e) {
		if (e == owner) return false;
		boolean flag = true;
		if (getVectorToTarget(e).length() >= importanceSearchRange) flag = false;
		if (e.getChallengeRating() < ownerEco.getChallengeRating()) flag = false;
		if (!e.canEat(ownerEco)) flag = false;
		return flag;
	}

	private void move() {
		if (enemies.isEmpty()) return;
		for (EntityEco e : enemies) {
			owner.applyForce(getFleeingForce(e));
		}
		updateRotation(owner.getVelocity());
	}

	private Force getFleeingForce(EntityEco e) {
		return Force.fromVec(getFleeingVector(e));
	}

	private Vector2f getFleeingVector(EntityEco e) {
		Vector2f vec = getVectorToTarget(e);
		if (vec.length() > 0) {
			vec.normalise();
			vec.scale(-ownerEco.getMoveSpeed());
		}
		return vec;
	}

	private Vector2f getVectorToTarget(Entity e) {
		return Vector2f.sub(e.getEntityCenter(), ownerEco.getEntityCenter(), null);
	}

	/**
	 * returns the urgency of eating where 1 is top priority and 0 is no priority.
	 * */
	public float needsToFlee() {
		if (enemies.isEmpty()) return -1;
		float per = enemies.size() / maxEnemiesBeforePanic;
		return per;
	}

	public boolean shouldFlee() {
		return needsToFlee() <= panicAmount;
	}

}
