package com.engine.render.postProcessing.gaussianBlur;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.engine.render.postProcessing.ImageRenderer;
import com.engine.render.shaders.VerticalBlurShader;
import com.engine.toolbox.CleanupSquad;
import com.engine.toolbox.Cleanupable;

public class VerticalBlur implements Cleanupable {

	private ImageRenderer renderer;
	private VerticalBlurShader shader;

	public VerticalBlur(int targetFboWidth, int targetFboHeight) {
		shader = new VerticalBlurShader();
		renderer = new ImageRenderer(targetFboWidth, targetFboHeight);
		shader.start();
		shader.loadTargetHeight(targetFboHeight);
		shader.stop();
		CleanupSquad.add(this);

	}

	public void render(int texture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
	}

	public int getOutputTexture() {
		return renderer.getOutputTexture();
	}

	@Override
	public void cleanup() {
		renderer.cleanUp();
		shader.cleanup();
	}
}
