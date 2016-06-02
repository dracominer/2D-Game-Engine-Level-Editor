package com.engine.lights;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.engine.entites.living.Player;

public class PlayerLight extends Light {

	public PlayerLight() {
		super(new Vector2f(0, 0), new Vector3f(1, 1, 1), new Vector3f(1, 0.2f, 0.04f));
	}

	public void update(Player player) {
		Vector2f pos = player.getEntityCenter();
		position.x = pos.x;
		position.y = pos.y;
	}

}
