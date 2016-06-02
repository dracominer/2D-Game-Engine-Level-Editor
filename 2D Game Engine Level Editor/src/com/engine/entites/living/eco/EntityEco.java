package com.engine.entites.living.eco;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.EntityLiving;
import com.engine.entites.util.AIEco;
import com.engine.entites.util.AI_Eco_SystemEntity;
import com.engine.entites.util.LogarithmicValue;
import com.engine.level.Level;
import com.engine.render.DisplayManager;
import com.engine.render.textures.ModelTexture;

public abstract class EntityEco extends EntityLiving {

	private static float STARVATION_DAMAGE = 0.5f;
	private static float STARVATION_HUNGER_REPLENISHED = 0.1f;
	private List<String> edibleTypes = new ArrayList<String>();

	protected float attackStrength = 3f;

	protected LogarithmicValue netHunger;

	/**
	 * this number set as the gender of the entity makes it male. This means it requires a female of decent age to reproduce
	 * */
	public static final int GENDER_MALE = 0;
	/**
	 * this number set as the gender of the entity makes it female. This means it requires a male of decent age to reproduce
	 * */
	public static final int GENDER_FEMALE = 1;
	/**
	 * this number set as the gender of the entity makes it asexual. This means that once the entity reaches a fertile age, it can asexuallly reproduce.
	 */
	public static final int GENDER_ASEXUAL = 1;

	protected float maxHunger = 40;
	protected float hunger = 35;
	protected float mass = 10;
	protected float age = 0;
	protected float averageDeathAge = 10;
	protected float averageDeathAgeRange = 2;
	protected float maxSize = 10f;
	protected int gender = GENDER_ASEXUAL;
	protected float chanceOfDeathAtBirth = 0.3f;
	protected float chanceOfDeathDuringLife = 0.00001f;
	protected float chanceOfDeathAfterGivingBirth = 0.23f;
	protected float fertileAgeMin = 5;
	protected float fertileAgeMax = 100;
	protected float ageOffset;

	protected float minSize = 0.1f;

	protected float timeSinceSocialContact = 0;
	protected float timeSinceReproduction = 0;

	protected float reproductionRate = 0.3f;
	protected float socializationRate = 0.05f;

	protected boolean hasAMate = false;
	protected boolean hasAFriend = false;

	protected AIEco ecoAI;

	public EntityEco(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		super(l, position, rotation, scale, modelTexture);
		netHunger = new LogarithmicValue(0, this);
	}

	protected void initializeEntity() {
		super.initializeEntity();
		ageOffset = level.getRandom().nextFloat() * averageDeathAgeRange * (level.getRandom().nextBoolean() ? -1 : 1);
		ai = this.ecoAI = getAI();
		this.setMoveSpeed(0.4f);
		if (level.getRandom().nextBoolean()) {
			this.setGender(GENDER_FEMALE);
		} else {
			this.setGender(GENDER_MALE);
		}
	}

	protected AIEco getAI() {
		return new AI_Eco_SystemEntity(this, new Random());
	}

	protected void resetParticleScale() {
		death.setParticleScale(scale * 0.1f);
		hurt.setParticleScale(scale * 0.1f);
		heal.setParticleScale(scale * 0.1f);
	}

	protected void updateAI() {
		ecoAI.update();
	}

	public void update() {
		checkIsInWall();
		super.update();
		updateHunger();
		age();
		this.replenishHunger(getHungerDecreaseRate() * DisplayManager.getFrameTimeSeconds());
		this.setMaxHealth(getMaxHealthFromAge());
	}

	protected float getMaxHealthFromAge() {
		return getChallengeRating() * 2f;
	}

	private void checkIsInWall() {
		if (level.getTileManager().isSolid(getEntityCenter())) die();
	}

	public abstract EntityEco getBaby();

	public void birth() {
		if (level.getRandom().nextFloat() < chanceOfDeathAtBirth) {
			this.die();
		}
	}

	protected void updateHunger() {
		hunger += netHunger.getCurrentValue(true);
		if (hunger > maxHunger) hunger = maxHunger;
		if (this.hunger <= 0) {
			this.damage(new LogarithmicValue(STARVATION_DAMAGE, this));
			replenishHunger(new LogarithmicValue(STARVATION_HUNGER_REPLENISHED, this));
		}
	}

	protected void age() {
		float da = DisplayManager.getFrameTimeSeconds();
		age += da;
		timeSinceReproduction += da;
		timeSinceSocialContact += da;
		if (level.getRandom().nextBoolean()) {
			this.replenishHunger(-da);
		}
		if (level.getRandom().nextFloat() < chanceOfDeathDuringLife) {
			this.die();
		}
		if (age > averageDeathAge + ageOffset) {
			this.die();
			//			System.out.println("dying of old age...age of " + age);
		}
		float perAge = age / (averageDeathAge + ageOffset);
		float scale = maxSize * perAge;
		this.setScale(Float.max(scale, minSize));
		if (healthBar != null) this.healthBar.setScale(scale * EntityLiving.HEALTH_BAR_RATIO);
	}

	public void die() {
		this.damage(new LogarithmicValue(this.health, this));
	}

	public float getMaxHunger() {
		return maxHunger;
	}

	public void setMaxHunger(float maxHunger) {
		this.maxHunger = maxHunger;
	}

	public float getHunger() {
		return hunger;
	}

	public void setHunger(float hunger) {
		this.hunger = hunger;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public float getAverageDeathAge() {
		return averageDeathAge;
	}

	public void setAverageDeathAge(float averageDeathAge) {
		this.averageDeathAge = averageDeathAge;
	}

	public float getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(float maxSize) {
		this.maxSize = maxSize;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public float getChanceOfDeathAtBirth() {
		return chanceOfDeathAtBirth;
	}

	public void setChanceOfDeathAtBirth(float chanceOfDeathAtBirth) {
		this.chanceOfDeathAtBirth = chanceOfDeathAtBirth;
	}

	public float getChanceOfDeathDuringLife() {
		return chanceOfDeathDuringLife;
	}

	public void setChanceOfDeathDuringLife(float chanceOfDeathDuringLife) {
		this.chanceOfDeathDuringLife = chanceOfDeathDuringLife;
	}

	public float getChanceOfDeathAfterGivingBirth() {
		return chanceOfDeathAfterGivingBirth;
	}

	public void setChanceOfDeathAfterGivingBirth(float chanceOfDeathAfterGivingBirth) {
		this.chanceOfDeathAfterGivingBirth = chanceOfDeathAfterGivingBirth;
	}

	public float getFertileAgeMin() {
		return fertileAgeMin;
	}

	public void setFertileAgeMin(float fertileAgeMin) {
		this.fertileAgeMin = fertileAgeMin;
	}

	public float getFertileAgeMax() {
		return fertileAgeMax;
	}

	public void setFertileAgeMax(float fertileAgeMax) {
		this.fertileAgeMax = fertileAgeMax;
	}

	public boolean isFertile() {
		return getAge() > getFertileAgeMin() && getAge() < getFertileAgeMax();
	}

	/**
	 * this is the identifying type of the entity
	 * */
	public String getEcoType() {
		return "ecoEntity_unnamed";
	}

	public String toString() {
		return getEcoType();
	}

	public LogarithmicValue getNetHunger() {
		return netHunger;
	}

	public void setNetHunger(LogarithmicValue netHunger) {
		this.netHunger = netHunger;
	}

	public float getAverageDeathAgeRange() {
		return averageDeathAgeRange;
	}

	public void setAverageDeathAgeRange(float averageDeathAgeRange) {
		this.averageDeathAgeRange = averageDeathAgeRange;
	}

	/**
	 * where a positive value will increase the hunger amount (more is full, less is starving). A negative number will decrease the number
	 * */
	public void replenishHunger(LogarithmicValue value) {
		this.netHunger.increase(value.getAmount());
	}

	/**
	 * where a positive value will increase the hunger amount (more is full, less is starving). A negative number will decrease the number
	 * */
	public void replenishHunger(float value) {
		this.replenishHunger(new LogarithmicValue(value, this));
	}

	public float getMaxAge() {
		return averageDeathAge + ageOffset;
	}

	public float getHungerPercent() {
		return hunger / maxHunger;
	}

	protected float getMaxTimeSinceSocialization() {
		return averageDeathAge * socializationRate;//TODO
	}

	public float getPercentNeedsToSocial() {
		return timeSinceSocialContact / getMaxTimeSinceSocialization();
	}

	protected float getMaxTimeSinceReproduction() {
		return averageDeathAge * reproductionRate;//TODO make this more meaningful
	}

	public float getPercentNeedsToReproduce() {
		if (!isFertile()) return 0;
		return timeSinceReproduction / getMaxTimeSinceReproduction();
	}

	public boolean willCannibalize() {
		return false;//TODO cannibalism
	}

	public float getEntityReach() {
		return scale * 2f;
	}

	public float getFoodValue() {
		float value = scale * getChallengeRating() * getAttackStrength(EntityLiving.MELEE);
		return value;
	}

	public boolean canEat(EntityEco e) {
		return willCannibalize() ? true : !e.getEcoType().equals(getEcoType());
	}

	public float getAgePercent() {
		return age / (averageDeathAge + ageOffset);
	}

	public float getChallengeRating() {
		return 10;
	}

	public float getAgeOffset() {
		return ageOffset;
	}

	public void setAgeOffset(float ageOffset) {
		this.ageOffset = ageOffset;
	}

	public float getTimeSinceSocialContact() {
		return timeSinceSocialContact;
	}

	public void setTimeSinceSocialContact(float timeSinceSocialContact) {
		this.timeSinceSocialContact = timeSinceSocialContact;
	}

	public float getTimeSinceReproduction() {
		return timeSinceReproduction;
	}

	public void setTimeSinceReproduction(float timeSinceReproduction) {
		this.timeSinceReproduction = timeSinceReproduction;
	}

	public float getMinSize() {
		return minSize;
	}

	public void setMinSize(float minSize) {
		this.minSize = minSize;
	}

	public float getHungerDecreaseRate() {
		return -0.01f * getFoodValue();
	}

	@Override
	public void onKillEntity(EntityLiving victim) {
		if (victim instanceof EntityEco) {
			EntityEco e = (EntityEco) victim;
			this.replenishHunger(e.getFoodValue());
		} else {
			this.replenishHunger(0.5f);
		}
	}

	public float getAttackStrength(String type) {
		return attackStrength;
	}

	public void setAttackStrength(float attackStrength) {
		this.attackStrength = attackStrength;
	}

	public boolean isViableMate(EntityEco mate) {
		if (mate == this) return false;
		if (mate.getEcoType().equals(getEcoType())) {
			if (this.gender == GENDER_MALE) { return mate.gender == GENDER_FEMALE; }
			if (this.gender == GENDER_FEMALE) { return mate.gender == GENDER_FEMALE; }
		}
		return false;

	}

	public float getCompatibility(EntityEco e) {
		if (!isViableMate(e)) return -1;
		return getAgePercent() * e.getAgePercent();
	}

	public float getSocialCompatibility(EntityEco e) {
		if (e.getEcoType().equals(getEcoType())) return 1f;
		return 0;
	}

	public boolean hasAMate() {
		return hasAMate;
	}

	public void setHasAMate(boolean hasAMate) {
		this.hasAMate = hasAMate;
	}

	/**
	 * @return the hasAFriend
	 */
	public boolean hasAFriend() {
		return hasAFriend;
	}

	/**
	 * @param hasAFriend the hasAFriend to set
	 */
	public void setHasAFriend(boolean hasAFriend) {
		this.hasAFriend = hasAFriend;
	}

	/**
	 * @return the reproductionRate
	 */
	public float getReproductionRate() {
		return reproductionRate;
	}

	/**
	 * @param reproductionRate the reproductionRate to set
	 */
	public void setReproductionRate(float reproductionRate) {
		this.reproductionRate = reproductionRate;
	}

	/**
	 * @return the socializationRate
	 */
	public float getSocializationRate() {
		return socializationRate;
	}

	/**
	 * @param socializationRate the socializationRate to set
	 */
	public void setSocializationRate(float socializationRate) {
		this.socializationRate = socializationRate;
	}

	protected void performDeathAnimation() {
		if (!level.getTileManager().isSolid(getEntityCenter())) if (death != null) death.createExplosion(getParticleStart(), (int) (250 * scale));
	}

	protected Vector2f getNearbyBabyLocation() {
		Vector2f pos = new Vector2f(position);
		float dx = (level.getRandom().nextFloat() * 2f) - 1f;
		float dy = (level.getRandom().nextFloat() * 2f) - 1f;
		pos.x += dx;
		pos.y += dy;
		return pos;
	}

	/**
	 * @return the edibleTypes
	 */
	public List<String> getEdibleTypes() {
		return edibleTypes;
	}

	/**
	 * @param edibleTypes the edibleTypes to set
	 */
	public void setEdibleTypes(List<String> edibleTypes) {
		this.edibleTypes = edibleTypes;
	}

	public void addEdibleType(String type) {
		edibleTypes.add(type);
	}

	public void removeEdibleType(String type) {
		edibleTypes.remove(type);
	}

}
