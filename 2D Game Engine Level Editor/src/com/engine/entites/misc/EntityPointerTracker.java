package com.engine.entites.misc;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.Entity;
import com.engine.level.Level;
import com.engine.particle.ParticleSystem;
import com.engine.particle.ParticleTexture;
import com.engine.render.textures.ModelTexture;

public class EntityPointerTracker extends Entity {

	ParticleSystem part;

	public EntityPointerTracker(Level l, Vector2f position, float scale, ModelTexture modelTexture) {
		super(l, position, 0, scale, modelTexture);
		part = new ParticleSystem(new ParticleTexture(modelTexture.getID()), 100, 0.1f, -0.04f, 5, 0.25f, level);
	}

	public void update() {
		super.update();
	}

}
