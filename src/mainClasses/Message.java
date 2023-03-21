package mainClasses;

import java.util.Comparator;
import java.util.Date;

/**
Represents a message object that contains information about a message sent by a user.
The message object implements the Comparator interface to enable sorting by date.
@author Ciprian Banci
@version 1.0
*/
public class Message implements Comparator<Message>{
	private Integer id;
	private Integer associationId;
	private String name;
	private String email;
	private String message;
	private Date date;
	
	/**
	Creates a new instance of the Message class with the specified message details.
	@param id the unique identifier for this message
	@param associationId the identifier of the association associated with this message
	@param name the name of the user who sent this message
	@param email the email address of the user who sent this message
	@param message the content of the message sent by the user
	@param date the date when the message was sent
	*/
	public Message(Integer id, Integer associationId, String name, String email, String message, Date date) {
		this.id = id;
		this.associationId = associationId;
		this.name = name;
		this.email = email;
		this.message = message;
		this.date = date;
	}
	
	public Message() {}

	public Integer getId() {
		return id; 
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssociationId() {
		return associationId;
	}

	public void setAssociationId(Integer associationId) {
		this.associationId = associationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compare(Message o1, Message o2) {
		return o1.getDate().compareTo(o2.getDate());
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", associationId=" + associationId + ", name=" + name + ", email=" + email
				+ ", message=" + message + ", date=" + date + "]";
	}
	
	
	
}
