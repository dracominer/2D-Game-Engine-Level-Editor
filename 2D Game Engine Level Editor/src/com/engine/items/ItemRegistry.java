package com.engine.items;

import java.util.HashMap;

import com.engine.render.textures.TextureManager;

public class ItemRegistry {

	private static HashMap<Integer, Item> items = new HashMap<Integer, Item>();
	private static int lastID = 0;
	public static final int Default_Item = lastID++;
	public static final int Gold_Piece = lastID++;
	public static final int Helmet = lastID++;
	public static final int Sword = lastID++;
	public static final int Bow = lastID++;
	public static final int Arrow = lastID++;

	static {
		System.out.println("loading items");
		loadAllItems();
		System.out.println("finished loading items");
	}

	private static void loadAllItems() {
		loadItem(Default_Item, new Item(TextureManager.getTexture("debug"), "debug"));
		loadItem(Gold_Piece, new Item(TextureManager.getTexture("coin"), "gold coin"));
	}

	public static void loadItem(int id, Item item) {
		items.put(id, item);
		System.out.println("at id " + id + " loaded item '" + item.getName() + "'.");
	}

	public static Item getItemFromId(int id) {
		return items.get(id);
	}

	public static Item getNewItemFromId(int id) {
		return items.get(id).copy();
	}

}
