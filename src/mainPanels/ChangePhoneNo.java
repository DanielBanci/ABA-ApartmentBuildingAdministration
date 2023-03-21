package mainPanels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
This class represents a panel used to change the phone number of a user account.
It extends the PanelRound class.
@author Ciprian Banci
@version 1.0
*/
public class ChangePhoneNo extends PanelRound{
	private JPanel title;
	private UpdateLine phoneNo;
	
	/**
	Creates a new ChangePhoneNo object for the specified user.
	@param user the user account for which the phone number will be updated
	*/
	public ChangePhoneNo(User user) {
		
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		title = new JPanel();
		title.setLayout(new GridLayout(1,1));
		title.setOpaque(false);
		title.setMaximumSize(new Dimension(500,100));
		
		JLabel titlel = new JLabel("Schimbare numar telefon");
		titlel.setHorizontalAlignment(JLabel.CENTER);
		titlel.setFont(new Font("Times New Roman",Font.PLAIN,20));
		
		title.add(titlel);
		
		phoneNo = new UpdateLine("Noul numar");

		//adding the content
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(title);
		this.add(ApInfoPanel.emptyNoSizePanel());
		
		this.add(phoneNo);
		this.add(ApInfoPanel.emptyNoSizePanel());
		
		//save button
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new GridLayout(1,1));
		bPanel.setOpaque(false);
		bPanel.setMaximumSize(new Dimension(450,50));
		bPanel.setMinimumSize(new Dimension(450,50));
		
		JButton save = new JButton("Salveaza");
		
		bPanel.add(ApartmentAccount.makeEmptyPanel());
		bPanel.add(save);
		bPanel.add(ApartmentAccount.makeEmptyPanel());
		
		save.addActionListener(saveAction(user));
		
		this.add(bPanel);
		this.add(ApartmentAccount.makeEmptyPanel());

		this.setMaximumSize(new Dimension(400,500));
		this.setMinimumSize(new Dimension(400,300));
		this.setPreferredSize(new Dimension(400,300));
	}
	
	/**
	Creates an ActionListener to handle saving the updated phone number.
	@param user the user account for which the phone number will be updated
	@return an ActionListener to handle saving the updated phone number
	*/
	private ActionListener saveAction(User user) {
		ChangePhoneNo f = this;
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!NewAccounts.isValidPhoneNumber(phoneNo.getData().getInput().getText())) {
					JOptionPane.showMessageDialog(null, "Numarul nu e format corect","Eroare",
							JOptionPane.ERROR_MESSAGE);
				}else {
					user.setPhoneNo(phoneNo.getData().getInput().getText());
					JOptionPane.showMessageDialog(null, "Noul numar a fost salvat!","Dialog",
							JOptionPane.INFORMATION_MESSAGE);
					if(user instanceof Apartment)
						Database.getInstance().updateApartment((Apartment)user);
					else
						Database.getInstance().refreshAdminNumberDB((Admin)user);
					phoneNo.getData().getInput().setText(null);
				}
				
			}
			
		};
		
		return action;
	}
}
