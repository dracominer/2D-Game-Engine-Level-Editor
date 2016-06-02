package com.engine.toolbox;

import com.engine.render.DisplayManager;

public class Strings {

	public static final String SAVE_DATA_PATH = "C:/";
	public static final String LEVEL_FILE_COMMENT = "#";
	public static final String LEVEL_FILE_IDENTIFIER = LEVEL_FILE_COMMENT + "Level File Definition";
	public static final String LEVEL_FILE_NOTES = LEVEL_FILE_COMMENT + "This is a file containing pertinent data to the levels withing a game. Editing of the files without proper knowledge of how the system works could result in breaking the game.";
	public static final String LEVEL_FILE_TILES_START = "<Tiles>";
	public static final String LEVEL_FILE_TILES_END = "</Tiles>";
	public static final String LEVEL_FILE_TILES_ROW_SEPERATOR = "-+-+-+-+-";
	public static final String LEVEL_FILE_TILES_NULL_TILE = "(empty tile)";
	public static final String DEFAULT_DATA_FOLDER = System.getProperty("user.home") + "/AppData/Local/." + DisplayManager.getTitle();
	public static final String DEFAULT_LEVEL_DATA_FOLDER = DEFAULT_DATA_FOLDER + "/levels";

}
