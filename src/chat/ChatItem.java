package chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.AbstractBorder;

import auxiliary.ApInfoPanel;
import auxiliary.RoundedBorder;
import mainClasses.Message;

/**
Represents a JPanel component to display a single message in a chat window.
The ChatItem class extends JPanel and creates a panel that retains information
about a message and displays it. It is used in a chat window to display messages
that are sent and received by different users. The panel is displayed on the left
or right side based on who sent the message, with the sender's name, message content,
and time of message sent. The class contains methods to create and display the
components of a chat item, set its size and position.
@author Ciprian Banci
@version 1.0
*/
public class ChatItem extends JPanel{
	private JPanel messagePanel;
	private int width;
	private JTextArea text;
	private Message message;
	private Boolean left;

	/**
	 * Creates a ChatItem object and initializes the panel components and attributes.
	 *
	 * @param b a boolean indicating if the message was sent by the user or received by the user.
	 * @param message a Message object containing the message content, sender name, and message date&time.
	 * @param maxHeight an int[] to update the maximum height of the chat window for vertical scrolling.
	 */
	public ChatItem(boolean b,Message message,int[] maxHeight) {
		/*
		 * make a panel that retain information about the message
		 * and display it according to b
		 */
		super();
		
		this.message = message;
		this.left = b;
		
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
 
		//panel for the chat message 
		this.messagePanel = new JPanel();
		this.messagePanel.setLayout(new BoxLayout(this.messagePanel,BoxLayout.Y_AXIS));
		this.setBackground(Color.WHITE);
		this.messagePanel.setPreferredSize(new Dimension(400,20));
		
		//set the color based on which user sent the message
		if(left)this.messagePanel.setBackground(Color.LIGHT_GRAY);
		else this.messagePanel.setBackground(Color.CYAN);
		
		text = makeTextMessage();
		
		//JPanel for name of the user who write the message
		if(left) {
			this.messagePanel.add(makeNamePanel());
		}
		
		//the message content
		//set the size of the chatItemPanel based on the message content
		this.messagePanel.setPreferredSize(new Dimension(width,text.getLineCount() * 10 + 80));
		this.messagePanel.setMaximumSize(new Dimension(width,text.getLineCount() * 10 + 80));
		this.messagePanel.setMinimumSize(new Dimension(width,text.getLineCount() * 10 + 80));

		this.messagePanel.add(text);
		//it will be put in the left or in the right of chatPaneItem based on
		//who sent the message
		if(left) {
			this.add(this.messagePanel);
			this.add(ApInfoPanel.emptyNoSizePanel());
		}else {
			this.add(ApInfoPanel.emptyNoSizePanel());
			this.add(this.messagePanel);
		}
		
		//addiding the time when message was sent:
		JPanel time = makeTimePanel();
		this.messagePanel.add(time);

		//make round border with spike
        AbstractBorder brdrLeft = new RoundedBorder(Color.BLACK,2,8,8,b); 
        this.messagePanel.setBorder(brdrLeft);
        
        //update the max height 
        maxHeight[0] += text.getLineCount() * 10 + 90;
	}
	
	/**
	 * Creates a panel to display the time when the message was sent.
	 * 
	 * @return a JPanel object containing a label displaying the message's date/time.
	 */
	private JPanel makeTimePanel() {
		/*
		 * make the panel which display the time
		 * when message was sent
		 */
		JPanel time = new JPanel();
		time.setLayout(new GridLayout(1,1));

		String timeSent = getTime(message.getDate());
		JLabel sentTime = new JLabel(timeSent);
		sentTime.setPreferredSize(new Dimension(30,20));
		sentTime.setHorizontalAlignment(JLabel.CENTER);
		
		//put the component in the right position
		//and color it
		if(left) {
			time.add(sentTime);
			sentTime.setHorizontalAlignment(JLabel.RIGHT);
			time.setBackground(Color.LIGHT_GRAY);
			sentTime.setBackground(Color.LIGHT_GRAY);
		}else {
			time.add(sentTime);
			sentTime.setHorizontalAlignment(JLabel.LEFT);
			time.setBackground(Color.CYAN);
			sentTime.setBackground(Color.CYAN);
		}
		
		return time;
	}
	
	/**
	Creates and returns the text area for the message
	@return the text area for the message
	*/
	private JTextArea makeTextMessage() {
		/*
		 * returns the text area of the message
		 * modify the message, get its apropriate size
		 * and display it
		 * color the message to mark if from current owner
		 */
		JTextArea chatItem = new JTextArea();
		chatItem.setText( modifyMessage(message.getMessage()));
		
		//set the text area size based on its content
		width = findPrefferedWidth(modifyMessage(message.getMessage()).split("\n")[0]);

		chatItem.setPreferredSize(new Dimension(width,chatItem.getLineCount() * 20));
		chatItem.setEditable(false);
		
		//mark if current owner
		if(left)chatItem.setBackground(Color.LIGHT_GRAY);
		else chatItem.setBackground(Color.CYAN);
		
		return chatItem;
	}
	
	/**
	Creates and returns the panel which displays the name of the user who sent the message
	@return the panel which displays the name of the user who sent the message
	*/
	private JPanel makeNamePanel() {
		/*
		 * returns the panel which display the name
		 * of who sent the message
		 */
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(1,1));
		
		JLabel userNameLabel = new JLabel();
		userNameLabel.setHorizontalAlignment(JLabel.LEFT);
		userNameLabel.setText(message.getName() + ":");
		
		namePanel.add(userNameLabel);
		namePanel.setBackground(Color.LIGHT_GRAY);
		
		return namePanel;
	}
	
	/**
	Returns the time string from the given date
	@param date the date to extract the time string from
	@return the time string from the given date
	*/
	private String getTime(Date date) {
		/*
		 * return the time from date
		 */
		String time = Chat.formatDateString(date);
		String[] tokens = time.split(" ");
		time = "";
		for(int i=0;i<tokens[1].length();i++) {
			if(time.length() == 5) break;
			time += tokens[1].charAt(i);
		}
			
		return time;
	}
	
	/**
	Returns the preferred width necessary to display the text
	@param s the text to calculate the preferred width for
	@return the preferred width necessary to display the text
	*/
	private int findPrefferedWidth(String s) {
		/*
		 * return the necessary width to display the text
		 */
		
		if(s.length() * 6 < 150) {
			return 150;
		}else {
			return s.length() * 6;
		}
	}
	
	/**
	Modifies the given string to fit in the text area for the chat
	@param s the string to modify
	@return the modified string to fit in the text area for the chat
	*/
	private String modifyMessage(String s) {
		/*
		 * modify the string s to fit in the text area for the chat
		 */
		s = s.replaceAll("\n", " ");
		String sb = new String();
		String line = new String();
	    int i = 0;
	    String word = new String();
	    while (i < s.length()) {
	    	word += s.charAt(i);
	    	if(s.charAt(i) == ' ' || i == s.length()-1) {
	    		if((line.length() + word.length()) < 67) {
	    			line += word;
	    			
	    		}else {
	    			line += '\n';
	    			sb += line;
	    			line = word;
	    		}
	    		if(i == s.length()-1) {
	    			sb += line;
	    		}	
	    		word = "";
	    	}else if(line.length() == 70) line += '\n';
	    	i++;
	    }
	    if(sb == "")
	    	sb = line;
	    return sb;
	}
}
