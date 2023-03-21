package auxiliary;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import appInterface.ApartmentAccount;
import mainPanels.Title;

/**

PayStatusUpdate is a custom JPanel used to display and update the payment status of all apartments from an association.
It extends the PanelRound class and provides a rounded panel and a scrollable
content area for displaying and editing information.
*/
public class PayStatusUpdate extends PanelRound{
	
	
	public PayStatusUpdate() {
		super(); 
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//title -> change
		Title title = new Title("Actualizare status plata",null);
		
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(title);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		//content
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
		
		JScrollPane pane = new JScrollPane(content,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.add(pane);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		//save button
		JPanel buttonS = new JPanel();
		buttonS.setLayout(new GridLayout(1,3));
		
		JButton save = new JButton("Salveaza");
		
		buttonS.add(ApartmentAccount.makeEmptyPanel());
		buttonS.add(save);
		buttonS.add(ApartmentAccount.makeEmptyPanel());
		
		this.add(buttonS);
	}
	
}
