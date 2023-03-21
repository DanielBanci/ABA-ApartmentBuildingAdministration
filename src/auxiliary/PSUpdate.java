package auxiliary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import appInterface.AdminAccount;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.Database;
import mainPanels.HomePanel;

/**
The PSUpdate class represents a graphical user interface for updating the payment status of an apartment.
It extends the JPanel class and has a JCheckBox and a JLabel to display the payment status of the apartment.
@author Ciprian Banci
@version 1.0
*/
public class PSUpdate extends JPanel{
	private JCheckBox checkBox;
	private JLabel status;
	private JFrame frame;
	private Association association;
	
	/**
	Constructs a new PSUpdate object.  
	@param apartment the apartment object to update
	@param f the JFrame object that contains the panel
	*/
	public PSUpdate(Apartment apartment,Association association, JFrame f) {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		frame = f;
		this.association = association;
		
		checkBox = new JCheckBox(apartment.getName());
		status = new JLabel();
		
		//setting initial values
		if(apartment.getPayedStatus()) {
			checkBox.setSelected(true);
			status.setText("Platit");
		}else {
			checkBox.setSelected(false);
			status.setText("Neplatit");
		}
		
		this.add(checkBox);
		this.add(status);
		
		checkBox.addActionListener(checkBoxAction(apartment));
	}
	
	/**
	Creates an ActionListener object to be added to the checkBox component.
	This ActionListener updates the payment status of the apartment and refreshes the database.
	@param apartment the apartment object to update
	@return an ActionListener object
	*/
	private ActionListener checkBoxAction(Apartment apartment) {
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBox.isSelected()) {//payed
					//verify if not saved
					if(!apartment.getPayedStatus()) {
						apartment.setPayedStatus(true);
						status.setText("Platit");
						Database.getInstance().refreshPayStatusDB(apartment);
						((AdminAccount)frame).homePanel = new HomePanel(association.getAdmin(), association, frame);
					}
				}else {//not payed
					//verify if saved
					if(apartment.getPayedStatus()) {
						apartment.setPayedStatus(false);
						status.setText("Neplatit");
						Database.getInstance().refreshPayStatusDB(apartment);
						((AdminAccount)frame).homePanel = new HomePanel(association.getAdmin(), association, frame);
					}
				}
				frame.pack();
			}
			
		};
		
		return action;
	}
}
