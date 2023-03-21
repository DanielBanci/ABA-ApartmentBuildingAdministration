package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;
import mainClasses.Database;

public class DatabaseTest {

	@Test
	public void test() {
		Association association = makeAssociation();
		Database.getInstance().insertAllData(association);
		
		Association associationFromDb = Database.getInstance().getAssociation(association.getAdmin());
		
		//chack admin
		assertEquals(associationFromDb.getAdmin().getEmail(), association.getAdmin().getEmail());
		
		//check apartments
		for(int i=0;i<association.getApartments().size();i++) {
			assertEquals(associationFromDb.getApartments().get(i).getEmail(), 
					association.getApartments().get(i).getEmail());
		}
		
		//other data
		assertEquals(associationFromDb.getBuilding(),association.getBuilding());
		assertEquals(associationFromDb.getDistrict(),association.getDistrict());
		assertEquals(associationFromDb.getcWaterPrice(),association.getcWaterPrice());
		assertEquals(associationFromDb.getElectBill(),association.getElectBill());
		assertEquals(associationFromDb.getGasBill(),association.getGasBill());
		assertEquals(associationFromDb.getId(),association.getId());
		assertEquals(associationFromDb.getLocality(),association.getLocality());
		assertEquals(associationFromDb.getnBLastUpdate(),association.getnBLastUpdate());
		assertEquals(associationFromDb.getPeopleNoTotal(),association.getPeopleNoTotal());
		assertEquals(associationFromDb.getRepairFund(),association.getRepairFund());
		assertEquals(associationFromDb.getRulFund(),association.getRulFund());
		assertEquals(associationFromDb.getUpdateFund(),association.getUpdateFund());
		assertEquals(associationFromDb.getSalaries(),association.getSalaries());
		assertEquals(associationFromDb.getTotalArea(),association.getTotalArea());
	}
	
	public Association makeAssociation() {
		
		int adminId = Database.getInstance().getLastAdminId()+1;
		int associationId = Database.getInstance().getLastAssociationId()+1;
		int apartmentId = Database.getInstance().getLastIdApartment()+1;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		Date date = cal.getTime();
		LocalDate dateld = LocalDate.now();
		date = new java.sql.Date(dateld.getYear()-1900,dateld.getMonthValue()-1,dateld.getDayOfMonth());
		
		Admin admin = new Admin("Nume admin","email@gmail.com", "password",
				"077777777777", date, adminId, 100);
		List<Apartment> apartments = new ArrayList<>();
		
		//apartments

		Apartment ap1 = new Apartment("John Smith", "john.smith@example.com", "password1", "555-1234", 
				date, 1, apartmentId, 100, 2, 30.0, false, true, 0.0, 10.0, 0.0, 10.0, 20.0, date, 
				true, 0.0, 0.0, 0.0, 0.0, true);
		Apartment ap2 = new Apartment("Mary Johnson", "mary.johnson@example.com", "password2", "555-5678", 
				date, 2, apartmentId+1, 100, 2, 30.0, false, false, 70.0, 80.0, 0.0, 80.0, 90.0, date, 
				false, 0.0, 0.0, 1.0, 1.5, false);
		Apartment ap3 = new Apartment("Peter Lee", "peter.lee@example.com", "password3", "555-9012", 
				date, 3, apartmentId+2, 100, 2, 20.0, true, true, 60.0, 70.0, 20.0, 70.0, 80.0, date, 
				true, 1.0, 1.0, 6.0, 6.0, false);
		Apartment ap4 = new Apartment("Karen Kim", "karen.kim@example.com", "password4", "555-3456", 
				date, 4, apartmentId+3, 100, 4, 40.0, false, false, 10.0, 50.0, 0.0, 20.0, 60.0, date, 
				false, 0.0, 0.0, 0.5, 1.0, false);
		apartments.add(ap1);
		apartments.add(ap2);
		apartments.add(ap3);
		apartments.add(ap4);
		List<Date> dates = new ArrayList<>();//tarea = 130 tpeople = 10
		
		Association association = new Association(associationId, "street", "2", "V", "2f", "dist", "loc", 
	            20.0, 10.0, 80.0, 80.0, 80.0, 200.0, 2000.0, 4000.0, 10.0, 60.0, admin, 
	            120.0, 10, apartments, dates, date);
		return association;
	}
}
