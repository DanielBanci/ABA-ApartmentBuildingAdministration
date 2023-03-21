package mainClasses;

import java.util.Comparator;
import java.util.Date;

/**
 * Reprezents an user of the application
 * 
 * @author Ciprian Banci
 * @version 1.0
 */
public abstract class User implements Comparator{
	
	protected String name;
	protected String email;
	protected String phoneNo;
	protected String password;
	protected Date registerDate; 
	
	/**
	 * Empty constructor for the User class.
	 */
	public User() {};
	/**

	Constructs a User object with the specified parameters.
	@param name the name of the user
	@param email the email of the user
	@param passwd the password of the user
	@param phoneNo the phone number of the user
	@param registerDate the date the user registered
	*/
	public User(String name,String email,String passwd,String phoneNo,Date registerDate) {
		this.name = name;
		this.email = email; 
		this.phoneNo = phoneNo;
		this.password = passwd;
		this.registerDate = registerDate;
	}
	
	/**

	Compares two User objects by email and password.
	@param usr1 the first User object to be compared
	@param usr2 the second User object to be compared
	@return 0 if the two User objects have the same email and password, -1 otherwise
	*/
	public int compare(User usr1,User usr2) {//??
		if(usr1.email.equals(usr2.email) && usr1.password.equals(usr2.password))
			return 0;
		else return -1;
	}

	/**

	Returns the name of the user.
	@return the name of the user
	*/
	public String getName() {
		return name;
	}

	/**

	Sets the name of the user.
	@param name the new name of the user
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**

	Returns the email of the user.
	@return the email of the user
	*/
	public String getEmail() {
		return email;
	}

	/**

	Sets the email of the user.
	@param email the new email of the user
	*/
	public void setEmail(String email) {
		this.email = email;
	}

	/**

	Returns the phone number of the user.
	@return the phone number of the user
	*/
	public String getPhoneNo() {
		return phoneNo;
	}

	/**

	Sets the phone number of the user.
	@param phoneNo the new phone number of the user
	*/
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**

	Returns the password of the user.
	@return the password of the user
	*/
	public String getPasswd() {
		return password;
	}

	/**

	Sets the password of the user.
	@param passwd the new password of the user
	*/
	public void setPasswd(String passwd) {
		this.password = passwd;
	}

	/**

	Returns the date the user registered.
	@return the date the user registered
	*/
	public Date getRegisterDate() {
		return registerDate;
	}
	
	/**

	Sets the register date of the user.
	@param passwd the register date of the user
	*/
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	/**

	Returns a string with users information.
	@return a string with users information
	*/
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", phoneNo=" + phoneNo + "]";
	}
	
}
