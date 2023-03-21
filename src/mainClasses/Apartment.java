package mainClasses;

import java.util.Date;

/**
*
*The Apartment class is a subclass of User and it represents an apartment in a housing association.
*It contains various fields related to the apartment, such as its number, id, association id, number of people,
*area, central heating availability, gas contor availability, cold water contor old value, hot water contor old value,
*overdue amount, cold water contor new value, hot water contor new value, contor update date, individual water contors availability,
*old and new values for the conversion factors for cold and hot water contors, payment status, etc.
*@author Ciprian Banci
*@version 1.0
*/
public class Apartment extends User{
	
	private Integer no; //apartment number
	private Integer id; //apartment id
	private Integer association_id; //housing association id
	private Integer peopleNo; //number of people living in the apartment
	private Double area; //area of the apartment
	private Boolean centralHeating; //availability of central heating
	private Boolean gasContor; //availability of gas contor
	private Double cWContorOld; //bath - old value for cold water contor
	private Double wWContorOld; //bath - old value for hot water contor
	private Double cWContorNew; //bath - new value for cold water contor
	private Double wWContorNew; //bath - new value for hot water contor
	private Double cWCOldK; //kitchen - old value for cold water contor
	private Double wWCOldK; //kitchen -old value for for hot water contor
	private Double cWCNewK; //kitchen -new value for cold water contor
	private Double wWCNewK; //kitchen -new value for hot water contor
	private Boolean individualWContors; //availability of individual water contors
	private Double overdue; //overdue amount
	private Date contorUpdateDate; //date of last contor update
	private Boolean payedStatus; //payment status

	/**
	 * Empty constructor for the Apartment class.
	 */
	public Apartment() {
		super(); 
	}
	
	/**
	*Constructs an Apartment object with the specified parameters.
	*@param name the name of the user
	*@param email the email of the user
	*@param password the password of the user
	*@param phoneNo the phone number of the user
	*@param registerDate the registration date of the user
	*@param no the number of the apartment
	*@param id the ID of the apartment
	*@param association_id the ID of the association that the apartment belongs to
	*@param peopleNo the number of people living in the apartment
	*@param area the area of the apartment
	*@param centralHeating whether the apartment has central heating or not
	*@param gasContor whether the apartment has a gas contor or not
	*@param cWContorOld the old value of the cold water contor
	*@param wWContorOld the old value of the warm water contor
	*@param overdue the overdue debt for the apartment
	*@param cWContorNew the new value of the cold water contor
	*@param wWContorNew the new value of the warm water contor
	*@param contorUpdateDate the date when the contors were updated
	*@param individualWContors whether the apartment has individual warm water contors or not
	*@param cWCOldK the old value of the cold water coefficient
	*@param wWCOldK the old value of the warm water coefficient
	*@param cWNewK the new value of the cold water coefficient
	*@param wWNewK the new value of the warm water coefficient
	*@param payedStatus whether the apartment has paid its debts or not
	*/
	public Apartment(String name, String email, String password, String phoneNo, Date registerDate, int no, int id, int association_id,
			int peopleNo, Double area, boolean centralHeating, boolean gasContor, Double cWContorOld, Double wWContorOld,Double overdue, 
			Double cWContorNew, Double wWContorNew, Date contorUpdateDate, boolean individualWContors, Double cWCOldK, Double wWCOldK,
			Double cWNewK, Double wWNewK, boolean payedStatus) {
			
		super(name,email,password,phoneNo,registerDate);
		this.no = no;
		this.id = id;
		this.association_id = association_id;
		this.peopleNo = peopleNo;
		this.area = area;
		this.centralHeating = centralHeating;
		this.gasContor = gasContor;
		this.cWContorOld = cWContorOld;
		this.wWContorOld = wWContorOld;
		this.overdue = overdue;
		this.cWContorNew = cWContorNew;
		this.wWContorNew = wWContorNew;
		this.contorUpdateDate = contorUpdateDate;
		this.individualWContors = individualWContors;
		this.wWCOldK = wWCOldK;
		this.cWCOldK = cWCOldK;
		this.cWCNewK = cWNewK;
		this.wWCNewK = wWNewK;
		
		this.payedStatus = payedStatus;
	}

	public static int compare(Apartment usr1, Apartment usr2) {
		if(usr1.getId() == usr2.getId() && usr1.email.equals(usr2.email) && usr1.password.equals(usr2.password))
			return 0;
		else return -1;
	}

	/**

	Compares two Apartment objects based on their email address.
	@param o1 the first Apartment object to compare
	@param o2 the second Apartment object to compare
	@return 1 if the email addresses of the two Apartment objects are the same, 0 otherwise
	*/
	@Override
	public int compare(Object o1, Object o2) {
		return ((Apartment)o1).getEmail().equals(((Apartment)o2).getEmail()) ? 1 : 0;
	}

	/**

	This method returns a string representation of the object.
	The string representation contains values of all the member variables of the Apartment object.
	@return a string representation of the Apartment object.
	*/
	@Override
	public String toString() {
		return "Apartment [no=" + no + ", id=" + id + ", association_id=" + association_id + ", peopleNo=" + peopleNo
				+ ", area=" + area + ", centralHeating=" + centralHeating + ", cWContorOld=" + cWContorOld
				+ ", wWContorOld=" + wWContorOld + ", overdue=" + overdue + ", cWContorNew=" + cWContorNew
				+ ", wWContorNew=" + wWContorNew + ", contorUpdateDate=" + contorUpdateDate + "]";
	}
	
	/**
	 * setters & getters
	 */
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPeopleNo() {
		return peopleNo;
	}

	public void setPeopleNo(Integer peopleNo) {
		this.peopleNo = peopleNo;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Boolean getCentralHeating() {
		return centralHeating;
	}

	public void setCentralHeating(Boolean centralHeating) {
		this.centralHeating = centralHeating;
	}

	public Double getcWContorOld() {
		return cWContorOld;
	}

	public void setcWContorOld(Double cWContorOld) {
		this.cWContorOld = cWContorOld;
	}

	public Double getwWContorOld() {
		return wWContorOld;
	}

	public void setwWContorOld(Double wWContorOld) {
		this.wWContorOld = wWContorOld;
	}

	public Double getOverdue() {
		return overdue;
	}

	public void setOverdue(Double overdue) {
		this.overdue = overdue;
	}

	public Double getcWContorNew() {
		return cWContorNew;
	}

	public void setcWContorNew(Double cWContorNew) {
		this.cWContorNew = cWContorNew;
	}

	public Double getwWContorNew() {
		return wWContorNew;
	}

	public void setwWContorNew(Double wWContorNew) {
		this.wWContorNew = wWContorNew;
	}

	public Date getContorUpdateDate() {
		return contorUpdateDate;
	}

	public void setContorUpdateDate(Date date) {
		this.contorUpdateDate = date;
	}

	public Integer getAssociation_id() {
		return association_id;
	}

	public void setAssociation_id(Integer association_id) {
		this.association_id = association_id;
	}

	public Boolean getGasContor() {
		return gasContor;
	}

	public void setGasContor(Boolean gasContor) {
		this.gasContor = gasContor;
	}

	public Double getcWCOldK() {
		return cWCOldK;
	}

	public void setcWCOldK(Double cWCOldK) {
		this.cWCOldK = cWCOldK;
	}

	public Double getwWCOldK() {
		return wWCOldK;
	}

	public void setwWCOldK(Double wWCOldK) {
		this.wWCOldK = wWCOldK;
	}

	public Double getcWCNewK() {
		return cWCNewK;
	}

	public void setcWCNewK(Double cWCNewK) {
		this.cWCNewK = cWCNewK;
	}

	public Double getwWCNewK() {
		return wWCNewK;
	}

	public void setwWCNewK(Double wWCNewK) {
		this.wWCNewK = wWCNewK;
	}

	public Boolean getIndividualWContors() {
		return individualWContors;
	}

	public void setIndividualWContors(Boolean individualWContors) {
		this.individualWContors = individualWContors;
	}

	public Boolean getPayedStatus() {
		return payedStatus;
	}

	public void setPayedStatus(Boolean payedStatus) {
		this.payedStatus = payedStatus;
	}

	
}
