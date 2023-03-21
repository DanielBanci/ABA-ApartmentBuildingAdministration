package mainPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import appInterface.ApartmentAccount;
import appInterface.Login;
import appInterface.NewAccounts;
import auxiliary.ApInfoPanel;
import auxiliary.MessagePanel;
import auxiliary.PanelRound;
import auxiliary.UpdateLine;
import mainClasses.Admin;
import mainClasses.Database;
import mainClasses.EmailSender;
import mainClasses.RandomPassword;

/**
This class extends MainPanel and creates a panel that allows for changing an admin's name and email.
After a successful change, the old admin will be disconnected and a new password will be generated and sent
to the new admin via email.
@author Ciprian Banci
@version 1.0
*/
public class ChangeAdmin extends MainPanel{
	private Title title;
	private PanelRound content;
	private Admin admin;
	private Admin newAdmin;
	private UpdateLine newName;
	private UpdateLine newEmail;
	private JFrame frame;
	
	/**
	 * Constructor for the class. It initializes the title, content and messages panels.
	 * 
	 * @param adm The admin that will be changed
	 * @param f The JFrame containing this class
	 */
	public ChangeAdmin(Admin adm, JFrame f) {
		super(); 
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		admin = adm; 
		frame = f; 
		title = new Title("Schimbare administrator",null);
		content = makeContent();
		
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(title);
		this.add(ApInfoPanel.emptyNoSizePanel());
		
		//message line
		JPanel mLine = new JPanel();
		mLine.setLayout(new BoxLayout(mLine, BoxLayout.X_AXIS));
		mLine.setOpaque(false);
		
		JPanel message = new MessagePanel(new Color(52,145,200),10,25,25,true);
		//message.setBackground(new Color(219,133,50));
		message.setLayout(new GridLayout(1,1));
		//message.setLayout(new BoxLayout(message,BoxLayout.Y_AXIS));
		message.setMinimumSize(new Dimension(630,180));
		message.setMaximumSize(new Dimension(630,180));
		message.setPreferredSize(new Dimension(630,180));

		JTextArea text = new JTextArea();
		text.setText("\n         Pentru a schimba administratorul asociatiei introduce-ti mai jos "
				+ "numele si e-mail-ul acestuia.\n"
				+ "   Noul administrator va prelua contul dumneavoastra, o noua parola va fi generata "
				+ "iar detaliile de \n"
				+ "   conectare vor fi trimise prin e-mail catre noul administrator.\n\n"
				+ "   Atentie!\n"
				+ "         Dupa aceasta operatie ve-ti fi deconectat automat si nu ve-ti mai "
				+ "avea acces la cont!");
		text.setEditable(false);
		text.setPreferredSize(new Dimension(100,100));
		text.setFont(new Font("Times New Roman",Font.PLAIN,16));
		message.add(text);
		text.setOpaque(false);
		
		mLine.add(ApartmentAccount.makeEmptyPanel());
		mLine.add(message);
		mLine.add(ApInfoPanel.emptyNoSizePanel());
		
		this.add(mLine);
		//this.add(ApInfoPanel.emptyNoSizePanel());
		this.add(ApartmentAccount.makeEmptyPanel());
		
		//content line
		JPanel line = new JPanel();
		line.setLayout(new BoxLayout(line,BoxLayout.X_AXIS));
		line.setOpaque(false);
		
		line.add(ApInfoPanel.emptyNoSizePanel());
		line.add(content);
		line.add(ApInfoPanel.emptyNoSizePanel());
		
		this.add(line);
		this.add(ApInfoPanel.emptyNoSizePanel());
		
		
		
	}
	
	/**
	 * Method that creates the content panel. It contains an update line for the name, 
	 * one for the email and a "save" button.
	 * @return Returns the content panel
	 */
	private PanelRound makeContent() {
		PanelRound content = new PanelRound();
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
		
		//title
		JPanel titleP = new JPanel();
		titleP.setLayout(new GridLayout(1,1));
		titleP.setOpaque(false);
		
		JLabel titlel = new JLabel("Noul administrator");
		titlel.setFont(new Font("Times New Roman",Font.PLAIN,18));
		titlel.setHorizontalAlignment(JLabel.CENTER);
		
		titleP.add(titlel);
		
		content.add(ApartmentAccount.makeEmptyPanel());
		content.add(titleP);
		content.add(ApInfoPanel.emptyNoSizePanel());
		
		newName = new UpdateLine("Nume");
		newEmail = new UpdateLine("Email");
		content.add(newName);
		content.add(ApartmentAccount.makeEmptyPanel());
		content.add(newEmail);
		content.add(ApInfoPanel.emptyNoSizePanel());
		
		//button line
		JPanel bLine = new JPanel();
		bLine.setLayout(new GridLayout(1,1));
		bLine.setOpaque(false);
		bLine.setMaximumSize(new Dimension(1000,50));
		
		JButton save = new JButton("Salveaza");
		save.addActionListener(saveAction());
		
		bLine.add(ApartmentAccount.makeEmptyPanel());
		bLine.add(save);
		bLine.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(bLine);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		content.setMinimumSize(new Dimension(400,200));
		content.setMaximumSize(new Dimension(400,200));
		content.setPreferredSize(new Dimension(400,200));
		
		return content;
	}
	
	/**
	 * Returns the action for save button.
	 * It checks the input and make change the admin email
	 * and set a new password to that account.
	 * Then it send an emal to the new owner with this data
	 * @return action for saveing admin change
	 */
	private ActionListener saveAction() {
		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//check input
				String name = newName.getData().getInput().getText();
				String email = newEmail.getData().getInput().getText();
				if(verifyEmail(email) && 
						NewAccounts.isValidUsername(name)) {
					
					//save new data
					admin.setName(name);
					admin.setEmail(email);
					
					//generate new password
					admin.setPasswd((new RandomPassword()).generatePassword().toString());
					
					//send email to new admin
					(new EmailSender()).sendToAsync(admin.getEmail(), admin.getPasswd());
					
					//save new admin in db
					Database.getInstance().updateAdmin(admin);
					
					//disconnect the old admin
					frame.dispose();
					Login.lunch();
				}
				
				
			}
			
		};
		
		return action;
	}
	
	/**
	 * Check of email is unique and valid.
	 * @param email the email to be checked
	 * @return true if the email is unique and valid
	 */
	private boolean verifyEmail(String email) {
		if(!NewAccounts.isValidEmail(email)) {
			JOptionPane.showMessageDialog(new JFrame(), "Adresa de e-mail nu e valida!", "Eroare", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(!Database.getInstance().isUniqueEmail(email)) {
			JOptionPane.showMessageDialog(new JFrame(), "Exista deja un cont cu aceasta adresa de e-mail!", "Eroare", 
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
