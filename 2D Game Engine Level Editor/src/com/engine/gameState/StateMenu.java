package com.engine.gameState;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.engine.gameState.GameStateManager.STATE;
import com.engine.gui.GuiStateChangeButton;
import com.engine.gui.GuiUpdatable;
import com.engine.render.DisplayManager;
import com.engine.render.GuiRenderer;
import com.engine.render.menu.MenuRenderer;
import com.engine.render.textures.TextureManager;

public class StateMenu extends GameState {

	private MenuRenderer renderer;
	private GuiRenderer rendererGuis;
	private float time = 0;
	private float rate = 0.2f;

	private List<GuiUpdatable> guis = new ArrayList<GuiUpdatable>();

	public StateMenu(String name) {
		super(name);
		renderer = new MenuRenderer(false);
		rendererGuis = new GuiRenderer();
		TextureManager.registerTexture("button", "/gui/button");
		GuiStateChangeButton button = new GuiStateChangeButton(TextureManager.get("button"), new Vector2f(0f, -0.25f), new Vector2f(0.75f, 0.125f));
		button.setNewState(STATE.PLAY.toString());
		guis.add(button);
	}

	@Override
	public void update() {
		for (GuiUpdatable g : guis) {
			g.update();
		}
		time += DisplayManager.getFrameTimeSeconds() * rate;
	}

	@Override
	public void render() {
		renderer.render(time, 1);
		rendererGuis.renderUpdatable(guis);
	}

	public void onOpened() {
		playBGSound();
	}

	private void playBGSound() {}

	public void onClosed() {}

}
