package com.engine.entites;

import org.lwjgl.util.vector.Vector2f;

import com.engine.level.Level;
import com.engine.level.tiles.Tile;
import com.engine.render.textures.ModelTexture;

public class Entity {

	protected Vector2f position;
	protected float rotation;
	protected float scale;
	protected Vector2f textureOffsets = new Vector2f(0, 0);

	protected boolean firstUpdate = true;

	/**
	 * amount of gui to display
	 * */
	protected Vector2f percent = new Vector2f(1.1f, 1.1f);

	protected ModelTexture texture;

	protected Level level;

	public Entity(Level l, Vector2f position, float rotation, float scale, ModelTexture modelTexture) {
		this.level = l;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.texture = modelTexture;
	}

	public void setLevel(Level l) {
		this.level = l;
	}

	public void update() {
		if (firstUpdate) {
			firstUpdate = false;
			initializeEntity();
		}
	}

	protected void initializeEntity() {}

	/**
	 * @return the position
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @return the rotation
	 */
	public float getRotationForRender() {
		return rotation;
	}

	/**
	 * @param rotation the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * @return the scale
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * @return the texturedModel
	 */
	public ModelTexture getTexture() {
		return texture;
	}

	public int getTextureID() {
		return getTexture().getID();
	}

	/**
	 * @param texturedModel the texturedModel to set
	 */
	public void setModelTexture(ModelTexture texturedModel) {
		this.texture = texturedModel;
	}

	public float getTextureXOffset() {
		return textureOffsets.x;
	}

	public float getTextureYOffset() {
		return textureOffsets.y;
	}

	/**
	 * @return the textureOffsets
	 */
	public Vector2f getTextureOffsets() {
		return textureOffsets;
	}

	/**
	 * @param textureOffsets the textureOffsets to set
	 */
	public void setTextureOffsets(Vector2f textureOffsets) {
		this.textureOffsets = textureOffsets;
	}

	public void increasePosition(Vector2f amount) {
		this.position.x += amount.x;
		this.position.y += amount.y;
	}

	public void increasePosition(float x, float y) {
		this.position.x += x;
		this.position.y += y;
	}

	public void increaseRotation(float amount) {
		this.rotation += amount;
	}

	public Vector2f getEntityCenter() {
		return new Vector2f(position.x + scale, position.y + scale);
	}

	public Vector2f getTileLocation() {
		int x = (int) Math.floor(position.x / Tile.TileSize);
		int y = (int) Math.floor(position.y / Tile.TileSize);
		return new Vector2f(x, y);
	}

	public Level getLevel() {
		return level;
	}

	/**
	 * @return the percent
	 */
	public Vector2f getPercent() {
		return percent;
	}

	/**
	 * @param percent the percent to set
	 */
	public void setPercent(Vector2f percent) {
		this.percent = percent;
	}

}
