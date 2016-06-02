package com.engine.gui.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CustomLevelDialogue extends JDialog {
	private static final long serialVersionUID = 4311823026115642951L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CustomLevelDialogue dialog = new CustomLevelDialogue();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

		textField = new JTextField();
		textField.setBounds(10, 41, 86, 20);
		contentPanel.add(textField);
		textField.setColumns(10);

		JLabel lblLevelWidth = new JLabel("Level Width");
		lblLevelWidth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevelWidth.setBounds(10, 11, 86, 19);
		contentPanel.add(lblLevelWidth);

		JLabel lblLevelHeight = new JLabel("Level Height");
		lblLevelHeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLevelHeight.setBounds(106, 11, 86, 19);
		contentPanel.add(lblLevelHeight);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(106, 41, 86, 20);
		contentPanel.add(textField_1);

		String[] options = { "Fill With Grass", "Generate Caves", "Leave Empty", "Random Noise" };

		JComboBox comboBox = new JComboBox(options);
		comboBox.setSelectedIndex(3);
		comboBox.setBounds(10, 113, 165, 20);
		contentPanel.add(comboBox);

		JLabel lblInitializationProcess = new JLabel("Initialization Process");
		lblInitializationProcess.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInitializationProcess.setBounds(10, 72, 165, 30);
		contentPanel.add(lblInitializationProcess);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
