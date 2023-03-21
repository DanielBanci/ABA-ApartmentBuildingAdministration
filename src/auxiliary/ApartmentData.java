package auxiliary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import appInterface.AdminAccount;
import appInterface.ApartmentAccount;
import appInterface.NewAccounts;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.Database;
import mainClasses.EmailSender;
import mainClasses.RandomPassword;

/**
 * A panel that contain all apartment data
 * The data are displayed 2 in a row and for the water contors the consum is also shown
 * @author Ciprian Banci
 *@version 1.0
 */
public class ApartmentData extends PanelRound{

	private JPanel namep;
	private JLabel namel;
	private UpdateLine email;
	private UpdateLine emailPhone;
	private UpdateLine password;
	private UpdateLine phoneNo;

	private UpdateLine area;
	private UpdateLine areaPeopleNo;
	private UpdateLine centralHGas;
	private UpdateLine overdueLastUpdate;
	private UpdateLine indCStatusP;
	private ContorLine cWContor;
	private ContorLine wWContor;
	private ContorLine cWCK;
	private ContorLine wWCK;
	private UpdateLine peopleNo;
	private UpdateLine centralHeating;
	private UpdateLine gasContor;
	private UpdateLine individualContors;
	private UpdateLine contorUpdateDate;
	private UpdateLine payedStatus;
	private UpdateLine overdue;
	private JButton newApartment;
	
	//for new accounts
	private UpdateLine noEmail;
	private UpdateLine phoneNoOverdue;
	private Boolean ans;
	
	private NewAccounts frame;
	
	static int lastIdApartment;
	static int count;
	private int lastIdAp;
	private boolean addedFlag;
	private Color color = new Color(39,166,0); 
	 
	/**
	 * Create a panel for new apartment account based on number, and the information from apartment if not null
	 * It also has save button for saving the new account
	 * @param frame the frame that contain the panel
	 * @param number the number of apartment
	 * @param apartment the apartment information are displayed for
	 */
	public ApartmentData(final NewAccounts frame,Integer number, Apartment apartment) {
		super();
		
		this.frame = frame;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(620,430));
		namep = new JPanel();
		namep.setLayout(new GridLayout(1,3));
		namep.setOpaque(false);
		
		namel = new JLabel("Apartamentul " + number);
		namel.setHorizontalAlignment(JLabel.CENTER);
		namel.setFont(new Font("Times New Roman",Font.BOLD,18));
		
		namep.add(ApartmentAccount.makeEmptyPanel());
		namep.add(namel);
		namep.add(ApartmentAccount.makeEmptyPanel());

		noEmail = new UpdateLine("Nr. apartament","Email",false,false);
		areaPeopleNo = new UpdateLine("Suprafata","Nr.persoane",false,true);
		centralHGas = new UpdateLine("Contor incalzire","Contor Gaz",true,true);//combobox
		phoneNoOverdue = new UpdateLine("Nr. telefon","Restante");
		indCStatusP = new UpdateLine("Contoare separate apa","Status plata",true,true);//combobox
		
		//set the number of apartment
		noEmail.getData().getInput().setText(number.toString());
		noEmail.getData().getInput().setEditable(false);
		
		//add data to panel
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(namep);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(noEmail);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(areaPeopleNo);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(phoneNoOverdue);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(centralHGas);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(indCStatusP);
		this.add(ApartmentAccount.makeEmptyPanel());

		//comboboxes models
		DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>();
		model1.addElement("Da");
		model1.addElement("Nu");
		centralHGas.getData().getComboBox().setModel(model1);
		
		DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
		model2.addElement("Da");
		model2.addElement("Nu");
		centralHGas.getData2().getComboBox().setModel(model2);
		
		DefaultComboBoxModel<String> model3 = new DefaultComboBoxModel<>();
		model3.addElement("Da");
		model3.addElement("Nu");
		indCStatusP.getData().getComboBox().setModel(model3);
		
		DefaultComboBoxModel<String> model4 = new DefaultComboBoxModel<>();
		model4.addElement("Platit");
		model4.addElement("Neplatit");
		indCStatusP.getData2().getComboBox().setModel(model4);
		
		DefaultComboBoxModel<Integer> modelNrPers = new DefaultComboBoxModel<>();
		for(Integer i = 1;i<21;i++) {
			modelNrPers.addElement(i);
		}
		areaPeopleNo.getData2().getComboBox().setModel(modelNrPers);
		
		//display contors area 
		//title for bath contors
		final JPanel titleAux = new JPanel();
		titleAux.setLayout(new GridLayout(1,3));
		titleAux.setOpaque(false);
		
		final JLabel cTitle = new JLabel("Contoare baie");
		cTitle.setHorizontalAlignment(JLabel.CENTER);
		cTitle.setFont(new Font("Times New Roman",Font.PLAIN,16));
		
		titleAux.add(ApartmentAccount.makeEmptyPanel());
		titleAux.add(cTitle);
		titleAux.add(ApartmentAccount.makeEmptyPanel());
		
		final JPanel titleAux2 = new JPanel();
		titleAux2.setLayout(new GridLayout(1,3));
		titleAux2.setOpaque(false);
		
		final JLabel cTitle2 = new JLabel("Contoare bucatarie");
		cTitle2.setHorizontalAlignment(JLabel.CENTER);
		cTitle2.setFont(new Font("Times New Roman",Font.PLAIN,16));
		
		titleAux2.add(ApartmentAccount.makeEmptyPanel());
		titleAux2.add(cTitle2);
		titleAux2.add(ApartmentAccount.makeEmptyPanel());
		
		//make contors data
		wWContor = new ContorLine("Apa calda");
		cWContor = new ContorLine("Apa rece");
		
		wWCK = new ContorLine("Apa calda");
		cWCK = new ContorLine("Apa rece");
		
		//add contors
		this.add(titleAux);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(cWContor);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(wWContor);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(titleAux2);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(cWCK);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(wWCK);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		//write data if apartment != null
		if(apartment != null) {
			this.set_backgroundColor(color);
			namel.setText(apartment.getName());
			
			noEmail.getData().getInput().setText(apartment.getNo().toString());
			noEmail.getData2().getInput().setText(apartment.getEmail());
			
			areaPeopleNo.getData().getInput().setText(apartment.getArea().toString());
			areaPeopleNo.getData2().getComboBox().setSelectedItem(apartment.getPeopleNo());
			
			phoneNoOverdue.getData().getInput().setText(apartment.getPhoneNo());
			phoneNoOverdue.getData2().getInput().setText(apartment.getOverdue().toString());
			
			//comboBoxes
			centralHGas.getData().getComboBox().setSelectedItem(apartment.getCentralHeating() ? "Da" : "Nu");
			centralHGas.getData2().getComboBox().setSelectedItem(apartment.getGasContor() ? "Da" : "Nu");
			
			indCStatusP.getData().getComboBox().setSelectedItem(apartment.getIndividualWContors() ? "Da" : "Nu");
			indCStatusP.getData2().getComboBox().setSelectedItem(apartment.getPayedStatus() ? "Platit" : "Neplatit");
			
			wWContor.getOldContor().getInput().setText(apartment.getwWContorOld().toString());
			wWContor.getNewContor().getInput().setText(apartment.getwWContorNew().toString());
			
			cWContor.getOldContor().getInput().setText(apartment.getcWContorOld().toString());
			cWContor.getNewContor().getInput().setText(apartment.getcWContorNew().toString());
			
			if(apartment.getIndividualWContors()) {//if separated contors
				wWCK.getOldContor().getInput().setText(apartment.getwWCOldK().toString());
				wWCK.getNewContor().getInput().setText(apartment.getwWCNewK().toString());
				
				cWCK.getOldContor().getInput().setText(apartment.getcWCOldK().toString());
				cWCK.getNewContor().getInput().setText(apartment.getcWCNewK().toString());
			}
		}
		
		//actions to change color when input change
		final ApartmentData f = this;
		//comboboxes
		ActionListener comboL = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.set_backgroundColor(Color.LIGHT_GRAY);
				f.repaint();
			}
		};
		
		//text fields
		CheckCreatedDocL checkDocL = new CheckCreatedDocL(f);
		noEmail.getData2().getInput().getDocument().addDocumentListener(checkDocL);

		areaPeopleNo.getData().getInput().getDocument().addDocumentListener(checkDocL);
		areaPeopleNo.getData2().getComboBox().addActionListener(comboL);

		phoneNoOverdue.getData().getInput().getDocument().addDocumentListener(checkDocL);
		phoneNoOverdue.getData2().getInput().getDocument().addDocumentListener(checkDocL);

		//comboBoxescomboL
		centralHGas.getData().getComboBox().addActionListener(comboL);
		centralHGas.getData2().getComboBox().addActionListener(comboL);

		indCStatusP.getData().getComboBox().addActionListener(comboL);
		indCStatusP.getData2().getComboBox().addActionListener(comboL);

		wWContor.getOldContor().getInput().getDocument().addDocumentListener(checkDocL);
		wWContor.getNewContor().getInput().getDocument().addDocumentListener(checkDocL);
		
		cWContor.getOldContor().getInput().getDocument().addDocumentListener(checkDocL);
		cWContor.getNewContor().getInput().getDocument().addDocumentListener(checkDocL);

		wWCK.getOldContor().getInput().getDocument().addDocumentListener(checkDocL);
		wWCK.getNewContor().getInput().getDocument().addDocumentListener(checkDocL);
		
		cWCK.getOldContor().getInput().getDocument().addDocumentListener(checkDocL);
		cWCK.getNewContor().getInput().getDocument().addDocumentListener(checkDocL);
		
		//button
		final JButton save = new JButton("Salveaza");
		final JPanel button = new JPanel();
		button.setLayout(new BoxLayout(button,BoxLayout.X_AXIS));
		button.setOpaque(false);
		
		button.add(ApartmentAccount.makeEmptyPanel());
		button.add(save);
		button.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(button);
		this.add(ApartmentAccount.makeEmptyPanel());

		ans = true;
		
		//comboboxes actions
		indCStatusP.getData().getComboBox().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//remove content if any
				f.removeAll();
				//add data to panel
				f.add(ApartmentAccount.makeEmptyPanel());
				f.add(namep);
				f.add(ApartmentAccount.makeEmptyPanel());
				
				f.add(noEmail);
				f.add(ApartmentAccount.makeEmptyPanel());
				
				f.add(areaPeopleNo);
				f.add(ApartmentAccount.makeEmptyPanel());
				
				f.add(phoneNoOverdue);
				f.add(ApartmentAccount.makeEmptyPanel());
				
				f.add(centralHGas);
				f.add(ApartmentAccount.makeEmptyPanel());
				
				f.add(indCStatusP);
				f.add(ApartmentAccount.makeEmptyPanel());
				
				String choise = indCStatusP.getData().getComboBox().getSelectedItem().toString();
				ans = choise.equals("Da") ? true : false;
				
				//title for contors
				if(ans) {//has separated contors 
					f.add(titleAux);
					f.add(ApartmentAccount.makeEmptyPanel());
				}else {//just one set of contors
					cTitle.setText("Contoare");
					f.add(titleAux);
					f.add(ApartmentAccount.makeEmptyPanel());
				}
				
				f.add(cWContor);
				f.add(ApartmentAccount.makeEmptyPanel());
				
				f.add(wWContor);
				f.add(ApartmentAccount.makeEmptyPanel());
				
				//check if there are separated contors for water
				if(ans) {
					f.add(titleAux2);
					f.add(ApartmentAccount.makeEmptyPanel());
					
					f.add(cWCK);
					f.add(ApartmentAccount.makeEmptyPanel());
					
					f.add(wWCK);
					f.add(ApartmentAccount.makeEmptyPanel());
					
					f.setMaximumSize(new Dimension(620,430));
				}else {
					f.setMaximumSize(new Dimension(620,330));
				}
				
				f.add(button);
				f.add(ApartmentAccount.makeEmptyPanel());
				
				frame.pack();
			}
		});
		final ApartmentData frameAp = this;
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check input + TO DO -> check the phone number, make the email unic
				if(noEmail.getData2().getInput().getText().isEmpty() || //check if empty
					areaPeopleNo.getData().getInput().getText().isEmpty() ||
					phoneNoOverdue.getData().getInput().getText().isEmpty() ||
					phoneNoOverdue.getData2().getInput().getText().isEmpty() ||
					cWContor.getOldContor().getInput().getText().isEmpty() ||
					cWContor.getNewContor().getInput().getText().isEmpty() ||
					wWContor.getOldContor().getInput().getText().isEmpty() ||
					wWContor.getNewContor().getInput().getText().isEmpty() || ( ans && ( //if individual contors
					cWCK.getOldContor().getInput().getText().isEmpty() ||
					cWCK.getNewContor().getInput().getText().isEmpty() || 
					wWCK.getOldContor().getInput().getText().isEmpty() ||
					wWCK.getNewContor().getInput().getText().isEmpty()))
					) {
					JOptionPane.showMessageDialog(null, "Toate campurile trebuie completate","Eroare",
							JOptionPane.ERROR_MESSAGE);
				}else if(!AdminAccount.isNumeric(areaPeopleNo.getData().getInput().getText()) ||//check if numeric
						!AdminAccount.isNumeric(phoneNoOverdue.getData2().getInput().getText()) ||
						!AdminAccount.isNumeric(cWContor.getOldContor().getInput().getText()) ||
						!AdminAccount.isNumeric(cWContor.getNewContor().getInput().getText()) ||
						!AdminAccount.isNumeric(wWContor.getOldContor().getInput().getText()) ||
						!AdminAccount.isNumeric(wWContor.getNewContor().getInput().getText()) || ( ans && ( //if individual contors
						!AdminAccount.isNumeric(cWCK.getOldContor().getInput().getText()) ||
						!AdminAccount.isNumeric(cWCK.getNewContor().getInput().getText()) || 
						!AdminAccount.isNumeric(wWCK.getOldContor().getInput().getText()) ||
						!AdminAccount.isNumeric(wWCK.getNewContor().getInput().getText())))
						
						) {
					JOptionPane.showMessageDialog(null, "Campurile suprafata, restante, "
							+ "si cele pentru \ncontoare trebuie sa fie numere!\n"
							+ "'.' e folosit ca delimitator!","Eroare",
							JOptionPane.ERROR_MESSAGE);
				}else if(!NewAccounts.isValidEmail(noEmail.getData2().getInput().getText())){
					JOptionPane.showMessageDialog(null, "Adresa de e-mail nu e valida!","Eroare",
							JOptionPane.ERROR_MESSAGE);
				}else if(!Database.getInstance().isUniqueEmail(noEmail.getData2().getInput().getText())){
					JOptionPane.showMessageDialog(null, "Exista deja un cont cu aceasta adresa!","Eroare",
							JOptionPane.ERROR_MESSAGE);
				}else if(!NewAccounts.isValidPhoneNumber(phoneNoOverdue.getData().getInput().getText())){
					JOptionPane.showMessageDialog(null, "Numarul de telefon este format gresit!","Eroare",
							JOptionPane.ERROR_MESSAGE);
				}else if(emailUsedBefore(noEmail.getData2().getInput().getText())){
					JOptionPane.showMessageDialog(null, "Exista deja un cont cu acest e-mail!","Eroare",
							JOptionPane.ERROR_MESSAGE);
				}else if(//check if old contor are at leats equal with the new contor
						Double.valueOf(cWContor.getNewContor().getInput().getText()) <
						Double.valueOf(cWContor.getOldContor().getInput().getText()) ||
						Double.valueOf(wWContor.getNewContor().getInput().getText()) <
						Double.valueOf(wWContor.getOldContor().getInput().getText())|| ( ans && (
						Double.valueOf(cWCK.getNewContor().getInput().getText()) <
						Double.valueOf(cWCK.getOldContor().getInput().getText()) ||
						Double.valueOf(wWCK.getNewContor().getInput().getText()) <
						Double.valueOf(wWCK.getOldContor().getInput().getText()) ) )
						){
						JOptionPane.showMessageDialog(null, "Contoarele noi trebuie sa fie cel putin "
								+ "egale cu cele vechi!","Eroare",JOptionPane.ERROR_MESSAGE);
				}else{//input ok
					//mark that account was created
					frameAp.set_backgroundColor(color);
					frameAp.repaint();
					Apartment newAp= new Apartment();//missing id,association id,
					
					newAp.setName("Apartamentul " + noEmail.getData().getInput().getText());
					newAp.setEmail(noEmail.getData2().getInput().getText());
					newAp.setPhoneNo(phoneNoOverdue.getData().getInput().getText());
					//auto generate password
					newAp.setPasswd((new RandomPassword()).generatePassword().toString());
					//make register date
					LocalDate date = LocalDate.now();
					java.sql.Date sqlDate = new java.sql.Date(date.getYear()-1900,date.getMonthValue()-1,date.getDayOfMonth());
					
					newAp.setRegisterDate(sqlDate);
					newAp.setNo(Integer.valueOf(frameAp.noEmail.getData().getInput().getText()));
					newAp.setPeopleNo(Integer.valueOf(areaPeopleNo.getData2().getComboBox().getSelectedItem().toString()));
					newAp.setArea(Double.valueOf(areaPeopleNo.getData().getInput().getText()));
					boolean answer = centralHGas.getData().getComboBox().getSelectedItem().toString().equals("Da")
							? true : false;
					newAp.setCentralHeating(answer);
					answer = centralHGas.getData2().getComboBox().getSelectedItem().toString().equals("Da")
							? true : false;
					newAp.setGasContor(answer);
					newAp.setcWContorOld(Double.valueOf(cWContor.getOldContor().getInput().getText()));
					newAp.setcWContorNew(Double.valueOf(cWContor.getNewContor().getInput().getText()));
					
					newAp.setwWContorOld(Double.valueOf(wWContor.getOldContor().getInput().getText()));
					newAp.setwWContorNew(Double.valueOf(wWContor.getNewContor().getInput().getText()));
					
					answer = indCStatusP.getData().getComboBox().getSelectedItem().toString().equals("Da")
							? true : false;
					newAp.setIndividualWContors(answer);
					
					//setting the kitchen contors if any
					if(answer) {
						newAp.setcWCOldK(Double.valueOf(cWCK.getOldContor().getInput().getText()));
						newAp.setcWCNewK(Double.valueOf(cWCK.getNewContor().getInput().getText()));
						
						newAp.setwWCOldK(Double.valueOf(wWCK.getOldContor().getInput().getText()));
						newAp.setwWCNewK(Double.valueOf(wWCK.getNewContor().getInput().getText()));
					}else{//poate le pui pe 0?
						newAp.setcWCOldK(0.0);
						newAp.setcWCNewK(0.0);
						
						newAp.setwWCOldK(0.0);
						newAp.setwWCNewK(0.0);
					}
					newAp.setOverdue(Double.valueOf(phoneNoOverdue.getData2().getInput().getText()));
					newAp.setContorUpdateDate(sqlDate);
					//payed status
					answer = indCStatusP.getData2().getComboBox().getSelectedItem().toString().equals("Platit")
							? true : false;
					newAp.setPayedStatus(answer);
					//need to check if created before!
					Boolean existFlag = false;
					//if created before save the new data
					for(Apartment ap : frame.apartments) {
						if(ap.getNo() == newAp.getNo()) {
							ap = newAp;
							existFlag = true;
						}
					}
					//add to list if new
					if(!existFlag) {
						frame.apartments.add(newAp);
					}
					
					//check if all the apartments were created
					if(frame.apartments.size() == frame.aptsNo) {
						Collections.sort(frame.apartments, new Comparator<Apartment>() {
						    @Override
						    public int compare(Apartment o1, Apartment o2) {
						        return o1.getNo() - o2.getNo();
						    }
						});
						//set the id for apartments
						Integer id = Database.getInstance().getLastIdApartment() + 1;
						for(Apartment ap : frame.apartments) {
							ap.setId(id);
							id++;
						}
						//save the apartments in association
						frame.association.setApartments(frame.apartments);
						//write information to db
						Database.getInstance().insertAllData(frame.association);
						//send email to the new accounts
						//message
						
						sendEmailToUsers(frame.association);
						JOptionPane.showMessageDialog(null, "Conturile au fost create iar datele de conectare au\n"
								+ "fost timise catre utilizatori!"
								);
						
						//close window and open admin account
						AdminAccount.lunch(frame.association, frame.getAdmin());
						frame.dispose();
					}
				}
					
					
			}
		});
	}
	
	private boolean emailUsedBefore(String email) {
		/*
		 * return true if the email was used before for another apartment
		 */
		for(Apartment ap : frame.apartments) {
			if(ap.getEmail().equals(email))
				return true;
		}
		
		if(frame.getAdmin().getEmail().equals(email))
			return true;
		
		return false;
	}
	
	private void sendEmailToUsers(Association association) {
		/*
		 * send email to users (apartments)
		 * with connecting informations
		 */
		EmailSender email = new EmailSender();
		for(Apartment ap : association.getApartments()) {
			email.sendToAsync(ap.getEmail(), ap.getPasswd());
		}
		
		
	}
	
	/**
	 * Create a panel that display the apartment informations.
	 * @param apartment the apartment for which we display the information
	 * @param cBoxes if comboBoxes are required for boolean values
	 */
	public ApartmentData(final Apartment apartment, boolean cBoxes) {
		super();
		setLayout(new GridLayout(20,1));//check if correct!!!!!!!!
		
		//namep = new UpdateLine("Nume");
		namel = new JLabel(apartment.getName());
		namel.setHorizontalAlignment(JLabel.CENTER);
		namel.setFont(new Font("Times New Roman",Font.BOLD,16));

		emailPhone = new UpdateLine("Emai","Nr.telefon"); 
		areaPeopleNo = new UpdateLine("Suprafata","Nr.persoane");
		centralHGas = new UpdateLine("Contor incalzire","Contor Gaz",cBoxes,cBoxes);//combobox
		overdueLastUpdate = new UpdateLine("Restante","Ultima act. contoare");
		indCStatusP = new UpdateLine("Contoare separate apa","Status plata",cBoxes,cBoxes);//combobox
		
		//setting editable permisions
		emailPhone.getData().getInput().setEditable(false);
		emailPhone.getData2().getInput().setEditable(false);
		//overdueLastUpdate.getData2().getInput().setEditable(false);
		
		//add data to panel
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(namel);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(emailPhone);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(areaPeopleNo);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(overdueLastUpdate);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(centralHGas);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(indCStatusP);
		this.add(ApartmentAccount.makeEmptyPanel());

		//write data
		emailPhone.getData().getInput().setText(apartment.getEmail());
		emailPhone.getData2().getInput().setText(apartment.getPhoneNo());
		
		areaPeopleNo.getData().getInput().setText(apartment.getArea().toString());
		areaPeopleNo.getData2().getInput().setText(apartment.getPeopleNo().toString());
		
		overdueLastUpdate.getData().getInput().setText(apartment.getOverdue().toString());
		overdueLastUpdate.getData2().getInput().setText(apartment.getContorUpdateDate().toString());
		
		//comboboxes models
		if(cBoxes) {//check if combo boxes
			DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>();
			model1.addElement("Da");
			model1.addElement("Nu");
			centralHGas.getData().getComboBox().setModel(model1);

			DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
			model2.addElement("Da");
			model2.addElement("Nu");
			centralHGas.getData2().getComboBox().setModel(model2);

			DefaultComboBoxModel<String> model3 = new DefaultComboBoxModel<>();
			model3.addElement("Da");
			model3.addElement("Nu");
			indCStatusP.getData().getComboBox().setModel(model3);

			DefaultComboBoxModel<String> model4 = new DefaultComboBoxModel<>();
			model4.addElement("Platit");
			model4.addElement("Neplatit");
			indCStatusP.getData2().getComboBox().setModel(model4);
			
			//write apartment data
			centralHGas.getData().getComboBox().setSelectedItem(apartment.getCentralHeating() ? "Da" : "Nu");
			centralHGas.getData2().getComboBox().setSelectedItem(apartment.getGasContor() ? "Da" : "Nu");
			indCStatusP.getData().getComboBox().setSelectedItem(apartment.getIndividualWContors() ? "Da" : "Nu");
			indCStatusP.getData2().getComboBox().setSelectedItem(apartment.getPayedStatus() ? "Platit" : "Neplatit");
		}else {
			centralHGas.getData().getInput().setText(apartment.getCentralHeating() ? "Da" : "Nu");
			centralHGas.getData2().getInput().setText(apartment.getGasContor() ? "Da" : "Nu");

			indCStatusP.getData().getInput().setText(apartment.getIndividualWContors() ? "Da" : "Nu");
			indCStatusP.getData2().getInput().setText(apartment.getPayedStatus() ? "Platit" : "Neplatit");
		}
		
		
		//check if there are separated contors
		JLabel cTitle = new JLabel("Contoare baie");
		cTitle.setHorizontalAlignment(JLabel.CENTER);
		cTitle.setFont(new Font("Times New Roman",Font.PLAIN,16));
		if(apartment.getIndividualWContors()) {
			this.setLayout(new GridLayout(26,2));
			this.add(cTitle);
			this.add(ApartmentAccount.makeEmptyPanel());
		}else {
			cTitle.setText("Contoare");
			this.add(cTitle);
			this.add(ApartmentAccount.makeEmptyPanel());
		}
		
		wWContor = new ContorLine(apartment,"Apa calda");
		cWContor = new ContorLine(apartment,"Apa rece");
		
		this.add(cWContor);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(wWContor);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		//write data
		wWContor.getOldContor().getInput().setText(apartment.getwWContorOld().toString());
		wWContor.getNewContor().getInput().setText(apartment.getwWContorNew().toString());
		wWContor.getConsum().getInput().setText(((Double)(apartment.getwWContorNew() - apartment.getwWContorOld())).toString());
		
		cWContor.getOldContor().getInput().setText(apartment.getcWContorOld().toString());
		cWContor.getNewContor().getInput().setText(apartment.getcWContorNew().toString());
		cWContor.getConsum().getInput().setText(((Double)(apartment.getcWContorNew() - apartment.getcWContorOld())).toString());
		
		//check if there are separated contors for water
		if(apartment.getIndividualWContors()) {
			JLabel cTitle2 = new JLabel("Contoare baie");
			cTitle2.setHorizontalAlignment(JLabel.CENTER);
			cTitle2.setFont(new Font("Times New Roman",Font.PLAIN,16));
			
			wWCK = new ContorLine(apartment,"Apa calda");
			cWCK = new ContorLine(apartment,"Apa rece");
			
			//this.add(ApartmentAccount.makeEmptyPanel());
			this.add(cTitle2);
			this.add(ApartmentAccount.makeEmptyPanel());
			
			this.add(cWCK);
			this.add(ApartmentAccount.makeEmptyPanel());
			
			this.add(wWCK);
			this.add(ApartmentAccount.makeEmptyPanel());
			
			//write data
			wWCK.getOldContor().getInput().setText(apartment.getwWCOldK().toString());
			wWCK.getNewContor().getInput().setText(apartment.getwWCNewK().toString());
			wWCK.getConsum().getInput().setText(((Double)(apartment.getwWCNewK() - apartment.getwWCOldK())).toString());
			
			cWCK.getOldContor().getInput().setText(apartment.getcWCOldK().toString());
			cWCK.getNewContor().getInput().setText(apartment.getcWCNewK().toString());
			cWCK.getConsum().getInput().setText(((Double)(apartment.getcWCNewK() - apartment.getcWCOldK())).toString());
		}
		
		this.setPreferredSize(new Dimension(620,420));
		this.setMaximumSize(new Dimension(620,420));
		//setting editable permisions
		emailPhone.getData().getInput().setEditable(false);
		emailPhone.getData2().getInput().setEditable(false);
		
		areaPeopleNo.getData().getInput().setEditable(false);
		areaPeopleNo.getData2().getInput().setEditable(false);
		
		overdueLastUpdate.getData().getInput().setEditable(false);
		overdueLastUpdate.getData2().getInput().setEditable(false);
		
		if(cBoxes) {
			centralHGas.getData().getComboBox().setEditable(false);
			centralHGas.getData2().getComboBox().setEditable(false);

			indCStatusP.getData().getComboBox().setEditable(false);
			indCStatusP.getData2().getComboBox().setEditable(false);
		}else {
			centralHGas.getData().getInput().setEditable(false);
			centralHGas.getData2().getInput().setEditable(false);

			indCStatusP.getData().getInput().setEditable(false);
			indCStatusP.getData2().getInput().setEditable(false);
		}
		
		cWContor.getOldContor().getInput().setEditable(false);
		cWContor.getNewContor().getInput().setEditable(false);
		
		wWContor.getOldContor().getInput().setEditable(false);
		wWContor.getNewContor().getInput().setEditable(false);
		
		if(apartment.getIndividualWContors()) {
			wWCK.getOldContor().getInput().setEditable(false);
			wWCK.getNewContor().getInput().setEditable(false);
			
			cWCK.getOldContor().getInput().setEditable(false);
			cWCK.getNewContor().getInput().setEditable(false);
		}
		
	}

	public JPanel getNameP() {
		return this.namep;
	}

	public void setNameP(JPanel name) {
		this.namep = name;
	}

	public JPanel getEmail() {
		return email;
	}

	public JPanel getNamep() {
		return namep;
	}

	public void setNamep(JPanel namep) {
		this.namep = namep;
	}

	public JLabel getNamel() {
		return namel;
	}

	public void setNamel(JLabel namel) {
		this.namel = namel;
	}

	public UpdateLine getPassword() {
		return password;
	}

	public void setPassword(UpdateLine password) {
		this.password = password;
	}

	public UpdateLine getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(UpdateLine phoneNo) {
		this.phoneNo = phoneNo;
	}

	public UpdateLine getArea() {
		return area;
	}

	public void setArea(UpdateLine area) {
		this.area = area;
	}

	public UpdateLine getPeopleNo() {
		return peopleNo;
	}

	public void setPeopleNo(UpdateLine peopleNo) {
		this.peopleNo = peopleNo;
	}

	public UpdateLine getCentralHeating() {
		return centralHeating;
	}

	public void setCentralHeating(UpdateLine centralHeating) {
		this.centralHeating = centralHeating;
	}

	public UpdateLine getOverdue() {
		return overdue;
	}

	public void setOverdue(UpdateLine overdue) {
		this.overdue = overdue;
	}

	public JButton getNewApartment() {
		return newApartment;
	}

	public void setNewApartment(JButton newApartment) {
		this.newApartment = newApartment;
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		ApartmentData.count = count;
	}

	public int getLastIdAp() {
		return lastIdAp;
	}

	public void setLastIdAp(int lastIdAp) {
		this.lastIdAp = lastIdAp;
	}

	public boolean isAddedFlag() {
		return addedFlag;
	}

	public void setAddedFlag(boolean addedFlag) {
		this.addedFlag = addedFlag;
	}

	public void setEmail(UpdateLine email) {
		this.email = email;
	}

	public static void setLastIdApartment(int lastIdApartment) {
		ApartmentData.lastIdApartment = lastIdApartment;
	}

	public UpdateLine getEmailPhone() {
		return emailPhone;
	}

	public void setEmailPhone(UpdateLine emailPhone) {
		this.emailPhone = emailPhone;
	}

	public UpdateLine getAreaPeopleNo() {
		return areaPeopleNo;
	}

	public void setAreaPeopleNo(UpdateLine areaPeopleNo) {
		this.areaPeopleNo = areaPeopleNo;
	}

	public UpdateLine getCentralHGas() {
		return centralHGas;
	}

	public void setCentralHGas(UpdateLine centralHGas) {
		this.centralHGas = centralHGas;
	}

	public UpdateLine getOverdueLastUpdate() {
		return overdueLastUpdate;
	}

	public void setOverdueLastUpdate(UpdateLine overdueLastUpdate) {
		this.overdueLastUpdate = overdueLastUpdate;
	}

	public UpdateLine getIndCStatusP() {
		return indCStatusP;
	}

	public void setIndCStatusP(UpdateLine indCStatusP) {
		this.indCStatusP = indCStatusP;
	}

	public ContorLine getcWContor() {
		return cWContor;
	}

	public void setcWContor(ContorLine cWContor) {
		this.cWContor = cWContor;
	}

	public ContorLine getwWContor() {
		return wWContor;
	}

	public void setwWContor(ContorLine wWContor) {
		this.wWContor = wWContor;
	}

	public ContorLine getcWCK() {
		return cWCK;
	}

	public void setcWCK(ContorLine cWCK) {
		this.cWCK = cWCK;
	}

	public ContorLine getwWCK() {
		return wWCK;
	}

	public void setwWCK(ContorLine wWCK) {
		this.wWCK = wWCK;
	}

	public UpdateLine getGasContor() {
		return gasContor;
	}

	public void setGasContor(UpdateLine gasContor) {
		this.gasContor = gasContor;
	}

	public UpdateLine getIndividualContors() {
		return individualContors;
	}

	public void setIndividualContors(UpdateLine individualContors) {
		this.individualContors = individualContors;
	}

	public UpdateLine getContorUpdateDate() {
		return contorUpdateDate;
	}

	public void setContorUpdateDate(UpdateLine contorUpdateDate) {
		this.contorUpdateDate = contorUpdateDate;
	}

	public UpdateLine getPayedStatus() {
		return payedStatus;
	}

	public void setPayedStatus(UpdateLine payedStatus) {
		this.payedStatus = payedStatus;
	}
}
