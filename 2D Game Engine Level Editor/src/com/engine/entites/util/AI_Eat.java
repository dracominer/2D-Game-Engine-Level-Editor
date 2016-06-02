package com.engine.entites.util;

import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.EntityLiving;
import com.engine.entites.living.eco.EntityEco;
import com.engine.render.DisplayManager;

public class AI_Eat extends AIEco {

	protected float maxSearchRange = 10f;

	protected EntityEco target = null;

	protected float timeSinceLastAttack = 0;
	protected float attackCooldown = 0.5f;
	protected float attackMaxOffset = 5f;
	protected AI_Eco_SystemEntity mainAI;

	public AI_Eat(EntityEco owner, Random rand, AI_Eco_SystemEntity main) {
		super(owner, rand);
		mainAI = main;
	}

	@Override
	public void update() {
		timeSinceLastAttack += DisplayManager.getFrameTimeSeconds();
		updateTarget();
		if (target != null) {
			move();
		}
	}

	private void updateTarget() {
		if (target != null) {
			if (!target.isAlive()) target = null;
		}
		if (target == null) {
			List<EntityEco> ecos = level.getEcoEntities(ownerEco, maxSearchRange);
			if (ecos == null) return;
			if (ecos.isEmpty()) return;
			float len = Float.MAX_VALUE;
			for (EntityEco e : ecos) {
				if (isViableTarget(e)) {
					Vector2f diff = Vector2f.sub(e.getEntityCenter(), ownerEco.getEntityCenter(), null);
					if (diff.length() < len) {
						len = diff.length();
						target = e;
					}
				}
			}
		}
	}

	private boolean isViableTarget(EntityEco e) {
		if (e == owner) return false;
		//		if (e.getChallengeRating() > ownerEco.getChallengeRating()) return false;
		if (!ownerEco.canEat(e)) return false;
		return true;
	}

	private void move() {
		Vector2f vec = getVectorToTarget();
		float dist = vec.length();
		if (dist < ownerEco.getEntityReach()) {
			if (timeSinceLastAttack > attackCooldown) consume();
		} else {
			vec.normalise();
			vec.scale(ownerEco.getMoveSpeed());
			ownerEco.setVelocity(vec);
		}
		updateRotation(ownerEco.getVelocity());
	}

	private Vector2f getVectorToTarget() {
		return Vector2f.sub(target.getEntityCenter(), ownerEco.getEntityCenter(), null);
	}

	private void consume() {
		if (target.isAlive()) {
			float damage = ownerEco.getAttackStrength(EntityLiving.MELEE) + (2f * rand.nextFloat() * (rand.nextBoolean() ? 1 : -1));
			target.damage(new LogarithmicValue(damage, ownerEco));
		}
		timeSinceLastAttack = 0 - (rand.nextFloat() * attackMaxOffset);
	}

}
