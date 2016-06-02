package com.engine.entites.util;

import java.util.Random;

import com.engine.entites.living.eco.EntityEco;

public class AI_Eco_SystemEntity extends AIEco {

	private AI_Idle idle;
	private AI_Eat eat;
	private AI_Reproduce reproduce;
	private AI_Social social;
	private AI_Flee flee;

	private float hungerPercent = 0.75f;
	private float reproductionPercent = 0.75f;
	private float socialPercent = 0.75f;
	private float chanceToUpdateFleeList = 0.125f;

	private static final boolean DEBUG = false;

	public AI_Eco_SystemEntity(EntityEco owner, Random rand) {
		super(owner, rand);
		idle = new AI_Idle(owner, rand, 0.125f, 0.1f, 180f, this);
		eat = new AI_Eat(owner, rand, this);
		reproduce = new AI_Reproduce(owner, rand, this);
		social = new AI_Social(owner, rand, this);
		flee = new AI_Flee(owner, rand, this);
	}

	@Override
	public void update() {
		if (rand.nextFloat() < chanceToUpdateFleeList) flee.updateTargets();
		getState().update();
	}

	private AIEco getState() {
		if (flee.shouldFlee()) {
			print("flee");
			return flee;
		}
		if (ownerEco.getHungerPercent() <= hungerPercent) {
			print("eating");
			return eat;
		}
		if (ownerEco.getPercentNeedsToSocial() >= socialPercent) {
			print("social");
			return social;
		}
		if (ownerEco.getPercentNeedsToReproduce() >= reproductionPercent && ownerEco.isFertile()) {
			print("reproduce");
			return reproduce;
		}
		//		print("idle");
		return idle;
	}

	private void print(String s) {
		if (DEBUG) System.out.println(s);
	}

	public AI_Idle getIdle() {
		return idle;
	}

	public void setIdle(AI_Idle idle) {
		this.idle = idle;
	}

	public AI_Eat getEat() {
		return eat;
	}

	public void setEat(AI_Eat eat) {
		this.eat = eat;
	}

	public AI_Reproduce getReproduce() {
		return reproduce;
	}

	public void setReproduce(AI_Reproduce reproduce) {
		this.reproduce = reproduce;
	}

	public AI_Social getSocial() {
		return social;
	}

	public void setSocial(AI_Social social) {
		this.social = social;
	}

	public AI_Flee getFlee() {
		return flee;
	}

	public void setFlee(AI_Flee flee) {
		this.flee = flee;
	}

	/**
	 * @return the hungerPercent
	 */
	public float getHungerPercent() {
		return hungerPercent;
	}

	/**
	 * @param hungerPercent the hungerPercent to set
	 */
	public void setHungerPercent(float hungerPercent) {
		this.hungerPercent = hungerPercent;
	}

	/**
	 * @return the reproductionPercent
	 */
	public float getReproductionPercent() {
		return reproductionPercent;
	}

	/**
	 * @param reproductionPercent the reproductionPercent to set
	 */
	public void setReproductionPercent(float reproductionPercent) {
		this.reproductionPercent = reproductionPercent;
	}

	/**
	 * @return the socialPercent
	 */
	public float getSocialPercent() {
		return socialPercent;
	}

	/**
	 * @param socialPercent the socialPercent to set
	 */
	public void setSocialPercent(float socialPercent) {
		this.socialPercent = socialPercent;
	}

}
