package com.engine.entites.util;

import java.util.Random;

import com.engine.entites.living.eco.EntityEco;
import com.engine.level.Level;

public abstract class AIEco extends EntityAI {

	protected Level level;

	protected EntityEco ownerEco;

	public AIEco(EntityEco owner, Random rand) {
		super(owner, rand);
		ownerEco = owner;
		level = owner.getLevel();
	}

}
