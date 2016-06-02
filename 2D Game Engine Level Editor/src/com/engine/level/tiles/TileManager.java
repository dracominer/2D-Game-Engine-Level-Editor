package com.engine.level.tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.engine.data.DataFile;
import com.engine.level.SavedLevel;
import com.engine.level.tiles.generation.CelularAutonoma;
import com.engine.render.MasterRenderer;
import com.engine.render.textures.TextureManager;
import com.engine.toolbox.Strings;

public class TileManager {

	public static final String prefix = "tile_";

	public static Tile grass;
	public static Tile stone;

	private Tile[][] tiles;

	private int width;
	private int height;

	public TileManager(int width, int height) {
		TileRegistry.init();
		this.height = height;
		this.width = width;
		tiles = new Tile[this.width][this.height];
		grass = new Tile(TextureManager.get(prefix + "grass"), 1.04f).setSolid(false);
		stone = new Tile(TextureManager.get(prefix + "stone"), 1.01f).setSolid(true).setRestitution(1.5f);
		System.out.println("Stone Solid? " + stone.isSolid());
		System.out.println("Sand Solid? " + grass.isSolid());
	}

	private CelularAutonoma autonoma;
	private boolean[][] cells;

	public void generateCellularAutonoma(float chanceToStartAlive, int numberOfSteps, long seed) {
		autonoma = new CelularAutonoma(chanceToStartAlive, numberOfSteps);
		autonoma.setSeed(seed);
		cells = autonoma.generate(width, height);
		processCellList(cells);
	}

	public void step(boolean broken) {
		cells = autonoma.doSimulationStep(cells);
		processCellList(cells);
	}

	private void processCellList(boolean[][] cells) {
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[0].length; y++) {
				tiles[x][y] = cells[x][y] ? stone.copy().setPosition(x, y) : grass.copy().setPosition(x, y);
			}
		}
	}

	public Vector2f getLogicalSpawnpoint(Random r, int tries, Vector2f playerPos, float minDistance) {
		Vector2f result = createRandomSpawnpoint(r);
		Tile t = getTileAt(result);
		int count = 0;
		while (true) {
			result = createRandomSpawnpoint(r);
			t = getTileAt(result);
			count++;
			if (t.isSolid()) {
				Vector2f diff = Vector2f.sub(playerPos, result, null);
				if (Math.abs(diff.length()) >= minDistance) break;
			}
			if (count >= tries) break;
		}
		return result;
	}

	public Vector2f getLogicalSpawnpoint(Random r, int tries) {
		Vector2f result = createRandomSpawnpoint(r);
		Tile t = getTileAt(result);
		int count = 0;
		while (t != null && t.isSolid()) {
			result = createRandomSpawnpoint(r);
			t = getTileAt(result);
			count++;
			if (count >= tries) break;
		}
		return result;
	}

	public Vector2f getLogicalSpawnpoint(Random r) {
		Vector2f result = createRandomSpawnpoint(r);
		Tile t = getTileAt(result);
		while (t.isSolid()) {
			result = createRandomSpawnpoint(r);
			t = getTileAt(result);
		}
		return result;
	}

	private Vector2f createRandomSpawnpoint(Random r) {
		int x = r.nextInt(width);
		int y = r.nextInt(height);
		return new Vector2f(x, y);
	}

	public boolean isIndexWithinBounds(int x, int y) {
		return !(x < 0 || x >= width || y < 0 || y >= height);
	}

	public Tile getTileAt(float worldX, float worldY) {
		int x = (int) Math.floor(worldX / Tile.TileSize);
		int y = (int) Math.floor(worldY / Tile.TileSize);
		if (!isIndexWithinBounds(x, y)) return null;
		return tiles[x][y];
	}

	public Tile getTileAt(Vector2f position) {
		return getTileAt(position.x, position.y);
	}

	public boolean isSolid(float x, float y) {
		Tile t = getTileAt(x, y);
		if (t == null) return true;
		return t.isSolid();
	}

	public boolean isSolid(Vector2f pos) {
		return this.isSolid(pos.x, pos.y);
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void processTiles(MasterRenderer renderer) {
		for (Tile[] sub : tiles) {
			for (Tile t : sub) {
				if (t == null) continue;
				renderer.processTile(t);
			}
		}
	}

	public void generateBinarySpacePartitionDungeon() {}

	public void loadLevel(DataFile data) {
		loadingTiles = false;
		width = height = -1;
		boolean updatedTileList = false;
		for (String line : data.getLines()) {
			if (line == null) break;
			if (line.startsWith(Strings.LEVEL_FILE_COMMENT)) continue;
			if (width > 0 && height > 0 && !updatedTileList) {
				tiles = new Tile[width][height];
				updatedTileList = true;
			}
			handleLine(line);
		}
		loadingTiles = false;
	}

	private boolean loadingTiles = false;
	private int load_X = 0;
	private int load_Y = 0;

	private void handleLine(String line) {
		if (line == null) return;
		if (loadingTiles) {
			if (line.equals(Strings.LEVEL_FILE_TILES_END)) {
				loadingTiles = false;
				return;
			}
			if (line.equals(Strings.LEVEL_FILE_TILES_ROW_SEPERATOR)) {
				load_Y = 0;
				load_X++;
				return;
			}
			if (line.equals(Strings.LEVEL_FILE_TILES_NULL_TILE)) {
				setTile(load_X, load_Y, null);
				load_Y++;
				return;
			}
			String[] parts = line.split(Tile.split);
			Tile t;
			int id = Integer.parseInt(parts[0]);
			Class<? extends Tile> clazz = TileRegistry.getTile(id);
			try {
				t = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				t = new Tile();
			}
			t.setDensity(Float.parseFloat(parts[1]));
			t.setRestitution(Float.parseFloat(parts[2]));
			t.setSolid(Boolean.parseBoolean(parts[3]));
			t.setTexture(TextureManager.get(parts[4]));
			t.setPosition(load_X, load_Y);
			System.out.println("loading a tile @ (" + load_X + "," + load_Y + ").");
			setTile(load_X, load_Y, t);
			load_Y++;
			hasRealTiles = true;
			return;
		}
		if (line.startsWith("width")) {
			String[] parts = line.split("=");
			this.width = Integer.parseInt(parts[parts.length - 1]);
			return;
		}
		if (line.startsWith("height")) {
			String[] parts = line.split("=");
			this.height = Integer.parseInt(parts[parts.length - 1]);
			return;
		}
		if (line.equals(Strings.LEVEL_FILE_TILES_START)) {
			loadingTiles = true;
			return;
		}
		if (line.equals(Strings.LEVEL_FILE_TILES_END)) {
			loadingTiles = false;
			return;
		}
	}

	private void setTile(int x, int y, Tile t) {
		if (!isIndexWithinBounds(x, y)) return;
		if (t != null) t.setPosition(x, y);
		tiles[x][y] = t;
	}

	public void setTile(Vector2f mouseWorldPosition, Tile tile) {
		int x = (int) Math.floor(mouseWorldPosition.x / Tile.TileSize);
		int y = (int) Math.floor(mouseWorldPosition.y / Tile.TileSize);
		if (!isIndexWithinBounds(x, y)) return;
		tile.setPosition(x, y);
		tiles[x][y] = tile;
	}

	private boolean hasRealTiles = false;

	public void createTilesFromData(DataFile data, SavedLevel savedLevel) {
		hasRealTiles = false;
		for (String line : data.getLines()) {
			handleLine(line);
		}
		if (!hasRealTiles) {
			savedLevel.onDataFailToLoad();
		}
	}

	public void putTilesIntoData(DataFile data) {
		List<String> lines = new ArrayList<String>();
		lines.add(Strings.LEVEL_FILE_IDENTIFIER);
		lines.add(Strings.LEVEL_FILE_NOTES);
		lines.add("width=" + width);
		lines.add("height=" + height);
		lines.add(Strings.LEVEL_FILE_TILES_START);
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				Tile t = tiles[x][y];
				if (t == null) {
					lines.add(Strings.LEVEL_FILE_TILES_NULL_TILE);
				} else {
					lines.add(t.toString());
				}
			}
			lines.add(Strings.LEVEL_FILE_TILES_ROW_SEPERATOR);
		}
		lines.add(Strings.LEVEL_FILE_TILES_END);
		data.addAll(lines);

	}

}
