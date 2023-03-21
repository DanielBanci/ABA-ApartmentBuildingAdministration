package auxiliary;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
AccountData is a class that extends JPanel and is used to create and display a field
for entering account data, such as username, password, and security question.
@param title a String representing the title of the account data field
@param flag a boolean representing whether the field is a combobox or not
The class has two constructors:
AccountData(String title, boolean flag) 
AccountData(String title)
The first constructor takes in a title string and a boolean flag. The flag indicates
whether the account data field is a combobox or not. If the title contains the word "parola"
or "Parola", a JPasswordField is created. Otherwise, a JTextField is created.
The second constructor takes in a title string and creates a JTextField if the title does
not contain the word "parola" or "Parola", and a JPasswordField if it does.
The class also has private fields:
title a JLabel representing the title of the account data field
input a JTextField for entering data (if the field is not a combobox)
password a JPasswordField for entering password (if the field is a password field)
comboBox a JComboBox for selecting data (if the field is a combobox)
*/
public class AccountData extends JPanel{
	private JLabel title;
	private JTextField input;
	private JPasswordField password;
	private JComboBox comboBox;
	
	public AccountData(String title,boolean flag) {
		super();
		setLayout(new GridLayout(1,2));
		this.setOpaque(false);
		this.title = new JLabel(title);
		this.title.setHorizontalAlignment(JLabel.CENTER);
		this.title.setFont(new Font("Times New Roman",Font.PLAIN,16));
		
		add(this.title);
		if(!flag) {
			if(title.contains("parola") || title.contains("Parola")) {
				password = new JPasswordField();
				add(password);
			}else {
				input = new JTextField();
				add(input);
			}
		}else {
			comboBox = new JComboBox();
			add(comboBox);
		}
		
	}
	public AccountData(String title) {
		super();
		setLayout(new GridLayout(1,2));
		this.title = new JLabel(title);
		this.title.setHorizontalAlignment(JLabel.CENTER);
		this.title.setFont(new Font("Times New Roman",Font.PLAIN,16));
		
		add(this.title);
		
		if(title.contains("parola") || title.contains("Parola")) {
			password = new JPasswordField();
			add(password);
		}else {
			input = new JTextField();
			add(input);
		}
	}
	
	//setters & getters
	public JLabel getTitle() {
		return title;
	}
	public void setTitle(JLabel title) {
		this.title = title;
	}
	public JTextField getInput() {
		return input;
	}
	public void setInput(JTextField input) {
		this.input = input;
	}
	public JPasswordField getPasswordP() {
		return password;
	}
	public void setPasswordContent(String password) {
		
		this.password.setText(password);
	}
	public JComboBox getComboBox() {
		return comboBox;
	}
	public void setComboBox(JComboBox comboBox) {
		this.comboBox = comboBox;
	}
}
