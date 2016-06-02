package com.engine.entites.util;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.EntityLiving;
import com.engine.entites.living.item.EntityWeapon;
import com.engine.level.Level;
import com.engine.render.DisplayManager;

public class AIMobMelee extends EntityAI {

	protected Level level;

	protected static final float RANGE = 74f;
	protected static final float SPEED = 200f;
	protected static final float WEAPON_SCALE_MUL = 1.5f;

	protected float aggroDistace = 10;
	protected EntityWanderingAI defaultAction;
	protected float cooldown = 0;

	protected float lasSwingDirection = 1;

	public AIMobMelee(EntityLiving owner, Random rand) {
		super(owner, rand);
		this.level = owner.getLevel();
		defaultAction = new EntityWanderingAI(owner, rand, 0.37f, 0.47f, 45f);
	}

	@Override
	public void update() {
		cooldown -= DisplayManager.getFrameTimeSeconds();
		Vector2f playerPos = level.getPlayer().getEntityCenter();
		System.out.println(playerPos);
		Vector2f difference = Vector2f.sub(level.getPlayer().getEntityCenter(), owner.getEntityCenter(), null);
		float dist = difference.length();
		if (dist <= aggroDistace) {
			updateRotation(difference);
			if (dist < owner.getEntityReach()) {
				if (cooldown < 0) {
					EntityWeapon weapon = new EntityWeapon(level, owner.getPosition(), owner.getRotation(), owner.getScale() * WEAPON_SCALE_MUL, owner);
					weapon.setRotationStart(owner.getRotation());
					weapon.setRotationRange(RANGE);
					weapon.setSpeed(SPEED);
					weapon.setRotationDirection(lasSwingDirection *= -1f);
					level.add(weapon);
					cooldown = weapon.getAttackCoolDown();
				}
				owner.setVelocity(new Vector2f(0, 0));
			} else {
				difference.normalise();
				difference.scale(owner.getMoveSpeed());
				owner.setVelocity(difference);
			}
		} else {
			defaultAction.update();
		}
	}

	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * @return the aggroDistace
	 */
	public float getAggroDistace() {
		return aggroDistace;
	}

	/**
	 * @param aggroDistace the aggroDistace to set
	 */
	public void setAggroDistace(float aggroDistace) {
		this.aggroDistace = aggroDistace;
	}

	/**
	 * @return the defaultAction
	 */
	public EntityAI getDefaultAction() {
		return defaultAction;
	}

	/**
	 * @param defaultAction the defaultAction to set
	 */
	public void setDefaultAction(EntityWanderingAI defaultAction) {
		this.defaultAction = defaultAction;
	}

	/**
	 * @param chanceToStop
	 * @see com.engine.entites.util.EntityWanderingAI#setChanceToStop(float)
	 */
	public void setChanceToStop(float chanceToStop) {
		defaultAction.setChanceToStop(chanceToStop);
	}

	/**
	 * @param chanceToTurn
	 * @see com.engine.entites.util.EntityWanderingAI#setChanceToTurn(float)
	 */
	public void setChanceToTurn(float chanceToTurn) {
		defaultAction.setChanceToTurn(chanceToTurn);
	}

	/**
	 * @param turnRange
	 * @see com.engine.entites.util.EntityWanderingAI#setTurnRange(float)
	 */
	public void setTurnRange(float turnRange) {
		defaultAction.setTurnRange(turnRange);
	}

}
