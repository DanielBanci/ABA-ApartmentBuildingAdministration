package mainPanels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import appInterface.ApartmentAccount;
import appInterface.NewAccounts;
import auxiliary.ApInfoPanel;
import auxiliary.PanelRound;
import auxiliary.UpdateLine;
import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Database;
import mainClasses.User;

/**
A panel that allows the user to change their password. The panel is built using
the PanelRound class as a base and contains input fields for the old password,
new password and password verification.
@author Ciprian Banci
@version 1.0
*/
public class ChangePassword extends PanelRound{
	/*
	 * panel to change the password
	 */
	private JPanel title;
	private JPanel boxPanel;
	
	/**
	Constructs a new ChangePassword panel for the given user. The panel includes
	input fields for the user's old password, new password and password verification,
	as well as a checkbox to show the entered passwords in plain text.
	@param user the user for which to change the password
	*/
	public ChangePassword(User user) {//scoate f
		super(); 
		
		//frame = f;
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		title = new JPanel();
		title.setLayout(new GridLayout(1,1));
		title.setOpaque(false);
		title.setMaximumSize(new Dimension(3000,100));
		title.setMinimumSize(new Dimension(100,100));
		
		JLabel tlb = new JLabel("Schimbare parola");
		tlb.setFont(new Font("Times New Roman",Font.PLAIN,20));
		tlb.setHorizontalAlignment(JLabel.CENTER);
		
		title.add(tlb);
		
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(title);
		this.add(ApInfoPanel.emptyNoSizePanel());

		final UpdateLine oldPassword = new UpdateLine("Parola veche: ");
		final UpdateLine newPassword = new UpdateLine("Parola noua: ");
		final UpdateLine newPasswordV = new UpdateLine("Verifica parola: ");

		oldPassword.setOpaque(false);
		newPassword.setOpaque(false);
		newPasswordV.setOpaque(false);
		
		
		this.add(oldPassword);
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(newPassword);
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(newPasswordV);
		
		//checkBox for shwoing password
		boxPanel = new JPanel();
		boxPanel.setOpaque(false);
		boxPanel.setLayout(new GridLayout(1,4));
		boxPanel.add(ApartmentAccount.makeEmptyPanel());
		boxPanel.add(ApartmentAccount.makeEmptyPanel());
		boxPanel.setMaximumSize(new Dimension(1000,50));
		
		final JCheckBox checkBox = new JCheckBox("Vezi parola");
		checkBox.setOpaque(false);
		checkBox.setFont(new Font("Times New Roman",Font.PLAIN,15));
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox.isSelected()) {
					//passwordField.setEchoChar((char)0);
					oldPassword.getData().getPasswordP().setEchoChar((char)0);
					newPassword.getData().getPasswordP().setEchoChar((char)0);
					newPasswordV.getData().getPasswordP().setEchoChar((char)0);
				}
				else {
					//passwordField.setEchoChar('*');
					oldPassword.getData().getPasswordP().setEchoChar('•');
					newPassword.getData().getPasswordP().setEchoChar('•');
					newPasswordV.getData().getPasswordP().setEchoChar('•');
				}
			}
		});
		boxPanel.add(checkBox);
		boxPanel.add(ApartmentAccount.makeEmptyPanel());
		//boxPanel.add(ApInfoPanel.emptyNoSizePanel());
		this.add(boxPanel);
		this.add(ApInfoPanel.emptyNoSizePanel());
		
		JPanel buttonLine = new JPanel();
		buttonLine.setLayout(new GridLayout(1,3));
		buttonLine.setMaximumSize(new Dimension(1000,30));
		buttonLine.setOpaque(false);
		buttonLine.add(ApartmentAccount.makeEmptyPanel());

		JButton save = new JButton("Salveaza");
		save.setPreferredSize(new Dimension(100,30));
		save.setMaximumSize(new Dimension(1000,30));
		buttonLine.add(save);
		buttonLine.add(ApartmentAccount.makeEmptyPanel());
		
		/**
		 * Save action for save button.
		 * It checks the input and set the new password to user check pass.
		 */
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//check input
				if(!String.valueOf(oldPassword.getData().getPasswordP().getPassword()).equals(user.getPasswd())) {
					JOptionPane.showMessageDialog(null, "Parola incorecta","Eroare",JOptionPane.ERROR_MESSAGE);
					oldPassword.getData().setPasswordContent(null);
					newPassword.getData().setPasswordContent(null);
					newPasswordV.getData().setPasswordContent(null);
				}else if(!String.valueOf(newPassword.getData().getPasswordP().getPassword()).equals(
						String.valueOf(newPasswordV.getData().getPasswordP().getPassword()))) {
					JOptionPane.showMessageDialog(null, "Parolele nu se potrivesc","Eroare",JOptionPane.ERROR_MESSAGE);
				}else if(!NewAccounts.isStrongPassword(String.valueOf(newPassword.getData().getPasswordP().getPassword()))){
					JOptionPane.showMessageDialog(null, "Parola trebuie sa contina cel putin 8 caractere\n"
							+ "si cel putin:\n"
							+ "- 1 litera mica\n"
							+ "- 1 litera mare\n"
							+ "- 1 cifra\n"
							+ "- 1 caracter special('@$!%*?&')","Eroare",JOptionPane.ERROR_MESSAGE);
					newPassword.getData().setPasswordContent(null);
					newPasswordV.getData().setPasswordContent(null);
				}else{
					user.setPasswd(String.valueOf(newPassword.getData().getPasswordP().getPassword()));
					if(user instanceof Apartment)
						Database.getInstance().updateApartment((Apartment)user);//CHECK THIS!!!!!!!!!!(FOR ADMIN)
					else
						Database.getInstance().refreshAdminPasswordDB((Admin)user);
					JOptionPane.showMessageDialog(null, "Parola a fost schimbata","Dialog",JOptionPane.INFORMATION_MESSAGE);
					oldPassword.getData().setPasswordContent(null);
					newPassword.getData().setPasswordContent(null);
					newPasswordV.getData().setPasswordContent(null);
				}
			}
		});
		this.add(buttonLine);
		this.add(ApartmentAccount.makeEmptyPanel());
		this.setMaximumSize(new Dimension(400,500));
		this.setMinimumSize(new Dimension(400,300));
		this.setPreferredSize(new Dimension(400,300));
	}

}
