package mainPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import appInterface.ApartmentAccount;
import auxiliary.ApInfoPanel;
import auxiliary.MessagePanel;
import auxiliary.PanelRound;
import auxiliary.TablePanel;
import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;

import javax.swing.JFrame;

/**

The panel that displays the notice board panel of association.
@author Ciprian Banci
@version 1.0
*/
public class NoticeBoardPanel extends MainPanel{
	/*
	 * the notice board panel
	 */
	private TablePanel table; 
	private JFrame frame;
	private Title title;
	private boolean admin;

	/**
	Creates a new NoticeBoardPanel for a specific apartment, using the given association object and frame.
	The panel will display the apartment account, followed by a title, a line with association data,
	a table panel, and an additional last line with a message.
	@param association - the apartment association to display information about
	@param apartment - the apartment object for which the panel is created
	@param f - the frame to which the panel is added
	*/
	public NoticeBoardPanel(Association association, Apartment apartment, JFrame f) {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(ApartmentAccount.makeEmptyPanel());
		 
		frame = f;
		table = new TablePanel(apartment,association,frame);
		title = new Title("Avizier",null);
		
		admin = false;
		
		title.setPreferredSize(new Dimension(100,100));
        title.setMaximumSize(new Dimension(3000,100));
        title.setMinimumSize(new Dimension(100,100));
        this.add(title);
		
		//association data
        this.add(makeAssocDataLine(association));
        this.add(ApartmentAccount.makeEmptyPanel());
		
		JPanel tableLine = makeTableLine();
		
		this.add(tableLine);
		
		//last line
		JPanel lastLine = makeLastLineApartment();
		
		JPanel empty = new JPanel();
		empty.setOpaque(false); 
		empty.setMinimumSize(new Dimension(200,100));
		empty.setMaximumSize(new Dimension(200,300000));
		this.add(ApInfoPanel.emptyNoSizePanel());
	}
	
	/**
	 * Returns the panel with a message.
	 * @return the panel with a message.
	 */
	private JPanel makeLastLineApartment() {
		
		JPanel lastLine = new JPanel();
		lastLine.setLayout(new BoxLayout(lastLine,BoxLayout.X_AXIS));
		lastLine.setOpaque(false);
		
		JPanel message = new MessagePanel(Color.black,10,25,25,false);
		message.setBackground(new Color(219,133,50));
		message.setLayout(new GridLayout(1,1));
		message.setMinimumSize(new Dimension(300,200));
		message.setMaximumSize(new Dimension(300,200));
		message.setPreferredSize(new Dimension(300,200));
		
		lastLine.add(ApartmentAccount.makeEmptyPanel());
		JTextArea text = new JTextArea();
		text.setText("\n        Pentru orice nelamuliri puteti\n"
				+ "        scrie o sesizare in chat sau\n"
				+ "        puteti solicita detaliile prin\n"
				+ "        butonul din stanga.");
		text.setPreferredSize(new Dimension(100,100));
		text.setFont(new Font("Times New Roman",Font.PLAIN,16));
		message.add(text);
		text.setOpaque(false);
		//message.repaint();
		JPanel empLastLine = new JPanel();
		empLastLine.setOpaque(false);
		empLastLine.setMinimumSize(new Dimension(500,10));
		empLastLine.setMaximumSize(new Dimension(900,10));
		lastLine.add(empLastLine);
		lastLine.add(message);
		
		return lastLine;
	}
	
	/**

	Creates a new NoticeBoardPanel for a specific association, using the given association object and frame.
	The panel will display the association's title, followed by a line with association data, a table panel,
	and an empty last line.
	@param association - the apartment association to display information about
	@param admin - the administrator object for the association
	@param f - the frame to which the panel is added
	*/
	public NoticeBoardPanel(Association association, Admin admin, JFrame f) {
		super();
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(ApartmentAccount.makeEmptyPanel());
		
		frame = f;
		table = new TablePanel(admin,association,frame);
		title = new Title("Avizier",null);
		this.admin = true;
		
		//title
		title.setPreferredSize(new Dimension(300,100));
		title.setMinimumSize(new Dimension(30000,100));
		title.setMaximumSize(new Dimension(30000,100));
		this.add(title);
		
		//informations about the association
		JPanel assocDataLine = makeAssocDataLine(association);
		
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(assocDataLine);
		this.add(ApInfoPanel.emptyNoSizePanel());
		
		//table line
		JPanel tableLine = makeTableLine();
		
		this.add(tableLine);
		
		JPanel lastPanel = new JPanel(); //empty
		lastPanel.setOpaque(false);
		lastPanel.setMinimumSize(new Dimension(100,10));
		lastPanel.setMaximumSize(new Dimension(100,500));
		
		this.add(ApInfoPanel.emptyNoSizePanel());
	}
	
	/**
	 * Put the table in a panel that help us displaying it correctly.
	 * @return the table panel.
	 */
	private JPanel makeTableLine() {
		/*
		 * returns the table line
		 */
		JPanel tableLine = new JPanel();
		tableLine.setLayout(new BoxLayout(tableLine,BoxLayout.X_AXIS));
		tableLine.setOpaque(false);
		tableLine.add(ApartmentAccount.makeEmptyPanel());
		
		tableLine.add(table);
		tableLine.setMinimumSize(new Dimension(100,105));
		tableLine.add(ApartmentAccount.makeEmptyPanel());
		
		return tableLine;
	}
	
	/**
	 * Returns a line with association data, such as month of pay,
	 * name of association, adress and the administrator
	 * @param association the association for the data that will be displayed
	 * @return the line with association data.
	 */
	private JPanel makeAssocDataLine(Association association) {
		
		JPanel assocDataLine = new JPanel();
		assocDataLine.setLayout(new BoxLayout(assocDataLine,BoxLayout.X_AXIS));
		assocDataLine.setOpaque(false);
		assocDataLine.add(ApartmentAccount.makeEmptyPanel());
		
		BufferedImage img2 = null;
		    try {
			       img2 = ImageIO.read(getClass().getResource("/woodBackground.jpg"));
			  } catch (Exception ex) {
			       System.out.println(ex);
		  }
		JPanel associationData = new PanelRound(img2);
		associationData.setLayout(new BoxLayout(associationData,BoxLayout.Y_AXIS));
		
		associationData.setMaximumSize(new Dimension(500,100));
		associationData.setMinimumSize(new Dimension(500,100));
		associationData.setPreferredSize(new Dimension(500,100));
		
		associationData.add(ApartmentAccount.makeEmptyPanel());
		
		JLabel payMonth = new JLabel();
		payMonth.setText(makePayingMonth(association));
		
		JLabel assocName = new JLabel();
		assocName.setText("Asociatia: str. " + association.getStreet() + ", nr. " + association.getNumber() + 
				", bl. " + association.getBuilding() + ", sc. " + association.getStair());
		
		JLabel assocLocality = new JLabel();
		assocLocality.setText("Localitatea: " + association.getLocality() );
		
		JLabel assocDistrict = new JLabel("Judetul: " + association.getDistrict());
		
		payMonth.setFont(new Font("Times New Roman",Font.BOLD,18));
		payMonth.setHorizontalAlignment(JLabel.CENTER);
		
		assocDistrict.setFont(new Font("Times New Roman",Font.BOLD,18));
		assocDistrict.setHorizontalAlignment(JLabel.LEFT);
		
		assocName.setFont(new Font("Times New Roman",Font.BOLD,18));
		assocName.setHorizontalAlignment(JLabel.LEFT);
		
		assocLocality.setFont(new Font("Times New Roman",Font.BOLD,18));
		assocLocality.setHorizontalAlignment(JLabel.LEFT);
		
		associationData.add(payMonth);
		associationData.add(assocName);
		associationData.add(assocLocality);
		associationData.add(assocDistrict);
		
		if(!admin) {
		JLabel adm = new JLabel("Administrator: " + association.getAdmin().getName());
		adm.setFont(new Font("Times New Roman",Font.BOLD,18));
		adm.setHorizontalAlignment(JLabel.LEFT);
		
		associationData.add(ApartmentAccount.makeEmptyPanel());
		associationData.add(adm);
		associationData.add(ApartmentAccount.makeEmptyPanel());
		
		associationData.setMaximumSize(new Dimension(500,150));
		associationData.setMinimumSize(new Dimension(500,150));
		associationData.setPreferredSize(new Dimension(500,150));
		}

		associationData.setBackground(Color.ORANGE);

		
		assocDataLine.add(associationData);
		//assocDataLine.setMinimumSize(new Dimension(30000,250));
		
		JPanel emptyadl = new JPanel();
		emptyadl.setOpaque(false);
		emptyadl.setMinimumSize(new Dimension(10,10));
		emptyadl.setMaximumSize(new Dimension(2000,10));
		assocDataLine.add(emptyadl);
		
		return assocDataLine;
	}
	
	/**
	 * Returns a string that contain the date for which the notice board is shown
	 * @param association the association for the data that will be displayed
	 * @return a string reprezenting paying month
	 */
	private String makePayingMonth(Association association) {
		/*
		 * return previous month + year
		 */
		String content = null;
		
		Calendar c = Calendar.getInstance();
		c.setTime(association.getnBLastUpdate());
		
		c.add(Calendar.MONTH, -1);
		
		
		content = "                        " +//to be centered
				"Cheltuieli luna " +
				getMonthName(getPreviousDate(association.getnBLastUpdate())).toUpperCase() + 
				" " + c.get(Calendar.YEAR);
		
		return content;
	}
	
	/**
	 * Returns the previous date; a month earlyer from date
	 * @param date 
	 * @return a date reprezenting date - 1 month
	 */
	public static Date getPreviousDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		return c.getTime();
	}
	
	/** 
	 * Return a string reprezenting the date month.
	 * @param date the date from which we get the month
	 * @return a string reprezenting the date month.
	 */
	public static String getMonthName(Date date) {
		String month = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int m = cal.get(Calendar.MONTH) + 1;//starts from 0
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
	
	//setters & getters
	public TablePanel getTable() {
		return table;
	}
	public void setTable(TablePanel table) {
		this.table = table;
	}
}
