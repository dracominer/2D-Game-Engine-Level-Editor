package com.engine.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.engine.camera.Camera;
import com.engine.entites.Entity;
import com.engine.entites.EntityPhysical;
import com.engine.entites.living.Player;
import com.engine.entites.living.eco.EntityEco;
import com.engine.entites.util.BoundingShape;
import com.engine.gui.GUI;
import com.engine.level.tiles.Tile;
import com.engine.level.tiles.TileManager;
import com.engine.lights.Light;
import com.engine.particle.ParticleMaster;
import com.engine.render.DisplayManager;
import com.engine.render.MasterRenderer;
import com.engine.render.textures.TextureManager;
import com.engine.toolbox.MousePicker;

public class Level {

	private static final float minCamZ = 0.4f;
	private static final float maxCamZ = 50;
	private static final float MOUSE_SENSITIVITY = 0.7f;

	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> entities_que = new ArrayList<Entity>();
	protected List<Entity> entities_dead = new ArrayList<Entity>();
	protected List<EntityPhysical> physicalEntities = new ArrayList<EntityPhysical>();
	protected List<GUI> guis = new ArrayList<GUI>();

	protected Vector2f spawnPoint = new Vector2f();

	protected long ticks = 0l;

	protected Camera cam;

	protected List<Light> lights = new ArrayList<Light>();

	protected MasterRenderer renderer;

	protected MousePicker picker;

	protected TileManager tileManager;

	protected Player player;

	protected Random rand = new Random();

	private boolean hasInitialized = false;

	protected float timeSinceUpdate = 0;

	protected float timePerFrame = 1f / 60f;

	protected long seed = 0;

	//	protected Fbo postProcessFBO = new Fbo(DisplayManager.getWidthi(), DisplayManager.getHeighti(), Fbo.DEPTH_RENDER_BUFFER);

	public Level(int width, int height, Player player) {
		renderer = new MasterRenderer();
		tileManager = new TileManager(width, height);
		cam = new Camera(new Vector3f(0, 0, 5));
		this.player = processNewPlayer(player);
		picker = new MousePicker(cam, MasterRenderer.getProjectionMatrix());
		this.seed = rand.nextLong();
		rand.setSeed(seed);
	}

	protected Player processNewPlayer(Player player) {
		player.setLevel(this);
		player.setPosition(getSpawnPosition());
		return player;
	}

	protected Player initPlayer(Player base) {
		base.setPosition(getSpawnPosition());
		base.setScale(1.0f);
		base.setRotation(0);
		base.setLevel(this);
		return base;
	}

	protected Player initPlayer() {
		Player base = new Player(TextureManager.getTexture("wolf").setNumberOfRows(16));
		base.setPosition(getSpawnPosition());
		base.setScale(1.0f);
		base.setRotation(0);
		base.setLevel(this);
		return base;
	}

	public Level(Player player) {
		this(50, 50, player);
	}

	protected void init() {
		this.player = initPlayer();
		add(new Light(new Vector2f(0, 0), new Vector3f(1, 1, 1)));
		add(this.player);
	}

	public void add(GUI gui) {
		guis.add(gui);
	}

	public void remove(GUI gui) {
		guis.remove(gui);
	}

	public void updateWhilePaused() {
		cam.update(minCamZ, maxCamZ, MOUSE_SENSITIVITY, player.getPosition());
	}

	public void update() {
		timeSinceUpdate += DisplayManager.getFrameTimeSeconds();
		if (!hasInitialized) {
			init();
			hasInitialized = true;
		}
		if (timeSinceUpdate >= timePerFrame) {
			timeSinceUpdate = 0;
			perTickUpdate();
		}
	}

	protected void perTickUpdate() {
		cam.update(minCamZ, maxCamZ, MOUSE_SENSITIVITY, player.getPosition());
		if (ticks == 0) init();
		ticks++;
		picker.update(cam, didMouseWorldPosChange());
		refreshLists();
		physicalEntities.clear();
		for (Entity e : entities) {
			if (e instanceof EntityPhysical) physicalEntities.add((EntityPhysical) e);
		}
		for (Entity e : entities) {
			e.update();
		}
		ParticleMaster.update(getCam());
	}

	public void add(Entity e) {
		entities_que.add(e);
	}

	public void remove(Entity e) {
		entities_dead.add(e);
	}

	public void add(Light l) {
		lights.add(l);
	}

	public void remove(Light l) {
		lights.remove(l);
	}

	public void render() {
		//		postProcessFBO.bindFrameBuffer();
		renderScene();
		//		postProcessFBO.unbindFrameBuffer();
		//		PostProcessing.doPostProcessing(postProcessFBO.getColourTexture());
	}

	protected void refreshLists() {
		for (Entity e : entities_dead) {
			if (e == player) {
				gameOver();
			}
			entities.remove(e);
		}
		for (Entity e : entities_que) {
			e.setLevel(this);
			entities.add(e);
		}
		entities_dead.clear();
		entities_que.clear();
	}

	protected void gameOver() {
		player.setHealth(player.getMaxHealth());
		LevelManager.nextLevel();
	}

	protected void renderScene() {
		process();
		renderer.render(cam);
		renderer.clearAll();
	}

	private void process() {
		for (Entity e : entities) {
			if (e == null) continue;
			renderer.processEntity(e);
		}
		tileManager.processTiles(renderer);
		for (GUI g : guis) {
			renderer.processGui(g);
		}
	}

	public Vector2f getSpawnPosition() {
		return new Vector2f(spawnPoint.x, spawnPoint.y);
	}

	public Camera getCam() {
		return cam;
	}

	public TileManager getTileManager() {
		return tileManager;
	}

	public Player getPlayer() {
		if (player == null) System.out.println("Player was found null");
		return this.player;
	}

	public boolean didCollide(EntityPhysical source) {
		return getCollision(source) == null;
	}

	public EntityPhysical getCollision(EntityPhysical source) {
		if (source == null) return null;
		BoundingShape box = source.getBoundingBox();
		for (EntityPhysical p : physicalEntities) {
			if (p == null) continue;
			if (p == source) continue;
			if (box.collidesWith(p.getBoundingBox())) return p;
		}
		return null;
	}

	public List<EntityPhysical> getCollisions(EntityPhysical source) {
		List<EntityPhysical> result = new ArrayList<EntityPhysical>();
		if (source == null) return null;
		BoundingShape box = source.getBoundingBox();
		for (EntityPhysical p : physicalEntities) {
			if (p == null) continue;
			if (p == source) continue;
			if (box.collidesWith(p.getBoundingBox())) result.add(p);
		}
		return result;
	}

	public boolean futureCollision(EntityPhysical source) {
		if (source == null) return false;
		BoundingShape box = source.getBoundingBox();
		Vector2f pos = source.getEntityCenter();
		Vector2f vel = source.getVelocity();
		box.setPosition(pos.x + vel.x * DisplayManager.getFrameTimeSeconds(), pos.y + vel.y * DisplayManager.getFrameTimeSeconds());
		for (EntityPhysical p : physicalEntities) {
			if (p == null) continue;
			if (p == source) continue;
			if (box.collidesWith(p.getBoundingBox())) return true;
		}
		return false;
	}

	public boolean isInSolidTile(Entity e) {
		return tileManager.isSolid(e.getEntityCenter());
	}

	public Random getRandom() {
		return this.rand;
	}

	public float getGravity() {
		return -9.8f;
	}

	public boolean isInSolidTile(Vector2f pos) {
		return tileManager.isSolid(pos);
	}

	public boolean isInSolidTile(Vector3f position) {
		return this.isInSolidTile(new Vector2f(position));
	}

	public float getFrictionAtLocation(Vector2f entityCenter) {
		Tile t = tileManager.getTileAt(entityCenter);
		if (t == null) return 0.987f;
		return t.getFriction();
	}

	public boolean futureTileCollision(EntityPhysical e, float dx, float dy) {
		Vector2f pos = new Vector2f(e.getEntityCenter().x + dx, e.getEntityCenter().y + dy);
		return isInSolidTile(pos);
	}

	public Player onLevelClosed() {
		return getPlayer();
	}

	public void onLevelReopened(Player newPlayer) {
		this.player = processNewPlayer(newPlayer);
		add(player);
	}

	public List<Entity> getCloseEntities(Vector2f origin, float range) {
		List<Entity> result = new ArrayList<Entity>();
		for (Entity e : entities) {
			Vector2f diff = Vector2f.sub(e.getEntityCenter(), origin, null);
			if (diff.length() <= range) {
				result.add(e);
			}
		}
		return result;
	}

	public List<EntityEco> getEcoEntities(Entity source, float range) {
		List<Entity> es = getCloseEntities(source.getEntityCenter(), range);
		List<EntityEco> ecos = new ArrayList<EntityEco>();
		for (Entity e : es) {
			if (e instanceof EntityEco) {
				ecos.add((EntityEco) e);
			}
		}
		return ecos;
	}

	public float getRestitutionAtLocation(float x, float y) {
		Tile t = tileManager.getTileAt(x, y);
		if (t == null) return 1;
		return t.getRestitution();
	}

	public Vector3f getMouseWorldPosition3() {
		return picker.getMousePosition();
	}

	public Vector2f getMouseWorldPosition() {
		Vector3f pos = getMouseWorldPosition3();
		if (pos != null) return new Vector2f(pos.x, pos.y);
		return new Vector2f(-100, -100);
	}

	public Vector2f getMouseCoords() {
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}

	public boolean didMouseWorldPosChange() {
		if (player == null) return didMouseMove();
		return !player.isAtRest() || didMouseMove();
	}

	public static boolean didMouseMove() {
		return Mouse.getDX() != 0 || Mouse.getDY() != 0 || Mouse.getDWheel() != 0;
	}

	public void loadLevel() {}

	public void saveLevel() {}

	protected void refreshSpawnPoint() {
		this.spawnPoint = tileManager.getLogicalSpawnpoint(rand);
	}

	/**
	 * @return the picker
	 */
	public MousePicker getPicker() {
		return picker;
	}

	/**
	 * @param picker the picker to set
	 */
	public void setPicker(MousePicker picker) {
		this.picker = picker;
	}

	/**
	 * @return the rand
	 */
	public Random getRand() {
		return rand;
	}

	/**
	 * @param rand the rand to set
	 */
	public void setRand(Random rand) {
		this.rand = rand;
	}

	/**
	 * @return the hasInitialized
	 */
	public boolean isHasInitialized() {
		return hasInitialized;
	}

	/**
	 * @param hasInitialized the hasInitialized to set
	 */
	public void setHasInitialized(boolean hasInitialized) {
		this.hasInitialized = hasInitialized;
	}

	/**
	 * @return the timeSinceUpdate
	 */
	public float getTimeSinceUpdate() {
		return timeSinceUpdate;
	}

	/**
	 * @param timeSinceUpdate the timeSinceUpdate to set
	 */
	public void setTimeSinceUpdate(float timeSinceUpdate) {
		this.timeSinceUpdate = timeSinceUpdate;
	}

	/**
	 * @return the timePerFrame
	 */
	public float getTimePerFrame() {
		return timePerFrame;
	}

	/**
	 * @param timePerFrame the timePerFrame to set
	 */
	public void setTimePerFrame(float timePerFrame) {
		this.timePerFrame = timePerFrame;
	}

	/**
	 * @return the seed
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * @param seed the seed to set
	 */
	public void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * @param cam the cam to set
	 */
	public void setCam(Camera cam) {
		this.cam = cam;
	}

	/**
	 * @param tileManager the tileManager to set
	 */
	public void setTileManager(TileManager tileManager) {
		this.tileManager = tileManager;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

}
