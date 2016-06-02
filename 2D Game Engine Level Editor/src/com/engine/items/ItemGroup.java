package com.engine.items;

public class ItemGroup {

	private Item item = ItemRegistry.getItemFromId(ItemRegistry.Default_Item);
	private int amount = 0;

	public ItemGroup(Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}

	public ItemGroup(Item item) {
		this.item = item;
		this.amount = 1;
	}

	public void incrementSize(int amount) {
		this.amount += amount;
		checkSize();
	}

	public boolean consume(int amount) {
		if (this.amount >= amount) {
			this.amount -= amount;
			return true;
		}
		return false;
	}

	public boolean canConsume(int amount) {
		return this.amount >= amount;
	}

	private void checkSize() {
		if (amount < 0) amount = 0;
		if (amount > item.getMaxGroupSize()) {
			System.out.println("Exceeding max stack size... size of " + amount);
		}
	}

	public boolean isEmpty() {
		checkSize();
		return amount == 0;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	public int getMaxAmount() {
		if (item == null) return 1;
		return item.maxGroupSize;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
