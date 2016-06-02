package com.engine.level;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.engine.camera.Camera;
import com.engine.level.tiles.Tile;
import com.engine.level.tiles.TileManager;
import com.engine.render.MasterRenderer;
import com.engine.toolbox.MousePicker;

public class Level {

	private static final float minCamZ = 5f;
	private static final float maxCamZ = 50f;
	private static final float MOUSE_SENSITIVITY = 0.01f;
	private static final float CAM_MOVE_SPEED = 20f;
	private static final float CAM_MOVE_THRESHOLD = 0.75f;

	protected Camera cam;

	protected MasterRenderer renderer;

	protected MousePicker picker;

	protected TileManager tileManager;

	protected Tile selectedTile = null;

	public Level() {
		renderer = new MasterRenderer();
		tileManager = new TileManager(25, 25);
		cam = new Camera(new Vector3f(0, 0, 5));
		picker = new MousePicker(cam, MasterRenderer.getProjectionMatrix());
		tileManager.fillWithTile(TileManager.grass);
	}

	public void update() {
		cam.update(minCamZ, maxCamZ, MOUSE_SENSITIVITY, CAM_MOVE_SPEED, CAM_MOVE_THRESHOLD);
		picker.update(cam, true);
		Vector2f position = getMouseWorldPosition();
		Tile t = tileManager.getTileAt(position);
		if (Mouse.isButtonDown(2)) {
			if (t == null) {
				System.out.println("selected tile was found null");
			} else {
				this.selectedTile = t.copy();
				System.out.print("Selected tile is now: " + selectedTile.toString());
			}
		}
		if (Mouse.isButtonDown(0)) {
			if (selectedTile != null) tileManager.setTile(position, selectedTile.copy());
		}
		if (Mouse.isButtonDown(1)) {
			tileManager.setTile(position, null);
		}
	}

	public void render() {
		renderScene();
	}

	protected void renderScene() {
		process();
		renderer.render(cam);
		renderer.clearAll();
	}

	private void process() {
		tileManager.processTiles(renderer);
	}

	public Camera getCam() {
		return cam;
	}

	public TileManager getTileManager() {
		return tileManager;
	}

	public Vector3f getMouseWorldPosition3() {
		return picker.getMousePosition();
	}

	public Vector2f getMouseWorldPosition() {
		Vector3f pos = getMouseWorldPosition3();
		if (pos != null) return new Vector2f(pos.x, pos.y);
		return new Vector2f(-100, -100);
	}

	public Vector2f getMouseCoords() {
		return new Vector2f(Mouse.getX(), Mouse.getY());
	}
}
