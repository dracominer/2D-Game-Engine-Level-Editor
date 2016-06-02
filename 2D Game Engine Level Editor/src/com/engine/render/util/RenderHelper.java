package com.engine.render.util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.engine.render.Loader;
import com.engine.render.RawModel;

public class RenderHelper {

	private static RawModel quad;

	static {
		quad = Loader.get().getSquare();
	}

	public static RawModel getQuad() {
		return quad;
	}

	public static int getVertexCount() {
		return quad.getVertexCount();
	}

	public static void bindQuad() {
		GL30.glBindVertexArray(quad.getVaoID());
	}

	public static void unbindQuad() {
		GL30.glBindVertexArray(0);
	}

	public static void prepForRender() {
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public static void cleanUpRender() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public static void draw(int textureID) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		drawArrays();
	}

	public static void drawArrays() {
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
	}

	public static void bindTexture(int texture, int unit) {
		GL13.glActiveTexture(unit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}

}
