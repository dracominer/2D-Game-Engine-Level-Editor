package com.engine.render.shaders;

public class MenuShader extends ShaderProgram {

	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "baseVertex.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "menuFrag.txt";

	private int location_Time;

	public MenuShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_Time = getUniformVariableLocation("time");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	public void loadTime(float time) {
		loadFloat(location_Time, time);
	}

}
