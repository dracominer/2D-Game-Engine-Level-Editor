package com.engine.entites.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.eco.EntityEco;
import com.engine.render.DisplayManager;

public class FlockingAI extends AIEco {

	private String type;
	private List<EntityEco> nearAgents = new ArrayList<EntityEco>();
	private float timeBetweenUpdates = 0.25f;
	private float refreshCooldown = 0;
	private float searchRange = 2f;

	private Vector2f alignment = new Vector2f();
	private Vector2f cohesion = new Vector2f();
	private Vector2f seperation = new Vector2f();

	private float alignmentWeight = 1.0f;
	private float cohesionWeight = 1.0f;
	private float seperationWeight = 1.0f;
	private float currentMovementWeight = 1.0f;

	private float nearbyAgents = 0;

	public FlockingAI(EntityEco owner, Random rand) {
		super(owner, rand);
		type = owner.getEcoType();
	}

	@Override
	public void update() {
		refreshCooldown -= DisplayManager.getFrameTimeSeconds();
		if (refreshCooldown <= 0) {
			refreshAgentList();
		}
		calcAlignment();
		calcCohesion();
		calcSeperation();
		calcMovement();

	}

	private void refreshAgentList() {
		refreshCooldown = timeBetweenUpdates;
		List<EntityEco> near = level.getEcoEntities(ownerEco, searchRange);
		nearAgents.clear();
		for (EntityEco e : near) {
			if (e == ownerEco) continue;
			if (e.getEcoType().equals(type)) {
				nearAgents.add(e);
			}
		}
		nearbyAgents = nearAgents.size();
	}

	private void calcAlignment() {
		if (nearAgents.isEmpty()) return;
		alignment = new Vector2f(0, 0);
		for (EntityEco e : nearAgents) {
			Vector2f.add(alignment, e.getVelocity(), alignment);
		}
		alignment.x *= 1f / nearbyAgents;
		alignment.y *= 1f / nearbyAgents;
		if (alignment.length() < 0) alignment.normalise();
	}

	private void calcCohesion() {
		if (nearAgents.isEmpty()) return;
		cohesion = new Vector2f(0, 0);
		for (EntityEco e : nearAgents) {
			Vector2f.add(cohesion, e.getPosition(), cohesion);
		}
		cohesion.x *= 1f / nearbyAgents;
		cohesion.y *= 1f / nearbyAgents;
		Vector2f.sub(cohesion, ownerEco.getEntityCenter(), cohesion);
		if (cohesion.length() < 0) cohesion.normalise();
	}

	private void calcSeperation() {
		if (nearAgents.isEmpty()) return;
		seperation = new Vector2f(0, 0);
		for (EntityEco e : nearAgents) {
			Vector2f.add(seperation, Vector2f.sub(e.getPosition(), ownerEco.getEntityCenter(), null), seperation);
		}
		seperation.x *= -1f / nearbyAgents;
		seperation.y *= -1f / nearbyAgents;
		if (seperation.length() < 0) seperation.normalise();
	}

	private Vector2f getAlignment() {
		return new Vector2f(alignment.x * alignmentWeight, alignment.y * alignmentWeight);
	}

	private Vector2f getCohesion() {
		return new Vector2f(cohesion.x * cohesionWeight, cohesion.y * cohesionWeight);
	}

	private Vector2f getSeperation() {
		return new Vector2f(seperation.x * seperationWeight, seperation.y * seperationWeight);
	}

	private Vector2f getCurrentMovement() {
		Vector2f current = ownerEco.getVelocity();
		return new Vector2f(current.x * currentMovementWeight, current.y * currentMovementWeight);
	}

	private void calcMovement() {
		Vector2f move = new Vector2f(0, 0);
		Vector2f.add(move, getAlignment(), move);
		Vector2f.add(move, getCohesion(), move);
		Vector2f.add(move, getSeperation(), move);
		Vector2f.add(move, getCurrentMovement(), move);
		move.x /= 2f;
		move.y /= 2f;
		move.x *= ownerEco.getMoveSpeed();
		move.y *= ownerEco.getMoveSpeed();
		ownerEco.setVelocity(move);
		super.updateRotation(move);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the nearAgents
	 */
	public List<EntityEco> getNearAgents() {
		return nearAgents;
	}

	/**
	 * @param nearAgents the nearAgents to set
	 */
	public void setNearAgents(List<EntityEco> nearAgents) {
		this.nearAgents = nearAgents;
	}

	/**
	 * @return the timeBetweenUpdates
	 */
	public float getTimeBetweenUpdates() {
		return timeBetweenUpdates;
	}

	/**
	 * @param timeBetweenUpdates the timeBetweenUpdates to set
	 */
	public void setTimeBetweenUpdates(float timeBetweenUpdates) {
		this.timeBetweenUpdates = timeBetweenUpdates;
	}

	/**
	 * @return the refreshCooldown
	 */
	public float getRefreshCooldown() {
		return refreshCooldown;
	}

	/**
	 * @param refreshCooldown the refreshCooldown to set
	 */
	public void setRefreshCooldown(float refreshCooldown) {
		this.refreshCooldown = refreshCooldown;
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
	 * @return the alignmentWeight
	 */
	public float getAlignmentWeight() {
		return alignmentWeight;
	}

	/**
	 * @param alignmentWeight the alignmentWeight to set
	 */
	public void setAlignmentWeight(float alignmentWeight) {
		this.alignmentWeight = alignmentWeight;
	}

	/**
	 * @return the cohesionWeight
	 */
	public float getCohesionWeight() {
		return cohesionWeight;
	}

	/**
	 * @param cohesionWeight the cohesionWeight to set
	 */
	public void setCohesionWeight(float cohesionWeight) {
		this.cohesionWeight = cohesionWeight;
	}

	/**
	 * @return the seperationWeight
	 */
	public float getSeperationWeight() {
		return seperationWeight;
	}

	/**
	 * @param seperationWeight the seperationWeight to set
	 */
	public void setSeperationWeight(float seperationWeight) {
		this.seperationWeight = seperationWeight;
	}

	/**
	 * @param alignment the alignment to set
	 */
	public void setAlignment(Vector2f alignment) {
		this.alignment = alignment;
	}

	/**
	 * @param cohesion the cohesion to set
	 */
	public void setCohesion(Vector2f cohesion) {
		this.cohesion = cohesion;
	}

	/**
	 * @param seperation the seperation to set
	 */
	public void setSeperation(Vector2f seperation) {
		this.seperation = seperation;
	}

	/**
	 * @return the currentMovementWeight
	 */
	public float getCurrentMovementWeight() {
		return currentMovementWeight;
	}

	/**
	 * @param currentMovementWeight the currentMovementWeight to set
	 */
	public void setCurrentMovementWeight(float currentMovementWeight) {
		this.currentMovementWeight = currentMovementWeight;
	}

	/**
	 * @return the nearbyAgents
	 */
	public float getNearbyAgents() {
		return nearbyAgents;
	}

	/**
	 * @param nearbyAgents the nearbyAgents to set
	 */
	public void setNearbyAgents(float nearbyAgents) {
		this.nearbyAgents = nearbyAgents;
	}

}
