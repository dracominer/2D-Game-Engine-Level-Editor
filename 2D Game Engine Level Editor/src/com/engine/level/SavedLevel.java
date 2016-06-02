package com.engine.level;

import com.engine.data.DataFile;
import com.engine.entites.living.Player;
import com.engine.toolbox.Strings;

public abstract class SavedLevel extends Level {

	protected DataFile file;

	public SavedLevel(int width, int height, Player player) {
		super(width, height, player);
		initFile();
	}

	public SavedLevel(Player player) {
		super(player);
		initFile();
	}

	private void initFile() {
		file = new DataFile(getLevelName(), getFilePath());
		file.init();
	}

	public String getFilePath() {
		return Strings.DEFAULT_LEVEL_DATA_FOLDER + "/" + getLevelName() + ".dna";

	}

	public abstract String getLevelName();

	public void loadLevel() {
		boolean success = file.loadDataFromFile();
		if (success) {
			tileManager.createTilesFromData(file, this);
			onDataLoaded();
			System.out.println("Succesfully loaded all data for level " + getLevelName());
		} else {
			System.out.println("Failed to load data for level " + getLevelName());
			onDataFailToLoad();
			System.out.println("Completed level failsafe for " + getLevelName());
		}
		for (String s : file.getLines()) {
			processLine(s);
		}
	}

	protected void processLine(String line) {
		if (line.startsWith("seed")) {
			String[] parts = line.split("=");
			super.setSeed(Long.parseLong(parts[1]));
			rand.setSeed(seed);
		}
	}

	/**
	 * called when the data is successfully loaded.
	 **/
	public abstract void onDataLoaded();

	/**
	 * this is called when, for whatever reason, the DataFile fails to load data from the file associated with this level. Normally this means that the file does not exist and then this will be the primary initialization of the level
	 * */
	public abstract void onDataFailToLoad();

	public void saveLevel() {
		tileManager.putTilesIntoData(file);
		preSave();
		file.saveData();
	}

	/**
	 * this is called after the default data is loaded to the file
	 * */
	public void preSave() {
		file.add("seed=" + seed);

	}

}
