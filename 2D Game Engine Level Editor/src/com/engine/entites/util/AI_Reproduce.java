package com.engine.entites.util;

import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.eco.EntityEco;

public class AI_Reproduce extends AIEco {

	protected EntityEco mate = null;
	protected float searchRange = 10;
	protected float chanceToForgetMate = 0.125f;
	protected AI_Eco_SystemEntity mainAI;

	public AI_Reproduce(EntityEco owner, Random rand, AI_Eco_SystemEntity main) {
		super(owner, rand);
		mainAI = main;
	}

	@Override
	public void update() {
		if (rand.nextFloat() <= chanceToForgetMate) mate = null;
		if (ownerEco.getGender() != EntityEco.GENDER_ASEXUAL) {
			if (mate != null) {
				if (!mate.isAlive()) {
					mate = null;
					ownerEco.setHasAMate(false);
				}
			}
			if (mate == null) {
				updateMate();
			} else {
				moveToMate();
			}
		} else {
			asexuallyReproduce();
		}
	}

	private void updateMate() {
		List<EntityEco> list = level.getEcoEntities(ownerEco, searchRange);
		if (list == null) return;
		if (list.isEmpty()) return;
		float bestCompatibility = -1;
		for (EntityEco e : list) {
			if (isViableMate(e)) {
				float compatibility = ownerEco.getCompatibility(e);
				if (compatibility > bestCompatibility) {
					mate = e;
					bestCompatibility = compatibility;
					mate.setHasAMate(true);
					ownerEco.setHasAMate(true);
				}
			}
		}
	}

	private boolean isViableMate(EntityEco e) {
		if (e.hasAMate()) return false;
		return ownerEco.isViableMate(e);
	}

	private void moveToMate() {
		Vector2f vec = getVectorToTarget();
		float dist = vec.length();
		if (dist < ownerEco.getEntityReach()) {
			mate();
		} else {
			vec.normalise();
			vec.scale(ownerEco.getMoveSpeed());
			ownerEco.setVelocity(vec);
		}
		updateRotation(ownerEco.getVelocity());
	}

	private Vector2f getVectorToTarget() {
		return Vector2f.sub(mate.getEntityCenter(), ownerEco.getEntityCenter(), null);
	}

	private void mate() {
		if (mate.isFertile()) {
			if (ownerEco.getGender() == EntityEco.GENDER_FEMALE) {
				EntityEco baby = ownerEco.getBaby();
				if (rand.nextFloat() <= baby.getChanceOfDeathAtBirth()) {
					baby.die();
				}
				if (rand.nextFloat() <= ownerEco.getChanceOfDeathAfterGivingBirth()) {
					ownerEco.die();
				}
				level.add(baby);
				ownerEco.setTimeSinceReproduction(0);
				System.out.println("reproduced with " + mate.getEcoType());
			}
		}
	}

	private void asexuallyReproduce() {
		EntityEco baby = ownerEco.getBaby();
		level.add(baby);
		ownerEco.setTimeSinceReproduction(0);
	}

}
