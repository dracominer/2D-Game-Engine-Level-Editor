package com.engine.render.shaders;

public class ContrastShader extends ShaderProgram {

	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "contrastVertex.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "contrastFragment.txt";

	private int contrast;

	public ContrastShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		contrast = getUniformVariableLocation("contrast");
	}

	public void loadContrast(float contrast) {
		super.loadFloat(this.contrast, contrast);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
