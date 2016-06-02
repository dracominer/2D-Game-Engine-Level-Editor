package com.engine.level;

import com.engine.entites.living.Player;

public class SimpleLevel extends Level {

	public SimpleLevel(Player player) {
		super(player);
		this.tileManager.generateCellularAutonoma(0.25f, 1, rand.nextLong());
	}

}
