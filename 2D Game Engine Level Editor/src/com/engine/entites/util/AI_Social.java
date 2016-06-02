package com.engine.entites.util;

import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.eco.EntityEco;

public class AI_Social extends AIEco {

	protected EntityEco friend = null;
	protected float searchRange = 5;
	protected float chanceToForgetFriend = 0.25f;
	protected AI_Eco_SystemEntity mainAI;
	protected float xpForSocialization = 0.5f;

	public AI_Social(EntityEco owner, Random rand, AI_Eco_SystemEntity main) {
		super(owner, rand);
		mainAI = main;
	}

	@Override
	public void update() {
		if (rand.nextFloat() <= chanceToForgetFriend) friend = null;
		if (friend != null) {
			if (!friend.isAlive()) {
				friend = null;
				ownerEco.setHasAFriend(false);
			}
		}
		if (friend == null) {
			mainAI.getIdle().update();
			updateFriend();
		} else {
			moveToFriend();
		}
	}

	private void updateFriend() {
		List<EntityEco> list = level.getEcoEntities(ownerEco, searchRange);
		if (list == null) return;
		if (list.isEmpty()) return;
		float bestCompatibility = -1;
		for (EntityEco e : list) {
			if (!isCompatible(e)) continue;
			float compatibility = ownerEco.getSocialCompatibility(e);
			if (compatibility > bestCompatibility) {
				friend = e;
			}
		}
		if (friend != null) {
			ownerEco.setHasAFriend(true);
			friend.setHasAFriend(true);
		}
	}

	private boolean isCompatible(EntityEco other) {
		if (ownerEco.getEcoType().equals(other.getEcoType())) return true;
		return false;
	}

	private void moveToFriend() {
		Vector2f vec = getVectorToTarget();
		float dist = vec.length();
		if (dist < ownerEco.getEntityReach()) {
			socialize();
		} else {
			vec.normalise();
			vec.scale(ownerEco.getMoveSpeed());
			ownerEco.setVelocity(vec);
		}
		updateRotation(ownerEco.getVelocity());
	}

	private Vector2f getVectorToTarget() {
		return Vector2f.sub(friend.getEntityCenter(), ownerEco.getEntityCenter(), null);
	}

	private void socialize() {
		friend.changeXP(xpForSocialization);
		ownerEco.setTimeSinceSocialContact(0);
		//		System.out.println("socialized with " + friend.getEcoType());
	}

}
