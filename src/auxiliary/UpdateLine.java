package auxiliary;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import appInterface.ApartmentAccount;

/**
The UpdateLine class creates a panel that contains one or two AccountData 
objects for updating account information.
This class extends the JPanel class.
@author Ciprian Banci
@version 1.0
*/
public class UpdateLine extends JPanel{
	private AccountData data;
	private AccountData data2;
	
	/**
	Constructs a new UpdateLine with a single AccountData object with a given title.
	@param title the title for the AccountData object
	*/
	public UpdateLine(String title) {
		super(); 
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.add(ApartmentAccount.makeEmptyPanel());
		this.setOpaque(false);
		
		data = new AccountData(title,false);
		
		data.setPreferredSize(new Dimension(300,20));
		data.setMinimumSize(new Dimension(300,20));
		data.setMaximumSize(new Dimension(300,20));
		
		this.add(data);
		
		this.add(ApartmentAccount.makeEmptyPanel());
	}
	
	/**
	Constructs a new UpdateLine with two AccountData objects with given titles.
	@param title the title for the first AccountData object
	@param title2 the title for the second AccountData object
	*/
	public UpdateLine(String title, String title2) {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.add(ApartmentAccount.makeEmptyPanel());
		this.setOpaque(false);
		
		data = new AccountData(title,false);
		
		data.setPreferredSize(new Dimension(300,20));
		data.setMinimumSize(new Dimension(300,20));
		data.setMaximumSize(new Dimension(300,20));
		
		data2 = new AccountData(title2,false);
		data2.setPreferredSize(new Dimension(300,20));
		data2.setMinimumSize(new Dimension(300,20));
		data2.setMaximumSize(new Dimension(300,20));
		
		this.add(data);
		this.add(data2);
		
		this.add(ApartmentAccount.makeEmptyPanel());
	}
	
	/**

	Constructs a new UpdateLine with two AccountData objects with given titles and check box flags.
	@param title the title for the first AccountData object
	@param title2 the title for the second AccountData object
	@param cBoxFlag1 a boolean indicating whether a check box is to be displayed for the first object
	@param cBoxFlag2 a boolean indicating whether a check box is to be displayed for the second object
	*/
	public UpdateLine(String title, String title2,Boolean cBoxFlag1, Boolean cBoxFlag2) {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.add(ApartmentAccount.makeEmptyPanel());
		this.setOpaque(false);
		
		data = new AccountData(title,cBoxFlag1);
		
		data.setPreferredSize(new Dimension(300,20));
		data.setMinimumSize(new Dimension(300,20));
		data.setMaximumSize(new Dimension(300,20));
		
		data2 = new AccountData(title2,cBoxFlag2);
		data2.setPreferredSize(new Dimension(300,20));
		data2.setMinimumSize(new Dimension(300,20));
		data2.setMaximumSize(new Dimension(300,20));
		
		this.add(data);
		this.add(data2);
		
		this.add(ApartmentAccount.makeEmptyPanel());
	}

	public AccountData getData() {
		return data;
	}

	public void setData(AccountData data) {
		this.data = data;
	}

	public AccountData getData2() {
		return data2;
	}

	public void setData2(AccountData data2) {
		this.data2 = data2;
	}
}
