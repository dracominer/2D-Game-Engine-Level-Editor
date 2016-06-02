package com.engine.font;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class FontStyle {
	//		DROP_SHADOW(0.5f, 0.1f, 0.7f, 0.1f, new Vector3f(0, 0, 0), new Vector2f(0.006f, 0.006f)), //
	//		GLOW(0.4f, 0.1f, 0.7f, 0.1f, new Vector3f(0, 0, 0), new Vector2f(0f, 0f)), //
	//		STANDARD(0.5f, 0.1f, 0.5f, 0.1f, new Vector3f(0, 0, 0), new Vector2f(0f, 0f));

	public static final FontStyle DROP_SHADOW = new FontStyle(0.5f, 0.1f, 0.7f, 0.1f, new Vector3f(0, 0, 0), new Vector2f(0.006f, 0.006f));
	public static final FontStyle GLOW = new FontStyle(0.4f, 0.1f, 0.7f, 0.1f, new Vector3f(0, 0, 0), new Vector2f(0f, 0f));
	public static final FontStyle STANDARD = new FontStyle(0.5f, 0.1f, 0.0f, 0.1f, new Vector3f(0, 0, 0), new Vector2f(0f, 0f));

	float width;
	float edge;
	float borderEdge;
	float borderWidth;
	Vector3f borderColor;
	Vector2f borderOffset;

	private FontStyle(float width, float edge, float borderEdge, float borderWidth, Vector3f borderColor, Vector2f borderOffset) {
		this.width = width;
		this.edge = edge;
		this.borderEdge = borderEdge;
		this.borderWidth = borderWidth;
		this.borderColor = borderColor;
		this.borderOffset = borderOffset;
	}

	public FontStyle copy() {
		return new FontStyle(width, edge, borderEdge, borderWidth, borderColor, borderOffset);
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setEdge(float edge) {
		this.edge = edge;
	}

	public void setBorderEdge(float borderEdge) {
		this.borderEdge = borderEdge;
	}

	public void setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
	}

	public void setBorderColor(Vector3f borderColor) {
		this.borderColor = borderColor;
	}

	public void setBorderOffset(Vector2f borderOffset) {
		this.borderOffset = borderOffset;
	}

	public float getWidth() {
		return width;
	}

	public float getEdge() {
		return edge;
	}

	public float getBorderEdge() {
		return borderEdge;
	}

	public float getBorderWidth() {
		return borderWidth;
	}

	public Vector3f getBorderColor() {
		return borderColor;
	}

	public Vector2f getBorderOffset() {
		return borderOffset;
	}
}
