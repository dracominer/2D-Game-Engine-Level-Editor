package com.engine.level.tiles;

import java.util.HashMap;

public class TileRegistry {

	private static HashMap<Integer, Class<? extends Tile>> tiles = new HashMap<Integer, Class<? extends Tile>>();
	
	public static final int Tile_class_ID = 0;

	public static void init() {
		registerTile(Tile_class_ID, Tile.class);
	}

	public static void registerTile(int id, Class<? extends Tile> tileClass) {
		tiles.put(id, tileClass);
	}

	public static Class<? extends Tile> getTile(int id) {
		return tiles.get(id);
	}

}
