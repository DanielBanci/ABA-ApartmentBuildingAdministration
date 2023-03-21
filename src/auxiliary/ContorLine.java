package auxiliary;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import appInterface.ApartmentAccount;
import mainClasses.Apartment;

/**
A JPanel class that displays a title and three AccountData objects representing old and 
new meter readings, and optionally consumption.
The title reprezents whatever the contors aree for cold water or warm water
@author Ciprian Banci
@version 1.0
*/
public class ContorLine extends JPanel{
	private JLabel title;
	private AccountData oldContor;
	private AccountData newContor;
	private AccountData consum;
	public ContorLine() {super();}
	/**
	Constructs a ContorLine object with the specified title.
	The title is displayed at the left side of the panel
	The panel contains two AccountData objects for old and new meter readings, respectively.
	@param title the title to be displayed
	*/
	public ContorLine(String title) {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setOpaque(false);
		this.add(ApartmentAccount.makeEmptyPanel());
		this.title = new JLabel(title);
		this.title.setPreferredSize(new Dimension(70,30));

		oldContor = new AccountData("Contor vechi",false);
		oldContor.setMaximumSize(new Dimension(300,20));
		
		newContor = new AccountData("Contor nou",false);
		newContor.setMaximumSize(new Dimension(300,20));

		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(oldContor);
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(newContor);
		this.add(ApartmentAccount.makeEmptyPanel());
	}
	
	/**
	Constructs a ContorLine object with the specified apartment, title, and flag.
	The title is displayed at the left side of the panel.
	The panel contains three AccountData objects for old and new meter readings, and consumption, respectively.
	The input field for the old meter reading is set to non-editable.
	The input field for the consumption is set to non-editable.
	@param apartment the apartment for which the readings and consumption are displayed
	@param title the title to be displayed
	*/
	public ContorLine(Apartment apartment, String title) {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setOpaque(false);
		this.add(ApartmentAccount.makeEmptyPanel());
		this.title = new JLabel(title);
		this.title.setPreferredSize(new Dimension(70,30));

		oldContor = new AccountData("Contor vechi",false);
		oldContor.setMaximumSize(new Dimension(300,20));
		
		oldContor.getInput().setEditable(false);
		
		newContor = new AccountData("Contor nou",false);
		newContor.setMaximumSize(new Dimension(300,20));
		consum = new AccountData("Consum",false);
		consum.setMaximumSize(new Dimension(300,20));
		
		consum.getInput().setEditable(false);
		
		this.add(this.title);
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(oldContor);
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(newContor);
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(consum);
		this.add(ApartmentAccount.makeEmptyPanel());
	}
	public JLabel getTitle() {
		return title;
	}
	public void setTitle(JLabel title) {
		this.title = title;
	}
	public AccountData getOldContor() {
		return oldContor;
	}
	public void setOldContor(AccountData oldContor) {
		this.oldContor = oldContor;
	}
	public AccountData getNewContor() {
		return newContor;
	}
	public void setNewContor(AccountData newContor) {
		this.newContor = newContor;
	}
	public AccountData getConsum() {
		return consum;
	}
	public void setConsum(AccountData consum) {
		this.consum = consum;
	}
	
}
