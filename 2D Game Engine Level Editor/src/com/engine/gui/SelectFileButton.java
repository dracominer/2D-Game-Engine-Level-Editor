package com.engine.gui;

import java.io.File;

import javax.swing.JFileChooser;

import org.lwjgl.util.vector.Vector2f;

import com.engine.gameState.GameStateManager;

public class SelectFileButton extends GuiInteractable {

	public SelectFileButton(int textureID, Vector2f position, Vector2f scale) {
		super(textureID, position, scale);
	}

	@Override
	protected void onMouseTouching(int mouseButton, Vector2f position) {
		if (mouseButton != -1) {
			System.out.println("Starting a file selector");
			selectFile();
			System.out.println("Finished selection");
		}
	}

	private void selectFile() {
		String filelocation = "";
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showDialog(null, "use this file");
		File f = fileChooser.getSelectedFile();
		if (f != null) {
			filelocation = f.getAbsolutePath();
			GameStateManager.setFileLocation(filelocation);
			GameStateManager.loadDataFromFile();
		}
	}

	@Override
	protected void onMouseEnter(int mouseButton, Vector2f position) {}

	@Override
	protected void onMouseNotTouching(int mouseButton, Vector2f position) {}

	@Override
	protected void onMouseExit(int mouseButton, Vector2f position) {}

}
