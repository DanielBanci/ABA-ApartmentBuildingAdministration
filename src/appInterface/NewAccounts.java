package appInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import auxiliary.ApartmentData;
import auxiliary.CheckCreatedDocL;
import auxiliary.PanelRound;
import auxiliary.UpdateLine;
import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.Database;
import mainPanels.ChangePassword;
import mainPanels.Title;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

/**
The NewAccounts class provides a user interface for user to create a 
new account in the application.
@author Ciprian Banci
@version 1.0
*/
public class NewAccounts extends JFrame {

	private JPanel contentPane;
	private JScrollPane pane;
	
	private PanelRound newAdmin;
	private PanelRound newAssociation;
	
	//accounts
	private Admin admin;
	public Association association;
	public List<Apartment> apartments;
	private JPanel subContent;
	
	//admin data
	private UpdateLine admName;
	private UpdateLine admEmail;
	private UpdateLine admPasswd;
	private UpdateLine admPasswdV;
	private UpdateLine admPhoneNo;
	
	//associaton data
	private UpdateLine localityDistrict;
	private UpdateLine streetNumber;
	private UpdateLine buildingStair;
	private UpdateLine coldWarmWPrice;
	private UpdateLine repairUpdateF;
	private UpdateLine rulElectF;
	private UpdateLine salariesWarming;
	private UpdateLine gasTrash;
	
	private UpdateLine apartsNo;//with combobox
	public Integer aptsNo;
	
	NewAccounts frame = this;
	

	/**
	 * Launch the application.
	 */
	public static void lunch() { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewAccounts frame = new NewAccounts();
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
	public NewAccounts() {
		apartments = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 420);
		setMinimumSize(new Dimension(800,400));
		final NewAccounts frame = this;
		Image img = null;
		img = (new ImageIcon(this.getClass().getResource("/brickBackground.jpg"))).getImage();
		final Image background = img;
		contentPane = new JPanel() { 
			@Override
			protected void paintComponent(Graphics g) {
		       super.paintComponent(g);
		        g.drawImage(background, 0, 0, null); // image full size
		        this.setOpaque(true); 
			  }
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		contentPane.setPreferredSize(new Dimension(700,420));//650/420
		setContentPane(contentPane);
		
		subContent = new JPanel();
		subContent.setLayout(new BoxLayout(subContent,BoxLayout.Y_AXIS));
		subContent.setOpaque(false);
		
		//title
		Title titlePanel = new Title("Creare cont",null);
		titlePanel.setMaximumSize(new Dimension(5000,100));
		titlePanel.setMinimumSize(new Dimension(200,100));
		titlePanel.setPreferredSize(new Dimension(500,100));
		this.add(ApartmentAccount.makeEmptyPanel());
		this.pack();
		this.add(titlePanel);
		
		pane = new JScrollPane(subContent,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setOpaque(false);
		pane.getViewport().setOpaque(false);
		
		contentPane.add(ApartmentAccount.makeEmptyPanel());
		this.add(pane);
		
		newAdmin = makeNewAdmin();
		
		subContent.add(newAdmin);

		//association
		newAssociation = makeNewAssociation();
		
	}
	
	/**
	 * Return a panel that help the user to make a new association account.
	 * @return a panel that help the user to make a new association account.
	 */
	private PanelRound makeNewAssociation() {
		final PanelRound content = new PanelRound();
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
		content.setMaximumSize(new Dimension(620,800));
		
		JPanel titleP = new JPanel();
		titleP.setLayout(new GridLayout(1,3));
		titleP.setOpaque(false);
		
		JLabel titleL = new JLabel("Asociatie");
		titleL.setHorizontalAlignment(JLabel.CENTER);
		titleL.setFont(new Font("Times New Roman",Font.PLAIN,18));
		
		titleP.add(titleL);
		
		content.add(ApartmentAccount.makeEmptyPanel());
		content.add(titleP);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		localityDistrict = new UpdateLine("Localitatea","Judetul");
		streetNumber = new UpdateLine("Strada","Numarul");
		buildingStair = new UpdateLine("Bloc","Scara");
		coldWarmWPrice = new UpdateLine("Pret contor apa rece","Pret contor apa calda");
		repairUpdateF = new UpdateLine("Fond de reparatii","Fond de imbunatatiri");
		rulElectF = new UpdateLine("Fond de rulment","Electricitate scara");
		salariesWarming = new UpdateLine("Salarii","Factura incalzire");
		gasTrash = new UpdateLine("Factura gas","Pret gunoi");
		apartsNo = new UpdateLine("","Nr. de apartamente",false,true);
		apartsNo.getData().getInput().setVisible(false);
		DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();//schimba
		for(Integer i=1;i<101;i++) {
			model.addElement(i);
		}
		apartsNo.getData2().getComboBox().setModel(model);
		
		final NewAccounts frame = this;
		
		//mark if changes where made
		CheckCreatedDocL checkDocLis = new CheckCreatedDocL(content);
		localityDistrict.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		localityDistrict.getData2().getInput().getDocument().addDocumentListener(checkDocLis);

		streetNumber.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		streetNumber.getData2().getInput().getDocument().addDocumentListener(checkDocLis);

		buildingStair.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		buildingStair.getData2().getInput().getDocument().addDocumentListener(checkDocLis);

		coldWarmWPrice.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		coldWarmWPrice.getData2().getInput().getDocument().addDocumentListener(checkDocLis);

		repairUpdateF.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		repairUpdateF.getData2().getInput().getDocument().addDocumentListener(checkDocLis);

		rulElectF.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		rulElectF.getData2().getInput().getDocument().addDocumentListener(checkDocLis);

		salariesWarming.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		salariesWarming.getData2().getInput().getDocument().addDocumentListener(checkDocLis);

		gasTrash.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		gasTrash.getData2().getInput().getDocument().addDocumentListener(checkDocLis);
		  
		apartsNo.getData2().getComboBox().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				content.set_backgroundColor(Color.LIGHT_GRAY);
				content.repaint();
			}
		});
		
		content.add(localityDistrict);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(streetNumber);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(buildingStair);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(coldWarmWPrice);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(repairUpdateF);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(rulElectF);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(salariesWarming);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(gasTrash);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(apartsNo);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		//button
		JPanel buttonP = new JPanel();
		buttonP.setLayout(new BoxLayout(buttonP,BoxLayout.X_AXIS));
		buttonP.setOpaque(false);
				
		JButton newAssoc = new JButton("Salveaza cont");
			
		buttonP.add(ApartmentAccount.makeEmptyPanel());
		buttonP.add(newAssoc);
		buttonP.add(ApartmentAccount.makeEmptyPanel());
			
		content.add(buttonP);
		content.add(ApartmentAccount.makeEmptyPanel());
		
		
		
		//button action
		newAssoc.addActionListener(makeNewAssocAction());
		
		return content;
	}
	
	/**
	 * Return the action listener for the button the create the new association account.
	 * @return the action listener for the button the create the new association account.
	 */
	private ActionListener makeNewAssocAction() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check the input NEED TO BE CHANGED (GOOD FOR LINE DESIGN)
				if(checkNewAssocinput()) {//input ok
					//mark that account was created
					newAssociation.set_backgroundColor(new Color(39,166,0));
					newAssociation.repaint();
					association = new Association();//missing peopleNoTotal, sMSum, apartments
					
					association.setLocality(localityDistrict.getData().getInput().getText());
					association.setDistrict(localityDistrict.getData2().getInput().getText());
					association.setStreet(streetNumber.getData().getInput().getText());
					association.setNumber(streetNumber.getData2().getInput().getText());
					association.setBuilding(buildingStair.getData().getInput().getText());
					association.setStair(buildingStair.getData2().getInput().getText());
					association.setcWaterPrice(Double.valueOf(coldWarmWPrice.getData().getInput().getText()));
					association.setwWaterPrice(Double.valueOf(coldWarmWPrice.getData2().getInput().getText()));
					association.setRepairFund(Double.valueOf(repairUpdateF.getData().getInput().getText()));
					association.setUpdateFund(Double.valueOf(repairUpdateF.getData2().getInput().getText()));
					association.setRulFund(Double.valueOf(rulElectF.getData().getInput().getText()));
					association.setElectBill(Double.valueOf(rulElectF.getData2().getInput().getText()));
					association.setSalaries(Double.valueOf(salariesWarming.getData().getInput().getText()));
					association.setWarmingBill(Double.valueOf(salariesWarming.getData2().getInput().getText()));
					association.setGasBill(Double.valueOf(gasTrash.getData().getInput().getText()));
					association.setTrashPrice(Double.valueOf(gasTrash.getData2().getInput().getText()));
					association.setPayingDates(null);
					
					//set association id
					association.setId(Database.getInstance().getLastAssociationId()+1);
					//admin
					association.setAdmin(admin);
					
					//set notice board last update date as current date
					LocalDate date = LocalDate.now();
					java.sql.Date sqlDate = new java.sql.Date(date.getYear()-1900,date.getMonthValue()-1,date.getDayOfMonth());
					
					association.setnBLastUpdate(sqlDate);
					
					//clear subContent
					subContent.removeAll();
					subContent.add(newAdmin);
					subContent.add(newAssociation);
					
					//make new apartments window
					aptsNo = Integer.valueOf(apartsNo.getData2().getComboBox().getSelectedItem().toString());
					
					//check if the apartments number changed
					for(Integer i=0;i<apartments.size();i++) {
						if(apartments.get(i).getNo() > aptsNo) {
							apartments.remove(apartments.get(i));
						}
					}
					
					//apartments.sort
					Collections.sort(apartments, new Comparator<Apartment>() {
					    @Override
					    public int compare(Apartment o1, Apartment o2) {
					        return o1.getNo() - o2.getNo();
					    }
					});
					Integer index = 0;
					for(Integer i=1;i<aptsNo+1;i++) {
						if(index < apartments.size() && i == apartments.get(index).getNo()) {
							
							subContent.add(new ApartmentData(frame,i,apartments.get(index)));
							index++;
						}else {
							subContent.add(new ApartmentData(frame,i,null));
						}
					}
					
					frame.pack();
					
				}
			}
		};
		
		return action;
	}
	
	/**
	 * Check the input for new association account.
	 * @return true if input is correct formatted.
	 */
	private boolean checkNewAssocinput() {
		if(
				localityDistrict.getData().getInput().getText().isEmpty() ||
				localityDistrict.getData2().getInput().getText().isEmpty() ||
				streetNumber.getData().getInput().getText().isEmpty() ||
				streetNumber.getData2().getInput().getText().isEmpty() ||
				buildingStair.getData().getInput().getText().isEmpty() ||
				buildingStair.getData2().getInput().getText().isEmpty() ||
				coldWarmWPrice.getData().getInput().getText().isEmpty() ||
				coldWarmWPrice.getData2().getInput().getText().isEmpty() ||
				repairUpdateF.getData().getInput().getText().isEmpty() ||
				repairUpdateF.getData2().getInput().getText().isEmpty() ||
				rulElectF.getData().getInput().getText().isEmpty() ||
				rulElectF.getData2().getInput().getText().isEmpty() ||
				salariesWarming.getData().getInput().getText().isEmpty() ||
				salariesWarming.getData2().getInput().getText().isEmpty() ||
				gasTrash.getData().getInput().getText().isEmpty() ||
				gasTrash.getData2().getInput().getText().isEmpty()
				) {
				JOptionPane.showMessageDialog(null, "Toate campurile trebuie completate","Eroare",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}else if(
					!AdminAccount.isNumeric(coldWarmWPrice.getData().getInput().getText()) ||
					!AdminAccount.isNumeric(coldWarmWPrice.getData2().getInput().getText()) ||
					!AdminAccount.isNumeric(repairUpdateF.getData().getInput().getText()) ||
					!AdminAccount.isNumeric(repairUpdateF.getData2().getInput().getText()) ||
					!AdminAccount.isNumeric(rulElectF.getData().getInput().getText()) || 
					!AdminAccount.isNumeric(rulElectF.getData2().getInput().getText()) ||
					!AdminAccount.isNumeric(salariesWarming.getData().getInput().getText()) ||
					!AdminAccount.isNumeric(salariesWarming.getData2().getInput().getText()) ||
					!AdminAccount.isNumeric(gasTrash.getData().getInput().getText()) ||
					!AdminAccount.isNumeric(gasTrash.getData2().getInput().getText())
					) {
				JOptionPane.showMessageDialog(null, "Toate preturile trebuie sa fie numere!\n"
						+ "'.' e folosit ca delimitator!","Eroare",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		return true;
	}
	
	/**
	 * Return a panel that help the user to make a new admin account.
	 * @return a panel that help the user to make a new admin account.
	 */
	private PanelRound makeNewAdmin() {//adauga admin id cand creezi contul
		//new account admin
		newAdmin = new PanelRound();
		newAdmin.setMaximumSize(new Dimension(450,280));
		newAdmin.setLayout(new BoxLayout(newAdmin,BoxLayout.Y_AXIS));
				
		JPanel admTitle = new JPanel();
		admTitle.setLayout(new GridLayout(1,3));
		admTitle.setOpaque(false);
				
		JLabel admTitleLbl = new JLabel("Administrator");
		admTitleLbl.setHorizontalAlignment(JLabel.CENTER);
		admTitleLbl.setFont(new Font("Times New Roman",Font.PLAIN,18));
			
		admTitle.add(ApartmentAccount.makeEmptyPanel());
		admTitle.add(admTitleLbl);
		admTitle.add(ApartmentAccount.makeEmptyPanel());
		
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new GridLayout(1,2));
		checkBoxPanel.setOpaque(false);
		
		JCheckBox checkB = new JCheckBox("Vezi parola");
		checkB.setOpaque(false);
		
		checkBoxPanel.add(ApartmentAccount.makeEmptyPanel());
		checkBoxPanel.add(checkB);
		checkBoxPanel.setMaximumSize(new Dimension(400,50));
				
		admName = new UpdateLine("Nume");
		admEmail = new UpdateLine("Email");
		admPasswd = new UpdateLine("Parola");
		admPasswdV = new UpdateLine("Verifica parola");
		admPhoneNo = new UpdateLine("Nr. telefon");
		
		checkB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkB.isSelected()) {
					admPasswd.getData().getPasswordP().setEchoChar((char)0);
					admPasswdV.getData().getPasswordP().setEchoChar((char)0);
				}
				else {
					admPasswd.getData().getPasswordP().setEchoChar('•');
					admPasswdV.getData().getPasswordP().setEchoChar('•');
				}
			}
		});
			
		newAdmin.add(admTitle);
		newAdmin.add(ApartmentAccount.makeEmptyPanel());
		
		newAdmin.add(admName);
		newAdmin.add(ApartmentAccount.makeEmptyPanel());
			
		newAdmin.add(admEmail);
		newAdmin.add(ApartmentAccount.makeEmptyPanel());
			
		newAdmin.add(admPasswd);
		newAdmin.add(ApartmentAccount.makeEmptyPanel());
				
		newAdmin.add(admPasswdV);
		//newAdmin.add(ApartmentAccount.makeEmptyPanel());
			
		newAdmin.add(checkBoxPanel);
		//newAdmin.add(ApartmentAccount.makeEmptyPanel());
		
		newAdmin.add(admPhoneNo);
		newAdmin.add(ApartmentAccount.makeEmptyPanel());
		
		//button
		JPanel buttonP = new JPanel();
		buttonP.setLayout(new BoxLayout(buttonP,BoxLayout.X_AXIS));
		buttonP.setOpaque(false);
		
		JButton newAdm = new JButton("Salveaza cont");
		
		buttonP.add(ApartmentAccount.makeEmptyPanel());
		buttonP.add(newAdm);
		buttonP.add(ApartmentAccount.makeEmptyPanel());
		
		newAdmin.add(buttonP);
		newAdmin.add(ApartmentAccount.makeEmptyPanel());
		
		//change color when input change
		
		CheckCreatedDocL checkDocLis = new CheckCreatedDocL(newAdmin);
		  
		admEmail.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		admName.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		admPasswd.getData().getPasswordP().getDocument().addDocumentListener(checkDocLis);
		admPasswdV.getData().getPasswordP().getDocument().addDocumentListener(checkDocLis);
		admPhoneNo.getData().getInput().getDocument().addDocumentListener(checkDocLis);
		
		//button action
		newAdm.addActionListener(newAdmAction());
		
		return newAdmin;
	}
	
	/**
	 * Check if a string is in a correct email format.
	 * @param email
	 * @return true if the email is correctly formatted.
	 */
	public static boolean isValidEmail(String email) {
		// Regular expression pattern for email validation
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
				"[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
				"A-Z]{2,7}$";

		// Creating a pattern object and matching it with the email string
		Pattern pattern = Pattern.compile(emailRegex);
		if (email == null) {
			return false;
		}
		// Matching the pattern with the email string
		java.util.regex.Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * Check if not empty, 3<length<30, alphanumeric + underscores
	 * @param username
	 * @return true if username match the requirements
	 */
	public static boolean isValidUsername(String username) {
		/*
		 * 
		 */
	    // Check if the username is null or empty
	    if (username == null || username.isEmpty()) {
	        return false;
	    }
	    
	    // Check the length of the username
	    if (username.length() < 3 || username.length() > 30) {
	        return false;
	    }
	    // Check that the username contains only alphanumeric characters and underscores
	    for (int i = 0; i < username.length(); i++) {
	        char c = username.charAt(i);
	        if (!Character.isLetterOrDigit(c) && c != '_' && c != ' ') {
	           return false;
	        }
	    }

	    // If all checks pass, the username is valid
	    return true;
	}

	/**
	 * Return the action for the button that create the new admin account.
	 * @return the action for the button that create the new admin account.
	 */
	private ActionListener newAdmAction() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check content
				if(checkAdmInput()){//input ok
					newAdmin.set_backgroundColor(new Color(39,166,0));
					newAdmin.repaint();
					LocalDate date = LocalDate.now();
					java.sql.Date sqlDate = new java.sql.Date(date.getYear()-1900,date.getMonthValue()-1,date.getDayOfMonth());
					
					admin = new Admin();//lipsa id, association id
					admin.setName(admName.getData().getInput().getText());
					admin.setEmail(admEmail.getData().getInput().getText());
					admin.setPasswd(String.valueOf(admPasswd.getData().getPasswordP().getPassword()));
					admin.setPhoneNo(admPhoneNo.getData().getInput().getText());
					admin.setRegisterDate(sqlDate);
					
					
					//set id
					admin.setId(Database.getInstance().getLastAdminId()+1);
					
					//add association new account
					subContent.remove(newAssociation);
					subContent.add(newAssociation,1);
					//subContent.add(new ApartmentData(frame,1));
					contentPane.setPreferredSize(new Dimension(650,520));

					frame.pack();
					
				}
					
			}
		};
		
		return action;
	}
	
	/**
	 * Check the input for admin.
	 * @return true if the input is correctly formatted.
	 */
	private boolean checkAdmInput() {
		if(admName.getData().getInput().getText().isEmpty() ||
				admEmail.getData().getInput().getText().isEmpty() ||
				admPasswd.getData().getPasswordP().getPassword().length == 0 ||
				admPasswdV.getData().getPasswordP().getPassword().length == 0 ||
				admPhoneNo.getData().getInput().getText().isEmpty()
					) {
				JOptionPane.showMessageDialog(null, "Toate campurile trebuie completate","Eroare",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}else if(!Arrays.equals(admPasswd.getData().getPasswordP().getPassword(), 
					admPasswdV.getData().getPasswordP().getPassword())
					) {
				JOptionPane.showMessageDialog(null, "Parolele nu se potrivesc","Eroare",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}else if(!isValidEmail(admEmail.getData().getInput().getText())){
				JOptionPane.showMessageDialog(null, "Adresa de e-mail nu e valida!","Eroare",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}else if(!Database.getInstance().isUniqueEmail(admEmail.getData().getInput().getText())){
				JOptionPane.showMessageDialog(null, "Exista deja un cont cu aceasta adresa!","Eroare",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}else if(!isValidPhoneNumber(admPhoneNo.getData().getInput().getText())){
				JOptionPane.showMessageDialog(null, "Numarul de telefon este format gresit!","Eroare",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}else if(!isStrongPassword(String.valueOf(admPasswd.getData().getPasswordP().getPassword()))){
				JOptionPane.showMessageDialog(null, "Parola trebuie sa contina cel putin 8 caractere\n"
						+ "si cel putin:\n"
						+ "- 1 litera mica\n"
						+ "- 1 litera mare\n"
						+ "- 1 cifra\n"
						+ "- 1 caracter special('@$!%*?&')","Eroare",JOptionPane.ERROR_MESSAGE);
				admPasswd.getData().setPasswordContent(null);
				admPasswdV.getData().setPasswordContent(null);
				return false;
			} else if(!isValidUsername(admName.getData().getInput().getText())) {
				JOptionPane.showMessageDialog(new JFrame(), "Numele trebuie sa contina intre 3 si 30 de litere!\n"
						+ "contine doar cifre, litere, spatiu sau underscore!", "Eroare", 
						JOptionPane.ERROR_MESSAGE);
				return false;
			}else {
				return true;
			}
	}
	
	/**
	 * Check if phoneNumber reprezents a correct phone number (romanian phone number)
	 * @param phoneNumber
	 * @return true if phone number is correctly formatted.
	 */
	public static boolean isValidPhoneNumber(String phoneNumber) {
	    // regular expression for Romanian phone numbers 
	    String regex = "^07[0-9]{8}$|^\\+407[0-9]{8}$";
	    
	    // check if the phone number matches the regular expression
	    return phoneNumber.matches(regex);
	}
	
	/**
	 * Checks if password has at least 8 characters,
	 * a lowercase letter, an uppercase letter, a number 
	 * and a special character
	 * @param password
	 * @return true if password is strong enough
	 */
	public static boolean isStrongPassword(String password) {
	    // Define the criteria for a strong password
	    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	    
	    // Check if the password matches the criteria
	    if (password.matches(regex)) {
	        return true;
	    } else {
	        return false;
	    }
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
