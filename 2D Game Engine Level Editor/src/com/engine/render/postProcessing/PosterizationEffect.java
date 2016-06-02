package com.engine.render.postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.engine.render.DisplayManager;
import com.engine.render.shaders.PosterizationShader;
import com.engine.toolbox.CleanupSquad;
import com.engine.toolbox.Cleanupable;

public class PosterizationEffect implements Cleanupable {
	private ImageRenderer renderer;
	private PosterizationShader shader;

	private static float GAMMA = 1f;
	private static float NUMBER_OF_COLORS = 5f;

	public PosterizationEffect() {
		renderer = new ImageRenderer();
		shader = new PosterizationShader();
		shader.start();
		shader.loadGamma(GAMMA);
		shader.loadNumberOfColors(NUMBER_OF_COLORS);
		shader.stop();
		CleanupSquad.add(this);
	}

	public PosterizationEffect(int width, int height) {
		if (width > 0 && height > 0) {
			renderer = new ImageRenderer(width, height);
		} else {
			renderer = new ImageRenderer();
		}
		shader = new PosterizationShader();
		shader.start();
		shader.loadGamma(GAMMA);
		shader.loadNumberOfColors(NUMBER_OF_COLORS);
		shader.stop();
		CleanupSquad.add(this);
	}

	public PosterizationEffect(boolean useFBO) {
		this(useFBO ? DisplayManager.getWidthi() : -1, useFBO ? DisplayManager.getHeighti() : -1);
	}

	public void render(int texture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
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