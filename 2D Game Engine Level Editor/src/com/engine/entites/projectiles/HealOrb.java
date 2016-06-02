package com.engine.entites.projectiles;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.Entity;
import com.engine.entites.living.EntityLiving;
import com.engine.entites.util.LogarithmicValue;
import com.engine.level.Level;
import com.engine.particle.ParticleSystemSpinning;
import com.engine.particle.ParticleTexture;
import com.engine.render.textures.ModelTexture;
import com.engine.render.textures.TextureManager;

public class HealOrb extends MagicOrb {

	public HealOrb(Entity owner, Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(owner, l, position, rotation, scale, modelTexture);
	}

	public HealOrb(Entity owner, ModelTexture texture) {
		super(owner, texture);
	}

	public HealOrb(Entity owner) {
		super(owner);
	}

	@Override
	protected void init() {
		super.particles = new ParticleSystemSpinning(new ParticleTexture(TextureManager.get("particle")).setUsesAdditive(false), 250, 2, 0.01f, 0.75f, 0.25f, level);
		super.particles.setLifeError(0.25f);
		super.particles.setScaleError(2f);
		super.particles.setSpeedError(0.75f);
		this.damage = 5;
	}

	@Override
	protected boolean onInteractWithEntity(EntityLiving e) {
		e.heal(new LogarithmicValue(damage, owner));
		return true;
	}

}
