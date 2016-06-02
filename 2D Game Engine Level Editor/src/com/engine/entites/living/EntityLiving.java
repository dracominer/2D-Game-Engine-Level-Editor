/**
 * 
 */
package com.engine.entites.living;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.engine.entites.Entity;
import com.engine.entites.EntityPhysical;
import com.engine.entites.misc.EntityHealthbar;
import com.engine.entites.util.EntityAI;
import com.engine.entites.util.EntityWanderingAI;
import com.engine.entites.util.LogarithmicValue;
import com.engine.level.Level;
import com.engine.level.tiles.Tile;
import com.engine.particle.ParticleMaster;
import com.engine.particle.ParticleSystemSpinning;
import com.engine.particle.ParticleTexture;
import com.engine.render.DisplayManager;
import com.engine.render.textures.ModelTexture;
import com.engine.render.textures.TextureManager;
import com.engine.toolbox.Maths;

/**
 * @author Duncan
 *
 */
public class EntityLiving extends EntityPhysical {

	protected static ParticleSystemSpinning death = null;
	protected static ParticleSystemSpinning hurt = null;
	protected static ParticleSystemSpinning heal = null;
	protected static ParticleSystemSpinning levelUp = null;

	protected static final float HEALTH_BAR_RATIO = 1.5f;

	public static final String MELEE = "melee";
	public static final String RANGED = "ranged";
	public static final String MAGIC = "magic";

	protected LogarithmicValue netDamage = null;
	protected LogarithmicValue netRegen = null;

	protected EntityHealthbar healthBar;

	/**
	 * this is a boolean value to check and see if the entity is currently alive
	 * */
	protected boolean isAlive = true;

	/**
	 * this is the highest health the entity can have. This is not final because it can be possible to improve this value or decrease it
	 * */
	protected float maxHealth = 10;
	/**
	 * this is the actual health of the entity. If it drops below zero, the entity dies and is removed from the game
	 * */
	protected float health = 10;
	/**
	 * this is the actual health of the entity. If it drops below zero, the entity dies and is removed from the game
	 * */
	protected float lastHealth = 0;
	/**
	 * this is the motion in meters per second;
	 * */
	protected float moveSpeed = 0.25f;

	/**
	 * this is the amount of health that the entity gets back each second. It is in health points per second. ie if this is = 5 then after each second 5 health will be healed.
	 * */
	protected float regen = 0.5f;

	/**
	 * the artificial intelligence of the entity. This will do most of the required logic for patterns that will be used across many entities
	 * 
	 * */
	protected EntityAI ai;
	/**
	 * the experience level of the entity.
	 * */
	protected float xp = 0;
	protected int xpLevel = 1;

	protected int lastXPLevel = 0;

	protected boolean isInWall = false;

	/**
	 * @param l
	 * @param position
	 * @param rotation
	 * @param scale
	 * @param modelTexture
	 */
	public EntityLiving(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
	}

	@Override
	protected void initializeEntity() {
		ai = new EntityWanderingAI(this, level.getRandom(), 0.37f, 0.47f, 45f);
		//death
		death = new ParticleSystemSpinning(new ParticleTexture(TextureManager.get("saoDeath")), 1, 1f, -0.000001f, 5, 0.125f, level);
		death.setDirection(new Vector3f(1, 0, 0), 1);
		death.setLifeError(10);
		death.setScaleError(0.75f);
		death.setSpeedError(0.5f);
		death.setAverageTurnSpeed(getParticleSpinSpeed());
		death.setSpinSpeedError(getParticleSpinSpeedError());
		//hurt
		hurt = new ParticleSystemSpinning(new ParticleTexture(TextureManager.get("saoHurt")), 1, 1f, 0, 1, 0.125f, level);
		hurt.setDirection(new Vector3f(1, 0, 0), 1);
		hurt.setLifeError(2);
		hurt.setScaleError(0.75f);
		hurt.setSpeedError(0.5f);
		hurt.setAverageTurnSpeed(getParticleSpinSpeed());
		hurt.setSpinSpeedError(getParticleSpinSpeedError());
		//heal
		heal = new ParticleSystemSpinning(new ParticleTexture(TextureManager.get("saoHeal")), 1, 1f, 0, 1, 0.125f, level);
		heal.setDirection(new Vector3f(1, 0, 0), 1);
		heal.setLifeError(2);
		heal.setScaleError(0.75f);
		heal.setSpeedError(0.5f);
		heal.setAverageTurnSpeed(getParticleSpinSpeed());
		heal.setSpinSpeedError(getParticleSpinSpeedError());
		//Level Up
		levelUp = new ParticleSystemSpinning(new ParticleTexture(TextureManager.get("xp")), 1, 1f, 0, 1, 0.125f, level);
		levelUp.setDirection(new Vector3f(1, 0, 0), 1);
		levelUp.setLifeError(2);
		levelUp.setScaleError(0.75f);
		levelUp.setSpeedError(0.5f);
		levelUp.setAverageTurnSpeed(getParticleSpinSpeed());
		levelUp.setSpinSpeedError(getParticleSpinSpeedError());
		//Other
		if (hasHealthBar()) {
			healthBar = new EntityHealthbar(level, position, 0, scale * HEALTH_BAR_RATIO, TextureManager.getTexture("healthBar"), this);
			level.add(healthBar);
		}
		this.setRegen(this.regen);
	}

	public void update() {
		super.update();
		if (!isAlive) whenFoundDead();
		updateAI();
		updateHealth();
		resetParticleScale();
		updateXP();
	}

	protected void whenFoundDead() {
		level.remove(this);
	}

	protected void resetParticleScale() {}

	protected void updateAI() {
		ai.update();
	}

	private void updateXP() {
		changeXP(baseXPPerSecond() * DisplayManager.getFrameTimeSeconds());
		int xpLevel = getXPLevel();
		if (xpLevel > lastXPLevel) performLevelUpAnimation();
		lastXPLevel = getXPLevel();
	}

	protected void updateHealth() {
		if (isInWall) {
			this.damage(new LogarithmicValue(getSuffocationDamage(), null));
		}
		float amount = 0;
		Entity source = this;
		if (netRegen != null) {
			amount += netRegen.getCurrentValue(false);
			this.changeHealth(amount, netRegen.getSource());
			if (netRegen.getAmount() < regen) {
				netRegen.setAmount(regen);
			} else {
				netRegen.decrease(amount);
			}
			if (netRegen.getAmount() < 0) netRegen = null;
		}
		if (netDamage != null) {
			amount -= netDamage.getCurrentValue(false);
			netDamage.decrease(amount);
			source = netDamage.getSource();
			if (netDamage.getAmount() < 0) netDamage = null;
		}
		this.changeHealth(amount, source);
	}

	protected void updatePhysics() {
		if (!isAlive) level.remove(this);
		super.doPhysics();
		Tile t = level.getTileManager().getTileAt(getEntityCenter());
		if (t == null) {
			this.setInWall(true);
		} else if (t.isSolid()) {
			this.setInWall(true);
		}
	}

	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * @param isAlive the isAlive to set
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * @return the maxHealth
	 */
	public float getMaxHealth() {
		return maxHealth;
	}

	/**
	 * @param maxHealth the maxHealth to set
	 */
	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	/**
	 * @return the health
	 */
	public float getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(float health) {
		this.health = health;
	}

	/**
	 * @return the moveSpeed
	 */
	public float getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * @param moveSpeed the moveSpeed to set
	 */
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	/**
	 * @return the regen
	 */
	public float getRegen() {
		return regen;
	}

	/**
	 * @param regen the regen to set
	 */
	public void setRegen(float regen) {
		this.setRegen(regen, true);
	}

	/**
	 * @param regen the regen to set
	 */
	public void setRegen(float regen, boolean startRegen) {
		this.regen = regen;
		if (startRegen) this.heal(new LogarithmicValue(this.regen, this));
	}

	private boolean changeHealth(float amount, Entity source) {
		lastHealth = this.health;
		this.health += amount;
		float delta = Maths.roundToSmallNumber(health - lastHealth);
		if (health < 0) {
			isAlive = false;
			if (source instanceof EntityLiving) {
				((EntityLiving) source).onKillEntity(this);
			}
			performDeathAnimation();
			return true;
		}
		if (health > maxHealth) {
			health = maxHealth;
			delta = 0;
		}
		if (delta > getDefOfSignificantHealthChange()) smallHealAnimation();
		if (delta < -getDefOfSignificantHealthChange()) smallHurtAnimation();
		return false;
	}

	protected void smallHealAnimation() {
		if (showsParticles()) if (level.getRandom().nextFloat() < getChanceToShowParticles()) if (heal != null) heal.createExplosion(getParticleStart(), getDefaultParticleAmount());
	}

	protected void smallHurtAnimation() {
		if (showsParticles()) if (level.getRandom().nextFloat() < getChanceToShowParticles()) if (hurt != null) hurt.createExplosion(getParticleStart(), getDefaultParticleAmount());
	}

	protected void performHealAnimation(float healAmount) {
		if (showsParticles()) if (heal != null) heal.createExplosion(getParticleStart(), getParticlesForHeal(healAmount));
	}

	protected void performHurtAnimation(float damage) {
		if (showsParticles()) if (hurt != null) hurt.createExplosion(getParticleStart(), getParticlesForHurt(damage));
	}

	protected void performDeathAnimation() {
		if (showsParticles()) if (death != null) death.createExplosion(getParticleStart(), getParticlesForDeath());
	}

	//TODO
	protected void performLevelUpAnimation() {
		if (showsParticles()) if (levelUp != null) levelUp.createExplosion(getParticleStart(), getParticlesForLevelUp());
	}

	public float getHealthPercent() {
		return health / maxHealth;
	}

	public Vector3f getParticleStart() {
		return new Vector3f(position.x, position.y, ParticleMaster.ParticleStartHeight);
	}

	public float getMeleeDamage(EntityLiving victim, float distance) {
		return (1f / distance) * 2;
	}

	public boolean heal(LogarithmicValue health) {
		if (health == null) return false;
		if (netRegen == null) {
			netRegen = health;
		} else {
			this.netRegen.increase(health.getAmount());
		}
		if (health.getSource() != this) {
			if (health.getAmount() < 0) performHurtAnimation(health.getAmount());
			if (health.getAmount() > 0) performHealAnimation(health.getAmount());
		}
		lastHealth = this.health;
		return true;
	}

	public void damage(LogarithmicValue damage) {
		if (damage == null) return;
		if (netDamage == null) {
			netDamage = damage;
		} else {
			this.netDamage.increase(damage.getAmount());
			this.netDamage.setSource(damage.getSource());
		}
		if (damage.getSource() != this) {
			if (damage.getAmount() > 0) performHurtAnimation(damage.getAmount());
			if (damage.getAmount() < 0) performHealAnimation(damage.getAmount());
		}
	}

	/**
	 * @return the isInWall
	 */
	public boolean isInWall() {
		return isInWall;
	}

	/**
	 * @param isInWall the isInWall to set
	 */
	public void setInWall(boolean isInWall) {
		this.isInWall = isInWall;
	}

	@Override
	public void setLevel(Level l) {
		this.level = l;
		if (healthBar == null) {
			healthBar = new EntityHealthbar(l, position, 0, scale * 1.5f, TextureManager.getTexture("healthBar"), this);
		}
		healthBar.setLevel(l);
		//		this.init();
	}

	public float getEntityReach() {
		return scale * 2f;
	}

	public float getAttackStrength(String type) {
		if (type == MELEE) return 4.5f;
		if (type == RANGED) return 2.5f;
		if (type == MAGIC) return 7.6f;
		return 1;
	}

	public Entity getLastAttacker() {
		if (netDamage == null) return null;
		return netDamage.getSource();
	}

	public void onKillEntity(EntityLiving victim) {
		if (victim == null) return;
		changeXP(victim.getXPamount(this));
	}

	public float getXPamount(EntityLiving attacker) {
		return 2f + (5f * level.getRandom().nextFloat());
	}

	public void changeXP(float amount) {
		xp += amount;
		if (xp > getXPforLevelUp()) {
			xp %= getXPforLevelUp();
			xpLevel++;
		}
	}

	/**
	 * @return the netDamage
	 */
	public LogarithmicValue getNetDamage() {
		return netDamage;
	}

	/**
	 * @param netDamage the netDamage to set
	 */
	public void setNetDamage(LogarithmicValue netDamage) {
		this.netDamage = netDamage;
	}

	/**
	 * @return the netRegen
	 */
	public LogarithmicValue getNetRegen() {
		return netRegen;
	}

	/**
	 * @param netRegen the netRegen to set
	 */
	public void setNetRegen(LogarithmicValue netRegen) {
		this.netRegen = netRegen;
	}

	/**
	 * @return the healthBar
	 */
	public EntityHealthbar getHealthBar() {
		return healthBar;
	}

	/**
	 * @param healthBar the healthBar to set
	 */
	public void setHealthBar(EntityHealthbar healthBar) {
		this.healthBar = healthBar;
	}

	/**
	 * @return the ai
	 */
	public EntityAI getAi() {
		return ai;
	}

	/**
	 * @param ai the ai to set
	 */
	public void setAi(EntityAI ai) {
		this.ai = ai;
	}

	/**
	 * @return the xp
	 */
	public float getXp() {
		return xp;
	}

	/**
	 * @param xp the xp to set
	 */
	public void setXp(float xp) {
		this.xp = xp;
	}

	public float getXPPerLevel() {
		return 0.5f;
	}

	public int getXPLevel() {
		return xpLevel;
	}

	public float getXPforLevelUp() {
		return ((float) ((getXPLevel() << 5))) * 0.9875f;
	}

	protected float baseXPPerSecond() {
		return 0.2f;
	}

	protected boolean hasHealthBar() {
		return true;
	}

	protected int getDefaultParticleAmount() {
		return 5;
	}

	protected float getChanceToShowParticles() {
		return 0.025f;
	}

	protected float getParticleSpinSpeed() {
		return 180;
	}

	protected float getParticleSpinSpeedError() {
		return 360;
	}

	protected float getDefOfSignificantHealthChange() {
		return 0.3f;
	}

	protected float getSuffocationDamage() {
		return 20f;
	}

	public boolean showsParticles() {
		return true;
	}

	protected int getParticlesForHeal(float healAmount) {
		return (int) (25 * healAmount);
	}

	protected int getParticlesForHurt(float harmAmount) {
		return (int) (25 * harmAmount);
	}

	protected int getParticlesForLevelUp() {
		return (int) (25 * scale);
	}

	protected int getParticlesForDeath() {
		return (int) (250 * scale);
	}

}
