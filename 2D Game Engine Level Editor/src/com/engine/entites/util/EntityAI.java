package com.engine.entites.util;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.EntityLiving;

public abstract class EntityAI {

	protected EntityLiving owner;
	protected Random rand;

	public EntityAI(EntityLiving owner, Random rand) {
		this.owner = owner;
		this.rand = rand;
	}

	public abstract void update();

	protected void updateRotation(Vector2f motion) {
		owner.setRotation((float) Math.toDegrees(Math.atan2(motion.y, motion.x)) - 90f);
	}

}
