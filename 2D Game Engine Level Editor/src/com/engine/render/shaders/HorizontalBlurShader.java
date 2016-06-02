package com.engine.render.shaders;

public class HorizontalBlurShader extends ShaderProgram {

	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "horizontalBlurVertex.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "blurFragment.txt";

	private int location_targetWidth;

	public HorizontalBlurShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	public void loadTargetWidth(float width) {
		super.loadFloat(location_targetWidth, width);
	}

	@Override
	protected void getAllUniformLocations() {
		location_targetWidth = super.getUniformVariableLocation("targetWidth");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
