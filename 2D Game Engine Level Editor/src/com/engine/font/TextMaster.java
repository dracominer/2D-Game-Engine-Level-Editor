package com.engine.font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.engine.render.Loader;

public class TextMaster {
	private static Loader loader;
	private static HashMap<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private static FontRenderer renderer;

	public static void init(Loader theloader) {
		renderer = new FontRenderer();
		loader = theloader;
	}

	public static void render() {
		renderer.render(texts);
	}

	public static void loadText(GUIText text) {
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vaoID = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vaoID, data.getVertexCount());
		List<GUIText> textBatch = texts.get(font);
		if (textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}

	public static void removeText(GUIText text) {
		List<GUIText> textBatch = texts.get(text.getFont());
		text.getMesh();
		textBatch.remove(text);
		if (textBatch.isEmpty()) {
			texts.remove(text.getFont());
		}
	}

	public static void cleanup() {
		if (renderer != null) renderer.cleanup();
	}

}
