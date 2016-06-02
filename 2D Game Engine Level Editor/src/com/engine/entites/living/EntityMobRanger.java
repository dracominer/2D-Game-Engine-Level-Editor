package com.engine.entites.living;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.util.AIMobRanged;
import com.engine.level.Level;
import com.engine.render.textures.ModelTexture;

public class EntityMobRanger extends EntityMob {

	public EntityMobRanger(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
		this.ai = new AIMobRanged(this, level.getRandom());
	}

	@Override
	protected void initializeEntity() {
		super.initializeEntity();
		this.ai = new AIMobRanged(this, level.getRandom());
	}

}
