package auxiliary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appInterface.AdminAccount;
import appInterface.ApartmentAccount;
import appInterface.NewAccounts;
import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.Database;
import mainClasses.EmailSender;
import mainClasses.RandomPassword;
import mainPanels.HomePanel;
import mainPanels.MainPanel;
import mainPanels.NoticeBoardPanel;
import mainPanels.Title;

/**
 * Panel for showing apartment information.
 * @author Ciprian Banci
 * @version 1.0
 */
public class ApInfoPanel extends MainPanel {
	Title title;
	//buttons
	JComboBox apChoose;
	JButton modify;
	JButton save;
	JButton pSModify;
	//panels
	ApartmentData apData;
	Apartment target;
	PanelRound payingStatus;
	//aux panels
	JPanel apDataLine;
	JPanel buttons;
	PanelRound saveButtonP;
	
	JFrame frame;
	
	/**
	 * Contructor of the panel for apartment account.
	 * It only display the data.
	 * @param association the association of the apartment 
	 * @param apartment the apartment for which the information will be displayed
	 * @param f the frame that contain the panel
	 */
	public ApInfoPanel(final Association association, Apartment apartment, ApartmentAccount f) {
		/*
		 * apartment info for apartment account
		 */
		super();
		
		frame = f;
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setOpaque(false);

        //title 
		title = new Title("Informatii apartament",null);
		title.setPreferredSize(new Dimension(100,70));
        title.setMaximumSize(new Dimension(3000,70));

         
        apData = new ApartmentData(apartment,false);
        
        //auxiliary panels
        //ap data line -> support for apData panel
        apDataLine = new JPanel();
        apDataLine.setLayout(new BoxLayout(apDataLine,BoxLayout.X_AXIS));
        apDataLine.setOpaque(false);
        
        apDataLine.add(ApartmentAccount.makeEmptyPanel());
        apDataLine.add(apData);
        apDataLine.add(emptyNoSizePanel());
        
        //adding the content
        this.add(ApartmentAccount.makeEmptyPanel());
        this.add(title);
        this.add(ApartmentAccount.makeEmptyPanel());
        //appDataLine
        apDataLine.add(ApartmentAccount.makeEmptyPanel());
        apDataLine.add(apData);
        apDataLine.add(emptyNoSizePanel());
		this.add(apDataLine);
	}
	
	/**
	 * Constructor of the panel for admin account.
	 * It display the informations and let the user edit them.
	 * It contains a combo box to choose the apartment, a button that let
	 * the admin to update the paying status for all apartments, a modify 
	 * button for modifying the values and a save button
	 * @param association the association of admin
	 * @param admin the current user
	 * @param f the frame that contain the panel
	 */
	public ApInfoPanel(final Association association, Admin admin, AdminAccount f) {
		super();
		
		frame = f;
		target = association.getApartments().get(0);//initially the first apartment info are shown
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setOpaque(false);

        //title
		title = new Title("Informatii apartamente",null);
		title.setPreferredSize(new Dimension(100,70));
        title.setMaximumSize(new Dimension(3000,70));

        payingStatus = makePayingStatus(association);
        
        apData = new ApartmentData(target,true);
        
        //auxiliary panels
        //ap data line -> support for apData panel
        apDataLine = new JPanel();
        apDataLine.setLayout(new BoxLayout(apDataLine,BoxLayout.X_AXIS));
        apDataLine.setOpaque(false);

        
        //saving button panel
        saveButtonP = new PanelRound();
		saveButtonP.setLayout(new BoxLayout(saveButtonP,BoxLayout.Y_AXIS));
		saveButtonP.setPreferredSize(new Dimension(200,50));
		saveButtonP.setMinimumSize(new Dimension(200,50));
		saveButtonP.setMaximumSize(new Dimension(200,50));
        
        //the line with choose apartment button panel and modify paying status
		JPanel buttonLine = new JPanel();
		buttonLine.setLayout(new BoxLayout(buttonLine,BoxLayout.X_AXIS));
		buttonLine.setOpaque(false);

		
		//the panel with choose apartment button
		JPanel contentBP = new PanelRound();//new JPanel();
		contentBP.setMaximumSize(new Dimension(1000,100));
		contentBP.setMinimumSize(new Dimension(1000,100));
		contentBP.setLayout(new BoxLayout(contentBP,BoxLayout.X_AXIS));
		contentBP.setOpaque(false);
		
		//panel with modify paying status
		JPanel pSButtonP = new PanelRound();
		pSButtonP.setMaximumSize(new Dimension(1000,100));
		pSButtonP.setMinimumSize(new Dimension(1000,100));
		pSButtonP.setLayout(new FlowLayout());
		pSButtonP.setLayout(new BoxLayout(pSButtonP,BoxLayout.X_AXIS));

		
		//left buttons panel
		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
		buttons.setOpaque(false);

		//modify button panel
		PanelRound modifyButtonP = new PanelRound();
		modifyButtonP.setLayout(new BoxLayout(modifyButtonP,BoxLayout.Y_AXIS));
		modifyButtonP.setPreferredSize(new Dimension(200,50));
		modifyButtonP.setMinimumSize(new Dimension(200,50));
		modifyButtonP.setMaximumSize(new Dimension(200,50));

		//support for modify button
		JPanel modifySupport = new JPanel();
		modifySupport.setLayout(new BoxLayout(modifySupport,BoxLayout.X_AXIS));
		modifySupport.setOpaque(false);
		
		//support for save button
		JPanel saveSupport = new JPanel();
		saveSupport.setLayout(new BoxLayout(saveSupport,BoxLayout.X_AXIS));
		saveSupport.setOpaque(false);

		//buttons
		//for apartment choise
		apChoose = new JComboBox();
		apChoose.setMaximumSize(new Dimension(150,20));
		//model
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel();
		for(Apartment ap : association.getApartments()) {
			model.addElement(ap.getName());
		}
		apChoose.setModel(model);
		apChoose.addActionListener(apChooseAction(association));
		
		//for modifying the paying dates
		pSModify = new JButton("Actualizare");
		pSModify.addActionListener(modifyPayingStatus(association));
		
		//for modifying the values
		modify = new JButton("Modifica");
		modify.addActionListener(modifyAction());
		
		//for saveing the new values
		save = new JButton("Salveaza");
		save.addActionListener(saveAction(association));

		//adding the content
		this.add(ApartmentAccount.makeEmptyPanel());
        this.add(title);
        this.add(ApartmentAccount.makeEmptyPanel());
        
        //content for JCombo panel
        contentBP.add(ApartmentAccount.makeEmptyPanel());
		contentBP.add(new JLabel("Alege un apartament"));
		contentBP.add(ApartmentAccount.makeEmptyPanel());
		contentBP.add(apChoose);
		contentBP.add(ApartmentAccount.makeEmptyPanel());
		
		//content for modifying paying dates
		pSButtonP.add(ApartmentAccount.makeEmptyPanel());
		pSButtonP.add(new JLabel("Actualizare status plata"));
		pSButtonP.add(ApartmentAccount.makeEmptyPanel());
		pSButtonP.add(pSModify);
		pSButtonP.add(ApartmentAccount.makeEmptyPanel());
		
		//JCombo line
		buttonLine.add(ApartmentAccount.makeEmptyPanel());
		buttonLine.add(contentBP);
		buttonLine.add(ApartmentAccount.makeEmptyPanel());
		buttonLine.add(pSButtonP);
		buttonLine.add(emptyNoSizePanel());
        
		//left buttons
		//modify support
		modifySupport.add(ApartmentAccount.makeEmptyPanel());
		modifySupport.add(modify);
		modifySupport.add(ApartmentAccount.makeEmptyPanel());
		//modify panel
		modifyButtonP.add(ApartmentAccount.makeEmptyPanel());
		modifyButtonP.add(modifySupport);
		modifyButtonP.add(ApartmentAccount.makeEmptyPanel());
		//save button support
		saveSupport.add(ApartmentAccount.makeEmptyPanel());
		saveSupport.add(save);
		saveSupport.add(ApartmentAccount.makeEmptyPanel());
		//save button panel
		saveButtonP.add(ApartmentAccount.makeEmptyPanel());
		saveButtonP.add(saveSupport);
		saveButtonP.add(ApartmentAccount.makeEmptyPanel());
		//left buttons panel
		buttons.add(modifyButtonP);
		
        //appDataLine
        apDataLine.add(ApartmentAccount.makeEmptyPanel());
        apDataLine.add(apData);
        apDataLine.add(ApartmentAccount.makeEmptyPanel());
        apDataLine.add(buttons);
		
        this.add(buttonLine);
		this.add(apDataLine);

		//document listener for input
		addDocList();
		addComboAct(target);

	}
	
	/**
	 * Makes the panel that let the admin to update the pay status for all apartments
	 * @param association the current association 
	 * @return the panel that let the admin to update the pay status for all apartments
	 */
	private PanelRound makePayingStatus(Association association) {
		PanelRound panel = new PanelRound();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
		
		JScrollPane pane = new JScrollPane(content,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		for(Apartment ap : association.getApartments()) {
			content.add(new PSUpdate(ap,association,frame));
		}
		
		//title
		JPanel titleP = new JPanel();
		titleP.setLayout(new GridLayout(1,1));
		titleP.setOpaque(false);
		
		JLabel title = new JLabel("Status plata");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Times New Roman",Font.PLAIN,18));
		
		titleP.add(title);
		
		panel.add(titleP);
		
		//for sides
		JPanel paneP = new JPanel();
		paneP.setLayout(new BoxLayout(paneP,BoxLayout.X_AXIS));
		paneP.setOpaque(false);
		
		paneP.add(ApartmentAccount.makeEmptyPanel());
		paneP.add(pane);
		paneP.add(ApartmentAccount.makeEmptyPanel());
		panel.add(paneP);
		panel.add(ApartmentAccount.makeEmptyPanel());

		panel.setMinimumSize(new Dimension(200,100));
		panel.setMaximumSize(new Dimension(200,200));
		
		return panel;
	}
	
	/**
	 * Makes the action that show the update pay status panel
	 * @param associatio the current association
	 * @return the action that show the update pay status panel
	 */
	private ActionListener modifyPayingStatus(Association associatio) {
		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//check if already shown
				boolean pSFlag = false;
				boolean saveFlag = false;
				for(Object o : buttons.getComponents()) {
					if(o.equals(payingStatus)) {
						pSFlag = true;
					}
					if(o.equals(saveButtonP)) {
						saveFlag = true;
					}
				}
				
				buttons.remove(payingStatus);
				
				//put a spce if not already
				if(buttons.getComponents().length == 1) {//just modify  button
					buttons.add(ApartmentAccount.makeEmptyPanel());
				}else if(buttons.getComponents().length == 2 && buttons.getComponent(1).equals(saveButtonP) ) {
					//modify and save
					buttons.add(ApartmentAccount.makeEmptyPanel());
				}
				
				//reset size
				buttons.setPreferredSize(new Dimension(200,420));
				buttons.setMaximumSize(new Dimension(200,420));
				
				buttons.add(payingStatus,-1);
				
				frame.pack();
			}
			
		};
		
		return action;
	}
	
	/**
	 * Makes a panel with of size set and with transparent background
	 * @return a panel with of size set and with transparent background
	 */
	public static JPanel emptyNoSizePanel() {
		/*
		 * return an empty panel, with no size set
		 * the panel is invisible
		 */
		JPanel emp = new JPanel();
		emp.setOpaque(false);
		
		return emp;
		
	}
	
	/**
	 * Makes action for combo boxes of the apartment data panel
	 * @param target the current apartment 
	 */
	private void addComboAct(Apartment target) {
		//central heating contor
		apData.getCentralHGas().getData().getComboBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String saveAns = target.getCentralHeating() ? "Da" : "Nu";
				if(//if ans changed
					!apData.getCentralHGas().getData().getComboBox().getSelectedItem().toString().equals(saveAns)
					) {
					apData.set_backgroundColor(Color.LIGHT_GRAY);
					apData.repaint();
				}
			}
		});
		
		//gas contor
		apData.getCentralHGas().getData2().getComboBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String saveAns = target.getGasContor() ? "Da" : "Nu";
				if(//if ans changed
					!apData.getCentralHGas().getData2().getComboBox().getSelectedItem().toString().equals(saveAns)
					) {
					apData.set_backgroundColor(Color.LIGHT_GRAY);
					apData.repaint();
				}
			}
		});
		
		//individual water contors
		apData.getIndCStatusP().getData().getComboBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String saveAns = target.getIndividualWContors() ? "Da" : "Nu";
				if(//if ans changed
					!apData.getIndCStatusP().getData().getComboBox().getSelectedItem().toString().equals(saveAns)
					) {
					apData.set_backgroundColor(Color.LIGHT_GRAY);
					apData.repaint();
				}
			}
		});
		
		//paying status
		apData.getIndCStatusP().getData2().getComboBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String saveAns = target.getIndividualWContors() ? "Platit" : "Neplatit";
				if(//if ans changed
					!apData.getIndCStatusP().getData2().getComboBox().getSelectedItem().toString().equals(saveAns)
					) {
					apData.set_backgroundColor(Color.LIGHT_GRAY);
					apData.repaint();
				}
			}
		});
	}
	
	/**
	 * Set a doc listener to march if any changes were made
	 */
	private void addDocList() {
		/*
		 * action for text fields 
		 * -> change color when save
		 * -> change back when any changes where made
		 */
		CheckCreatedDocL checkDocLis = new CheckCreatedDocL(apData);
		
		apData.getEmailPhone().getData().getInput().getDocument().addDocumentListener(checkDocLis);
		apData.getAreaPeopleNo().getData().getInput().getDocument().addDocumentListener(checkDocLis);
		apData.getAreaPeopleNo().getData2().getInput().getDocument().addDocumentListener(checkDocLis);
		apData.getOverdueLastUpdate().getData().getInput().getDocument().addDocumentListener(checkDocLis);
		apData.getcWContor().getOldContor().getInput().getDocument().addDocumentListener(checkDocLis);
		apData.getcWContor().getNewContor().getInput().getDocument().addDocumentListener(checkDocLis);
		apData.getwWContor().getOldContor().getInput().getDocument().addDocumentListener(checkDocLis);
		apData.getwWContor().getNewContor().getInput().getDocument().addDocumentListener(checkDocLis);
		if(target.getIndividualWContors()){  //if individual contors
				apData.getwWCK().getOldContor().getInput().getDocument().addDocumentListener(checkDocLis);
				apData.getwWCK().getNewContor().getInput().getDocument().addDocumentListener(checkDocLis);
				apData.getcWCK().getOldContor().getInput().getDocument().addDocumentListener(checkDocLis);
				apData.getcWCK().getNewContor().getInput().getDocument().addDocumentListener(checkDocLis);
		}
	}
	
	/**
	 * Makes the action used for saving new data
	 * @param association the current association
	 * @return the action used for saving new data
	 */
	private ActionListener saveAction(Association association) {
		
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check the input
				//if empty
				if(apData.getEmailPhone().getData().getInput().getText().isEmpty() ||
				apData.getAreaPeopleNo().getData().getInput().getText().isEmpty() ||
				apData.getAreaPeopleNo().getData2().getInput().getText().isEmpty() ||
				apData.getOverdueLastUpdate().getData().getInput().getText().isEmpty()||
				apData.getcWContor().getOldContor().getInput().getText().isEmpty() ||
				apData.getcWContor().getNewContor().getInput().getText().isEmpty() ||
				apData.getwWContor().getOldContor().getInput().getText().isEmpty() ||
				apData.getwWContor().getNewContor().getInput().getText().isEmpty() ||
				(target.getIndividualWContors() && ( //if individual contors
						apData.getwWCK().getOldContor().getInput().getText().isEmpty() ||
						apData.getwWCK().getNewContor().getInput().getText().isEmpty() ||
						apData.getcWCK().getOldContor().getInput().getText().isEmpty() ||
						apData.getcWCK().getNewContor().getInput().getText().isEmpty()
						))
						) {
					JOptionPane.showMessageDialog(null, "Toate campurile trebuie completate","Eroare",
							JOptionPane.ERROR_MESSAGE);
					
					apDataLine.remove(apData);
					apData = new ApartmentData(target,true);
					apDataLine.add(apData,1);
					buttons.remove(saveButtonP);
					frame.repaint();
					frame.pack();
				}else if(
						!AdminAccount.isNumeric(apData.getAreaPeopleNo().getData().getInput().getText()) ||
						!AdminAccount.isNumeric(apData.getAreaPeopleNo().getData2().getInput().getText()) ||
						!AdminAccount.isNumeric(apData.getOverdueLastUpdate().getData().getInput().getText())||
						!AdminAccount.isNumeric(apData.getcWContor().getOldContor().getInput().getText()) ||
						!AdminAccount.isNumeric(apData.getcWContor().getNewContor().getInput().getText()) ||
						!AdminAccount.isNumeric(apData.getwWContor().getOldContor().getInput().getText()) ||
						!AdminAccount.isNumeric(apData.getwWContor().getNewContor().getInput().getText()) ||
						(target.getIndividualWContors() && ( //if individual contors
								!AdminAccount.isNumeric(apData.getwWCK().getOldContor().getInput().getText()) ||
								!AdminAccount.isNumeric(apData.getwWCK().getNewContor().getInput().getText()) ||
								!AdminAccount.isNumeric(apData.getcWCK().getOldContor().getInput().getText()) ||
								!AdminAccount.isNumeric(apData.getcWCK().getNewContor().getInput().getText())
								))
						){
					JOptionPane.showMessageDialog(null, "Toate campurile trebuie sa fie numerice;"
							+ "\n'.' e folosit ca delimitator!","Eroare",
							JOptionPane.ERROR_MESSAGE);
					apDataLine.remove(apData);
					apData = new ApartmentData(target,true);
					buttons.remove(saveButtonP);
					apDataLine.add(apData,1);
					frame.repaint();
					frame.pack();
				}else if(!NewAccounts.isValidEmail(apData.getEmailPhone().getData().getInput().getText())){
					JOptionPane.showMessageDialog(null, "Adresa de e-mail nu e valida!","Eroare",
							JOptionPane.ERROR_MESSAGE);
				}else if(!apData.getEmailPhone().getData().getInput().getText().equals(target.getEmail()) &&
						!Database.getInstance().isUniqueEmail(apData.getEmailPhone().getData().getInput().getText())){
					JOptionPane.showMessageDialog(null, "Exista deja un cont cu aceasta adresa!","Eroare",
							JOptionPane.ERROR_MESSAGE);
				}else if(//check if old contor are at leats equal with the new contor
						Double.valueOf(apData.getcWContor().getNewContor().getInput().getText()) <
						Double.valueOf(apData.getcWContor().getOldContor().getInput().getText()) ||
						Double.valueOf(apData.getwWContor().getNewContor().getInput().getText()) <
						Double.valueOf(apData.getwWContor().getOldContor().getInput().getText())|| 
						( target.getIndividualWContors() && (
						Double.valueOf(apData.getwWCK().getNewContor().getInput().getText()) <
						Double.valueOf(apData.getwWCK().getOldContor().getInput().getText()) ||
						Double.valueOf(apData.getcWCK().getNewContor().getInput().getText()) <
						Double.valueOf(apData.getcWCK().getOldContor().getInput().getText()) ) )
						){
						JOptionPane.showMessageDialog(null, "Contoarele noi trebuie sa fie cel putin "
								+ "egale cu cele vechi!","Eroare",JOptionPane.ERROR_MESSAGE);
				}else{
					//input ok
					
					//mark as saved
					apData.set_backgroundColor(new Color(39,166,0));
					apData.repaint();
					
					//set the new data
					target.setArea(Double.valueOf(apData.getAreaPeopleNo().getData().getInput().getText()));
					target.setPeopleNo(Integer.valueOf(apData.getAreaPeopleNo().getData2().getInput().getText()));
					target.setOverdue(Double.valueOf(apData.getOverdueLastUpdate().getData().getInput().getText()));
					
					//contors
					//check if changed
					boolean cUpdateFlag = false;
					if(target.getcWContorOld() != Double.valueOf(apData.getcWContor().getOldContor().getInput().getText()) ||
							target.getcWContorNew() != Double.valueOf(apData.getcWContor().getNewContor().getInput().getText())||
							target.getwWContorOld() != Double.valueOf(apData.getwWContor().getOldContor().getInput().getText()) ||
							target.getwWContorNew() != Double.valueOf(apData.getwWContor().getNewContor().getInput().getText())
							) {
						cUpdateFlag = true;
						target.setcWContorOld(Double.valueOf(apData.getcWContor().getOldContor().getInput().getText()));
						target.setcWContorNew(Double.valueOf(apData.getcWContor().getNewContor().getInput().getText()));
						
						target.setwWContorOld(Double.valueOf(apData.getwWContor().getOldContor().getInput().getText()));
						target.setwWContorNew(Double.valueOf(apData.getwWContor().getNewContor().getInput().getText()));
						
						//display new consum
						apData.getcWContor().getConsum().getInput().setText(
								((Double)(Double.valueOf(apData.getcWContor().getNewContor().getInput().getText()) -
								Double.valueOf(apData.getcWContor().getOldContor().getInput().getText()))).toString()
								);
						apData.getwWContor().getConsum().getInput().setText(
								((Double)(Double.valueOf(apData.getwWContor().getNewContor().getInput().getText()) -
								Double.valueOf(apData.getwWContor().getOldContor().getInput().getText()))).toString()
								);
						
					}
					
					//check is there are separated contors
					if(target.getIndividualWContors() &&(
							target.getcWCOldK() != Double.valueOf(apData.getcWCK().getOldContor().getInput().getText()) ||
							target.getcWCNewK() != Double.valueOf(apData.getcWCK().getNewContor().getInput().getText()) ||
							target.getwWCOldK() != Double.valueOf(apData.getwWCK().getOldContor().getInput().getText()) ||
							target.getwWCNewK() != Double.valueOf(apData.getwWCK().getNewContor().getInput().getText())
							)) {
						cUpdateFlag = true;
						target.setcWCOldK(Double.valueOf(apData.getcWCK().getOldContor().getInput().getText()));
						target.setcWCNewK(Double.valueOf(apData.getcWCK().getNewContor().getInput().getText()));
						
						target.setwWCOldK(Double.valueOf(apData.getwWCK().getOldContor().getInput().getText()));
						target.setwWCNewK(Double.valueOf(apData.getwWCK().getNewContor().getInput().getText()));
						
						//display new consum
						apData.getcWCK().getConsum().getInput().setText(
								((Double)(Double.valueOf(apData.getcWCK().getNewContor().getInput().getText()) -
								Double.valueOf(apData.getcWCK().getOldContor().getInput().getText()))).toString()
								);
						apData.getwWCK().getConsum().getInput().setText(
								((Double)(Double.valueOf(apData.getwWCK().getNewContor().getInput().getText()) -
								Double.valueOf(apData.getwWCK().getOldContor().getInput().getText()))).toString()
								);
					}
					
					//from comboboxes
					//warming contor
					String choise = apData.getCentralHGas().getData().getComboBox().getSelectedItem().toString();
					boolean ans = choise.equals("Da") ? true : false;
					target.setCentralHeating(ans);
					
					//gas contor
					choise = apData.getCentralHGas().getData2().getComboBox().getSelectedItem().toString();
					ans = choise.equals("Da") ? true : false;
					target.setGasContor(ans);
					
					//individual contors
					choise = apData.getIndCStatusP().getData().getComboBox().getSelectedItem().toString();
					ans = choise.equals("Da") ? true : false;
					target.setIndividualWContors(ans);
					
					//status plata
					choise = apData.getIndCStatusP().getData2().getComboBox().getSelectedItem().toString();
					ans = choise.equals("Platit") ? true : false;
					target.setPayedStatus(ans);
					
					//update the contor last update date if contors were changed
					if(cUpdateFlag) {
						//get the date
						LocalDate date = LocalDate.now();
						java.sql.Date sqlDate = new java.sql.Date(date.getYear()-1900,date.getMonthValue()-1,date.getDayOfMonth());
					
						target.setContorUpdateDate(sqlDate);
					}
					//check if e-mail was changed
					if(!apData.getEmailPhone().getData().getInput().getText().equals(target.getEmail())) {
						//send e-mail to inform new user and old user about the changes
						//get a new password for the new account
						String password = (new RandomPassword()).generatePassword().toString();
						//get the old user to inform him/her about the changes
						String oldUser = target.getEmail();
						
						//set the new e-mail and password
						target.setEmail(apData.getEmailPhone().getData().getInput().getText());
						target.setPasswd(password);
						
						//inform user about sending the emails
						JOptionPane.showMessageDialog(null, "Se v-or trimite informatiile de conectare catre noul cont.\n"
								+ "Se v-a transmite vechiului locatar un mesaj de informare.\n"
								+ "Va rugam apasati 'Ok' si asteptati mesajul scris de confirmare...",
								"Dialog",
								JOptionPane.INFORMATION_MESSAGE);
						//inform the old owner
						sendChanges(oldUser);
						
						//send the connection informations to the new user
						(new EmailSender()).sendToAsync(apData.getEmailPhone().getData().getInput().getText(), password);
						
						//confirmation message
						JOptionPane.showMessageDialog(null, "E-mail-urile au fost trimise!",
								"Dialog",
								JOptionPane.INFORMATION_MESSAGE);
					}
					
					//refresh the list of apartments
					for(Apartment ap : association.getApartments()) {
						if(ap.getId() == target.getId()) {
							ap = target;
						}
					}
					//writeApartmentToDB(target);
					Database.getInstance().updateApartment(target);
					
					//refresh the notice board
					((AdminAccount)frame).noticeBoardPanel = new NoticeBoardPanel(association, association.getAdmin(),
							frame);
					// refresh the home panel
					((AdminAccount)frame).homePanel = new HomePanel(association.getAdmin(), association,
							frame);
				}
			}
		};
		
		return action;
	}
	
	/**
	 * Inform the old user that his/her email no longer exists in our databases 
	 * @param oldUser the user to be informed
	 */
	private void sendChanges(String oldUser) {
		EmailSender email = new EmailSender();
		email.sendChangesToAsync(oldUser);
		
	}
	
	/**
	 * Makes the action that let the admin to edit the information.
	 * @return the action that let the admin to edit the information.
	 */
	private ActionListener modifyAction() {
		
	ActionListener action =	new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mark that saved
				apData.set_backgroundColor(new Color(39,166,0));
				//setting editable permisions
				apData.getEmailPhone().getData().getInput().setEditable(true);
				apData.getEmailPhone().getData2().getInput().setEditable(false);
				
				apData.getAreaPeopleNo().getData().getInput().setEditable(true);
				apData.getAreaPeopleNo().getData2().getInput().setEditable(true);
				
				apData.getOverdueLastUpdate().getData().getInput().setEditable(true);
				apData.getOverdueLastUpdate().getData2().getInput().setEditable(false);
				
				apData.getcWContor().getOldContor().getInput().setEditable(true);
				apData.getcWContor().getNewContor().getInput().setEditable(true);
				
				apData.getwWContor().getOldContor().getInput().setEditable(true);
				apData.getwWContor().getNewContor().getInput().setEditable(true);
				
				if(target.getIndividualWContors()) {
					apData.getwWCK().getOldContor().getInput().setEditable(true);
					apData.getwWCK().getNewContor().getInput().setEditable(true);
					
					apData.getcWCK().getOldContor().getInput().setEditable(true);
					apData.getcWCK().getNewContor().getInput().setEditable(true);
				}
				buttons.remove(saveButtonP);
				
				//check if paying status update panel is visible
				boolean flag = false;
				for(Object o : buttons.getComponents()) {
					if(o.equals(payingStatus)) {
						flag = true;
					}
				}
				//reset size (to be shown in center)
				if(!flag) {
					buttons.setPreferredSize(new Dimension(200,100));
					buttons.setMaximumSize(new Dimension(200,100));
				}

				buttons.add(saveButtonP,1);
				frame.repaint();
				frame.pack();
			}
		};
		return action;
	}
	
	/**
	 * Makes the action for chooseing the apartment for which the data will be shown.
	 * @param association the current association.
	 * @return the action for chooseing the apartment for which the data will be shown.
	 */
	private ActionListener apChooseAction(Association association) {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String option = apChoose.getSelectedItem().toString();
				
				//get the apartment to desplay data
				target = null;
				for(Apartment ap : association.getApartments()) {
					if(ap.getName().equals(option)) {
						target = ap;
						break;
					}
				}
				apDataLine.remove(apData);
				frame.repaint();
				apData = new ApartmentData(target,true);
				apDataLine.add(apData,1);
				buttons.remove(saveButtonP);
				buttons.remove(payingStatus);
				
				//reset size (to be shown in center)
				buttons.setPreferredSize(new Dimension(200,50));
				buttons.setMaximumSize(new Dimension(200,50));
				
				frame.pack();
			}
		};
		
		return action;
	}
}
