package com.engine.entites.projectiles;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.engine.entites.Entity;
import com.engine.entites.EntityPhysical;
import com.engine.entites.living.EntityLiving;
import com.engine.entites.living.eco.EcoPlant;
import com.engine.entites.util.LogarithmicValue;
import com.engine.level.Level;
import com.engine.level.tiles.Tile;
import com.engine.particle.ParticleMaster;
import com.engine.particle.ParticleSystemSpinning;
import com.engine.particle.ParticleTexture;
import com.engine.render.DisplayManager;
import com.engine.render.textures.ModelTexture;
import com.engine.render.textures.TextureManager;

public class Projectile extends EntityPhysical {
	protected Entity owner;
	/**
	 * in meters per second
	 * */
	protected float speed = 0;
	protected float damage = 4f;

	protected float velX = 0;
	protected float velY = 0;
	protected float distanceTraveled = 0;
	protected boolean canMoveThroughSolidWalls = false;
	protected float inaccuracy = 0;

	protected ParticleSystemSpinning particles;

	public Projectile(Entity owner, Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
		this.owner = owner;
		particles = new ParticleSystemSpinning(new ParticleTexture(TextureManager.get("feather")), 250, 1, 0.01f, 0.5f, scale * 0.5f, level);
		particles.setDirection(new Vector3f(1, 0, 0), 1f);
		particles.setAverageTurnSpeed(180);
		particles.setSpinSpeedError(90);
	}

	public Projectile(Entity owner, ModelTexture texture) {
		this(owner, owner.getLevel(), new Vector2f(owner.getPosition()), owner.getRotation(), (owner.getScale() * 0.5f), texture);
	}

	public Projectile(Entity owner) {
		this(owner, TextureManager.getTexture("arrow"));
	}

	/**
	 * this will actually start the prjectile moving. Without this function, it wont start moving
	 * */
	public Projectile fire(float metersPerSecond) {
		Random r = new Random();
		float offAngle = r.nextFloat() * inaccuracy * (r.nextBoolean() ? 1 : -1);
		this.rotation = owner.getRotation() + offAngle;
		this.speed = metersPerSecond;
		velX = (float) -(speed * Math.sin(Math.toRadians(rotation)));
		velY = (float) (speed * Math.cos(Math.toRadians(rotation)));
		return this;
	}

	public void update() {
		float dx = moveX();
		float dy = moveY();
		distanceTraveled += Math.sqrt((dx * dx) + (dy * dy));
		shape.update(this);
		checkIsStillAlive();
		if (emitParticles()) particles.generateParticles(new Vector3f(position.x, position.y, ParticleMaster.ParticleStartHeight));
		correctAngle();
	}

	protected boolean emitParticles() {
		return true;
	}

	protected void correctAngle() {
		setRotation((float) Math.toDegrees(Math.atan2(velY, velX)) - 90f);
	}

	protected float moveX() {
		velX *= getAirDragConstant();
		float dx = velX * DisplayManager.getFrameTimeSeconds();
		this.increasePosition(dx, 0);
		return dx;
	}

	protected float moveY() {
		velX *= getAirDragConstant();
		float dy = velY * DisplayManager.getFrameTimeSeconds();
		this.increasePosition(dy, 0);
		return dy;
	}

	protected void checkIsStillAlive() {
		boolean flag = false;
		flag = onTouchTile(level.getTileManager().getTileAt(getEntityCenter()));
		if (isTooSmall(velX) && isTooSmall(velY)) {
			flag = true;
		}
		EntityPhysical e = level.getCollision(this);
		if (e == owner) return;
		if (e instanceof EcoPlant) return;
		if (e != null) {
			if (e instanceof EntityLiving) {
				EntityLiving living = (EntityLiving) e;
				flag = this.onInteractWithEntity(living);
			}
		}
		if (flag) level.remove(this);
	}

	protected boolean onInteractWithEntity(EntityLiving e) {
		if (owner instanceof EntityLiving) {
			e.damage(new LogarithmicValue(((EntityLiving) owner).getAttackStrength(EntityLiving.RANGED), owner));
		} else {
			e.damage(new LogarithmicValue(damage, owner));
		}
		return true;
	}

	protected boolean onTouchTile(Tile t) {
		if (t == null) return true;
		return t.isSolid();
	}

	protected boolean isTooSmall(float value) {
		return Math.abs(value) < getMinSpeedValue();
	}

	protected float getAirDragConstant() {
		return 0.99999987f;
	}

	protected float getMinSpeedValue() {
		return 0.5f;
	}

	/**
	 * @return the owner
	 */
	public Entity getOwner() {
		return owner;
	}

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @return the velX
	 */
	public float getVelX() {
		return velX;
	}

	/**
	 * @return the velY
	 */
	public float getVelY() {
		return velY;
	}

	/**
	 * @return the distanceTraveled
	 */
	public float getDistanceTraveled() {
		return distanceTraveled;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	public Projectile setInnacuracy(float value) {
		this.inaccuracy = value;
		return this;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @param velX the velX to set
	 */
	public void setVelX(float velX) {
		this.velX = velX;
	}

	/**
	 * @param velY the velY to set
	 */
	public void setVelY(float velY) {
		this.velY = velY;
	}

	public float getAttackCooldown() {
		return 0.15f;
	}

}
