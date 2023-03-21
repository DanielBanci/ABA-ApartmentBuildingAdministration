package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import appInterface.AdminAccount;


public class AdminAccountTest {

	@Test
	public void testGetCurrentMonth() {

		//case 1
		assertEquals("februarie", AdminAccount.getCurrentMonth(new Date()));
		
		//case 2
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		assertEquals("martie", AdminAccount.getCurrentMonth(cal.getTime()));
		
		//case 2
		cal.add(Calendar.MONTH, -3);
		assertEquals("decembrie", AdminAccount.getCurrentMonth(cal.getTime()));
	}

}
