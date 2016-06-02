package com.engine.entites.living.eco;

import org.lwjgl.util.vector.Vector2f;

import com.engine.level.Level;
import com.engine.render.textures.ModelTexture;

public class EcoFwog extends EntityEco {

	public EcoFwog(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
	}

	protected void initializeEntity() {
		super.initializeEntity();
		this.setAverageDeathAge(20);
		this.setMaxSize(0.56f);
		this.setMaxHealth(25);
		this.setRegen(0.5f, true);
		this.setAttackStrength(12.5f);
	}

	public String getEcoType() {
		return "fwog";
	}

	@Override
	public EntityEco getBaby() {
		EcoFwog fwog = new EcoFwog(getLevel(), getNearbyBabyLocation(), 0, minSize, getTexture());
		fwog.setAge(0);
		return fwog;
	}

}
