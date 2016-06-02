package com.engine.entites.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.engine.entites.living.eco.EcoPlant;
import com.engine.entites.living.eco.EntityEco;

public class PlantAI extends AIEco {

	private float searchRange = 2.5f;
	private float minPercent = 0.45f;
	private float maxNeighbors = 20;

	public PlantAI(EntityEco owner, Random rand) {
		super(owner, rand);
	}

	@Override
	public void update() {
		updateShouldDie();
		if (ownerEco.getPercentNeedsToReproduce() > minPercent) {
			reproduce();
		}
	}

	private void updateShouldDie() {
		List<EntityEco> near = level.getEcoEntities(ownerEco, searchRange);
		List<EntityEco> nearbyPlants = new ArrayList<EntityEco>();
		for (EntityEco e : near) {
			if (ownerEco instanceof EcoPlant) nearbyPlants.add(e);
		}
		if (nearbyPlants.size() > maxNeighbors) {
			ownerEco.die();
		}
	}

	private void reproduce() {
		EntityEco baby = ownerEco.getBaby();
		level.add(baby);
		ownerEco.setTimeSinceReproduction(0);
	}

	/**
	 * @return the searchRange
	 */
	public float getSearchRange() {
		return searchRange;
	}

	/**
	 * @param searchRange the searchRange to set
	 */
	public void setSearchRange(float searchRange) {
		this.searchRange = searchRange;
	}

	/**
	 * @return the minPercent
	 */
	public float getMinPercent() {
		return minPercent;
	}

	/**
	 * @param minPercent the minPercent to set
	 */
	public void setMinPercent(float minPercent) {
		this.minPercent = minPercent;
	}

	/**
	 * @return the maxNeighbors
	 */
	public float getMaxNeighbors() {
		return maxNeighbors;
	}

	/**
	 * @param maxNeighbors the maxNeighbors to set
	 */
	public void setMaxNeighbors(float maxNeighbors) {
		this.maxNeighbors = maxNeighbors;
	}

}
