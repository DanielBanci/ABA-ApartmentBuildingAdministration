package chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import appInterface.ApartmentAccount;
import mainClasses.Association;
import mainClasses.Database;
import mainClasses.Message;
import mainClasses.User;

/**

A JPanel that displays a chat for an association, allows the user to write 
new messages and shows old messages.
The chat panel is divided into two sections: a JScrollPane that shows 
the history of messages and a JPanel for writing new messages.
@author Ciprian Banci
@version 1.0
*/
public class Chat extends JPanel {
	private Association association;
	private User user;
	private JFrame frame;
	private List<Message> messages;
	private Date lastDate;
	
	//panels
	private JScrollPane content;
	private JPanel inputPanel;
	
	//text contents
	private JPanel chatContent;
	private JTextArea inputText;
	private int[] maxHeight;
	
	/**
	Constructs a new Chat object with an association, a user, and a JFrame.
	@param association the association for which the chat is displayed
	@param user the user that uses the chat
	@param frame the frame that contains the chat panel
	*/
	public Chat(Association association,User user,JFrame frame) {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setOpaque(false);
		
		this.association = association;
		this.user = user;
		this.frame = frame;
		this.maxHeight = new int[1]; 
		this.maxHeight[0] = 0;
		
		//getting the messages from databese
		messages = new ArrayList<>();
		messages = Database.getInstance().getMessagesFromDB(association);
		
		content = makeChatContent();
		this.add(content);
		this.add(ApartmentAccount.makeEmptyPanel());
		
		inputPanel = makeInput();
		
		this.add(inputPanel);
	}
	
	/**
	Makes a panel where the user can write a new message.
	@return the panel where the user can write a new message
	*/
	private JPanel makeInput() {
		/*
		 * make the panel where the user can write a new message
		 */
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BoxLayout(messagePanel,BoxLayout.X_AXIS));
		
		//making the text area
		inputText = new JTextArea();
		JScrollPane messagePane = new JScrollPane();
		messagePane.setMaximumSize(new Dimension(726,100));
		messagePane.setViewportView(inputText);
		messagePanel.add(messagePane);

		//making the send button;
		JButton sendButton = new JButton("Trimite");
		Image imagei = null;
		ImageIcon imageIconi = null;
		try {
			imagei = (new ImageIcon(this.getClass().getResource("/messageIcon.png"))).getImage();
			imageIconi = new ImageIcon(imagei);
			sendButton.setIcon(imageIconi);
		}catch(Exception e) {
			e.printStackTrace();
		}
		sendButton.setPreferredSize(new Dimension(100,60));
		messagePanel.add(sendButton);
		messagePanel.setBackground(Color.WHITE);

		sendButton.addActionListener(makeSendAction());
		messagePanel.setOpaque(false);
		return messagePanel; 
	}
	
	/**
	 * Returns a string reprezenting the date in yyyy-MM-dd HH:mm:ss format.
	 * @param date the date to be converted
	 * @return a string reprezenting the date in yyyy-MM-dd HH:mm:ss format
	 */
	public static String formatDateString(Date date) {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return formatter.format(date);
	}

	/**
	Makes a new ActionListener that will be used when the send button is pressed.
	@return the new ActionListener that will be used when the send button is pressed
	*/
	private ActionListener makeSendAction() {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputText.getText() != null && !inputText.getText().equals("") && !inputText.getText().equals(" ")) {
					//get the message
					String content = inputText.getText();
					//clean the content
					inputText.setText("");
					//make new message
					Message newMessage = new Message(Database.getInstance().getMessLastId()+1,association.getId(),user.getName(),
							user.getEmail(),content,new Date());
					
					//write new message to databases
					Database.getInstance().insertMessage(newMessage);
					
					//write the message on chat
					writeMessage(newMessage);
					
					//add the newMessage to messages list
					messages.add(0, newMessage);
					frame.pack();
				}
			}
		};
		
		return action;
	}

	/**
	Writes a message to the chat window.
	@param message the message to be displayed in the chat window
	*/
	private void writeMessage(Message message) {
		boolean currentUser = message.getEmail().equals(user.getEmail()) ? true : false;
		//write the message on chat
		if(lastDate == null) {
			//no message displayed
			chatContent.add(makeDateLabel(message));
			lastDate = message.getDate();
			maxHeight[0] += 30;
			chatContent.add(new ChatItem(!currentUser,message,maxHeight));
		}else if(convertDateToNoTime(lastDate).compareTo(convertDateToNoTime(message.getDate())) == 0) {
			//message from the current last date on chat
			lastDate = message.getDate();
			chatContent.add(new ChatItem(!currentUser,message,maxHeight));
			maxHeight[0] += 30;
		}else {
			//message on chat from older time
			chatContent.add(makeDateLabel(message));
			lastDate = message.getDate();
			maxHeight[0] += 30;
			chatContent.add(new ChatItem(!currentUser,message,maxHeight));
		}
		chatContent.setPreferredSize(new Dimension(580,maxHeight[0]));
	}
	
	/**
	Makes the chat window content, including sorting the messages and setting their formatting.
	@return a JScrollPane containing the formatted messages for the chat window
	*/
	private JScrollPane makeChatContent() {
		//making chat area 
		chatContent = new JPanel();
		chatContent.setLayout(new BoxLayout(chatContent,BoxLayout.Y_AXIS));
		chatContent.setBackground(Color.WHITE);
		chatContent.setOpaque(true);

		//sort the messages based on the sent date
		Collections.sort(messages, new Comparator<Message>() {
			@Override
			public int compare(Message m1, Message m2) {
				return m1.getDate().compareTo(m2.getDate());
			}
		});

		lastDate = null;
		//getting the date of the first message and display the first message
		for(Message mess : messages) {
			writeMessage(mess);
		}

		//put the chat into a scroll pane
		JScrollPane chatArea = new JScrollPane();
		chatArea.setViewportView(chatContent);

		chatArea.setPreferredSize(new Dimension(820,400));
		chatArea.setMaximumSize(new Dimension(820,400));
		
		return chatArea;
	}
	
	/**
	Converts a date object to a date object with no time.
	@param date the date object to be converted
	@return a date object with no time
	*/
	public static Date convertDateToNoTime(Date date) {

		// Create a Calendar object and set it to the date with time
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		// Set the time portion to zero
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// Get the Date object without time portion
		Date dateWithoutTime = calendar.getTime();
		
		return dateWithoutTime;

	}
	
	/**
	Formats a date object to a string in the format "dd-MM-yyyy".
	@param date the date object to be formatted
	@return a string in the format "dd-MM-yyyy"
	*/
	public static String formatDateString2(Date date) {
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	    return formatter.format(date);
	}
	
	/**
	 * Returns a label containing the date when message was sent
	 * if the date is today the message "Astazi" will be displayed
	 * @param message the message for which we make a date label
	 * @return a label with the message date
	 */
	private JLabel makeDateLabel(Message message) {
		/*
		 * makes a JLabel with a date on it
		 * the date reprezents when the message was sent
		 */
		JLabel dateLabel = new JLabel();
		String date = message.getDate().toString();

		//format date
		date = formatDateString2(message.getDate());

		if(convertDateToNoTime(message.getDate()).compareTo(convertDateToNoTime(new Date())) == 0 ) {
			dateLabel.setText("---" + "Astazi" + "---");
		}
		else
			dateLabel.setText("---" + date + "---");
		dateLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		return dateLabel;
	}
}
