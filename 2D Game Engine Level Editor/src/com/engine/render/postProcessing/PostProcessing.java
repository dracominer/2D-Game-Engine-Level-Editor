package com.engine.render.postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.engine.render.Loader;
import com.engine.render.RawModel;
import com.engine.toolbox.ClickManager;

public class PostProcessing {

	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private static RawModel quad;
	private static ContrastEffect colChange;

	public static void init(Loader loader) {
		quad = loader.loadToVAO(POSITIONS, 2);
		colChange = new ContrastEffect();
	}

	public static void doPostProcessing(int colourTexture) {
		ClickManager.update();
		start();
		colChange.render(colourTexture);
		end();
	}

	private static void start() {
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	private static void end() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

}
