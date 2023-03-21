package appInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.Database;
import mainClasses.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.SpringLayout;
import java.awt.Rectangle;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
The Login class provides a user interface for user to login to the application.
@author Ciprian Banci
@version 1.0
*/
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField emailtf;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void lunch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setBackground(new Color(240, 240, 240));
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//main content pane

		setContentPane(contentPane);
		contentPane.setLayout(null);
		final Login f = this;
		
		//left panel
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 390, 425);
		contentPane.add(panel_1);
		
		//content for left panel
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Bine ai venit!");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setLabelFor(this);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2.add(lblNewLabel_2);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(Color.LIGHT_GRAY);
		panel_2_1.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("E-mail:");
		lblNewLabel.setBounds(10, 10, 60, 19);
		panel_2_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		emailtf = new JTextField();
		emailtf.setBounds(10, 33, 265, 25);
		panel_2_1.add(emailtf);
		emailtf.setColumns(10);
		
		
		JLabel lblNewLabel_1 = new JLabel("Parola:");
		lblNewLabel_1.setBounds(10, 68, 69, 19);
		panel_2_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 101, 265, 25);
		panel_2_1.add(passwordField);
		
		JPanel panel_2_1_2 = new JPanel();
		panel_2_1_2.setBounds(new Rectangle(1, 1, 1, 1));
		panel_2_1_2.setBackground(new Color(0, 0, 0));
		panel_2_1_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		//button to login
		
		JButton btnLogin = new JButton("Conectare");
		btnLogin.setBackground(new Color(153, 204, 255));
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_2_1_2.add(btnLogin);
		SpringLayout sl_panel_1 = new SpringLayout();
		sl_panel_1.putConstraint(SpringLayout.NORTH, panel_2_1, 19, SpringLayout.SOUTH, panel_2);
		sl_panel_1.putConstraint(SpringLayout.WEST, panel_2_1, 0, SpringLayout.WEST, panel_2);
		sl_panel_1.putConstraint(SpringLayout.EAST, panel_2_1, 0, SpringLayout.EAST, panel_2);
		sl_panel_1.putConstraint(SpringLayout.WEST, panel_2_1_2, 0, SpringLayout.WEST, panel_2);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, panel_2_1_2, -48, SpringLayout.SOUTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, panel_2_1_2, 0, SpringLayout.EAST, panel_2);
		sl_panel_1.putConstraint(SpringLayout.WEST, panel_2, 38, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, panel_2, -62, SpringLayout.EAST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.NORTH, panel_2, 0, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, panel_2, 66, SpringLayout.NORTH, panel_1);
		panel_1.setLayout(sl_panel_1);
		panel_1.add(panel_2);
		panel_1.add(panel_2_1_2);
		panel_1.add(panel_2_1);
		
		/**
		 * Checkbox for showing the password
		 */
		final JCheckBox chckbxNewCheckBox = new JCheckBox("Vezi parola");
		chckbxNewCheckBox.setBackground(Color.LIGHT_GRAY);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxNewCheckBox.isSelected()) {
					passwordField.setEchoChar((char)0);
				}
				else {
					passwordField.setEchoChar('•');
				}
			}
		});
		chckbxNewCheckBox.setBounds(6, 132, 155, 21);
		panel_2_1.add(chckbxNewCheckBox);
		
		/**
		 * jlabel with action->create new account when is clicked
		 * mouse entered/exited-> the text change its color
		 */
		final JLabel lblNewLabel_3 = new JLabel("Nu ai cont? Creeaza unul!");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				NewAccount a = new NewAccount();
				a.lunch();
				f.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Color c = lblNewLabel_3.getBackground();
				lblNewLabel_3.setBackground(lblNewLabel_3.getForeground());
				lblNewLabel_3.setForeground(c);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Color c = lblNewLabel_3.getBackground();
				lblNewLabel_3.setBackground(lblNewLabel_3.getForeground());
				lblNewLabel_3.setForeground(c);
			}
		});
		lblNewLabel_3.setForeground(new Color(0, 102, 102));
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblNewLabel_3, 264, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblNewLabel_3, -120, SpringLayout.SOUTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, panel_2_1, -6, SpringLayout.NORTH, lblNewLabel_3);
		sl_panel_1.putConstraint(SpringLayout.NORTH, panel_2_1_2, 31, SpringLayout.SOUTH, lblNewLabel_3);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblNewLabel_3, 92, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, lblNewLabel_3, 276, SpringLayout.WEST, panel_1);
		lblNewLabel_3.setBackground(Color.ORANGE);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_1.add(lblNewLabel_3);
		
		/**
		 * login button -> search for user and log it on if it s find
		 * if not an error message will show up
		 */
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String textEmail = emailtf.getText();
				String textPasswd = String.valueOf(passwordField.getPassword());
				
				//EDIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				
				textEmail = "admin@gmail.com";
				textPasswd = "admin123";
				textEmail = "ap1@gmail.com";
				textPasswd = "ap1123"; 
				
				//textEmail = "a@s.com";
				//textPasswd = "12345678Aa@";
				//^EDIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				//get user from databases
				User user = null;
				//MANAGE THE EXCEPTION FOR WRONG USER INPUT
				if(Database.getInstance().findUser(textEmail, textPasswd)) {
					user = Database.getInstance().getAdmin(textEmail,textPasswd);
					if(user == null) {
						user = Database.getInstance().getApartment(textEmail,textPasswd);
					}
					Association association = Database.getInstance().getAssociation(user);
					if(user instanceof Admin)
						AdminAccount.lunch(association,(Admin)user);
					else 
						ApartmentAccount.lunch((Apartment) user,association);
					f.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Email/parola incorect(a)","Eroare logare",JOptionPane.ERROR_MESSAGE);
					emailtf.setText(null);
					passwordField.setText(null);
				}
			}	
		});
		
		//right pannel
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBounds(396, 10, 390, 425);
		contentPane.add(panel_1_1);
		
		//load the image
		
		Image image = null;
		try {
			image = (new ImageIcon(this.getClass().getResource("/login.png"))).getImage();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//display the login image
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(image));
		lblNewLabel_4.setBounds(41,71,325,282);
		
		panel_1_1.add(lblNewLabel_4);
		
		
	}
	
}
