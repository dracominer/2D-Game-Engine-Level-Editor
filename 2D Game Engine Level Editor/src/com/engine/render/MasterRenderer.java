package com.engine.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.engine.camera.Camera;
import com.engine.entites.Entity;
import com.engine.font.TextMaster;
import com.engine.gui.GUI;
import com.engine.level.tiles.Tile;
import com.engine.render.textures.ModelTexture;
import com.engine.toolbox.Cleanupable;

public class MasterRenderer implements Cleanupable {
	public static final float FOV = 70;// normal is 70
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 1000;

	private EntityRenderer entityRenderer;
	private GuiRenderer guiRenderer;

	private Map<ModelTexture, List<Entity>> entities = new HashMap<ModelTexture, List<Entity>>();
	private Map<ModelTexture, List<Tile>> tiles = new HashMap<ModelTexture, List<Tile>>();
	private Map<Integer, List<GUI>> guis = new HashMap<Integer, List<GUI>>();
	private Vector3f skyColor = new Vector3f(0, 0, 0);
	private static Matrix4f projectionMatrix;

	public MasterRenderer() {
		createProjectionMatrix();
		entityRenderer = new EntityRenderer(projectionMatrix);
		guiRenderer = new GuiRenderer();
		TextMaster.init(Loader.get());
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(skyColor.x, skyColor.y, skyColor.z, 1);
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void initProjectionMatrix() {
		createProjectionMatrix();
	}

	private static void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void render(Camera cam) {
		prepare();
		entityRenderer.render(cam, entities, tiles);
		guiRenderer.render(guis);
		TextMaster.render();
	}

	public void processEntities(List<Entity> entities) {
		for (Entity e : entities) {
			processEntity(e);
		}
	}

	public void processEntity(Entity entity) {
		ModelTexture tex = entity.getTexture();
		List<Entity> es = entities.get(tex);
		if (es == null) {
			es = new ArrayList<Entity>();
			es.add(entity);
		} else {
			es.add(entity);
		}
		entities.put(tex, es);
	}

	public void processTile(Tile t) {
		ModelTexture tex = t.getModelTexture();
		List<Tile> es = tiles.get(tex);
		if (es == null) {
			es = new ArrayList<Tile>();
			es.add(t);
		} else {
			es.add(t);
		}
		tiles.put(tex, es);
	}

	public void processGui(GUI gui) {
		int tex = gui.getTextureID();
		List<GUI> gs = guis.get(tex);
		if (gs == null) {
			gs = new ArrayList<GUI>();
			gs.add(gui);
		} else {
			gs.add(gui);
		}
		guis.put(tex, gs);
	}

	public void clearEntitiesList() {
		entities.clear();
	}

	public void clearTilesList() {
		tiles.clear();
	}

	public void clearGuiList() {
		guis.clear();
	}

	public void clearAll() {
		entities.clear();
		guis.clear();
		tiles.clear();
	}

	@Override
	public void cleanup() {
		clearAll();
		entityRenderer.cleanUp();
		guiRenderer.cleanUp();
		TextMaster.cleanup();
	}

}
