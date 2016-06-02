package com.engine.entites.living.item;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.Entity;
import com.engine.entites.EntityPhysical;
import com.engine.entites.living.EntityLiving;
import com.engine.entites.living.eco.EcoPlant;
import com.engine.entites.util.LogarithmicValue;
import com.engine.level.Level;
import com.engine.render.DisplayManager;
import com.engine.render.textures.ModelTexture;
import com.engine.render.textures.TextureManager;

public class EntityWeapon extends EntityPhysical {

	private static float ANGLE_ERROR = 5f;

	protected EntityLiving owner;

	protected float rotationRange = 10;
	protected float rotationStart = 0;
	protected float rotationDirection = 1;
	protected float time = 0;
	protected float speed = 0.1f;

	protected float distance;

	protected float damage = 2;

	protected List<EntityPhysical> attackedList = new ArrayList<EntityPhysical>();

	public EntityWeapon(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture, EntityLiving owner) {
		super(l, new Vector2f(position.x, position.y), rotation, scale, modelTexture);
		this.owner = owner;
		distance = owner.getScale();
	}

	public EntityWeapon(Level l, Vector2f position, float rotation, float scale, EntityLiving owner) {
		this(l, position, rotation, scale, TextureManager.getTexture("sword"), owner);
	}

	public void update() {
		updateRotation();
		doCollision();
		shape.update(this);
	}

	protected void updateRotation() {
		time += DisplayManager.getFrameTimeSeconds();
		float rotationStart = this.rotationStart + (this.rotationRange / 2f * -rotationDirection);
		this.rotation = rotationStart + (rotationDirection * time * speed);
		if (Math.abs(rotationStart - rotation) > rotationRange) {
			level.remove(this);
		}
		Vector2f pos = owner.getPosition();
		float dx = distance * (float) -Math.sin(Math.toRadians(rotation));
		float dy = distance * (float) Math.cos(Math.toRadians(rotation));
		this.position.x = dx + pos.x;
		this.position.y = dy + pos.y;
	}

	protected void doCollision() {
		List<Entity> near = level.getCloseEntities(getEntityCenter(), owner.getEntityReach());
		for (Entity e : near) {
			if (e == owner) continue;
			if (!harmsPlants() && e instanceof EcoPlant) continue;
			if (e instanceof EntityLiving) {
				if (attackedList.contains(e)) continue;
				float angle = getDegrees(e);
				if (getAbsDiff(angle, rotation) <= ANGLE_ERROR) {
					EntityLiving living = (EntityLiving) e;
					living.damage(new LogarithmicValue(owner.getAttackStrength(EntityLiving.MELEE), owner));
					attackedList.add(living);
				}
			}
		}
		if (level.isInSolidTile(this)) {
			level.remove(this);
		}
	}

	private float getAbsDiff(float a, float b) {
		return abs(abs(a) - abs(b));
	}

	private float abs(float num) {
		return (float) Math.abs(num);
	}

	private float getDegrees(Entity other) {
		return (float) (Math.toDegrees(getRadians(other)) - 90d);
	}

	private float getRadians(Entity other) {
		Vector2f diff = Vector2f.sub(other.getEntityCenter(), owner.getEntityCenter(), null);
		return (float) Math.atan2(diff.y, diff.x);
	}

	/**
	 * @return the owner
	 */
	public EntityLiving getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EntityLiving owner) {
		this.owner = owner;
	}

	/**
	 * @return the rotationRange
	 */
	public float getRotationRange() {
		return rotationRange;
	}

	/**
	 * @param rotationRange the rotationRange to set
	 */
	public void setRotationRange(float rotationRange) {
		this.rotationRange = rotationRange;
	}

	/**
	 * @return the rotationStart
	 */
	public float getRotationStart() {
		return rotationStart;
	}

	/**
	 * @param rotationStart the rotationStart to set
	 */
	public void setRotationStart(float rotationStart) {
		this.rotationStart = rotationStart;
	}

	/**
	 * @return the rotationDirection
	 */
	public float getRotationDirection() {
		return rotationDirection;
	}

	/**
	 * @param rotationDirection the rotationDirection to set
	 */
	public void setRotationDirection(float rotationDirection) {
		this.rotationDirection = rotationDirection;
	}

	/**
	 * @return the time
	 */
	public float getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(float time) {
		this.time = time;
	}

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return the damage
	 */
	public float getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getAttackCoolDown() {
		return (speed / rotationRange) * (0.15f);
	}

	protected boolean harmsPlants() {
		return false;
	}

}
