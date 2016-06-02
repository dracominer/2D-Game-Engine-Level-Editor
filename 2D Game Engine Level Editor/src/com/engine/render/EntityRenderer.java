package com.engine.render;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import com.engine.camera.Camera;
import com.engine.entites.Entity;
import com.engine.level.tiles.Tile;
import com.engine.render.shaders.EntityShader;
import com.engine.render.textures.ModelTexture;
import com.engine.render.util.RenderHelper;
import com.engine.toolbox.Maths;

public class EntityRenderer {

	private static final float CEL_LEVELS = 10f;

	private EntityShader shader;

	public EntityRenderer(Matrix4f projectionMatrix) {
		this.shader = new EntityShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.loadCelLevels(CEL_LEVELS);
		shader.stop();
	}

	public void render(Camera cam, Map<ModelTexture, List<Entity>> entities, Map<ModelTexture, List<Tile>> tiles) {
		shader.start();
		shader.loadViewMatrix(cam);
		RenderHelper.bindQuad();
		RenderHelper.prepForRender();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		renderEntities(entities);
		renderTiles(tiles);
		RenderHelper.cleanUpRender();
		shader.stop();
	}

	private void renderEntities(Map<ModelTexture, List<Entity>> entities) {
		Set<ModelTexture> textures = entities.keySet();
		for (ModelTexture tex : textures) {
			List<Entity> es = entities.get(tex);
			//			System.out.println("Texture: " + tex.getID());
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getID());
			shader.loadNumberOfRows(tex.getNumberOfRows());
			for (Entity e : es) {
				//				System.out.println("rendering entity " + e.toString());
				shader.loadTextureOffsets(e.getTextureOffsets());
				shader.loadPercents(e.getPercent());
				shader.loadTransformation(Maths.createTransformationMatrix(e.getPosition(), e.getRotationForRender(), e.getScale()));
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, RenderHelper.getVertexCount());
			}
		}
	}

	private void renderTiles(Map<ModelTexture, List<Tile>> tiles) {
		Set<ModelTexture> textures = tiles.keySet();
		shader.loadPercents(new Vector2f(1.1f, 1.1f));
		for (ModelTexture tex : textures) {
			List<Tile> ts = tiles.get(tex);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getID());
			shader.loadNumberOfRows(tex.getNumberOfRows());
			for (Tile t : ts) {
				shader.loadTextureOffsets(t.getTextureOffsets());
				shader.loadTransformation(Maths.createTransformationMatrix(t));
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, RenderHelper.getVertexCount());
			}
		}
	}

	public void cleanUp() {
		shader.cleanup();
	}

}
