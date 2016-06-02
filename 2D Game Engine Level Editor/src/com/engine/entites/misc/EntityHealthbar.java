package com.engine.entites.misc;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.Entity;
import com.engine.entites.living.EntityLiving;
import com.engine.level.Level;
import com.engine.render.textures.ModelTexture;

public class EntityHealthbar extends Entity {

	protected EntityLiving owner;

	public EntityHealthbar(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture, EntityLiving owner) {
		super(l, position, rotation, scale, modelTexture);
		this.owner = owner;
	}

	public void update() {
		this.percent.x = owner.getHealthPercent();
		this.setPosition(owner.getPosition());
		if (level != null) if (owner == null) level.remove(this);
	}

}
