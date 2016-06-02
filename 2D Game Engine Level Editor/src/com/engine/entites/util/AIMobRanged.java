package com.engine.entites.util;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.EntityLiving;
import com.engine.entites.projectiles.EntityArrow;
import com.engine.level.Level;
import com.engine.render.DisplayManager;

public class AIMobRanged extends EntityAI {

	protected Level level;

	protected static final float RANGE = 75f;
	protected static final float POWER = 20f;
	protected static final float ARROW_SCALE_MUL = 0.5f;
	protected static final float INNACURACY = 40f;

	protected float aggroDistace = 50;
	protected float attackRange = 10f;
	protected EntityWanderingAI defaultAction;
	protected float cooldown = 0;

	public AIMobRanged(EntityLiving owner, Random rand) {
		super(owner, rand);
		this.level = owner.getLevel();
		defaultAction = new EntityWanderingAI(owner, rand, 0.37f, 0.47f, 45f);
	}

	@Override
	public void update() {
		cooldown -= DisplayManager.getFrameTimeSeconds();
		Vector2f difference = Vector2f.sub(level.getPlayer().getEntityCenter(), owner.getEntityCenter(), null);
		float dist = difference.length();
		if (dist <= aggroDistace) {
			updateRotation(difference);
			if (dist < attackRange) {
				if (cooldown < 0) {
					EntityArrow arrow = new EntityArrow(owner, RANGE);
					arrow.setScale(owner.getScale() * ARROW_SCALE_MUL);
					arrow.setInnacuracy(INNACURACY);
					arrow.fire(POWER);
					level.add(arrow);
					cooldown = arrow.getAttackCooldown();
				}
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
	 * @return the attackRange
	 */
	public float getAttackRange() {
		return attackRange;
	}

	/**
	 * @param attackRange the attackRange to set
	 */
	public void setAttackRange(float attackRange) {
		this.attackRange = attackRange;
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
