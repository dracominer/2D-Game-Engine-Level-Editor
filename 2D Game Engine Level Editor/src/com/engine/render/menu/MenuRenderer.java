package com.engine.render.menu;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.engine.render.DisplayManager;
import com.engine.render.postProcessing.ImageRenderer;
import com.engine.render.shaders.MenuShader;
import com.engine.render.util.RenderHelper;
import com.engine.toolbox.CleanupSquad;
import com.engine.toolbox.Cleanupable;

public class MenuRenderer implements Cleanupable {

	private ImageRenderer renderer;
	private MenuShader shader;

	public MenuRenderer() {
		renderer = new ImageRenderer();
		shader = new MenuShader();
		CleanupSquad.add(this);
	}

	public MenuRenderer(int width, int height) {
		if (width > 0 && height > 0) {
			renderer = new ImageRenderer(width, height);
		} else {
			renderer = new ImageRenderer();
		}
		shader = new MenuShader();
		CleanupSquad.add(this);
	}

	public MenuRenderer(boolean useFBO) {
		this(useFBO ? DisplayManager.getWidthi() : -1, useFBO ? DisplayManager.getHeighti() : -1);
	}

	public void render(float time, int texture) {
		start();
		shader.start();
		shader.loadTime(time);
		RenderHelper.bindQuad();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		RenderHelper.unbindQuad();
		shader.stop();
		end();
	}

	private void start() {
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	private void end() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	@Override
	public void cleanup() {
		shader.cleanup();
		renderer.cleanUp();
	}

	public int getResultingTexture() {
		return renderer.getOutputTexture();
	}

}
