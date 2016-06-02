package com.engine.level.tiles;

import org.lwjgl.util.vector.Vector2f;

import com.engine.render.textures.ModelTexture;
import com.engine.render.textures.TextureManager;

public class Tile {

	public static final float TileSize = 1.0f;

	private int texture;
	private int x;
	private int y;
	private float density = 1;
	private Vector2f textureOffsets = new Vector2f(0, 0);
	private boolean solid;
	private float restitution = 1f;

	public static final String split = ":-:";

	private boolean isSelected = false;

	public Tile() {}

	public Tile init(int texture, int x, int y, float density) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.density = density;
		return this;
	}

	public Tile(int texture, int x, int y, float density) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.density = density;
	}

	public Tile(int texture, float density) {
		this.texture = texture;
		this.x = 0;
		this.y = 0;
		this.density = density;
	}

	public Tile copy() {
		return new Tile(texture, density).setSelected(this.isSelected).setSolid(solid).setRestitution(restitution);
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public Tile setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		return this;
	}

	/**
	 * @return the texture
	 */
	public int getTexture() {
		return texture;
	}

	public ModelTexture getModelTexture() {
		return new ModelTexture(getTexture());
	}

	/**
	 * @param texture the texture to set
	 */
	public Tile setTexture(int texture) {
		this.texture = texture;
		return this;

	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	public Tile setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * @return the density
	 */
	public float getDensity() {
		return density;
	}

	/**
	 * @param density the density to set
	 */
	public Tile setDensity(float density) {
		this.density = density;
		return this;
	}

	public float getFriction() {
		return 1f / density;
	}

	/**
	 * @param textureOffsets the textureOffsets to set
	 */
	public void setTextureOffsets(Vector2f textureOffsets) {
		this.textureOffsets = textureOffsets;
	}

	public Vector2f getTextureOffsets() {
		return textureOffsets;
	}

	public boolean isSolid() {
		return solid;
	}

	public Tile setSolid(boolean value) {
		this.solid = value;
		return this;
	}

	/**
	 * @param restitution the restitution to set
	 */
	public Tile setRestitution(float restitution) {
		this.restitution = restitution;
		return this;
	}

	/**
	 * @return the restitution
	 */
	public float getRestitution() {
		return restitution;
	}

	public String toString() {
		return TileRegistry.Tile_class_ID + split + getDensity() + split + getRestitution() + split + isSolid() + split + TextureManager.getKeyForValue(getTexture());
	}

}
