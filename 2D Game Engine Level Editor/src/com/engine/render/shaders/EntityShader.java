package com.engine.render.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import com.engine.camera.Camera;

public class EntityShader extends ShaderProgram {

	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "entityVertexShader.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "entityFragmentShader.txt";

	private int location_transformationMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_TextureOffsets;
	private int location_NumberOfRows;
	private int location_Percents;
	private int location_CelLevels;

	public EntityShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformVariableLocation("transformationMatrix");
		location_viewMatrix = super.getUniformVariableLocation("viewMatrix");
		location_projectionMatrix = super.getUniformVariableLocation("projectionMatrix");
		location_NumberOfRows = getUniformVariableLocation("numberOfRows");
		location_TextureOffsets = getUniformVariableLocation("offset");
		location_Percents = getUniformVariableLocation("percent");
		location_CelLevels = getUniformVariableLocation("celLevels");
	}

	public void loadCelLevels(float levels) {
		super.loadFloat(location_CelLevels, levels);
	}

	public void loadPercents(Vector2f percents) {
		super.load2dVector(location_Percents, percents);
	}

	public void loadNumberOfRows(float numberOfRows) {
		super.loadFloat(location_NumberOfRows, numberOfRows);
	}

	public void loadTextureOffsets(Vector2f textureOffsets) {
		super.load2dVector(location_TextureOffsets, textureOffsets);
	}

	public void loadOffsetData(int numberOfRows, Vector2f textureOffsets) {
		super.loadInt(location_NumberOfRows, numberOfRows);
		super.load2dVector(location_TextureOffsets, textureOffsets);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

	public void loadViewMatrix(Matrix4f view) {
		super.loadMatrix(location_viewMatrix, view);
	}

	public void loadViewMatrix(Camera cam) {
		super.loadMatrix(location_viewMatrix, cam.getViewMatrix());
	}

	public void loadAllModelViewMatrix(Matrix4f model, Camera cam) {
		loadTransformation(model);
		loadViewMatrix(cam);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
