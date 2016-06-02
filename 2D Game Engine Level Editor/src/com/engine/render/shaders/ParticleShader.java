package com.engine.render.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "particleVShader.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "particleFShader.txt";

	private int location_projectionMatrix;
	private int location_NumberOfRows;
	private int location_FogDensity;
	private int location_FogGradient;
	private int location_SkyColor;

	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformVariableLocation("projectionMatrix");
		location_NumberOfRows = super.getUniformVariableLocation("numberOfRows");
		location_FogDensity = super.getUniformVariableLocation("density");
		location_FogGradient = super.getUniformVariableLocation("gradient");
		location_SkyColor = super.getUniformVariableLocation("skyColor");
	}

	public void loadSkyColor(Vector3f color) {
		super.load3dVector(location_SkyColor, color);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_NumberOfRows, numberOfRows);
	}

	public void loadFogData(float fogDensity, float fogGradient) {
		super.loadFloat(location_FogDensity, fogDensity);
		super.loadFloat(location_FogGradient, fogGradient);

	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "texOffsets");
		super.bindAttribute(6, "blendFactor");

	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

}
