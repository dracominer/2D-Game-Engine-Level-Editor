package com.engine.render.shaders;

import org.lwjgl.util.vector.Vector2f;

import com.engine.render.DisplayManager;

public class SobelShader extends ShaderProgram {

	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "kernalVertex.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "sobelFragment.txt";

	private int location_PixelSize;

	public SobelShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_PixelSize = getUniformVariableLocation("pixelSize");
	}

	public void loadPixelSize(Vector2f size) {
		super.load2dVector(location_PixelSize, size);
	}

	public void loadPixelSize() {
		this.loadPixelSize(new Vector2f(1f / DisplayManager.getWidth(), 1f / DisplayManager.getHeight()));
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
