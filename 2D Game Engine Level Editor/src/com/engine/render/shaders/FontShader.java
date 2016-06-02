package com.engine.render.shaders;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends ShaderProgram {

	private static final String VERTEX_FILE = ShaderProgram.PREFIX_SOURCE + "fontVertex.txt";
	private static final String FRAGMENT_FILE = ShaderProgram.PREFIX_SOURCE + "fontFragment.txt";

	private static int location_Translation;
	private static int location_Color;
	private static int location_Width;
	private static int location_Edge;
	private static int location_BorderWidth;
	private static int location_BorderEdge;
	private static int location_BorderColor;
	private static int location_BorderOffset;

	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_Translation = super.getUniformVariableLocation("translation");
		location_Color = super.getUniformVariableLocation("color");
		location_Width = super.getUniformVariableLocation("width");
		location_Edge = super.getUniformVariableLocation("edge");
		location_BorderWidth = super.getUniformVariableLocation("borderWidth");
		location_BorderEdge = super.getUniformVariableLocation("borderEdge");
		location_BorderColor = super.getUniformVariableLocation("borderColor");
		location_BorderOffset = super.getUniformVariableLocation("borderOffset");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	public void loadDefaultCharData() {
		loadCharData(0.5f, 0.1f);
	}

	public void loadDefaultCharBorderData() {
		loadCharBorderData(0.7f, 0.1f, new Vector3f(0, 0, 0), new Vector2f(0, 0));
	}

	public void loadCharData(float width, float edge) {
		super.loadFloat(location_Width, width);
		super.loadFloat(location_Edge, edge);
	}

	public void loadCharBorderData(float width, float edge, Vector3f color, Vector2f offset) {
		super.loadFloat(location_BorderWidth, width);
		super.loadFloat(location_BorderEdge, edge);
		super.load3dVector(location_BorderColor, color);
		super.load2dVector(location_BorderOffset, offset);
	}

	public void loadColor(Vector3f color) {
		super.load3dVector(location_Color, color);
	}

	public void loadTranslation(Vector2f translation) {
		super.load2dVector(location_Translation, translation);
	}

}
