package com.engine.render;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import com.engine.gui.GUI;
import com.engine.gui.GuiUpdatable;
import com.engine.render.shaders.GuiShader;
import com.engine.render.util.RenderHelper;
import com.engine.toolbox.Maths;

public class GuiRenderer {

	private GuiShader shader;

	public GuiRenderer() {
		shader = new GuiShader();
	}

	public void render(Map<Integer, List<GUI>> guis) {
		shader.start();
		Set<Integer> textures = guis.keySet();
		RenderHelper.bindQuad();
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		for (int tex : textures) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
			for (GUI gui : guis.get(tex)) {
				shader.loadPercent(gui.getPercent());
				renderGui(gui);
			}
		}
		GL20.glDisableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		shader.stop();
	}

	private void renderGui(GUI gui) {
		shader.loadTransformation(Maths.createTransformationMatrix(gui.getPosition(), gui.getScale()));
		RenderHelper.drawArrays();
	}

	public void cleanUp() {
		shader.cleanup();
	}

	public void renderUpdatable(List<GuiUpdatable> guis) {
		shader.start();
		RenderHelper.bindQuad();
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		for (GuiUpdatable gui : guis) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTextureID());
			shader.loadPercent(gui.getPercent());
			renderGui(gui);
		}
		GL20.glDisableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		shader.stop();
	}

}
