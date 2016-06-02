package com.engine.level;

import com.engine.entites.living.Player;

public abstract class PreMadeLevel extends Level {

	public PreMadeLevel(int width, int height, Player player) {
		super(width, height, player);
	}

	public PreMadeLevel(Player player) {
		super(player);
	}

	protected void init() {}

	public Player onLevelClosed() {
		return getPlayer();
	}

	public void onLevelReopened(Player newPlayer) {
		this.player = processNewPlayer(newPlayer);
		add(player);
	}

	protected abstract String getLevelFile();

}
