package com.engine.entites.living.eco;

import org.lwjgl.util.vector.Vector2f;

import com.engine.level.Level;
import com.engine.render.DisplayManager;
import com.engine.render.textures.AnimatedTexture;
import com.engine.render.textures.ModelTexture;

public class EcoSlime extends EntityEco {

	protected AnimatedTexture anim;
	protected float time = 0;

	public EcoSlime(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
		anim = new AnimatedTexture(modelTexture.getID());
		anim.setNumberOfRows(modelTexture.getNumberOfRows());
	}

	public void update() {
		super.update();
		time += DisplayManager.getFrameTimeSeconds();
		if (time > 0.27f) {
			anim.nextFrame();
			time = 0;
		}
	}

	/**
	 * @return the textureOffsets
	 */
	public Vector2f getTextureOffsets() {
		return anim.getOffsets();
	}

	protected void initializeEntity() {
		super.initializeEntity();
		this.setAverageDeathAge(28);
		this.setMaxSize(0.75f);
		this.setMinSize(0.02f);
		this.setMaxHealth(15);
		this.setRegen(0.98f, true);
		this.setAttackStrength(12.5f);
		this.setGender(GENDER_ASEXUAL);
	}

	public String getEcoType() {
		return "slime";
	}

	@Override
	public EntityEco getBaby() {
		EcoSlime baby = new EcoSlime(getLevel(), getNearbyBabyLocation(), 0, minSize, this.texture);
		baby.setAge(0);
		return baby;
	}

	/**
	 * @return the rotation
	 */
	public float getRotationForRender() {
		return 0;
	}

}
