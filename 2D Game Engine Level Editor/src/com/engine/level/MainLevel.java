package com.engine.level;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.engine.audio.Sound;
import com.engine.entites.living.EntityMob;
import com.engine.entites.living.Player;
import com.engine.entites.living.eco.EcoBee;
import com.engine.level.tiles.Tile;
import com.engine.level.tiles.TileManager;
import com.engine.render.textures.ModelTexture;
import com.engine.render.textures.TextureManager;

public class MainLevel extends SavedLevel {

	private Sound testSound;

	public MainLevel(Player p) {
		super(p);
		tileManager = new TileManager(50, 50);
		testSound = new Sound("audio/flowey.wav");
	}

	@Override
	protected void init() {}

	private void registerEntities() {
		super.entities.clear();
		ModelTexture texture = TextureManager.getTexture("wolf").setNumberOfRows(16);
		player = new Player(this, getSpawnPosition(), 0, texture);
		add(player);
		populate();
	}

	public void populate() {
		for (int i = 0; i < 100; i++) {
			EcoBee bee = new EcoBee(this, tileManager.getLogicalSpawnpoint(rand, 30), 0, 0.1f, TextureManager.getTexture("bee"));
			add(bee);
		}
		for (int i = 0; i < 20; i++) {
			EntityMob mob = new EntityMob(this, tileManager.getLogicalSpawnpoint(rand, 30), 0, Player.PLAYER_SIZE * 0.75f, TextureManager.getTexture("baddie"));
			add(mob);
		}
	}

	protected void perTickUpdate() {
		super.perTickUpdate();
		if (Mouse.isButtonDown(2)) {
			tileManager.setTile(getMouseWorldPosition(), new Tile(TextureManager.get(TileManager.prefix + "stone"), 2.5f).setRestitution(0.1f).setSolid(false));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			tileManager.generateCellularAutonoma(0.15f, 20, rand.nextLong());
			refreshSpawnPoint();
			player.setPosition(getSpawnPosition());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			testSound.disableLooping();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			testSound.enableLooping();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
			testSound.play();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
			testSound.pause();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
			testSound.stop();
		}
	}

	@Override
	public String getLevelName() {
		return "Main Level";
	}

	@Override
	public void onDataFailToLoad() {
		tileManager.generateCellularAutonoma(0.35f, 3, rand.nextLong());
	}

	public void loadLevel() {
		super.loadLevel();
		refreshSpawnPoint();
		registerEntities();
	}

	@Override
	public void onDataLoaded() {}

	@Override
	public void preSave() {
		super.preSave();
	}

}
