package appInterface;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import auxiliary.ApInfoPanel;
import auxiliary.ContorLine;
import auxiliary.PanelRound;
import auxiliary.TablePanel;
import chat.ChatPanel;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.Database;
import mainPanels.HomePanel;
import mainPanels.MainPanel;
import mainPanels.MenuBarPanel;
import mainPanels.ModifyAccountInfo;
import mainPanels.NoticeBoardPanel;
import mainPanels.Title;

/**
Represents a window for managing an apartment account
The window shows the main features of the apartment account such as
Home, Chat, Update panel, Ap info panel, Menu bar, etc.
It also allows updating the meters (water, gas, electricity) used in the apartment
@author Ciprian Banci
@version 1.0
*/
public class ApartmentAccount extends JFrame {

	public JPanel contentPane;
	public NoticeBoardPanel noticeBoardPanel;
	public ChatPanel chatPanel;
	public TablePanel table;
	public JPanel menuBarPanel;
	public JPanel updatePanel;
	public JPanel homePanel;
	public JPanel apInfoPanel;
	public ModifyAccountInfo modDataAcc;

	/**
	 * Launch the application for apartment.
	 * @param apartment The Apartment for which to create the window 
	 * @param association The Association which the apartment belongs to
	 */
	public static void lunch(final Apartment apartment,final Association association) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApartmentAccount frame = new ApartmentAccount(apartment, association);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param apartment The Apartment for which to create the window
	 * @param association The Association which the apartment belongs to
	 */
	public ApartmentAccount(Apartment apartment, Association association) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1100,700));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.X_AXIS));
		
		menuBarPanel = new MenuBarPanel(this,apartment);
		noticeBoardPanel = new NoticeBoardPanel(association,apartment,this);
		chatPanel = new ChatPanel(association,apartment,this);//makeChatWindow(association,apartment);
		updatePanel = makeUpdatePanel(association,apartment);
		homePanel = new HomePanel(apartment,association,this);
		apInfoPanel = new ApInfoPanel(association,apartment,this);
		modDataAcc = new ModifyAccountInfo(apartment);
		
		contentPane.add(menuBarPanel);
		contentPane.add(homePanel);
	}
	
	/**
	 * Creates the panel used for updating the water contors.
	 * @param apartment The Apartment for which to create the window
	 * @param association The Association which the apartment belongs to
	 * @return the JPanel containing the update panel
	 */
	private JPanel makeUpdatePanel(final Association association,final Apartment apartment) {

		JPanel updatePanel = new MainPanel();
		updatePanel.setLayout(new BoxLayout(updatePanel,BoxLayout.Y_AXIS));
		updatePanel.add(makeEmptyPanel()); 
		
		//title
		JPanel titlePanel = new Title("Actualizare contoare",null);
		titlePanel.setMaximumSize(new Dimension(4000,100));
		titlePanel.setPreferredSize(new Dimension(100,100));
		updatePanel.add(titlePanel);
		JPanel first = new JPanel();
		first.setOpaque(false);
		updatePanel.add(first);
		
		//contors bath or just the contors if not separated
		PanelRound contorsBath = new PanelRound();
		contorsBath.setLayout(new BoxLayout(contorsBath,BoxLayout.Y_AXIS));
		contorsBath.add(ApartmentAccount.makeEmptyPanel());
		
		//check if there are separated contors
		if(apartment.getIndividualWContors()) {
			JPanel titlep = new JPanel();
			titlep.setLayout(new BoxLayout(titlep,BoxLayout.X_AXIS));
			titlep.setOpaque(false);
			JLabel titleCon = new JLabel("Contoare baie");
			titleCon.setFont(new Font("Times New Roman",Font.BOLD,20));
			
			titlep.add(makeEmptyPanel());
			titlep.add(titleCon);
			titlep.add(makeEmptyPanel());
			contorsBath.add(titlep);
			contorsBath.add(ApartmentAccount.makeEmptyPanel());
		}
		
		//warm contor bath
		//JPanel wCLine = makeContorLine(apartment);
		final ContorLine wCLine = new ContorLine(apartment,"Apa calda");
		contorsBath.add(wCLine);
		contorsBath.add(ApartmentAccount.makeEmptyPanel());
		
		final ContorLine cCLine = new ContorLine(apartment,"Apa rece");
		contorsBath.add(cCLine);
		
		updatePanel.add(contorsBath);
		updatePanel.add(makeEmptyPanel());
		
		final ContorLine wCKLine = new ContorLine(apartment,"Apa calda");//null;
		final ContorLine cCKLine = new ContorLine(apartment,"Apa rece");//null;
		
		//kitchen contors if any
		if(apartment.getIndividualWContors()) {
			PanelRound contorsK = new PanelRound();
			contorsK.setLayout(new BoxLayout(contorsK,BoxLayout.Y_AXIS));
			contorsK.add(ApartmentAccount.makeEmptyPanel());
			JPanel titlep2 = new JPanel();
			titlep2.setLayout(new BoxLayout(titlep2,BoxLayout.X_AXIS));
			titlep2.setOpaque(false);
			JLabel titleCon2 = new JLabel("Contoare bucatarie");
			titleCon2.setFont(new Font("Times New Roman",Font.BOLD,20));
			
			titlep2.add(makeEmptyPanel());
			titlep2.add(titleCon2);
			titlep2.add(makeEmptyPanel());
			
			
			contorsK.add(titlep2);
			contorsK.add(ApartmentAccount.makeEmptyPanel());
			
			contorsK.add(wCKLine);
			contorsK.add(ApartmentAccount.makeEmptyPanel());
			
			contorsK.add(cCKLine);
			
			updatePanel.add(contorsK);
			updatePanel.add(makeEmptyPanel());
		}
		
		//if contor was updated this month display the new value
		if(sameMonth(apartment.getContorUpdateDate(),new Date())) {
			//contors/bath contors
			//warm water
			wCLine.getOldContor().getInput().setText(apartment.getwWContorOld().toString());
			wCLine.getNewContor().getInput().setText(apartment.getwWContorNew().toString());
			
			//cold water
			cCLine.getOldContor().getInput().setText(apartment.getcWContorOld().toString());
			cCLine.getNewContor().getInput().setText(apartment.getcWContorNew().toString());
			
			Double wConsum = Math.round((apartment.getwWContorNew() - apartment.getwWContorOld()) * 100)/100.0;
			Double cConsum = Math.round((apartment.getcWContorNew() - apartment.getcWContorOld()) * 100)/100.0;
			wCLine.getConsum().getInput().setText(wConsum.toString());
			cCLine.getConsum().getInput().setText(cConsum.toString());
			
			//contors kitchen
			if(apartment.getIndividualWContors()) {
				//warm water
				wCKLine.getOldContor().getInput().setText(apartment.getwWCOldK().toString());
				wCKLine.getNewContor().getInput().setText(apartment.getwWCNewK().toString());
				
				//cold water
				cCKLine.getOldContor().getInput().setText(apartment.getcWCOldK().toString());
				cCKLine.getNewContor().getInput().setText(apartment.getcWCNewK().toString());
				
				wConsum = Math.round((apartment.getwWCNewK() - apartment.getwWCOldK()) * 100)/100.0;
				cConsum = Math.round((apartment.getcWCNewK() - apartment.getcWCOldK()) * 100)/100.0;
				wCKLine.getConsum().getInput().setText(wConsum.toString());
				cCKLine.getConsum().getInput().setText(cConsum.toString());
			}
			
			//calculate the consum
		}else {
			//if not put the new values as old and let space for user to write their input
			wCLine.getOldContor().getInput().setText(apartment.getwWContorNew().toString());
			wCLine.getNewContor().getInput().setText("");
			
			cCLine.getOldContor().getInput().setText(apartment.getwWContorNew().toString());
			cCLine.getNewContor().getInput().setText("");
			
			if(apartment.getIndividualWContors()) {
				wCKLine.getOldContor().getInput().setText(apartment.getwWCNewK().toString());
				wCKLine.getNewContor().getInput().setText("");
				cCKLine.getOldContor().getInput().setText(apartment.getwWCNewK().toString());
				cCKLine.getNewContor().getInput().setText("");
			}
		}
		
		//button
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new GridLayout(1,3));
		buttonPanel.add(makeEmptyPanel());
		buttonPanel.setMaximumSize(new Dimension(3000,100));
		
		JButton updateButton = new JButton("Actualizare");
		buttonPanel.add(updateButton);
		buttonPanel.add(makeEmptyPanel());
		final ApartmentAccount frame = this;
		
		/**
		 Action for update button.
		 Check the input and save the new values if correct.
		 */
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//verify input
				if(cCLine.getNewContor().getInput().getText().isEmpty() || 
					cCLine.getNewContor().getInput().getText().isEmpty() ||
					(apartment.getIndividualWContors() && (
					cCKLine.getNewContor().getInput().getText().isEmpty() || 
					wCKLine.getNewContor().getInput().getText().isEmpty()) ) ) {
	    			JOptionPane.showMessageDialog(new JFrame(), "Toate campurile trebuie completate!", "Eroare", 
	    					JOptionPane.ERROR_MESSAGE);
	    		}else if(!AdminAccount.isNumeric(cCLine.getNewContor().getInput().getText()) ||
	    			!AdminAccount.isNumeric(wCLine.getNewContor().getInput().getText()) ||
	    			(apartment.getIndividualWContors() && (
	    			!AdminAccount.isNumeric(cCKLine.getNewContor().getInput().getText()) ||
	    			!AdminAccount.isNumeric(wCKLine.getNewContor().getInput().getText()) ) ) ){
	    	JOptionPane.showMessageDialog(new JFrame(), "Toate campurile trebuie sa contina doar cifre.\n "
	    			+ "'.' este folosit ca delimitator de zecimale!", "Eroare", JOptionPane.ERROR_MESSAGE);
	    }else if(//check if old contor are at leats equal with the new contor
				Double.valueOf(cCLine.getNewContor().getInput().getText()) <
				Double.valueOf(apartment.getcWContorNew()) ||
				Double.valueOf(wCLine.getNewContor().getInput().getText()) <
				Double.valueOf(apartment.getwWContorNew())|| 
				(apartment.getIndividualWContors() && (
				Double.valueOf(cCKLine.getNewContor().getInput().getText()) <
				Double.valueOf(apartment.getcWCNewK()) ||
				Double.valueOf(wCKLine.getNewContor().getInput().getText()) <
				Double.valueOf(apartment.getwWCNewK()) ) )
				){
				JOptionPane.showMessageDialog(null, "Contoarele noi trebuie sa fie cel putin "
						+ "egale cu cele vechi!","Eroare",JOptionPane.ERROR_MESSAGE);
				frame.remove(frame.updatePanel);
				frame.updatePanel = makeUpdatePanel(association, apartment);
				frame.add(frame.updatePanel);
				frame.pack();
		}else{
	    	//input ok
	    	Double wc = Math.round(Double.valueOf(wCLine.getNewContor().getInput().getText())*100)/100.0;
	    	Double cc = Math.round(Double.valueOf(cCLine.getNewContor().getInput().getText())*100)/100.0;
	    	
	    	//new values become old values
	    	apartment.setcWContorOld(apartment.getcWContorNew());
	    	apartment.setwWContorOld(apartment.getwWContorNew());
	    	
		  	//retain the new values as new
	    	apartment.setwWContorNew(wc);
	    	apartment.setcWContorNew(cc);
	    	
	    	//compute the consum and display it
	    	Double coldC = apartment.getcWContorNew() - apartment.getcWContorOld();
	    	Double warmC = apartment.getwWContorNew() - apartment.getwWContorOld();
	    	
	    	wCLine.getConsum().getInput().setText(warmC.toString());
	    	cCLine.getConsum().getInput().setText(coldC.toString());

	    	cCLine.getNewContor().getInput().setText(apartment.getcWContorNew().toString());
	    	wCLine.getNewContor().getInput().setText(apartment.getwWContorNew().toString());
	    	cCLine.getOldContor().getInput().setText(apartment.getcWContorOld().toString());
	    	wCLine.getOldContor().getInput().setText(apartment.getwWContorOld().toString());

	    	if(apartment.getIndividualWContors()) {
	    		//contors
	    		Double wcK = Math.round(Double.valueOf(wCKLine.getNewContor().getInput().getText())*100)/100.0;
		    	Double ccK = Math.round(Double.valueOf(cCKLine.getNewContor().getInput().getText())*100)/100.0;
		    	
		    	apartment.setcWCOldK(apartment.getcWCNewK());
		    	apartment.setwWCOldK(apartment.getwWCNewK());
		    	
		    	apartment.setcWCNewK(ccK);
		    	apartment.setwWCNewK(wcK);
		    	
		    	//consum
		    	coldC = apartment.getcWCNewK() - apartment.getcWCOldK();
		    	warmC = apartment.getwWCNewK() - apartment.getwWCOldK();
		    	
		    	//display consum
		    	wCKLine.getConsum().getInput().setText(warmC.toString());
		    	cCKLine.getConsum().getInput().setText(coldC.toString());
		    	
		    	//display new contor
		    	cCKLine.getNewContor().getInput().setText(apartment.getcWCNewK().toString());
		    	wCKLine.getNewContor().getInput().setText(apartment.getwWCNewK().toString());
		    	
		    	//the old contor
		    	cCKLine.getOldContor().getInput().setText(apartment.getcWCOldK().toString());
		    	wCKLine.getOldContor().getInput().setText(apartment.getwWCOldK().toString());
	    	}
	    	
	    	//set the new date
	    	LocalDate date = LocalDate.now();
			java.sql.Date sqlDate = new java.sql.Date(date.getYear()-1900,date.getMonthValue()-1,date.getDayOfMonth());
			
			apartment.setContorUpdateDate(sqlDate);
	    	
	    	//write new informations to databases
			Database.getInstance().updateApartment(apartment);
	    	
	    	//refresh the information in association.apartments
	    	for(Apartment ap : association.getApartments()) {
	    		if(ap.getId() == apartment.getId()) {
	    			ap.setcWContorOld(apartment.getcWContorOld());
	    			ap.setwWContorOld(apartment.getwWContorOld());
	    			ap.setcWContorNew(apartment.getcWContorNew());
	    			ap.setwWContorNew(apartment.getwWContorNew());
	    			
	    		}
    	}
		    	
	    	//update the windows
	    	frame.noticeBoardPanel = null;
	    	frame.noticeBoardPanel = new NoticeBoardPanel(association,apartment,frame);
	    	JOptionPane.showMessageDialog(new JFrame(), "Contoarele au fost actualizate",
	    			"Dialog", JOptionPane.INFORMATION_MESSAGE);
	    	
			}
			}
		});

		updatePanel.add(buttonPanel);
		JPanel last = new JPanel();
		last.setOpaque(false);
		updatePanel.add(last);
		
		return updatePanel;
	}
	
	/**
	 * Check if the dates are from the save month
	 * @param d1 the first date
	 * @param d2 the second date
	 * @return true if d1 and d2 has the same month
	 */
	private boolean sameMonth(Date d1,Date d2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		cal1.setTime(d1);
		cal2.setTime(d2);
		
		return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	}
	
	/**
	 * Return an auxiliary panel with opaque false.
	 * @return an auxiliary panel with opaque false.
	 */
	public static JPanel makeEmptyPanel() {
    	JPanel empBill = new JPanel();
		empBill.setPreferredSize(new Dimension(10,10));
		empBill.setMaximumSize(new Dimension(10,10));
		//empBill.setMinimumSize(new Dimension(10,10));
		empBill.setOpaque(false);
		return empBill;
    }

}
