package com.engine.render.shaders;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ShockwaveShader extends ShaderProgram {

	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "baseVertex.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "shockwaveFragment.txt";

	private int location_Time;
	private int location_Parameters;
	private int location_Center;

	public ShockwaveShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_Time = getUniformVariableLocation("time");
		location_Parameters = getUniformVariableLocation("shockParams");
		location_Center = getUniformVariableLocation("center");
	}

	public void loadTime(float time) {
		super.loadFloat(location_Time, time);
	}

	public void loadParameters(Vector3f params) {
		super.load3dVector(location_Parameters, params);
	}

	public void loadCenter(Vector2f center) {
		super.load2dVector(location_Center, center);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
