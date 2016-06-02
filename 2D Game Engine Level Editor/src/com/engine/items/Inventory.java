package com.engine.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Inventory {

	private List<ItemGroup> items = new ArrayList<ItemGroup>();

	public Inventory() {
		System.out.println("created a new inventory");
	}

	public void clear(Item type, int amount) {
		int left = amount;
		for (ItemGroup g : items) {
			if (left <= 0) break;
			if (g.getItem() == type) {
				while (g.getAmount() > 0) {
					g.incrementSize(-1);
					left--;
				}
			}
		}
		refreshInventory();
	}

	public void add(Item item, int amount) {
		int left = amount;
		for (ItemGroup g : items) {
			if (left <= 0) break;
			if (g.getItem() == item) {
				while (g.getAmount() < g.getMaxAmount()) {
					g.incrementSize(1);
					left--;
				}
			}
		}
		refreshInventory();
	}

	public void refreshInventory() {
		List<ItemGroup> empty = new ArrayList<ItemGroup>();
		for (ItemGroup g : items) {
			if (g.isEmpty()) empty.add(g);
		}
		items.removeAll(empty);
	}

	public void remove(Item item, int amount) {
		int left = amount;
		for (ItemGroup g : items) {
			if (left <= 0) break;
			if (g.getItem() == item) {
				while (g.getAmount() > 0) {
					g.incrementSize(-1);
					left--;
				}
			}
		}
		refreshInventory();
	}

	/**
	 * @param item
	 * @see java.util.List#add(java.lang.Object)
	 */
	public void add(ItemGroup item) {
		items.add(item);
		refreshInventory();
	}

	/**
	 * @param item
	 * @return
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(ItemGroup item) {
		refreshInventory();
		return items.contains(item);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.List#get(int)
	 */
	public ItemGroup get(int arg0) {
		refreshInventory();
		return items.get(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(ItemGroup arg0) {
		boolean flag = items.remove(arg0);
		refreshInventory();
		return flag;
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<ItemGroup> arg0) {
		return items.removeAll(arg0);
	}

	/**
	 * @return
	 * @see java.util.List#size()
	 */
	public int size() {
		refreshInventory();
		return items.size();
	}

	public void printInventory() {
		refreshInventory();
		System.out.println("-----Inventory-----");
		for (ItemGroup g : items) {
			System.out.println(g.getAmount() + " x " + g.getItem().getName());
			System.out.println("------------------");
		}
	}

}
