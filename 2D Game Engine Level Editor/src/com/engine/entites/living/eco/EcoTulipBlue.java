package com.engine.entites.living.eco;

import org.lwjgl.util.vector.Vector2f;

import com.engine.level.Level;
import com.engine.render.textures.ModelTexture;

public class EcoTulipBlue extends EcoPlant {

	public EcoTulipBlue(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
	}

	@Override
	public EntityEco getBaby() {
		EcoTulipBlue tulip = new EcoTulipBlue(getLevel(), getBirthLocation(), 0, minSize, getTexture());
		return tulip;
	}

}
