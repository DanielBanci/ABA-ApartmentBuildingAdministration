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
import auxiliary.ApartmentData;
import auxiliary.PanelRound;
import auxiliary.TablePanel;
import auxiliary.UpdateLine;

import chat.ChatPanel;

import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.Database;
import mainPanels.ChangeAdmin;
import mainPanels.HomePanel;
import mainPanels.MainPanel;
import mainPanels.MenuBarPanel;
import mainPanels.ModifyAccountInfo;
import mainPanels.NoticeBoardPanel;
import mainPanels.Title;

/**

The AdminAccount class is a graphical interface class for the admin of the application.
It extends the JFrame class to create a window that displays different panels of the application,
such as the home panel, notice board panel, chat panel, and the apartment information panel.
@author Ciprian Banci
@version 1.0
*/
public class AdminAccount extends JFrame {

	public JPanel contentPane;
	public static NoticeBoardPanel noticeBoardPanel;
	public ChatPanel chatPanel;
	public JPanel menuBarPanel;
	public JPanel updatePanel;
	public JPanel homePanel;
	public ApInfoPanel apInfoPanel;
	public ApartmentData apData;
	public Apartment target;
	public ModifyAccountInfo modDataAcc;
	public ChangeAdmin adminCh;
	

	/**
	 * Launch the application.
	 */
	public static void lunch(final Association association,final Admin admin) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminAccount frame = new AdminAccount(association,admin);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	Creates the AdminAccount window.
	@param association The association whose information will be displayed in the window.
	@param admin The admin user whose information will be displayed in the window.
	*/
	public AdminAccount(Association association,Admin admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setPreferredSize(new Dimension(1100,700));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.X_AXIS));
		
		menuBarPanel = new MenuBarPanel(this,admin);
		contentPane.add(menuBarPanel);
		
		noticeBoardPanel = new NoticeBoardPanel(association,admin,this);
		chatPanel = new ChatPanel(association,admin,this);
		updatePanel = makeUpdatePanel(association,admin);
		homePanel = new HomePanel(admin,association,this);
		apInfoPanel = new ApInfoPanel(association,admin,this);
		contentPane.add(homePanel);
		modDataAcc = new ModifyAccountInfo(admin);
		adminCh = new ChangeAdmin(admin,this);

		this.pack();
		this.setVisible(true);
		
	}
	
	/**
	Creates the panel used for updating the expenses of the association.
	@param association The association whose expenses will be updated.
	@param admin The admin user who will update the expenses.
	@return The panel used for updating the expenses of the association.
	*/
	private JPanel makeUpdatePanel(final Association association,final Admin admin) {

		updatePanel = new MainPanel();
		updatePanel.setLayout(new BoxLayout(updatePanel,BoxLayout.Y_AXIS));
		updatePanel.add(ApartmentAccount.makeEmptyPanel());
		
		//title
		JPanel titlePanel = new Title("Actualizare cheltuieli",null);
		updatePanel.add(titlePanel);
		updatePanel.add(ApartmentAccount.makeEmptyPanel());
		
		//data panel 
		JPanel dataPanel = new JPanel();
		dataPanel.setOpaque(false);
		//dataPanel.setLayout(new GridLayout(1,2));
		dataPanel.setLayout(new BoxLayout(dataPanel,BoxLayout.X_AXIS));
		
		JPanel leftContent = new JPanel();
		leftContent.setLayout(new BoxLayout(leftContent,BoxLayout.X_AXIS));
		leftContent.setOpaque(false);
		leftContent.add(ApartmentAccount.makeEmptyPanel());
		
		JPanel left = new PanelRound();
		left.setOpaque(false);

		left.setLayout(new GridLayout(9,1));

		JLabel leftTitle = new JLabel();
		leftTitle.setText("Sume totale");
		leftTitle.setFont(new Font("Times New Roman",Font.PLAIN,20));
		leftTitle.setHorizontalAlignment(JLabel.CENTER);
		left.add(leftTitle);
		
		final UpdateLine repairFund = new UpdateLine("Fond reparatii:");
		final UpdateLine rulFund = new UpdateLine("Fond rulment:");
		final UpdateLine updateFund = new UpdateLine("Fond imbunatatiri:");
		final UpdateLine salaries = new UpdateLine("Salarii:");
		final UpdateLine elect = new UpdateLine("Electricitate:");	
		final UpdateLine gas = new UpdateLine("Gaz:");
		final UpdateLine warming = new UpdateLine("Incalzire:");
		
		left.add(repairFund);
		left.add(rulFund);
		left.add(updateFund);
		left.add(salaries);
		left.add(elect);
		left.add(gas);
		left.add(warming);
		
		leftContent.add(left);
		dataPanel.add(leftContent);
		
		
		//right panel
		JPanel right = new PanelRound();
		right.setOpaque(false); 
		right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS));
		
		//right top
		JPanel rightTop = new JPanel();
		rightTop.setOpaque(false);
		rightTop.setLayout(new GridLayout(9,1));
		JLabel rightTopName1 = new JLabel();
		rightTopName1.setText("Pret per contor");
		rightTopName1.setFont(new Font("Times New Roman",Font.PLAIN,20));
		rightTopName1.setHorizontalAlignment(JLabel.CENTER);
		rightTop.add(rightTopName1);
		final UpdateLine warmWater = new UpdateLine("Apa calda:");
		
		rightTop.add(warmWater);
		final UpdateLine coldWater = new UpdateLine("Apa rece:");
				
		rightTop.add(coldWater);
				
		JLabel rightTopName2 = new JLabel();
		rightTopName2.setText("Pret per persoana");
		rightTopName2.setFont(new Font("Times New Roman",Font.PLAIN,20));
		rightTopName2.setHorizontalAlignment(JLabel.CENTER);
		rightTop.add(rightTopName2);
		final UpdateLine trash = new UpdateLine("Gunoi:");
		
		rightTop.add(trash);
	
		right.add(rightTop);
		leftContent.add(left);
		leftContent.add(ApartmentAccount.makeEmptyPanel());
		dataPanel.add(leftContent);

		dataPanel.add(right);

		updatePanel.add(ApInfoPanel.emptyNoSizePanel());
		updatePanel.add(dataPanel);
		updatePanel.add(ApInfoPanel.emptyNoSizePanel());
		
		dataPanel.setMinimumSize(new Dimension(400,400));
		dataPanel.setPreferredSize(new Dimension(400,400));
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new GridLayout(1,3));
		bPanel.setOpaque(false);
		bPanel.setMaximumSize(new Dimension(300,30));
		JButton updateButton = new JButton("Actualizare");

		updateButton.setPreferredSize(new Dimension(100,30));
		bPanel.add(ApartmentAccount.makeEmptyPanel());
		bPanel.add(updateButton);
		bPanel.add(ApartmentAccount.makeEmptyPanel());
		right.add(bPanel);
		right.add(ApartmentAccount.makeEmptyPanel());
		updateButton.setHorizontalAlignment(JButton.CENTER);
		
		AdminAccount frame = this;
		
		/**
		 Action for update button.
		 Check the input to be correct and update the apropriate notice board
		 */
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//verify input
				if(repairFund.getData().getInput().getText().isEmpty() || 
						rulFund.getData().getInput().getText().isEmpty() || 
						updateFund.getData().getInput().getText().isEmpty() || 
						salaries.getData().getInput().getText().isEmpty() || 
						elect.getData().getInput().getText().isEmpty() || 
						warming.getData().getInput().getText().isEmpty() || 
						warmWater.getData().getInput().getText().isEmpty() || 
						coldWater.getData().getInput().getText().isEmpty() || 
						trash.getData().getInput().getText().isEmpty() || 
						gas.getData().getInput().getText().isEmpty() ) {
		
	    			JOptionPane.showMessageDialog(new JFrame(), "Toate campurile trebuie completate!", "Eroare", 
	    					JOptionPane.ERROR_MESSAGE);
	    		}else if(
	    				!isNumeric(repairFund.getData().getInput().getText()) || 
	    				!isNumeric(rulFund.getData().getInput().getText()) || 
	    				!isNumeric(updateFund.getData().getInput().getText()) || 
	    				!isNumeric(salaries.getData().getInput().getText()) || 
	    				!isNumeric(elect.getData().getInput().getText()) || 
	    				!isNumeric(warming.getData().getInput().getText()) || 
	    				!isNumeric(warmWater.getData().getInput().getText()) || 
	    				!isNumeric(coldWater.getData().getInput().getText()) || 
	    				!isNumeric(trash.getData().getInput().getText()) || 
	    				!isNumeric(gas.getData().getInput().getText()) )
	    				{
	    	JOptionPane.showMessageDialog(new JFrame(), "Toate campurile trebuie sa contina doar cifre.\n "
	    			+ "'.' este folosit ca delimitator de zecimale!", "Eroare", JOptionPane.ERROR_MESSAGE);
	    }else {
	    	//input ok
	    	//getting data
	    	
	    	Double repPrice = Double.valueOf(repairFund.getData().getInput().getText());
	    	Double rulPrice = Double.valueOf(rulFund.getData().getInput().getText());
	    	Double updatePrice = Double.valueOf(updateFund.getData().getInput().getText());
	    	Double salPrice = Double.valueOf(salaries.getData().getInput().getText());
	    	Double electPrice = Double.valueOf(elect.getData().getInput().getText());
	    	Double warmingPrice = Double.valueOf(warming.getData().getInput().getText());
	    	Double warmWPrice = Double.valueOf(warmWater.getData().getInput().getText());
	    	Double coldWPrice = Double.valueOf(coldWater.getData().getInput().getText());
	    	Double trashPrice = Double.valueOf(trash.getData().getInput().getText());
	    	Double gasPrice = Double.valueOf(gas.getData().getInput().getText());
	    	
	    	// round data to 2 zecimals
	    	repPrice = Math.round(repPrice * 100) / 100.0;
	    	rulPrice = Math.round(rulPrice * 100) / 100.0;
	    	updatePrice = Math.round(updatePrice * 100) / 100.0;
	    	salPrice = Math.round(salPrice * 100) / 100.0;
	    	electPrice = Math.round(electPrice * 100) / 100.0;
	    	warmingPrice = Math.round(warmingPrice * 100) / 100.0;
	    	warmWPrice = Math.round(warmWPrice * 100) / 100.0;
	    	coldWPrice = Math.round(coldWPrice * 100) / 100.0;
	    	trashPrice = Math.round(trashPrice * 100) / 100.0;
	    	gasPrice = Math.round(gasPrice * 100) / 100.0;
	    	
	    	//retain the new information
	    	association.setRepairFund(repPrice);
	    	association.setRulFund(rulPrice);
	    	association.setUpdateFund(updatePrice);
	    	association.setSalaries(salPrice);
	    	association.setElectBill(electPrice);
	    	association.setWarmingBill(warmingPrice);
	    	association.setwWaterPrice(warmWPrice);
	    	association.setcWaterPrice(coldWPrice);
	    	association.setTrashPrice(trashPrice);
	    	association.setGasBill(gasPrice);
	    	
	    	//calculate the overdue price 
	    	//and setting paying status to false
	    	for(Apartment ap : association.getApartments()) {
	    		ap.setOverdue(calculateOverdue(ap));
	    		ap.setPayedStatus(false);
	    	}
	    	
	    	Database.getInstance().UpdateAllData(association);

	    	//confirmation message
	    	JOptionPane.showMessageDialog(new JFrame(), "Avizierul a fost actualizat!", 
	    			"Dialog", JOptionPane.INFORMATION_MESSAGE);
	    	frame.noticeBoardPanel = new NoticeBoardPanel(association, admin, frame);
	    }
			}
		});
		//setting the old values
		repairFund.getData().getInput().setText(association.getRepairFund().toString());
		updateFund.getData().getInput().setText(association.getUpdateFund().toString());
		rulFund.getData().getInput().setText(association.getRulFund().toString());
		salaries.getData().getInput().setText(association.getSalaries().toString());
		elect.getData().getInput().setText(association.getElectBill().toString());
		warming.getData().getInput().setText(association.getWarmingBill().toString());
		warmWater.getData().getInput().setText(association.getwWaterPrice().toString());
		coldWater.getData().getInput().setText(association.getcWaterPrice().toString());
		trash.getData().getInput().setText(association.getTrashPrice().toString());
		gas.getData().getInput().setText(association.getGasBill().toString());
		
		
		return updatePanel;
	}
	
	/**
	This method calculates the overdue amount for a given apartment.
	If the apartment is unpaid, it will add the total month value, the last overdue value and penalties.
	@param apartment the apartment for which to calculate the overdue amount
	@return the overdue amount for the apartment
	*/
	public static Double calculateOverdue(Apartment apartment) {
		Double overdue = 0.0;
		
		if(!apartment.getPayedStatus()){//unpayed
			overdue += Double.valueOf(noticeBoardPanel.getTable().columnData[apartment.getNo()][15]);//total month
			//add the last valeu of the overdue
			overdue += apartment.getOverdue();
			//add penalties
			overdue += Double.valueOf(noticeBoardPanel.getTable().columnData[apartment.getNo()][16]);
		}
		
		return overdue;
	}
	
	/**
	This method checks if a given string can be parsed as a numeric value.
	@param str the string to check
	@return true if the string can be parsed as a numeric value, false otherwise
	*/
	public static boolean isNumeric(String str) {
	    try {
	        double d = Double.parseDouble(str);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	
	/**
	This method returns the name of the month corresponding to a given date.
	@param date the date for which to retrieve the month name
	@return the name of the month corresponding to the given date
	*/
	public static String getCurrentMonth(Date date) {
		String month = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int m = cal.get(Calendar.MONTH);
		m ++;//month starts from 0 in calendar
		switch(m) {
		case 1: month = "ianuarie"; break;
		case 2: month = "februarie"; break;
		case 3: month = "martie"; break;
		case 4: month = "aprilie"; break;
		case 5: month = "mai"; break;
		case 6: month = "iunie"; break;
		case 7: month = "iulie"; break;
		case 8: month = "august"; break;
		case 9: month = "septembrie"; break;
		case 10: month = "octombrie"; break;
		case 11: month = "noiembrie"; break;
		case 12: month = "decembrie"; break;
					
		}
		
		return month; 
	}

}
