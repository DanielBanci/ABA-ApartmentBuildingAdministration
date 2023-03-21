package mainPanels;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import appInterface.ApartmentAccount;
import auxiliary.ApInfoPanel;
import mainClasses.User;

/**
 * The panel that let the user to edit the password or phone number
 * @author Ciprian Banci
 * @version 1.0
 */
public class ModifyAccountInfo extends MainPanel{
	private Title title;
	private User user;
	private ChangePhoneNo phoneNo;
	private ChangePassword password;
	
	/**
	 * Contructor. Makes the content for user.
	 * @param u current user
	 */
	public ModifyAccountInfo(User u) {
		super(); 
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		user = u;
		this.title = new Title("Schimbare date cont",null);
		title.setMaximumSize(new Dimension(5000,100));
		title.setMinimumSize(new Dimension(200,100));
		title.setPreferredSize(new Dimension(500,100));
		
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(title);
		this.add(ApInfoPanel.emptyNoSizePanel());
		//line
		JPanel line = new JPanel();
		line.setLayout(new BoxLayout(line,BoxLayout.X_AXIS));
		line.setOpaque(false);
		
		password = new ChangePassword(user);
		phoneNo = new ChangePhoneNo(user);
		
		line.add(ApartmentAccount.makeEmptyPanel());
		line.add(password);
		line.add(ApartmentAccount.makeEmptyPanel());
		line.add(phoneNo);
		line.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(line);
		this.add(ApInfoPanel.emptyNoSizePanel());
	}
}
