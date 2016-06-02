package com.engine.level.tiles.generation;

import java.util.Random;

public class CelularAutonoma {

	private Random rand;

	private final float chanceToStartAlive;

	private final int numberOfSteps;

	/**
	 * default: 2
	 * */
	private int deathLimit = 3;
	/**
	 * default: 3
	 * */
	private int birthLimit = 4;

	public CelularAutonoma(float initialChance, int numberOfSteps) {
		this.chanceToStartAlive = initialChance;
		this.numberOfSteps = numberOfSteps;
		rand = new Random();
	}

	public void setSeed(long seed) {
		rand.setSeed(seed);
	}

	public boolean[][] generate(int width, int height) {
		boolean[][] cells = new boolean[width][height];
		cells = initList(cells);
		cells = doSimulation(cells);
		return cells;
	}

	public boolean[][] initList(boolean[][] cells) {
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[0].length; y++) {
				if (random() < chanceToStartAlive) {
					cells[x][y] = true;
				}
			}
		}
		return cells;
	}

	public boolean[][] doSimulation(boolean[][] cells) {
		System.out.println("Performing " + numberOfSteps + " steps in the simulation");
		if (numberOfSteps <= 0) return cells;
		boolean[][] newCells = cells;
		boolean[][] oldCells = newCells;
		for (int i = 0; i < numberOfSteps; i++) {
			newCells = doSimulationStep(oldCells);
			cells = newCells;
			oldCells = newCells;
		}
		return cells;
	}

	public boolean[][] doSimulationStep(boolean[][] oldMap) {
		boolean[][] newMap = new boolean[oldMap.length][oldMap[0].length];
		for (int x = 0; x < newMap.length; x++) {
			for (int y = 0; y < newMap[0].length; y++) {
				int neighbors = getLivingNeighbors(oldMap, x, y);
				boolean current = oldMap[x][y];
				if (current) {
					newMap[x][y] = getLivingCalculation(neighbors);
				} else {
					newMap[x][y] = getDeathCalculation(neighbors);
				}
			}
		}
		return newMap;
	}

	private boolean getLivingCalculation(int neighbors) {
		if (neighbors < deathLimit) return false;
		return true;
	}

	private boolean getDeathCalculation(int neighbors) {
		if (neighbors > birthLimit) return true;
		return false;
	}

	public int getLivingNeighbors(boolean[][] cells, int x, int y) {
		int amount = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0) continue;
				int xx = x + i;
				int yy = y + j;
				if (xx < 0 || yy < 0 || xx >= cells.length || yy >= cells.length) {
					amount++;
				} else if (cells[xx][yy]) {
					amount++;
				}
			}
		}
		return amount;
	}

	public void printCellList(boolean[][] cells) {
		for (int x = 0; x < cells.length; x++) {
			String row = "";
			for (int y = 0; y < cells[0].length; y++) {
				row += cells[x][y] ? "#" : " ";
			}
			System.out.println(row);
		}
	}

	private float random() {
		return rand.nextFloat();
	}

	/**
	 * @param deathLimit the deathLimit to set
	 */
	public void setDeathLimit(int deathLimit) {
		this.deathLimit = deathLimit;
	}

	/**
	 * @param birthLimit the birthLimit to set
	 */
	public void setBirthLimit(int birthLimit) {
		this.birthLimit = birthLimit;
	}

	public boolean[][] setBorder(boolean[][] cells) {
		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[0].length; y++) {
				if (x == 0 || y == 0 || x == cells.length - 1 || y == cells[0].length - 1) cells[x][y] = true;
			}
		}
		return cells;
	}

}
