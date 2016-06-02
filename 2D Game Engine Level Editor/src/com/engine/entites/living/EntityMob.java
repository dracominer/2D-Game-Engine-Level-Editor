package com.engine.entites.living;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.util.AIMobMelee;
import com.engine.level.Level;
import com.engine.render.textures.ModelTexture;

public class EntityMob extends EntityLiving {

	protected float aggroDistance = 10;

	public EntityMob(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
	}

	@Override
	protected void initializeEntity() {
		this.setMoveSpeed(1.5f);
		this.setRegen(1.2f, false);
		super.initializeEntity();
		this.ai = new AIMobMelee(this, level.getRandom());
		((AIMobMelee) ai).setChanceToTurn(0.12f);
		((AIMobMelee) ai).setChanceToStop(0.4f);
		((AIMobMelee) ai).setAggroDistace(aggroDistance);
	}

}
