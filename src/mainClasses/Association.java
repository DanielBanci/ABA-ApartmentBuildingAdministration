package mainClasses;

import java.util.Date;
import java.util.List;
/**
	The Association class represents an association with 
	all its relevant attributes.
	@author Ciprian Banci
	@version 1.0
*/
public class Association {

	private Integer id;
	private String street;
	private String number;
	private String building;
	private String stair;
	private String district;
	private String locality;
	private Double wWaterPrice;
	private Double cWaterPrice;
	private Double repairFund;
	private Double rulFund;
	private Double updateFund;
	private Double electBill;
	private Double salaries;
	private Double warmingBill;
	private Double trashPrice;
	private Double gasBill;
	private Admin admin; //admin of association
	private Double totalArea; //square meters of the building, used for calculating warming price
	private Integer peopleNoTotal;
	private Date nBLastUpdate;
	private List<Apartment> apartments; //apartments of association
	private List<Date> payingDates;
	
	/**
	 * Default constructor for Association 
	 */
	public Association() {}
	
	/**

	Constructor for the Association class.
	@param id The ID of the association.
	@param street The street name of the association.
	@param number The street number of the association.
	@param building The building number of the association.
	@param stair The stair number of the association.
	@param district The district name of the association.
	@param locality The locality name of the association.
	@param wWaterPrice The price of warm water for the association.
	@param cWaterPrice The price of cold water for the association.
	@param repairFund The repair fund of the association.
	@param rulFund The rule fund of the association.
	@param updateFund The update fund of the association.
	@param electBill The electricity bill of the association.
	@param salaries The salaries of the association.
	@param warmingBill The warming bill of the association.
	@param trashPrice The price of trash for the association.
	@param gasBill The gas bill of the association.
	@param admin The admin of the association.
	@param sMSum The total square meters of the building of the association, used for calculating warming price.
	@param peopleNoTotal The total number of people living in the association.
	@param apartments The list of apartments of the association. 
	@param payingDates The list of paying dates of the association.
	@param nBLastUpdate The last update date for the non-billing data of the association.
	*/
	public Association(Integer id, String street, String number, String building,String stair, String district, 
			String locality, Double wWaterPrice, 
			Double cWaterPrice, Double repairFund, Double rulFund, Double updateFund, Double electBill, Double salaries,
			Double warming, Double trashPrice, Double gasBill, Admin admin, Double sMSum, 
			Integer peopleNoTotal, List<Apartment> apartments, List<Date> payingDates,Date nBLastUpdate) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.building = building;
		this.stair = stair;
		this.district = district;
		this.locality = locality;
		this.wWaterPrice = wWaterPrice;
		this.cWaterPrice = cWaterPrice;
		this.repairFund = repairFund;
		this.rulFund = rulFund;
		this.updateFund = updateFund;
		this.electBill = electBill;
		this.salaries = salaries;
		this.warmingBill = warming;
		this.trashPrice = trashPrice;
		this.gasBill = gasBill;
		this.admin = admin;
		this.totalArea = sMSum;
		this.peopleNoTotal = peopleNoTotal;
		this.apartments = apartments;
		this.payingDates = payingDates;
		this.nBLastUpdate = nBLastUpdate;
	}
	
	//setters and getters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public Double getwWaterPrice() {
		return wWaterPrice;
	}
	public void setwWaterPrice(Double wWaterPrice) {
		this.wWaterPrice = wWaterPrice;
	}
	public Double getcWaterPrice() {
		return cWaterPrice;
	}
	public void setcWaterPrice(Double cWaterPrice) {
		this.cWaterPrice = cWaterPrice;
	}
	public Double getRepairFund() {
		return repairFund;
	}
	public void setRepairFund(Double repairFund) {
		this.repairFund = repairFund;
	}
	public Double getRulFund() {
		return rulFund;
	}
	public void setRulFund(Double rulFund) {
		this.rulFund = rulFund;
	}
	public Double getUpdateFund() {
		return updateFund;
	}
	public void setUpdateFund(Double updateFund) {
		this.updateFund = updateFund;
	}
	public Double getElectBill() {
		return electBill;
	}
	public void setElectBill(Double electPrice) {
		this.electBill = electPrice;
	}
	public Double getSalaries() {
		return salaries;
	}
	public void setSalaries(Double salaries) {
		this.salaries = salaries;
	}
	public Double getWarmingBill() {
		return warmingBill;
	}
	public void setWarmingBill(Double warming) {
		this.warmingBill = warming;
	}
	public Double getTrashPrice() {
		return trashPrice;
	}
	public void setTrashPrice(Double trashPrice) {
		this.trashPrice = trashPrice;
	}
	public Double getGasBill() {
		return gasBill;
	}
	public void setGasBill(Double gasBill) {
		this.gasBill = gasBill;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Double getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(Double sMSum) {
		this.totalArea = sMSum;
	}
	public Integer getPeopleNoTotal() {
		return peopleNoTotal;
	}
	public void setPeopleNoTotal(Integer peopleNoTotal) {
		this.peopleNoTotal = peopleNoTotal;
	}
	public List<Apartment> getApartments() {
		return apartments;
	}
	public void setApartments(List<Apartment> apartments) {
		this.apartments = apartments;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getStair() {
		return stair;
	}
	public void setStair(String stair) {
		this.stair = stair;
	}
	public List<Date> getPayingDates() {
		return payingDates;
	}
	public void setPayingDates(List<Date> payingDates) {
		this.payingDates = payingDates;
	}
	public Date getnBLastUpdate() {
		return nBLastUpdate;
	}
	public void setnBLastUpdate(Date nBLastUpdate) {
		this.nBLastUpdate = nBLastUpdate;
	}
}
