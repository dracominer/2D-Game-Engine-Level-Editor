package com.engine.render.textures;

import java.util.HashMap;
import java.util.Map;

import com.engine.level.tiles.TileManager;
import com.engine.render.Loader;
import com.engine.toolbox.ResourceLocation;

public class TextureManager {

	private static Map<String, Integer> textures = new HashMap<String, Integer>();
	private static Loader loader;

	static {
		loader = new Loader();
		reloadTextures();
	}

	public static void reloadTextures() {
		registerTexture("arrow", "/sprites/arrow");
		registerTexture("debug", "/sprites/directionalSprite");
		registerTexture("baddie", "/sprites/baddie");
		registerTexture("turtle", "/sprites/turtle");
		registerTexture("crab", "/sprites/crab");
		registerTexture("fwog", "/sprites/fwog");
		registerTexture("slime", "/sprites/slime");
		registerTexture("bee", "/sprites/bee");
		registerTexture("blue_tulip", "/sprites/blue_tulip");
		registerTexture("notice", "/sprites/playerNotifier");
		registerTexture("sword", "/sprites/sword");
		registerTexture("wolf", "/sprites/wolfAtlas");
		registerTexture("gui", "/gui/gui");
		registerTexture("particle", "/particleSprites/blueFuzzyOrb");
		registerTexture("magic_orb", "/particleSprites/fuzzyOrb");
		registerTexture("saoDeath", "/particleSprites/saoParticle");
		registerTexture("saoHurt", "/particleSprites/saoHurt");
		registerTexture("saoHeal", "/particleSprites/saoHeal");
		registerTexture("feather", "/particleSprites/feather");
		registerTexture("xp", "/particleSprites/xp");
		registerTexture("healthBar", "/gui/healthbar");
		registerTexture("coin", "/items/coin");
		registerTexture(TileManager.prefix + "ground", "/tiles/dirt");
		registerTexture(TileManager.prefix + "sand", "/tiles/sand");
		registerTexture(TileManager.prefix + "stone", "/tiles/stone2");
		registerTexture(TileManager.prefix + "grass", "/tiles/grass2");
	}

	public static void loadAllTextures(String[] names, String[] files) {
		for (int i = 0; i < names.length; i++) {
			registerTexture(names[i], files[i]);
		}
	}

	public static void loadAllTextures(String[] names, ResourceLocation[] locations) {
		for (int i = 0; i < names.length; i++) {
			registerTexture(names[i], locations[i]);
		}
	}

	public static void registerTexture(String name, int id) {
		textures.put(name, id);
	}

	public static void registerTexture(String name, String path) {
		textures.put(name, loader.loadTexture(path));
	}

	public static void registerTexture(String name, ResourceLocation loc) {
		textures.put(name, loader.loadTexture(loc));
	}

	public static int get(String name) {
		return textures.get(name);
	}

	public static ModelTexture getTexture(String name) {
		return new ModelTexture(get(name));
	}

	public static void cleanup() {
		loader.cleanup();
	}

	public static String getKeyForValue(int texture) {
		for (String key : textures.keySet()) {
			if (textures.get(key).equals(texture)) return key;
		}
		return "n/a";
	}

}
