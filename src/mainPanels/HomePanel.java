package mainPanels;

import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import appInterface.ApartmentAccount;
import auxiliary.ApInfoPanel;
import auxiliary.MessagePanel;
import auxiliary.PanelRound;
import calendar.CalendarPanel;
import chat.Chat;
import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.User;

/**
 * The home panel for a user.
 * It extends `MainPanel`.
 * @author Ciprian Banci
 * @version 1.0
 */
public class HomePanel extends MainPanel {
	private JPanel title;
	private JPanel content;
	private CalendarPanel calendar;
	private JFrame frame;
	
	/**
     * Constructs a new HomePanel for an administrator user.
     *
     * @param admin the administrator user
     * @param association the association that the user belongs to
     * @param frame the frame in which to display the panel
     */
	public HomePanel(final Admin admin, final Association association,JFrame frame) {
		super(); 
    	this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    	
    	this.frame = frame;
    	 
      	//title
        title = new Title("Acasa",null);
        
        this.add(ApartmentAccount.makeEmptyPanel());
        this.add(title);
        this.add(ApartmentAccount.makeEmptyPanel());
        this.add(makeUserNameLine(admin));
        //content
        JPanel content = new JPanel();
        content.setOpaque(false);
        
        content = makeAdminContent(admin,association);
        
        this.add(ApInfoPanel.emptyNoSizePanel());
        this.add(content);
        this.add(ApInfoPanel.emptyNoSizePanel());
        this.add(ApInfoPanel.emptyNoSizePanel());
		this.setOpaque(false);
	}
	
	/**
	 * Makes the left side of home panel for user admin.
	 * It contains the number of apartments that did not pay yet.
	 * And inform the admin about how the paying dates are saved
	 * @param admin the current admin
	 * @param association the admins association
	 * @return the left side of home panel for admin
	 */
	private PanelRound makeLeftSide(Admin admin,Association association) {
		PanelRound content = new PanelRound();
		//content.setLayout(new GridLayout(4,1));
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
		content.set_backgroundColor(Color.CYAN);
		content.setMaximumSize(new Dimension(300,330));
		content.setMinimumSize(new Dimension(300,330));
		content.setPreferredSize(new Dimension(300,330));
		
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(2,1));
		top.setOpaque(false);
		
		JLabel title = new JLabel("Informatii generale");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Times New Roman",Font.BOLD,20));
		
		JPanel titleP = new JPanel();
		titleP.setOpaque(false);
		titleP.setLayout(new GridLayout(1,1));
		titleP.add(title);
		
		content.add(titleP);
		//content.add(title);
		
		JTextArea apNoPay = new JTextArea(getNoPayedApartments(association) 
				+ (getNoPayedApartments(association) >= 20 ? " de " : "") + " apartamente nu si-au achitat\n"
				+ "cheltuielile pentru "
				+ "luna " + NoticeBoardPanel.getMonthName(NoticeBoardPanel.getPreviousDate(association.getnBLastUpdate())) 
				+ "!");
		apNoPay.setEditable(false);
		apNoPay.setOpaque(false);
		JPanel aux = new JPanel();
		aux.setLayout(new BoxLayout(aux,BoxLayout.X_AXIS));
		aux.setOpaque(false);
		
		apNoPay.setMaximumSize(new Dimension(100,70));
		apNoPay.setFont(new Font("Times New Roman",Font.PLAIN,16));
		aux.add(ApInfoPanel.emptyNoSizePanel());
		aux.add(apNoPay);
		aux.add(ApInfoPanel.emptyNoSizePanel());
		
		JTextArea apNoUpdatC = new JTextArea(getNoCUpdateApartments(association) + 
				(getNoCUpdateApartments(association) >= 20 ? " de" : "") + " apartamente nu si-au actualizat\n"
				+ "contoarele luna aceasta! ");
		apNoUpdatC.setEditable(false);
		apNoUpdatC.setOpaque(false);
		
		apNoUpdatC.setMaximumSize(new Dimension(100,70));
		apNoUpdatC.setFont(new Font("Times New Roman",Font.PLAIN,16));
		
		JPanel aux2 = new JPanel();
		aux2.setLayout(new BoxLayout(aux2,BoxLayout.X_AXIS));
		aux2.setOpaque(false);
		
		aux2.add(ApInfoPanel.emptyNoSizePanel());
		aux2.add(apNoUpdatC);
		aux2.add(ApInfoPanel.emptyNoSizePanel());
		top.add(aux);
		top.add(aux2);
		content.add(top);
		
		MessagePanel message = new MessagePanel(new Color(52,145,200),10,15,18,true);
		message.setLayout(new BorderLayout());
		message.add(ApartmentAccount.makeEmptyPanel(),BorderLayout.SOUTH);
		message.add(ApartmentAccount.makeEmptyPanel(),BorderLayout.WEST);
		message.add(ApartmentAccount.makeEmptyPanel(),BorderLayout.NORTH);
		message.add(ApartmentAccount.makeEmptyPanel(),BorderLayout.EAST);
		
		JPanel mesLine = new JPanel();
		mesLine.setLayout(new BoxLayout(mesLine,BoxLayout.X_AXIS));
		mesLine.setOpaque(false);
		
		JTextArea text = new JTextArea();
		text.setOpaque(false);
		text.setEditable(false);
		text.setText("Puteti seta datile de plata prin\n"
					+ "dublu click pe ziua dorita din\n"
					+ "calendarul din stanga.\n"
					+ "Pentru a deselecta dati dublu\n"
					+ "click inca o data.\n");
		text.setFont(new Font("Times New Roman",Font.PLAIN,16));
		message.add(text,BorderLayout.CENTER);
		message.setMinimumSize(new Dimension(250,250));
		message.setMaximumSize(new Dimension(250,250));

		mesLine.add(ApartmentAccount.makeEmptyPanel());
		mesLine.add(message);
		mesLine.add(ApartmentAccount.makeEmptyPanel());
		
		content.add(mesLine);
		
		return content;
	}
	
	private Integer getNoCUpdateApartments(Association association) {
		Integer no = 0;
		for(Apartment ap : association.getApartments()) {
			if(!checkMonth(ap.getContorUpdateDate(),new Date()) &&
				!checkMonth(ap.getContorUpdateDate(),association.getnBLastUpdate())) {
				no++;
			}
		}
		return no;
	}
	
	private boolean checkMonth(Date d1, Date d2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		int month1 = cal.get(Calendar.MONTH);
		cal.setTime(d2);
		int month2 = cal.get(Calendar.MONTH);
		
		return month1 == month2;
	}
	
	/**
	 * Returns the number of apartments that did not pay yet
	 * @param association the association of apartments
	 * @return the number of apartments that did not pay yet
	 */
	private Integer getNoPayedApartments(Association association) {
		Integer no = 0;
		for(Apartment ap : association.getApartments()) {
			if(!ap.getPayedStatus()) {
				no++; 
			}
		}
		return no;
	}
	
	 /**
     * Creates the content of the panel for an administrator user.
     *
     * @param admin the administrator user
     * @param association the association that the user belongs to
     * @return the content of the panel
     */
	private JPanel makeAdminContent(final Admin admin, final Association association) {
    	/*
    	 * make home content for user (admin)
    	 */
		JPanel content = new JPanel();
    	content.setLayout(new BoxLayout(content,BoxLayout.X_AXIS));
    	content.setOpaque(false);
		
		content.add(ApInfoPanel.emptyNoSizePanel());
		content.add(makeLeftSide(admin,association));
		content.add(ApInfoPanel.emptyNoSizePanel());
		
		calendar = new CalendarPanel(admin,association);
		content.add(calendar);
		content.add(ApInfoPanel.emptyNoSizePanel());

		return content;
    }
	
	/**
     * Constructs a new HomePanel for an apartment owner.
     *
     * @param apartment the apartment owner
     * @param association the association that the user belongs to
     * @param frame the frame in which to display the panel
     */
    public HomePanel(final Apartment apartment, final Association association, JFrame frame){
    	super();
    	this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    	this.frame = frame;

    	//title
        title = new Title("Acasa",null);
        
        this.add(ApartmentAccount.makeEmptyPanel());
        this.add(title);
        this.add(ApartmentAccount.makeEmptyPanel());
        
        //user name
      	this.add(makeUserNameLine(apartment));
      		
        //content
        content = makeContent(apartment,association);
		
		this.add(content);
		this.setOpaque(false);
    }
    
    /**

    Creates a content panel for the given apartment and association.
    @param apartment the apartment
    @param association the association
    @return a content panel with apartment information and a calendar
    */
    private JPanel makeContent(final Apartment apartment, final Association association) {
    	JPanel content = new JPanel();
    	content.setLayout(new BoxLayout(content,BoxLayout.X_AXIS));
    	content.setOpaque(false);
		
		content.add(ApInfoPanel.emptyNoSizePanel());
		content.add(makeApInfo(apartment,association));
		content.add(ApInfoPanel.emptyNoSizePanel());
		
		calendar = new CalendarPanel(apartment,association);
		content.add(calendar);
		content.add(ApInfoPanel.emptyNoSizePanel());

		return content;
    	
    }
    
    /**

    Creates a panel with the general information of the given apartment.
    @param apartment the apartment
    @param association the association
    @return a panel with the apartment general information
    */
    private PanelRound makeApInfo(final Apartment apartment, final Association association) {
    	PanelRound apInfo = new PanelRound();
    	//apInfo.setLayout(new GridLayout(2,1));
    	apInfo.setLayout(new BoxLayout(apInfo,BoxLayout.Y_AXIS));
		apInfo.setPreferredSize(new Dimension(300,330));
		apInfo.setMaximumSize(new Dimension(300,330));
		apInfo.set_backgroundColor(Color.CYAN);
		
		JLabel title = new JLabel("Informatii generale");
		title.setFont(new Font("Times New Roman",Font.BOLD,20));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel titlep = new JPanel();
		titlep.setLayout(new GridLayout(1,1));
		titlep.setOpaque(false);
		
		titlep.add(title);
		
		apInfo.add(titlep);
		
		JLabel monthBill = new JLabel("Suma de plata luna " + 
				NoticeBoardPanel.getMonthName(NoticeBoardPanel.getPreviousDate(association.getnBLastUpdate())) +
				":    " + ((ApartmentAccount)frame).noticeBoardPanel.getTable().getTotalPay(apartment));
		monthBill.setFont(new Font("Times New Roman",Font.PLAIN,17));
		monthBill.setHorizontalAlignment(JLabel.CENTER);

		//month bill status
		JLabel monthBillStatus = new JLabel("Status plata:   " + (apartment.getPayedStatus() ? "Platit" : "Neplatit") );
		monthBillStatus.setFont(new Font("Times New Roman",Font.PLAIN,17));
		monthBillStatus.setHorizontalAlignment(JLabel.CENTER);

		//contors update status
		JLabel contorsStatus = new JLabel("Ultima act. a contoarelor: " /*+
				apartment.getContorUpdateDate().toString()*/);
		JLabel l = new JLabel(apartment.getContorUpdateDate().toString());
		l.setFont(new Font("Times New Roman",Font.PLAIN,17));
		l.setHorizontalAlignment(JLabel.CENTER);
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setLayout(new GridLayout(5,1));
		contorsStatus.setFont(new Font("Times New Roman",Font.PLAIN,17));
		contorsStatus.setHorizontalAlignment(JLabel.CENTER);
		
		p.add(contorsStatus);
		p.add(l);
		//apInfo.add(l2);
		p.add(monthBill);
		p.add(monthBillStatus);
		apInfo.add(p);

		return apInfo;
    }
    
	/**
	 * Return a panel with the name of the current user.
	 * @param user
	 * @return a panel with the name of the current user.
	 */
    private JPanel makeUserNameLine(User user) {
    	/*
    	 * makes the line with user name
    	 */
    	JPanel content = new JPanel();
    	content.setLayout(new BoxLayout(content,BoxLayout.X_AXIS));
    	BufferedImage img = null;
    	try {
	        img = ImageIO.read(getClass().getResource("/userName.jpg"));
	      } catch (Exception ex) {
	        System.out.println(ex);
	      }
    	PanelRound cLeft = new PanelRound(img);
		cLeft.setLayout(new GridLayout(1,1));
		cLeft.setPreferredSize(new Dimension(100,70));
		cLeft.setMaximumSize(new Dimension(3000,70));
		JLabel userName = new JLabel(user.getName());
		userName.setHorizontalAlignment(JLabel.CENTER);
		userName.setFont(new Font("Times New Roman",Font.BOLD,20));
		userName.setForeground(Color.BLACK);
		cLeft.add(userName);
		JPanel empty = new JPanel();
		empty.setOpaque(false);
		empty.setMaximumSize(new Dimension(3000,70));
		
		content.add(cLeft);
		content.add(empty);
		content.setOpaque(false);
		return content;
    }
 
}
