package com.engine.entites.util;

import com.engine.entites.Entity;
import com.engine.render.DisplayManager;

public class LogarithmicValue {

	private float amount;
	private Entity source;

	public LogarithmicValue(float amount, Entity source) {
		this.amount = amount;
		this.source = source;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @return the source
	 */
	public Entity getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Entity source) {
		this.source = source;
	}

	public float getCurrentValue(boolean decreaseTotal) {
		float damage = amount * DisplayManager.getFrameTimeSeconds();
		float abs = Math.abs(damage);
		if (decreaseTotal) {
			if (amount > 0) amount -= abs;
			if (amount < 0) amount += abs;
		}
		return damage;
	}

	public void decrease(float increment) {
		amount -= increment;
	}

	public void increase(float increment) {
		amount += increment;
	}

}
