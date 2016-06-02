package com.engine.entites.living;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import com.engine.entites.living.eco.EntityEco;
import com.engine.entites.living.item.EntityWeapon;
import com.engine.entites.projectiles.EntityRubberBall;
import com.engine.items.Inventory;
import com.engine.items.ItemGroup;
import com.engine.items.ItemRegistry;
import com.engine.level.Level;
import com.engine.level.tiles.Tile;
import com.engine.physics.Force;
import com.engine.render.DisplayManager;
import com.engine.render.textures.AnimatedTexture;
import com.engine.render.textures.ModelTexture;

public class Player extends EntityEco {

	public static final float PLAYER_SIZE = 0.5f;

	public static final float MOVE_SPEED = 5f;

	public static enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	private static final float MAX_HEALTH = 10000;
	private static final float REGENERATION = 23.14f;
	private static final float PROJECTILE_INNACURACY = 10f;
	//	private static final float PROJECTILE_MAX_DISTANCE = 50f;
	private static final float PROJECTILE_STRENGTH = 10f;

	private static final float MELEE_SPEED = 200;
	private static final float MELEE_RANGE = 75;
	private static final float MELEE_SCALE_MULTIPLIER = 1.75f;

	private float attackCooldown = 0;

	private float lastSwingDir = 1;
	private float time = 0;

	private Inventory inventory = new Inventory();

	Tile prevTile;

	private Direction direction = Direction.DOWN;

	private AnimatedTexture anim_down;

	public Player(Level l, Vector2f position, float rotation, ModelTexture modelTexture) {
		super(l, position, rotation, PLAYER_SIZE, modelTexture);
		this.setMaxHealth(MAX_HEALTH);
		this.setHealth(MAX_HEALTH - 4);
		this.setRegen(REGENERATION, true);
		anim_down = new AnimatedTexture(modelTexture);
	}

	public Player(ModelTexture texture) {
		super(null, new Vector2f(), 0, 1, texture);
	}

	protected void initializeEntity() {
		super.initializeEntity();
		inventory.add(new ItemGroup(ItemRegistry.getItemFromId(ItemRegistry.Gold_Piece), 5));
	}

	public void update() {
		updateTexture();
		checkInput();
		super.updatePhysics();
		updateRotation();
		if (prevTile != null) prevTile.setSelected(false);
		prevTile = level.getTileManager().getTileAt(getEntityCenter());
		if (prevTile != null) {
			prevTile.setSelected(true);
		}
		super.updateHealth();
		attackAttempt();
		attackCooldown -= DisplayManager.getFrameTimeSeconds();
		//		System.out.println("Player Health: " + this.getHealth());
	}

	private void updateTexture() {
		time += DisplayManager.getFrameTimeSeconds();
		if (time > 0.27f) {
			anim_down.nextFrame();
			time = 0;
		}
	}

	/**
	 * @return the textureOffsets
	 */
	@Override
	public Vector2f getTextureOffsets() {
		return anim_down.getOffsets();
	}

	/**
	 * @return the texturedModel
	 */
	@Override
	public ModelTexture getTexture() {
		if (direction == Direction.DOWN) return anim_down;
		return anim_down;
	}

	private void attackAttempt() {
		if (Mouse.isButtonDown(1) && attackCooldown <= 0) {
			EntityRubberBall proj = new EntityRubberBall(this);
			proj.setInnacuracy(PROJECTILE_INNACURACY);
			proj.fire(PROJECTILE_STRENGTH);
			level.add(proj);
			attackCooldown = proj.getAttackCooldown();
		}
		if (Mouse.isButtonDown(0) && attackCooldown <= 0) {
			EntityWeapon melee = new EntityWeapon(level, position, rotation, scale * MELEE_SCALE_MULTIPLIER, this);
			melee.setSpeed(MELEE_SPEED);
			melee.setRotationStart(rotation);
			melee.setRotationDirection(lastSwingDir *= -1);
			melee.setRotationRange(MELEE_RANGE);
			level.add(melee);
			attackCooldown = melee.getAttackCoolDown();
		}
	}

	private void updateRotation() {
		Vector2f mouse = level.getMouseCoords();
		float myX = DisplayManager.getCenterX();
		float myY = DisplayManager.getCenterY();
		float mouseX = mouse.x;
		float mouseY = mouse.y;
		float dx = mouseX - myX;
		float dy = mouseY - myY;
		float theta = (float) Math.atan2(dy, dx);
		float degrees = (float) Math.toDegrees(theta) - 90f;
		setRotation(degrees);
	}

	private void checkInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) applyForce(Force.PLAYER_UP);
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) applyForce(Force.PLAYER_DOWN);
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) applyForce(Force.PLAYER_LEFT);
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) applyForce(Force.PLAYER_RIGHT);
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			inventory.printInventory();
		}
	}

	/**
	 * @return the rotation
	 */
	public float getRotationForRender() {
		return 0;
	}

	@Override
	protected float getFrictionAtLocation() {
		if (prevTile != null) return prevTile.getFriction();
		return 0.987f;
	}

	public float getRotationTowardsMouse() {
		return rotation;
	}

	@Override
	public EntityEco getBaby() {
		return null;
	}

}
