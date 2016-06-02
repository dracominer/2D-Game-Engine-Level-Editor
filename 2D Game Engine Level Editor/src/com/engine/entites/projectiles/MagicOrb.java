package com.engine.entites.projectiles;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.Entity;
import com.engine.level.Level;
import com.engine.particle.ParticleSystemSpinning;
import com.engine.particle.ParticleTexture;
import com.engine.render.textures.ModelTexture;
import com.engine.render.textures.TextureManager;

public class MagicOrb extends Projectile {

	public MagicOrb(Entity owner, Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(owner, l, position, rotation, scale, modelTexture);
		init();
	}

	public MagicOrb(Entity owner, ModelTexture texture) {
		super(owner, texture);
		init();
	}

	public MagicOrb(Entity owner) {
		super(owner, TextureManager.getTexture("magic_orb"));
		init();
	}

	protected void init() {
		super.particles = new ParticleSystemSpinning(new ParticleTexture(TextureManager.get("particle")).setUsesAdditive(false), 250, 2, 0.01f, 0.75f, 0.25f, level);
		super.particles.setLifeError(0.25f);
		super.particles.setScaleError(2f);
		super.particles.setSpeedError(0.75f);
	}

	@Override
	protected float getMinSpeedValue() {
		return 2f;
	}

}
