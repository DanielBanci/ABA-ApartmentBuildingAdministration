package mainClasses;

import java.util.Date;

/**
	The Admin class extends the User class and 
	contains information specific to an administrator user.
*/
public class Admin extends User{
	
	private Integer id;
	private Integer associationId;
	
	/**
	Default constructor for the Admin class. 
	*/
	public Admin() { 
		super();
	}
	
	/**
	Constructor for the Admin class that takes in the 
	administrator's information as parameters.
	@param name the name of the administrator
	@param email the email address of the administrator
	@param passwd the password of the administrator
	@param phoneNo the phone number of the administrator
	@param registerDate the date the administrator registered
	@param id the id of the administrator 
	@param associationId the id of the association the 
	administrator belongs to
	*/
	public Admin(String name,String email,String passwd,String phoneNo,Date registerDate,int id, int associationId) {
		super(name,email,passwd,phoneNo,registerDate);
		this.id = id;
		this.associationId = associationId;
	}
	
	/**
	Returns a string representation of the administrator's name and email address.
	@return a string representation of the administrator's name and email address
	*/
	public String toString() {
		return this.name + " " + this.email;
	}
	
	/**
	Compares two Admin objects based on their email addresses.
	@param o1 the first Admin object to compare
	@param o2 the second Admin object to compare
	@return 0 if the email addresses of the two Admin objects are equal, -1 otherwise
	*/
	@Override
	public int compare(Object o1, Object o2) {
		if(((Admin)o1).email.equals(((Admin)o2).email))
			return 0;
		return -1;
	}
	
	//setters and getters
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
}
