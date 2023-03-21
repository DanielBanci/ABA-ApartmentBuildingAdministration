package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import auxiliary.TablePanel;
import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;

public class TablePanelTest {

	public Association makeAssociation() {
		Admin admin = new Admin("Nume admin","email@gmail.com", "password",
				"077777777777", new Date(), 100, 
				100);
		List<Apartment> apartments = new ArrayList<>();
		
		//apartments
		
		//date for calculating the overdue
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		Date date = cal.getTime();
		
		Apartment ap1 = new Apartment("John Smith", "john.smith@example.com", "password1", "555-1234", 
				new Date(), 1, 1, 100, 2, 30.0, false, true, 0.0, 10.0, 0.0, 10.0, 20.0, new Date(), 
				true, 0.0, 0.0, 0.0, 0.0, true);
		Apartment ap2 = new Apartment("Mary Johnson", "mary.johnson@example.com", "password2", "555-5678", 
				new Date(), 2, 2, 100, 2, 30.0, false, false, 70.0, 80.0, 0.0, 80.0, 90.0, new Date(), 
				false, 0.0, 0.0, 1.0, 1.5, false);
		Apartment ap3 = new Apartment("Peter Lee", "peter.lee@example.com", "password3", "555-9012", 
				new Date(), 3, 3, 100, 2, 20.0, true, true, 60.0, 70.0, 20.0, 70.0, 80.0, new Date(), 
				true, 1.0, 1.0, 6.0, 6.0, false);
		Apartment ap4 = new Apartment("Karen Kim", "karen.kim@example.com", "password4", "555-3456", 
				new Date(), 4, 4, 100, 4, 40.0, false, false, 10.0, 50.0, 0.0, 20.0, 60.0, new Date(), 
				false, 0.0, 0.0, 0.5, 1.0, false);
		apartments.add(ap1);
		apartments.add(ap2);
		apartments.add(ap3);
		apartments.add(ap4);
		List<Date> dates = new ArrayList<>();//tarea = 130 tpeople = 10
		
		Association association = new Association(100, "street", "2", "V", "2f", "dist", "loc", 
	            20.0, 10.0, 80.0, 80.0, 80.0, 200.0, 2000.0, 4000.0, 10.0, 60.0, admin, 
	            120.0, 10, apartments, dates, date);
		return association;
	}
	@Test
	public void testMakeColumnDataAdminAccount() {
		// for admin account
		Association association = makeAssociation();
		// create a new instance of the makeColumnData function
		
		
		// test case 1: check the size of the data array
		String[][] data = TablePanel.makeColumnData(association.getAdmin(),association);//null;//yourClass.makeColumnData(admin, association);
		assertEquals(19, data[0].length);
		assertEquals(6, data.length);

		// check prices line
		assertEquals("Pret ->", data[0][0]);
		assertEquals("-", data[0][1]);
		assertEquals("-", data[0][2]);
		assertEquals("20.0", data[0][3]);//rep
		assertEquals("20.0", data[0][4]);//rul
		assertEquals("20.0", data[0][5]);//upt
		assertEquals("50.0", data[0][6]);//elect
		assertEquals("500.0", data[0][7]);//sal
		assertEquals("-", data[0][8]);
		assertEquals("-", data[0][9]);
		assertEquals("20.0", data[0][10]);//warm water
		assertEquals("10.0", data[0][11]);//cold water
		assertEquals("10.0", data[0][12]);//gas
		assertEquals("10.0", data[0][13]);//trash
		assertEquals("40.0", data[0][14]);//warming
		assertEquals("-", data[0][15]);
		assertEquals("0.2%", data[0][16]);
		assertEquals("-", data[0][17]);
		assertEquals("-", data[0][18]);

		//check line for ap1
		assertEquals("1", data[1][0]);
		assertEquals("30.0", data[1][1]);
		assertEquals("2", data[1][2]);
		assertEquals("20.0", data[1][3]);
		assertEquals("20.0", data[1][4]);
		assertEquals("20.0", data[1][5]);
		assertEquals("50.0", data[1][6]);
		assertEquals("500.0", data[1][7]);
		assertEquals("10.0", data[1][8]);
		assertEquals("10.0", data[1][9]);
		assertEquals("200.0", data[1][10]);
		assertEquals("100.0", data[1][11]);
		assertEquals("0.0", data[1][12]);
		assertEquals("20.0", data[1][13]);
		assertEquals("1200.0", data[1][14]);
		assertEquals("2130.0", data[1][15]);
		assertEquals("0.0", data[1][16]);
		assertEquals("0.0", data[1][17]);
		assertEquals("2130.0", data[1][18]);
		
		//check line for ap2
		assertEquals("2", data[2][0]);
		assertEquals("30.0", data[2][1]);
		assertEquals("2", data[2][2]);
		assertEquals("20.0", data[2][3]);
		assertEquals("20.0", data[2][4]);
		assertEquals("20.0", data[2][5]);
		assertEquals("50.0", data[2][6]);
		assertEquals("500.0", data[2][7]);
		assertEquals("10.0", data[2][8]);
		assertEquals("10.0", data[2][9]);
		assertEquals("200.0", data[2][10]);
		assertEquals("100.0", data[2][11]);
		assertEquals("20.0", data[2][12]);
		assertEquals("20.0", data[2][13]);
		assertEquals("1200.0", data[2][14]);
		assertEquals("2150.0", data[2][15]);
		assertEquals("0.0", data[2][16]);
		assertEquals("0.0", data[2][17]);
		assertEquals("2150.0", data[2][18]);
		
		//check line for ap3
		assertEquals("3", data[3][0]);
		assertEquals("20.0", data[3][1]);
		assertEquals("2", data[3][2]);
		assertEquals("20.0", data[3][3]);
		assertEquals("20.0", data[3][4]);
		assertEquals("20.0", data[3][5]);
		assertEquals("50.0", data[3][6]);
		assertEquals("500.0", data[3][7]);
		assertEquals("15.0", data[3][8]);
		assertEquals("15.0", data[3][9]);
		assertEquals("300.0", data[3][10]);
		assertEquals("150.0", data[3][11]);
		assertEquals("0.0", data[3][12]);
		assertEquals("20.0", data[3][13]);
		assertEquals("0.0", data[3][14]);
		assertEquals("1080.0", data[3][15]);
		Double overdue = 31 * (20*0.002);
		assertEquals(((Double)(Math.round(overdue*100)/100.0)).toString(), data[3][16]);
		assertEquals("20.0", data[3][17]);
		Double sum = 1080 + overdue + 20;
		assertEquals(sum.toString(), data[3][18]);
		
		//check line for ap4
		assertEquals("4", data[4][0]);
		assertEquals("40.0", data[4][1]);
		assertEquals("4", data[4][2]);
		assertEquals("20.0", data[4][3]);
		assertEquals("20.0", data[4][4]);
		assertEquals("20.0", data[4][5]);
		assertEquals("50.0", data[4][6]);
		assertEquals("500.0", data[4][7]);
		assertEquals("10.0", data[4][8]);
		assertEquals("10.0", data[4][9]);
		assertEquals("200.0", data[4][10]);
		assertEquals("100.0", data[4][11]);
		assertEquals("40.0", data[4][12]);
		assertEquals("40.0", data[4][13]);
		assertEquals("1600.0", data[4][14]);
		assertEquals("2590.0", data[4][15]);
		assertEquals("0.0", data[4][16]);
		assertEquals("0.0", data[4][17]);
		assertEquals("2590.0", data[4][18]);
		
		//check line for total line
		assertEquals("Total ->", data[5][0]);
		assertEquals("120.0", data[5][1]);
		assertEquals("10", data[5][2]);
		assertEquals("80.0", data[5][3]);
		assertEquals("80.0", data[5][4]);
		assertEquals("80.0", data[5][5]);
		assertEquals("200.0", data[5][6]);
		assertEquals("2000.0", data[5][7]);
		assertEquals("45.0", data[5][8]);
		assertEquals("45.0", data[5][9]);
		assertEquals("900.0", data[5][10]);
		assertEquals("450.0", data[5][11]);
		assertEquals("60.0", data[5][12]);
		assertEquals("100.0", data[5][13]);
		assertEquals("4000.0", data[5][14]);
		assertEquals("7950.0", data[5][15]);
		assertEquals(overdue.toString(), data[5][16]);
		assertEquals("20.0", data[5][17]);
		sum += 6870;
		assertEquals(sum.toString(), data[5][18]);
	}
	
	@Test
	public void testMakeWarmingPrice(){
		Association association = makeAssociation();

		Double warmingPrice = TablePanel.makeWarmingPrice(association);
		
		//case 1
		assertEquals(warmingPrice, 40.0,0.0001);
		
		//case2
		//modify ap4 to have central heating
		association.getApartments().get(3).setCentralHeating(true);
		warmingPrice = TablePanel.makeWarmingPrice(association);
		assertEquals(warmingPrice, 66.6666,0.0001);
		
		//case 3 add 140 square meters to an apartment withou central heating
		association.getApartments().get(1).setArea(association.getApartments().get(3).getArea() + 140);
		association.setTotalArea(association.getTotalArea() + 140);
		warmingPrice = TablePanel.makeWarmingPrice(association);
		assertEquals(warmingPrice, 20.0,0.0001);
	}
	
	@Test
	public void testMakeGasPrice(){
		Association association = makeAssociation();
		
		Double gasPrice = TablePanel.makeGasPrice(association);
		
		//case 1
		assertEquals(gasPrice, 10.0,0.0001);
		
		//case 2
		//set gas contor to false to an apartment
		association.getApartments().get(0).setGasContor(false);
		gasPrice = TablePanel.makeGasPrice(association);
		assertEquals(gasPrice, 7.5,0.0001);
		
		//case 3
		//add 2 other person to an apartment
		association.getApartments().get(0).setPeopleNo(association.getApartments().get(0).getPeopleNo() + 2);
		association.setPeopleNoTotal(association.getPeopleNoTotal() + 2);
		gasPrice = TablePanel.makeGasPrice(association);
		assertEquals(gasPrice, 6.0,0.0001);
	}
	
	@Test
	public void testCalculatePenalties(){
		//we only use the date from association
		Association association = new Association();

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		Date date = cal.getTime();
		association.setnBLastUpdate(date);
		
		Double overdue = 20.0;
		Double penalties = TablePanel.calculatePenalties(overdue, association);
		
		//case 1
		assertEquals(penalties, 1.24,0.0001);
		
		//case 2
		cal.add(Calendar.DAY_OF_MONTH, +5);
		date = cal.getTime();
		association.setnBLastUpdate(date);
		penalties = TablePanel.calculatePenalties(overdue, association);
		assertEquals(penalties, 1.04,0.0001);
		
		//case 3
		overdue = 214.25;
		penalties = TablePanel.calculatePenalties(overdue, association);
		assertEquals(penalties, 11.141,0.001);
	}

	@Test
	public void testGetDays() {
		Calendar cal = Calendar.getInstance();
		
		Date d = cal.getTime();
		
		long days =  TablePanel.getDays(d);
		//case 1
		assertEquals(days, 0);
		
		//case 2
		cal.add(Calendar.MONTH, -1);
		d = cal.getTime();
		days =  TablePanel.getDays(d);
		assertEquals(days, 31);
		
		//case 3
		cal.add(Calendar.DAY_OF_MONTH, +10);
		d = cal.getTime();
		days =  TablePanel.getDays(d);
		assertEquals(days, 21);
	}
}
