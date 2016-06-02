package com.engine.entites;

import org.lwjgl.util.vector.Vector2f;

import com.engine.level.Level;
import com.engine.render.textures.ModelTexture;

public class PlayerCompass extends Entity {

	private static final float SCALE_RELATIVE_TO_PLAYER = 1.4f;

	public PlayerCompass(Level l, ModelTexture modelTexture) {
		super(l, new Vector2f(0, 0), 0, 1, modelTexture);
		this.setScale(level.getPlayer().scale * SCALE_RELATIVE_TO_PLAYER);

	}

	public void update() {
		super.update();
		setPosition(level.getPlayer().getPosition());
		setRotation(level.getPlayer().getRotationTowardsMouse());
	}

}
