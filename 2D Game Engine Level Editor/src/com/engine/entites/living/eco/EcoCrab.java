package com.engine.entites.living.eco;

import org.lwjgl.util.vector.Vector2f;

import com.engine.level.Level;
import com.engine.render.textures.ModelTexture;

public class EcoCrab extends EntityEco {

	public EcoCrab(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
		//EcoFwog
	}

	protected void initializeEntity() {
		super.initializeEntity();
		this.setAverageDeathAge(20);
		this.setMaxSize(0.5f);
		this.setMaxHealth(25);
		this.setRegen(0.5f, true);
		this.setAttackStrength(12.5f);
	}

	public String getEcoType() {
		return "crab";
	}

	@Override
	public EntityEco getBaby() {
		EcoCrab crab = new EcoCrab(getLevel(), getNearbyBabyLocation(), 0, minSize, getTexture());
		crab.setAge(0);
		return crab;
	}

	@Override
	public boolean willCannibalize() {
		return false;
	}

}
