package com.engine.entites.living.eco;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.util.AIEco;
import com.engine.entites.util.PlantAI;
import com.engine.level.Level;
import com.engine.render.textures.ModelTexture;

public abstract class EcoPlant extends EntityEco {

	protected int birthRange = 5;
	protected float minDistanceFromParent = 0.5f;

	public EcoPlant(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
	}

	protected void initializeEntity() {
		super.initializeEntity();
		this.setMaxSize(0.75f);
		this.setAverageDeathAge(30);
		this.setMinSize(0.1f);
		this.setFertileAgeMin(3f);
		this.setFertileAgeMax(20f);
		this.setChanceOfDeathDuringLife(0.0001f);
		this.setReproductionRate(0.09f);
	}

	protected AIEco getAI() {
		PlantAI ai = new PlantAI(this, new Random());
		return ai;
	}

	protected Vector2f getBirthLocation() {
		Random rand = level.getRandom();
		float dx = (rand.nextFloat() * (rand.nextBoolean() ? 1 : -1)) * ((float) rand.nextInt(birthRange));
		float dy = (rand.nextFloat() * (rand.nextBoolean() ? 1 : -1)) * ((float) rand.nextInt(birthRange));
		dx = Float.max(dx, minDistanceFromParent);
		dy = Float.max(dy, minDistanceFromParent);
		return new Vector2f(position.x + dx, position.y + dy);
	}

	public float getHungerDecreaseRate() {
		return 0.0009f * getFoodValue();
	}

	/**
	 * @return the rotation
	 */
	public float getRotationForRender() {
		return 0;
	}

	protected boolean hasHealthBar() {
		return false;
	}

	public boolean isViableMate(EntityEco mate) {
		if (mate == this) return false;
		if (mate.getEcoType().equals(getEcoType())) { return mate.getAge() >= (getAge() - getAgeOffsetForMateCalc()); }
		return false;
	}

	public String getEcoType() {
		return "unnamed plant";
	}

	protected float getAgeOffsetForMateCalc() {
		return 1.5f;
	}

	protected void performLevelUpAnimation() {
		//Do nothing
	}

	protected void performHealAnimation(float healAmount) {
		//Do nothing
	}

	protected void performHurtAnimation(float damage) {
		//Do nothing
	}

}
