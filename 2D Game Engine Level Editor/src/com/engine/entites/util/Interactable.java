package com.engine.entites.util;

import com.engine.entites.Entity;

public interface Interactable {

	public static enum InteractType {
		LEFT_CLICK, RIGHT_CLICK, MIDDLE_CLICK, TOUCH, HIT_WITH_PROJECTILE
	}

	public boolean onInteract(Entity source, float distance, InteractType interaction);

	public boolean canInteract(Entity source, float distance, InteractType interaction);

}
