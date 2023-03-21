package chat;

import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import appInterface.ApartmentAccount;
import auxiliary.ApInfoPanel;
import mainClasses.Association;
import mainClasses.User;
import mainPanels.MainPanel;
import mainPanels.Title;

/**
The ChatPanel class represents the panel for displaying the chat between users in an association.
It extends the MainPanel class.
@author Ciprian Banci
@version 1.0
*/
public class ChatPanel extends MainPanel{
	private Title title;
	private Chat chat; 
	
	/**
	Creates a new ChatPanel object for the user.
	@param association the Association of the user
	@param user the current user
	@param frame the frame that contains the panel
	*/
	public ChatPanel(Association association, User user, JFrame frame) {
		super(); 
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//title
		Image image = null;
		try {
			image = (new ImageIcon(this.getClass().getResource("/chatImage.png"))).getImage();
		}catch(Exception e) {
			e.printStackTrace();
		}
		title = new Title("Chat",image);
		
		this.add(ApartmentAccount.makeEmptyPanel());
		this.add(title);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		chat = new Chat(association,user,frame);
		this.add(chat);
		this.add(ApInfoPanel.emptyNoSizePanel());
	}

	/**

	Returns the Chat object for the panel.
	@return the Chat object for the panel
	*/
	public Chat getChat() {
		return chat;
	}	
}
