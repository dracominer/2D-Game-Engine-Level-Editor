package com.engine.render.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public class GuiShader extends ShaderProgram {
	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "guiVertexShader.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "guiFragmentShader.txt";

	private int location_transformationMatrix;
	private int location_Percents;

	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformVariableLocation("transformationMatrix");
		location_Percents = getUniformVariableLocation("percent");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	public void loadPercent(Vector2f percent) {
		super.load2dVector(location_Percents, percent);
	}

}
