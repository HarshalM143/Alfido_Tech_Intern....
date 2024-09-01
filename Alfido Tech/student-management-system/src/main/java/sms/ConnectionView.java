package sms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ConnectionView {

	static JFrame connectionFrame;

	private JTextField loginField;
	private JPasswordField passwordField;
	private JTextField databaseUrlField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				Translator.setLanguage(Language.ENG);
				Translator.getMessagesFromXML();

				try {
					ConnectionView window = new ConnectionView();
					window.connectionFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ConnectionView() {
		initialize();

		connectionFrame.setVisible(true);
	}

	private void initialize() {
		connectionFrame = new JFrame();
		connectionFrame.setBounds(100, 100, 640, 480);
		connectionFrame.setResizable(false);
		connectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connectionFrame.setTitle(Translator.getValue("sms"));

		JPanel topPanel = new JPanel();
		topPanel.setBackground(SystemColor.textHighlight);
		connectionFrame.getContentPane().add(topPanel, BorderLayout.NORTH);

		JLabel connectText = new JLabel(Translator.getValue("connectText"));
		connectText.setForeground(new Color(255, 255, 255));
		connectText.setFont(new Font("Tahoma", Font.PLAIN, 25));
		topPanel.add(connectText);

		JPanel bottomPanel = new JPanel();
		connectionFrame.getContentPane().add(bottomPanel, BorderLayout.CENTER);

		JLabel loginText = new JLabel(Translator.getValue("loginText"));
		loginText.setBounds(68, 134, 162, 25);
		loginText.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel passwordText = new JLabel(Translator.getValue("passwordText"));
		passwordText.setBounds(68, 174, 162, 25);
		passwordText.setFont(new Font("Tahoma", Font.PLAIN, 12));

		loginField = new JTextField();
		loginField.setName("loginField");
		loginField.setBounds(240, 139, 330, 20);
		loginField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setName("passwordField");
		passwordField.setBounds(240, 179, 330, 20);

		databaseUrlField = new JTextField();
		databaseUrlField.setName("databaseUrlField");
		databaseUrlField.setText("jdbc:mysql://localhost:3306/studentsdb");
		databaseUrlField.setColumns(10);
		databaseUrlField.setBounds(240, 96, 330, 20);

		JLabel databaseUrlText = new JLabel(Translator.getValue("databaseUrlText"));
		databaseUrlText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		databaseUrlText.setBounds(68, 91, 162, 25);

		JButton changeLanguageButton = new JButton(Translator.getValue("changeLanguage"));

		changeLanguageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Language selectedLanguage = (Language) JOptionPane.showInputDialog(null, Translator.getValue("sms"),
						Translator.getValue("selectLanguage"), JOptionPane.QUESTION_MESSAGE, null, Language.values(),
						Language.ENG.toString());

				if (selectedLanguage != null)
					Translator.setLanguage(selectedLanguage);
				else
					return;

				Translator.getMessagesFromXML();

				connectionFrame.dispose();
				new ConnectionView();
			}
		});

		changeLanguageButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		changeLanguageButton.setBounds(480, 365, 135, 25);
		bottomPanel.add(changeLanguageButton);

		JButton connectButton = new JButton(Translator.getValue("connectButton"));
		connectButton.setName("connectButton");
		connectButton.setBounds(221, 290, 190, 42);
		connectButton.setFont(new Font("Tahoma", Font.PLAIN, 20));

		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If one of the fields are empty then warn user about it
				if (loginField.getText().equals("") || databaseUrlField.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), Translator.getValue("fillEmptyFields"),
							Translator.getValue("error"), JOptionPane.ERROR_MESSAGE);
				} else {

					DBHandler.setLogin(loginField.getText());
					DBHandler.setPassword(passwordField.getText());
					DBHandler.setDatabaseUrl(databaseUrlField.getText());

					if (DBHandler.createTables()) {
						JOptionPane.showMessageDialog(new JFrame(), Translator.getValue("connectionEstablished"),
								Translator.getValue("success"), JOptionPane.INFORMATION_MESSAGE);

						ManagementView.main(null);
						connectionFrame.dispose();

					} else {
						JOptionPane.showMessageDialog(new JFrame(), Translator.getValue("connectionNotEstablished"),
								Translator.getValue("error"), JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		bottomPanel.setLayout(null);
		bottomPanel.add(passwordText);
		bottomPanel.add(loginText);
		bottomPanel.add(passwordField);
		bottomPanel.add(loginField);
		bottomPanel.add(connectButton);
		bottomPanel.add(databaseUrlText);
		bottomPanel.add(databaseUrlField);

	}
}
