package com.engine.render.shaders;

public class PosterizationShader extends ShaderProgram {

	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "baseVertex.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "posterizeFragment.txt";

	private int location_Gamma;
	private int location_NumberOfColors;

	public PosterizationShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_Gamma = getUniformVariableLocation("gamma");
		location_NumberOfColors = getUniformVariableLocation("numberOfColors");
	}

	public void loadGamma(float gamma) {
		super.loadFloat(location_Gamma, gamma);
	}

	public void loadNumberOfColors(float number) {
		super.loadFloat(location_NumberOfColors, number);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
