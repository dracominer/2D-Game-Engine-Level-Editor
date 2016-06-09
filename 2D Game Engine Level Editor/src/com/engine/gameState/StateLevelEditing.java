package com.engine.gameState;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.engine.gui.GuiButton;
import com.engine.gui.GuiUpdatable;
import com.engine.render.GuiRenderer;
import com.engine.render.textures.TextureManager;

public class StateLevelEditing extends GameState {

	private List<GuiUpdatable> guis = new ArrayList<GuiUpdatable>();
	GuiRenderer guiRender;

	public StateLevelEditing(String name) {
		super(name);
		guiRender = new GuiRenderer();
		GuiButton saveFile = new GuiButton(TextureManager.get("button"), new Vector2f(-0.875f, 0.875f), new Vector2f(0.125f, 0.125f)) {
			@Override
			public void onClicked(int button, Vector2f position) {
				GameStateManager.save();
			}
		};
		guis.add(saveFile);
	}

	@Override
	public void update() {
		GameStateManager.currentLevel.update();
		for (GuiUpdatable g : guis) {
			g.update();
		}
	}

	@Override
	public void render() {
		GameStateManager.currentLevel.render();
		guiRender.renderUpdatable(guis);
	}

	public void onClosed() {}

}
