package com.engine.level;

import java.util.HashMap;

import com.engine.entites.living.Player;
import com.engine.render.Loader;
import com.engine.render.postProcessing.PostProcessing;
import com.engine.render.textures.TextureManager;

public class LevelManager {

	private static HashMap<Integer, Level> levels = new HashMap<Integer, Level>();
	private static int currentLevelID;

	public static final int ID_LEVEL_MAIN = 0;
	public static final int ID_LEVEL_SIMPLE = 1;

	public static Player player;

	public static void init() {
		PostProcessing.init(Loader.get());
		player = new Player(TextureManager.getTexture("wolf"));
		loadAllLevels();
	}

	private static void loadAllLevels() {
		put(ID_LEVEL_MAIN, new MainLevel(player));
		//		put(ID_LEVEL_SIMPLE, new SimpleLevel(player));
		LevelManager.currentLevelID = LevelManager.ID_LEVEL_MAIN;
	}

	public static void setLevel(int levelID) {
		player = get(LevelManager.currentLevelID).onLevelClosed();
		LevelManager.currentLevelID = levelID;
		get(LevelManager.currentLevelID).onLevelReopened(player);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.HashMap#containsKey(java.lang.Object)
	 */
	public static boolean containsKey(Object arg0) {
		return levels.containsKey(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.HashMap#containsValue(java.lang.Object)
	 */
	public static boolean containsValue(Object arg0) {
		return levels.containsValue(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public static Level get(Object arg0) {
		return levels.get(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public static Level put(Integer arg0, Level arg1) {
		return levels.put(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.HashMap#remove(java.lang.Object)
	 */
	public static Level remove(Object arg0) {
		return levels.remove(arg0);
	}

	public static void update() {
		get(currentLevelID).update();
	}

	public static void render() {
		get(currentLevelID).render();
	}

	public static void nextLevel() {
		//		System.out.println("loading next level");
		player = get(LevelManager.currentLevelID).onLevelClosed();
		LevelManager.currentLevelID++;
		if (get(LevelManager.currentLevelID) == null) LevelManager.currentLevelID = LevelManager.ID_LEVEL_MAIN;
		get(LevelManager.currentLevelID).onLevelReopened(player);
		//		System.out.println("level is now : (ID) " + currentLevelID);
	}

	public static void save() {
		get(currentLevelID).saveLevel();
	}

	public static void load() {
		get(currentLevelID).loadLevel();
	}

	public static void pausedUpdate() {
		get(LevelManager.currentLevelID).updateWhilePaused();
	}

}
