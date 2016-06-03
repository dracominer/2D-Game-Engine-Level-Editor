package com.engine.gui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.engine.gameState.GameStateManager;
import com.engine.level.tiles.Tile;
import com.engine.level.tiles.TileManager;

public class CustomLevelDialogue extends JDialog implements ActionListener {
	private static final long serialVersionUID = 4311823026115642951L;
	private final JPanel contentPanel = new JPanel();
	private JTextField widthInput;
	private JTextField heightInput;

	@SuppressWarnings("rawtypes")
	private JComboBox initProcess;
	String[] options = { "Fill With Grass", "Generate Caves", "Leave Empty", "Random Noise" };

	private static final String BUTTON_OK = "ok_button";
	private static final String BUTTON_CANCEL = "cancel_button";

	private boolean isDone = false;
	private boolean cancelled = false;

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomLevelDialogue() {
		setTitle("New Level");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 229, 208);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		widthInput = new JTextField();
		widthInput.setBounds(10, 41, 86, 20);
		contentPanel.add(widthInput);
		widthInput.setColumns(10);

		JLabel lblLevelWidth = new JLabel("Level Width");
		lblLevelWidth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevelWidth.setBounds(10, 11, 86, 19);
		contentPanel.add(lblLevelWidth);

		JLabel lblLevelHeight = new JLabel("Level Height");
		lblLevelHeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevelHeight.setBounds(106, 11, 86, 19);
		contentPanel.add(lblLevelHeight);

		heightInput = new JTextField();
		heightInput.setColumns(10);
		heightInput.setBounds(106, 41, 86, 20);
		contentPanel.add(heightInput);

		initProcess = new JComboBox(options);
		initProcess.setSelectedIndex(3);
		initProcess.setBounds(10, 113, 165, 20);
		contentPanel.add(initProcess);

		JLabel lblInitializationProcess = new JLabel("Initialization Process");
		lblInitializationProcess.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInitializationProcess.setBounds(10, 72, 165, 30);
		contentPanel.add(lblInitializationProcess);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand(BUTTON_OK);
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(BUTTON_CANCEL);
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String key = e.getActionCommand();
		if (key.equals(BUTTON_OK)) {
			isDone = true;
			exit();
		}
		if (key.equals(BUTTON_CANCEL)) {
			isDone = true;
			cancelled = true;
			exit();
		}
		System.out.println(isDone ? "Completed processes. Exiting the program" : "Action performed. Did not exit");
	}

	public void exit() {
		System.out.println("Exiting the program");
		this.setVisible(false);
		this.dispose();
		if (!cancelled) {
			int width = getLevelWidth();
			int height = getLevelHeight();
			TileManager manager = new TileManager(width, height);
			if (initProcess.getSelectedItem().equals(options[0])) {
				//fill with grass
				manager.fillWithTile(TileManager.grass);
			} else if (initProcess.getSelectedItem().equals(options[1])) {
				// gen caves
				manager.generateCellularAutonoma(0.25f, 20, new Random().nextLong());
			} else if (initProcess.getSelectedItem().equals(options[2])) {
				// leave empty
			} else if (initProcess.getSelectedItem().equals(options[3])) {
				// rand noise
				manager.randomNoise(new Tile[] { TileManager.grass, TileManager.stone });
			}
			System.out.println("LEVEL DATA: " + width + "x" + height + ". Init process: '" + initProcess.getSelectedItem() + "'");
			GameStateManager.currentLevel.setTileManager(manager);
			System.out.println("The actions were implemented to the level.");
		} else {
			System.out.println("The program was cancelled. No effect on the level");
		}
	}

	private int getLevelWidth() {
		try {
			return Integer.parseInt(widthInput.getText());
		} catch (Exception e) {
			System.err.println("failed to parse string for the level width. Substituting 10");
			return 10;
		}
	}

	private int getLevelHeight() {
		try {
			return Integer.parseInt(heightInput.getText());
		} catch (Exception e) {
			System.err.println("failed to parse string for the level height. Substituting 10");
			return 10;
		}
	}

	public boolean isDone() {
		return isDone;
	}
}
