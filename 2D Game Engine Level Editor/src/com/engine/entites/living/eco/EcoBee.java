package com.engine.entites.living.eco;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.util.AIEco;
import com.engine.entites.util.FlockingAI;
import com.engine.level.Level;
import com.engine.render.textures.ModelTexture;

public class EcoBee extends EntityEco {

	public EcoBee(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
	}

	protected void initializeEntity() {
		super.initializeEntity();
		this.setMinSize(0.0625f);
		this.setMaxSize(0.125f);
		this.setAverageDeathAge(100);
		this.setMoveSpeed(1.3f);
	}

	protected AIEco getAI() {
		FlockingAI ai = new FlockingAI(this, new Random());
		ai.setAlignmentWeight(0.6f);
		ai.setCohesionWeight(0.8f);
		ai.setSeperationWeight(0.75f);
		ai.setCurrentMovementWeight(0.89f);
		return ai;
	}

	/**
	 * this is the identifying type of the entity
	 * */
	public String getEcoType() {
		return "bee";
	}

	@Override
	public EntityEco getBaby() {
		EcoBee bee = new EcoBee(level, getNearbyBabyLocation(), 0, minSize, texture);
		bee.setAge(0);
		return bee;
	}

}
