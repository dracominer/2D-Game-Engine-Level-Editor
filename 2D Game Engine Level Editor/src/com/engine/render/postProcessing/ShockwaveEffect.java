package com.engine.render.postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.engine.render.DisplayManager;
import com.engine.render.shaders.ShockwaveShader;
import com.engine.toolbox.CleanupSquad;
import com.engine.toolbox.Cleanupable;

public class ShockwaveEffect implements Cleanupable {
	private ImageRenderer renderer;
	private ShockwaveShader shader;

	private static final Vector3f parameters = new Vector3f(10.0f, 0.5f, 0.1f);
	private float time = 0;

	public ShockwaveEffect() {
		renderer = new ImageRenderer();
		shader = new ShockwaveShader();
		shader.start();
		shader.loadParameters(parameters);
		shader.stop();
		CleanupSquad.add(this);
	}

	public ShockwaveEffect(int width, int height) {
		renderer = new ImageRenderer(width, height);
		shader = new ShockwaveShader();
		shader.start();
		shader.loadParameters(parameters);
		shader.stop();
		CleanupSquad.add(this);
	}

	public void render(int texture) {
		time += DisplayManager.getFrameTimeSeconds();
		shader.start();
		shader.loadTime(time);
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

	public void loadShockwave(Vector2f center, float time) {
		shader.start();
		shader.loadCenter(center);
		shader.stop();
		this.time = time;
		System.out.println("loading a shockwave at : " + center.toString() + ", with time : " + time);
	}

	public int getResultingTexture() {
		return renderer.getOutputTexture();
	}
}
