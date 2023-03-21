package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import calendar.CalendarPanel;
import mainClasses.Admin;
import mainClasses.Apartment;
import mainClasses.Association;

public class CalendarPanelTest {

	
	@Test
	public void testConvertColumn() {
		assertEquals(5, CalendarPanel.convertColumn(0));
		assertEquals(6, CalendarPanel.convertColumn(1));
		assertEquals(0, CalendarPanel.convertColumn(2));
		assertEquals(1, CalendarPanel.convertColumn(3));
		assertEquals(2, CalendarPanel.convertColumn(4));
		assertEquals(3, CalendarPanel.convertColumn(5));
		assertEquals(4, CalendarPanel.convertColumn(6));
	}
	
	@Test
	public void testMakeColumnData() {
		Association association = makeAssociation();
		String[][] data = CalendarPanel.makeColumnData(association);
		assertEquals("1", data[0][2]);
		assertEquals("28", data[4][1]);
		assertEquals("1", data[0][2]);
		assertEquals("12", data[5][6]);
	}

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
		List<Date> dates = new ArrayList<>();//tarea = 120 tpeople = 10
		
		Association association = new Association(100, "street", "2", "V", "2f", "dist", "loc", 
	            20.0, 10.0, 80.0, 80.0, 80.0, 200.0, 2000.0, 4000.0, 10.0, 60.0, admin, 
	            120.0, 10, apartments, dates, date);
		return association;
	}
}
